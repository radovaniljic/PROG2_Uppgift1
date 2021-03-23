import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ShareAlert extends Alert {
	private TextField name = new TextField();
	private TextField amount = new TextField();
	private TextField price = new TextField();

	public ShareAlert() {
		super(AlertType.CONFIRMATION);
		GridPane grid = new GridPane();

		Label nameLabel = new Label("Namn");
		nameLabel.setStyle("-fx-font-weight: bold");
		grid.addRow(0, nameLabel, name);

		Label amountLabel = new Label("Antal");
		amountLabel.setStyle("-fx-font-weight: bold");
		grid.addRow(1, amountLabel, amount);

		Label priceLabel = new Label("Pris");
		priceLabel.setStyle("-fx-font-weight: bold");
		grid.addRow(2, priceLabel, price);

		getDialogPane().setContent(grid);

	}

	public String getName() {
		return name.getText();
	}

	public int getAmount() {
		return Integer.parseInt(amount.getText());
	}

	public double getPrice() {
		return Double.parseDouble(price.getText());
	}

}