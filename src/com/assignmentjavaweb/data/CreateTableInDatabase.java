package com.assignmentjavaweb.data;

import com.assignmentjavaweb.entitys.Phone;
import com.assignmentjavaweb.security.Security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateTableInDatabase {
    public static void main(String[] args) {
        String createdtable = "CREATED TABLE SUCCESS !";
        Security security = new Security();

        DataConnectionHelper data = new DataConnectionHelper();
        List<Object> list = new ArrayList<>();
        list.add(new Phone());
        data.createTable(list, data.getConnecttion());

        // Ghi hoạt động vào log.txt
        System.out.println(new Date() + " - LOG : " + createdtable);
        security.writeLog(createdtable);
    }
}
