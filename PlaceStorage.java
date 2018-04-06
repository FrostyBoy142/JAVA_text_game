package jeu_base_texte.storage;

import jeu_base_texte.item.Item;

public class PlaceStorage extends Storage {

	@Override
	public void displayItemList() {
		if (this.list.isEmpty()) {
			System.out.println("There are no items around you\n#		    ||");
		} else {

			for (Item item : this.list) {
				System.out.println("   - " + item + "\n#		    ||");
			}
		}
	}
}