package com.meizu.flyme.calendar.subcription_new.recommend.basecard;

import android.support.annotation.NonNull;
import java.util.ArrayList;

public final class ItemTypePool {

    private static ArrayList<Class<? extends ItemContent>> contents = new ArrayList<>();
    private static ArrayList<ItemViewProvider> providers = new ArrayList<>();


    public synchronized static void register(
        Class<? extends ItemContent> itemContent, ItemViewProvider provider) {
        contents.add(itemContent);
        providers.add(provider);
    }


    public static ArrayList<Class<? extends ItemContent>> getContents() {
        return contents;
    }


    public static ArrayList<ItemViewProvider> getProviders() {
        return providers;
    }


    public static ItemViewProvider getProviderByIndex(int index) {
        return providers.get(index);
    }

}
