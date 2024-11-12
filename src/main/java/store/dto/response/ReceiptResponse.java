package store.dto.response;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public record ReceiptResponse(
        List<OrderDetail> productOrders,
        List<OrderDetail> giftOrders,
        int totalQuantity,
        int totalPurchaseAmount,
        int promotionalDiscount,
        int membershipDiscount,
        int totalToPay
) {
    public static ReceiptResponse from(
            List<OrderDetail> productOrders,
            List<OrderDetail> giftOrders,
            int totalQuantity,
            int totalPurchaseAmount,
            int promotionalDiscount,
            int membershipDiscount
    ) {
        int totalToPay = totalPurchaseAmount - promotionalDiscount - membershipDiscount;
        return new ReceiptResponse(
                productOrders,
                giftOrders,
                totalQuantity,
                totalPurchaseAmount,
                promotionalDiscount,
                membershipDiscount,
                totalToPay
        );
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);

        StringBuilder output = new StringBuilder();
        output.append("\n");
        output.append(formatHeader());
        output.append(formatProductOrders(numberFormat));
        output.append(formatGiftOrders());
        output.append(formatFooter(numberFormat));

        return output.toString();
    }

    private String formatHeader() {
        return "==============W 편의점================\n" +
                "상품명\t\t수량\t\t금액\n";
    }

    private String formatProductOrders(NumberFormat numberFormat) {
        StringBuilder productOutput = new StringBuilder();
        for (OrderDetail order : productOrders) {
            productOutput.append(String.format("- %s\t\t%d\t%s\n",
                    order.productName(),
                    order.quantity(),
                    numberFormat.format(order.totalPrice())));
        }
        return productOutput.toString();
    }

    private String formatGiftOrders() {
        if (giftOrders.isEmpty()) {
            return "";
        }
        StringBuilder giftOutput = new StringBuilder();
        giftOutput.append("=============증\t정===============\n");
        for (OrderDetail gift : giftOrders) {
            giftOutput.append(String.format("- %s\t\t%d\n",
                    gift.productName(),
                    gift.quantity()));
        }
        return giftOutput.toString();
    }

    private String formatFooter(NumberFormat numberFormat) {
        return String.format(
                "====================================\n" +
                        "총구매액\t\t%d\t%s\n" +
                        "행사할인\t\t\t-%s\n" +
                        "멤버십할인\t\t-%s\n" +
                        "내실돈\t\t\t%s\n\n",
                totalQuantity,
                numberFormat.format(totalPurchaseAmount),
                numberFormat.format(promotionalDiscount),
                numberFormat.format(membershipDiscount),
                numberFormat.format(totalToPay)
        );
    }

    public record OrderDetail(String productName, int quantity, int totalPrice) {
        public static OrderDetail from(String productName, int quantity, int pricePerUnit) {
            return new OrderDetail(productName, quantity, quantity * pricePerUnit);
        }
    }
}
