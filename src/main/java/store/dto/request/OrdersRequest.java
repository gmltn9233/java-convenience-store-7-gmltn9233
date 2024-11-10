package store.dto.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.common.exception.ErrorMessage;
import store.common.exception.InvalidOrderException;

public record OrdersRequest(Map<String, Integer> orders) {

    public static OrdersRequest from(String input) {
        String cleanedInput = cleanInput(input);
        List<String> items = splitItems(cleanedInput);

        Map<String, Integer> resultMap = parseItems(items);

        return new OrdersRequest(resultMap);
    }

    private static String cleanInput(String input) {
        return input.replaceAll("\\[|\\]", "").trim();
    }

    private static List<String> splitItems(String input) {
        return List.of(input.split(","));
    }

    private static Map<String, Integer> parseItems(List<String> items) {
        Map<String, Integer> resultMap = new HashMap<>();

        for (String item : items) {
            List<String> parts = splitParts(item);
            String productName = parts.get(0).trim();
            int quantity = parseQuantity(parts.get(1).trim());
            resultMap.put(productName, quantity);
        }

        return resultMap;
    }

    private static List<String> splitParts(String item) {
        List<String> parts = List.of(item.split("-"));
        if (parts.size() != 2) {
            throw new InvalidOrderException(ErrorMessage.INVALID_ORDER_FORMAT.getMessage());
        }
        return parts;
    }

    private static int parseQuantity(String quantityString) {
        try {
            int quantity = Integer.parseInt(quantityString);
            if (quantity <= 0) {
                throw new InvalidOrderException(ErrorMessage.QUANTITY_MUST_BE_POSITIVE_NUMBER.getMessage());
            }
            return quantity;
        } catch (NumberFormatException e) {
            throw new InvalidOrderException(ErrorMessage.QUANTITY_MUST_BE_NUMBER.getMessage());
        }
    }
}
