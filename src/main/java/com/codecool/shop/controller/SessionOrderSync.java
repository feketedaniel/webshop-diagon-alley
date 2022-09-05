package com.codecool.shop.controller;

import com.codecool.shop.controller.util.Serializer;
import com.codecool.shop.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = {"/api/getSessionOrder"})
public class SessionOrderSync extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order serverOrder = (Order) session.getAttribute("order");
        if (serverOrder == null) {
            serverOrder = new Order();
            session.setAttribute("order", serverOrder);
        }
        Serializer.serializeOrder(resp, serverOrder);
    }
}

