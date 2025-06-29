package com.duelscripts;

/**
 * Resolves combat between two fighters for a single turn.
 */
public class CombatResolver {
    
    /**
     * Represents the result of a turn of combat.
     */
    public static class TurnResult {
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
    
    /**
     * Resolves one turn of combat between two fighters.
     * @param fighter1 The first fighter
     * @param action1 The first fighter's action
     * @param fighter2 The second fighter
     * @param action2 The second fighter's action
     * @return A TurnResult describing what happened
     */
    public static TurnResult resolveTurn(Fighter fighter1, Action action1, 
                                       Fighter fighter2, Action action2) {
        StringBuilder description = new StringBuilder();
        
        description.append(String.format("%s: %s\n", fighter1.getName(), action1));
        description.append(String.format("%s: %s\n", fighter2.getName(), action2));
        description.append("\n");
        
        // Calculate damage fighter1 deals to fighter2
        int damage1to2 = 0;
        boolean crit1 = false;
        if (action1.getStrikeTarget() != action2.getParryTarget()) {
            // Strike lands - not parried
            int calculatedDamage = fighter1.getWeapon().calculateDamage(action1.getStrikeTarget(), fighter1.getStrength());
            // Check if it was a critical hit by seeing if damage is roughly double what we'd expect
            int expectedBaseDamage = (int) Math.round((fighter1.getWeapon().getBaseDamage() + fighter1.getStrength()) 
                                                    * action1.getStrikeTarget().getDamageMultiplier());
            crit1 = calculatedDamage > expectedBaseDamage * 1.5; // Rough check for crit
            
            damage1to2 = calculatedDamage;
            fighter2.takeDamage(damage1to2);
            
            description.append(String.format("%s strikes %s's %s for %d damage%s!\n", 
                             fighter1.getName(), fighter2.getName(), 
                             action1.getStrikeTarget().name().toLowerCase(),
                             damage1to2, crit1 ? " (CRITICAL HIT)" : ""));
        } else {
            description.append(String.format("%s's strike to %s is parried by %s!\n",
                             fighter1.getName(), 
                             action1.getStrikeTarget().name().toLowerCase(),
                             fighter2.getName()));
        }
        
        // Calculate damage fighter2 deals to fighter1
        int damage2to1 = 0;
        boolean crit2 = false;
        if (action2.getStrikeTarget() != action1.getParryTarget()) {
            // Strike lands - not parried
            int calculatedDamage = fighter2.getWeapon().calculateDamage(action2.getStrikeTarget(), fighter2.getStrength());
            int expectedBaseDamage = (int) Math.round((fighter2.getWeapon().getBaseDamage() + fighter2.getStrength()) 
                                                    * action2.getStrikeTarget().getDamageMultiplier());
            crit2 = calculatedDamage > expectedBaseDamage * 1.5;
            
            damage2to1 = calculatedDamage;
            fighter1.takeDamage(damage2to1);
            
            description.append(String.format("%s strikes %s's %s for %d damage%s!\n",
                             fighter2.getName(), fighter1.getName(),
                             action2.getStrikeTarget().name().toLowerCase(),
                             damage2to1, crit2 ? " (CRITICAL HIT)" : ""));
        } else {
            description.append(String.format("%s's strike to %s is parried by %s!\n",
                             fighter2.getName(),
                             action2.getStrikeTarget().name().toLowerCase(),
                             fighter1.getName()));
        }
        
        description.append(String.format("\n%s: %d/%d HP\n", fighter1.getName(), 
                         fighter1.getHitPoints(), fighter1.getMaxHitPoints()));
        description.append(String.format("%s: %d/%d HP\n", fighter2.getName(),
                         fighter2.getHitPoints(), fighter2.getMaxHitPoints()));
        
        return new TurnResult(description.toString(), damage2to1, damage1to2, crit2, crit1);
    }
}
