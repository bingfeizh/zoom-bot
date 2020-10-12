package zoomapi.components;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import zoomapi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatMessagesComponent extends BaseComponent {

    public HttpResponse<JsonNode> list(HashMap<String, String> path, HashMap<String, String> queries) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("user_id");}}, false);
        return get_request("/chat/users/" + path.get("user_id") + "/messages",
                queries, null);
    }

    public HttpResponse<JsonNode> post(HashMap<String, String> path,
                                       HashMap<String, String> queries,
                                       HashMap<String, String> data) throws InterruptedException {
        return post_request("/chat/users/me/messages",
                queries, data, null, null);
    }

    public HttpResponse<JsonNode> update(HashMap<String, String> path,
                                       HashMap<String, String> queries,
                                       HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("message_id");}}, false);
        return put_request("/chat/users/me/messages/" + path.get("message_id"),
                queries, data, null, null);
    }

    public HttpResponse<JsonNode> delete(HashMap<String, String> path,
                                       HashMap<String, String> queries,
                                       HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("message_id");}}, false);
        return delete_request("/chat/users/me/messages/" + path.get("message_id"),
                queries, data, null, null);
    }

}
