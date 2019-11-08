package factories;

import data.Strings;
import entity.JSONObjects;
import entity.MapOfJSONObjects;
import enums.JSONObjectsEnum;

/**
 * @author Yuliia Shcherbakova ON 07.11.2019
 * @project parser
 */
public class JSONObjectsFactory {

    private JSONObjectsFactory() {

    }

    //RECEIVE MAP ENTRY DEPENDING ON WAY TO PARSE
    public static JSONObjects getJSONObjectList(JSONObjectsEnum objectsEnum) {
        switch (objectsEnum) {
            case PRODUCT_PAGE:
                return MapOfJSONObjects.getMapOfJSONObjects().get(Strings.PRODUCT_PAGE);
            case PRODUCT_PAGE_CONCURRENT:
                return MapOfJSONObjects.getMapOfJSONObjects().get(Strings.PRODUCT_PAGE_CONCURRENT);
            default:
                return null;
        }
    }
}
