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

public class CardDialog extends Dialog<Price> {
    private Price price;
    private double priceToDisplay;
    private GridPane gpCode;

    public CardDialog(Price price, double priceToDisplay) {
        super();
        this.setTitle("Enter code");
        this.price = price;
        this.priceToDisplay = priceToDisplay;
        buildUI();
        setPropertyBindings();
        setResultConverter();
    }

    private void buildUI() {
        Pane pane = createGridPane();
        getDialogPane().setContent(pane);

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
                return true;
            }
        });
        getDialogPane().expandableContentProperty().set(
                new Label("Entrez votre code (montant à payer : " + this.priceToDisplay + " €).")
        );
        getDialogPane().setExpanded(true);
    }

    private void setPropertyBindings() {

    }

    public Pane createGridPane() {
        VBox content = new VBox(10);

        Button b1 = new Button("1");
        Button b2 = new Button("2");
        Button b3 = new Button("3");
        Button b4 = new Button("4");
        Button b5 = new Button("5");
        Button b6 = new Button("6");
        Button b7 = new Button("7");
        Button b8 = new Button("8");
        Button b9 = new Button("9");

        Label codeLabel = new Label("Code");
        this.gpCode = new GridPane();
        this.gpCode.add(b7, 0, 0);
        this.gpCode.add(b8, 1, 0);
        this.gpCode.add(b9, 2, 0);
        this.gpCode.add(b4, 0, 1);
        this.gpCode.add(b5, 1, 1);
        this.gpCode.add(b6, 2, 1);
        this.gpCode.add(b1, 0, 2);
        this.gpCode.add(b2, 1, 2);
        this.gpCode.add(b3, 2, 2);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.add(codeLabel, 0, 0);
        grid.add(gpCode, 0, 1);
        GridPane.setHgrow(this.gpCode, Priority.ALWAYS);

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

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public double getPriceToDisplay() {
        return priceToDisplay;
    }

    public void setPriceToDisplay(double priceToDisplay) {
        this.priceToDisplay = priceToDisplay;
    }
}
