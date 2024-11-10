package store.common.Loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionManager;
import store.domain.store.ConvenienceStore;
import store.domain.store.Inventory;
import store.domain.store.Product;
import store.domain.store.StoreInventory;

public class StoreLoader {

    private static final String FILE_PATH = "src/main/resources/products.md";
    private static final String NULL_PROMOTION = "null";
    private final PromotionLoader promotionLoader;

    public StoreLoader(PromotionLoader promotionLoader) {
        this.promotionLoader = promotionLoader;
    }

    public ConvenienceStore createConvenienceStore() throws IOException {
        List<Promotion> promotions = promotionLoader.loadPromotions();
        List<String> lines = readFileLines(FILE_PATH);

        Map<Product, Inventory> inventoryMap = new LinkedHashMap<>();
        Map<Product, Promotion> promotionMap = new LinkedHashMap<>();

        for (int i = 1; i < lines.size(); i++) { // Assuming the first line is a header
            parseProductLine(lines.get(i), inventoryMap, promotionMap, promotions);
        }

        StoreInventory storeInventory = new StoreInventory(inventoryMap);
        PromotionManager promotionManager = new PromotionManager(promotionMap);
        return new ConvenienceStore(storeInventory, promotionManager);
    }

    private List<String> readFileLines(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    private void parseProductLine(String line, Map<Product, Inventory> inventoryMap,
                                  Map<Product, Promotion> promotionMap, List<Promotion> promotions) {
        List<String> columns = parseLine(line);
        if (columns.size() < 4) {
            System.err.println("Invalid product data: " + line);
            return;
        }

        String name = columns.get(0).trim();
        int price = parseInteger(columns.get(1).trim(), "price");
        int quantity = parseInteger(columns.get(2).trim(), "quantity");
        String promotionName = columns.get(3).trim();

        Product product = new Product(name, price);
        Inventory inventory = inventoryMap.getOrDefault(product, new Inventory(0, 0));

        updateInventoryAndPromotions(product, quantity, promotionName, inventory, inventoryMap, promotionMap,
                promotions);
    }

    private List<String> parseLine(String line) {
        return List.of(line.split(","));
    }

    private int parseInteger(String value, String fieldName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + fieldName + ": " + value);
        }
    }

    private void updateInventoryAndPromotions(Product product, int quantity, String promotionName,
                                              Inventory currentInventory, Map<Product, Inventory> inventoryMap,
                                              Map<Product, Promotion> promotionMap, List<Promotion> promotions) {
        if (isRegularInventory(promotionName)) {
            currentInventory.addRegularInventory(quantity);
        } else {
            addPromotionInventory(product, quantity, promotionName, currentInventory, promotionMap, promotions);
        }
        inventoryMap.put(product, currentInventory);
    }

    private boolean isRegularInventory(String promotionName) {
        return promotionName.equalsIgnoreCase(NULL_PROMOTION);
    }

    private void addPromotionInventory(Product product, int quantity, String promotionName,
                                       Inventory currentInventory, Map<Product, Promotion> promotionMap,
                                       List<Promotion> promotions) {
        Promotion promotion = findPromotion(promotions, promotionName);
        if (promotion != null) {
            currentInventory.addPromotionalInventory(quantity);
            promotionMap.put(product, promotion);
        }
    }

    private Promotion findPromotion(List<Promotion> promotions, String promotionName) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(promotionName))
                .findFirst()
                .orElse(null);
    }
}
