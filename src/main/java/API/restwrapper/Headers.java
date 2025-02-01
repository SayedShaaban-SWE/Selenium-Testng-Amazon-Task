package API.restwrapper;

import java.util.HashMap;
import java.util.Map;

public class Headers {
    public static Map<String, String> genericHeaders(){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
