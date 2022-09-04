package com.codecool.shop.controller;

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

@WebServlet(urlPatterns = {"/api/cart/*"})
public class OrderController extends HttpServlet {
    ProductService productService = ProductService.init();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            order = new Order();
        }
        Product product = productService.getProductById(Integer.parseInt(req.getParameter("productId")));
        order.addProduct(product);
        session.setAttribute("order", order);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        } else {
            Product product = productService.getProductById(Integer.parseInt(req.getParameter("productId")));
            order.subProduct(product);
            session.setAttribute("order", order);
        }
    }
}
