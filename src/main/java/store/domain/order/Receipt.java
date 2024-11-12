package store.domain.order;

public class Receipt {
    private final Orders productOrders;
    private final Orders giftOrders;

    private boolean membership = false;

    public Receipt(Orders productOrders, Orders giftOrders) {
        this.productOrders = productOrders;
        this.giftOrders = giftOrders;
    }

    public Orders getProductOrders() {
        return productOrders;
    }

    public Orders getGiftOrders() {
        return giftOrders;
    }

    public void applyMembership() {
        membership = true;
    }

    public int calculateMembershipDiscount() {
        int totalPurchaseAmount = productOrders.getTotalPurchaseAmount();
        int promotionalPurchase = giftOrders.getPromotionalDiscount();
        int discount = 0;

        if (membership) {
            discount = (int) ((totalPurchaseAmount - promotionalPurchase) * 0.3);
        }

        return Math.min(discount, 8000);
    }


}
