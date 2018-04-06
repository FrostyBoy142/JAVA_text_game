package jeu_base_texte.storage;

import java.util.ArrayList;
import java.util.List;

import jeu_base_texte.item.Item;

public abstract class Storage {

	private static final int MAX_STORAGE = 50;
	private int availableSpace = MAX_STORAGE;

	public List<Item> list = new ArrayList<Item>();

	public abstract void displayItemList();

	public boolean isFull() {
		return this.availableSpace <= 0;
	}

	public boolean isEmpty() {
		return this.availableSpace == MAX_STORAGE;
	}

	public boolean add(Item item) {
		boolean b = false;
		if (!this.isFull()) {

			this.list.add(item);
			b = true;
			this.availableSpace--;
		}

		return b;
	}

	public boolean remove(Item item) {
		this.availableSpace++;
		return this.list.remove(item);
	}

	public Item getItemByName(String name) {
		Item item = null;
		boolean found = false;
		int i = 0;

		while (i < list.size() && !found) {

			if (name.equals(list.get(i).name.toLowerCase())) {
				item = list.get(i);
				found = true;
			}
			i++;
		}
		return item;
	}

	public void displayItemsDescription() {
		System.out.println("" + this.list.size());
		if (this.list.isEmpty()) {
			System.out.println("There are no items around you\n#		    ||");

		} else {
			for (Item item : this.list) {
				System.out.println("  - " + item + " : " + item.description);
			}
			System.out.println("\n#		    ||");
		}
	}
}