package zoomapi.api;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import zoomapi.baseUnit.Message;
import zoomapi.components.BaseComponent;
import zoomapi.components.ChatChannelsComponent;
import zoomapi.components.ChatMessagesComponent;
import zoomapi.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class ChatComponent extends BaseComponent {

    public void send(String channel, String message) throws InterruptedException {
        ChatChannelsComponent chatChannelsComponent = new ChatChannelsComponent();
        chatChannelsComponent.init(this.getBase_uri(), this.config);
        ChatMessagesComponent chatMessagesComponent = new ChatMessagesComponent();
        chatMessagesComponent.init(this.getBase_uri(), this.config);

        HashMap<String, String> path = new HashMap<>();
        HashMap<String, String> queries = new HashMap<>();
        HashMap<String, String> data = new HashMap<>();

        JSONArray channelArr = chatChannelsComponent.list(path,queries).getBody().getObject().getJSONArray("channels");
        String cid = "";
        for (int i = 0; i < channelArr.length(); i++) {
            String name = channelArr.getJSONObject(i)
                    .get("name").toString();
            if (name.equals(channel)) {
                cid = channelArr.getJSONObject(i)
                        .get("id")
                        .toString();
                path.put("channel_id", cid);
            }
        }

        path.clear();
        queries.clear();

        data.put("message", message);
        data.put("to_channel", cid);
        chatMessagesComponent.post(path, queries,data);

    }

    public List<Message> history(String channel, LocalDate start, LocalDate end) throws InterruptedException {
        ChatChannelsComponent chatChannelsComponent = new ChatChannelsComponent();
        chatChannelsComponent.init(this.getBase_uri(), this.config);
        ChatMessagesComponent chatMessagesComponent = new ChatMessagesComponent();
        chatMessagesComponent.init(this.getBase_uri(), this.config);
        //System.out.println(this.config.get("token"));

        HashMap<String, String> path = new HashMap<>();
        HashMap<String, String> queries = new HashMap<>();

        JSONArray channelArr = chatChannelsComponent.list(path,queries).getBody().getObject().getJSONArray("channels");
        String cid = "";
        for (int i = 0; i < channelArr.length(); i++) {
            String name = channelArr.getJSONObject(i)
                    .get("name").toString();
            if (name.equals(channel)) {
                cid = channelArr.getJSONObject(i)
                        .get("id")
                        .toString();
                //path.put("channel_id", cid);
            }
        }

        path.clear();
        queries.clear();
        path.put("user_id", "me");
        queries.put("to_channel", cid);
        queries.put("page_size", "50");
        List<Message> msgs = new ArrayList<Message>();

        for (LocalDate d = start; d.isBefore(end.plusDays(1)); d = d.plusDays(1)) {
            String token = "token";
            queries.put("next_page_token", "");
            queries.put("date", d.toString());
            while (token.length() != 0) {
                JSONObject response = chatMessagesComponent.list(path, queries).getBody().getObject();
                JSONArray messageArr = response.getJSONArray("messages");
                for (int i = 0; i < messageArr.length(); i++) {
                    JSONObject s = messageArr.getJSONObject(i);
                    Message msg = util.messageParser(s);
                    msgs.add(msg);

                }
                //System.out.println(msgs.size());
                token = response.getString("next_page_token");
                queries.put("next_page_token", token);
                //System.out.println(token);
            }
        }
        return msgs;
    }

    public List<Message> search(String channel, LocalDate start, LocalDate end, Predicate<Message> p){
        List<Message> ret = new ArrayList<>();
        try{
            List<Message> msg = history(channel, start, end);
            for(Message curMsg : msg){
                if(p.test(curMsg)){
                    ret.add(curMsg);
                }
            }
        }catch (InterruptedException e){
            System.out.println(e);
        }
        return ret;
    }
}
