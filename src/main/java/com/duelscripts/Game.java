package com.duelscripts;

/**
 * Main game class that orchestrates combat between fighters.
 */
public class Game {
    private final Fighter fighter1;
    private final Fighter fighter2;
    
    public Game(Fighter fighter1, Fighter fighter2) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
    }
    
    /**
     * Executes one turn of combat between the two fighters.
     * @return The result of the turn
     */
    public CombatResolver.TurnResult executeTurn() {
        Action action1 = fighter1.getAction();
        Action action2 = fighter2.getAction();
        
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
}
