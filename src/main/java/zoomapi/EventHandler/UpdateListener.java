package zoomapi.EventHandler;

import zoomapi.baseUnit.Member;
import zoomapi.baseUnit.Message;

public interface UpdateListener {

    public void onNewMessage(Message data);

    public void onUpdatedMessage(String msgId, Message newMsg);

    public void onNewMember(String channelId, Member newMember);

}
