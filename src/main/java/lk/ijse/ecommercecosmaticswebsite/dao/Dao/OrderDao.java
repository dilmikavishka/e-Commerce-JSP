package lk.ijse.ecommercecosmaticswebsite.dao.Dao;


import lk.ijse.ecommercecosmaticswebsite.dao.CrudDAO;
import lk.ijse.ecommercecosmaticswebsite.dto.OrderDetailsWithOrder;
import lk.ijse.ecommercecosmaticswebsite.entity.Order;

import java.util.List;

public interface OrderDao extends CrudDAO<Order> {
    String generateNewOrderId();

    List<OrderDetailsWithOrder> getOrderDetailsByUser(String userId);

    List<OrderDetailsWithOrder> getAllOrderDetails();

    boolean updateOrder(String orderId, String status);
}
