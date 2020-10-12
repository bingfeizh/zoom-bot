package bots;

import zoomapi.EventHandler.UpdateListener;
import zoomapi.api.NotificationComponent;
import zoomapi.baseUnit.Member;
import zoomapi.baseUnit.Message;
import zoomapi.clients.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class NotificationBot{

    static HashMap<String, String> path;
    static HashMap<String, String> query;
    static HashMap<String, String> data;

    final Set<UpdateListener> listeners = new CopyOnWriteArraySet<>();

    @SuppressWarnings("Cookie rejected")
    public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException, InterruptedException, NoSuchMethodException, SQLException, InvocationTargetException {

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
        NotificationComponent notificationComponent = NotificationComponent.getInstance(client);

        System.out.println("----------- Start test -----------");
        UpdateListener updateListener = new UpdateHandler();
        HashSet<String> channels = new HashSet<>();

        channels.add("test");
//        channels.add("wow");
        notificationComponent.register(channels, updateListener);

    }

    static class UpdateHandler implements UpdateListener{

        @Override
        public void onNewMessage(Message data) {
            System.out.println("New message received: " + data);
        }

        @Override
        public void onUpdatedMessage(String msgId, Message updatedMsg) {
            System.out.println("Message " + msgId + " updated: " + updatedMsg);
        }

        @Override
        public void onNewMember(String channelId, Member newMember) {
            System.out.print("New member " + newMember + " added to channel " + channelId);
        }
    }
}
