package jeu_base_texte.objective;

import jeu_base_texte.Game;
import jeu_base_texte.Place;
import jeu_base_texte.character.PlayableCharacter;

public class GoPlaceObjective extends Objective {
	Place place;

	public GoPlaceObjective(String description, Place place) {
		super(description);
		this.place = place;
	}

	@Override
	public void checkIfAchieved(Game game) {
		PlayableCharacter hero = (PlayableCharacter) game.getCharacter();
		if (hero.location.equals(this.place)) {
			this.succeeded = true;
		}
	}

}
