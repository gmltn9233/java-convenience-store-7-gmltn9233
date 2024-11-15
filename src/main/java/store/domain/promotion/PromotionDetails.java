package store.domain.promotion;

public class PromotionDetails {
    private final int buy;
    private final int bonus;

    public PromotionDetails(int buy, int bonus) {
        this.buy = buy;
        this.bonus = bonus;
    }

    public int getPromotionBonus(int purchaseQuantity) {
        int completeSets = purchaseQuantity / (buy + bonus);
        return completeSets * bonus;
    }

    public int canGift(int purchaseQuantity) {
        int completeSets = purchaseQuantity / (buy + bonus);
        if (purchaseQuantity < buy + bonus) {
            completeSets = purchaseQuantity;
        }
        if (completeSets + bonus == (buy + bonus)) {
            return bonus;
        }
        return 0;
    }

    public int getCriteria() {
        return buy + bonus;
    }

    public boolean isEligible(int purchaseQuantity) {
        return purchaseQuantity >= buy;
    }
}
