package systems.PAJRs;

import entity.JSONObjects;
import entity.Product;
import enums.JSONObjectsEnum;
import org.json.simple.JSONObject;
import parsers.Parser;
import parsers.ProductPageParser;
import systems.JSONObjectCreator;

/**
 * @author Yuliia Shcherbakova ON 07.11.2019
 * @project parser
 *
 *
 * PAJR MEANS ParserAndJSONReceiver
 *
 */
public class ProductPagePAJR extends ParserAndJSONReceiver {
    private Product product;

    public ProductPagePAJR(Parser parser, JSONObjectsEnum objectsEnum) {
        super(parser, objectsEnum);
    }

    @Override
    protected void generateJSON() {
        product = ((ProductPageParser) parser).getProduct();
        JSONObject jsonFromProduct = JSONObjectCreator.getJSONFromProduct(product);
        JSONObjects jsonObjects = getJSONObjects();
        jsonObjects.addElement(jsonFromProduct);
    }

}
