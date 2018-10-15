package org.betavzw.db;


import java.sql.*;


public class Oef2Ma1510 {


    private static final String SELECT = "select count(bieren.brouwernr) as aantalVanBrouwer,  brouwers.BrNaam from bieren inner join brouwers where bieren.BrouwerNr = \n" +
            "brouwers.brouwernr group by bieren.brouwernr \n" +
            "order by brouwers.brnaam;";


    public static void main(String[] args) throws SQLException {


        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bieren?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Brussels", "cursist", "VDAB");
             Statement statement = conn.createStatement())

        {

            ResultSet rs = statement.executeQuery(SELECT);


            System.out.println("Brouwernaam\tAantal bieren van brouwer\n");


            while (rs.next()) {


                String Brouwernaam = rs.getString("brnaam");

                String aantalVanBrouwer = rs.getString("aantalVanBrouwer");


                System.out.printf("%s: %s bieren\n", Brouwernaam, aantalVanBrouwer);

            }

        }
    }
}
