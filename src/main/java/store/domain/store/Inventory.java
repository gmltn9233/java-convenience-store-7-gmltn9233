package store.domain.store;

import store.common.exception.ErrorMessage;
import store.common.exception.QuantityException;

public class Inventory {
    private int regularInventory;
    private int promotionalInventory;

    public Inventory(int regularInventory, int promotionalInventory) {
        this.regularInventory = regularInventory;
        this.promotionalInventory = promotionalInventory;
    }

    public int getRegularInventory() {
        return regularInventory;
    }

    public int getPromotionalInventory() {
        return promotionalInventory;
    }

    public void addRegularInventory(int quantity) {
        this.regularInventory += quantity;
    }

    public void addPromotionalInventory(int quantity) {
        this.promotionalInventory += quantity;
    }

    public int getMaxPromotionQuantity(int purchaseQuantity) {
        return Math.min(promotionalInventory, purchaseQuantity);
    }

    public void checkRegularInventory(int purchaseQuantity) {
        validatePurchaseQuantity(regularInventory, purchaseQuantity);
    }

    public void checkPromotionalInventory(int purchaseQuantity) {
        validatePurchaseQuantity(promotionalInventory, purchaseQuantity);
    }

    public void consumeRegularInventory(int purchaseQuantity) {
        checkRegularInventory(purchaseQuantity);
        regularInventory -= purchaseQuantity;
    }

    public void consumePromotionalInventory(int purchaseQuantity) {
        checkPromotionalInventory(purchaseQuantity);
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
