/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu_base_texte.item;

/**
 *
 * @author antonin
 */
public class SwordItem extends EquipableItem {
	public int damage;

	public SwordItem(String name, int weight, int dam) {
		super(name, weight);
		this.damage = dam;
	}

	@Override
	public String toString() {
		String str = "Sword : " + this.name;
		str += "\n\t- damage : " + this.damage;
		str += "\n\t- weight :" + this.weight;
		return str;
	}
}
