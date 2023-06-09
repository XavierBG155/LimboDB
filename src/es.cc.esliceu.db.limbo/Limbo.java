package es.cc.esliceu.db.limbo;

import es.cc.esliceu.db.limbo.dao.LimboDAO;
import es.cc.esliceu.db.limbo.dao.LimboDAOImpl;
import es.cc.esliceu.db.limbo.domain.Usuari;
import es.cc.esliceu.db.limbo.util.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Limbo {

    public static void main(String[] args) throws IOException {
        LimboDAO limboDAO = new LimboDAOImpl();
        FileInputStream input = new FileInputStream("C:\\Users\\berga\\Desktop\\classe\\LimboDB\\resources\\limbo.properties");
        Properties props = new Properties();
        props.load(input);
        /*String URL = props.getProperty("url");
        String USERNAME = props.getProperty("user");
        String PASSWORD = props.getProperty("password");*/
        Scanner scanner = new Scanner(System.in);
        System.out.println(Color.YELLOW_BRIGHT + """
                **************************************************
                **                  Limbo app                   **
                **************************************************
                """);
        System.out.println(Color.CYAN + "1)" + Color.WHITE_BRIGHT + " Login");
        System.out.println(Color.CYAN + "2)" + Color.WHITE_BRIGHT + " Registrar-se");
        System.out.println(Color.CYAN + "3)" + Color.WHITE_BRIGHT + " Ajuda");
        System.out.println(Color.CYAN + "4)" + Color.WHITE_BRIGHT + " Sortir");
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Esculli una opció:" + Color.RESET);
        int opcio = scanner.nextInt();
        scanner.nextLine();
        try {
            switch (opcio) {
                case 1 -> {
                    System.out.println(Color.YELLOW_BRIGHT + """
                            **************************************************
                            **                     Login                    **
                            **************************************************
                            """);
                    System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Username:" + Color.RESET);
                    String nomUsuari = scanner.nextLine();
                    System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Password:" + Color.RESET);
                    limboDAO.loginUsuari(nomUsuari);
                    String passUsuari = scanner.nextLine();
                    limboDAO.loginUsuari(passUsuari);
                }
                case 2 -> {
                    System.out.println(Color.YELLOW_BRIGHT + """
                            **************************************************
                            **                  Registre                    **
                            **************************************************
                            """);
                    System.out.println(Color.WHITE_BRIGHT);
                    System.out.println("Username:");
                    String nomUsuari = scanner.nextLine();
                    System.out.println("Email:");
                    String emailUsuari = scanner.nextLine();
                    System.out.println("Password:");
                    String passUsuari = scanner.nextLine();
                    System.out.println("Nom:");
                    String nomRealUsuari = scanner.nextLine();
                    System.out.println("Primer llinatge:");
                    String llin1Usuari = scanner.nextLine();
                    System.out.println("Segon llinatge:");
                    String llin2Usuari = scanner.nextLine();
                    Usuari usuari = new Usuari(nomUsuari, emailUsuari, passUsuari, nomRealUsuari, llin1Usuari, llin2Usuari);
                    limboDAO.registreUsuari(usuari);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void info(String texte) {
        System.out.println(Color.BLUE_BOLD + "\t" + texte + Color.RESET);
    }

    public static void errada(String texte) {
        System.out.println(Color.RED_BOLD + "\t" + texte + Color.RESET);
    }
}
