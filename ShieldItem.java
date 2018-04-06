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
public class ShieldItem extends EquipableItem {
	public int protection;

	public ShieldItem(String name, int weight, int protection) {
		super(name, weight);
		this.protection = protection;
	}

	@Override
	public String toString() {
		String str = "Shield : " + this.name;
		str += "\n\t- protection : " + this.protection;
		str += "\n\t- weight :" + this.weight;
		return str;
	}

}
