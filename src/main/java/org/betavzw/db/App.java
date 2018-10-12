package org.betavzw.db;



import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class App
{
    private static final String SELECT = "SELECT id, voornaam, achternaam, geboortedatum FROM persoon";
    private static final String SELECT_By_ID = "SELECT id, voornaam, achternaam, geboortedatum FROM persoon WHERE id = ?";
    private static final String UPDATE = "UPDATE persoon SET voornaam = ?, achternaam = ?, geboortedatum = ? WHERE id = ?";

    private static final String CONN_STRING = "jdbc:mysql://localhost/testdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Brussels";

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public static void main ( String[] args )
    {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(CONN_STRING, "root", "VDAB")){

            toonGegevens(conn);
            System.out.print("Van wie wil je de gegevens aanpassen? ");
            int id = Integer.parseInt(scanner.nextLine());
            String nieuweVoornaam, nieuweAchternaam;
            LocalDate nieuweGeboortedatum;

            try (PreparedStatement prep = conn.prepareStatement(SELECT_By_ID)){

                prep.setInt(1, id);

                try(ResultSet rs = prep.executeQuery()){

                    if (rs.next()){
                        nieuweVoornaam = rs.getString("voornaam");
                        nieuweVoornaam = leesNieuweWaarde(scanner, nieuweVoornaam);
                        nieuweAchternaam = rs.getString("achternaam");
                        nieuweAchternaam = leesNieuweWaarde(scanner, nieuweAchternaam);
                        nieuweGeboortedatum = rs.getDate("geboortedatum").toLocalDate();
                        nieuweGeboortedatum = leesNieuweWaarde(scanner, nieuweGeboortedatum);

                    } else {
                        throw new IllegalArgumentException("Id niet gevonden");
                    }
                }
            }

            try (PreparedStatement prep = conn.prepareStatement(UPDATE)){
                prep.setString(1, nieuweVoornaam);
                prep.setString(2, nieuweAchternaam);
                prep.setObject(3, nieuweGeboortedatum);
                prep.setInt(4, id);
                prep.executeUpdate();
            }

            System.out.println("Persoon is aangepast");
            toonGegevens(conn);

        } catch(SQLException ex ){
            System.out.println(ex.getMessage());

        } catch(IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void toonGegevens(Connection conn) throws SQLException {

        try (Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(SELECT)) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String voornaam = rs.getString("voornaam");
                String achternaam = rs.getString("achternaam");
                LocalDate geboortedatum = rs.getDate("geboortedatum").toLocalDate();
                System.out.printf("%d) %s %s geboren op %s%n", id, voornaam, achternaam, geboortedatum.format(dtf));
            }
        }
    }

    private static String leesNieuweWaarde(Scanner scanner, String oudeWaarde) {

        System.out.printf("oude waarde: %s, geef nieuwe waarde (<ENTER> om te behouden): ", oudeWaarde);
        String nieuweWaarde = scanner.nextLine();

        if (!nieuweWaarde.equals("")) oudeWaarde = nieuweWaarde;
            return oudeWaarde;
    }

    private static LocalDate leesNieuweWaarde(Scanner scanner, LocalDate oudeWaarde) {

        System.out.printf("oude waarde: %s, geef nieuwe waarde (<ENTER> om te behouden): ", oudeWaarde.format(dtf));
        String nieuweWaarde = scanner.nextLine();

        if (!nieuweWaarde.equals("")) oudeWaarde = LocalDate.parse(nieuweWaarde, dtf);
            return oudeWaarde;
    }

}

