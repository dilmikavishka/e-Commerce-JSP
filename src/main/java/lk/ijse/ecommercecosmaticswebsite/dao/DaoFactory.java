package lk.ijse.ecommercecosmaticswebsite.dao;


import lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl.CartDaoImpl;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl.CartItemDaoImpl;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl.CategoryDaoImpl;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl.UserDaoImpl;

public class DaoFactory {
    public static  DaoFactory  daoFactory;

    private DaoFactory() {}

    public static DaoFactory getDaoFactory() {
        return (daoFactory == null) ? new DaoFactory() : daoFactory;
    }

    public enum DAOTYPE{
        User,Category,Product,Order,OrderDetail,Cart,CartItem
    }
    public SuperDAO getDAOType(DAOTYPE daotype) {
        switch (daotype){
            case User:
                return new UserDaoImpl();
            case Category:
                return new CategoryDaoImpl();
            case Cart:
                return new CartDaoImpl();
            case CartItem:
                return new CartItemDaoImpl();
            default:
                return null;
        }

    }
}
