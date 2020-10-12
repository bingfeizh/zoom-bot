package zoomapi.components;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import zoomapi.util;

import java.util.ArrayList;
import java.util.HashMap;

public class UserComponent extends BaseComponent {
    public HttpResponse<JsonNode> list(HashMap<String, String> path, HashMap<String, String> queries) throws InterruptedException {
        return get_request( "/users",
                queries, null);
    }

    public HttpResponse<JsonNode> create(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        return post_request("/users",
                null, data, null, null);
    }

    public HttpResponse<JsonNode> get(HashMap<String, String> path,
                                      HashMap<String, String> queries,
                                      HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("id");}}, false);
        return get_request("/users/" + path.get("id"),
                queries, null);
    }

    public HttpResponse<JsonNode> update(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("id");}}, false);
        return patch_request("/users/" + path.get("id"),
                queries, data, null, null);
    }

    public HttpResponse<JsonNode> delete(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("id");}}, false);
        return delete_request("/users/"+ path.get("id"),
                queries, null, null, null);
    }
}
