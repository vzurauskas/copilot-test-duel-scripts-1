package com.duelscripts.combat;

/**
 * Represents the result of a single turn of combat.
 */
public class TurnResult {
    private final String description;
    private final int fighter1Damage;
    private final int fighter2Damage;
    private final boolean fighter1CriticalHit;
    private final boolean fighter2CriticalHit;
    
    public TurnResult(String description, int fighter1Damage, int fighter2Damage, 
                     boolean fighter1CriticalHit, boolean fighter2CriticalHit) {
        this.description = description;
        this.fighter1Damage = fighter1Damage;
        this.fighter2Damage = fighter2Damage;
        this.fighter1CriticalHit = fighter1CriticalHit;
        this.fighter2CriticalHit = fighter2CriticalHit;
    }
    
    public String getDescription() { return description; }
    public int getFighter1Damage() { return fighter1Damage; }
    public int getFighter2Damage() { return fighter2Damage; }
    public boolean isFighter1CriticalHit() { return fighter1CriticalHit; }
    public boolean isFighter2CriticalHit() { return fighter2CriticalHit; }
}
