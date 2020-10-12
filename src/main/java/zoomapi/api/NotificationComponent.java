package zoomapi.api;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import zoomapi.EventHandler.UpdateListener;
import zoomapi.baseUnit.Member;
import zoomapi.baseUnit.Message;
import zoomapi.clients.OAuthClient;
import zoomapi.components.BaseComponent;
import zoomapi.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class NotificationComponent extends BaseComponent {

    static private NotificationComponent notificationComponentInstance;

    OAuthClient client;

    private NotificationComponent(OAuthClient client){
        this.client = client;
    }

    public synchronized static NotificationComponent getInstance(OAuthClient client){
        if(notificationComponentInstance == null){
            notificationComponentInstance = new NotificationComponent(client);
        }
        return notificationComponentInstance;
    }


    private class fetchingThread extends Thread implements Runnable{

        private UpdateListener updateListener;

        HashMap<String, HashMap<String, Message>> messageMap; //All messages in a channel <channel id, message>
        HashMap<String, HashMap<String, Member>> memberMap; //Members in all users channels <channel id, <member id, member>>
        HashMap<String, String> channels; //All channels a user in <channel id, channel name>
        HashSet<String> channelName; //Specific channel names
        List<Message> newMessage; //A list of new messages get in one loop
        List<Message> updatedMessage; //A list of updated messages get in one loop
        List<Member> newMember; //A list of new members in a channel get in one loop

        public fetchingThread(HashSet<String> channels, UpdateListener updateListener) {
            this.channels = new HashMap<>();
            this.channelName = channels;
            this.messageMap = new HashMap<>();
            this.memberMap = new HashMap<>();
            this.newMessage = new ArrayList<>();
            this.updatedMessage = new ArrayList<>();
            this.newMember = new ArrayList<>();
            this.updateListener = updateListener;
        }

        public void run() {

            HashMap<String, String> path = new HashMap<>();
            HashMap<String, String> queries = new HashMap<>();
            HashMap<String, String> data = new HashMap<>();

            //Get all users channel Ids and a specific channel id
            JSONArray channelArr = null;
            try {
                channelArr =client.chatChannels().list(path,queries).getBody().getObject().getJSONArray("channels");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<String> cids = new ArrayList<>();
            for (int i = 0; i < channelArr.length(); i++) {
                String name = channelArr.getJSONObject(i).get("name").toString();
                String id = channelArr.getJSONObject(i).get("id").toString();

                if (channelName.contains(name)) {
                    cids.add(id);
                }
                channels.put(id, name);
            }

            //Initialize message map
            for (String cid : cids) {
                HashMap<String, Message> msgMap = new HashMap<>();
                path.put("user_id", "me");
                queries.put("to_channel", cid);
                queries.put("page_size", "50");

                String token = "token";
                queries.put("next_page_token", "");
                while (token.length() != 0) {
                    JSONObject response = null;
                    try {
                        response = client.chatMessages().list(path, queries).getBody().getObject();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    JSONArray messageArr = response.getJSONArray("messages");
                    //System.out.println(messageArr);
                    for (int i = 0; i < messageArr.length(); i++) {
                        JSONObject s = messageArr.getJSONObject(i);
                        Message msg = util.messageParser(s);
                        msgMap.put(msg.getId(), msg);
                    }
                    token = response.getString("next_page_token");
                    queries.put("next_page_token", token);
                }

                messageMap.put(cid, msgMap);

                path.clear();
                queries.clear();
            }

            //Initialize member list
            for (String id : channels.keySet()) {
                HashMap<String, Member> memMap = new HashMap<>();
                path.put("channel_id", id);
                queries.put("page_size", "50");

                String token = "token";
                queries.put("next_page_token", "");
                while (token.length() != 0) {
                    JSONObject response = null;
                    try {
                        response = client.chatChannels().list_member(path, queries, data).getBody().getObject();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    JSONArray memberArr = response.getJSONArray("members");
                    for (int i = 0; i < memberArr.length(); i++) {
                        JSONObject s = memberArr.getJSONObject(i);
                        Member member = util.memberParser(s);
                        memMap.put(member.getId(), member);
                    }
                    token = response.getString("next_page_token");
                    queries.put("next_page_token", token);
                }
                memberMap.put(id, memMap);
            }

            System.out.println("Initialization done!");


            while(true) {
                //Check new messages
                for (String cid : cids) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    List<Message> messagesList = chatHistory(cid);

                    for (Message msg : messagesList) {
                        String msgId = msg.getId();
                        //Check new message
                        if (!messageMap.get(cid).containsKey(msgId)) {
                            messageMap.get(cid).put(msgId, msg);
                            newMessage.add(msg);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if (updateListener != null)
                                        updateListener.onNewMessage(msg);
                                }
                            }).start();
                        }
                        //Check updated message
                        else if (!messageMap.get(cid).get(msgId).getMessage().equals(msg.getMessage())) {
                            updatedMessage.add(msg);
                            messageMap.get(cid).put(msgId, msg);


                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if (updateListener != null)
                                        updateListener.onUpdatedMessage(msgId, msg);
                                }
                            }).start();
                        }
                    }
                    //Print notification
//                    for (Message m : newMessage) {
//                        System.out.println(m + "*");
//                    }
//                    for (Message m : updatedMessage) {
//                        System.out.println(m + "#");
//                    }

                    newMessage = new ArrayList<>();
                    updatedMessage = new ArrayList<>();
                }

                //Check new members
                for (String id : channels.keySet()) {
                    //Check new members of a specific channel
                    List<Member> memberList = channelMembers(id);
                    newMember = new ArrayList<>();
                    //Check new members
                    for (Member m : memberList) {
                        String mId = m.getId();
                        if (!memberMap.get(id).containsKey(mId)) {
                            memberMap.get(id).put(mId, m);
                            newMember.add(m);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if(updateListener != null)
                                        updateListener.onNewMember(id, m);
                                }
                            }).start();
                        }
                    }
                    //Print notification
//                    for (Member m : newMember)
//                        System.out.println(channels.get(id) + " " + m.getId() + " " + m.getEmail());
                }
            }
        }

        //Get today's chat history for a specific channel
        private List<Message> chatHistory(String channelId) {
            HashMap<String, String> path = new HashMap<>();
            HashMap<String, String> queries = new HashMap<>();
            HashMap<String, String> data = new HashMap<>();

            path.put("user_id", "me");
            queries.put("page_size", "50");
            queries.put("to_channel", channelId);

            List<Message> messageList = new ArrayList<>();

            JSONObject response = null;
            try {
                response = client.chatMessages().list(path, queries).getBody().getObject();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            JSONArray messageArr = response.getJSONArray("messages");
            for (int i = 0; i < messageArr.length(); i++) {
                JSONObject s = messageArr.getJSONObject(i);
                Message msg = util.messageParser(s);
                messageList.add(msg);
            }
            return messageList;
        }

        //Get all members of a specific channel
        private List<Member> channelMembers(String channelId) {

            HashMap<String, String> path = new HashMap<>();
            HashMap<String, String> queries = new HashMap<>();
            HashMap<String, String> data = new HashMap<>();

            List<Member> memberList = new ArrayList<>();

            path.put("channel_id", channelId);

            String token = "token";
            queries.put("next_page_token", "");
            while (token.length() != 0) {
                JSONObject response = null;
                try {
                    response = client.chatChannels().list_member(path, queries, data).getBody().getObject();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JSONArray memberArr = response.getJSONArray("members");
                for (int i = 0; i < memberArr.length(); i++) {
                    JSONObject s = memberArr.getJSONObject(i);
                    Member member = util.memberParser(s);
                    memberList.add(member);
                }
                token = response.getString("next_page_token");
                queries.put("next_page_token", token);
            }
            return memberList;
        }
    }

    public void register(HashSet<String> channels, UpdateListener updateListener) {
        Thread t = new fetchingThread(channels, updateListener);
        t.run();
    }
}
