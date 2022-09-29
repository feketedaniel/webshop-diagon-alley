package com.codecool.shop.controller;

import com.codecool.shop.model.SessionUser;
import org.apache.log4j.chainsaw.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/logout"})
public class Logout extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String email = ((SessionUser) session.getAttribute("user")).getEmail();
        session.invalidate();
        logger.info("User {} logged out", email);
        resp.sendRedirect("/shop");
    }
}



