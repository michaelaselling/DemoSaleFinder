package com.example.micka.demosalefinder;

/**
 *Interface for MapActivity, AccountActivity and implements by DBShopStorage.
 */

import java.util.List;

public interface ShopStorage {

    List<Shop> getAllShops();

    List<Shop> storeAllShops(List<Shop> allShops);
}
