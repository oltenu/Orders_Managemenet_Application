package logic;

import data.access.OrderDAO;
import model.Bill;
import model.ClientM;
import model.OrderM;
import model.Product;
import validator.OrderValidator;

import java.util.List;

public class OrderBLL {
    private final OrderValidator orderValidator;
    private final OrderDAO orderDAO;

    public OrderBLL() {
        orderValidator = new OrderValidator();
        orderDAO = new OrderDAO();
    }

    public boolean insertOrder(Product product, ClientM clientM, BillBLL billBLL, ProductBLL productBLL, int amount) {
        Bill bill = billBLL.createBill(product, amount);
        OrderM orderM = new OrderM(orderValidator.getCurrentId(), product.getId(), clientM.getId(), bill.id(), amount);
        if (!orderValidator.validateOrder(orderM, product)){
            System.out.println("asasf");
            return false;
        }
        orderDAO.insert(orderM);
        int newAmount = product.getAmount() - amount;
        product.setAmount(newAmount);
        productBLL.updateProduct(product.getId(), product.getProductName(), product.getAmount(), product.getPrice(), product.getManufacturer());
        billBLL.saveBill();

        return true;
    }

    public OrderM findOrder(long id) {
        return orderDAO.findById(id);
    }

    public List<OrderM> findAllOrders() {
        return orderDAO.findAll();
    }

    public void deleteOrder(long id) {
        orderDAO.delete(id);
    }
}
