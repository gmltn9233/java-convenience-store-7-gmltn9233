package store.domain.order;

import store.domain.store.Product;
import store.dto.response.ReceiptResponse.OrderDetail;

public class Order {
    private final Product product;
    private final int purchaseQuantity;

    public Order(Product product, int purchaseQuantity) {
        this.product = product;
        this.purchaseQuantity = purchaseQuantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getPurchaseQuantity() {
        return this.purchaseQuantity;
    }

    public OrderDetail toOrderDetail() {
        return OrderDetail.from(product.getName(), purchaseQuantity, product.getPrice());
    }

}
