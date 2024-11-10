package store.common.exception;

public enum ErrorMessage {

    INVALID_ORDER_FORMAT("주문은 [사이다-2],[감자칩-1] 와 같은 형식으로 이루어져야 합니다."),
    INVALID_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    QUANTITY_MUST_BE_NUMBER("주문 수량은 숫자형식이여야 합니다."),
    QUANTITY_MUST_BE_POSITIVE_NUMBER("주문 수량은 0보다 커야 합니다."),
    INVALID_RESPONSE("응답은 Y 혹은 N으로 이루어져야 합니다."),
    INVALID_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    private final String message;
    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

}
