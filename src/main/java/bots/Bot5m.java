package bots;

import kong.unirest.json.JSONArray;
import zoomapi.api.DataComponent;
import zoomapi.baseUnit.Channel;
import zoomapi.baseUnit.Member;
import zoomapi.baseUnit.Message;
import zoomapi.clients.*;
import zoomapi.components.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Bot5m {

    @SuppressWarnings("Cookie rejected")
    public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException, InterruptedException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException, SQLException {

        String id = "";
        String secret = "";
        String port = "";
        String browserPath = "";
        String redirectUrl = "";

        INIParser parser = new INIParser();
        if (parser.parse_ini_file("")) {
            id = parser.getClient_id();
            secret = parser.getClient_secret();
            port = parser.getPort();
            browserPath = parser.getBrowser_path();
            redirectUrl = parser.getRedirectURL();
        }

        OAuthClient client = new OAuthClient();
        client.init(id, secret, port, redirectUrl, browserPath);

        DataComponent dataComponent = new DataComponent(client);

        System.out.println("----------- Start test -----------");
        System.out.println("List the channels");
        List<Channel> channels = dataComponent.getChannels();
        System.out.println(channels);
        System.out.println("***************************************************************");

        System.out.println("Get a channel");
        Channel channel = dataComponent.getChannel("test");
        System.out.println(channel);
        System.out.println("***************************************************************");

        System.out.println("List Channel Members");
        List<Member> members = dataComponent.getMembers(channel.getId());
        System.out.println(members);
        System.out.println("***************************************************************");

        System.out.println("------------- Chat message Test --------------");

        System.out.println("List messages");
        List<Message> messages = dataComponent.getMessages(channel.getId());
        System.out.println(messages);
        System.out.println("***************************************************************");
    }
}


