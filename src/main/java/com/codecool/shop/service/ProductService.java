package com.codecool.shop.service;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.jdbc.OrderDaoJdbc;
import com.codecool.shop.dao.jdbc.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.jdbc.ProductDaoJdbc;
import com.codecool.shop.dao.jdbc.SupplierDaoJdbc;
import com.codecool.shop.dao.mem.*;
import com.codecool.shop.model.*;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private static ProductService instance = null;
    private static Properties connProps;
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;
    private OrderDao orderDao;

    private UserDao userDao;

    private ProductService(ProductDao productDao, ProductCategoryDao productCategoryDao, SupplierDao supplierDao, OrderDao orderDao, UserDao userDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
        this.orderDao = orderDao;
        this.userDao = userDao;
    }

    public static ProductService init() throws IOException, SQLException {
        setProperties();
        if (Objects.equals(connProps.getProperty("dao"), "memory")) {
            if (instance == null) {
                ProductDao productDataStore = ProductDaoMem.getInstance();
                ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
                SupplierDao supplierDataStorage = SupplierDaoMem.getInstance();
                OrderDao orderDao = OrderDaoMem.getInstance();
                UserDao userDao = UserDaoMem.getInstance();
                instance = new ProductService(productDataStore, productCategoryDataStore, supplierDataStorage, orderDao, userDao);
            }
            return instance;
        } else if (Objects.equals(connProps.getProperty("dao"), "jdbc")) {
            DataSource dataSource = connect();
            ProductDao productDao = new ProductDaoJdbc(dataSource);
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoJdbc(dataSource);
            SupplierDao supplierDao = new SupplierDaoJdbc(dataSource);
            OrderDao orderDao = new OrderDaoJdbc(dataSource);
            UserDao userDao = null;
            return new ProductService(productDao, productCategoryDao, supplierDao, orderDao, userDao);
        } else {
            logger.error("Incorrect config property: {}", connProps.getProperty("dao"));
            throw new RuntimeException();
        }
    }

    private static DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(connProps.getProperty("database"));
        dataSource.setUser(connProps.getProperty("user"));
        dataSource.setPassword(connProps.getProperty("password"));

        dataSource.getConnection().close();

        return dataSource;
    }

    private static void setProperties() throws IOException {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String appConfigPath = rootPath + "connection.properties";
        connProps = new Properties();
        connProps.load(new FileInputStream(appConfigPath));

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

    public boolean isRegistered(String email) {
        return userDao.isRegistered(email);
    }

    public void addNewUser(User user) {
        userDao.add(user);
    }

    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
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

    public void saveOrder(Order order) {
        orderDao.add(order);
    }

    public Set<Order> getAllOrder() {
        return orderDao.getAll();
    }

    public void setOrderPayed(int orderId) {
        orderDao.find(orderId).setPayed(true);
    }

    public Set<User> getAllUser() {
        return userDao.getAllUser();
    }
}
