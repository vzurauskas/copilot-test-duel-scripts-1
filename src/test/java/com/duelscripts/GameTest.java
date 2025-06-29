package com.duelscripts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    
    private Fighter createTestFighter(String name, int hp) {
        Weapon weapon = new Weapon("Test Weapon", 10, 0.1);
        return new Fighter(name, hp, 5, weapon);
    }
    
    @Test
    public void testGameCreation() {
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        
        Game game = new Game(fighter1, fighter2);
        
        assertEquals(fighter1, game.getFighter1());
        assertEquals(fighter2, game.getFighter2());
    }
    
    @Test
    public void testExecuteTurn() {
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        Game game = new Game(fighter1, fighter2);
        
        CombatResolver.TurnResult result = game.executeTurn();
        
        assertNotNull(result);
        assertNotNull(result.getDescription());
        assertTrue(result.getDescription().contains("Alice"));
        assertTrue(result.getDescription().contains("Bob"));
    }
    
    @Test
    public void testIsGameOverAndGetWinner() {
        Fighter fighter1 = createTestFighter("Alice", 1); // Low HP
        Fighter fighter2 = createTestFighter("Bob", 50);
        Game game = new Game(fighter1, fighter2);
        
        // Initially, game should not be over
        assertFalse(game.isGameOver());
        assertNull(game.getWinner());
        
        // Execute turns until one fighter dies
        int maxTurns = 10; // Safety limit
        for (int i = 0; i < maxTurns && !game.isGameOver(); i++) {
            game.executeTurn();
        }
        
        // Game should be over now
        assertTrue(game.isGameOver());
        
        // There should be a winner
        Fighter winner = game.getWinner();
        assertNotNull(winner);
        assertTrue(winner.isAlive());
    }
    
    @Test
    public void testBothFightersDefeated() {
        // Create fighters with very low HP to simulate mutual defeat
        Fighter fighter1 = createTestFighter("Alice", 1);
        Fighter fighter2 = createTestFighter("Bob", 1);
        
        // Manually reduce both to 0 HP to test this scenario
        fighter1.takeDamage(1);
        fighter2.takeDamage(1);
        
        Game game = new Game(fighter1, fighter2);
        
        assertTrue(game.isGameOver());
        assertNull(game.getWinner()); // No winner when both are dead
    }
    
    @Test
    public void testGameNotOverWhenBothAlive() {
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        Game game = new Game(fighter1, fighter2);
        
        assertFalse(game.isGameOver());
        assertNull(game.getWinner());
        
        // Execute one turn - both should still be alive
        game.executeTurn();
        
        // Game likely continues (unless very unlucky with damage)
        if (fighter1.isAlive() && fighter2.isAlive()) {
            assertFalse(game.isGameOver());
            assertNull(game.getWinner());
        }
    }
}
