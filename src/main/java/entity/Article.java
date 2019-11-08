package entity;

/**
 * @author Yuliia Shcherbakova ON 07.11.2019
 * @project parser
 */
public class Article {
    private String id;
    private String uri;

    public Article(String id, String uri) {
        this.id = id;
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }
}
