package com.duelscripts.combat;

import com.duelscripts.core.Fighter;
import com.duelscripts.core.Weapon;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class BattleResultTest {
    
    private Fighter createTestFighter(String name, int hp) {
        Weapon weapon = new Weapon("Test Weapon", 10, 0.1);
        return new Fighter(name, hp, 5, weapon);
    }
    
    @Test
    public void testBattleResultWithWinner() {
        Fighter winner = createTestFighter("Alice", 30);
        Fighter loser = createTestFighter("Bob", 0);
        
        List<TurnResult> turnHistory = new ArrayList<>();
        BattleStats stats = new BattleStats(25, 15, 3, 2, 1, 0, 10.0);
        String summary = "Alice wins!";
        
        BattleResult result = new BattleResult(winner, 4, turnHistory, stats, summary, false);
        
        assertEquals(winner, result.getWinner());
        assertEquals(4, result.getTotalTurns());
        assertEquals(turnHistory, result.getTurnHistory());
        assertEquals(stats, result.getStatistics());
        assertEquals(summary, result.getBattleSummary());
        assertFalse(result.reachedTurnLimit());
    }
    
    @Test
    public void testBattleResultWithTimeout() {
        Fighter fighter1 = createTestFighter("Alice", 20);
        Fighter fighter2 = createTestFighter("Bob", 25);
        
        List<TurnResult> turnHistory = new ArrayList<>();
        BattleStats stats = new BattleStats(10, 8, 2, 1, 0, 0, 3.6);
        String summary = "Battle timeout!";
        
        BattleResult result = new BattleResult(null, 5, turnHistory, stats, summary, true);
        
        assertNull(result.getWinner());
        assertEquals(5, result.getTotalTurns());
        assertEquals(turnHistory, result.getTurnHistory());
        assertEquals(stats, result.getStatistics());
        assertEquals(summary, result.getBattleSummary());
        assertTrue(result.reachedTurnLimit());
    }
    
    @Test
    public void testBattleResultWithDraw() {
        Fighter fighter1 = createTestFighter("Alice", 0);
        Fighter fighter2 = createTestFighter("Bob", 0);
        
        List<TurnResult> turnHistory = new ArrayList<>();
        BattleStats stats = new BattleStats(30, 35, 4, 5, 2, 1, 21.7);
        String summary = "Both fighters have fallen!";
        
        BattleResult result = new BattleResult(null, 3, turnHistory, stats, summary, false);
        
        assertNull(result.getWinner());
        assertEquals(3, result.getTotalTurns());
        assertEquals(turnHistory, result.getTurnHistory());
        assertEquals(stats, result.getStatistics());
        assertEquals(summary, result.getBattleSummary());
        assertFalse(result.reachedTurnLimit());
    }
    
    @Test
    public void testBattleResultEmptyTurnHistory() {
        Fighter winner = createTestFighter("Alice", 50);
        List<TurnResult> emptyHistory = new ArrayList<>();
        BattleStats stats = new BattleStats(0, 0, 0, 0, 0, 0, 0.0);
        String summary = "Instant victory!";
        
        BattleResult result = new BattleResult(winner, 0, emptyHistory, stats, summary, false);
        
        assertEquals(winner, result.getWinner());
        assertEquals(0, result.getTotalTurns());
        assertTrue(result.getTurnHistory().isEmpty());
        assertEquals(stats, result.getStatistics());
        assertEquals(summary, result.getBattleSummary());
        assertFalse(result.reachedTurnLimit());
    }
    
    @Test
    public void testBattleResultGetters() {
        Fighter winner = createTestFighter("TestFighter", 1);
        List<TurnResult> turnHistory = new ArrayList<>();
        BattleStats stats = new BattleStats(5, 3, 1, 1, 0, 0, 2.7);
        String summary = "Test summary";
        
        BattleResult result = new BattleResult(winner, 3, turnHistory, stats, summary, true);
        
        // Test all getters
        assertEquals(winner, result.getWinner());
        assertEquals(3, result.getTotalTurns());
        assertEquals(turnHistory, result.getTurnHistory());
        assertEquals(stats, result.getStatistics());
        assertEquals(summary, result.getBattleSummary());
        assertTrue(result.reachedTurnLimit());
    }
}
