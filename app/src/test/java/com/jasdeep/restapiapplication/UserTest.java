package com.jasdeep.restapiapplication;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserTest {

    private User getUser() {
        return new User(1, "User", "Username", "Password");
    }


    @Test
    public void constructorTest() {
        User user = null;
        assertNull(user);
        user = new User(1, "User", "Username", "Password");
        assertNotNull(user);
    }

    @Test
    public void getUserIdTest() {
        assertEquals(1, getUser().getUserId());
    }

    @Test
    public void setUserIdTest() {
        User user = getUser();
        assertEquals(1, user.getUserId());

        Random random = new Random();
        int r = random.nextInt(5000);

        assertNotEquals(r, user.getUserId());
        user.setUserId(r);

        assertNotEquals(1, user.getUserId());
        assertEquals(r, user.getUserId());
    }

    @Test
    public void getNameTest() {
        assertEquals("User", getUser().getName());
    }

    @Test
    public void setNameTest() {
        User user = getUser();
        assertEquals("User", user.getName());

        Random random = new Random();
        String s = Integer.toString(random.nextInt());

        assertNotEquals(s, user.getName());
        user.setName(s);

        assertNotEquals("User", user.getName());
        assertEquals(s, user.getName());
    }

    @Test
    public void getUsernameTest() {
        assertEquals("Username", getUser().getUsername());
    }

    @Test
    public void setUsernameTest() {
        User user = getUser();
        assertEquals("Username", user.getUsername());

        Random random = new Random();
        String s = Integer.toString(random.nextInt());

        assertNotEquals(s, user.getUsername());
        user.setUsername(s);

        assertNotEquals("Username", user.getUsername());
        assertEquals(s, user.getUsername());
    }

    @Test
    public void getPasswordTest() {
        assertEquals("Password", getUser().getPassword());
    }

    @Test
    public void setPasswordTest() {
        User user = getUser();
        assertEquals("Password", user.getPassword());

        Random random = new Random();
        String s = Integer.toString(random.nextInt());

        assertNotEquals(s, user.getPassword());
        user.setPassword(s);

        assertNotEquals("Password", user.getPassword());
        assertEquals(s, user.getPassword());
    }
}