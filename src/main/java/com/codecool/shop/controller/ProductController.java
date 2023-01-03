package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
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
import java.sql.SQLException;
import java.util.Enumeration;

@WebServlet(urlPatterns = {"/shop"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProductService productService = null;
        try {
            productService = ProductService.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        HttpSession session = req.getSession();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("user", session.getAttribute("user"));

        context.setVariable("categories", productService.getAllProductCategory());
        context.setVariable("suppliers", productService.getAllSupplier());
        setProductsBySearchParameters(productService, context, req);

        engine.process("product/shop.html", context, resp.getWriter());
    }

    private void setProductsBySearchParameters(ProductService service, WebContext context, HttpServletRequest req) {
        Enumeration<String> params = req.getParameterNames();
        if (params.hasMoreElements()) {
            String param = params.nextElement();
            switch (param) {
                case "categoryId":
                    context.setVariable("products", service.getProductsForCategory(Integer.parseInt(req.getParameter(param))));
                    break;
                case "supplierId":
                    context.setVariable("products", service.getProductsForSupplier(Integer.parseInt(req.getParameter(param))));
                    break;
            }
        } else {
            context.setVariable("products", service.getAll());
        }
    }

}
