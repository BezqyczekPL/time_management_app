package pl.pollub.projektandroid;

import java.io.Serializable;


public class Lista_Element implements Serializable
{

    private int id;

    private String name;
    private String amount;
    private String data;
    private String login;
    private boolean czyWcisnieto;

    public Lista_Element(String name, String amount, boolean czyWcisnieto, String data, String login)
    {
        this.name = name;
        this.amount = amount;
        this.czyWcisnieto = czyWcisnieto;
        this.login = login;
        this.data = data;
    }

    public boolean czyWcisnieto()
    {
        return czyWcisnieto;
    }

    public void ustawCheckbox(boolean czyWcisnieto)
    {
        this.czyWcisnieto = czyWcisnieto;
    }

    public String getProdukt() {
        return name;
    }

    public void setProdukt(String produkt) {
        this.name = produkt;
    }

    public String getLiczba() {
        return amount;
    }

    public void setLiczba(String liczba) {
        this.amount = liczba;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDate()
    {
        return data;
    }

    public void setDate(String data)
    {
        this.data = data;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}