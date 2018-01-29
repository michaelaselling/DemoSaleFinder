package com.example.micka.demosalefinder;

import java.util.List;

/**
 *Interface for MainActivity, MemberActivity, AccountActivity and ShopActivity and implements by DBMemberStorage.
 */

public interface MemberStorage {

    Member storeMember (Member member);

    List<Member> getAllMembers();

    List<Shop> storeFavoriteShops (List<Shop> favoriteShops);

    List<Shop> getAllFavoriteShops();

    boolean deleteFavoriteShop(Shop shop);
}
