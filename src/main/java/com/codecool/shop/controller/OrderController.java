package com.codecool.shop.controller;

import com.codecool.shop.controller.util.Serializer;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/api/cart/*"})
public class OrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = null;
        try {
            productService = ProductService.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            order = new Order();
        }
        int prodId = Integer.parseInt(req.getParameter("productId"));
        Product product = productService.getProductById(prodId);
        order.addOrderItem(product);
        session.setAttribute("order", order);
        Serializer.serializeOrder(resp, order);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = null;
        try {
            productService = ProductService.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        } else {
            int prodId = Integer.parseInt(req.getParameter("productId"));
            Product product = productService.getProductById(prodId);
            order.subOrderItem(product);
            session.setAttribute("order", order);
            Serializer.serializeOrder(resp, order);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = null;
        try {
            productService = ProductService.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        } else {
            Product product = productService.getProductById(Integer.parseInt(req.getParameter("productId")));
            order.removeOrderItem(product);
            session.setAttribute("order", order);
            Serializer.serializeOrder(resp, order);
        }
    }
}
