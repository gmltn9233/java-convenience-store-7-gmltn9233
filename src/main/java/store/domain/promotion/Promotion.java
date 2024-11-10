package store.domain.promotion;

public class Promotion {
    private final String name;
    private final PromotionDetails promotionDetails;
    PromotionTime promotionTime;

    public Promotion(String name, PromotionDetails promotionDetails, PromotionTime promotionTime) {
        this.name = name;
        this.promotionDetails = promotionDetails;
        this.promotionTime = promotionTime;
    }

    public int getPromotionBenefit(int purchaseQuantity) {
        if (promotionTime.validatePurchaseTime()) {
            return promotionDetails.getPromotionBonus(purchaseQuantity);
        }
        return 0;
    }

    public int getCriteria() {
        return promotionDetails.getCriteria();
    }

    public boolean isEligible(int purchaseQuantity) {
        return promotionDetails.isEligible(purchaseQuantity);
    }

    public String getName() {
        return name;
    }
}
