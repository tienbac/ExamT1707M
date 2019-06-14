package com.assignmentjavaweb.data;

import com.assignmentjavaweb.security.Security;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataConnectionHelper {
    static Connection conn;
    Statement stmt;
    Security security = new Security();

    String successToHost = "Connection to host success !";
    String successToDb = "Connection to database success !";
    String failToHost = "Connection to host fail !";
    String failToDb = "Connection to database fail !";

    public Connection getConnecttion(){
        String DATABASE = "examt1707m";
        String HOST_URL = "jdbc:mysql://localhost:3306/";
        String USER = "root";
        String PASSWORD = "";
        security.writeLog(HOST_URL + DATABASE + " / " + USER + " / " + PASSWORD);
        conn = getConnectionToDbNotExists(DATABASE, HOST_URL, USER, PASSWORD);
        return conn;
    }

    public Connection getConnectionToHost(String HOST_URL, String USER, String PASSWORD){
        if (conn == null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(HOST_URL, USER, PASSWORD);
                System.out.println(new Date() + " - LOG : " + successToHost);
                security.writeLog(successToHost);
            } catch (ClassNotFoundException | SQLException e) {
                // class.forName
                e.printStackTrace();
                System.out.println(new Date() + " - LOG : " + failToHost);
                security.writeLog(failToHost);
                conn = null;
            }
        }
        return conn;
    }

    public Connection getConnectionToDbNotExists(String DATABASE, String HOST_URL, String USER, String PASSWORD){
        Connection connection = getConnectionToHost(HOST_URL, USER, PASSWORD);
        if (connection == null){
            connection = getConnecttion();
        }
        try {
            ResultSet rs = connection.getMetaData().getCatalogs();
            List<Boolean> list = new ArrayList<>();
            while (rs.next()){
                String catalogs = rs.getString(1);
                if (DATABASE.equalsIgnoreCase(catalogs)){
                    list.add(true);
                }else {
                    list.add(false);
                }
            }
            if (list.indexOf(true) == -1){
                stmt = connection.createStatement();
                String sqlDb = "CREATE DATABASE " + DATABASE + " CHARACTER SET utf8 COLLATE utf8_general_ci";
                stmt.executeUpdate(sqlDb);
                connection = getConnectionToDb(DATABASE, HOST_URL, USER, PASSWORD, connection);
            }else {
                connection = getConnectionToDb(DATABASE, HOST_URL, USER, PASSWORD, connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnectionToDb(String DATABASE, String HOST_URL, String USER, String PASSWORD,  Connection connection){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(HOST_URL + DATABASE, USER, PASSWORD);
            System.out.println(new Date() + " - LOG : " + successToDb);
            security.writeLog(successToDb);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(new Date() + " - LOG : " + failToDb);
            security.writeLog(failToDb);
            connection = null;
        }
        return connection;
    }


    public String createSql(Object object){
        Class cObject = object.getClass();
        Field[] fields = cObject.getDeclaredFields();
        ArrayList<ObjectSql> lists = new ArrayList<>();
        List<String> list = new ArrayList<>();

        for (Field field: fields
             ) {
            ObjectSql objectSql = new ObjectSql();
            objectSql.setName(field.getName());
            objectSql.setType(field.getType().getSimpleName());
            list.add(field.getName());
            lists.add(objectSql);
        }

        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE " + cObject.getSimpleName() + "s ( ");
        for (int i =0; i < lists.size(); i++){
            ObjectSql objectSql = lists.get(i);
            if (objectSql.getName().equalsIgnoreCase("id")){
                lists.remove(objectSql);
            }
        }

        String sqlId = "id int NOT NULL AUTO_INCREMENT, ";
        builder.append(sqlId);
        for (ObjectSql obj: lists
        ) {
            if (obj.getType().equalsIgnoreCase("String")){
                String sql = obj.getName() + " varchar(200) NOT NULL, ";
                builder.append(sql);
            }else if (obj.getType().equalsIgnoreCase("long")){
                String sql = obj.getName() + " bigint NOT NULL, ";
                builder.append(sql);
            }else {
                String sql = obj.getName() + " " + obj.getType() + " NOT NULL,";
                builder.append(sql);
            }
        }
        String sqlKey = "CONSTRAINT " + cObject.getSimpleName() + "_pk PRIMARY KEY (id)";
        builder.append(sqlKey);

        String sqlOr = builder.toString() + " );";
        return sqlOr;
    }

    public void createTable(List<Object> list, Connection connection){
        if (connection == null){
            connection = getConnecttion();
        }
        try {
            stmt = connection.createStatement();
            for (Object object: list
            ) {
                String sqlTable = createSql(object);
                stmt.addBatch(sqlTable);
            }
//            String sql = "ALTER TABLE User ADD CONSTRAINT User_Feedback FOREIGN KEY User_Feedback (userId)\n" +
//                    "    REFERENCES User (id);";
            int[] status = stmt.executeBatch();
            System.out.println(status[1]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class ObjectSql{
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
