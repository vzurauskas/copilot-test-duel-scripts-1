package com.duelscripts.combat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BattleStatsTest {
    
    @Test
    public void testBattleStatsCreation() {
        BattleStats stats = new BattleStats(15, 20, 3, 4, 1, 2, 8.5);
        
        assertEquals(15, stats.getTotalDamageByFighter1());
        assertEquals(20, stats.getTotalDamageByFighter2());
        assertEquals(3, stats.getSuccessfulStrikesByFighter1());
        assertEquals(4, stats.getSuccessfulStrikesByFighter2());
        assertEquals(1, stats.getCriticalHitsByFighter1());
        assertEquals(2, stats.getCriticalHitsByFighter2());
        assertEquals(8.5, stats.getAverageDamagePerTurn(), 0.001);
    }
    
    @Test
    public void testBattleStatsWithZeroValues() {
        BattleStats stats = new BattleStats(0, 0, 0, 0, 0, 0, 0.0);
        
        assertEquals(0, stats.getTotalDamageByFighter1());
        assertEquals(0, stats.getTotalDamageByFighter2());
        assertEquals(0, stats.getSuccessfulStrikesByFighter1());
        assertEquals(0, stats.getSuccessfulStrikesByFighter2());
        assertEquals(0, stats.getCriticalHitsByFighter1());
        assertEquals(0, stats.getCriticalHitsByFighter2());
        assertEquals(0.0, stats.getAverageDamagePerTurn(), 0.001);
    }
    
    @Test
    public void testToStringFormat() {
        BattleStats stats = new BattleStats(15, 20, 3, 4, 1, 2, 8.5);
        String result = stats.toString();
        
        assertNotNull(result);
        assertTrue(result.contains("Battle Statistics"));
        assertTrue(result.contains("damage dealt"));
        assertTrue(result.contains("successful strikes"));
        assertTrue(result.contains("critical hits"));
        assertTrue(result.contains("Average damage per turn"));
        assertTrue(result.contains("15")); // Fighter1 damage
        assertTrue(result.contains("20")); // Fighter2 damage
        assertTrue(result.contains("8.5")); // Average damage
    }
    
    @Test
    public void testToStringWithLargeNumbers() {
        BattleStats stats = new BattleStats(100, 150, 25, 30, 5, 8, 12.75);
        String result = stats.toString();
        
        assertNotNull(result);
        assertTrue(result.contains("100"));
        assertTrue(result.contains("150"));
        assertTrue(result.contains("25"));
        assertTrue(result.contains("30"));
        assertTrue(result.contains("5"));
        assertTrue(result.contains("8"));
        assertTrue(result.contains("12.8")); // Should be formatted to 1 decimal
    }
}
