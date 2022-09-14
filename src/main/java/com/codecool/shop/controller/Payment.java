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

@WebServlet(urlPatterns = {"/payment"})
public class Payment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        WebContext context = new WebContext(req, resp, req.getServletContext());
        ProductService productService = ProductService.init();
        context.setVariable("categories", productService.getAllProductCategory());
        context.setVariable("suppliers", productService.getAllSupplier());
        if (req.getParameter("orderId") != null) {
//            TODO: redirect to orderconfirmed
            System.out.println("TODO: redirect to orderconfirmed");
            resp.sendRedirect("/shop");
        } else {
//            Order order = (Order) session.getAttribute("order");
//            //now should check for chekout validation in session not just order
//            if (order == null) {
//                resp.sendRedirect("/shop");}
//            } else if (!order.isChecked()) {
//                resp.sendRedirect("/checkout");
//            }
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            engine.process("product/payment.html", context, resp.getWriter());
        }
    }
}
