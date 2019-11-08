import data.Counters;
import data.Strings;
import entity.JSONObjects;
import enums.JSONObjectsEnum;
import factories.JSONObjectsFactory;
import fileWork.JSONFileWriter;
import parsers.MainPageParser;
import parsers.Parser;

import java.io.File;
import java.io.IOException;

/**
 * @author Yuliia Shcherbakova ON 05.11.2019
 * @project parser
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        //if you want concurrency
        concurrent();
        //without multithreading
        //oneThreaded();
    }

    private static void concurrent() throws InterruptedException {
        Parser parser = new MainPageParser(Strings.MAIN_PAGE_URL,
                Strings.DATA_LOAD_URL, 100,
                JSONObjectsEnum.PRODUCT_PAGE_CONCURRENT);
        try {
            parser.parse();
        } catch (IOException e) {
            return;
        }
        JSONObjects jsonObjectList = JSONObjectsFactory
                .getJSONObjectList(JSONObjectsEnum.PRODUCT_PAGE_CONCURRENT);
        File file = new File(Strings.PRODUCT_PAGE_JSON_FILE_PATH);
        JSONFileWriter writer = new JSONFileWriter(file, jsonObjectList);
        writer.write();

        System.out.println("Requests: " + Counters.getNumberOfRequests());
        System.out.println("Products counting colors: " + Counters.getNumberOfProducts());
    }

    private static void oneThreaded() {
        Parser parser = new MainPageParser(Strings.MAIN_PAGE_URL,
                Strings.DATA_LOAD_URL, 100,
                JSONObjectsEnum.PRODUCT_PAGE);
        try {
            parser.parse();
        } catch (IOException e) {
            return;
        }
        JSONObjects jsonObjectList = JSONObjectsFactory
                .getJSONObjectList(JSONObjectsEnum.PRODUCT_PAGE);
        File file = new File(Strings.PRODUCT_PAGE_JSON_FILE_PATH);
        JSONFileWriter writer = new JSONFileWriter(file, jsonObjectList);
        writer.write();
    }

}
