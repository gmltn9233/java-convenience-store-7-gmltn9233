package store.domain.order;

public class Receipt {
    private final Orders productOrders;
    private final Orders giftOrders;

    private boolean membership = false;

    public Receipt(Orders productOrders, Orders giftOrders) {
        this.productOrders = productOrders;
        this.giftOrders = giftOrders;
    }

    public void applyMembership() {
        membership = true;
    }
}
