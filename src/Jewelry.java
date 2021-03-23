public class Jewelry extends Valuable {

	private int amountOfGems;
	private boolean material;

	public Jewelry(String name, int amountOfGems, boolean material) {
		super(name);
		this.amountOfGems = amountOfGems;
		this.material = material;
	}

	public int getAmountOfGems() {
		return amountOfGems;
	}

	public boolean isGolden() {
		return material;
	}

	public double getValue() {
		if (material == true) {
			return (2000 + (amountOfGems * 500));
		} else {
			return (700 + (amountOfGems * 500));
		}
	}

	public String toString() {
		if (material == true) {
			return String.format(
					getName() + ". Värde: " + addVAT() + ". Av: Guld. Antal ädelstenar: " + getAmountOfGems());
		} else {
			return String.format(
					getName() + ". Värde: " + addVAT() + ". Av: Silver. Antal ädelstenar: " + getAmountOfGems());
		}
	}

}
