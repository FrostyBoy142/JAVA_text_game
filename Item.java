package jeu_base_texte.item;

public class Item {

	public String name;
	public int weight;
	public String description;

	public Item(String name, int weight) {
		this.name = name;
		this.weight = weight;

	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + weight;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return this.name + ", " + this.weight + " dag";
	}
}