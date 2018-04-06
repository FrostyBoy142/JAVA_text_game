package jeu_base_texte;

import jeu_base_texte.character.PlayableCharacter;

public enum Command {
	QUIT, GO, PICKUP, DROP, EAT, LOOK, WHEREAMI, OBJECTIVES, INVENTORY, OPEN, CLOSE, UNLOCK, TALK, HELP, FIGHT, EQUIP, UNEQUIP, INVALID;

	public static Command valueOfCommand(String id) {
		try {
			return Command.valueOf(id);
		} catch (IllegalArgumentException e) {
			return Command.INVALID;
		}
	}

	// fonction qui détecte si une chaine possède que des espaces
	public static boolean stringOnlySpaces(String string) {
		boolean isEmpty = true;

		if (string != null) {

			isEmpty = "".equals(string.replaceAll("[\\s\\u00A0]+$", ""));
		}

		return isEmpty;
	}

	// Fonction qui permet de concaténer les paramètre de la commande à partir du
	// deuxieme paramètre , meme si celui si est séparé en pusieurs
	public String concatParams(String[] params, Integer numberBeginParam) {
		String retrn = "";
		for (int i = numberBeginParam - 1; i < params.length; i++) {
			String mot = params[i];
			if (!stringOnlySpaces(mot)) {
				if (i != numberBeginParam - 1) {
					retrn += " ";
				}
				retrn += mot;
			}
		}
		return retrn;
	}

	public boolean execute(Game game, String[] params) {
		boolean quit = false;
		PlayableCharacter hero = (PlayableCharacter) game.getCharacter();
		boolean inChest = hero.isChestOpen();
		switch (this) {
		case QUIT:
			quit = true;
			break;

		case UNLOCK:
			if (inChest) {
				System.out.println("You must close the chest before leaving\n#		    ||");
				break;
			}
			if (params.length < 3) {
				System.out.println("You need three arguments to use this command\n#		    ||");
				break;
			}

			String command = concatParams(params, 2).toLowerCase();
			hero.unlock(command);
			break;
		case OPEN:
			if (inChest) {
				System.out.println("You must close the chest first\n#		    ||");
				break;
			}
			if (params.length < 2) {
				System.out.println("You need two arguments to use this command\n#		    ||");
				break;
			}
			String chestName = concatParams(params, 2).toLowerCase();
			hero.openChest(chestName);

			break;

		case CLOSE:
			if (!inChest) {
				System.out.println("There is no chest to be closed\n#		    ||");
				break;
			}
			hero.closeChest();
			break;
		case GO:
			if (inChest) {
				System.out.println("You must close the chest first\n#		    ||");
				break;
			}
			if (params.length < 2) {
				System.out.println("You need two arguments to use this command\n#		    ||");
				break;
			}

			String exitName = concatParams(params, 2).toLowerCase();
			hero.go(exitName);
			break;
		case WHEREAMI:
			System.out.println("You are in " + hero.location + "\n#		    ||");
			break;
		case LOOK:
			if (inChest) {
				hero.displayItemInOpenChest();
				break;
			}
			if (params.length != 2) {
				System.out.println("You need two arguments to use this command\n#		    ||");
				break;
			}

			String param = concatParams(params, 2).toLowerCase();
			hero.look(param);
			break;
		case PICKUP:
			if (params.length < 2) {
				System.out.println("You need two arguments to use this command\n#		    ||");
				break;
			}
			String nameItem = concatParams(params, 2).toLowerCase();
			hero.pickup(nameItem);
			break;
		case DROP:

			if (params.length < 2) {
				System.out.println("You need two arguments to use this command\n#		    ||");
				break;
			}
			String nameItem2 = concatParams(params, 2).toLowerCase();
			hero.drop(nameItem2);

			break;
		case OBJECTIVES:
			if (params.length != 1) {
				System.out.println("There are too many arguments\n#		    ||");
				break;
			}

			game.displayObjectives();

			break;
		case INVENTORY:
			if (params.length != 1) {
				System.out.println("There are too many arguments\n#		    ||");
				break;
			}
			hero.dispInventory();
			break;
		case TALK:
			if (params.length < 2) {
				System.out.println("You need two arguments to use this command\n#		    ||");
				break;
			}
			String nameHelper = concatParams(params, 2).toLowerCase();
			hero.talk(nameHelper);

			break;
		case FIGHT:
			if (params.length < 2) {
				System.out.println("You need two arguments to use this command\n#		    ||");
				break;
			}
			String nameEnemy = concatParams(params, 2).toLowerCase();
			hero.fight(nameEnemy);

			break;
		case EQUIP:
			if (params.length < 2) {
				System.out.println("You need two arguments to use this command\n#		    ||");
				break;
			}
			String nameItem3 = concatParams(params, 2).toLowerCase();
			hero.equip(nameItem3);
			break;
		case UNEQUIP:
			if (params.length != 2) {
				System.out.println("You need two arguments to use this command\n#		    ||");
				break;
			}
			String param2 = concatParams(params, 2).toLowerCase();
			hero.unequip(param2);
			break;
		case EAT:
			if (params.length < 2) {
				System.out.println("You need two arguments to use this command");
				break;
			}
			String nameItem4 = concatParams(params, 2).toLowerCase();
			hero.eat(nameItem4);
			break;
		case HELP:
			if (params.length > 2) {
				System.out.println("You need a maximum of two arguments to use this command");
			} else {
				if (params.length == 1) {
					for (Command c : Command.values()) {
						c.help();
					}
				} else {
					Command.valueOfCommand(params[1].toUpperCase()).help();
				}
			}
			break;
		case INVALID:
		default:
			System.out.println("Invalid command line");
			System.out.println("Type \"HELP\" to see the list of commands\n#		    ||");
			break;

		}
		return quit;
	}

	public void help() {
		switch (this) {
		case QUIT:
			System.out.println("QUIT");
			System.out.println("\t- quit the current game");
			System.out.println("\t- exit a chest");
			break;
		case GO:
			System.out.println("GO");
			System.out.println("\t- choose a direction");
			System.out.println("\t  (ex: go bathroom)");
			break;
		case PICKUP:
			System.out.println("PICKUP");
			System.out.println("\t- pickup an item");
			System.out.println("\t  (ex: pickup cheese)");
			break;
		case DROP:
			System.out.println("DROP");
			System.out.println("\t- drop an item");
			System.out.println("\t  (ex: drop microphone)");
			break;
		case EAT:
			System.out.println("EAT");
			System.out.println("\t- eat an eadable item");
			System.out.println("\t  (ex: eat cheese)");
			break;
		case LOOK:
			System.out.println("LOOK");
			System.out.println("\t- '' around : displays everything around you");
			System.out.println("\t- '' floor  : displays a list of items on the floor of the current place");
			System.out.println("\t  (ex: look around)");
			break;
		case FIGHT:
			System.out.println("FIGHT");
			System.out.println("\t- engage a fight with an enemy ");
			System.out.println("\t  (ex: fight spider)");
			break;
		case EQUIP:
			System.out.println("EQUIP");
			System.out.println("\t- equip an item of your inventory");
			System.out.println("\t  (ex: equip sword)");
			break;
		case UNEQUIP:
			System.out.println("UNEQUIP");
			System.out.println("\t- unequip an item of your inventory");
			System.out.println("\t  (ex: unequip shield)");
			break;
		case WHEREAMI:
			System.out.println("WHEREAMI");
			System.out.println("\t- displays your current location");
			break;
		case OBJECTIVES:
			System.out.println("OBJECTIVES");
			System.out.println("\t- lists your objectives");
			break;
		case INVENTORY:
			System.out.println("INVENTORY");
			System.out.println("\t- displays your inventory");
			break;
		case OPEN:
			System.out.println("OPEN");
			System.out.println("\t- open a chest or an exit");
			System.out.println("\t  (ex: open door)");
			break;
		case CLOSE:
			System.out.println("CLOSE");
			System.out.println("\t- close a chest or an exit");
			System.out.println("\t  (ex: close door)");
			break;
		case UNLOCK:
			System.out.println("UNLOCK");
			System.out.println("\t- unlock a locked chest or a locked exit");
			System.out.println("\t  (ex: unlock door with key)");
			break;
		case TALK:
			System.out.println("TALK");
			System.out.println("\t- talk to a NPC");
			break;
		case INVALID:
			System.out.println("INVALID");
			System.out.println("\t- your command is invalid");
			break;
		default:
		case HELP:
			System.out.println("HELP");
			System.out.println("\t- displays the list if the commands");
			break;
		}
	}

}
