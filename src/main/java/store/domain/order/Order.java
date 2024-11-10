package store.domain.order;

import store.domain.store.Product;
import store.dto.response.ReceiptResponse.OrderDetail;

public class Order {
    private final Product product;
    private int purchaseQuantity;

    public Order(Product product, int purchaseQuantity) {
        this.product = product;
        this.purchaseQuantity = purchaseQuantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public void plusQuantity(int quantity) {
        this.purchaseQuantity += quantity;
    }

    public int getPurchaseQuantity() {
        return this.purchaseQuantity;
    }

    public OrderDetail toOrderDetail() {
        return OrderDetail.from(product.getName(), purchaseQuantity, product.getPrice());
    }

}
