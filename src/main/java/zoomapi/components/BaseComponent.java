package zoomapi.components;

import kong.unirest.Cookie;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import zoomapi.clients.ApiClient;
import zoomapi.util;

import java.util.HashMap;

public class BaseComponent extends ApiClient {
    public void init(String base_uri, HashMap<String, String> config, int timeout) {
        if (timeout <= 0) timeout = 15;
        super.init(base_uri,timeout);
        this.config = config;
    }
    public void init(String base_uri, HashMap<String, String> config) {
        init(base_uri, config, 15);
    }

    public HttpResponse<JsonNode> post_request(String endpoint,
                                               HashMap<String, String> params,
                                               HashMap<String, String> data,
                                               HashMap<String, String> headers,
                                               Cookie cookies) throws InterruptedException {
        if (headers == null) {
            headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + config.get("token"));
        }
        return super.post_request(endpoint,params,data,headers,cookies);
    }
}
