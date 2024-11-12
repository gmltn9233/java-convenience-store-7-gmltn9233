package store.service;

import java.util.List;
import java.util.stream.Collectors;
import store.common.exception.ErrorMessage;
import store.common.exception.InvalidOrderException;
import store.domain.order.Order;
import store.domain.order.Orders;
import store.domain.order.Receipt;
import store.domain.promotion.PromotionManager;
import store.domain.store.ConvenienceStore;
import store.domain.store.Product;
import store.domain.store.StoreInventory;
import store.dto.request.OrdersRequest;
import store.dto.response.ReceiptResponse;
import store.dto.response.ReceiptResponse.OrderDetail;
import store.dto.response.StoreResponse;

public class StoreService {

    private final ConvenienceStore convenienceStore;

    public StoreService(ConvenienceStore convenienceStore) {
        this.convenienceStore = convenienceStore;
    }

    public Orders generateOrders(OrdersRequest ordersRequest) {
        StoreInventory storeInventory = convenienceStore.getStoreInventory();

        List<Order> orders = ordersRequest.orders().entrySet().stream()
                .map(entry -> generateOrder(storeInventory, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new Orders(orders);
    }

    private Order generateOrder(StoreInventory storeInventory, String productName, int quantity) {
        Product product = storeInventory.findProductByName(productName);

        if (product == null) {
            throw new InvalidOrderException(ErrorMessage.INVALID_PRODUCT.getMessage());
        }

        return new Order(product, quantity);
    }

    public List<StoreResponse> generateStoreResponses() {
        StoreInventory storeInventory = convenienceStore.getStoreInventory();
        PromotionManager promotionManager = convenienceStore.getPromotionManager();
        return storeInventory.getProductInventoryDetails(promotionManager);
    }


    public ReceiptResponse generateReceiptResponse(Receipt receipt) {
        List<OrderDetail> productOrderDetails = receipt.getProductOrders().toOrderDetails();
        List<OrderDetail> giftOrderDetails = receipt.getGiftOrders().toGiftOrderDetails();

        int totalQuantity = receipt.getProductOrders().getTotalQuantity();
        int totalPurchaseAmount = receipt.getProductOrders().getTotalPurchaseAmount();
        int promotionalDiscount = receipt.getGiftOrders().getPromotionalDiscount();
        int membershipDiscount = receipt.calculateMembershipDiscount();

        return ReceiptResponse.from(
                productOrderDetails,
                giftOrderDetails,
                totalQuantity,
                totalPurchaseAmount,
                promotionalDiscount,
                membershipDiscount
        );
    }


    public Receipt generateReceipt(Orders orders) {
        return convenienceStore.purchaseProducts(orders);
    }
}
