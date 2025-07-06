package com.duelscripts.scripting;

import com.duelscripts.core.Fighter;
import com.duelscripts.combat.TurnResult;

import java.util.List;

/**
 * Provides context information for combat scripts to make strategic decisions.
 * Contains all the information a script needs to determine the next action.
 */
public class FighterContext {
    private final Fighter self;
    private final Fighter opponent;
    private final int currentTurn;
    private final List<TurnResult> battleHistory;
    
    public FighterContext(Fighter self, Fighter opponent, int currentTurn, 
                         List<TurnResult> battleHistory) {
        this.self = self;
        this.opponent = opponent;
        this.currentTurn = currentTurn;
        this.battleHistory = List.copyOf(battleHistory); // Defensive copy
    }
    
    /**
     * Gets the fighter that will be taking the action.
     * @return The fighter using this context
     */
    public Fighter getSelf() {
        return self;
    }
    
    /**
     * Gets the opponent fighter.
     * @return The opponent fighter
     */
    public Fighter getOpponent() {
        return opponent;
    }
    
    /**
     * Gets the current turn number (1-based).
     * @return The current turn number
     */
    public int getCurrentTurn() {
        return currentTurn;
    }
    
    /**
     * Gets the battle history up to this point.
     * @return An immutable list of all previous turn results
     */
    public List<TurnResult> getBattleHistory() {
        return battleHistory;
    }
    
    /**
     * Gets the most recent turn result, if any.
     * @return The last turn result, or null if this is the first turn
     */
    public TurnResult getLastTurnResult() {
        if (battleHistory.isEmpty()) {
            return null;
        }
        return battleHistory.get(battleHistory.size() - 1);
    }
    
    /**
     * Checks if this is the first turn of the battle.
     * @return true if this is turn 1, false otherwise
     */
    public boolean isFirstTurn() {
        return currentTurn == 1;
    }
    
    /**
     * Gets the fighter's current health percentage.
     * @return A value between 0.0 and 1.0 representing health percentage
     */
    public double getSelfHealthPercentage() {
        return (double) self.getHitPoints() / self.getMaxHitPoints();
    }
    
    /**
     * Gets the opponent's current health percentage.
     * @return A value between 0.0 and 1.0 representing opponent's health percentage
     */
    public double getOpponentHealthPercentage() {
        return (double) opponent.getHitPoints() / opponent.getMaxHitPoints();
    }
}
