package com.duelscripts.combat;

import com.duelscripts.core.Fighter;

import java.util.List;

/**
 * Represents the complete result of a battle between two fighters.
 */
public class BattleResult {
    private final Fighter winner;
    private final int totalTurns;
    private final List<TurnResult> turnHistory;
    private final BattleStats statistics;
    private final String battleSummary;
    private final boolean reachedTurnLimit;
    
    public BattleResult(Fighter winner, int totalTurns, List<TurnResult> turnHistory,
                       BattleStats statistics, String battleSummary, boolean reachedTurnLimit) {
        this.winner = winner;
        this.totalTurns = totalTurns;
        this.turnHistory = List.copyOf(turnHistory); // Defensive copy
        this.statistics = statistics;
        this.battleSummary = battleSummary;
        this.reachedTurnLimit = reachedTurnLimit;
    }
    
    /**
     * Gets the winner of the battle, or null if it was a draw/timeout.
     */
    public Fighter getWinner() {
        return winner;
    }
    
    /**
     * Gets the total number of turns the battle lasted.
     */
    public int getTotalTurns() {
        return totalTurns;
    }
    
    /**
     * Gets a copy of the turn-by-turn battle history.
     */
    public List<TurnResult> getTurnHistory() {
        return turnHistory;
    }
    
    /**
     * Gets the battle statistics.
     */
    public BattleStats getStatistics() {
        return statistics;
    }
    
    /**
     * Gets a human-readable summary of the battle.
     */
    public String getBattleSummary() {
        return battleSummary;
    }
    
    /**
     * Returns true if the battle ended due to reaching the turn limit.
     */
    public boolean reachedTurnLimit() {
        return reachedTurnLimit;
    }
    
    @Override
    public String toString() {
        return battleSummary;
    }
}
