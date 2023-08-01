<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($tabela, $email, $haslo)
    {
        $email = $this->prepareData($email);
        $haslo = $this->prepareData($haslo);
        $this->sql = "select * from " . $tabela . " where email = '" . $email . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbemail = $row['email'];
            $dbpassword = $row['password'];
            if ($dbemail == $email && password_verify($haslo, $dbpassword)) {
                return true;
            } else return false;
        } else return false;
    }

    function signUp($table, $email, $login, $haslo , $imie, $nazwisko, $data)
    {
        $haslo = $this->prepareData($haslo);
        $haslo = password_hash($haslo, PASSWORD_DEFAULT);
        $email = $this->prepareData($email);
        $imie = $this->prepareData($imie);
        $nazwisko = $this->prepareData($nazwisko);
        $data = $this->prepareData($data);
        $login = $this->prepareData($login);
        $this->sql =
            "INSERT INTO " . $table . " (email, password, login, name, surname, date_of_birth) VALUES ('" . $email . "','" . $haslo . "', '" . $login . "', '" . $imie . "','" . $nazwisko . "','" . $data . "')";
        if (mysqli_query($this->connect, $this->sql)) {
                return true;
            }
    }

    function dodajDoListy($table, $produkt, $liczba, $status, $user_id)
    {
        $produkt = $this->prepareData($produkt);
        $liczba = $this->prepareData($liczba);
        $status = $this->prepareData($status);
        $user_id = $this->prepareData($user_id);
        $this->sql =
                "INSERT INTO " . $table . " (name, amount, status, user_id) VALUES ('" . $produkt . "','" . $liczba . "','" . $status . "','" . $user_id . "')";

        if (mysqli_query($this->connect, $this->sql))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    function dodajGrupe($table, $nazwa_grupy)
    {
        $nazwa_grupy = $this->prepareData($nazwa_grupy);
        $this->sql =
            "INSERT INTO " . $table . " (name_of_group) VALUES ('" . $nazwa_grupy . "')";

        if (mysqli_query($this->connect, $this->sql))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    function dodajDoTerminarza($table, $nazwa, $data_deadline, $godzina_deadline,$uzytkownik_id)
    {
        $nazwa = $this->prepareData($nazwa);
        $data_deadline = $this->prepareData($data_deadline);
        $godzina_deadline = $this->prepareData($godzina_deadline);
        $uzytkownik_id = $this->prepareData($uzytkownik_id);
        $this->sql =
                "INSERT INTO " . $table . " (title, deadline_day, deadline_time, user_id) VALUES ('" . $nazwa . "','" . $data_deadline . "','" .  $godzina_deadline . "','" . $uzytkownik_id . "')";

        if (mysqli_query($this->connect, $this->sql))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    function dodajDoTerminarzyka($table, $nazwa, $liczba, $godzina_rozpoczecia, $data, $user_id, $godzina_zakonczenia)
    {
        $nazwa = $this->prepareData($nazwa);
        $liczba = $this->prepareData($liczba);
        $godzina_rozpoczecia = $this->prepareData($godzina_rozpoczecia);
        $data = $this->prepareData($data);
        $user_id = $this->prepareData($user_id);
        $godzina_zakonczenia = $this->prepareData($godzina_zakonczenia);
        $this->sql =
            "INSERT INTO " . $table . " (name, start_time, end_time, time, date, user_id) VALUES ('" . $nazwa . "','" . $godzina_rozpoczecia . "','" .  $godzina_zakonczenia . "','" . $liczba . "','" . $data. "','" . $user_id. "')";

        if (mysqli_query($this->connect, $this->sql))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    function checkEmail($email) {
        $email = mysqli_real_escape_string($this->connect, $email);

        $sql = mysqli_query($this->connect,"SELECT * FROM users WHERE email='$email'");

        if (mysqli_num_rows($sql) != 0) {
            return true;
        }

        return false;
    }

    function checkLogin($login) {
        $login = mysqli_real_escape_string($this->connect, $login);

        $sql = mysqli_query($this->connect,"SELECT * FROM users WHERE login='$login'");

        if (mysqli_num_rows($sql) != 0) {
            return true;
        }

        return false;
    }

}

?>