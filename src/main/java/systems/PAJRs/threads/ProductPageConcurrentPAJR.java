package systems.PAJRs.threads;

import enums.JSONObjectsEnum;
import parsers.Parser;
import systems.PAJRs.ProductPagePAJR;

import java.io.IOException;

/**
 * @author Yuliia Shcherbakova ON 07.11.2019
 * @project parser
 *
 * PAJR MEANS ParserAndJSONReceiver
 *
 */
public class ProductPageConcurrentPAJR extends ProductPagePAJR implements Runnable {
    public ProductPageConcurrentPAJR(Parser parser, JSONObjectsEnum objectsEnum) {
        super(parser, objectsEnum);
    }

    public void run() {
        try {
            parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
