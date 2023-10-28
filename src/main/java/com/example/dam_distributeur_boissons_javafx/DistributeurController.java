package com.example.dam_distributeur_boissons_javafx;

import com.example.dam_distributeur_boissons_javafx.distributeur.Boisson;
import com.example.dam_distributeur_boissons_javafx.distributeur.Distributeur;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class DistributeurController implements Initializable {
    @FXML
    private Label lblSortie;
    @FXML
    private Label lblNum;
    @FXML
    private Button btnCarte;
    @FXML
    private Button btnPieces;
    @FXML
    private Button btnPaiement;
    @FXML
    private Label lblMoneyBack;
    @FXML
    private GridPane gpDigits;
    @FXML
    private Label lblTotal;
    @FXML
    private ComboBox cbListeBoissons;
    @FXML
    private GridPane gpListeBoissons;

//    @FXML
//    private Label welcomeText;

//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//        btnPaiement.setText("Merci !");
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Boisson> boissons;
        Map<Boisson, Integer> boissonsQuantites;
        Distributeur distributeur = new Distributeur();
        List<Button> buttons = new ArrayList<>();
        Dialog<Price> priceDialog = new PriceDialog(new Price("0"));
        CardDialog cardDialog = new CardDialog(new Price("0"));
        Button b1 = new Button("1");
        Button b2 = new Button("2");
        Button b3 = new Button("3");
        Button b4 = new Button("4");
        Button b5 = new Button("5");
        Button b6 = new Button("6");
        Button b7 = new Button("7");
        Button b8 = new Button("8");
        Button b9 = new Button("9");
        int index = 0;
        String moneyBackText = "Monaie rendue : ";
        String[] marques = {
                "Coca",
                "Lipton_logo",
                "Logo-oasis",
                "Nespresso",
                "Cristaline",
                "Schweppes",
                "Nesquik_brand_logo.svg",
                "Logo_Orangina"
        };

        boissons = distributeur.getBoissons();
        boissonsQuantites = distributeur.getBoissonsQuantites();

        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        buttons.add(b4);
        buttons.add(b5);
        buttons.add(b6);
        buttons.add(b7);
        buttons.add(b8);
        buttons.add(b9);

        buttons.forEach(b -> b.setOnAction(actionEvent -> {
            distributeur.setNumBoisson(Integer.parseInt(b.getText()) - 1);
            try {
                Boisson boissonSelectionnee = Objects.requireNonNull(boissons.stream()
                        .filter(boisson -> distributeur.getNumBoisson() == boisson.getId())
                        .findAny()
                        .orElse(null)
                );
                int total = boissonSelectionnee.getPrix();
//                distributeur.setTotal(total);
                lblNum.setText("Boisson : " + b.getText());
                distributeur.setBoissonSelectionnee(boissonSelectionnee);
            } catch (NullPointerException e) {
                distributeur.setTotal(-1);
                lblNum.setText("Boisson : erreur");
            }
        }));

        gpDigits.add(b7, 0, 0);
        gpDigits.add(b8, 1, 0);
        gpDigits.add(b9, 2, 0);
        gpDigits.add(b4, 0, 1);
        gpDigits.add(b5, 1, 1);
        gpDigits.add(b6, 2, 1);
        gpDigits.add(b1, 0, 2);
        gpDigits.add(b2, 1, 2);
        gpDigits.add(b3, 2, 2);

        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 4; j++) {
                Boisson b = boissons.get(index);
                int id = b.getId();
                VBox vb = new VBox();
                vb.setSpacing(10);
                Label lbl = new Label(
                        (id + 1) + ". " + b.getMarque() + " (" + boissonsQuantites.get(b) + ") : "
                                + convertCentsToEuros(b.getPrix()) + " €"
                );
                ImageView imageView = new ImageView(new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream("/" + marques[index] + ".png")),
                        50,
                        50,
                        false,
                        false
                ));
                imageView.setPreserveRatio(true);
                vb.getChildren().add(lbl);
                vb.getChildren().add(imageView);
                gpListeBoissons.add(vb, i, j);
                index++;
            }
        }

        lblTotal.setText("Total : " + distributeur.getTotal());
        lblNum.setText("Boisson : " + distributeur.getNumBoisson());
        lblMoneyBack.setText(moneyBackText + distributeur.getMoneyBack());

        cbListeBoissons.setItems(FXCollections.observableList(boissons.stream().toList()));

        btnPieces.setOnAction(e -> {
            Optional<Price> result = priceDialog.showAndWait();
            if (result.isPresent()) {
                Price price = result.get();
                int total = Integer.parseInt(price.getPrice());
                int moneyBack = distributeur.getMoneyBack();
                distributeur.setTotal(total);
                btnPaiement.setText("Choisir boisson");
                lblTotal.setText("Total : " + convertCentsToEuros(total) + " €");
//                lblMoneyBack.setText(moneyBackText + moneyBack);
            }
        });

        btnCarte.setOnAction(e -> {
            if (distributeur.getNumBoisson() != 0) {
                cardDialog.setPrice(new Price(String.valueOf(distributeur.getTotal())));
                Optional<Price> result = cardDialog.showAndWait();
                if (result.isPresent()) {
                    Price price = new Price(String.valueOf(distributeur.getTotal()));
                    if (price.getPrice().equals("-1")) {
                        lblTotal.setText("Total : erreur");
                    } else {
                        lblTotal.setText("Total : " + convertCentsToEuros(distributeur.getTotal()) + " €");
                        btnPaiement.setText("Merci !");
                        distributeur.getBoissonsQuantites().put(
                                distributeur.getBoissonSelectionnee(),
                                distributeur.getBoissonsQuantites().get(distributeur.getBoissonSelectionnee()) - 1
                        );
                        lblSortie.setText("Sortie boissons : " + distributeur.getBoissonSelectionnee().getMarque());
                    }
                }
            }
        });

        btnPaiement.setOnAction(e -> {
            if (distributeur.getTotal() != 0) {
                int total = distributeur.getTotal();
                int boissonPrice = Objects.requireNonNull(boissons.stream()
                                .filter(boisson -> distributeur.getNumBoisson() == boisson.getId())
                                .findAny()
                                .orElse(null))
                        .getPrix();
                int moneyBack = total - boissonPrice;
                distributeur.setMoneyBack(moneyBack);
                lblMoneyBack.setText(moneyBackText + convertCentsToEuros(moneyBack) + " €");
                btnPaiement.setText("Merci !");
                distributeur.setTotal(0);
                lblTotal.setText("Total : " + distributeur.getTotal());
                distributeur.getBoissonsQuantites().put(
                        distributeur.getBoissonSelectionnee(),
                        distributeur.getBoissonsQuantites().get(distributeur.getBoissonSelectionnee()) - 1
                );
                int index2 = 0;
                for(int i = 0; i < 2; i++) {
                    for(int j = 0; j < 4; j++) {
                        Boisson b = boissons.get(index2);
                        int id = b.getId();
                        VBox vb = new VBox();
                        vb.setSpacing(10);
                        Label lbl = new Label(
                                (id + 1) + ". " + b.getMarque() + " (" + boissonsQuantites.get(b) + ") : "
                                        + convertCentsToEuros(b.getPrix()) + " €"
                        );
                        ImageView imageView = new ImageView(new Image(
                                Objects.requireNonNull(getClass().getResourceAsStream("/" + marques[index2] + ".png")),
                                50,
                                50,
                                false,
                                false
                        ));
                        imageView.setPreserveRatio(true);
                        vb.getChildren().add(lbl);
                        vb.getChildren().add(imageView);
                        gpListeBoissons.add(vb, i, j);
                        index2++;
                    }
                }
            }

            lblSortie.setText("Sortie boissons : " + distributeur.getBoissonSelectionnee().getMarque());
        });
    }

    private double convertCentsToEuros(int price) {
        return price / 100.0;
    }
}