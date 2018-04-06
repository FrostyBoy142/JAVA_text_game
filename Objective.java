package jeu_base_texte.objective;

import jeu_base_texte.Game;

public abstract class Objective {

	public boolean succeeded;
	private String description;

	public Objective(String description) {
		this.description = description;
		this.succeeded = false;
	}

	public void displayDescription() {
		System.out.print("" + description);
		if (this.succeeded) {
			System.out.println(" : Complete");
		} else {
			System.out.println(" : In progress");
		}
		System.out.println("\n#		    ||");
	}

	public abstract void checkIfAchieved(Game game);
}
