package pl.pollub.projektandroid;

import java.io.Serializable;

public class ToDo_Element implements Serializable
{
    private int id;
    private String title;
    private String deadline_time;
    private String deadline_day;
    private String login;
    private int user_id;
    private boolean czyWcisnietoZakoncz;
    private boolean czyWcisnietoTeraz;
    private boolean czyWcisnietoEdytuj;
    private int now;

    public ToDo_Element(String title, String deadline_time, String deadline_day, String login, int now, boolean czyWcisnietoZakoncz, boolean czyWcisnietoTeraz, boolean czyWcisnietoEdytuj, int user_id)
    {
        this.title = title;
        this.deadline_time = deadline_time;
        this.deadline_day= deadline_day;
        this.now = now;
        this.login = login;
        this.czyWcisnietoZakoncz = czyWcisnietoZakoncz;
        this.czyWcisnietoEdytuj = czyWcisnietoEdytuj;
        this.czyWcisnietoTeraz = czyWcisnietoTeraz;
        this.user_id = user_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean czyWcisnietoZakoncz()
    {
        return czyWcisnietoZakoncz;
    }
    public boolean czyWcisnietoEdytuj()
    {
        return czyWcisnietoEdytuj;
    }
    public boolean czyWcisnietoTeraz()
    {
        return czyWcisnietoTeraz;
    }

    public void ustawCheckboxZakoncz(boolean czyWcisnieto)
    {
        this.czyWcisnietoZakoncz = czyWcisnieto;
    }

    public void ustawCheckboxEdytuj(boolean czyWcisnieto)
    {
        this.czyWcisnietoEdytuj = czyWcisnieto;
    }

    public void ustawCheckboxTeraz(boolean czyWcisnieto)
    {
        this.czyWcisnietoTeraz = czyWcisnieto;
    }

    public String getWydarzenie() {
        return title;
    }

    public void setWydarzenie(String nazwa) {
        this.title = nazwa;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String nazwa) {
        this.login = nazwa;
    }

    public String getGodzina_deadline() {
        return deadline_time;
    }

    public void setGodzina_deadline(String godzina_deadline) {
        this.deadline_time = godzina_deadline;
    }


    public String getData_deadline() {
        return deadline_day;
    }

    public void setData_rozpoczecia(String data_deadline) {
        this.deadline_day = data_deadline;
    }



    public int getUser_id() {
        return user_id;
    }

    public void setUzytkownik_id(int uzytkownik_id) {
        this.user_id = uzytkownik_id;
    }

    public boolean isCzyWcisnietoTeraz() {
        return czyWcisnietoTeraz;
    }

    public void setCzyWcisnietoTeraz(boolean czyWcisnietoTeraz) {
        this.czyWcisnietoTeraz = czyWcisnietoTeraz;
    }

    public boolean isCzyWcisnietoEdytuj() {
        return czyWcisnietoEdytuj;
    }

    public void setCzyWcisnietoEdytuj(boolean czyWcisnietoEdytuj) {
        this.czyWcisnietoEdytuj = czyWcisnietoEdytuj;
    }

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }
}
