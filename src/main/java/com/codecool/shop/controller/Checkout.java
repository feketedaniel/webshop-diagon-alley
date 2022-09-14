package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/checkout"})
public class Checkout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        ProductService productService = ProductService.init();
        context.setVariable("categories", productService.getAllProductCategory());
        context.setVariable("suppliers", productService.getAllSupplier());
        if(order==null){
            resp.sendRedirect("/shop");
        } else if (order.getOrderItems().size()<=0) {
            resp.sendRedirect("/shop");
        }
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        engine.process("product/checkout.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        if(order==null){
            resp.sendRedirect("/shop");
        } else if (order.getOrderItems().size()<=0) {
            resp.sendRedirect("/shop");
        }
        //TODO:check inputs, update session order
        resp.sendRedirect("/payment");
    }

    private void initRoute(HttpServletRequest req, HttpServletResponse resp) {

    }
}
