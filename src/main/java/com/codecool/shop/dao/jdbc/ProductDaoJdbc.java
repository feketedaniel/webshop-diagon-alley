package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {
    private DataSource dataSource;
    public ProductDaoJdbc(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name,default_price,default_currency,description,product_category_id,supplier_id,img_url FROM product WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ProductCategory productCategory = new ProductCategoryDaoJdbc(dataSource).find(rs.getInt(5));
            Supplier supplier =new SupplierDaoJdbc(dataSource).find(rs.getInt(6));
            Product product = new Product(rs.getString(1),rs.getBigDecimal(2), rs.getString(3),rs.getString(4),productCategory,supplier,rs.getString(7) );
            product.setId(id);
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with id: " + id, e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id,name,default_price,default_currency,description,product_category_id,supplier_id,img_url FROM product";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            while (rs.next()) {
                ProductCategory productCategory = new ProductCategoryDaoJdbc(dataSource).find(rs.getInt(6));
                Supplier supplier =new SupplierDaoJdbc(dataSource).find(rs.getInt(7));
                Product product = new Product(rs.getString(2),rs.getBigDecimal(3), rs.getString(4),rs.getString(5),productCategory,supplier,rs.getString(8) );
                product.setId(rs.getInt(1));
                result.add(product);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }
}
