package com.mondragon.tradehunter.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Prediction")
@NoArgsConstructor
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int predictionID;

    private double value;
    private LocalDateTime consultDate;
    private LocalDateTime predictionDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID")
    private User user;

    public Prediction(int predictionID, double value, LocalDateTime consultDate, LocalDateTime predictionDate,
            User user) {
        this.predictionID = predictionID;
        this.value = value;
        this.consultDate = consultDate;
        this.predictionDate = predictionDate;
        this.user = user;
    }

    public int getPredictionID() {
        return predictionID;
    }

    public void setPredictionID(int predictionID) {
        this.predictionID = predictionID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getConsultDate() {
        return consultDate;
    }

    public void setConsultDate(LocalDateTime consultDate) {
        this.consultDate = consultDate;
    }

    public LocalDateTime getPredictionDate() {
        return predictionDate;
    }

    public void setPredictionDate(LocalDateTime predictionDate) {
        this.predictionDate = predictionDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
