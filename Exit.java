package jeu_base_texte;

import jeu_base_texte.padlock.Padlock;

public class Exit {
	static int nbExits = 0;

	public String name;
	public boolean lock;
	private Padlock pl;
	public Place place;

	public Exit(String name, Place place, Padlock pl) {
		this.name = name;
		this.pl = pl;
		this.place = place;

		if (pl == null) {
			this.lock = false;
		} else {
			this.lock = true;
		}
	}

	public Exit(boolean open, Padlock pl, Place place) {
		this.name = "exit_" + nbExits;
		this.lock = open;
		this.pl = pl;
		this.place = place;

		nbExits++;
	}

	public boolean isLocked() {
		return this.lock;
	}

	public boolean unlock(Object obj) {
		boolean retrn = false;
		if (this.pl == null) {
			System.out.println("This exit isn't locked\n#		    ||");
		} else {
			if (this.pl.unlock(obj)) {
				this.lock = false;
				retrn = true;
			}
		}
		return retrn;
	}

	public Padlock getPadlock() {
		return pl;
	}

	@Override
	public String toString() {
		return this.name;
	}

}