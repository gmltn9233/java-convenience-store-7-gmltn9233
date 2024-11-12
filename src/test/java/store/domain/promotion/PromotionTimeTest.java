package store.domain.promotion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromotionTimeTest {

    @Test
    @DisplayName("유효한 구매 시간 테스트")
    void validPurchaseTime() {
        LocalDateTime startDate = LocalDateTime.of(0, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(9999, 12, 31, 23, 59);
        PromotionTime promotionTime = new PromotionTime(startDate, endDate);

        boolean isValid = promotionTime.validatePurchaseTime();

        assertTrue(isValid);
    }

    @Test
    @DisplayName("유요하지 않은 구매 시간 테스트")
    void invalidPurchaseTime() {
        LocalDateTime startDate = LocalDateTime.of(0, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(0, 12, 31, 23, 59);
        PromotionTime promotionTime = new PromotionTime(startDate, endDate);

        boolean isValid = promotionTime.validatePurchaseTime();

        assertTrue(!isValid);
    }
}
