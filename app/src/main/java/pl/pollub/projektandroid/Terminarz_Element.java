package pl.pollub.projektandroid;

import java.io.Serializable;


public class Terminarz_Element implements Serializable
{

    private int id;

    private String name;
    private String start_time;
    private String end_time;
    private String login;
    private String time;
    private String date;
    private int status;

    public Terminarz_Element(String name, String start, String koniec, String czas, int status, String login, String data)
    {
        this.name = name;
        this.start_time = start;
        this.end_time = koniec;
        this.login = login;
        this.time = czas;
        this.date = data;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}