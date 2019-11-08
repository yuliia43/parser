package systems;

import entity.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 07.11.2019
 * @project parser
 */
public class JSONObjectCreator {

    public static JSONObject getJSONFromProduct(Product product) {
        JSONObject object = new JSONObject();
        object.put("articleId", product.getArticleId());
        object.put("brand", product.getBrand());
        object.put("productName", product.getProductName());
        object.put("price", product.getPrice());
        object.put("color", product.getColor());
        List<Product> anotherColors = product.getAnotherColors();
        if (anotherColors != null) {
            JSONArray array = new JSONArray();
            for (Product anotherProduct : anotherColors) {
                JSONObject newObject = new JSONObject();
                newObject.put("articleId", anotherProduct.getArticleId());
                newObject.put("color", anotherProduct.getColor());
                //write productName only if it is unique
                if (!product.getProductName().equals(anotherProduct.getProductName()))
                    newObject.put("productName", anotherProduct.getProductName());
                //write price only if it is unique
                if (!product.getPrice().equals(anotherProduct.getPrice()))
                    newObject.put("price", anotherProduct.getPrice());
                array.add(newObject);
            }
            object.put("anotherColors", array);
        }
        return object;
    }

}
