package com.codecool.shop.dao;

import com.codecool.shop.model.base.Product;
import com.codecool.shop.model.base.ProductCategory;
import com.codecool.shop.model.base.Supplier;

import java.util.List;

public interface ProductDao {

    void add(Product product);

    Product find(int id);

    void remove(int id);

    List<Product> getAll();

    List<Product> getBy(Supplier supplier);

    List<Product> getBy(ProductCategory productCategory);

}
