package zoomapi.tablehandler;

import zoomapi.baseUnit.Channel;
import zoomapi.baseUnit.Message;
import zoomapi.baseUnit.Token;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MessageTableHandler extends GenericTableHandler{
    public MessageTableHandler(Class<Message> className) throws SQLException {
        super(className);
        init();
    }

    public List<Message> getMessages(String cid) throws InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException, IllegalAccessException {
        return (List<Message>) get(cid);
    }

    public void updateMessages(List<Message> list, String cid) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        List<Message> messages_db = getMessages(cid);
        for (Message c : messages_db) {
            super.delete(c.getUid());
        }
        for (Message c : list) {
            c.setTimestamp(Long.toString(new Date().getTime()));
            c.setUid(cid);
            insert(c);
        }
    }

    private boolean find(Message c, List<Message> list) {
        for (Message ch : list) {
            if (c.equals(ch))
                return true;
        }
        return false;
    }
}