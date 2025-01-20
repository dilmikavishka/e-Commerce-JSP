package lk.ijse.ecommercecosmaticswebsite.bo.Bo;


import lk.ijse.ecommercecosmaticswebsite.bo.SuperBo;
import lk.ijse.ecommercecosmaticswebsite.dto.OrderDTO;
import lk.ijse.ecommercecosmaticswebsite.dto.OrderDetailsWithOrder;

import java.util.List;

public interface OrderBo extends SuperBo {
    String generateNewOrderId();

    boolean saveOrder(OrderDTO order);

    List<OrderDetailsWithOrder> getAllOrderDetails();

    List<OrderDetailsWithOrder> getOrderDetailsByUser(String userId);

    boolean updateOrder(String orderId, String status);
}
