package store.view;

import java.util.List;
import store.dto.response.ReceiptResponse;
import store.dto.response.StoreResponse;

public class OutputView {
    public static void printStoreInventory(List<StoreResponse> storeResponses) {
        System.out.println("안녕하세요. w편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");

        for (StoreResponse response : storeResponses) {
            System.out.println(response.toString());
        }
        System.out.println();

    }

    public static void printReceipt(ReceiptResponse response) {
        System.out.println(response.toString());
    }

    public static void printError(String message) {
        System.out.println(message);
    }
}
