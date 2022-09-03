package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(urlPatterns = {"/shop"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStorage = SupplierDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore, productCategoryDataStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStorage.getAll());

        Enumeration<String> params = req.getParameterNames();
        if (params.hasMoreElements()) {
            String param = params.nextElement();
            System.out.println("Param: " + param + ", Value: " + req.getParameter(param));
            switch (param) {
                case "categoryId":
                    context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(Integer.parseInt(req.getParameter(param)))));
                    break;
                case "supplierId":
                    context.setVariable("products", productDataStore.getBy(supplierDataStorage.find(Integer.parseInt(req.getParameter(param)))));
                    break;
            }
        } else {
            context.setVariable("products", productDataStore.getAll());
        }


        engine.process("product/index.html", context, resp.getWriter());
    }

}
