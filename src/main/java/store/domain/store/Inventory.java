package store.domain.store;

public class Inventory {
    private int regularInventory;
    private int promotionalInventory;

    public Inventory(int regularInventory, int promotionalInventory) {
        this.regularInventory = regularInventory;
        this.promotionalInventory = promotionalInventory;
    }
}
