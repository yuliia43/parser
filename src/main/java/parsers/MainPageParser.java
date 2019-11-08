package parsers;

import connectionPool.ConnectionPoolHolder;
import data.Counters;
import entity.Article;
import enums.JSONObjectsEnum;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import systems.PAJRs.ProductPagePAJR;
import systems.StringsProceeding;
import systems.PAJRs.threads.ProductPageConcurrentPAJR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuliia Shcherbakova ON 05.11.2019
 * @project parser
 */
public class MainPageParser extends Parser {
    private String dataLoadUrl; //url to load data (founded by debugging website)

    public MainPageParser(String url, String dataLoadUrl, int numberOfProducts,  JSONObjectsEnum whatToParse) {
        super(url, whatToParse);
        /*loading only top numberOfProducts entries*/
        this.dataLoadUrl = dataLoadUrl.concat(String.valueOf(numberOfProducts));
    }

    public void parse() throws IOException {
        List<Article> articles = getArticles();
        parseAccordingToEnum(articles);
    }

    public List<Article> getArticles() {
        JSONArray entities = Unirest.get(dataLoadUrl)
                .asJson().getBody().getObject().getJSONArray("entities");
        Counters.incrementRequests();
        List<Article> articles = new ArrayList<Article>();
        for (int i = 0; i < entities.length(); i++) {
            JSONObject jsonObject = entities.getJSONObject(i);
            String id = jsonObject.get("id").toString();
            String brand = getAttributeValue(jsonObject, "brand");
            String name = getAttributeValue(jsonObject, "name");
            String uri = StringsProceeding.generateUri(id, name, brand);
            articles.add(new Article(id, uri));
        }
        return articles;
    }

    private String getAttributeValue(JSONObject jsonObject, String attribute) {
        return (String) jsonObject
                .getJSONObject("attributes")
                .getJSONObject(attribute)
                .getJSONObject("values")
                .get("label");
    }

    private void parseAccordingToEnum(List<Article> articles) throws IOException {
        switch (whatToParse) {
            case PRODUCT_PAGE: {
                nonConcurrentParse(articles);
                break;
            }
            case PRODUCT_PAGE_CONCURRENT: {
                concurrentParse(articles);
                break;
            }
        }
    }

    private void concurrentParse(List<Article> articles) {
        ExecutorService connectionPool = ConnectionPoolHolder.getConnectionPool();
        for (Article article : articles) {
            ProductPageConcurrentPAJR receiver =
                    (ProductPageConcurrentPAJR) parseProductPage(article);
            connectionPool.submit(receiver);
        }
        connectionPool.shutdown();
        //waiting until the shutdown
        try {
            //disconnect after 1 minute
            connectionPool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("Terminated! Not all data may be loaded into the file");
        }
    }

    private void nonConcurrentParse(List<Article> articles) throws IOException {
        for (Article article : articles) {
            ProductPagePAJR receiver =
                    parseProductPage(article);
            receiver.parse();
        }
    }

    private ProductPagePAJR parseProductPage(Article article) {
        String uri = article.getUri();
        Parser parser = new ProductPageParser(getUrl().concat(uri), article.getId(), whatToParse);
        return getParser(parser);
    }


    private ProductPagePAJR getParser(Parser parser) {
        switch (whatToParse) {
            case PRODUCT_PAGE:
                return new ProductPagePAJR(parser, whatToParse);
            case PRODUCT_PAGE_CONCURRENT:
                return new ProductPageConcurrentPAJR(parser, whatToParse);
        }
        return null;
    }

}
