package systems.PAJRs;

import entity.JSONObjects;
import entity.MapOfJSONObjects;
import entity.Product;
import enums.JSONObjectsEnum;
import org.json.simple.JSONObject;
import parsers.Parser;
import parsers.ProductPageParser;
import systems.JSONObjectCreator;

import java.io.IOException;
import java.util.Map;

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
    public void parse() throws IOException {
        super.parse();
        product = ((ProductPageParser) parser).getProduct();
        JSONObject jsonFromProduct = JSONObjectCreator.getJSONFromProduct(product);
        JSONObjects jsonObjects = getJSONObjects();
        jsonObjects.addElement(jsonFromProduct);
        Map map = MapOfJSONObjects.getMapOfJSONObjects();
    }
}
