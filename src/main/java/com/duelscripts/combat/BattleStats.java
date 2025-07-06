package com.duelscripts.combat;

/**
 * Tracks statistics for a complete battle between two fighters.
 */
public class BattleStats {
    private final int totalDamageByFighter1;
    private final int totalDamageByFighter2;
    private final int successfulStrikesByFighter1;
    private final int successfulStrikesByFighter2;
    private final int criticalHitsByFighter1;
    private final int criticalHitsByFighter2;
    private final double averageDamagePerTurn;
    
    public BattleStats(int totalDamageByFighter1, int totalDamageByFighter2,
                      int successfulStrikesByFighter1, int successfulStrikesByFighter2,
                      int criticalHitsByFighter1, int criticalHitsByFighter2,
                      double averageDamagePerTurn) {
        this.totalDamageByFighter1 = totalDamageByFighter1;
        this.totalDamageByFighter2 = totalDamageByFighter2;
        this.successfulStrikesByFighter1 = successfulStrikesByFighter1;
        this.successfulStrikesByFighter2 = successfulStrikesByFighter2;
        this.criticalHitsByFighter1 = criticalHitsByFighter1;
        this.criticalHitsByFighter2 = criticalHitsByFighter2;
        this.averageDamagePerTurn = averageDamagePerTurn;
    }
    
    public int getTotalDamageByFighter1() {
        return totalDamageByFighter1;
    }
    
    public int getTotalDamageByFighter2() {
        return totalDamageByFighter2;
    }
    
    public int getSuccessfulStrikesByFighter1() {
        return successfulStrikesByFighter1;
    }
    
    public int getSuccessfulStrikesByFighter2() {
        return successfulStrikesByFighter2;
    }
    
    public int getCriticalHitsByFighter1() {
        return criticalHitsByFighter1;
    }
    
    public int getCriticalHitsByFighter2() {
        return criticalHitsByFighter2;
    }
    
    public double getAverageDamagePerTurn() {
        return averageDamagePerTurn;
    }
    
    @Override
    public String toString() {
        return String.format(
            "Battle Statistics:\n" +
            "- Fighter 1: %d damage dealt, %d successful strikes, %d critical hits\n" +
            "- Fighter 2: %d damage dealt, %d successful strikes, %d critical hits\n" +
            "- Average damage per turn: %.1f",
            totalDamageByFighter1, successfulStrikesByFighter1, criticalHitsByFighter1,
            totalDamageByFighter2, successfulStrikesByFighter2, criticalHitsByFighter2,
            averageDamagePerTurn
        );
    }
}
