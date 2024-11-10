package store.domain.order;

import store.domain.store.Product;
import store.dto.response.ReceiptResponse.OrderDetail;

public class PromotionOrder extends Order {
    private final int giftQuantity;

    public PromotionOrder(Product product, int purchaseQuantity, int giftQuantity) {
        super(product, purchaseQuantity);
        this.giftQuantity = giftQuantity;
    }

    public int getGiftQuantity() {
        return this.giftQuantity;
    }

    @Override
    public OrderDetail toOrderDetail() {
        return OrderDetail.from(getProduct().getName(), getPurchaseQuantity(), getProduct().getPrice());
    }

    public OrderDetail toGiftOrderDetail() {
        return OrderDetail.from(getProduct().getName(), giftQuantity, 0);
    }
}
