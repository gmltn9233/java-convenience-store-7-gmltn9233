package store.common.Loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import store.common.exception.ErrorMessage;
import store.common.exception.StoreException;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionDetails;
import store.domain.promotion.PromotionTime;

public class PromotionLoader {

    private static final String FILE_PATH = "src/main/resources/promotions.md";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int EXPECTED_FIELD_COUNT = 5;

    public List<Promotion> loadPromotions() throws IOException {
        List<String> lines = readFileLines(FILE_PATH);
        return parsePromotions(lines);
    }

    private List<String> readFileLines(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    private List<Promotion> parsePromotions(List<String> lines) {
        List<Promotion> promotions = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (!line.isEmpty()) {
                try {
                    promotions.add(parsePromotionLine(line));
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid promotion data at line " + (i + 1) + ": " + e.getMessage());
                }
            }
        }
        return promotions;
    }

    private Promotion parsePromotionLine(String line) {
        List<String> values = List.of(line.split(","));
        if (values.size() != EXPECTED_FIELD_COUNT) {
            throw new IllegalArgumentException("Invalid number of fields: " + values.size());
        }

        String name = values.get(0).trim();
        int buy = parseInteger(values.get(1).trim(), "Buy quantity");
        int get = parseInteger(values.get(2).trim(), "Get quantity");
        LocalDateTime startDate = parseDate(values.get(3).trim(), true);
        LocalDateTime endDate = parseDate(values.get(4).trim(), false);

        return createPromotion(name, buy, get, startDate, endDate);
    }

    private int parseInteger(String value, String fieldName) {
        try {
            int result = Integer.parseInt(value);
            if (result <= 0) {
                throw new StoreException(ErrorMessage.MUST_BE_POSITIVE_INTEGER.getMessage());
            }
            return result;
        } catch (NumberFormatException e) {
            throw new StoreException(ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
        }
    }

    private LocalDateTime parseDate(String date, boolean isStartDate) {
        LocalDate localDate = LocalDate.parse(date, DATE_FORMATTER);
        return isStartDate ? localDate.atStartOfDay() : localDate.atTime(23, 59, 59);
    }

    private Promotion createPromotion(String name, int buy, int get, LocalDateTime startDate, LocalDateTime endDate) {
        PromotionTime promotionTime = new PromotionTime(startDate, endDate);
        PromotionDetails promotionDetails = new PromotionDetails(buy, get);
        return new Promotion(name, promotionDetails, promotionTime);
    }
}
