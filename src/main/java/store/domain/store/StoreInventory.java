package store.domain.store;

import java.util.Map;

public class StoreInventory {
    private final Map<Product, Inventory> productInventory;

    public StoreInventory(Map<Product, Inventory> storeInventory) {
        this.productInventory = storeInventory;
    }
}
