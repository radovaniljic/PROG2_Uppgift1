import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	private Button menuStart = new Button("Välj värdesak");
	private ContextMenu contextMenu = new ContextMenu();
	private ArrayList<Valuable> valuablesList = new ArrayList<>();
	private TextArea display;
	private RadioButton nameButton;
	private RadioButton valueButton;

	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		display = new TextArea();
		display.setEditable(false);
		root.setCenter(display);
		root.setStyle("-fx-font-size: 16");

		FlowPane bottom = new FlowPane();
		root.setBottom(bottom);

		FlowPane top = new FlowPane();
		top.setPadding(new Insets(5));
		root.setTop(top);

		Label header = new Label("Värdesaker");
		header.setStyle("-fx-font-weight: bold");
		top.getChildren().add(header);
		top.setAlignment(Pos.CENTER);

		VBox right = new VBox();
		right.setPadding(new Insets(15));

		Label sorting = new Label("Sortering");
		sorting.setStyle(("-fx-font-weight: bold"));
		right.getChildren().add(sorting);

		nameButton = new RadioButton("Namn");
		nameButton.setPadding(new Insets(5));
		nameButton.setOnAction(new SortingHandler());

		valueButton = new RadioButton("Värde");
		valueButton.setPadding(new Insets(5));
		valueButton.setOnAction(new SortingHandler());

		right.getChildren().addAll(nameButton, valueButton);
		root.setRight(right);

		ToggleGroup group = new ToggleGroup();
		nameButton.setToggleGroup(group);
		valueButton.setToggleGroup(group);

		Label newLabel = new Label("Ny: ");
		bottom.getChildren().add(newLabel);
		newLabel.setStyle("-fx-font-weight: bold");

		MenuItem jewelry = new MenuItem("Smycke");
		jewelry.setOnAction(new JewelryHandler());

		MenuItem share = new MenuItem("Aktie");
		share.setOnAction(new ShareHandler());

		MenuItem apparatus = new MenuItem("Apparat");
		apparatus.setOnAction(new ApparatusHandler());

		bottom.getChildren().add(menuStart);
		contextMenu.getItems().addAll(jewelry, share, apparatus);
		menuStart.setOnAction(new MenuHandler());

		Button showButton = new Button("Visa");
		bottom.getChildren().add(showButton);
		showButton.setOnAction(new ShowHandler());

		Button shareCrashButton = new Button("Börskrasch");
		shareCrashButton.setOnAction(new ShareCrashHandler());

		bottom.getChildren().add(shareCrashButton);
		bottom.setAlignment(Pos.CENTER);
		bottom.setHgap(5);
		bottom.setPadding(new Insets(10));

		Scene scene = new Scene(root, 800, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Sakregister");

	}

	class SortingHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (nameButton.isSelected()) {
				valuablesList.sort(Comparator.comparing(Valuable::getName));
			} else {
				if (valueButton.isSelected()) {
					valuablesList.sort(Comparator.comparing(Valuable::getValue).reversed());
				}
			}
		}
	}

	class ShareCrashHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			for (Valuable v : valuablesList) {
				if (v instanceof Share) {
					((Share) v).resetPrice();
				}
			}
		}
	}

	class MenuHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			contextMenu.show(menuStart, Side.BOTTOM, 0.0, 0.0);

		}
	}

	class JewelryHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				JewelryAlert dialog = new JewelryAlert();
				dialog.setHeaderText(null);
				dialog.setTitle("Nytt smycke");
				Optional<ButtonType> result = dialog.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					boolean golden = dialog.isGolden();
					
					String name = dialog.getName();
					if (name.trim().isEmpty()) {
						Alert nameMessage = new Alert(AlertType.ERROR, "Namnet kan inte vara tomt");
						nameMessage.setHeaderText(null);
						nameMessage.showAndWait();
					}
					
					if (isNameValid(name) == false) {
						Alert gemMessage = new Alert(AlertType.ERROR, "Namnet kan bara innehålla bokstäverna a-ö");
						gemMessage.setHeaderText(null);
						gemMessage.showAndWait();
					}

					int gems = dialog.getAmountOfGems();
					if (gems < 0) {
						Alert gemMessage = new Alert(AlertType.ERROR, "Antalet ädelstenar kan inte vara mindre än 0");
						gemMessage.setHeaderText(null);
						gemMessage.showAndWait();
					}
					
					if (!name.trim().isEmpty() && isNameValid(name) == true && gems >= 0) {
					Jewelry j = new Jewelry(name, gems, golden);
					valuablesList.add(j);
					}
				}
			} catch (NumberFormatException e) {
				Alert msg = new Alert(AlertType.ERROR, "Fel inmatning");
				msg.setHeaderText(null);
				msg.showAndWait();				
			}
		}
		
		public boolean isNameValid(String name) {
			return name.matches("[a-öA-Ö]+");
		}
	}
	
	class ShareHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				ShareAlert dialog = new ShareAlert();
				dialog.setHeaderText(null);
				dialog.setTitle("Ny aktie");
				Optional<ButtonType> result = dialog.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					String name = dialog.getName();
					if (name.trim().isEmpty()) {
						Alert nameMessage = new Alert(AlertType.ERROR, "Namnet kan inte vara tomt");
						nameMessage.setHeaderText(null);
						nameMessage.showAndWait();
					}
					
					if (isNameValid(name) == false) {
						Alert gemMessage = new Alert(AlertType.ERROR, "Namnet kan bara innehålla bokstäverna a-ö");
						gemMessage.setHeaderText(null);
						gemMessage.showAndWait();
					}
					
					int amount = dialog.getAmount();
					if (amount < 1) {
						Alert amountMessage = new Alert(AlertType.ERROR, "Antalet kan inte vara mindre än 1");
						amountMessage.setHeaderText(null);
						amountMessage.showAndWait();
					}
					double price = dialog.getPrice();
					if (price < 1) {
						Alert priceMessage = new Alert(AlertType.ERROR, "Priset kan inte vara mindre än 1");
						priceMessage.setHeaderText(null);
						priceMessage.showAndWait();
						
					}
					if (!name.trim().isEmpty() && isNameValid(name) == true && amount >= 1 && price >= 1) {
					Share s = new Share(name, amount, price);
					valuablesList.add(s);
					}
				}
			} catch (NumberFormatException e) {
				Alert msg = new Alert(AlertType.ERROR, "Fel inmatning");
				msg.setHeaderText(null);
				msg.showAndWait();
			}

		}
		
		public boolean isNameValid(String name) {
			return name.matches("[a-öA-Ö]+");
		}
	}

	class ApparatusHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				ApparatusAlert dialog = new ApparatusAlert();
				dialog.setHeaderText(null);
				dialog.setTitle("Ny apparat");
				Optional<ButtonType> result = dialog.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					
					String name = dialog.getName();
					if (name.trim().isEmpty() ) {
						Alert nameMessage = new Alert(AlertType.ERROR, "Namnet kan inte vara tomt");
						nameMessage.setHeaderText(null);
						nameMessage.showAndWait();
					}
					
					if (isNameValid(name) == false) {
						Alert gemMessage = new Alert(AlertType.ERROR, "Namnet kan bara innehålla bokstäverna a-ö");
						gemMessage.setHeaderText(null);
						gemMessage.showAndWait();
					}

					int price = dialog.getPrice();
					if (price < 1) {
						Alert priceMessage = new Alert(AlertType.ERROR, "Priset kan inte vara mindre än 1");
						priceMessage.setHeaderText(null);
						priceMessage.showAndWait();

					}

					int condition = dialog.getCondition();
					if (condition < 1 || condition > 10) {
						Alert conditionMessage = new Alert(AlertType.ERROR, "Kan inte vara mindre än 1 eller större än 10");
						conditionMessage.setHeaderText(null);
						conditionMessage.showAndWait();
					}
					if (!name.trim().isEmpty() && isNameValid(name) == true && price >= 1 && (condition >= 1 && condition <= 10)) {
					Apparatus a = new Apparatus(name, price, condition);
					valuablesList.add(a);
					}
				}
			} catch (NumberFormatException e) {
				Alert msg = new Alert(AlertType.ERROR, "Fel inmatning");
				msg.setHeaderText(null);
				msg.showAndWait();
			}
		}
		
		public boolean isNameValid(String name) {
			return name.matches("[a-öA-Ö]+");
		}
	}

	class ShowHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			display.clear();
			nameButton.setSelected(false);
			valueButton.setSelected(false);
			for (Valuable v : valuablesList) {
				if (v instanceof Jewelry) {;
					display.appendText(v.toString() + "\n");
				}
				if (v instanceof Share) {
					display.appendText(v.toString() + "\n");
				}
				if (v instanceof Apparatus) {
					display.appendText(v.toString() + "\n");
				}
			}
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

}
