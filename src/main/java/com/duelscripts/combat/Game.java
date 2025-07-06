package com.duelscripts.combat;

import com.duelscripts.core.Action;
import com.duelscripts.core.Fighter;
import com.duelscripts.scripting.FighterContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Main game class that orchestrates combat between fighters.
 */
public class Game {
    private final Fighter fighter1;
    private final Fighter fighter2;
    private final int maxTurns;
    private final boolean enableTurnDelay;
    private final List<TurnResult> battleHistory;
    private int currentTurn;
    
    public Game(Fighter fighter1, Fighter fighter2) {
        this(fighter1, fighter2, 50, false); // Default max 50 turns, no delay
    }
    
    public Game(Fighter fighter1, Fighter fighter2, int maxTurns, boolean enableTurnDelay) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
        this.maxTurns = maxTurns;
        this.enableTurnDelay = enableTurnDelay;
        this.battleHistory = new ArrayList<>();
        this.currentTurn = 1;
    }
    
    /**
     * Runs a complete battle between the two fighters.
     * @return The complete battle result
     */
    public BattleResult runFullCombat() {
        // Reset battle state
        battleHistory.clear();
        currentTurn = 1;
        
        displayBattleStart();
        
        while (!isGameOver() && currentTurn <= maxTurns) {
            displayTurnHeader(currentTurn);
            
            TurnResult turnResult = executeTurn();
            battleHistory.add(turnResult);
            
            System.out.println(turnResult.getDescription());
            
            if (enableTurnDelay && !isGameOver() && currentTurn < maxTurns) {
                try {
                    Thread.sleep(1000); // 1 second delay between turns
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            
            currentTurn++;
        }
        
        boolean reachedTurnLimit = currentTurn > maxTurns;
        Fighter winner = reachedTurnLimit ? null : getWinner();
        BattleStats stats = generateBattleStatistics(battleHistory);
        String summary = generateBattleSummary(winner, currentTurn - 1, reachedTurnLimit, stats);
        
        BattleResult result = new BattleResult(winner, currentTurn - 1, battleHistory, stats, summary, reachedTurnLimit);
        displayBattleEnd(result);
        
        return result;
    }
    
    /**
     * Displays the battle setup and fighter information.
     */
    public void displayBattleStart() {
        System.out.println("\nBATTLE SETUP:");
        System.out.println("=".repeat(60));
        System.out.println(fighter1);
        System.out.println("    VS");
        System.out.println(fighter2);
        System.out.println();
    }
    
    /**
     * Displays the turn header.
     */
    public void displayTurnHeader(int turnNumber) {
        System.out.println("TURN " + turnNumber + ":");
        System.out.println("-".repeat(40));
    }
    
    /**
     * Displays the battle end summary.
     */
    public void displayBattleEnd(BattleResult result) {
        System.out.println("\nBATTLE COMPLETE!");
        System.out.println("=".repeat(60));
        System.out.println(result.getBattleSummary());
        System.out.println();
        System.out.println(result.getStatistics());
    }
    
    /**
     * Generates battle statistics from the turn history.
     */
    private BattleStats generateBattleStatistics(List<TurnResult> turnHistory) {
        int totalDamageByFighter1 = 0;
        int totalDamageByFighter2 = 0;
        int successfulStrikesByFighter1 = 0;
        int successfulStrikesByFighter2 = 0;
        int criticalHitsByFighter1 = 0;
        int criticalHitsByFighter2 = 0;
        
        for (TurnResult turn : turnHistory) {
            // Fighter1 damage dealt to Fighter2
            totalDamageByFighter1 += turn.getFighter2Damage();
            if (turn.getFighter2Damage() > 0) {
                successfulStrikesByFighter1++;
                if (turn.isFighter1CriticalHit()) {
                    criticalHitsByFighter1++;
                }
            }
            
            // Fighter2 damage dealt to Fighter1
            totalDamageByFighter2 += turn.getFighter1Damage();
            if (turn.getFighter1Damage() > 0) {
                successfulStrikesByFighter2++;
                if (turn.isFighter2CriticalHit()) {
                    criticalHitsByFighter2++;
                }
            }
        }
        
        double averageDamagePerTurn = turnHistory.isEmpty() ? 0.0 : 
            (double)(totalDamageByFighter1 + totalDamageByFighter2) / turnHistory.size();
        
        return new BattleStats(
            totalDamageByFighter1, totalDamageByFighter2,
            successfulStrikesByFighter1, successfulStrikesByFighter2,
            criticalHitsByFighter1, criticalHitsByFighter2,
            averageDamagePerTurn
        );
    }
    
    /**
     * Generates a human-readable battle summary.
     */
    private String generateBattleSummary(Fighter winner, int totalTurns, boolean reachedTurnLimit, BattleStats stats) {
        StringBuilder summary = new StringBuilder();
        
        if (reachedTurnLimit) {
            summary.append("BATTLE TIMEOUT! No winner after ").append(totalTurns).append(" turns.");
        } else if (winner != null) {
            String loser = winner == fighter1 ? fighter2.getName() : fighter1.getName();
            summary.append("WINNER: ").append(winner.getName())
                   .append(" defeats ").append(loser)
                   .append(" in ").append(totalTurns).append(" turns!");
        } else {
            summary.append("DRAW! Both fighters have fallen!");
        }
        
        return summary.toString();
    }
    
    /**
     * Executes one turn of combat between the two fighters.
     * @return The result of the turn
     */
    public TurnResult executeTurn() {
        // Create context for each fighter
        FighterContext context1 = new FighterContext(fighter1, fighter2, currentTurn, battleHistory);
        FighterContext context2 = new FighterContext(fighter2, fighter1, currentTurn, battleHistory);
        
        // Get actions using context (for script-aware fighters) or fallback to no-context method
        Action action1, action2;
        try {
            action1 = fighter1.getAction(context1);
        } catch (Exception e) {
            // Fallback to backward-compatible method
            action1 = fighter1.getAction();
        }
        
        try {
            action2 = fighter2.getAction(context2);
        } catch (Exception e) {
            // Fallback to backward-compatible method
            action2 = fighter2.getAction();
        }
        
        return CombatResolver.resolveTurn(fighter1, action1, fighter2, action2);
    }
    
    /**
     * Checks if the game is over (one fighter is defeated).
     * @return true if one or both fighters are defeated
     */
    public boolean isGameOver() {
        return !fighter1.isAlive() || !fighter2.isAlive();
    }
    
    /**
     * Gets the winner of the game, if any.
     * @return The winning fighter, or null if both are alive or both are dead
     */
    public Fighter getWinner() {
        if (fighter1.isAlive() && !fighter2.isAlive()) {
            return fighter1;
        } else if (fighter2.isAlive() && !fighter1.isAlive()) {
            return fighter2;
        }
        return null; // Either both alive or both dead
    }
    
    public Fighter getFighter1() {
        return fighter1;
    }
    
    public Fighter getFighter2() {
        return fighter2;
    }
    
    public int getMaxTurns() {
        return maxTurns;
    }
}
