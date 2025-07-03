package com.duelscripts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class FighterContextTest {
    
    private Fighter createTestFighter(String name, int hp) {
        Weapon weapon = new Weapon("Test Weapon", 10, 0.1);
        return new Fighter(name, hp, 5, weapon);
    }
    
    @Test
    public void testFighterContextCreation() {
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        
        FighterContext context = new FighterContext(fighter1, fighter2, 1, new ArrayList<>());
        
        assertEquals(fighter1, context.getSelf());
        assertEquals(fighter2, context.getOpponent());
        assertEquals(1, context.getCurrentTurn());
        assertTrue(context.getBattleHistory().isEmpty());
    }
    
    @Test
    public void testIsFirstTurn() {
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        
        FighterContext context1 = new FighterContext(fighter1, fighter2, 1, new ArrayList<>());
        assertTrue(context1.isFirstTurn());
        
        FighterContext context2 = new FighterContext(fighter1, fighter2, 2, new ArrayList<>());
        assertFalse(context2.isFirstTurn());
    }
    
    @Test
    public void testHealthPercentages() {
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 100);
        
        // Damage fighter1 to 25 HP (50% health)
        fighter1.takeDamage(25);
        // Damage fighter2 to 25 HP (25% health)
        fighter2.takeDamage(75);
        
        FighterContext context = new FighterContext(fighter1, fighter2, 1, new ArrayList<>());
        
        assertEquals(0.5, context.getSelfHealthPercentage(), 0.001);
        assertEquals(0.25, context.getOpponentHealthPercentage(), 0.001);
    }
    
    @Test
    public void testGetLastTurnResult() {
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        
        // Empty history
        FighterContext contextEmpty = new FighterContext(fighter1, fighter2, 1, new ArrayList<>());
        assertNull(contextEmpty.getLastTurnResult());
        
        // With history
        var history = new ArrayList<CombatResolver.TurnResult>();
        CombatResolver.TurnResult turnResult = new CombatResolver.TurnResult("Test turn", 5, 10, false, true);
        history.add(turnResult);
        
        FighterContext contextWithHistory = new FighterContext(fighter1, fighter2, 2, history);
        assertEquals(turnResult, contextWithHistory.getLastTurnResult());
    }
    
    @Test
    public void testBattleHistoryImmutable() {
        Fighter fighter1 = createTestFighter("Alice", 50);
        Fighter fighter2 = createTestFighter("Bob", 50);
        
        var originalHistory = new ArrayList<CombatResolver.TurnResult>();
        CombatResolver.TurnResult turnResult = new CombatResolver.TurnResult("Test turn", 5, 10, false, true);
        originalHistory.add(turnResult);
        
        FighterContext context = new FighterContext(fighter1, fighter2, 2, originalHistory);
        
        // Try to modify the returned history - should not affect the context
        var returnedHistory = context.getBattleHistory();
        assertThrows(UnsupportedOperationException.class, () -> {
            returnedHistory.add(new CombatResolver.TurnResult("New turn", 0, 0, false, false));
        });
    }
}
