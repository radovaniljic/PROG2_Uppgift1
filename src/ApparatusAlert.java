import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ApparatusAlert extends Alert {
	private TextField name = new TextField();
	private TextField price = new TextField();
	private TextField condition = new TextField();

	public ApparatusAlert() {
		super(AlertType.CONFIRMATION);
		GridPane grid = new GridPane();

		Label nameLabel = new Label("Namn");
		nameLabel.setStyle("-fx-font-weight: bold");
		grid.addRow(0, nameLabel, name);

		Label priceLabel = new Label("Inköpspris");
		priceLabel.setStyle("-fx-font-weight: bold");
		grid.addRow(1, priceLabel, price);

		Label conditionLabel = new Label("Skick");
		conditionLabel.setStyle("-fx-font-weight: bold");
		grid.addRow(2, conditionLabel, condition);

		getDialogPane().setContent(grid);

	}

	public String getName() {
		return name.getText();
	}

	public int getPrice() {
		return Integer.parseInt(price.getText());
	}

	public int getCondition() {
		return Integer.parseInt(condition.getText());
	}

}