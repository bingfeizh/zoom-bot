package zoomapi.components;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import zoomapi.util;

import java.util.ArrayList;
import java.util.HashMap;

public class MeetingComponent extends BaseComponent {
    public HttpResponse<JsonNode> list(HashMap<String, String> path, HashMap<String, String> queries) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("user_id");}}, false);
        return get_request( "/users/" + path.get("user_id") + "/meetings",
                queries, null);
    }

    public HttpResponse<JsonNode> create(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("user_id");}}, false);
        if(data.containsKey("start_time")){
            data.put("start_time", util.date_to_str(data.get("start_time")));
        }
        return post_request("/users/" + path.get("user_id") + "/meetings",
                queries, data, null, null);
    }

    public HttpResponse<JsonNode> get(HashMap<String, String> path,
                                      HashMap<String, String> queries,
                                      HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("id");}}, false);
        return get_request("/meetings/" + path.get("id"),
                queries, null);
    }

    public HttpResponse<JsonNode> update(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("id");}}, false);
        if(data.containsKey("start_time")){
            data.put("start_time", util.date_to_str(data.get("start_time")));
        }
        return patch_request("/meetings/" + path.get("id"),
                queries, data, null, null);
    }

    public HttpResponse<JsonNode> delete(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("id");}}, false);
        return delete_request("/meetings/" + path.get("id"),
                queries, null, null, null);
    }
}
