package org.betavzw.db;


import java.sql.*;


public class Oef1Ma1510 {


    private static final String DELETE = "DELETE FROM bieren WHERE Alcohol IS null";


    public static void main(String[] args) throws SQLException {


        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bieren?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Brussels", "cursist", "VDAB");
             Statement stat = conn.createStatement() )

        {

            System.out.println("Verwijderd: " + stat.executeUpdate(DELETE));
        }

    }
}

