package com.example.micka.demosalefinder;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Handels Session objects through singelton method.
 */

public class Session {

    private static Session session;

   private List<Shop> storedShops = new ArrayList<>();
   private List<Member> storedMembers = new ArrayList<>();
   private Member member;
   private Shop shop;

    /**
     * Private constructor to prevent others
     * from creating Session-objects.
     */
    private Session() {

    }

    static {
        session = new Session();
    }

    public static Session getInstance() {
        return session;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member=member;
    }

    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop shop) {
        this.shop=shop;
    }

    public List<Shop> getStoredShops() { return storedShops; }

    public void setStoredShops(List<Shop> storedShops) { this.storedShops = storedShops; }

    public void setStoredMembers(List<Member> storedMembers) { this.storedMembers = storedMembers; }

    /**
     * Sets current member by email and password.
     */
    public void setCurrentMember(String email, String password) {
        for(int i = 0; i < storedMembers.size(); i++) {
            Member member = storedMembers.get(i);
            if(member.getEmail().equals(email) && member.getPassword().equals(password)) {
                setMember(member);
            }
        }
    }

    /**
     * Compares input with email from all members
     * in database and returns true if email exists.
     */
    public boolean checkIfEmailExists(String email) {
        boolean emailExist = false;

        for(int i = 0; i < storedMembers.size(); i++) {
            Member member = storedMembers.get(i);
            String tempEmail = member.getEmail();

            if (tempEmail.equals(email)) {
                emailExist = true;
            }
        }
        return emailExist;
    }

    /**
     * Compares input with passwords from all members
     * in database and returns true if password exists.
     */
    public boolean checkIfPasswordExists(String password) {
        boolean passwordExist = false;

        for(int i = 0; i < storedMembers.size(); i++) {
            Member member = storedMembers.get(i);
            String tempPassword = member.getPassword();

            if (tempPassword.equals(password)) {
                passwordExist = true;
            }
        }
        return passwordExist;
    }
}
