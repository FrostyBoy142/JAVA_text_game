package jeu_base_texte.storage;

import jeu_base_texte.item.Item;
import jeu_base_texte.padlock.Padlock;

public class Chest extends Storage {

	public boolean open;
	public boolean lock;
	public String name;
	private Padlock pl;

	public Chest(Padlock pl, String name) {
		this.name = name;
		if (pl == null) {
			this.lock = false;
		} else {
			this.lock = true;
		}
		this.open = false;
		this.pl = pl;
	}

	@Override
	public void displayItemList() {
		if (this.list.isEmpty()) {
			System.out.println("This chest is empty !\n#		    ||");
		} else {

			for (Item item : this.list) {
				System.out.print("    - ");
				System.out.println(item);
			}
			System.out.println("\n#		    ||");
		}
	}

	public boolean hasPadlock() {
		return this.pl != null;
	}

	public void setPadlock(Padlock pl) {
		if (this.hasPadlock()) {
			System.out.println("This chest is already locked\n#		    ||");

		} else {
			this.pl = pl;
			this.lock = true;
		}
	}

	public boolean unlock(Object obj) {
		boolean retrn = false;
		if (this.pl == null) {
			System.out.println("This item isn't locked\n#		    ||");

		} else {
			if (this.pl.unlock(obj)) {
				retrn = true;
				this.lock = false;
			}
		}
		return retrn;

	}

	public Padlock getPadlock() {
		return pl;
	}
}