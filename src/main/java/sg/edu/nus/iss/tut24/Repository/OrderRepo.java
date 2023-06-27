package sg.edu.nus.iss.tut24.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;

import sg.edu.nus.iss.tut24.Model.Order;

@Repository
public class OrderRepo 
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String FIND_ALL = "select * from orders";
    private final String FIND_BY_ID = "select * from orders where order_id =?";
    private final String INSERT_ORDER = "insert into orders (order_date, customer_name, ship_address, notes, tax) values (?, ?, ?, ?, ?)";
    
    public Order findOrderById(Integer orderId)
    {
        List<Order> orders = jdbcTemplate.query(FIND_BY_ID, BeanPropertyRowMapper.newInstance(Order.class), orderId);
        if(orders.isEmpty())
        {
            throw new IllegalArgumentException("Order not found");
        }
        return orders.get(0);
    }

    public List<Order> findAllOrders()
    {
        List<Order> orders = jdbcTemplate.query(FIND_ALL, BeanPropertyRowMapper.newInstance(Order.class));
         if(orders.isEmpty())
        {
            throw new IllegalArgumentException("No orders found");
        }
        return orders;
    }

    public Boolean saveOrder(Order order)
    {
        int numberOfOrder = 0;
        numberOfOrder = jdbcTemplate.update(INSERT_ORDER, order.getOrderDate(), order.getCustomerName(), order.getShipAddress(), order.getNotes(), order.getTax());
        return numberOfOrder>0 ? true : false;
    }

    //returns the primary key of inserted row
    public Integer insertOrder(Order order)
    {
        KeyHolder generatedKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() 
        {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException
            {
                PreparedStatement ps = con.prepareStatement(INSERT_ORDER, new String [] {"order-id"});
                ps.setDate(1, order.getOrderDate());
                ps.setString(2, order.getCustomerName());
                ps.setString(3, order.getShipAddress());
                ps.setString(4, order.getNotes());
                ps.setFloat(5, order.getTax());
                return ps;
            }
        };
        jdbcTemplate.update(psc, generatedKey);
        Integer returnValue = generatedKey.getKey().intValue();
        return returnValue;
    }
}
