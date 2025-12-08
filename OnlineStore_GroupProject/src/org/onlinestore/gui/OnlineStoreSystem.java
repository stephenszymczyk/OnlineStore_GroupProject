package org.onlinestore.gui;

import org.onlinestore.software.OnlineStore;

public class OnlineStoreSystem {

    public static void main(String[] args) {

        //loads online store data
        OnlineStore store = OnlineStore.loadOnlineStore();

        //launch GUI
        new MainHomePage(store);
    }
}