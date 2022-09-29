package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.User;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

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
        engine.process("product/login.html", context, resp.getWriter());

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = null;
        try {
            productService = ProductService.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        HttpSession session = req.getSession();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String email = req.getParameter("login-email");
        String password = req.getParameter("login-password");
        User registeredUser = productService.findByEmail(email);
        if (registeredUser == null) {
            resp.sendRedirect("/login");
            return;
        }

        KeySpec spec = new PBEKeySpec(password.toCharArray(), registeredUser.getSalt(), 65536, 128);
        SecretKeyFactory factory = null;
        byte[] hash;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Arrays.toString(hash));
        System.out.println(Arrays.toString(registeredUser.getPassword()));
        if (Arrays.toString(hash).equals(Arrays.toString(registeredUser.getPassword()))) {
            session.setAttribute("user", new SessionUser(registeredUser.getId(),
                    registeredUser.getName(),
                    registeredUser.getEmail()));
            resp.sendRedirect("/shop");
            return;
        }
        System.out.println("nouser");
        resp.sendRedirect("/login");

    }

    private class SessionUser {
        private UUID id;
        private String name;
        private String email;

        public SessionUser(UUID id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }

}