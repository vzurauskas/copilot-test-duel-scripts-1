package com.duelscripts;

import java.util.Random;

/**
 * Represents a weapon with base damage and critical hit chance.
 */
public class Weapon {
    private final String name;
    private final int baseDamage;
    private final double criticalHitChance;
    private final Random random;
    
    public Weapon(String name, int baseDamage, double criticalHitChance) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.criticalHitChance = Math.max(0.0, Math.min(1.0, criticalHitChance)); // Clamp between 0-1
        this.random = new Random();
    }
    
    // Constructor for testing with custom Random
    public Weapon(String name, int baseDamage, double criticalHitChance, Random random) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.criticalHitChance = Math.max(0.0, Math.min(1.0, criticalHitChance));
        this.random = random;
    }
    
    /**
     * Calculates damage for a strike to a specific body part.
     * @param bodyPart The body part being struck
     * @param fighterStrength The attacking fighter's strength
     * @return The calculated damage
     */
    public int calculateDamage(BodyPart bodyPart, int fighterStrength) {
        double baseDamageWithStrength = baseDamage + fighterStrength;
        double damageWithBodyPart = baseDamageWithStrength * bodyPart.getDamageMultiplier();
        
        // Check for critical hit
        boolean isCritical = random.nextDouble() < criticalHitChance;
        if (isCritical) {
            damageWithBodyPart *= 2.0; // Double damage on crit
        }
        
        return (int) Math.round(damageWithBodyPart);
    }
    
    /**
     * Checks if the last attack would be a critical hit (for testing)
     */
    public boolean wouldBeCritical() {
        return random.nextDouble() < criticalHitChance;
    }
    
    public String getName() {
        return name;
    }
    
    public int getBaseDamage() {
        return baseDamage;
    }
    
    public double getCriticalHitChance() {
        return criticalHitChance;
    }
    
    @Override
    public String toString() {
        return String.format("%s (Base: %d, Crit: %.1f%%)", name, baseDamage, criticalHitChance * 100);
    }
}
