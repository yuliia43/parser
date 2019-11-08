package entity;

import data.Strings;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Yuliia Shcherbakova ON 07.11.2019
 * @project parser
 */
public class MapOfJSONObjects {
    private static Map<String, JSONObjects> mapOfJSONObjects;

    static {
        mapOfJSONObjects = new HashMap<String, JSONObjects>();
        mapOfJSONObjects.put(Strings.PRODUCT_PAGE, new JSONObjects(new ArrayList<JSONObject>()));
        mapOfJSONObjects.put(Strings.PRODUCT_PAGE_CONCURRENT, new JSONObjects(new CopyOnWriteArrayList<JSONObject>()));
    }

    public static Map<String, JSONObjects> getMapOfJSONObjects() {
        return mapOfJSONObjects;
    }
}
