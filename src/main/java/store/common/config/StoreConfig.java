package store.common.config;

import java.io.IOException;
import store.common.Loader.PromotionLoader;
import store.common.Loader.StoreLoader;
import store.common.exception.ErrorMessage;
import store.controller.StoreController;
import store.domain.store.ConvenienceStore;
import store.service.StoreService;
import store.view.OutputView;

public class StoreConfig {
    public StoreController provideStoreController() {
        try {
            PromotionLoader promotionLoader = new PromotionLoader();
            StoreLoader storeLoader = new StoreLoader(promotionLoader);
            ConvenienceStore convenienceStore = storeLoader.createConvenienceStore();
            StoreService storeService = new StoreService(convenienceStore);
            return new StoreController(storeService);
        } catch (IOException e) {
            OutputView.printError(ErrorMessage.READ_FILE_ERROR.getMessage());
            return null;
        }
    }
}
