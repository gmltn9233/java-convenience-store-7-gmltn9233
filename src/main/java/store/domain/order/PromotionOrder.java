package store.domain.order;

import store.domain.store.Product;

public class PromotionOrder extends Order {
    private final int giftQuantity;

    public PromotionOrder(Product product, int purchaseQuantity, int giftQuantity) {
        super(product, purchaseQuantity);
        this.giftQuantity = giftQuantity;
    }
}
