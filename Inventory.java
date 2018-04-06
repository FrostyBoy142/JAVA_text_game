package jeu_base_texte.storage;

import jeu_base_texte.item.Item;
import jeu_base_texte.item.ShieldItem;
import jeu_base_texte.item.SwordItem;

public class Inventory extends Storage {

	public SwordItem sword = null;
	public ShieldItem shield = null;

	@Override
	public void displayItemList() {
		if (this.list.isEmpty()) {
			System.out.println("There are no more items in your inventory\n#		    ||");
		} else {

			for (Item item : this.list) {
				System.out.print("    - ");
				System.out.println(item + "\n#		    ||");
			}
			System.out.print("#		    ||");

			if (this.hasEquipment()) {
				System.out.println("Equipment :");
				if (this.sword != null)
					System.out.println("" + this.sword + "#\n		    ||");
				if (this.shield != null)
					System.out.println("" + this.shield + "#\n		    ||");
			}
		}
	}

	public boolean hasEquipment() {
		return hasSword() || hasShield();
	}

	public boolean hasSword() {
		return this.sword != null;
	}

	public boolean hasShield() {
		return this.shield != null;
	}

	public void addSword(SwordItem sword) {
		this.remove(sword);
		this.removeSword();
		this.sword = sword;
	}

	public boolean removeSword() {
		boolean retrn = false;
		if (this.hasSword()) {
			if (this.add(this.sword)) {
				this.sword = null;
				retrn = true;
			} else {
				System.out.println("You may want to free some space in your inventory\n#		    ||");

			}
		} else {
			System.out.println("You don't have a sword\n#		    ||");

		}
		return retrn;
	}

	public void addShield(ShieldItem shield) {
		this.remove(shield);
		this.removeShield();
		this.shield = shield;
	}

	public boolean removeShield() {
		boolean retrn = false;
		if (this.hasShield()) {
			if (this.add(this.shield)) {
				this.shield = null;
				retrn = true;
			} else {
				System.out.println("You may want to free some space in your inventory\n#		    ||");

			}

		} else {
			System.out.println("You don't have a shield\n#		    ||");

		}
		return retrn;
	}

}