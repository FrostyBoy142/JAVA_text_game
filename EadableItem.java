package jeu_base_texte.item;

public class EadableItem extends Item {

	public int healthGain;

	public EadableItem(String name, int weight, int healthGain) {
		super(name, weight);
		this.healthGain = healthGain;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + healthGain;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EadableItem other = (EadableItem) obj;
		if (healthGain != other.healthGain)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.name + ": " + this.healthGain + " HP " + this.weight + " dag";
	}

}