package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
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
import java.util.Enumeration;


@WebServlet(urlPatterns = {"/"})
public class Index extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = null;
        try {
            productService = ProductService.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        HttpSession session = req.getSession();

        System.out.println("Session contents: ");
        Enumeration<String> attributes = session.getAttributeNames();
        while (attributes.hasMoreElements()) {
            String attribute = (String) attributes.nextElement();
            System.out.println("Attribute name: "+attribute+"\n" +
                    "Attribute values:\t "+session.getAttribute(attribute));
        }

        System.out.println("Saved orders:");
        productService.getAllOrder().forEach(System.out::println);

        System.out.println("Saved users:");
        productService.getAllUser().forEach(System.out::println);


        context.setVariable("categories", productService.getAllProductCategory());
        context.setVariable("suppliers", productService.getAllSupplier());
        context.setVariable("user",session.getAttribute("user"));
        engine.process("product/index.html", context, resp.getWriter());
    }
}
