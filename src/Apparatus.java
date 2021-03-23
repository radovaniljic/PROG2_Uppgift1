public class Apparatus extends Valuable {

	private double price;
	private double condition;

	public Apparatus(String name, int price, int condition) {
		super(name);
		this.price = price;
		this.condition = condition;
	}

	public double getPrice() {
		return price;
	}

	public double getCondition() {
		return condition;
	}

	public double getValue() {
		return (price * (condition / 10));
	}

	public String toString() {
		return String.format(
				getName() + ". Värde: " + addVAT() + ". Inköpspris: " + getPrice() + ". Skick: " + getCondition());
	}

}
