package parsers;

import enums.JSONObjectsEnum;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * @author Yuliia Shcherbakova ON 05.11.2019
 * @project parser
 */
public abstract class Parser {
    private String url;
    protected JSONObjectsEnum whatToParse;

    public Parser(String url, JSONObjectsEnum whatToParse) {
        this.url = url;
        this.whatToParse = whatToParse;
    }

    protected Connection connect() {
        return Jsoup.connect(url)
                .header("Accept-Encoding", "gzip, deflate")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                .maxBodySize(0)
                .timeout(6000)
                .ignoreContentType(true);
    }

    public abstract void parse() throws IOException;

    public String getUrl() {
        return url;
    }

}
