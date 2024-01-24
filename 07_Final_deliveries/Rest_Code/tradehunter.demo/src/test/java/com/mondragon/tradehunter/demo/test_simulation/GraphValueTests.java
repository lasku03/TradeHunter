package com.mondragon.tradehunter.demo.test_simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.simulation.GraphValue;

class GraphValueTests {
    
    @Test
    void testConstructor() {
        String expectedName = "TestName";
        double expectedValue = 42.0;

        GraphValue graphValue = new GraphValue(expectedName, expectedValue);

        assertEquals(expectedName, graphValue.getName());
        assertEquals(expectedValue, graphValue.getValue(), 0.001);
    }

    @Test
    void testNameSetterAndGetter() {
        GraphValue graphValue = new GraphValue("InitialName", 10.0);

        String newName = "NewName";
        graphValue.setName(newName);

        assertEquals(newName, graphValue.getName());
    }

    @Test
    void testValueSetterAndGetter() {
        GraphValue graphValue = new GraphValue("InitialName", 10.0);

        double newValue = 20.0;
        graphValue.setValue(newValue);

        assertEquals(newValue, graphValue.getValue(), 0.001);
    }
}
