package com.codecool.shop.controller;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.OrderItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = {"/api/sessionSync"})
public class SessionSync extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Order serverOrder = (Order) session.getAttribute("order");
        if (serverOrder == null) {
            serverOrder = new Order();
            session.setAttribute("order", serverOrder);
        }
        serializeOrder(resp, gson, serverOrder);
    }

    private void serializeOrder(HttpServletResponse resp, Gson gson, Order serverOrder) throws IOException {
        OrderJson clientOrder = new OrderJson(serverOrder.getOrderItems());
        String jsonString = gson.toJson(clientOrder);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }

    private static class OrderJson implements Serializable {
        Map<Integer, OrderItem> orderItems;

        OrderJson(Map<Integer, OrderItem> orderItems) {
            this.orderItems = orderItems;
        }

        public Map<Integer, OrderItem> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(Map<Integer, OrderItem> orderItems) {
            this.orderItems = orderItems;
        }


        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            orderItems.forEach((productId, orderItem) ->{
                sb.append("prodId: ").append(productId).append(", prodName: "+orderItem.getName()).append(", amount: ").append(orderItem.getAmount()).append("\n");
            });
            return sb.toString();
        }
    }
}
