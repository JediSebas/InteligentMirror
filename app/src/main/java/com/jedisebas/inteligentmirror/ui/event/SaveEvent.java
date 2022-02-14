package com.jedisebas.inteligentmirror.ui.event;

import com.jedisebas.inteligentmirror.Loggeduser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SaveEvent implements Runnable {

    Thread t;
    String name, date;
    int days;

    SaveEvent(String eventname, int days, String date) {
        t = new Thread(this);
        name = eventname;
        this.days = days;
        this.date = date;
    }


    @Override
    public void run() {
        String DB_URL = "jdbc:mysql://"+ Loggeduser.ip+"/mirror";
        String USER = "user";
        String PASS = "user"; // test password
        String QUERY = "INSERT INTO `event` (`id`, `email`, `name`, `howlong`, `date`) " +
                "VALUES (NULL, '"+ Loggeduser.email +"', '"+ name +"', '"+ days +"', '"+ date +"');";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("DRIVER STILL WORKS BTW");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(QUERY);
            EventFragment.saveComplete = true;
        } catch (SQLException throwables) {
            System.out.println("HERE IS SOMETHING WRONG");
            throwables.printStackTrace();
        }
    }
}
