package com.example.dam_distributeur_boissons_javafx.model;

public class Boisson implements IBoisson {
    private int id;
    private String marque;
    private String matiere;
    private int prix;
    private String contenu;
    private String couleur;
    private boolean chaude;
    private double diametre;
    private double hauteur;

    public Boisson() {
    }

    public Boisson(String marque, String matiere, int prix, String contenu, String couleur) {
        this.marque = marque;
        this.matiere = matiere;
        this.prix = prix;
        this.contenu = contenu;
        this.couleur = couleur;
    }

    @Override
    public double getVolume() {
        return Math.PI * this.hauteur * (this.diametre/2) * (this.diametre/2);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public boolean isChaude() {
        return chaude;
    }
    public boolean isFroide() {
        return !chaude;
    }

    public void setChaude(boolean chaude) {
        this.chaude = chaude;
    }

    public double getDiametre() {
        return diametre;
    }

    public void setDiametre(double diametre) {
        this.diametre = diametre;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    @Override
    public String toString() {
        return getMarque() + "\n" +
                (isChaude() ? "Chaude" : "Froide") + "\n" +
                getPrix() + " cents\n";
    }
}
