package com.example.micka.demosalefinder;

/**
* Holds all valid shoptypes
 */

public enum ShopType {

    CLOTHES, SHOES, ELECTRONICS;

    /**
     * Converts Enum to String and checks shoptype.
     */
    public static String checkShopType(Enum sType) {

        String shopType = sType.toString();
        String clothes = ShopType.CLOTHES.name();
        String shoes = ShopType.SHOES.name();
        String electronics = ShopType.ELECTRONICS.name();

        if (shopType.equals(clothes)) {
           return clothes;
        } if ( shopType.equals(shoes)) {
            return shoes;
        } if ( shopType.equals(electronics)) {
            return electronics;
        } else {
            return null;
        }
    }
}
