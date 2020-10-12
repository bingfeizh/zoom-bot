package bots;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import zoomapi.clients.JWTZoomClient;
import zoomapi.clients.OAuthClient;

import java.io.IOException;
import java.util.HashMap;

public class JwtBot {

    static HashMap<String, String> path;
    static HashMap<String, String> query;
    static HashMap<String, String> data;
    static HashMap<String, String> member;

    static String id;
    static String secret;
    static String port;
    static String browserPath;
    static String redirectUrl;


    public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException, InterruptedException {
        INIParser parser = new INIParser();
        if (parser.parse_ini_file("")) {
            id = parser.getClient_id();
            secret = parser.getClient_secret();
            port = parser.getPort();
            browserPath = parser.getBrowser_path();
            redirectUrl = parser.getRedirectURL();
        }

        path = new HashMap<>();
        query = new HashMap<>();
        data = new HashMap<>();

        String contact = "yijiz13@uci.edu";

        member = new HashMap<>();
        member.put("email", contact);


        JWTZoomClient client = new JWTZoomClient();
        client.init(id,secret);

        JSONArray userArr = client.user().list(path,query).getBody().getObject().getJSONArray("users");
        System.out.println(userArr.toList());
        String cid = "";
        for(int i = 0; i < userArr.length(); i++){
            cid = userArr.getJSONObject(i).get("id").toString();
            path.put("user_id", cid);
            JSONArray meetings = client.meeting().list(path,query).getBody().getObject().getJSONArray("meetings");
            System.out.println(meetings.toList());
        }


        path.put("user_id", "diva@metaverseink.com");
        JSONArray meetings_list = client.meeting().list(path,query).getBody().getObject().getJSONArray("meetings");
        System.out.println(meetings_list.toList());
    }
}
