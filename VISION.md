# Vision: Duel Scripts Game

## Game Concept

Duel Scripts is a turn-based game where two fighters face off in a series of discrete, simultaneous turns. Each turn, both fighters perform two actions:

- **Strike Opponent**: Target one of three body parts—Head (highest damage), Torso (medium), or Legs (lowest).
- **Parry**: Attempt to block a strike to a chosen body part.

If a strike lands on a body part that the defender parries, the attack is completely deflected and deals no damage.

### Fighter Attributes
- **Name**
- **Hit Points**
- **Strength**
- **Weapon**: Each weapon has a base-damage value and a critical-hit chance.
- **Script**: An algorithm that determines the fighter's actions each turn, based on knowledge of themselves, their opponent, and the fight history.

## Technical Requirements
- The game is written in **Java**.
- This repository is a **Maven project**.
- The game is **entirely text-based** (no GUI).
- Fighters and scripts are implemented as simple Java objects within the codebase (no need for user input or external configuration).
