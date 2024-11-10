package store.domain.order;

import store.domain.store.Product;

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

}
