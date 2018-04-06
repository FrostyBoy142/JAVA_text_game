package jeu_base_texte.character;

public abstract class Character {

	public String name;
	public int health;

	public Character(String name, int health) {
		this.name = name;
		this.health = health;
	}

	boolean isAlive() {
		return health > 0;
	}

}