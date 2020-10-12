package zoomapi.components;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import zoomapi.util;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportComponent extends BaseComponent {
    public HttpResponse<JsonNode> get_account_report(HashMap<String, String> path,
                                                     HashMap<String, String> queries,
                                                     HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("start_time"); add("end_time");}}, false);
        if(queries.containsKey("start_time")){
            queries.put("from", util.date_to_str(queries.get("start_time")));
            queries.remove("start");
        }
        if(queries.containsKey("end_time")){
            queries.put("to", util.date_to_str(queries.get("end_time")));
            queries.remove("end");
        }
        return post_request("/report/users", queries, null, null, null);
    }
    public HttpResponse<JsonNode> get_user_report(HashMap<String, String> path,
                                                     HashMap<String, String> queries,
                                                     HashMap<String, String> data) throws InterruptedException {
        util.require_keys(path, new ArrayList<String>(){{add("user_id"); add("start_time"); add("end_time");}}, false);
        if(queries.containsKey("start_time")){
            queries.put("from", util.date_to_str(queries.get("start_time")));
            queries.remove("start");
        }
        if(queries.containsKey("end_time")){
            queries.put("to", util.date_to_str(queries.get("end_time")));
            queries.remove("end");
        }
        return post_request("/report/users/" + path.get("user_id") + "/meetings", queries, null, null, null);
    }

}
