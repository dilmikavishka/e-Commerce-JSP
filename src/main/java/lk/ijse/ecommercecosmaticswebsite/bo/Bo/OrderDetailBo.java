package lk.ijse.ecommercecosmaticswebsite.bo.Bo;


import lk.ijse.ecommercecosmaticswebsite.bo.SuperBo;
import lk.ijse.ecommercecosmaticswebsite.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailBo extends SuperBo {
    String generateNewOrderDetailId();

    boolean saveOrderDetails(List<OrderDetailDTO> orderDetails);
}
