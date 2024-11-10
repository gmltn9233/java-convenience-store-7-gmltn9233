package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.dto.request.OrdersRequest;
import store.dto.request.YesNoRequest;

public class InputView {

    public static OrdersRequest Orders() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return OrdersRequest.from(input());
    }


    private static String input() {
        return Console.readLine();
    }

}
