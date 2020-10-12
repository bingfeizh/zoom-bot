package bots;

import kong.unirest.json.JSONArray;
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
import java.util.function.Predicate;

public class ChatBot {

    static HashMap<String, String> path;
    static HashMap<String, String> query;
    static HashMap<String, String> data;

    @SuppressWarnings("Cookie rejected")
    public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException, InterruptedException, NoSuchMethodException, SQLException, InvocationTargetException {

        String channel_name = "test";
        String message = "123";
        String sender_key = "bingfei";
        String message_key = "hello";


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

        path = new HashMap<>();
        query = new HashMap<>();
        data = new HashMap<>();

        OAuthClient client = new OAuthClient();
        client.init(id, secret, port, redirectUrl, browserPath);

        System.out.println("----------- Start test -----------");
        System.out.println("Send a message to channel \"test\"");
        client.chat().send(channel_name, message);
        System.out.println("***************************************************************");

        System.out.println("List messages in channel \"" + channel_name + "\" from 5 days ago to now");
        List<Message> h = client.chat().history(channel_name, LocalDate.now(ZoneOffset.UTC).minusDays(5),
                LocalDate.now(ZoneOffset.UTC).plusDays(1));
        for (Message m : h) {
            System.out.println(m.toString());
        }
        System.out.println("***************************************************************");

        System.out.println("Search messages contains \"" + message_key + "\" in the history of channel \"" + channel_name + "\"");
        //Predicate<Message> predicate = i -> i.getSender().contains("hello");
        List<Message> msgSearch = client.chat().search(channel_name, LocalDate.now(ZoneOffset.UTC).minusDays(5),
                LocalDate.now(ZoneOffset.UTC).plusDays(1), i -> i.getMessage().contains(message_key));
        for (Message m : msgSearch) {
            System.out.println(m.toString());
        }

        System.out.println("***************************************************************");
        System.out.println("Search messages send by \"" + sender_key +"\" in the history of channel \"" + channel_name + "\"");
        List<Message> sndSearch = client.chat().search(channel_name, LocalDate.now(ZoneOffset.UTC).minusDays(5),
                LocalDate.now(ZoneOffset.UTC).plusDays(1), i -> i.getSender().contains(sender_key));
        for (Message m : sndSearch) {
            System.out.println(m.toString());
        }
    }
}
