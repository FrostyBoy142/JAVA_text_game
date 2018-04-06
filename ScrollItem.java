package jeu_base_texte.item;

public class ScrollItem extends Item {

	public String message;

	ScrollItem(String name, int weight, String message) {
		super(name, weight);
		this.message = message;
	}

	public String read() {
		return this.message;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		ScrollItem other = (ScrollItem) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
