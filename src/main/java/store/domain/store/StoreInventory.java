package store.domain.store;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import store.common.exception.ErrorMessage;
import store.common.exception.InvalidOrderException;
import store.common.exception.StoreException;
import store.domain.order.Order;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionManager;
import store.dto.request.YesNoRequest;
import store.dto.response.StoreResponse;
import store.view.InputView;
import store.view.OutputView;

public class StoreInventory {
    private final Map<Product, Inventory> productInventory;

    public StoreInventory(Map<Product, Inventory> storeInventory) {
        this.productInventory = storeInventory;
    }

    public Optional<Order> purchaseProduct(Order order, PromotionManager promotionManager) {
        Inventory inventory = getInventory(order);
        int purchaseQuantity = order.getPurchaseQuantity();

        if (!promotionManager.canPromotion(order)) {
            inventory.consumeRegularInventory(purchaseQuantity);
            return Optional.empty();
        }

        return processPromotionalPurchase(order, promotionManager, inventory);
    }

    private Optional<Order> processPromotionalPurchase(Order order, PromotionManager promotionManager,
                                                       Inventory inventory) {
        Optional<Order> giftOrder = createGiftOrder(order, promotionManager, inventory);
        handlePossibleBonusGift(order, promotionManager);
        if (giftOrder.isEmpty()) {
            handleNonPromotionalOrder(order.getPurchaseQuantity(), inventory);
            return giftOrder;
        }

        handleInventoryAdjustments(order, giftOrder, inventory);
        return giftOrder;
    }

    private void handlePossibleBonusGift(Order order, PromotionManager promotionManager) {
        Promotion promotion = promotionManager.getPromotion(order.getProduct()).orElse(null);
        if (promotion == null) {
            return;
        }

        int possibleGift = promotion.canGift(order.getPurchaseQuantity());
        if (possibleGift > 0 && promptGift(order.getProduct().getName(), possibleGift)) {
            order.plusQuantity(possibleGift);
        }
    }

    private Optional<Order> createGiftOrder(Order order, PromotionManager promotionManager, Inventory inventory) {
        int promotionQuantity = inventory.getMaxPromotionQuantity(order.getPurchaseQuantity());
        return promotionManager.createGiftOrder(order.getProduct(), promotionQuantity, inventory);
    }

    private void handleNonPromotionalOrder(int purchaseQuantity, Inventory inventory) {
        inventory.checkRegularInventory(purchaseQuantity);
        inventory.consumeRegularInventory(purchaseQuantity);
    }

    private void handleInventoryAdjustments(Order order, Optional<Order> giftOrder, Inventory inventory) {
        int regularQuantity = calculateRegularQuantity(order.getPurchaseQuantity(), giftOrder);
        handleRegularInventory(order.getProduct(), regularQuantity, inventory);
        consumeInventory(regularQuantity, giftOrder.map(Order::getPurchaseQuantity).orElse(0), inventory);
    }

    private int calculateRegularQuantity(int purchaseQuantity, Optional<Order> giftOrder) {
        return purchaseQuantity - giftOrder.map(Order::getPurchaseQuantity).orElse(0);
    }

    private void handleRegularInventory(Product product, int regularQuantity, Inventory inventory) {
        validateInventory(regularQuantity, inventory);
        if (regularQuantity > 0 && !promptPromotionLimit(product.getName(), regularQuantity)) {
            throw new InvalidOrderException(ErrorMessage.CANCLE_ORDER.getMessage());
        }
    }

    private void validateInventory(int regularQuantity, Inventory inventory) {
        inventory.checkRegularInventory(regularQuantity);
    }

    private void consumeInventory(int regularQuantity, int promotionQuantity, Inventory inventory) {
        inventory.consumePromotionalInventory(promotionQuantity);
        inventory.consumeRegularInventory(regularQuantity);
    }

    private boolean promptPromotionLimit(String name, int regularQuantity) {
        while (true) {
            try {
                YesNoRequest input = InputView.promotionLimit(name, regularQuantity);
                return input.response();
            } catch (StoreException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private boolean promptGift(String name, int bonus) {
        while (true) {
            try {
                YesNoRequest input = InputView.gift(name, bonus);
                return input.response();
            } catch (StoreException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private Inventory getInventory(Order order) {
        Product product = order.getProduct();
        return productInventory.get(product);
    }

    public Product findProductByName(String name) {
        return productInventory.keySet().stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<StoreResponse> getProductInventoryDetails(PromotionManager promotionManager) {
        return productInventory.entrySet().stream()
                .map(entry -> createStoreResponse(entry.getKey(), entry.getValue(), promotionManager))
                .collect(Collectors.toList());
    }

    private StoreResponse createStoreResponse(Product product, Inventory inventory, PromotionManager promotionManager) {
        String promotionName = promotionManager.getPromotionName(product);
        return StoreResponse.from(
                product,
                inventory.getPromotionalInventory(),
                inventory.getRegularInventory(),
                promotionName
        );
    }
}
