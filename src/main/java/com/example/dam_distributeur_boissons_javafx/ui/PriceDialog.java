package com.example.dam_distributeur_boissons_javafx.ui;

import com.example.dam_distributeur_boissons_javafx.model.Price;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class PriceDialog extends Dialog<Price> {
    private final Price price;
    private TextField tfPrice;

    public PriceDialog() {
        super();
        this.setTitle("Ajouter pieces");
        this.price = new Price("0");
        buildUI();
        setPropertyBindings();
        setResultConverter();
    }

    private void buildUI() {
        Pane pane = createGridPane();
        getDialogPane().setContent(pane);

//        getDialogPane().getStylesheets().add(getClass().getResource("application.css").toExternalForm());

//        Node image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/money.png"))));
//        getDialogPane().setGraphic(image);
//        getDialogPane().setHeader(image);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button button = (Button) getDialogPane().lookupButton(ButtonType.OK);
        button.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!validateDialog()) {
                    event.consume();
                }
            }

            private boolean validateDialog() {
                return !tfPrice.getText().isEmpty();
            }
        });
        getDialogPane().expandableContentProperty().set(new Label("Entrez vos pieces s'il vous plaît."));
        getDialogPane().setExpanded(true);
    }

    private void setPropertyBindings() {
        tfPrice.textProperty().bindBidirectional(price.priceProperty());
    }

    public Pane createGridPane() {
        VBox content = new VBox(10);

        Label amountLabel = new Label("Montant en cents");
        this.tfPrice = new TextField();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.add(amountLabel, 0, 0);
        grid.add(tfPrice, 0, 1);
        GridPane.setHgrow(this.tfPrice, Priority.ALWAYS);

        content.getChildren().add(grid);

        return content;
    }

    private void setResultConverter() {
        Callback<ButtonType, Price> personResultConverter = new Callback<ButtonType, Price>() {
            @Override
            public Price call(ButtonType param) {
                if (param == ButtonType.OK) {
                    return price;
                } else {
                    return null;
                }
            }
        };
        setResultConverter(personResultConverter);
    }
}
