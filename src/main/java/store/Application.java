package store;

import java.io.IOException;
import store.common.config.StoreConfig;
import store.common.exception.ErrorMessage;
import store.controller.StoreController;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        try {
            StoreConfig storeConfig = new StoreConfig();
            StoreController storeController = storeConfig.provideStoreController();
            storeController.salesStart();
        } catch (IOException e) {
            OutputView.printError(ErrorMessage.READ_FILE_ERROR.getMessage());
        } catch (Exception e) {
            OutputView.printError(ErrorMessage.UNEXPECTED_ERROR.getMessage());
        }
    }
}
