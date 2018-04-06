package jeu_base_texte.padlock;

import jeu_base_texte.item.Item;

public class PadlockKey implements Padlock {

	public Item item;

	public PadlockKey(Item item) {

		this.item = item;
	}

	@Override
	public boolean unlock(Object obj) {
		boolean retour = false;
		if (obj instanceof Item) {
			if (this.item.equals(obj)) {
				retour = true;
				System.out.println("Unlocked !\n#		    ||");
			} else {
				retour = false;
				System.out.println("This item can't unlock it\n#		    ||");
			}
		} else {
			System.out.println("You must provide an item\n#		    ||");
		}
		return retour;
	}

	@Override
	public String toString() {
		return "key"; // To change body of generated methods, choose Tools | Templates.
	}
}