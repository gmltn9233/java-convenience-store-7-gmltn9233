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
}
