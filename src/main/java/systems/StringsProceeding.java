package systems;

/**
 * @author Yuliia Shcherbakova ON 07.11.2019
 * @project parser
 */
public class StringsProceeding {

    public static String generateUri(String id, String productName, String brand) {
        return "/p/"+convertString(brand)+"/"+convertString(productName)+"-"+id;
    }

    private static String convertString(String str) {
        return str = str.toLowerCase()
                .replace(" & ", "-")
                .replace("\'", "")
                .replace('\\', '-')
                .replace('/', '-')
                .replace('+', '-')
                .replace(' ', '-');
    }

    public static String clearURI(String url){
        int index = url.indexOf("/");
        int protocolSchemeLength = index + 2; //size of http:// or https://
        int uriStart = url.substring(protocolSchemeLength).indexOf("/");
        return url.substring(0, uriStart+protocolSchemeLength);
    }
}
