package jeu_base_texte;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jeu_base_texte.character.Character;
import jeu_base_texte.character.PlayableCharacter;
import jeu_base_texte.objective.Objective;

public class Game {

	private World world;
	public List<Objective> objectives = new ArrayList<Objective>();
	private PlayableCharacter hero = new PlayableCharacter();

	public Game(World wor, PlayableCharacter her) {
		this.world = wor;
		this.hero = her;
	}

	public void play() {
		hero.location = world.getFirstPlace();
		boolean quit = false;

		boolean success = false;
		Scanner scan = new Scanner(System.in);

		System.out.println("#						 #############################\n#");
		System.out.println("#						#            WELCOME !");
		System.out.println("#						#     Let's get started :)\n#");
		System.out.println("#						 #############################\n#\n#\n#\n#");

		System.out
				.println("###############################################################################\n#\n#	Health:"
						+ hero.health + "\n#");
		System.out
				.println("#			 ,==================[ " + hero.location + " ]==================\n#		    ||");
		hero.location.displayDescription();

		do {
			while (hero.isChestOpen() && !quit) {

				System.out.println("What would you like to do in the storage ?\n#		    ||");

				quit = input(scan);
			}

			if (quit != true) {
				System.out.println("What would you like to do ?");
				quit = input(scan);
				success = testObjectives();

			}

		} while (!quit && !success);

		if (success == true) {
			System.out.println(
					"###############################################################################\n#\n#\n#\n#");
			System.out.println("#						 #############################\n#						#");
			System.out.println("#						#			YOU WIN !");
			System.out.println("#						#	Thank you for playing :)\n#						#");
			System.out.println("#						 #############################\n#\n#\n#\n#");
		} else if (quit == true) {
			System.out.println(
					"###############################################################################\n#\n#\n#\n#");
			System.out.println("#						 #############################\n#						#");
			System.out.println("#						#	Thank you for playing :)  \n#						#");
			System.out.println("#						 #############################\n#\n#\n#\n#");
		}
	}

	public boolean input(Scanner scan) {
		String entry = "";
		entry = scan.nextLine();
		String[] arrEntry = entry.split("\\s");
		arrEntry[0] = arrEntry[0].toUpperCase();

		return Command.valueOfCommand(arrEntry[0]).execute(this, arrEntry);
	}

	/*
	 * Renvoi l'index de l'objectif (fourni en paramètre) dans la liste des
	 * objectifs
	 */
	public int indexObjectiveInList(Objective objective) {
		int index = -1;
		for (int i = 0; i < this.objectives.size(); i++) {
			if (this.objectives.get(i).equals(objective)) {
				index = i;
			}
		}
		return index;
	}

	private boolean testObjectives() {
		boolean res = true;
		for (Objective objective : this.objectives) {
			objective.checkIfAchieved(this);
			res &= objective.succeeded;
		}
		return res;
	}

	public void addObjective(Objective o) {
		this.objectives.add(o);
	}

	public void displayObjectives() {
		System.out.println("Your objectives : ");
		for (Objective objective : objectives) {
			System.out.print("\t- ");
			objective.displayDescription();
		}
	}

	public Character getCharacter() {
		return this.hero;
	}

	public World getWorld() {
		return this.world;
	}
}
