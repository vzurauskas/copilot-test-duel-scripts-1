package com.duelscripts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FighterTest {
    
    private Fighter createTestFighter() {
        Weapon sword = new Weapon("Test Sword", 10, 0.1);
        return new Fighter("Test Fighter", 50, 8, sword);
    }
    
    @Test
    public void testFighterCreation() {
        Fighter fighter = createTestFighter();
        
        assertEquals("Test Fighter", fighter.getName());
        assertEquals(50, fighter.getHitPoints());
        assertEquals(50, fighter.getMaxHitPoints());
        assertEquals(8, fighter.getStrength());
        assertEquals("Test Sword", fighter.getWeapon().getName());
    }
    
    @Test
    public void testIsAlive() {
        Fighter fighter = createTestFighter();
        
        assertTrue(fighter.isAlive());
        
        fighter.takeDamage(25);
        assertTrue(fighter.isAlive());
        assertEquals(25, fighter.getHitPoints());
        
        fighter.takeDamage(25);
        assertFalse(fighter.isAlive());
        assertEquals(0, fighter.getHitPoints());
    }
    
    @Test
    public void testTakeDamage() {
        Fighter fighter = createTestFighter();
        
        fighter.takeDamage(15);
        assertEquals(35, fighter.getHitPoints());
        
        // Test overkill damage doesn't go below 0
        fighter.takeDamage(100);
        assertEquals(0, fighter.getHitPoints());
    }
    
    @Test
    public void testGetAction() {
        Fighter fighter = createTestFighter();
        Action action = fighter.getAction();
        
        // For this iteration, all fighters use the same hardcoded action
        assertEquals(BodyPart.HEAD, action.getStrikeTarget());
        assertEquals(BodyPart.TORSO, action.getParryTarget());
    }
    
    @Test
    public void testToString() {
        Fighter fighter = createTestFighter();
        String expected = "Test Fighter (HP: 50/50, Str: 8, Weapon: Test Sword)";
        assertEquals(expected, fighter.toString());
        
        fighter.takeDamage(20);
        String expectedAfterDamage = "Test Fighter (HP: 30/50, Str: 8, Weapon: Test Sword)";
        assertEquals(expectedAfterDamage, fighter.toString());
    }
}
