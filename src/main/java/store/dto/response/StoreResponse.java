package store.dto.response;

import java.text.NumberFormat;
import java.util.Locale;
import store.domain.store.Product;

public record StoreResponse(
        String productName,
        int price,
        int promotionalInventory,
        int regularInventory,
        String promotionName
) {
    public static StoreResponse from(Product product, int promotionalInventory, int regularInventory,
                                     String promotionName) {
        return new StoreResponse(
                product.getName(),
                product.getPrice(),
                promotionalInventory,
                regularInventory,
                promotionName
        );
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);
        StringBuilder output = new StringBuilder();

        if (hasPromotion()) {
            appendPromotionalInventory(output, numberFormat);
        }
        appendRegularInventory(output, numberFormat);

        return output.toString().trim();
    }

    private boolean hasPromotion() {
        return promotionName != null && !promotionName.isEmpty() && promotionalInventory > 0;
    }

    private void appendPromotionalInventory(StringBuilder output, NumberFormat numberFormat) {
        output.append(formatInventoryLine(productName, price, promotionalInventory, promotionName, numberFormat));
    }

    private void appendRegularInventory(StringBuilder output, NumberFormat numberFormat) {
        if (regularInventory > 0) {
            output.append(formatInventoryLine(productName, price, regularInventory, null, numberFormat));
        } else {
            output.append(formatNoStockLine(productName, price, numberFormat));
        }
    }

    private String formatInventoryLine(String productName, int price, int inventory, String promotionName,
                                       NumberFormat numberFormat) {
        StringBuilder line = new StringBuilder();
        line.append("- ").append(productName).append(" ").append(numberFormat.format(price)).append("원 ")
                .append(inventory).append("개");
        if (promotionName != null && !promotionName.isEmpty()) {
            line.append(" ").append(promotionName);
        }
        line.append("\n");
        return line.toString();
    }

    private String formatNoStockLine(String productName, int price, NumberFormat numberFormat) {
        return "- " + productName + " " + numberFormat.format(price) + "원 재고 없음\n";
    }
}
