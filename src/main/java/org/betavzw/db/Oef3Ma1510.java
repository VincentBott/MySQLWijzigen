package org.betavzw.db;


import java.sql.*;
import java.util.Scanner;


public class Oef3Ma1510 {


    private static final String SELECT_By_Alc = "select naam, alcohol from bieren where alcohol > ? AND alcohol < ? order by alcohol";  // Deze SQL-statement is nog niet volledig !


    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Geef een minimum alcoholwaarde: ");
        int minimum = Integer.parseInt(scanner.nextLine());

        System.out.print("Geef een maximum alcoholwaarde: ");
        int maximum = Integer.parseInt(scanner.nextLine());


        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bieren?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Brussels", "cursist", "VDAB");

             PreparedStatement prep = conn.prepareStatement(SELECT_By_Alc))

        {


            prep.setDouble(1, minimum);
            prep.setDouble(2, maximum);

            ResultSet rs = prep.executeQuery();


            while (rs.next()) {


                String biernaam = rs.getString("naam");

                double alcoholPercentage = rs.getDouble("alcohol");


                System.out.printf("%s: %f %%\n", biernaam, alcoholPercentage);

            }

        }
    }
}
