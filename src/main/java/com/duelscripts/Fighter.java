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
    private CombatScript combatScript;
    
    public Fighter(String name, int hitPoints, int strength, Weapon weapon) {
        this(name, hitPoints, strength, weapon, ScriptFactory.getDefaultScript());
    }
    
    public Fighter(String name, int hitPoints, int strength, Weapon weapon, CombatScript combatScript) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.maxHitPoints = hitPoints;
        this.strength = strength;
        this.weapon = weapon;
        this.combatScript = combatScript;
    }
    
    /**
     * Gets the next action for this fighter using their combat script.
     * @param context The current battle context
     * @return The action to take this turn
     */
    public Action getAction(FighterContext context) {
        return combatScript.getNextAction(context);
    }
    
    /**
     * For backward compatibility - generates a random action.
     * This maintains the old behavior for existing code.
     */
    public Action getAction() {
        return generateRandomAction();
    }
    
    /**
     * Generates a random action (used by RandomScript and for backward compatibility).
     * @return A random action
     */
    public Action generateRandomAction() {
        // Simple hardcoded strategy: strike head, parry torso
        // This maintains the current behavior
        return new Action(BodyPart.HEAD, BodyPart.TORSO);
    }
    
    /**
     * Sets a new combat script for this fighter.
     * @param combatScript The new script to use
     */
    public void setCombatScript(CombatScript combatScript) {
        this.combatScript = combatScript;
    }
    
    /**
     * Gets the current combat script.
     * @return The current combat script
     */
    public CombatScript getCombatScript() {
        return combatScript;
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
        return String.format("%s (HP: %d/%d, Str: %d, Weapon: %s, Script: %s)", 
                           name, hitPoints, maxHitPoints, strength, weapon.getName(), combatScript.getName());
    }
}
