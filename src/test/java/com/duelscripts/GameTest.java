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
        
        TurnResult result = game.executeTurn();
        
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
    
    @Test
    public void testRunFullCombat() {
        Fighter fighter1 = createTestFighter("Alice", 30);
        Fighter fighter2 = createTestFighter("Bob", 30);
        Game game = new Game(fighter1, fighter2);
        
        BattleResult result = game.runFullCombat();
        
        assertNotNull(result);
        assertTrue(result.getTotalTurns() > 0);
        assertTrue(result.getTotalTurns() <= game.getMaxTurns());
        assertNotNull(result.getTurnHistory());
        assertEquals(result.getTotalTurns(), result.getTurnHistory().size());
        assertNotNull(result.getStatistics());
        assertNotNull(result.getBattleSummary());
        
        // Either one fighter wins or it's a draw/timeout
        if (result.getWinner() != null) {
            assertTrue(result.getWinner().isAlive());
            assertFalse(result.reachedTurnLimit());
        }
    }
    
    @Test
    public void testRunFullCombatWithTurnLimit() {
        Fighter fighter1 = createTestFighter("Alice", 100); // High HP
        Fighter fighter2 = createTestFighter("Bob", 100);   // High HP
        Game game = new Game(fighter1, fighter2, 5, false); // Only 5 turns max
        
        BattleResult result = game.runFullCombat();
        
        assertNotNull(result);
        assertTrue(result.getTotalTurns() <= 5);
        
        // With high HP and only 5 turns, likely to reach turn limit
        if (result.reachedTurnLimit()) {
            assertEquals(5, result.getTotalTurns());
            assertNull(result.getWinner());
        }
    }
    
    @Test
    public void testBattleResultGetters() {
        Fighter fighter1 = createTestFighter("Alice", 20);
        Fighter fighter2 = createTestFighter("Bob", 20);
        Game game = new Game(fighter1, fighter2);
        
        BattleResult result = game.runFullCombat();
        
        // Test all getters are working
        assertNotNull(result.getTurnHistory());
        assertNotNull(result.getStatistics());
        assertNotNull(result.getBattleSummary());
        assertTrue(result.getTotalTurns() >= 0);
        
        // Winner can be null (draw/timeout) or a valid fighter
        if (result.getWinner() != null) {
            assertTrue(result.getWinner() == fighter1 || result.getWinner() == fighter2);
        }
    }
    
    @Test
    public void testGameMaxTurnsGetter() {
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        
        Game defaultGame = new Game(fighter1, fighter2);
        assertEquals(50, defaultGame.getMaxTurns());
        
        Game customGame = new Game(fighter1, fighter2, 25, false);
        assertEquals(25, customGame.getMaxTurns());
    }
    
    @Test
    public void testGameConstructors() {
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        
        // Test default constructor
        Game defaultGame = new Game(fighter1, fighter2);
        assertEquals(50, defaultGame.getMaxTurns());
        
        // Test parameterized constructor
        Game customGame = new Game(fighter1, fighter2, 10, true);
        assertEquals(10, customGame.getMaxTurns());
    }
}
