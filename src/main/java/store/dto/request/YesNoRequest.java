package store.dto.request;

import store.common.exception.ErrorMessage;
import store.common.exception.InvalidResponseException;

public record YesNoRequest(boolean response) {
    public static YesNoRequest from(String input) {
        input = input.trim().toUpperCase();

        if (!input.equals("Y") && !input.equals("N")) {
            throw new InvalidResponseException(ErrorMessage.INVALID_RESPONSE.getMessage());
        }

        boolean response = input.equals("Y");
        return new YesNoRequest(response);
    }
}
