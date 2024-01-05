package com.mondragon.tradehunter.demo.test_model;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

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

    @Test
    void testConstructor() {
        Message message = new Message();
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        Forum forum = new Forum();
        List<Forum> forums = new ArrayList<>();
        forums.add(forum);
        Search search = new Search();
        List<Search> searches = new ArrayList<>();
        searches.add(search);
        Prediction prediction = new Prediction();
        List<Prediction> predictions = new ArrayList<>();
        predictions.add(prediction);
        User user = new User(1, "Name", "Surname", "username", "password", "name.surname@gmail.com", 25, true, messages,
                forums, searches, predictions);
        assertEquals(1, user.getUserID());
        assertEquals("Name", user.getName());
        assertEquals("Surname", user.getSurname());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("name.surname@gmail.com", user.getEmail());
        assertEquals(25, user.getAge());
        assertTrue(user.isPremium());
        assertEquals(messages, user.getMessages());
        assertEquals(forums, user.getForums());
        assertEquals(searches, user.getSearches());
        assertEquals(predictions, user.getPredictions());
    }
}
