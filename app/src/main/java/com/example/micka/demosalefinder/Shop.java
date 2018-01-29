package com.example.micka.demosalefinder;

/*
 * Declares variables for shop
 */

public class Shop {

    private int id;
    private String name;
    private String address;
    private int salePercentage;
    private ShopType shopType;
    private double latitude;
    private double longitude;
    private String website;

    public Shop (int id, String name, String address, int salePercentage, ShopType shopType, double latitude, double longitude, String website) {
        this.id=id;
        this.name=name;
        this.address=address;
        this.salePercentage=salePercentage;
        this.shopType=shopType;
        this.latitude=latitude;
        this.longitude=longitude;
        this.website=website;
    }

    public Shop (String name, String address, int salePercentage, ShopType shopType, double latitude, double longitude, String website) {
        this.name=name;
        this.address=address;
        this.salePercentage=salePercentage;
        this.shopType=shopType;
        this.latitude=latitude;
        this.longitude=longitude;
        this.website=website;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getAddress() { return address; }

    public int getSalePercentage() { return salePercentage; }

    public ShopType getShopType() { return shopType; }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public String getWebsite() { return website; }

    public String toString() {return id + " " + address + " " + name + " " + salePercentage + " " + shopType + " " + latitude + " " + longitude + " " + website; }

}
