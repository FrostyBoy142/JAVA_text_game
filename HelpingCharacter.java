package jeu_base_texte.character;

public class HelpingCharacter extends Character {

	private String speech;

	public void speak() {
		System.out.println("" + this.speech + "\n#		    ||");
	}

	public HelpingCharacter(String name, String speech) {
		super(name, 1);
		this.speech = speech;
	}

	@Override
	public String toString() {
		return this.name;
	}
}