package zoomapi.components;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import zoomapi.util;

import java.util.ArrayList;
import java.util.HashMap;

public class WebinarComponent extends BaseComponent{
    public HttpResponse<JsonNode> list(HashMap<String, String> path, HashMap<String, String> queries) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("user_id");}}, false);
        return get_request( "/users/" + path.get("user_id") + "/webinars",
                queries, null);
    }

    public HttpResponse<JsonNode> create(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("user_id");}}, false);
        if(data.containsKey("start_time")){
            data.put("start_time", util.date_to_str(data.get("start_time")));
        }
        return post_request("/users/" + path.get("user_id") + "/webinars",
                null, data, null, null);
    }

    public HttpResponse<JsonNode> get(HashMap<String, String> path,
                                      HashMap<String, String> queries,
                                      HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("id");}}, false);
        return get_request("/webinars/" + path.get("id"),
                queries, null);
    }

    public HttpResponse<JsonNode> update(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("id");}}, false);
        if(data.containsKey("start_time")){
            data.put("start_time", util.date_to_str(data.get("start_time")));
        }
        return patch_request("/webinars/" + path.get("id"),
                queries, data, null, null);
    }

    public HttpResponse<JsonNode> delete(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("id");}}, false);
        return delete_request("webinars/" + path.get("id"),
                queries, null, null, null);
    }

    public HttpResponse<JsonNode> end(HashMap<String, String> path,
                                      HashMap<String, String> queries,
                                      HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("id");}}, false);
        return put_request("/webinars/" + path.get("id") + "/status",
                null, data, null, null);
    }

    public HttpResponse<JsonNode> register(HashMap<String, String> path,
                                      HashMap<String, String> queries,
                                      HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>()
            {{add("id"); add("email"); add("first_name"); add("last_name");}}, false);
        return post_request("/webinars/" + path.get("id") + "/registrants",
                        queries, data, null, null);
    }
}
