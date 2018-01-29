package com.example.micka.demosalefinder;

import java.util.ArrayList;
import java.util.List;

/*
 * Declares variables for member
 */

public class Member {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Shop> favoriteShops = new ArrayList<Shop>();

    public Member (int id, String firstName, String lastName, String email, String password) {
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
    }

    public Member (String firstName, String lastName, String email, String password) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
    }

    public int getId() { return id; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {return password; }

    public List<Shop> getFavoriteShops() { return favoriteShops; }

    public void setFavoriteShops(List<Shop> myFavoriteShops) { this.favoriteShops=myFavoriteShops; }

    public String toString() {return id + " " + firstName + " " + lastName + " " + email + " " + password; }

    public void addFavoriteShop(Shop shop) {
        favoriteShops.add(shop);
    }

    public void removeFavoriteShop(Shop shop) {
        favoriteShops.remove(shop);
    }
}
