package org.betavzw.db;



import java.sql.*;
import java.util.Scanner;


public class AutoIncrement
{

    public static void main( String[] args ) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Brussels", "root","VDAB");

            PreparedStatement prep = conn.prepareStatement("INSERT INTO werknemer (naam) VALUES (?)", Statement.RETURN_GENERATED_KEYS)){
            System.out.print("Geef naam <RETURN> om te stoppen: ");
            String naam = scanner.nextLine();


            while (!naam.equals("")){

                prep.setString(1, naam);
                prep.executeUpdate();


                try(ResultSet rs = prep.getGeneratedKeys()){

                    if(rs.next()){
                        int id = rs.getInt(1);
                        System.out.printf("%s toegevoegd met id %d%n", naam, id);
                    }
                }

                System.out.print("Geef naam <RETURN> om te stoppen: ");
                naam = scanner.nextLine();
            }
        }
    }
}