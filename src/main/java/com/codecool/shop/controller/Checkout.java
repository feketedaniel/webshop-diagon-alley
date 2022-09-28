package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.PaymentDetails;
import com.codecool.shop.service.ProductService;
import org.apache.log4j.chainsaw.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/checkout"})
public class Checkout extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        ProductService productService = null;
        try {
            productService = ProductService.init();
        } catch (SQLException e) {
            logger.error("SQLException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        context.setVariable("categories", productService.getAllProductCategory());
        context.setVariable("suppliers", productService.getAllSupplier());
        if (order == null) {
            logger.warn("Manual request sent to /checkout");
            resp.sendRedirect("/shop");
        } else if (order.getOrderItems().size() == 0) {
            logger.warn("Manual request sent to /checkout");
            resp.sendRedirect("/shop");
        }
        context.setVariable("user", session.getAttribute("user"));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        engine.process("product/checkout.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ProductService productService = null;
        try {
            productService = ProductService.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            logger.warn("Manual request sent to /checkout");
            resp.sendRedirect("/shop");
        } else if (order.getOrderItems().size() == 0) {
            logger.warn("Manual request sent to /checkout");
            resp.sendRedirect("/shop");
        }
        PaymentDetails paymentDetails = new PaymentDetails();
        updatePaymentDetails(paymentDetails, req);
        order.setPaymentDetails(paymentDetails);
        order.setChecked(true);
        productService.saveOrder(order);
        logger.info("Order saved with id: {}", order.getId());
        session.setAttribute("order", order);
        resp.sendRedirect("/payment");

    }

    private void updatePaymentDetails(PaymentDetails paymentDetails, HttpServletRequest req) {
        paymentDetails.setShippingCountry(req.getParameter("shipping-country"));
        paymentDetails.setShippingCity(req.getParameter("shipping-city"));
        paymentDetails.setShippingZip(Integer.parseInt(req.getParameter("zip-shipping")));
        paymentDetails.setShippingStreetHouseNum(req.getParameter("address-shipping"));

        paymentDetails.setBillingCountry(req.getParameter("billing-country"));
        paymentDetails.setBillingCity(req.getParameter("billing-city"));
        paymentDetails.setBillingZip(Integer.parseInt(req.getParameter("zip-billing")));
        paymentDetails.setBillingStreetHouseNum(req.getParameter("address-billing"));

        paymentDetails.setName(req.getParameter("name"));
        paymentDetails.setEmail(req.getParameter("e-mail"));
        paymentDetails.setPhone(req.getParameter("phone"));
    }
}
