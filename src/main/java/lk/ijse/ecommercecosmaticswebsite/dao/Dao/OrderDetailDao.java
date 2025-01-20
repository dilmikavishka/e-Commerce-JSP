package lk.ijse.ecommercecosmaticswebsite.dao.Dao;


import lk.ijse.ecommercecosmaticswebsite.dao.CrudDAO;
import lk.ijse.ecommercecosmaticswebsite.entity.OrderDetail;

public interface OrderDetailDao extends CrudDAO<OrderDetail> {
    String generateNewOrderDetailId();
}
