package entity;

import org.json.simple.JSONObject;

import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 07.11.2019
 * @project parser
 */
public class JSONObjects {
    private List<JSONObject> jsonObjectList;

    public JSONObjects(List<JSONObject> jsonObjectList) {
        this.jsonObjectList = jsonObjectList;
    }

    public List<JSONObject> getJsonObjectList() {
        return jsonObjectList;
    }

    public void setJsonObjectList(List<JSONObject> objectList) {
        jsonObjectList = objectList;
    }

    public void addElement(JSONObject object){
        jsonObjectList.add(object);
    }
}
