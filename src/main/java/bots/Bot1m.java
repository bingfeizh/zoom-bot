package bots;

import kong.unirest.json.JSONArray;
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

    public class Bot1m {

        static HashMap<String, String> path;
        static HashMap<String, String> query;
        static HashMap<String, String> data;

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

            String contact = "";

            OAuthClient client = new OAuthClient();
            client.init(id, secret, port, redirectUrl, browserPath);

            System.out.println("----------- Start test -----------");
            System.out.println("List the channels");
            JSONArray channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
            List<String> channels = channelArr.toList();
            System.out.println(channels);
            System.out.println("***************************************************************");

//
//            System.out.println("Create a channel called \"NewChannelJava\" ");
//            data.put("name", "NewChannelJava");
//            data.put("type", "1");
//            client.chatChannels().create(path, query, data);
//            channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
//            channels = channelArr.toList();
//            System.out.println(channels);
//            data.clear();
//            System.out.println("***************************************************************");
//
//
//            System.out.println("Get a channel");
//            channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
//            String cid;
//            for (int i = 0; i < channelArr.length(); i++) {
//                String name = channelArr.getJSONObject(i)
//                        .get("name").toString();
//                if (name.equals("NewChannelJava")) {
//                    cid = channelArr.getJSONObject(i)
//                            .get("id")
//                            .toString();
//                    path.put("channel_id", cid);
//                }
//            }
//            System.out.println(client.chatChannels().get(path,query,data).getBody().toString());
//            path.clear();
//            System.out.println("***************************************************************");
//
//            System.out.println("Update a channel");
//            channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
//
//            for (int i = 0; i < channelArr.length(); i++) {
//                String name = channelArr.getJSONObject(i)
//                        .get("name").toString();
//                if (name.equals("NewChannelJava")) {
//                    cid = channelArr.getJSONObject(i)
//                            .get("id")
//                            .toString();
//                    path.put("channel_id", cid);
//                    data.put("name", "UpdatedChannelJava");
//                }
//            }
//            client.chatChannels().update(path,query,data);
//            System.out.println("Channel updated, name is now UpdatedChannelJava!");
//            channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
//            channels = channelArr.toList();
//            System.out.println(channels);
//            path.clear();
//            data.clear();
//            System.out.println("***************************************************************");
//
//            System.out.println("List Channel Members");
//            channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
//
//            for (int i = 0; i < channelArr.length(); i++) {
//                String name = channelArr.getJSONObject(i)
//                        .get("name").toString();
//                if (name.equals("UpdatedChannelJava")) {
//                    cid = channelArr.getJSONObject(i)
//                            .get("id")
//                            .toString();
//                    path.put("channel_id", cid);
//                }
//            }
//            String members = client.chatChannels().list_member(path,query,data).getBody().getObject().getJSONArray("members").toString();
//            System.out.println(members);
//            System.out.println("***************************************************************");
//
//            System.out.println("Invite a member");
//            data.put("members", contact);
//            client.chatChannels().invite(path, query, data);
//            System.out.println("Members after invite");
//            members = client.chatChannels().list_member(path,query,data).getBody().getObject().getJSONArray("members").toString();
//            System.out.println(members);
//            data.clear();
//
//            System.out.println("***************************************************************");
//
//            System.out.println("Remove a member");
//            JSONArray memberArr = client.chatChannels().list_member(path,query,data).getBody().getObject().getJSONArray("members");
//            for (int i = 0; i < memberArr.length(); i++) {
//                String name = memberArr.getJSONObject(i)
//                        .get("name").toString();
//                if (name.equals("Bingfei Zhang")) {
//                    String mid = memberArr.getJSONObject(i)
//                            .get("id")
//                            .toString();
//                    path.put("member_id", mid);
//                }
//            }
//            client.chatChannels().remove(path, query, data);
//            System.out.println("Members after remove");
//            members = client.chatChannels().list_member(path,query,data).getBody().getObject().getJSONArray("members").toString();
//            System.out.println(members);
//
//            System.out.println("***************************************************************");
//
//            System.out.println("Delete a channel");
//            channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
//
//            for (int i = 0; i < channelArr.length(); i++) {
//                String name = channelArr.getJSONObject(i)
//                        .get("name").toString();
//                if (name.equals("UpdatedChannelJava")) {
//                    cid = channelArr.getJSONObject(i)
//                            .get("id")
//                            .toString();
//                    path.put("channel_id", cid);
//                    data.put("name", "UpdatedChannelJava");
//                }
//            }
//            client.chatChannels().delete(path,query,data);
//            channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
//            channels = channelArr.toList();
//            System.out.println("Channels after deleting: " + channels);
//            path.clear();
//
//            System.out.println("***************************************************************");
//
//            System.out.println("Leave a channel");
//            channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
//            channels = channelArr.toList();
//            System.out.println("Channels before creating: " + channels);
//
//            System.out.println("Create a channel first");
//            data.put("name", "CreatedChannelForLeavingJava");
//            data.put("type", "1");
//            client.chatChannels().create(path,query,data);
//            channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
//            channels = channelArr.toList();
//            data.clear();
//            System.out.println("Channels after creating: " + channels);
//
//            System.out.println("Then invite a member to it.");
//            channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
//
//            for (int i = 0; i < channelArr.length(); i++) {
//                String name = channelArr.getJSONObject(i)
//                        .get("name").toString();
//                if (name.equals("CreatedChannelForLeavingJava")) {
//                    cid = channelArr.getJSONObject(i)
//                            .get("id")
//                            .toString();
//                    path.put("channel_id", cid);
//                }
//            }
//            data.put("members", contact);
//            client.chatChannels().invite(path, query, data);
//            System.out.println("Members after invite");
//            members = client.chatChannels().list_member(path,query,data).getBody().getObject().getJSONArray("members").toString();
//            System.out.println(members);
//            data.clear();
//
//            System.out.println("Then leave it.");
//
//            client.chatChannels().leave(path,query,data);
//            System.out.println("left channel");
//            channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
//            channels = channelArr.toList();
//            System.out.println("Channels after leaving: " + channels);
//
//            //Path is not cleared here for easy rejoin.
//            data.clear();
//
//            System.out.println("***************************************************************");
//
//            System.out.println("Join the previously left channel");
//            client.chatChannels().join(path,query,data);
//            channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
//            channels = channelArr.toList();
//            System.out.println("Channels after joining: " + channels);
//            path.clear();

            System.out.println("***************************************************************");

            System.out.println("------------- Chat message Test --------------");

            System.out.println("Send a message");
            data.put("message", "Hello!");
            data.put("to_contact", contact);
            client.chatMessages().post(path, query, data);

            path.clear();
            data.clear();
            System.out.println("***************************************************************");

            System.out.println("List messages");
            path.put("user_id", "me");
            query.put("to_contact", contact);
            JSONArray msgArr = client.chatMessages().list(path,query).getBody().getObject().getJSONArray("messages");
            List<String> msgs = msgArr.toList();
            System.out.println(msgs);

            path.clear();
            query.clear();

            System.out.println("***************************************************************");

            System.out.println("Update a message");
            path.put("user_id", "me");
            query.put("to_contact", contact);
            msgArr = client.chatMessages().list(path,query).getBody().getObject().getJSONArray("messages");
            String mid;

            for (int i = 0; i < msgArr.length(); i++) {
                String message = msgArr.getJSONObject(i)
                        .get("message").toString();
                if (message.equals("Hello!")) {
                    mid = msgArr.getJSONObject(i)
                            .get("id")
                            .toString();
                    path.put("message_id", mid);
                }
            }
            data.put("message", "Updated message");
            data.put("to_contact", contact);
            client.chatMessages().update(path,query,data);
            msgArr = client.chatMessages().list(path,query).getBody().getObject().getJSONArray("messages");
            msgs = msgArr.toList();
            System.out.println(msgs);
            data.clear();

            System.out.println("***************************************************************");

            System.out.println("Delete a message");
            query.put("to_contact", contact);
            client.chatMessages().delete(path,query,data);
            msgArr = client.chatMessages().list(path,query).getBody().getObject().getJSONArray("messages");
            msgs = msgArr.toList();
            System.out.println(msgs);


            System.out.println("***************************************************************");

        }
    }

