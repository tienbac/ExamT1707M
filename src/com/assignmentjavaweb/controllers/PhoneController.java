package com.assignmentjavaweb.controllers;

import com.assignmentjavaweb.entitys.Phone;
import com.assignmentjavaweb.model.PhoneModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PhoneController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/addphone.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String brand = req.getParameter("brand");
        int price = Integer.parseInt(req.getParameter("price"));
        String description = req.getParameter("description");

        Phone phone = new Phone(name, brand, price, description);
        if (!phone.isValid()){
            HashMap<String, ArrayList<String>> errors = phone.getErrors();
            req.setAttribute("phone",phone);
            req.setAttribute("errors",errors);
            req.getRequestDispatcher("addphone.jsp").forward(req,resp);
        }

        PhoneModel phoneModel = new PhoneModel();
        phoneModel.addPhone(phone.getName(), phone.getBrand(), phone.getPrice(), phone.getDescription());
        resp.sendRedirect("/listphone.jsp");
    }
}
