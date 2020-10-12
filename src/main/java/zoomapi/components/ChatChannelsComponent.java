package zoomapi.components;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import zoomapi.util;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatChannelsComponent extends BaseComponent{
    public HttpResponse<JsonNode> list(HashMap<String, String> path, HashMap<String, String> queries) throws InterruptedException {
        return get_request("/chat/users/me/channels",
                queries, null);
    }

    public HttpResponse<JsonNode> create(HashMap<String, String> path,
                                       HashMap<String, String> queries,
                                       HashMap<String, String> data) throws InterruptedException {
        return post_request("/chat/users/me/channels",
                queries, data, null, null);
    }

    public HttpResponse<JsonNode> get(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("channel_id");}}, false);
        return get_request("/chat/channels/" + path.get("channel_id"),
                queries, null);
    }

    public HttpResponse<JsonNode> update(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("channel_id");}}, false);
        return patch_request("/chat/channels/" + path.get("channel_id"),
                queries, data, null, null);
    }

    public HttpResponse<JsonNode> delete(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("channel_id");}}, false);
        return delete_request("/chat/channels/" + path.get("channel_id"),
                null, null, null, null);
    }

    public HttpResponse<JsonNode> list_member(HashMap<String, String> path,
                                      HashMap<String, String> queries,
                                      HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("channel_id");}}, false);
        return get_request("/chat/channels/" + path.get("channel_id") + "/members",
                queries, null);
    }

    public HttpResponse<JsonNode> invite(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("channel_id");}}, false);
        return post_request("chat/channels/" + path.get("channel_id") + "/members",
                queries, data, null, null);
    }

    public HttpResponse<JsonNode> join(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("channel_id");}}, false);
        return post_request("chat/channels/" + path.get("channel_id") + "/members/me",
                queries, null, null, null);
    }

    public HttpResponse<JsonNode> leave(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("channel_id");}}, false);
        return delete_request("/chat/channels/" + path.get("channel_id") + "/members/me",
                null, null, null, null);
    }

    public HttpResponse<JsonNode> remove(HashMap<String, String> path,
                                        HashMap<String, String> queries,
                                        HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("channel_id"); add("member_id");}}, false);
        return delete_request("/chat/channels/" +
                path.get("channel_id") +
                "/members/" +
                path.get("member_id"),
                null, null, null, null);
    }
}
