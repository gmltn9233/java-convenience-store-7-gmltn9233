package store;

import java.io.IOException;
import store.common.config.StoreConfig;
import store.controller.StoreController;

public class Application {
    public static void main(String[] args) throws IOException {
        StoreConfig storeConfig = new StoreConfig();
        StoreController storeController = storeConfig.provideStoreController();
        storeController.salesStart();
    }
}
