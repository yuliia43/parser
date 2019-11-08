package fileWork;

import entity.JSONObjects;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 07.11.2019
 * @project parser
 */
public class JSONFileWriter {
    private File file;
    private JSONObjects objects;

    public JSONFileWriter(File file, JSONObjects objects) {
        this.file = file;
        this.objects = objects;
    }

    public void write() {
        FileWriter writer = null;
        try {
            if (!file.exists())
                file.createNewFile();
            writer = new FileWriter(file);
            List<JSONObject> jsonObjectList = objects.getJsonObjectList();
            //System.out.println("Unique products: " + jsonObjectList.size());
            JSONArray array = new JSONArray();
            array.addAll(jsonObjectList);
            writer.write(array.toJSONString());
            writer.flush();
        } catch (IOException e) {
            System.out.println("Could not receive file" + file.getName());
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println("IOException");
            }
        }
    }


}
