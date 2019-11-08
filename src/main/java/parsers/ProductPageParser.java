package parsers;

import data.Counters;
import entity.Product;
import enums.JSONObjectsEnum;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import systems.StringsProceeding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Yuliia Shcherbakova ON 06.11.2019
 * @project parser
 */
public class ProductPageParser extends Parser {
    private String articleId;
    private Product product;

    public ProductPageParser(String url, Product product, JSONObjectsEnum whatToParse) {
        super(url, whatToParse);
        this.product = product;
    }

    public ProductPageParser(String url, String articleId, JSONObjectsEnum whatToParse) {
        super(url, whatToParse);
        this.articleId = articleId;
    }


    public void parse() throws IOException {
        Connection connection = connect();
        Document document = null;
        try {
            document = connection.get();
            Counters.incrementRequests();
        } catch (IOException e) {
            System.err.println("Could not connect to " + getUrl());
            throw e;
        }
        Elements section = document.getElementsByAttributeValue("data-test-id", "StaticSplitSection");
        String productName = section
                .select("div[data-test-id=ProductName]")
                .first()
                .text();
        String brand = section
                .select("img[data-test-id=BrandLogo]")
                .first()
                .attr("alt");
        Elements colorsDiv = section
                .select("[data-test-id=ThumbnailsList],[data-test-id=Slider]");
        String colorInfo = section
                .select("[data-test-id=ColorVariantColorInfo]").first().text();
        Elements priceEl = section.select("[data-test-id=ProductPriceFormattedBasePrice]");
        String price = (priceEl.size() >= 1) ? priceEl.first().text()
                : section.select("[data-test-id=FormattedSalePrice]")
                .first()
                .text();
        if (product == null) {
            product = new Product(articleId, productName, brand, price, colorInfo);
            List<String> colorsStrs = new ArrayList<String>();
            for (Element colour : colorsDiv.select("a").next()) {
                String uri = colour.attr("href");
                parsingAnotherColors(uri);
            }
        } else {
            setArticleId();
            if (product.getAnotherColors() == null)
                initAnotherColors();
            product.addAnotherColors(new Product(articleId, productName, price, colorInfo));
        }

        Counters.incrementProducts();
    }

    private void parsingAnotherColors(String uri) throws IOException {
        String page = StringsProceeding.clearURI(getUrl());
        ProductPageParser pageParser = pageParser =
                new ProductPageParser(page.concat(uri), product, whatToParse);
        pageParser.parse();

    }

    private void initAnotherColors() {
        switch (whatToParse) {
            case PRODUCT_PAGE: {
                product.setAnotherColors(new ArrayList<Product>());
                break;
            }
            case PRODUCT_PAGE_CONCURRENT: {
                product.setAnotherColors(new CopyOnWriteArrayList<Product>());
                break;
            }
        }
    }

    private void setArticleId() {
        String url = getUrl();
        int idx = url.lastIndexOf("-");
        articleId = url.substring(idx + 1);
    }

    public Product getProduct() {
        return product;
    }
}
