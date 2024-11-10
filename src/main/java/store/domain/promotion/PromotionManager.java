package store.domain.promotion;

import java.util.Map;
import store.domain.store.Product;

public class PromotionManager {
    private final Map<Product, Promotion> productPromotions;

    public PromotionManager(Map<Product, Promotion> productPromotions) {
        this.productPromotions = productPromotions;
    }
}
