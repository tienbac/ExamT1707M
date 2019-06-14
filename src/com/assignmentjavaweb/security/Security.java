package com.assignmentjavaweb.security;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Security {
    public void writeLog(String string){
        String pathname = "log.txt";
        File file = new File(pathname);
        String status = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        try {
            if (file.createNewFile()){
                status = dateFormat.format(new Date()) + " - LOG : File " + pathname + " was created!";
            }else {
                status = dateFormat.format(new Date()) + " - LOG : File " + pathname + " has exists ! Ready to write new log !";
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(status);
            writer.newLine();
            writer.append(dateFormat.format(new Date()) + " - LOG : " + string);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
