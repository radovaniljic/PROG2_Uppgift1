import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class JewelryAlert extends Alert {
	private TextField name = new TextField();
	private TextField gem = new TextField();
	private CheckBox box = new CheckBox("Av guld");

	public JewelryAlert() {
		super(AlertType.CONFIRMATION);
		GridPane grid = new GridPane();

		Label nameLabel = new Label("Namn");
		nameLabel.setStyle("-fx-font-weight: bold");
		grid.addRow(0, nameLabel, name);

		Label gemLabel = new Label("Stenar");
		gemLabel.setStyle("-fx-font-weight: bold");
		grid.addRow(1, gemLabel, gem);

		grid.addRow(2, box);
		box.setPadding(new Insets(5));

		getDialogPane().setContent(grid);

	}

	public String getName() {
		return name.getText();
	}

	public int getAmountOfGems() {
		return Integer.parseInt(gem.getText());
	}

	public boolean isGolden() {
		return box.isSelected();
	}


}
