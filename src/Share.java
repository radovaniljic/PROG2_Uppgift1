public class Share extends Valuable {

	private int amount;
	private double price;

	public Share(String name, int amount, double price) {
		super(name);
		this.amount = amount;
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public double getPrice() {
		return price;
	}

	public void resetPrice() {
		price = 0;
	}

	public double getValue() {
		return (amount * price);
	}

	public String toString() {
		return String
				.format(getName() + ". Värde: " + addVAT() + ". Antal: " + getAmount() + ". Kurs: " + getPrice());
	}

}
