package store.domain.store;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import store.domain.order.Order;
import store.domain.promotion.PromotionManager;
import store.dto.response.StoreResponse;

public class StoreInventory {
    private final Map<Product, Inventory> productInventory;

    public StoreInventory(Map<Product, Inventory> storeInventory) {
        this.productInventory = storeInventory;
    }

    public Optional<Order> purchaseProduct(Order order, PromotionManager promotionManager) {
        Inventory inventory = getInventory(order);
        int purchaseQuantity = order.getPurchaseQuantity();

        if (!promotionManager.hasPromotion(order)) {
            inventory.consumeRegularInventory(purchaseQuantity);
            return Optional.empty();
        }

        return purchasePromotionalProduct(order, promotionManager, inventory);
    }

    private Optional<Order> purchasePromotionalProduct(Order order, PromotionManager promotionManager,
                                                       Inventory inventory) {
        int purchaseQuantity = order.getPurchaseQuantity();
        int promotionQuantity = inventory.getMaxPromotionQuantity(purchaseQuantity);
        int regularQuantity = purchaseQuantity - promotionQuantity;

        Optional<Order> giftOrder = promotionManager.createGiftOrder(order.getProduct(), promotionQuantity, inventory);
        if (giftOrder.isEmpty()) {
            regularQuantity = purchaseQuantity;
            inventory.consumeRegularInventory(regularQuantity);
            return giftOrder;
        }
        inventory.consumePromotionalInventory(promotionQuantity);
        inventory.consumeRegularInventory(regularQuantity);

        return giftOrder;
    }

    private Inventory getInventory(Order order) {
        Product product = order.getProduct();
        return productInventory.get(product);
    }

    public Product findProductByName(String name) {
        return productInventory.keySet().stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<StoreResponse> getProductInventoryDetails(PromotionManager promotionManager) {
        return productInventory.entrySet().stream()
                .map(entry -> {
                    Product product = entry.getKey();
                    Inventory inventory = entry.getValue();
                    String promotionName = promotionManager.getPromotionName(product);
                    return StoreResponse.from(
                            product,
                            inventory.getPromotionalInventory(),
                            inventory.getRegularInventory(),
                            promotionName
                    );
                })
                .collect(Collectors.toList());
    }

}
