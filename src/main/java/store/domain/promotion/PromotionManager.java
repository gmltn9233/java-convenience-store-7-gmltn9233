package store.domain.promotion;

import java.util.Map;
import java.util.Optional;
import store.domain.order.Order;
import store.domain.order.PromotionOrder;
import store.domain.store.Inventory;
import store.domain.store.Product;

public class PromotionManager {
    private final Map<Product, Promotion> productPromotions;

    public PromotionManager(Map<Product, Promotion> productPromotions) {
        this.productPromotions = productPromotions;
    }

    public boolean hasPromotion(Order order) {
        Product product = order.getProduct();
        return productPromotions.containsKey(product);
    }

    public String getPromotionName(Product product) {
        return getPromotion(product).map(Promotion::getName).orElse("");
    }

    public Optional<Order> createGiftOrder(Product product, int purchaseQuantity, Inventory inventory) {
        Promotion promotion = productPromotions.get(product);

        if (!promotion.isEligible(purchaseQuantity)) {
            inventory.mergeRemainingInventory();
            return Optional.empty();
        }

        int promotionBenefit = promotion.getPromotionBenefit(purchaseQuantity);
        if (promotionBenefit <= 0) {
            return Optional.empty();
        }
        int promotionQuantity = promotion.getCriteria() * promotionBenefit;

        return Optional.of(new PromotionOrder(product, promotionQuantity, promotionBenefit));
    }

    public Optional<Promotion> getPromotion(Product product) {
        return Optional.ofNullable(productPromotions.get(product));
    }

}
