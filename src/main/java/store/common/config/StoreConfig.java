package store.common.config;

import java.io.IOException;
import store.common.Loader.PromotionLoader;
import store.common.Loader.StoreLoader;
import store.controller.StoreController;
import store.domain.store.ConvenienceStore;
import store.service.StoreService;

public class StoreConfig {
    public StoreController provideStoreController() throws IOException {
        PromotionLoader promotionLoader = new PromotionLoader();
        StoreLoader storeLoader = new StoreLoader(promotionLoader);
        ConvenienceStore convenienceStore = storeLoader.createConvenienceStore();
        StoreService storeService = new StoreService(convenienceStore);

        return new StoreController(storeService);
    }
}
