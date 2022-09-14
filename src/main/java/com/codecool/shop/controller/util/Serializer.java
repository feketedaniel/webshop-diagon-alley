package com.codecool.shop.controller.util;

import com.codecool.shop.model.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class Serializer {
    private static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void serializeOrder(HttpServletResponse resp, Order serverOrder) throws IOException {
        String jsonString = gson.toJson(serverOrder);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }
}