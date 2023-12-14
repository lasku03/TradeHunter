package com.mondragon.tradehunter.demo.test_model;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mondragon.tradehunter.demo.model.Forum;
import com.mondragon.tradehunter.demo.model.Prediction;
import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.model.Message;
import com.mondragon.tradehunter.demo.model.Search;

class UserTest {
    @Test
    void testUserIDGetterAndSetter() {
        User user = new User();
        user.setUserID(1);
        assertEquals(1, user.getUserID());
    }

    @Test
    void testNameGetterAndSetter() {
        User user = new User();
        user.setName("Name");
        assertEquals("Name", user.getName());
    }

    @Test
    void testSurnameGetterAndSetter() {
        User user = new User();
        user.setSurname("Surname");
        assertEquals("Surname", user.getSurname());
    }

    @Test
    void testUsernameGetterAndSetter() {
        User user = new User();
        user.setUsername("name.surname");
        assertEquals("name.surname", user.getUsername());
    }

    @Test
    void testPasswordGetterAndSetter() {
        User user = new User();
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testEmailGetterAndSetter() {
        User user = new User();
        user.setEmail("name.surname@email.com");
        assertEquals("name.surname@email.com", user.getEmail());
    }

    @Test
    void testAgeGetterAndSetter() {
        User user = new User();
        user.setAge(25);
        assertEquals(25, user.getAge());
    }

    @Test
    void testPremiumGetterAndSetter() {
        User user = new User();
        user.setPremium(true);
        assertTrue(user.isPremium());
    }

    @Test
    void testMessagesGetterAndSetter() {
        User user = new User();
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        messages.add(message);
        user.setMessages(messages);
        assertEquals(messages, user.getMessages());
    }

    @Test
    void testForumsGetterAndSetter() {
        User user = new User();
        List<Forum> forums = new ArrayList<>();
        Forum forum = new Forum();
        forums.add(forum);
        user.setForums(forums);
        assertEquals(forums, user.getForums());
    }

    @Test
    void testSearchesGetterAndSetter() {
        User user = new User();
        List<Search> searches = new ArrayList<>();
        Search search = new Search();
        searches.add(search);
        user.setSearches(searches);
        assertEquals(searches, user.getSearches());
    }

    @Test
    void testPredictionsGetterAndSetter() {
        User user = new User();
        List<Prediction> predictions = new ArrayList<>();
        Prediction prediction = new Prediction();
        predictions.add(prediction);
        user.setPredictions(predictions);
        assertEquals(predictions, user.getPredictions());
    }
}
