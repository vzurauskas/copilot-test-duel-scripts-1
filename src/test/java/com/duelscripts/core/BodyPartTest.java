package com.duelscripts.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BodyPartTest {
    
    @Test
    public void testDamageMultipliers() {
        assertEquals(1.5, BodyPart.HEAD.getDamageMultiplier(), 0.001);
        assertEquals(1.0, BodyPart.TORSO.getDamageMultiplier(), 0.001);
        assertEquals(0.7, BodyPart.LEGS.getDamageMultiplier(), 0.001);
    }
    
    @Test
    public void testEnumValues() {
        BodyPart[] parts = BodyPart.values();
        assertEquals(3, parts.length);
        assertEquals(BodyPart.HEAD, parts[0]);
        assertEquals(BodyPart.TORSO, parts[1]);
        assertEquals(BodyPart.LEGS, parts[2]);
    }
}
