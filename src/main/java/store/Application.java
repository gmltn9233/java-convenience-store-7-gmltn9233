package store;

import store.common.config.StoreConfig;
import store.controller.StoreController;

public class Application {
    public static void main(String[] args) {
        StoreConfig storeConfig = new StoreConfig();
        StoreController storeController = storeConfig.provideStoreController();
        storeController.salesStart();
    }
}
