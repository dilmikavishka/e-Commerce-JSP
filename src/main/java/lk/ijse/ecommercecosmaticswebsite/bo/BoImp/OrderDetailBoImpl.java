package lk.ijse.ecommercecosmaticswebsite.bo.BoImp;


import lk.ijse.ecommercecosmaticswebsite.bo.Bo.OrderDetailBo;
import lk.ijse.ecommercecosmaticswebsite.dao.Dao.OrderDetailDao;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoFactory;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl.OrderDetailDaoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.OrderDetailDTO;
import lk.ijse.ecommercecosmaticswebsite.entity.OrderDetail;

import javax.sql.DataSource;
import java.util.List;

public class OrderDetailBoImpl implements OrderDetailBo {
    OrderDetailDao orderDetailDao = (OrderDetailDao) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.OrderDetail);

    public OrderDetailBoImpl(DataSource dataSource) {
        this.orderDetailDao = new OrderDetailDaoImpl(dataSource);
    }

    public OrderDetailBoImpl() {

    }

    @Override
    public String generateNewOrderDetailId() {
        return orderDetailDao.generateNewOrderDetailId();
    }

    @Override
    public boolean saveOrderDetails(List<OrderDetailDTO> orderDetails) {
        boolean allSaved = true;

        try {
            for (OrderDetailDTO orderDetailDTO : orderDetails) {
                String generateNewOrderDetailId = generateNewOrderDetailId();
                orderDetailDTO.setOrderDetailId(generateNewOrderDetailId);
                OrderDetail orderDetail = new OrderDetail(
                        orderDetailDTO.getOrderDetailId(),
                        orderDetailDTO.getOrderId(),
                        orderDetailDTO.getProductId(),
                        orderDetailDTO.getQuantity(),
                        orderDetailDTO.getPrice()
                );

                boolean saved = orderDetailDao.save(orderDetail);
                if (!saved) {
                    allSaved = false;
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving cart items", e);
        }

        return allSaved;
    }
}
