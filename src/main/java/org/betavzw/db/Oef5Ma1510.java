package org.betavzw.db;


import java.sql.*;
import java.util.Scanner;


public class Oef5Ma1510 {


    private static final String SELECT_By_Alc = "select bieren.alcohol, bieren.naam,  brouwers.brnaam from bieren inner join brouwers " +
        "where bieren.BrouwerNr = brouwers.BrouwerNr AND bieren.alcohol > ? AND bieren.alcohol   < ? order by bieren.alcohol";



    public static void main(String[] args) throws SQLException {


        Scanner scanner = new Scanner(System.in);

        System.out.print("Geef een minimumpercentage: ");
        double minimum = Double.parseDouble(scanner.nextLine());


        System.out.print("Geef een maximumpercentage: ");
        double maximum = Double.parseDouble(scanner.nextLine());


        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bieren?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Brussels", "cursist", "VDAB");
             PreparedStatement prep = conn.prepareStatement(SELECT_By_Alc))

        {


            prep.setDouble(1, minimum);
            prep.setDouble(2, maximum);

            ResultSet rs = prep.executeQuery();


            while (rs.next()) {


                String biernaam = rs.getString("bieren.naam");

                String alcoholPercentage = rs.getString("bieren.alcohol");

                String brouwernaam = rs.getString("brouwers.brnaam");

                System.out.printf("%s %%: %s \n", alcoholPercentage, biernaam);

            }

        }
    }
}
