package store.domain.promotion;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;

public class PromotionTime {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public PromotionTime(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean validatePurchaseTime() {
        LocalDateTime purchaseTime = DateTimes.now();
        return (validateTimeRange(purchaseTime));
    }

    private boolean validateTimeRange(LocalDateTime time) {
        return !time.isBefore(startDate) && !time.isAfter(endDate);
    }
}
