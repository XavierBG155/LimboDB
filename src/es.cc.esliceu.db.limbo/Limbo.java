package es.cc.esliceu.db.limbo;

import es.cc.esliceu.db.limbo.dao.AdrecaDAO;
import es.cc.esliceu.db.limbo.dao.AdrecaDAOImpl;
import es.cc.esliceu.db.limbo.dao.CiutatDAO;
import es.cc.esliceu.db.limbo.dao.CiutatDAOImpl;
import es.cc.esliceu.db.limbo.dao.CompraDAO;
import es.cc.esliceu.db.limbo.dao.CompraDAOImpl;
import es.cc.esliceu.db.limbo.dao.ProducteDAO;
import es.cc.esliceu.db.limbo.dao.ProducteDAOImpl;
import es.cc.esliceu.db.limbo.dao.TargetaDAO;
import es.cc.esliceu.db.limbo.dao.TargetaDAOImpl;
import es.cc.esliceu.db.limbo.dao.UsuariDAO;
import es.cc.esliceu.db.limbo.dao.UsuariDAOImpl;
import es.cc.esliceu.db.limbo.domain.Adreca;
import es.cc.esliceu.db.limbo.domain.Categoria;
import es.cc.esliceu.db.limbo.domain.Ciutat;
import es.cc.esliceu.db.limbo.domain.Compra;
import es.cc.esliceu.db.limbo.domain.Producte;
import es.cc.esliceu.db.limbo.domain.Targeta;
import es.cc.esliceu.db.limbo.domain.Usuari;
import es.cc.esliceu.db.limbo.util.Color;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Limbo {

static TargetaDAO targetaDAO = new TargetaDAOImpl();
static UsuariDAO usuariDAO = new UsuariDAOImpl();
static CiutatDAO ciutatDAO=new CiutatDAOImpl();
static CompraDAO compraDAO = new CompraDAOImpl();
static ProducteDAO producteDAO = new ProducteDAOImpl();
static AdrecaDAO adrecaDAO = new AdrecaDAOImpl();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        menuPrincipal();
    }

    private static void menuPrincipal() {
        System.out.println(Color.YELLOW_BRIGHT + """
                **************************************************
                **                  Limbo app                   **
                **************************************************
                """);
        System.out.println(Color.CYAN + "1)" + Color.WHITE_BRIGHT + " Login");
        System.out.println(Color.CYAN + "2)" + Color.WHITE_BRIGHT + " Registrar-se");
        System.out.println(Color.CYAN + "h)" + Color.WHITE_BRIGHT + " Ajuda");
        System.out.println(Color.CYAN + "x)" + Color.WHITE_BRIGHT + " Sortir");
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Esculli una opció:" + Color.RESET);
        String opcio = scanner.nextLine();
        try {
            switch (opcio) {
                case "1" -> login();
                case "2" -> registre();
                case "h" -> {
                    System.out.println("""
                            1) : et permet entrar a l'aplicació, sempre i quan tinguis compte.
                            2) : et permet crear un compte nou.
                            """);
                    menuPrincipal();
                }
                case "x" -> System.exit(0);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void registre() throws SQLException {
        System.out.println(Color.YELLOW_BRIGHT + """
                **************************************************
                **                  Registre                    **
                **************************************************
                """);
        System.out.print(Color.WHITE_BRIGHT);
        System.out.print("Username: ");
        String nomUsuari = scanner.nextLine();
        System.out.print("Email: ");
        String emailUsuari = scanner.nextLine();
        System.out.print("Password: ");
        String passUsuari = scanner.nextLine();
        System.out.print("Nom: ");
        String nomRealUsuari = scanner.nextLine();
        System.out.print("Primer llinatge :");
        String llin1Usuari = scanner.nextLine();
        System.out.print("Segon llinatge :");
        String llin2Usuari = scanner.nextLine();
        Usuari usuari = new Usuari(0, emailUsuari, nomRealUsuari, llin1Usuari, llin2Usuari, nomUsuari, passUsuari);
        usuariDAO.registreUsuari(usuari);
    }

    private static void login() throws SQLException {
        System.out.println(Color.YELLOW_BRIGHT + """
                **************************************************
                **                     Login                    **
                **************************************************
                """);
        boolean login = false;
        int intents = 3;
        String nomUsuari = null;
        while (!login) {
            System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Username:" + Color.RESET);
            nomUsuari = scanner.nextLine();
            login = usuariDAO.loginUsuari("username", nomUsuari);
        }
        login = false;
        while (!login) {
            System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Password:" + Color.RESET);
            String passUsuari = scanner.nextLine();
            login = usuariDAO.loginUsuari("contrasenya", passUsuari);
            if (!login) {
                intents--;
                System.out.printf("Queden %d/3 intents\n", intents);
                if (intents == 0) {
                    errada("No s'ha pogut validar l'usuari");
                    break;
                }
            }
        }
        if (login) principal(nomUsuari);
    }

    private static void principal(String nomUsuari) throws SQLException {
        try {
            Usuari usuari = usuariDAO.selectUser(nomUsuari);
            System.out.printf(Color.YELLOW_BRIGHT + """
                            **************************************************
                            **                  Opcions                     **
                            Usuari:""" + Color.WHITE_BRIGHT + """
                            %s %s""" + Color.RED_BRIGHT + "  %s \n" + Color.YELLOW_BRIGHT +
                            "**************************************************\n"
                    , usuari.getNOM(), usuari.getLlinatge1(), usuari.getUSERNAME());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Color.CYAN + "c)" + Color.WHITE_BRIGHT + " Cerca productes");
        System.out.println(Color.CYAN + "v)" + Color.WHITE_BRIGHT + " Veure cistella");
        System.out.println(Color.CYAN + "d)" + Color.WHITE_BRIGHT + " Dades personals");
        System.out.println(Color.CYAN + "h)" + Color.WHITE_BRIGHT + " Ajuda");
        System.out.println(Color.CYAN + "x)" + Color.WHITE_BRIGHT + " Sortir");
        System.out.println(Color.RED_BRIGHT + "---------------    Productes suggerits     ---------------");

        try {
            for (int i = 0; i < 5; i++) {
                Producte producte = producteDAO.producteAleatori();
                System.out.printf(Color.YELLOW_BRIGHT + "%d" + Color.WHITE_BRIGHT + "   %s   %s" + Color.GREEN_BRIGHT + "   %s" + Color.CYAN + "   %.2f€\n", i, producte.getNom(), producte.getDescripcio(), producte.getMarca(), producte.getPvp());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Color.RED_BRIGHT + "----------------------------------------------------------");
        System.out.println(Color.CYAN + "(0-4)" + Color.WHITE_BRIGHT + "Productes suggerits");
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Esculli una opció:" + Color.RESET);

        String opcio = scanner.next();
        scanner.nextLine();
        switch (opcio) {
            case "c" -> cercaProducte();
            case "x" -> menuPrincipal();
            case "d" -> dadesPersonals(nomUsuari);
        }
    }

    private static void dadesPersonals(String nomUsuari) throws SQLException {
        try {
            Usuari usuari = usuariDAO.selectUser(nomUsuari);
            System.out.printf(Color.YELLOW_BRIGHT + """
                            **************************************************
                            **                  Dades personals             **
                            ** Usuari:\s""" + Color.WHITE_BRIGHT + """
                            %s %s""" + Color.RED_BRIGHT + "  %s \n" + Color.YELLOW_BRIGHT +
                            "**************************************************\n"
                    , usuari.getNOM(), usuari.getLlinatge1(), usuari.getUSERNAME());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Color.CYAN + "s)" + Color.WHITE_BRIGHT + " Settings");
        System.out.println(Color.CYAN + "c)" + Color.WHITE_BRIGHT + " Compres realitzades");
        System.out.println(Color.CYAN + "a)" + Color.WHITE_BRIGHT + " Adreces");
        System.out.println(Color.CYAN + "t)" + Color.WHITE_BRIGHT + " Targetes");
        System.out.println(Color.CYAN + "x)" + Color.WHITE_BRIGHT + " Sortir");
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Esculli una opció:" + Color.RESET);
        String opcio = scanner.nextLine();
        switch (opcio) {
            case "s" -> settings(nomUsuari);
            case "c" -> compresRealitzades(nomUsuari);
            case "a" -> llistaAdreces(nomUsuari);
            case "t" -> opcionsTargeta(nomUsuari);
        }
    }

    private static void opcionsTargeta(String nomUsuari) throws SQLException {
        Usuari usuari = usuariDAO.selectUser(nomUsuari);
        System.out.printf(Color.YELLOW_BRIGHT + """
                        **************************************************
                        **                     Targetes                 **
                        ** Usuari:\s""" + Color.WHITE_BRIGHT + """
                        %s %s""" + Color.RED_BRIGHT + "  %s \n" + Color.YELLOW_BRIGHT +
                        "**************************************************\n"
                , usuari.getNOM(), usuari.getLlinatge1(), usuari.getUSERNAME());
        try {
            List<Targeta> llistaTargeta = targetaDAO.selectTargetes(nomUsuari);
            for (Targeta targeta : llistaTargeta) {
                System.out.printf("%d" + Color.CYAN_BRIGHT + "  %s  %d\n", targeta.getID(), targeta.getTipus(), targeta.getNumero());
            }
            System.out.println(Color.WHITE_BRIGHT + "a) Afegir targeta");
            System.out.println(Color.WHITE_BRIGHT + "e) Eliminar targeta");
            System.out.println(Color.WHITE_BRIGHT + "x) Sortir");
            System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Esculli una opció:" + Color.RESET);
            String opcio = scanner.nextLine();
            switch (opcio) {
                case "a" -> afegirTargeta(nomUsuari);
                case "e" -> eliminarTargetaMenu();
                case "x" -> dadesPersonals(nomUsuari);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void afegirTargeta(String nomUsuari) throws SQLException {
        System.out.print(Color.YELLOW_BRIGHT + "Tipus: \n" + Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "v) Visa | mc) Mastercard | m) Maestro:" + Color.RESET + "\n");
        String tipus = scanner.nextLine();
        switch (tipus) {
            case "v" -> tipus = "Visa";
            case "mc" -> tipus = "Mastercard";
            case "m" -> tipus = "Maestro";
        }
        System.out.println("Número: ");
        long numero = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Data de caducitat (aaaa/mm/dd): ");
        String caducitat = scanner.nextLine();
        int codi_seguretat = codiSeguretat();
        targetaDAO.afegirTargeta(tipus, numero, caducitat, codi_seguretat, usuariDAO.selectUser(nomUsuari).getID());
    }

    private static int codiSeguretat() {
        System.out.println("Codi de seguretat: ");
        int codi_seguretat = Integer.parseInt(scanner.nextLine());
        while (String.valueOf(codi_seguretat).length() != 3) {
            codiSeguretat();
        }
        return codi_seguretat;
    }

    private static void eliminarTargetaMenu() throws SQLException {
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Targeta per eliminar (id):" + Color.RESET);
        int targetaId = scanner.nextInt();
        scanner.nextLine();
        System.out.printf("Segur que vols eliminar la targeta amb id %d? (si/no): ", targetaId);
        String opcio = scanner.nextLine();
        if (Objects.equals(opcio, "si") || Objects.equals(opcio, "Si")){
            targetaDAO.eliminarTargeta(targetaId);
        } else eliminarTargetaMenu();
    }

    private static void llistaAdreces(String nomUsuari) throws SQLException {
        Usuari usuari = usuariDAO.selectUser(nomUsuari);
        System.out.printf(Color.YELLOW_BRIGHT + """
                        **************************************************
                        **                     Adreces                  **
                        ** Usuari:\s""" + Color.WHITE_BRIGHT + """
                        %s %s""" + Color.RED_BRIGHT + "  %s \n" + Color.YELLOW_BRIGHT +
                        "**************************************************\n"
                , usuari.getNOM(), usuari.getLlinatge1(), usuari.getUSERNAME());
        try {
            List<Adreca> llistaAdreces = adrecaDAO.selectAdreces(nomUsuari);
            for (Adreca adreca : llistaAdreces) {
                System.out.printf("%d" + Color.CYAN_BRIGHT + "  %s  %s  %s", adreca.getId(), adreca.getCarrer(), adreca.getNumero(), adreca.getCP());
            }
            System.out.println(Color.WHITE_BRIGHT + "a) Afegir adreça d'enviament");
            System.out.println(Color.WHITE_BRIGHT + "x) Sortir");
            System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Esculli una opció:" + Color.RESET);
            String opcio = scanner.nextLine();
            switch (opcio) {
                case "a" -> afegirAdreca(nomUsuari);
                case "x" -> compresRealitzades(nomUsuari);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void afegirAdreca(String nomUsuari) throws SQLException {
        Usuari usuari = usuariDAO.selectUser(nomUsuari);
        Ciutat ciutat = ciutatDAO.selectCiutat();
        System.out.println(Color.YELLOW_BRIGHT + """
                **************************************************
                **                Afegir adreça                 **
                **************************************************
                """);
        System.out.print(Color.WHITE_BRIGHT);
        System.out.print("Carrer: ");
        String carrer = scanner.nextLine();
        System.out.print("Numero: ");
        String numero = scanner.nextLine();
        System.out.print("Pis: ");
        int pis = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Porta: ");
        String porta = scanner.nextLine();
        System.out.print("Codi postal :");
        String CP = scanner.nextLine();
        Adreca adreca = new Adreca(0, usuari.getID(), carrer, numero, pis, porta, CP, ciutat.getID());
        adrecaDAO.registreAdreca(adreca, nomUsuari);
    }

    private static void compresRealitzades(String nomUsuari) {
        try {
            Usuari usuari = usuariDAO.selectUser(nomUsuari);
            System.out.printf(Color.YELLOW_BRIGHT + """
                            **************************************************
                            **                     Compres                  **
                            ** Usuari:\s""" + Color.WHITE_BRIGHT + """
                            %s %s""" + Color.RED_BRIGHT + "  %s \n" + Color.YELLOW_BRIGHT +
                            "**************************************************\n"
                    , usuari.getNOM(), usuari.getLlinatge1(), usuari.getUSERNAME());
            try {
                List<Compra> compraList = compraDAO.selectCompra(nomUsuari);
                for (Compra compra : compraList) {
                    System.out.printf("""
                            %d   %s %s %.1f
                            """, compra.getId(), compra.getData(), compra.getIdTransaccio(), compraDAO.getDetallCompra(compra.getId()));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private static void settings(String nomUsuari) throws SQLException {
        try {
            Usuari usuari = usuariDAO.selectUser(nomUsuari);
            System.out.printf(Color.YELLOW_BRIGHT + """
                            **************************************************
                            **                    Settings                  **
                            ** Usuari:\s""" + Color.WHITE_BRIGHT + """
                            %s %s""" + Color.RED_BRIGHT + "  %s \n" + Color.YELLOW_BRIGHT +
                            "**************************************************\n"
                    , usuari.getNOM(), usuari.getLlinatge1(), usuari.getUSERNAME());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Color.WHITE_BRIGHT);
        System.out.println("a) Modifica dades personals");
        System.out.println("b) Canvi password");
        System.out.println("x) Sortir");
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Esculli una opció:" + Color.RESET);
        String opcio = scanner.nextLine();
        switch (opcio) {
            case "a" -> modificaDades(nomUsuari);
            case "b" -> canviaContrasenya();
            case "x" -> settings(nomUsuari);
        }
    }

    private static void canviaContrasenya() {
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Password antic:" + Color.RESET);
        String passAntinc = scanner.nextLine();
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Password nou:" + Color.RESET);
        String passNou = scanner.nextLine();
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Repeteix el nou password:" + Color.RESET);
        String passNouRep = scanner.nextLine();
        if (Objects.equals(passNou, passNouRep)) {
            usuariDAO.canviaContrasenya(passAntinc, passNou);
        } else {
            System.out.println(Color.RED_BRIGHT + "La nova contrasenya no coincideix, torna a provar");
            canviaContrasenya();
        }
        menuPrincipal();
    }

    private static void modificaDades(String nomUsuari) throws SQLException {
        Usuari user = usuariDAO.selectUser(nomUsuari);
        System.out.printf(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Nom (%s):" + Color.RESET + "\n", user.getNOM());
        String nom = scanner.nextLine();
        System.out.printf(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Llinatge1 (%s):" + Color.RESET + "\n", user.getLlinatge1());
        String llin1 = scanner.nextLine();
        System.out.printf(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Llinatge2 (%s):" + Color.RESET + "\n", user.getLlinatge2());
        String llin2 = scanner.nextLine();

        usuariDAO.canviaDades(nom, llin1, llin2, user.getNOM(), user.getLlinatge1(), user.getLlinatge2());
        menuPrincipal();
    }

    private static void cercaProducte() throws SQLException {
        System.out.println(Color.YELLOW_BRIGHT + """
                ******************************************
                **                 Cerca                **
                ******************************************
                """);
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Nom producte:" + Color.RESET);
        String nomProducte = scanner.nextLine();
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Descripció:" + Color.RESET);
        String descripcio = scanner.nextLine();
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Marca:" + Color.RESET);
        String marca = scanner.nextLine();
        System.out.println(Color.BLACK_BOLD + "" + Color.YELLOW_BACKGROUND + "Categoria:" + Color.RESET);
        int i;
        List<Categoria> categoriaList = producteDAO.getProductes();
        for (Categoria categoria : categoriaList) {
            System.out.printf("(" + Color.YELLOW_BRIGHT + "%s" + Color.WHITE_BRIGHT + ") %s %s\n", categoria.getId(), categoria.getNom(), categoria.getDescripcio());
        }
        String categoria = scanner.nextLine();
        i = 0;
        List<Producte> llistaProductes = producteDAO.filtraProductes(nomProducte, descripcio, marca, categoria);
        if (Objects.equals(nomProducte, "") && Objects.equals(descripcio, "") && Objects.equals(marca, "") && Objects.equals(categoria, "")) {
            System.out.println(Color.CYAN_BRIGHT + "No ha sigut aplicat cap filtre, per tant, es mostraran tots els resultats.");
            for (Producte producte : llistaProductes) {
                System.out.printf(Color.YELLOW_BRIGHT + "%d   " + Color.WHITE_BRIGHT + "%s   %s   %s" + Color.GREEN_BRIGHT + "   %s" + Color.CYAN + "   %.2f€\n", i, producte.getNom(), producte.getDescripcio(), producte.getCategoria(), producte.getMarca(), producte.getPvp());
                i++;
            }
        } else {
            System.out.printf(Color.CYAN_BRIGHT + "Mostrant %d productes\n", llistaProductes.size());
            for (Producte producte : llistaProductes) {
                System.out.printf(Color.YELLOW_BRIGHT + "%d   " + Color.WHITE_BRIGHT + "%s   %s   %s" + Color.GREEN_BRIGHT + "   %s" + Color.CYAN + "   %.2f€\n", i, producte.getNom(), producte.getDescripcio(), producte.getCategoria(), producte.getMarca(), producte.getPvp());
                i++;
            }
        }

    }

/*    public static void info(String texte) {
        System.out.println(Color.BLUE_BOLD + "\t" + texte + Color.RESET);
    }*/

    public static void errada(String texte) {
        System.out.println(Color.RED_BOLD + "\t" + texte + Color.RESET);
    }
}
