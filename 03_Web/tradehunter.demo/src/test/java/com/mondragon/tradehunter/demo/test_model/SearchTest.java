package com.mondragon.tradehunter.demo.test_model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.model.Search;
import com.mondragon.tradehunter.demo.model.User;

class SearchTest {
        @Test
    void testSearchIDGetterAndSetter() {
        Search search = new Search();
        search.setSearchID(1);
        assertEquals(1, search.getSearchID());
    }

    @Test
    void testInitialDateGetterAndSetter() {
        Search search = new Search();
        LocalDateTime now = LocalDateTime.now();
        search.setInitialDate(now);
        assertEquals(now, search.getInitialDate());
    }

    @Test
    void testFinalDateGetterAndSetter() {
        Search search = new Search();
        LocalDateTime now = LocalDateTime.now();
        search.setFinalDate(now);
        assertEquals(now, search.getFinalDate());
    }

    @Test
    void testFavoriteGetterAndSetter() {
        Search search = new Search();
        search.setFavorite(true);
        assertTrue(search.isFavorite());
    }

    @Test
    void testUserGetterAndSetter() {
        Search search = new Search();
        User user = new User();
        search.setUser(user);
        assertEquals(user, search.getUser());
    }
}
