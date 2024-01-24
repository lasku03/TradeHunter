package com.mondragon.tradehunter.demo.test_model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.model.Prediction;
import com.mondragon.tradehunter.demo.model.User;

class PredictionTest {
        @Test
    void testPredictionIDGetterAndSetter() {
        Prediction prediction = new Prediction();
        prediction.setPredictionID(1);
        assertEquals(1, prediction.getPredictionID());
    }

    @Test
    void testValueGetterAndSetter() {
        Prediction prediction = new Prediction();
        prediction.setValue(42.0);
        assertEquals(42.0, prediction.getValue(), 0.01);
    }

    @Test
    void testConsultDateGetterAndSetter() {
        Prediction prediction = new Prediction();
        LocalDateTime now = LocalDateTime.now();
        prediction.setConsultDate(now);
        assertEquals(now, prediction.getConsultDate());
    }

    @Test
    void testPredictionDateGetterAndSetter() {
        Prediction prediction = new Prediction();
        LocalDateTime now = LocalDateTime.now();
        prediction.setPredictionDate(now);
        assertEquals(now, prediction.getPredictionDate());
    }

    @Test
    void testUserGetterAndSetter() {
        Prediction prediction = new Prediction();
        User user = new User();
        prediction.setUser(user);
        assertEquals(user, prediction.getUser());
    }

    @Test
    void testConstructor(){
        LocalDateTime consultDate = LocalDateTime.now();
        LocalDateTime predictionDate = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        User user = new User();
        Prediction prediction = new Prediction(1, 1000.0, consultDate, predictionDate, user);
        assertEquals(1, prediction.getPredictionID());
        assertEquals(1000.0, prediction.getValue(), 0.01);
        assertEquals(consultDate, prediction.getConsultDate());
        assertEquals(predictionDate, prediction.getPredictionDate());
        assertEquals(user, prediction.getUser());
    }
}
