package systems.PAJRs;

import entity.JSONObjects;
import enums.JSONObjectsEnum;
import factories.JSONObjectsFactory;
import parsers.Parser;

import java.io.IOException;

/**
 * @author Yuliia Shcherbakova ON 07.11.2019
 * @project parser
 *
 * THIS CLASS PARSES PAGE AND FILLS JSON OBJECTS ARRAY
 *
 */
public abstract class ParserAndJSONReceiver {
    protected Parser parser;
    private JSONObjectsEnum objectsEnum;

    public ParserAndJSONReceiver(Parser parser, JSONObjectsEnum objectsEnum) {
        this.parser = parser;
        this.objectsEnum = objectsEnum;
    }


    public void parse() throws IOException {
            parser.parse();
            generateJSON();
    }

    protected abstract void generateJSON();

    protected JSONObjects getJSONObjects(){
        return JSONObjectsFactory.getJSONObjectList(objectsEnum);
    }
}

