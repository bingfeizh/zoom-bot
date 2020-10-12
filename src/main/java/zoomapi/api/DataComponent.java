package zoomapi.api;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import zoomapi.baseUnit.Channel;
import zoomapi.baseUnit.Member;
import zoomapi.baseUnit.Message;
import zoomapi.clients.OAuthClient;
import zoomapi.tablehandler.ChannelTableHandler;
import zoomapi.tablehandler.MemberTableHandler;
import zoomapi.tablehandler.MessageTableHandler;
import zoomapi.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DataComponent {

    ChannelTableHandler channelTableHandler;
    MemberTableHandler memberTableHandler;
    MessageTableHandler messageTableHandler;

    OAuthClient client;

    HashMap<String, String> path;
    HashMap<String, String> query;
    HashMap<String, String> data;

    public DataComponent(OAuthClient client) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        this.channelTableHandler = new ChannelTableHandler(Channel.class);
        this.memberTableHandler = new MemberTableHandler(Member.class);
        this.messageTableHandler = new MessageTableHandler(Message.class);
        this.client = client;
    }

    public List<Channel> getChannels() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InterruptedException, SQLException, InstantiationException {
        if (channelTableHandler.getChannels(client.getApiKey()).size() == 0 || stale("channel", null)) {
            System.out.println("Data out outdated, update data...");
            update("channel", client.getApiKey());
        }
        List<Channel> channels = channelTableHandler.getChannels(client.getApiKey());
        return channels;
    }

    public Channel getChannel(String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InterruptedException, SQLException, InstantiationException {
        if (channelTableHandler.getChannels(client.getApiKey()).size() == 0 || stale("channel", null)) {
            System.out.println("Data out outdated, update data...");
            update("channel", client.getApiKey());
        }
        List<Channel> channels = channelTableHandler.getChannels(client.getApiKey());
        for (Channel c : channels) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public List<Member> getMembers(String cid) throws InterruptedException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException, IllegalAccessException {
        if (memberTableHandler.getMembers(cid).size() == 0 || stale("member", cid)) {
            System.out.println("Data out outdated, update data...");
            update("member", cid);
        }
        List<Member> members = memberTableHandler.getMembers(cid);
        return members;
    }

    public Member getMember(String cid, String id) throws InterruptedException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException, IllegalAccessException {
        if (memberTableHandler.getMembers(cid).size() == 0 || stale("member", cid)) {
            System.out.println("Data out outdated, update data...");
            update("member", cid);
        }
        List<Member> members = memberTableHandler.getMembers(cid);
        for (Member m : members) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    public List<Message> getMessages(String cid) throws InterruptedException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException, IllegalAccessException {
        if (messageTableHandler.getMessages(cid).size() == 0 || stale("message", cid)) {
            System.out.println("Data out outdated, update data...");
            update("message", cid);
        }
        List<Message> messages = messageTableHandler.getMessages(cid);
        return messages;
    }

    public Message getMessage(String cid, String id) throws InterruptedException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException, IllegalAccessException {
        if (messageTableHandler.getMessages(cid).size() == 0 || stale("message", cid)) {
            System.out.println("Data out outdated, update data...");
            update("message", cid);
        }
        List<Message> messages = messageTableHandler.getMessages(cid);
        for (Message m : messages) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    private boolean stale(String table, String cid) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (table.equals("channel")) {
            Long timestamp = Long.parseLong(channelTableHandler.getChannels(client.getApiKey()).get(0).getTimestamp());
            return new Date().getTime() - timestamp > 900000;
        }
        else if (table.equals("member")) {
            Long timestamp = Long.parseLong(memberTableHandler.getMembers(cid).get(0).getTimestamp());
            return new Date().getTime() - timestamp > 900000;
        }
        else if (table.equals("message")) {
            Long timestamp = Long.parseLong(messageTableHandler.getMessages(cid).get(0).getTimestamp());
            return new Date().getTime() - timestamp > 900000;
        }
        else {
            return true;
        }
    }

    private void update(String table, String cid) throws InterruptedException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException, IllegalAccessException {
        if (table.equals("channel")) {
            path = new HashMap<>();
            query = new HashMap<>();
            data = new HashMap<>();

            JSONArray channelArr = client.chatChannels().list(path,query).getBody().getObject().getJSONArray("channels");
            List<Channel> channelList = new ArrayList<>();
            for (int i = 0; i < channelArr.length(); i++) {
                JSONObject s = channelArr.getJSONObject(i);
                Channel channel = util.channelParser(s);
                channelList.add(channel);
            }

            channelTableHandler.updateChannels(channelList, client.getApiKey());
        }
        else if (table.equals("member")) {
            path = new HashMap<>();
            query = new HashMap<>();
            data = new HashMap<>();

            path.put("channel_id", cid);
            query.put("page_size", "100");

            JSONArray memberArr = client.chatChannels().list_member(path,query,data).getBody().getObject().getJSONArray("members");
            List<Member> memberList = new ArrayList<>();
            for (int i = 0; i < memberArr.length(); i++) {
                JSONObject s = memberArr.getJSONObject(i);
                Member member = util.memberParser(s);
                memberList.add(member);
            }

            memberTableHandler.updateMembers(memberList, cid);
        }
        else if (table.equals("message")) {
            path = new HashMap<>();
            query = new HashMap<>();
            data = new HashMap<>();

            query.put("to_channel", cid);
            query.put("page_size", "50");
            path.put("user_id", "me");

            JSONArray messageArr = client.chatMessages().list(path,query).getBody().getObject().getJSONArray("messages");

            List<Message> messagesList = new ArrayList<>();
            for (int i = 0; i < messageArr.length(); i++) {
                JSONObject s = messageArr.getJSONObject(i);
                Message message = util.messageParser(s);
                messagesList.add(message);
            }
            messageTableHandler.updateMessages(messagesList, cid);
        }
    }

}
