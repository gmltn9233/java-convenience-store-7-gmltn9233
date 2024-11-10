package store.common.exception;

public enum ErrorMessage {

    INVALID_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    private final String message;
    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

}
