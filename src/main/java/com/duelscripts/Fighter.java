package com.duelscripts;

/**
 * Represents a fighter in the duel.
 */
public class Fighter {
    private final String name;
    private int hitPoints;
    private final int maxHitPoints;
    private final int strength;
    private final Weapon weapon;
    
    public Fighter(String name, int hitPoints, int strength, Weapon weapon) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.maxHitPoints = hitPoints;
        this.strength = strength;
        this.weapon = weapon;
    }
    
    /**
     * For this first iteration, fighters use hardcoded actions.
     * This will be replaced with scripts in future iterations.
     */
    public Action getAction() {
        // Simple hardcoded strategy: strike head, parry torso
        return new Action(BodyPart.HEAD, BodyPart.TORSO);
    }
    
    /**
     * Applies damage to this fighter.
     * @param damage The amount of damage to take
     */
    public void takeDamage(int damage) {
        hitPoints = Math.max(0, hitPoints - damage);
    }
    
    /**
     * Checks if the fighter is still alive.
     * @return true if hit points > 0
     */
    public boolean isAlive() {
        return hitPoints > 0;
    }
    
    public String getName() {
        return name;
    }
    
    public int getHitPoints() {
        return hitPoints;
    }
    
    public int getMaxHitPoints() {
        return maxHitPoints;
    }
    
    public int getStrength() {
        return strength;
    }
    
    public Weapon getWeapon() {
        return weapon;
    }
    
    @Override
    public String toString() {
        return String.format("%s (HP: %d/%d, Str: %d, Weapon: %s)", 
                           name, hitPoints, maxHitPoints, strength, weapon.getName());
    }
}
