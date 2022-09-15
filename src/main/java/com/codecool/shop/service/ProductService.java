package com.codecool.shop.service;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.base.Order;
import com.codecool.shop.model.base.Product;
import com.codecool.shop.model.base.ProductCategory;
import com.codecool.shop.model.base.Supplier;

import java.util.List;

public class ProductService {
    private static ProductService instance = null;
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;
    private OrderDao orderDao;

    private ProductService(ProductDao productDao, ProductCategoryDao productCategoryDao, SupplierDao supplierDao, OrderDao orderDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
        this.orderDao = orderDao;
    }

    public static ProductService init() {
        if (instance == null) {
            ProductDao productDataStore = ProductDaoMem.getInstance();
            ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
            SupplierDao supplierDataStorage = SupplierDaoMem.getInstance();
            OrderDao orderDao = OrderDaoMem.getInstance();
            instance = new ProductService(productDataStore, productCategoryDataStore, supplierDataStorage, orderDao);
        }
        return instance;
    }

    public ProductCategory getProductCategory(int categoryId) {
        return productCategoryDao.find(categoryId);
    }

    public List<Product> getProductsForCategory(int categoryId) {
        var category = productCategoryDao.find(categoryId);
        return productDao.getBy(category);
    }

    public List<Product> getProductsForSupplier(int supplierId) {
        var supplier = supplierDao.find(supplierId);
        return productDao.getBy(supplier);
    }

    public List<Product> getAll() {
        return productDao.getAll();
    }

    public List<ProductCategory> getAllProductCategory() {
        return productCategoryDao.getAll();
    }

    public List<Supplier> getAllSupplier() {
        return supplierDao.getAll();
    }

    public Product getProductById(int productId) {
        return productDao.find(productId);
    }

    public void addOrder(Order order) {
        orderDao.add(order);
    }

    public List<Order> getAllOrder() {
        return orderDao.getAll();
    }
}
