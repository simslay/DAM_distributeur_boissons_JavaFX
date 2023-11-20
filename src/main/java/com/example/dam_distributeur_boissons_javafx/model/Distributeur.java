package com.example.dam_distributeur_boissons_javafx.model;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Distributeur implements IDistributeur {
    private Map<Boisson, Integer> boissonsQuantites = new HashMap<>();
    private List<Boisson> boissons = new ArrayList<>();
    private int total = 0;
    private int numBoisson = 0;
    private int moneyBack = 0;
    private Boisson boissonSelectionnee;

    public Distributeur() {
    }

    public Distributeur(Map<Boisson, Integer> boissonsQuantites) {
        this.boissonsQuantites = boissonsQuantites;
    }

    /**
     * trie les boissons par chaude ou froide
     */
    public void sortChaudesFirst() {
        boissons = boissons.stream().sorted(Comparator.comparing(Boisson::isFroide)).collect(Collectors.toList());
    }

    public void sortFroidesFirst() {
        boissons = boissons.stream().sorted(Comparator.comparing(Boisson::isChaude)).collect(Collectors.toList());
    }

    public List<Boisson> getBoissonsChaudes() {
        return boissons.stream().filter(Boisson::isChaude).collect(Collectors.toList());
    }

    public List<Boisson> getBoissonsFroides() {
        return boissons.stream().filter(Boisson::isFroide).collect(Collectors.toList());
    }

    public void createBoissonsFromDiskJSON() {
        InputStream inputStream = getClass().getResourceAsStream("/boissons.json");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        JSONTokener jsonTokener = new JSONTokener(bufferedReader);
        JSONArray jsonArray = new JSONArray(jsonTokener);

        for(Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            Boisson b = new Boisson();

            b.setId(jsonObject.getInt("id"));
            b.setMarque(jsonObject.getString("marque"));
            b.setPrix(jsonObject.getInt("prix"));
            b.setChaude(jsonObject.getBoolean("chaude"));

            boissons.add(b);
            boissonsQuantites.put(b, 10);
        }
    }

    public void createBoissonsFromDisk() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/boissons.txt");

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String l;

                while ((l = reader.readLine()) != null) {
                    Boisson b = new Boisson();
                    String[] properties = l.split(",");

                    b.setId(Integer.parseInt(properties[0]));
                    b.setMarque(properties[1]);
                    b.setPrix(Integer.parseInt(properties[2]));
                    b.setChaude(Boolean.parseBoolean(properties[3]));

                    boissons.add(b);
                    boissonsQuantites.put(b, 10);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            finally {
                inputStream.close();
            }
        } else {
            System.err.println("Le fichier n'a pas été trouvé dans les ressources.");
        }
    }

    @Override
    public void createBoissons() {
        Boisson b1 = new Boisson();
        Boisson b2 = new Boisson();
        Boisson b3 = new Boisson();
        Boisson b4 = new Boisson();
        Boisson b5 = new Boisson();
        Boisson b6 = new Boisson();
        Boisson b7 = new Boisson();
        Boisson b8 = new Boisson();

        b1.setId(0);
        b1.setMarque("Coca");
        b1.setPrix(180);
        b1.setChaude(false);
        b2.setId(1);
        b2.setMarque("Lipton");
        b2.setPrix(100);
        b2.setChaude(false);
        b3.setId(2);
        b3.setMarque("Oasis");
        b3.setPrix(150);
        b3.setChaude(false);
        b4.setId(3);
        b4.setMarque("Nespresso");
        b4.setPrix(80);
        b4.setChaude(true);
        b5.setId(4);
        b5.setMarque("Cristaline");
        b5.setPrix(80);
        b5.setChaude(false);
        b6.setId(5);
        b6.setMarque("Schweppes");
        b6.setPrix(100);
        b6.setChaude(false);
        b7.setId(6);
        b7.setMarque("Nesquik");
        b7.setPrix(80);
        b7.setChaude(true);
        b8.setId(7);
        b8.setMarque("Orangina");
        b8.setPrix(150);
        b8.setChaude(false);

        boissons.add(b1);
        boissons.add(b2);
        boissons.add(b3);
        boissons.add(b4);
        boissons.add(b5);
        boissons.add(b6);
        boissons.add(b7);
        boissons.add(b8);

        boissonsQuantites.put(b1, 10);
        boissonsQuantites.put(b2, 10);
        boissonsQuantites.put(b3, 10);
        boissonsQuantites.put(b4, 10);
        boissonsQuantites.put(b5, 10);
        boissonsQuantites.put(b6, 10);
        boissonsQuantites.put(b7, 10);
        boissonsQuantites.put(b8, 10);
    }

    public static Map<String, Integer> createPrix(List<Boisson> boissons) {
        Map<String, Integer> prix = new HashMap<>();

        boissons.forEach(b -> prix.put(b.getMarque(), b.getPrix()));

        return prix;
    }

    public Map<Boisson, Integer> getBoissonsQuantites() {
        return boissonsQuantites;
    }

    public void setBoissonsQuantites(Map<Boisson, Integer> boissonsQuantites) {
        this.boissonsQuantites = boissonsQuantites;
    }

    public List<Boisson> getBoissons() {
        return boissons;
    }

    public void setBoissons(List<Boisson> boissons) {
        this.boissons = boissons;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMoneyBack() {
        return this.moneyBack;
    }

    public void setMoneyBack(int moneyBack) {
        this.moneyBack = moneyBack;
    }

    public int getNumBoisson() {
        return numBoisson;
    }

    public void setNumBoisson(int numBoisson) {
        this.numBoisson = numBoisson;
    }

    public Boisson getBoissonSelectionnee() {
        return boissonSelectionnee;
    }

    public void setBoissonSelectionnee(Boisson boissonSelectionnee) {
        this.boissonSelectionnee = boissonSelectionnee;
    }

    /**
     * Redefinition de toString()
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

//        for(Boisson b : boissons) {
//            res.append(b);
//            res.append("\n");
//        }

        boissons.forEach(b -> {
            res.append(b);
            res.append("\n");
//            res.append(boissons.indexOf(b));
//            res.append("\n");
        });

        res.deleteCharAt(res.length() - 1);

        return res.toString();
    }
}
