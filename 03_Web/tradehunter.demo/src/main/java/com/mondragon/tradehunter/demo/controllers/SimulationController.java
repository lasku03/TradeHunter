package com.mondragon.tradehunter.demo.controllers;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mondragon.tradehunter.demo.simulation.GraphValue;
import com.mondragon.tradehunter.demo.simulation.SimulationMaker;

@RestController
// @RequestMapping("")
public class SimulationController {

    SimulationMaker simulationMaker = new SimulationMaker();

    @GetMapping("/simulation/start")
    public void startSimulation() {

        simulationMaker.makeSimulation();
    }

    @GetMapping("/simulation/stop")
    public void stopSimulation() throws InterruptedException {

        simulationMaker.stopSimulation();
    }

    public static void sendValues(List<GraphValue> graphValues) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:1880/simulation/graph";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<GraphValue>> request = new HttpEntity<>(graphValues, headers);

        ResponseEntity<List<GraphValue>> response = restTemplate.exchange(url, HttpMethod.POST, request,
                new ParameterizedTypeReference<List<GraphValue>>() {
                });

        List<GraphValue> newValues = response.getBody();

        if (newValues == null){
            throw new InterruptedException("The list is null");
        }
    }

    public static double sendPredictionValues(List<GraphValue> graphValues) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:1880/simulation/predictionValues";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<GraphValue>> request = new HttpEntity<>(graphValues, headers);

        ResponseEntity<Double> response = restTemplate.exchange(url, HttpMethod.POST, request,
                Double.class);

        Double prediction = response.getBody();

        if (prediction == null){
            throw new InterruptedException("The prediction is null");
        }

        return prediction;
    }

}