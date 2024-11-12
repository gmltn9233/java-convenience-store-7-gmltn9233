package store.domain.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.domain.order.Order;
import store.domain.order.Orders;
import store.domain.order.Receipt;
import store.domain.promotion.PromotionManager;

public class ConvenienceStore {
    private final StoreInventory storeInventory;
    private final PromotionManager promotionManager;

    public ConvenienceStore(StoreInventory storeInventory, PromotionManager promotionManager) {
        this.storeInventory = storeInventory;
        this.promotionManager = promotionManager;
    }

    public Receipt purchaseProducts(Orders orders) {
        List<Order> productOrders = orders.getOrders();
        Orders giftOrders = new Orders(new ArrayList<>());

        for (Order productOrder : productOrders) {
            Optional<Order> optionalOrder = storeInventory
                    .purchaseProduct(productOrder, promotionManager);
            optionalOrder.ifPresent(giftOrders::addOrder);
        }

        return new Receipt(orders, giftOrders);
    }

    public StoreInventory getStoreInventory() {
        return storeInventory;
    }

    public PromotionManager getPromotionManager() {
        return promotionManager;
    }

}
