package com.example.micka.demosalefinder;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests implementation of Session class.
 */

public class SessionTest {

    private Session session;

    @Test
    public void testIfEmailExistsTrue() {

        //Arrange
        List<Member> storedMembers = new ArrayList<Member>();
        Member member = new Member(0, "","","test.email@email.com" ,"");
        storedMembers.add(member);
        member = new Member(1, "","","a.a@email.com" ,"");
        storedMembers.add(member);
        member = new Member(2, "","","b.b@email.com" ,"");
        storedMembers.add(member);

        Session.getInstance().setStoredMembers(storedMembers);

        //Act
        boolean emailExists = Session.getInstance().checkIfEmailExists("a.a@email.com");

        //Assert
        Assert.assertTrue(emailExists);
    }

    @Test
    public void testIfEmailExistsFalse() {

        //Arrange
        List<Member> storedMembers = new ArrayList<Member>();
        Member member = new Member(0, "","","test.email@email.com" ,"");
        storedMembers.add(member);
        member = new Member(1, "","","a.a@email.com" ,"");
        storedMembers.add(member);
        member = new Member(2, "","","b.b@email.com" ,"");
        storedMembers.add(member);

        Session.getInstance().setStoredMembers(storedMembers);

        //Act
        boolean emailExists = Session.getInstance().checkIfEmailExists("test.error@notexist.com");

        //Assert
        Assert.assertFalse(emailExists);
    }

    @Test
    public void testIfPasswordExistsTrue() {

        //Arrange
        List<Member> storedMembers = new ArrayList<Member>();
        Member member = new Member(0, "","","" ,"testPassword");
        storedMembers.add(member);
        member = new Member(1, "","","" ,"anOtherPassWord");
        storedMembers.add(member);
        member = new Member(2, "","","" ,"PasswOrdTesT");
        storedMembers.add(member);

        Session.getInstance().setStoredMembers(storedMembers);

        //Act
        boolean passwordExists = Session.getInstance().checkIfPasswordExists("testPassword");

        //Assert
        Assert.assertTrue(passwordExists);
    }

    @Test
    public void testIfPasswordExistsFalse() {

        //Arrange
        List<Member> storedMembers = new ArrayList<Member>();
        Member member = new Member(0, "","","" ,"testPassword");
        storedMembers.add(member);
        member = new Member(1, "","","" ,"anOtherPassWord");
        storedMembers.add(member);
        member = new Member(2, "","","" ,"PasswOrdTesT");
        storedMembers.add(member);

        Session.getInstance().setStoredMembers(storedMembers);

        //Act
        boolean passwordExists = Session.getInstance().checkIfPasswordExists("errorPassword");

        //Assert
        Assert.assertFalse(passwordExists);
    }
}
