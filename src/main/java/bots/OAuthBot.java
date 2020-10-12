package bots;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.ini4j.spi.IniParser;
import zoomapi.clients.OAuthClient;
import zoomapi.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class OAuthBot {
    static HashMap<String, String> path;
    static HashMap<String, String> query;
    static HashMap<String, String> data;
    static HashMap<String, String> member;

    static String id;
    static String secret;
    static String port;
    static String browserPath;
    static String redirectUrl;


    public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException, InterruptedException, NoSuchMethodException, SQLException, InvocationTargetException {
        INIParser parser = new INIParser();
        if(parser.parse_ini_file("")){
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


        OAuthClient client = new OAuthClient();
        client.init(id, secret, port, redirectUrl, browserPath);

        path.put("id", "me");
        String username = client.user().get(path,query,data).getBody().toString();

        path.clear();

        path.put("user_id", "me");

        JSONArray meetings = client.meeting().list(path,query).getBody().getObject().getJSONArray("meetings");
        System.out.println(meetings.toList());

        path.clear();

        JSONArray channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
        System.out.println(channelArr.toList());

        String cid = "";
        for(int i = 0; i < channelArr.length(); i++){
            JSONObject channel = channelArr.getJSONObject(i);
            System.out.println(channel.toString());
            if(channel.get("name").equals("test")){
                System.out.println("Found channel Test " + channel.get("id"));
                cid = channel.get(id).toString();
            }
        }

        boolean stop = false;

        Scanner scanner = new Scanner(System.in);
        while(!stop){
            String input = scanner.nextLine();
            data.put("message", input);
            data.put("to_channel", cid);

            JSONObject response = client.chatMessages().post(path,query,data).getBody().getObject();
            System.out.println(response.toString());
            if(input.equals("stop")) stop = true;
        }

    }
}
