package store.domain.store;

import store.domain.promotion.PromotionManager;

public class ConvenienceStore {
    private final StoreInventory storeInventory;
    private final PromotionManager promotionManager;

    public ConvenienceStore(StoreInventory storeInventory, PromotionManager promotionManager) {
        this.storeInventory = storeInventory;
        this.promotionManager = promotionManager;
    }
}
