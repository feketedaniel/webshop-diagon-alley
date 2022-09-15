package com.codecool.shop.controller;

import com.codecool.shop.controller.util.Serializer;
import com.codecool.shop.model.base.Order;
import com.codecool.shop.model.base.Product;
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
        int prodId = Integer.parseInt(req.getParameter("productId"));
        Product product = productService.getProductById(prodId);
        order.addProduct(product, order);
        session.setAttribute("order", order);
        Serializer.serializeOrder(resp, order);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        } else {
            int prodId = Integer.parseInt(req.getParameter("productId"));
            Product product = productService.getProductById(prodId);
            order.subProduct(product, order);
            session.setAttribute("order", order);
            Serializer.serializeOrder(resp, order);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        } else {
            int prodId = Integer.parseInt(req.getParameter("productId"));
            order.removeOrderItem(prodId, order);
            session.setAttribute("order", order);
            Serializer.serializeOrder(resp, order);
        }
    }
}
