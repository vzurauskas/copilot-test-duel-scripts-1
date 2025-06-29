package com.duelscripts;

/**
 * Represents the different body parts that can be targeted in combat.
 * Each body part has different damage multipliers.
 */
public enum BodyPart {
    HEAD(1.5),      // Highest damage
    TORSO(1.0),     // Medium damage  
    LEGS(0.7);      // Lowest damage
    
    private final double damageMultiplier;
    
    BodyPart(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }
    
    public double getDamageMultiplier() {
        return damageMultiplier;
    }
}
