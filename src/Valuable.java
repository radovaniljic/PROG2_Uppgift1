abstract public class Valuable {
	private String name;

	public Valuable(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public double addVAT() {
		return getValue() * 1.25;
	}

	abstract public double getValue();

}
