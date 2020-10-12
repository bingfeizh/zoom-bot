package zoomapi.tablehandler;

import zoomapi.baseUnit.Channel;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ChannelTableHandler extends GenericTableHandler{
    public ChannelTableHandler(Class<Channel> className) throws SQLException {
        super(className);
        init();
    }

    public List<Channel> getChannels(String uid) throws InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException, IllegalAccessException {
//        List<String> condition = Arrays.asList("uid");
//        List<String> value = Arrays.asList(uid);
        return (List<Channel>) get(uid);
    }

    public void updateChannels(List<Channel> list, String uid) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        List<Channel> channels_db = getChannels(uid);
        for (Channel c : channels_db) {
            super.delete(c.getUid());
        }
        for (Channel c : list) {
            c.setTimestamp(Long.toString(new Date().getTime()));
            c.setUid(uid);
            insert(c);
        }
    }

    private boolean find(Channel c, List<Channel> list) {
        for (Channel ch : list) {
            if (c.equals(ch))
                return true;
        }
        return false;
    }
}

