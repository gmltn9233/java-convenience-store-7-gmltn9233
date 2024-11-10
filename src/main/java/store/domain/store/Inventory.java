package store.domain.store;

public class Inventory {
    private int regularInventory;
    private int promotionalInventory;

    public Inventory(int regularInventory, int promotionalInventory) {
        this.regularInventory = regularInventory;
        this.promotionalInventory = promotionalInventory;
    }
    public int getMaxPromotionQuantity(int purchaseQuantity) {
        return Math.min(promotionalInventory, purchaseQuantity);
    }

    public void consumeRegularInventory(int purchaseQuantity) {
        validatePurchaseQuantity(regularInventory, purchaseQuantity);
        regularInventory -= purchaseQuantity;
    }

    public void consumePromotionalInventory(int purchaseQuantity) {
        validatePurchaseQuantity(promotionalInventory, purchaseQuantity);
        promotionalInventory -= purchaseQuantity;
    }

    public void mergeRemainingInventory() {
        regularInventory += promotionalInventory;
        promotionalInventory = 0;
    }

    private void validatePurchaseQuantity(int availableQuantity, int purchaseQuantity) {
        if (availableQuantity < purchaseQuantity) {
            throw new QuantityException(ErrorMessage.INVALID_QUANTITY.getMessage());
        }
    }
}
