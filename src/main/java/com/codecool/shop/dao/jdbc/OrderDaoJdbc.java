package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Set;
import java.util.UUID;

public class OrderDaoJdbc implements OrderDao {
    private DataSource dataSource;
    public OrderDaoJdbc(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Override
    public void add(Order order) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO \"order\" (user_id, payment_details_id, is_checked, is_payed) VALUES (?, ?, ?,?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (order.getUserId() == null) {
                st.setNull(1, Types.INTEGER);
            } else {
                st.setString(1, order.getUserId().toString());
            }
            st.setInt(2, order.getPaymentDetails().getId());
            st.setBoolean(3, order.isPayed());
            st.setBoolean(4, order.isPayed());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next(); // Read next returned value - in this case the first one. See ResultSet docs for more explaination
            order.setId(rs.getInt(1));
            order.getPaymentDetails().setOrderId(rs.getInt(1));

        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new Author.", throwables);
        }

    }

    @Override
    public Order find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Set<Order> getAll() {
        return null;
    }

    @Override
    public Set<Order> getBy(UUID customerId) {
        return null;
    }
}
