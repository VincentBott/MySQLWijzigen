package org.betavzw.db;


import java.sql.*;


public class Oef6Ma1510 {


    private static final String UPDATE = "UPDATE SET bieren.alcohol, bieren.naam,  brouwers.brnaam from bieren inner join brouwers " +
            "WHERE bieren.BrouwerNr = brouwers.BrouwerNr AND bieren.alcohol > ? AND bieren.alcohol   < ? ORDER BY bieren.alcohol";


    public static void main(String[] args) throws SQLException {



        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bieren?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Brussels", "cursist", "VDAB");
             PreparedStatement prep = conn.prepareStatement(UPDATE))

        {


            prep.executeUpdate(UPDATE);


        }
    }
}
