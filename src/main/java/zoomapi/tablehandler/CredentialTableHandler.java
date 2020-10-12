package zoomapi.tablehandler;

import zoomapi.baseUnit.Token;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CredentialTableHandler extends GenericTableHandler{
    public CredentialTableHandler(Class<Token> className) throws SQLException {
        super(className);
        init();
    }

    public Token getToken(String id) throws InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException, IllegalAccessException {
//        List<String> condition = Arrays.asList("uid");
//        List<String> value = Arrays.asList(id);
        if (super.get(id).size() != 0) {
            return (Token) super.get(id).get(0);
        }
        else {
            return null;
        }
    }

    public void updateToken(Token token) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        super.delete(token.getUid());
        super.insert(token);
    }
}
