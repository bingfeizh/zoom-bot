package zoomapi.tablehandler;

import zoomapi.baseUnit.Channel;
import zoomapi.baseUnit.Member;
import zoomapi.baseUnit.Message;
import zoomapi.baseUnit.Token;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MemberTableHandler extends GenericTableHandler{
    public MemberTableHandler(Class<Member> className) throws SQLException {
        super(className);
        init();
    }

    public List<Member> getMembers(String cid) throws InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException, IllegalAccessException {
//        List<String> condition = Arrays.asList("uid", "cid");
//        List<String> value = Arrays.asList(uid, cid);
        return (List<Member>) get(cid);
    }

    public void updateMembers(List<Member> list, String cid) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        List<Member> members_db = getMembers(cid);
        for (Member c : members_db) {
            super.delete(c.getUid());
        }
        for (Member c : list) {
            c.setTimestamp(Long.toString(new Date().getTime()));
            c.setUid(cid);
            insert(c);
        }
    }

    private boolean find(Member c, List<Member> list) {
        for (Member ch : list) {
            if (c.equals(ch))
                return true;
        }
        return false;
    }
}
