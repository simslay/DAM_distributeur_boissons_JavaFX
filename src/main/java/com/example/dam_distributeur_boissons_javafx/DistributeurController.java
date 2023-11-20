package com.example.dam_distributeur_boissons_javafx;

import com.example.dam_distributeur_boissons_javafx.model.Boisson;
import com.example.dam_distributeur_boissons_javafx.model.Distributeur;
import com.example.dam_distributeur_boissons_javafx.model.Price;
import com.example.dam_distributeur_boissons_javafx.ui.CardDialog;
import com.example.dam_distributeur_boissons_javafx.ui.PriceDialog;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DistributeurController implements Initializable {
    /** FXML attributes */
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

    /** Java UI attributes */
    private final Button b1 = new Button("1");
    private final Button b2 = new Button("2");
    private final Button b3 = new Button("3");
    private final Button b4 = new Button("4");
    private final Button b5 = new Button("5");
    private final Button b6 = new Button("6");
    private final Button b7 = new Button("7");
    private final Button b8 = new Button("8");
    private final Button b9 = new Button("9");
    private final List<Button> buttons = new ArrayList<>();

    /** Model attributes */
    private Distributeur distributeur;
    private List<Boisson> boissons;
    private Map<Boisson, Integer> boissonsQuantites;

    /** Constants attributes */
    private final String MONEY_BACK_TEXT = "Monaie rendue : ";
    private final String TOTAL_TEXT = "Total : ";
    private final String SORTIE_BOISSONS_TEXT = "Sortie boissons : ";
    private final String BOISSON_TEXT = "Boisson : ";
    private final String[] MARQUES = {
            "Coca",
            "Lipton_logo",
            "Logo-oasis",
            "Nespresso",
            "Cristaline",
            "Schweppes",
            "Nesquik_brand_logo.svg",
            "Logo_Orangina"
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
        setOnActions();
    }

    private void init() {
        distributeur = new Distributeur();
        boissons = distributeur.getBoissons();
        boissonsQuantites = distributeur.getBoissonsQuantites();

//        distributeur.createBoissons();
//        distributeur.createBoissonsFromDisk();
        distributeur.createBoissonsFromDiskJSON();
        initButtons();
        updateBoissonsGrid(false);

        lblTotal.setText(TOTAL_TEXT + distributeur.getTotal());
        lblNum.setText(BOISSON_TEXT + distributeur.getNumBoisson());
        lblMoneyBack.setText(MONEY_BACK_TEXT + distributeur.getMoneyBack());

        cbListeBoissons.setItems(FXCollections.observableList(boissons.stream().toList()));
    }

    private void initButtons() {
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
            String boissonText = "Boisson : ";
            distributeur.setNumBoisson(Integer.parseInt(b.getText()) - 1);

            try {
                Boisson boissonSelectionnee = Objects.requireNonNull(boissons.stream()
                        .filter(boisson -> distributeur.getNumBoisson() == boisson.getId())
                        .findAny()
                        .orElse(null)
                );
                int total = boissonSelectionnee.getPrix();

                lblNum.setText(boissonText + b.getText());
                distributeur.setBoissonSelectionnee(boissonSelectionnee);
            } catch (NullPointerException e) {
                distributeur.setTotal(-1);
                lblNum.setText(boissonText + "erreur");
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
    }

    private void updateBoissonsGrid(boolean removeNodes) {
        int index = 0;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                Boisson b = boissons.get(index);
                int id = b.getId();
                VBox vb = new VBox();
                Label lbl = new Label(
                        (id + 1) + ". " + b.getMarque() + " (" + boissonsQuantites.get(b) + ") : "
                                + convertCentsToEuros(b.getPrix()) + " €"
                );
                ImageView imageView = new ImageView(new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream("/" + MARQUES[index] + ".png")),
                        50,
                        50,
                        false,
                        false
                ));

                imageView.setPreserveRatio(true);
                vb.setSpacing(10);
                vb.getChildren().add(lbl);
                vb.getChildren().add(imageView);

                if (removeNodes) {
                    gpListeBoissons.getChildren().removeFirst();
                }

                gpListeBoissons.add(vb, i, j);
                index++;
            }
        }
    }

    private void setOnActions() {
        btnPieces.setOnAction(e -> insertCoins());
        btnCarte.setOnAction(e -> cardPayment());
        btnPaiement.setOnAction(e -> coinsPayment());
    }

    private void insertCoins() {
        Dialog<Price> priceDialog = new PriceDialog();
        Optional<Price> result = priceDialog.showAndWait();

        if (result.isPresent()) {
            Price price = result.get();
            int total = Integer.parseInt(price.getPrice());

            distributeur.setTotal(total);
            btnPaiement.setText("Choisir boisson");
            lblTotal.setText(TOTAL_TEXT + convertCentsToEuros(total) + " €");
        }
    }

    private void cardPayment() {
        if (distributeur.getNumBoisson() != 0) {
            int total = distributeur.getBoissonSelectionnee().getPrix();
            CardDialog cardDialog = new CardDialog(new Price(String.valueOf(total)), convertCentsToEuros(total));
            Optional<Price> result;

            result = cardDialog.showAndWait();

            if (result.isPresent()) {
                if (total == -1) {
                    lblTotal.setText(TOTAL_TEXT + "erreur");
                } else {
                    btnPaiement.setText("Merci !");
                    distributeur.getBoissonsQuantites().put(
                            distributeur.getBoissonSelectionnee(),
                            distributeur.getBoissonsQuantites().get(distributeur.getBoissonSelectionnee()) - 1
                    );
                    updateBoissonsGrid(true);
                    lblSortie.setText(SORTIE_BOISSONS_TEXT + distributeur.getBoissonSelectionnee().getMarque());
                }
            }
        }
    }

    private void coinsPayment() {
        if (distributeur.getTotal() != 0) {
            int total = distributeur.getTotal();
            int boissonPrice = Objects.requireNonNull(boissons.stream()
                            .filter(boisson -> distributeur.getNumBoisson() == boisson.getId())
                            .findAny()
                            .orElse(null))
                    .getPrix();
            int moneyBack = total - boissonPrice;

            distributeur.setMoneyBack(moneyBack);
            lblMoneyBack.setText(MONEY_BACK_TEXT + convertCentsToEuros(moneyBack) + " €");
            btnPaiement.setText("Merci !");
            distributeur.setTotal(0);
            lblTotal.setText(TOTAL_TEXT + distributeur.getTotal());
            distributeur.getBoissonsQuantites().put(
                    distributeur.getBoissonSelectionnee(),
                    distributeur.getBoissonsQuantites().get(distributeur.getBoissonSelectionnee()) - 1
            );
            updateBoissonsGrid(true);
            lblSortie.setText(SORTIE_BOISSONS_TEXT + distributeur.getBoissonSelectionnee().getMarque());
        }
    }

    private static double convertCentsToEuros(int price) {
        return price / 100.0;
    }
}