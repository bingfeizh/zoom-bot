package zoomapi.clients;

import zoomapi.components.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class ZoomClient extends ApiClient{

    String API_BASE_URIS;
    HashMap<String, Class> COMPONENT_CLASSES;

    public ZoomClient() {
        API_BASE_URIS = "https://api.zoom.us/v2";
        COMPONENT_CLASSES = new HashMap<>();
        COMPONENT_CLASSES.put("user", UserComponent.class);
        COMPONENT_CLASSES.put("meeting", MeetingComponent.class);
        COMPONENT_CLASSES.put("report", ReportComponent.class);
        COMPONENT_CLASSES.put("webinar", WebinarComponent.class);
        COMPONENT_CLASSES.put("recording", ReportComponent.class);
    }

    public void init(String api_key, String api_secret, String data_type, int timeout) throws IllegalAccessException, InstantiationException, UnsupportedEncodingException {
        super.init(API_BASE_URIS, timeout);
        config.put("api_key", api_key);
        config.put("api_secret", api_secret);
        config.put("data_type", data_type);
    }
    public void init(String api_key, String api_secret) throws InstantiationException, IllegalAccessException, UnsupportedEncodingException {
        init(api_key, api_secret, "json", 15);
    }
    public void init(String api_key, String api_secret, String data_type) throws InstantiationException, IllegalAccessException, UnsupportedEncodingException {
        init(api_key, api_secret, data_type, 15);
    }
    public void init(String api_key, String api_secret, int timeout) throws InstantiationException, IllegalAccessException, UnsupportedEncodingException {
        init(api_key, api_secret, "json", timeout);
    }

    public void refreshToken() throws IOException {}

    public void setApiKey(String api_key) {
        this.config.put("api_key", api_key);
    }

    public void setApiSecret(String api_secret) {
        this.config.put("api_secret", api_secret);
    }

    public String getApiKey() {
        return this.config.get("api_key");
    }

    public String getApiSecret() {
        return this.config.get("api_secret");
    }

//    public Class meeting() {
//        return COMPONENT_CLASSES.get("meeting");
//    }
//
//    public Class report() {
//        return COMPONENT_CLASSES.get("report");
//    }
//
//    public Class user() {
//        return COMPONENT_CLASSES.get("user");
//    }
//
//    public Class webinar() {
//        return COMPONENT_CLASSES.get("webinar");
//    }
//
//    public Class recording() {
//        return COMPONENT_CLASSES.get("recording");
//    }
}
