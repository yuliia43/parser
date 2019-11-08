package parsers;

import data.Counters;
import entity.Product;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import systems.StringsProceeding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 06.11.2019
 * @project parser
 */
public class ProductPageParser extends Parser {
    private String articleId;
    private Product product;

    public ProductPageParser(String url, Product product) {
        super(url);
        this.product = product;
    }

    public ProductPageParser(String url, String articleId) {
        super(url);
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
        Product tempProduct = receiveProduct(section);
        addSizes(section, tempProduct);
        parseProduct(section, tempProduct);
        Counters.incrementProducts();
    }

    //searching sizes for products; recently searching only sizes in bubble
    private void addSizes(Elements section, Product tempProduct) {
        Elements bubbleSizes = section.select("[data-test-id=SizeBubbleList] [data-test-id=SizeBubble_available]");
        List<String> sizes = new ArrayList<String>();
        for (Element size : bubbleSizes) {
            sizes.add(size.text());
        }
        if (!sizes.isEmpty())
            tempProduct.setSizes(sizes);
    }

    //detects if product is root and parses it in appropriate way
    private void parseProduct(Elements section, Product tempProduct) throws IOException {
        if (product == null) {
            this.product = tempProduct;
            Elements colorsDiv = section
                    .select("[data-test-id=ThumbnailsList],[data-test-id=Slider]");
            for (Element colour : colorsDiv.select("a").next()) {
                String uri = colour.attr("href");
                parsingAnotherColors(uri);
            }
        } else {
            setArticleId();
            if (product.getAnotherColors() == null)
                product.setAnotherColors(new ArrayList<Product>());
            product.addAnotherColors(tempProduct);
        }
    }

    private Product receiveProduct(Elements section) {
        String productName = section
                .select("div[data-test-id=ProductName]")
                .first()
                .text();
        String brand = section
                .select("img[data-test-id=BrandLogo]")
                .first()
                .attr("alt");
        String colorInfo = section
                .select("[data-test-id=ColorVariantColorInfo]").first().text();
        Elements priceEl = section.select("[data-test-id=ProductPriceFormattedBasePrice]");
        String price = (priceEl.size() >= 1) ? priceEl.first().text()
                : section.select("[data-test-id=FormattedSalePrice]")
                .first()
                .text();
        return new Product(articleId, productName, brand, price, colorInfo);
    }

    private void parsingAnotherColors(String uri) throws IOException {
        String page = StringsProceeding.clearURI(getUrl());
        ProductPageParser pageParser =
                new ProductPageParser(page.concat(uri), product);
        pageParser.parse();

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
