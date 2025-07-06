package com.duelscripts.core;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class WeaponTest {
    
    @Test
    public void testWeaponCreation() {
        Weapon sword = new Weapon("Sword", 10, 0.15);
        
        assertEquals("Sword", sword.getName());
        assertEquals(10, sword.getBaseDamage());
        assertEquals(0.15, sword.getCriticalHitChance(), 0.001);
    }
    
    @Test
    public void testCriticalHitChanceClamping() {
        // Test values outside 0-1 range are clamped
        Weapon weapon1 = new Weapon("Test", 10, -0.5);
        assertEquals(0.0, weapon1.getCriticalHitChance());
        
        Weapon weapon2 = new Weapon("Test", 10, 1.5);
        assertEquals(1.0, weapon2.getCriticalHitChance());
    }
    
    @Test
    public void testBaseDamageCalculation() {
        // Use a fixed Random for predictable testing
        Random fixedRandom = new Random(12345) {
            @Override
            public double nextDouble() {
                return 0.9; // Always return high value (no crit)
            }
        };
        
        Weapon weapon = new Weapon("Test Weapon", 10, 0.5, fixedRandom);
        int fighterStrength = 5;
        
        // Test different body parts
        int headDamage = weapon.calculateDamage(BodyPart.HEAD, fighterStrength);
        int torsoDamage = weapon.calculateDamage(BodyPart.TORSO, fighterStrength);
        int legsDamage = weapon.calculateDamage(BodyPart.LEGS, fighterStrength);
        
        // Expected: (10 base + 5 strength) * multiplier
        assertEquals(23, headDamage); // 15 * 1.5 = 22.5, rounded to 23
        assertEquals(15, torsoDamage); // 15 * 1.0 = 15
        assertEquals(11, legsDamage);  // 15 * 0.7 = 10.5, rounded to 11
    }
    
    @Test
    public void testCriticalHitDamage() {
        // Force critical hit
        Random critRandom = new Random(12345) {
            @Override
            public double nextDouble() {
                return 0.1; // Always return low value (always crit)
            }
        };
        
        Weapon weapon = new Weapon("Crit Weapon", 10, 0.5, critRandom);
        int damage = weapon.calculateDamage(BodyPart.TORSO, 5);
        
        // Expected: (10 + 5) * 1.0 * 2.0 (crit) = 30
        assertEquals(30, damage);
    }
    
    @Test
    public void testToString() {
        Weapon weapon = new Weapon("Magic Sword", 15, 0.25);
        String expected = "Magic Sword (Base: 15, Crit: 25.0%)";
        assertEquals(expected, weapon.toString());
    }
}
