package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/payment"})
public class Payment extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Payment.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
//        gatewayCheck(order,resp);


        WebContext context = new WebContext(req, resp, req.getServletContext());
        ProductService productService = null;
        try {
            productService = ProductService.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        context.setVariable("user", session.getAttribute("user"));

        context.setVariable("categories", productService.getAllProductCategory());
        context.setVariable("suppliers", productService.getAllSupplier());
        if (req.getParameter("orderId") != null) {
            context.setVariable("orderItems", order.getOrderItems());
            RequestDispatcher dispatcher = getServletContext()
                    .getRequestDispatcher("/order_confirmation");
            logger.info("Successful order payment with orderId: {}, UserId: {}", order.getId(), order.getUserId());
            dispatcher.forward(req, resp);
        } else {

            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            engine.process("product/payment.html", context, resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        gatewayCheck(order, resp);


        WebContext context = new WebContext(req, resp, req.getServletContext());
        ProductService productService = null;
        try {
            productService = ProductService.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        context.setVariable("user", session.getAttribute("user"));

        context.setVariable("categories", productService.getAllProductCategory());
        context.setVariable("suppliers", productService.getAllSupplier());
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        engine.process("product/payment.html", context, resp.getWriter());

    }

    private void gatewayCheck(Order order, HttpServletResponse resp) throws IOException {
        if (order == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.sendRedirect("/shop");
        } else if (!order.isChecked()) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.sendRedirect("/checkout");
        }
    }
}
