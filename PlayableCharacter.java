package jeu_base_texte.character;

import java.util.Scanner;

import jeu_base_texte.Exit;
import jeu_base_texte.Place;
import jeu_base_texte.item.EadableItem;
import jeu_base_texte.item.EquipableItem;
import jeu_base_texte.item.Item;
import jeu_base_texte.item.ShieldItem;
import jeu_base_texte.item.SwordItem;
import jeu_base_texte.padlock.PadlockCode;
import jeu_base_texte.padlock.PadlockKey;
import jeu_base_texte.storage.Chest;
import jeu_base_texte.storage.Inventory;
import jeu_base_texte.storage.Storage;

public class PlayableCharacter extends Character {

	private static final int DEF_NRG = 100;
	private static final int DEF_HEALTH = 100;
	private static final int DEF_SPEED = 5;
	private static final int DEF_STRENGTH = 25;
	private int energy;
	private int strength;
	public int maxHealth;

	public Inventory inventory = new Inventory();
	private Chest chestOpen = null;
	private static final int DEF_PROTECTION = 5;
	public int protection;
	public Place location;

	public PlayableCharacter() {
		super("none", DEF_HEALTH);
		this.energy = DEF_NRG;
		this.maxHealth = DEF_HEALTH;

		this.strength = DEF_STRENGTH;
		this.location = null;
		this.protection = DEF_PROTECTION;
	}

	public boolean hasPlace() {
		return this.location != null;
	}

	public void talk(HelpingCharacter helper) {
		if (helper != null) {
			helper.speak();
		} else {
			System.out.println(" Nobody was found with this name\n#          ||");

		}
	}

	public boolean equip(String nameItem) {
		boolean retrn = false;
		Item item = this.inventory.getItemByName(nameItem);
		if (item != null) {
			if (item instanceof EquipableItem) {
				if (item instanceof SwordItem) {
					this.inventory.addSword((SwordItem) item);
					retrn = true;
					System.out.println("You are equiped with a weapon");
				} else if (item instanceof ShieldItem) {
					System.out.println("You are equiped with a shield");
					retrn = true;
					this.inventory.addShield((ShieldItem) item);
				}
			} else {
				System.out.println("This item is not equipable\n#          ||");
			}
		} else {
			System.out.println("tem not found\n#           ||");

		}
		return retrn;

	}

	public boolean unequip(String nameItem) {
		boolean retrn = false;
		switch (nameItem) {
		case "sword":
			if (this.inventory.removeSword()) {
				System.out.println("You have stored your weapon");
				retrn = true;
			}

		case "shield":
			if (this.inventory.removeShield()) {
				System.out.println("You havestored your shield");
				retrn = true;
			}

			break;
		default:
			System.out.println("Invalid command line\n");

		}
		return retrn;

	}

	public void attack(EnemyCharacter enemy) {
		if (this.inventory.hasSword()) {
			enemy.health -= this.inventory.sword.damage;
		} else {
			enemy.health -= this.strength;
		}
	}

	public boolean pickup(String itemName) {
		boolean retrn = false;
		Storage storage = null;
		Item item = null;
		if (this.isChestOpen()) {
			storage = this.chestOpen;
			item = this.chestOpen.getItemByName(itemName.toLowerCase());

		} else {
			storage = this.location.storage;
			item = this.location.storage.getItemByName(itemName.toLowerCase());
		}

		if (item != null) {
			if (this.strength >= item.weight) {
				if (this.inventory.add(item)) {
					System.out.println("" + item + " has been added to your inventory\n#          ||");
					storage.remove(item);
				} else {
					System.out.println("|| Your inventory is full\n#          ||");
				}
			} else {
				System.out.println("This is object is too heavy\n#          ||");
			}
		} else {
			System.out.println(" There is no " + itemName + "\n");
		}
		return retrn;
	}

	public boolean go(String exitName) {
		boolean retrn = false;
		if (this.location.exits.containsKey(exitName)) {
			if (!this.location.exits.get(exitName).isLocked()) {
				this.location = this.location.exits.get(exitName).place;
				System.out.println(
						"###############################################################################\n#\n#	Health:"
								+ this.health + "\n#");
				System.out.println(
						"#			 ,==================[ " + this.location + " ]==================\n#		    ||");
				this.location.displayDescription();

			} else {
				if (this.location.exits.get(exitName).getPadlock() instanceof PadlockKey)
					System.out.println(" You must unlock this exit with an item !\n#            ||");
				if (this.location.exits.get(exitName).getPadlock() instanceof PadlockCode)
					System.out.println(" You must unlock this exit with a code !\n#          ||");
			}
		} else {
			System.out.println(" There is no such exit\n#            ||");
		}
		return retrn;
	}

	public boolean look(String param) {
		boolean retrn = false;
		switch (param) {
		case "around":
			this.location.displayAll();
			retrn = true;
			break;
		case "floor":
			this.location.displayItems();
			retrn = true;
			break;
		}
		return retrn;
	}

	public boolean unlock(String command) {
		boolean retrn = false;
		String[] arrEntry = command.split(" with ");
		if (arrEntry.length == 2) {
			String nameUnlock = arrEntry[0];
			String objectName = arrEntry[1];
			Chest chest = null;
			Exit exit = null;
			chest = this.location.getChestByName(nameUnlock);
			exit = this.location.getExitByName(nameUnlock);
			if (chest != null) {
				if (chest.getPadlock() instanceof PadlockCode) {
					if (chest.unlock(objectName)) {
						retrn = true;
					}
					;
				} else if (chest.getPadlock() instanceof PadlockKey) {
					Item item = this.inventory.getItemByName(objectName);
					if (item != null) {
						if (chest.unlock(item)) {
							retrn = true;
						}
						;

					} else {
						System.out.println(" Item not found\n#          ||");
					}
				} else {
					System.out.println(" This chest is not locked\n");
				}

			} else if (exit != null) {
				if (exit.getPadlock() instanceof PadlockCode) {
					if (exit.unlock(objectName)) {
						retrn = true;
					}
					;
				} else if (exit.getPadlock() instanceof PadlockKey) {
					Item item = this.inventory.getItemByName(objectName);
					if (item != null) {
						if (exit.unlock(item)) {
							retrn = true;
						}
						;
					} else {
						System.out.println(" Item not found\n");
					}

				} else {
					System.out.println(" This exit is not locked\n");
				}

			} else {
				System.out.println(" The Item you wish to unlock doesn't exist\n");
			}
		}
		return retrn;

	}

	public boolean drop(String itemName) {
		boolean retrn = false;
		Storage storage = null;
		Item item = this.inventory.getItemByName(itemName);
		String libelleStorage;
		if (this.isChestOpen()) {
			storage = this.chestOpen;
			libelleStorage = "storage";

		} else {
			storage = this.location.storage;
			libelleStorage = "place";
		}

		if (item != null) {
			if (storage.add(item)) {
				if (this.inventory.remove(item)) {
					System.out.println(" " + item + " has been drop in the " + libelleStorage + "\n");
					retrn = true;
				} else {

				}
			} else {
				System.out.println(" There is no space to drop this item\n");
			}

		} else {
			System.out.println(" There is no " + itemName);
		}
		return retrn;
	}

	public boolean isChestOpen() {
		return this.chestOpen != null;
	}

	public void displayItemInOpenChest() {
		if (isChestOpen()) {
			this.chestOpen.displayItemList();
		}
	}

	public boolean openChest(String chestName) {
		Chest chest = this.location.chests.get(chestName);
		boolean retrn = false;

		if (chest != null) {
			if (chest.lock == false) {

				if (chest.open != true) {
					chest.open = true;
					this.chestOpen = chest;
					retrn = true;
					System.out.println("You have access to the chest\n");
					chest.displayItemList();
				} else {
					System.out.println("This chest is already open !\n");
				}
			} else {
				if (chest.getPadlock() instanceof PadlockKey)
					System.out.println("You must unlock this chest with an item !\n");
				else if (chest.getPadlock() instanceof PadlockCode)
					System.out.println("You must unlock this chest with a code !\n");
				else {
					System.out.println("ERROR");
				}
			}
		} else {
			System.out.println("This item doesn't exist or can't be opened\n");
		}

		return retrn;
	}

	public boolean closeChest() {
		boolean retrn = false;
		if (this.isChestOpen() == true) {
			this.chestOpen.open = false;
			this.chestOpen = null;
			retrn = true;
			System.out.println("You have closed the chest\n");

		} else {
			System.out.println("There is no chest to be closed !\n");
		}
		return retrn;
	}

	public Chest getOpenChest() {
		Chest retrn = null;
		if (this.isChestOpen()) {
			retrn = this.chestOpen;
		} else {
			retrn = null;
		}
		return retrn;
	}

	public boolean eat(String nameItem) {
		boolean retrn = false;
		Item item = this.inventory.getItemByName(nameItem);
		if (item != null) {

			boolean eaten = true;

			// increase or dicrease
			// character statistics
			if (item instanceof EadableItem) {

				if (((EadableItem) item).healthGain >= 0) {
					if (this.health == this.maxHealth) {
						System.out.println("Your health is at its peak");
						eaten = false;
						retrn = true;
					} else {
						System.out.println("You gained " + ((EadableItem) item).healthGain + " health");
						retrn = true;
					}
				} else {
					System.out.println("Oh-oh... This food might have been tainted");
					System.out.println("You lost " + ((EadableItem) item).healthGain + " health");
					retrn = true;
				}

				this.health += ((EadableItem) item).healthGain;

			} else {
				System.out.println("This item is not eadable");
				eaten = false;
			}

			if (eaten) {
				// si mangé, détruire l'item
				this.inventory.remove(item);
			}
		} else {
			System.out.println("Item not found");
		}
		return retrn;
	}

	public boolean talk(String nameHelper) {
		boolean retrn = false;
		if (this.location.helpingCharacters.containsKey(nameHelper)) {
			HelpingCharacter helper = this.location.helpingCharacters.get(nameHelper);
			if (helper != null) {
				helper.speak();
				retrn = true;
			} else {
				System.out.println("Nobody was found with this name\n");
			}
		} else {
			System.out.println("Nobody was found with this name\n");
		}
		return retrn;
	}

	public boolean fight(String enemyName) {
		boolean retrn = false;
		if (this.location.enemyCharacters.containsKey(enemyName)) {
			EnemyCharacter enemy = this.location.enemyCharacters.get(enemyName);
			int round = 0;
			String entry;
			boolean fled = false;

			Scanner scan = new Scanner(System.in);

			System.out.println("You are fighting " + enemy + "\n");
			do {
				System.out.println("Round " + round + "\n");
				System.out.println("Do you want to flee ? Y/N\n");
				do {
					entry = scan.nextLine().toLowerCase();
					if (entry.equals("y"))
						fled = true;
					if (entry.equals("n"))
						fled = false;
				} while (entry.equals("n") && entry.equals("y"));

				this.attack(enemy);
				enemy.attack(this);

				round++;
			} while (enemy.isAlive() && fled && this.isAlive());
			if (fled) {
				System.out.println("You fled\n");
				retrn = true;
			} else if (this.isAlive() && !enemy.isAlive()) {
				System.out.println("Congratulations ! You defeated " + enemy.toString() + " !\n");
				this.location.enemyCharacters.remove(enemy);
				retrn = true;
			} else if (!this.isAlive() || enemy.isAlive()) {
				System.out.println("WASTED : You are dead\n");
				retrn = true;
			}
		} else {
			System.out.println("Nobody was found with this name\n");
		}
		return retrn;
	}

	// cette fonction permet de connaitre la protection
	// du personnage en fonction de son equipement
	public int getProtection() {
		int protection;
		if (this.inventory.hasShield()) {
			protection = this.inventory.shield.protection;
		} else {
			protection = this.protection;
		}
		return protection;
	}

	// cette fonction permet de jauger les degats que prend
	// le personnage en fonction de son equipement
	public void hurt(int damage) {
		if (damage - getProtection() > 0) {
			this.health -= (damage - getProtection());
		}

		if (this.health < 0)
			this.health = 0;
	}

	public void dispInventory() {
		this.inventory.displayItemList();
	}

	public boolean isInventoryEmpty() {
		return this.inventory.isEmpty();
	}
}
