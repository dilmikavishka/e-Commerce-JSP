package lk.ijse.ecommercecosmaticswebsite.bo;


import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.*;

public class BoFactory {
    private static BoFactory boFactory;

    public BoFactory() {
    }

    public static BoFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BoFactory() : boFactory;
    }
    public enum BoType{
        User,Category,Product,Order,OrderDetail,Cart,CartItem
    }
    public SuperBo getBO(BoType type){
        switch (type){
            case User:
                return new UserBoImpl();
            case Category:
                return new CategoryBoImpl();
            case Product:
                return new ProductBoImpl();
            case Cart:
                return new CartBoImpl();
            case CartItem:
                return new CartItemBoImpl();
            case Order:
                return new OrderBoImpl();
            case OrderDetail:
                return new OrderDetailBoImpl();
            default:
                return null;
        }
    }
}
