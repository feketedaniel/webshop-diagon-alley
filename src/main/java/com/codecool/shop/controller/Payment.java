package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.service.ProductService;
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
            RequestDispatcher dispatcher = getServletContext()
                    .getRequestDispatcher("/order_confirmation");
            dispatcher.forward(req, resp);
        } else {
            Order order = (Order) session.getAttribute("order");
//            gatewayCheck(order,resp);
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            engine.process("product/payment.html", context, resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        WebContext context = new WebContext(req, resp, req.getServletContext());
        ProductService productService = ProductService.init();
        context.setVariable("categories", productService.getAllProductCategory());
        context.setVariable("suppliers", productService.getAllSupplier());
        Order order = (Order) session.getAttribute("order");
        gatewayCheck(order,resp);
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
