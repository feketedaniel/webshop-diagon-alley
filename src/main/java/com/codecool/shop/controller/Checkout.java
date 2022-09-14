package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.AddressInfo;
import com.codecool.shop.model.CustomerInfo;
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
import java.util.Enumeration;

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
        if (order == null) {
            resp.sendRedirect("/shop");
        } else if (order.getOrderItems().size() <= 0) {
            resp.sendRedirect("/shop");
        }
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        engine.process("product/checkout.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            resp.sendRedirect("/shop");
        } else if (order.getOrderItems().size() <= 0) {
            resp.sendRedirect("/shop");
        }
        //TODO:hard check inputs, update order
        updateBillingInfo(order, req);
        updateShippingInfo(order, req);
        updateCustomerInfo(order, req);
        order.setChecked(true);

        session.setAttribute("order", order);

        resp.sendRedirect("/payment");
    }

    private void updateBillingInfo(Order order, HttpServletRequest req) {
        String country = req.getParameter("billing-country");
        String city = req.getParameter("billing-city");
        int zip = Integer.parseInt(req.getParameter("zip-billing"));
        String address = req.getParameter("address-billing");
        AddressInfo billingInfo = new AddressInfo(country, city, zip, address);
        order.setBillingInformation(billingInfo);
    }

    private void updateShippingInfo(Order order, HttpServletRequest req) {
        String country = req.getParameter("shipping-country");
        String city = req.getParameter("shipping-city");
        int zip = Integer.parseInt(req.getParameter("zip-shipping"));
        String address = req.getParameter("address-shipping");
        AddressInfo shippingInfo = new AddressInfo(country, city, zip, address);
        order.setShippingInformation(shippingInfo);
    }

    private void updateCustomerInfo(Order order, HttpServletRequest req) {
        String name = req.getParameter("name");
        String email = req.getParameter("e-mail");
        String phone = req.getParameter("phone");
        CustomerInfo customerInfo = new CustomerInfo(name, email, phone);
        order.setCustomerInformation(customerInfo);
    }


}
