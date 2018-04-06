package jeu_base_texte.padlock;

public interface Padlock {
	/*
	 * On utilise ici la classe objet , afin de pouvoir dévérouiller un porte ou
	 * un coffre avec non seulement un item mais aussi un String (exemple de cadenas
	 * a code)
	 */
	boolean unlock(Object obj);
}