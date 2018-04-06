/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu_base_texte.character;

/**
 *
 * @author antonin
 */
public class EnemyCharacter extends Character {
	private static final int DEF_PROTECTION = 1;

	private int strength; // attacking damage
	public int protection; // protection against damages

	public EnemyCharacter(String name, int health, int strength) {
		super(name, health);
		this.strength = strength;
		this.protection = DEF_PROTECTION;
	}

	public void attack(PlayableCharacter hero) {
		hero.hurt(this.strength);
	}

	@Override
	public String toString() {
		return this.name;
	}

	public void hurt(int damage) {
		this.health -= (damage - this.protection);
		if (this.health < 0)
			this.health = 0;
	}
}
