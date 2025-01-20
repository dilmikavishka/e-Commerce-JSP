package lk.ijse.ecommercecosmaticswebsite.bo.BoImp;


import lk.ijse.ecommercecosmaticswebsite.bo.Bo.OrderBo;
import lk.ijse.ecommercecosmaticswebsite.dao.Dao.OrderDao;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoFactory;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl.OrderDaoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.OrderDTO;
import lk.ijse.ecommercecosmaticswebsite.dto.OrderDetailsWithOrder;
import lk.ijse.ecommercecosmaticswebsite.entity.Order;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class OrderBoImpl implements OrderBo {
    OrderDao orderDao = (OrderDao) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.Order);

    public OrderBoImpl(DataSource dataSource) {
        this.orderDao = new OrderDaoImpl(dataSource);
    }

    public OrderBoImpl(){

    }

    @Override
    public String generateNewOrderId() {
        return orderDao.generateNewOrderId();
    }

    @Override
    public boolean saveOrder(OrderDTO order) {
        Order orderEntity = new Order();
        orderEntity.setOrderId(order.getOrderId());
        orderEntity.setUserId(order.getUserId());
        orderEntity.setStatus(order.getStatus());
        orderEntity.setTotalPrice(order.getTotalPrice());
        return orderDao.save(orderEntity);
    }

    @Override
    public List<OrderDetailsWithOrder> getAllOrderDetails() {
        List<OrderDetailsWithOrder> orderDetails = orderDao.getAllOrderDetails();
        List<OrderDetailsWithOrder> orderDetailsWithOrders = new ArrayList<>();
        for (OrderDetailsWithOrder orderDetailsWithOrder : orderDetails){
            OrderDetailsWithOrder dto = new OrderDetailsWithOrder();
            dto.setOrderId(orderDetailsWithOrder.getOrderId());
            dto.setUserId(orderDetailsWithOrder.getUserId());
            dto.setOrderDate(orderDetailsWithOrder.getOrderDate());
            dto.setTotalPrice(orderDetailsWithOrder.getTotalPrice());
            dto.setStatus(orderDetailsWithOrder.getStatus());
            dto.setOrderDetailId(orderDetailsWithOrder.getOrderDetailId());
            dto.setProductId(orderDetailsWithOrder.getProductId());
            dto.setQuantity(orderDetailsWithOrder.getQuantity());
            dto.setPrice(orderDetailsWithOrder.getPrice());
            dto.setTotalPricePerItem(orderDetailsWithOrder.getTotalPricePerItem());
            orderDetailsWithOrders.add(dto);
        }
        return orderDetailsWithOrders;
    }

    @Override
    public List<OrderDetailsWithOrder> getOrderDetailsByUser(String userId) {
        List<OrderDetailsWithOrder> orderDetailsByUser = orderDao.getOrderDetailsByUser(userId);
        List<OrderDetailsWithOrder> orderDetailsWithOrders = new ArrayList<>();
        for (OrderDetailsWithOrder orderDetailsWithOrder : orderDetailsByUser){
            OrderDetailsWithOrder dto = new OrderDetailsWithOrder();
            dto.setOrderId(orderDetailsWithOrder.getOrderId());
            dto.setUserId(userId);
            dto.setOrderDate(orderDetailsWithOrder.getOrderDate());
            dto.setTotalPrice(orderDetailsWithOrder.getTotalPrice());
            dto.setStatus(orderDetailsWithOrder.getStatus());
            dto.setOrderDetailId(orderDetailsWithOrder.getOrderDetailId());
            dto.setProductId(orderDetailsWithOrder.getProductId());
            dto.setQuantity(orderDetailsWithOrder.getQuantity());
            dto.setPrice(orderDetailsWithOrder.getPrice());
            dto.setTotalPricePerItem(orderDetailsWithOrder.getTotalPricePerItem());
            orderDetailsWithOrders.add(dto);
        }
        return orderDetailsWithOrders;
    }

    @Override
    public boolean updateOrder(String orderId, String status) {
        return orderDao.updateOrder(orderId,status);
    }
}
