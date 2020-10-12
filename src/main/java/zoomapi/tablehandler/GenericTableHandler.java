package zoomapi.tablehandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenericTableHandler<T> {

    String table;
    List<String> fields;
    Connection connection;

    private Class<T> dao;

    public GenericTableHandler(Class<T> className) throws SQLException {
        this.dao = className;
        this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
        this.table = className.getName().substring(17);
        this.fields = new ArrayList<>();
        for (Field f : className.getDeclaredFields()) {
            String f_string = f.toString();
            String field = f_string.substring(f_string.indexOf(table) + table.length() + 1);
            fields.add(field);
            //System.out.println(field);
        }
    }

    public T buildGenericInstance() throws IllegalAccessException, InstantiationException {
        return dao.newInstance();
    }


    /**
     * Create table using currently set fields
     * @throws SQLException
     */
    public void init() throws SQLException {
        String str = "CREATE TABLE IF NOT EXISTS " + table + " (";
        try{
            str += fields.get(0) + " VARCHAR NOT NULL, ";
            for(int i = 1; i < fields.size(); i++){
                str += fields.get(i) + " VARCHAR NOT NULL, ";
            }

            str = str.substring(0, str.length() - 2);
            str += ");";

            //System.out.println(str);

            PreparedStatement ps = connection.prepareStatement(str);

            int status = ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public List<T> get(String uid) throws SQLException,
            InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String str = "SELECT * FROM " + table;
        if(uid.length() != 0){
            str += " WHERE uid = ?";
        }
        str += ";";
        //System.out.println(str);
        PreparedStatement ps = connection.prepareStatement(str);
        //ps.setString(0,table);
        if(uid.length() != 0){
            ps.setString(1,uid);
        }
        ResultSet rs = ps.executeQuery();

        List<T> ret = new ArrayList<>();

        // using reflection
        while(rs.next()){
            T t = buildGenericInstance();
            for(String field : fields){
                String curFieldVal = rs.getString(field);
                String fieldStr = field.substring(0,1).toUpperCase() + field.substring(1);
                Method setter = dao.getMethod("set"+fieldStr, String.class);
                setter.invoke(t,curFieldVal);
            }
            ret.add(t);
        }

        return ret;
    }

    public void insert(T obj) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Statement statement = connection.createStatement();
        String str = ("INSERT INTO " + table + " (");
        List<String> valsToInsert = new ArrayList<>();
        for(String field : fields){
            str += field + ", ";
            //System.out.println(field);
            String fieldStr = field.substring(0,1).toUpperCase()+field.substring(1);
            Method getter = dao.getDeclaredMethod("get"+fieldStr);
            String val = (String)getter.invoke(obj);
            valsToInsert.add(val);
        }
        str = str.substring(0, str.length()-2);
        str += ") VALUES (";
        for(String val : valsToInsert){
            str += "\"" + val + "\"" + ", ";
        }
        str = str.substring(0, str.length()-2);
        str += ");";
        //System.out.println(str);
        statement.execute(str);
    }

    public void delete(String id) throws SQLException {
        String str = "DELETE FROM " + table;
        if(id.length() != 0){
            str += " WHERE uid = ?";
        }
        //System.out.println(str);
        PreparedStatement ps = connection.prepareStatement(str);
        //ps.setString(1,table);

        if(id.length() != 0){
            ps.setString(1, id);
        }
        int status = ps.executeUpdate();
    }
}
