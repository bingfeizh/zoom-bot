package zoomapi.components;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import zoomapi.util;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordingComponent extends BaseComponent{
    public HttpResponse<JsonNode> list(HashMap<String, String> path, HashMap<String, String> queries) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("user_id");}}, false);
        if(queries.containsKey("start")){
            queries.put("from", util.date_to_str(queries.get("start")));
            queries.remove("start");
        }
        if(queries.containsKey("end")){
            queries.put("to", util.date_to_str(queries.get("end")));
            queries.remove("end");
        }
        return get_request( "/users/" + path.get("user_id") + "/recordings",
                queries, null);
    }

    public HttpResponse<JsonNode> get(HashMap<String, String> path,
                                      HashMap<String, String> queries,
                                      HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("meeting_id");}}, false);
        return get_request("/meetings/" + path.get("meeting_id") + "/recordings",
                queries, null);
    }

    public HttpResponse<JsonNode> delete(HashMap<String, String> path,
                                         HashMap<String, String> queries,
                                         HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("meeting_id");}}, false);
        return delete_request("/meetings/" + path.get("meeting_id") + "/recordings",
                queries, null, null, null);
    }
}
