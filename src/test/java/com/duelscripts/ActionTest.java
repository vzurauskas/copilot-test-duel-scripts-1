package com.duelscripts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {
    
    @Test
    public void testActionCreation() {
        Action action = new Action(BodyPart.HEAD, BodyPart.TORSO);
        
        assertEquals(BodyPart.HEAD, action.getStrikeTarget());
        assertEquals(BodyPart.TORSO, action.getParryTarget());
    }
    
    @Test
    public void testToString() {
        Action action = new Action(BodyPart.LEGS, BodyPart.HEAD);
        String expected = "Strike: LEGS, Parry: HEAD";
        assertEquals(expected, action.toString());
    }
    
    @Test
    public void testSameBodyPartForStrikeAndParry() {
        // Should be allowed - a fighter can strike and parry the same body part
        Action action = new Action(BodyPart.TORSO, BodyPart.TORSO);
        assertEquals(BodyPart.TORSO, action.getStrikeTarget());
        assertEquals(BodyPart.TORSO, action.getParryTarget());
    }
}
