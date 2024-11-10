package store.domain.order;

import java.util.List;
import java.util.stream.Collectors;
import store.dto.response.ReceiptResponse.OrderDetail;

public class Orders {
    private final List<Order> orders;

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<OrderDetail> toOrderDetails() {
        return orders.stream().map(Order::toOrderDetail).collect(Collectors.toList());
    }

    public List<OrderDetail> toGiftOrderDetails() {
        return orders.stream()
                .filter(order -> order instanceof PromotionOrder)
                .map(order -> ((PromotionOrder) order).toGiftOrderDetail())
                .collect(Collectors.toList());
    }

    public int getTotalQuantity() {
        return orders.stream().mapToInt(Order::getPurchaseQuantity).sum();
    }

    public int getTotalPurchaseAmount() {
        return orders.stream().mapToInt(order -> order.getProduct().getPrice() * order.getPurchaseQuantity()).sum();
    }

    public int getPromotionalDiscount() {
        return orders.stream()
                .filter(order -> order instanceof PromotionOrder)
                .mapToInt(order -> ((PromotionOrder) order).getProduct().getPrice()
                        * ((PromotionOrder) order).getGiftQuantity())
                .sum();
    }
}
