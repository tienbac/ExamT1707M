package com.assignmentjavaweb.model;

import com.assignmentjavaweb.data.DataConnectionHelper;
import com.assignmentjavaweb.entitys.Phone;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class PhoneModel {
    DataConnectionHelper data = new DataConnectionHelper();

    public ArrayList<Phone> getListPhone(){
        ArrayList<Phone> listPhone = new ArrayList<>();
        Connection connection = data.getConnecttion();
        if (connection==null){
            return null;
        }
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM phones";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Phone phone = new Phone();
                phone.setId(rs.getInt("id"));
                phone.setName(rs.getString("name"));
                phone.setBrand(rs.getString("brand"));
                phone.setPrice(rs.getInt("price"));
                phone.setDescription(rs.getString("description"));

                listPhone.add(phone);
            }
        }catch (SQLException e){
            System.out.println(new Date() + " - LOG : Sorry! Can't connect to database. Please try again!");
            e.printStackTrace();
        }
        return listPhone;
    }

    public Phone addPhone(String name, String brand, int price, String description){
        Connection connection = data.getConnecttion();
        if (connection==null){
            return null;
        }
        Phone phone = new Phone();
        phone.setName(name);
        phone.setBrand(brand);
        phone.setPrice(price);
        phone.setDescription(description);

        String sql = "INSERT INTO phones( name, brand, price, description) VALUE (?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, phone.getName());
            ps.setString(2, phone.getBrand());
            ps.setInt(3, phone.getPrice());
            ps.setString(4, phone.getDescription());
            ps.executeUpdate();
            System.out.println(new Date() + " - LOG : Add phone +" + phone.getName() + " success!");
        }catch (SQLException e){
            System.out.println(new Date() + " - LOG : Sorry! Can't connect to database. Please try again!");
            e.printStackTrace();
        }
        return phone;
    }
}
