package store.controller;

import java.util.List;
import store.common.exception.StoreException;
import store.domain.order.Orders;
import store.domain.order.Receipt;
import store.dto.request.OrdersRequest;
import store.dto.request.YesNoRequest;
import store.dto.response.ReceiptResponse;
import store.dto.response.StoreResponse;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    public void salesStart() {
        displayStoreInventory();
        Receipt receipt = createReceiptWithMembership();
        displayReceipt(receipt);
        if (requestRepurchase().response()) {
            salesStart();
        }
    }

    private Receipt createReceiptWithMembership() {
        Receipt receipt = getReceipt();
        if (requestMembership().response()) {
            receipt.applyMembership();
        }
        return receipt;
    }

    private Receipt getReceipt() {
        while (true) {
            try {
                OrdersRequest userOrders = InputView.Orders();
                Orders orders = storeService.generateOrders(userOrders);
                return storeService.generateReceipt(orders);
            } catch (StoreException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private void displayReceipt(Receipt receipt) {
        ReceiptResponse response = storeService.generateReceiptResponse(receipt);
        OutputView.printReceipt(response);
    }

    private void displayStoreInventory() {
        List<StoreResponse> storeResponses = storeService.generateStoreResponses();
        OutputView.printStoreInventory(storeResponses);
    }

    private YesNoRequest requestMembership() {
        return requestYesNO(InputView::membership);
    }

    private YesNoRequest requestRepurchase() {
        return requestYesNO(InputView::repurchase);
    }

    private YesNoRequest requestYesNO(InputSupplier inputSupplier) {
        while (true) {
            try {
                return inputSupplier.get();
            } catch (StoreException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    @FunctionalInterface
    private interface InputSupplier {
        YesNoRequest get() throws StoreException;
    }
}
