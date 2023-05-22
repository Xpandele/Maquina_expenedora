import daos.*;
import model.Producte;
import model.Slot;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;

public class Application {
    static Input in = new Input();
    static DAOFactory df = DAOFactory.getInstance();
    private static ProducteDAO producteDAO = new ProducteDAO_MySQL();
    private static SlotDAO slotDAO = new SlotDAO_MySQL();

    public static void main(String[] args) throws SQLException {

        int opcio;

        do {
            mostrarMenu();
            opcio = Integer.parseInt(in.readLine());

            switch (opcio) {
                case 1 -> mostrarMaquina();
                case 2 -> comprarProducte();
                case 10 -> mostrarInventari();
                case 11 -> afegirProductes();
                case 12 -> modificarMaquina();
                case 13 -> mostrarBenefici();
                case -1 -> System.out.println("Bye...");
                default -> System.out.println("Opció no vàlida!");
            }
        } while (opcio != -1);
    }
    private static void mostrarMenu() {
        System.out.println("\nMenú de la màquina expenedora");
        System.out.println("=============================");
        System.out.println("Selecciona la operació a realitzar introduïnt el número corresponent: \n");

        //Opcions per client / usuari
        System.out.println("[1] Mostrar Posició / Nom producte / Stock de la màquina");
        System.out.println("[2] Comprar un producte");

        //Opcions per administrador / manteniment
        System.out.println();
        System.out.println("[10] Mostrar llistat productes disponibles (BD)");
        System.out.println("[11] Afegir productes disponibles");
        System.out.println("[12] Assignar productes / stock a la màquina");
        System.out.println("[13] Mostrar benefici");

        System.out.println();
        System.out.println("[-1] Sortir de l'aplicació");
    }

    private static void modificarMaquina() throws SQLException {
        in.modificarMaquina();
    }

    private static void modificarProducte(Producte p) throws SQLException {
        System.out.println("El producte que estas intentant entrar ja existeix");
        System.out.println("Selecciona l'opcio que vols realitzar:");
        System.out.println("1 - Canviar codi");
        System.out.println("2 - Sortir");

        int opcio = Integer.parseInt(in.readLine());
        switch (opcio){
            case 1: {
                String nouCodi;
                System.out.println("Introdueix el nou codi del producte:");
                nouCodi = in.readLine();
                p.setCodiProducte(nouCodi);
                producteDAO.createProducte(p);
            } case 2: {
                break;
            }
        }
    }
    private static void mostrarInventari() {
        try {
            //Agafem tots els productes de la BD i els mostrem
            ArrayList<Producte> productes = producteDAO.readProductes();
            for (Producte prod : productes) {
                System.out.println(prod);
            }
        } catch (SQLRecoverableException e) {
            System.out.println("s'ha perdut la connexió.");

        }catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }

    private static void comprarProducte() throws SQLException {
        mostrarMaquina();
        in.comprarProducte();
    }
    private static void mostrarMaquina() throws SQLException {
        ArrayList<Slot> llistaSlots = slotDAO.readSlots();
        ArrayList<Producte> llistaProducte = producteDAO.readProductes();
        mostrarProductes(llistaSlots, llistaProducte);
    }

    private static void afegirProductes() throws SQLException {
        Producte p = Input.addProducte();

        try {
            //Demanem de guardar el producte p a la BD
            producteDAO.createProducte(p);
            //Agafem tots els productes de la BD i els mostrem (per compvoar que s'ha afegit)
            ArrayList<Producte> productes = producteDAO.readProductes();
            for (Producte prod : productes) {
                System.out.println(prod);
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                modificarProducte(p);
            } else {
                e.printStackTrace();
            }
        }

    }
    private static void mostrarBenefici() {
        in.mostrarBenefici();
    }

    private static void mostrarProductes(ArrayList<Slot> llistaSlots, ArrayList<Producte> llistaProductes) throws SQLException {
        for (Slot s : llistaSlots) {
            System.out.println("Posició: " + s.getPosicio());
            System.out.println("Quantitat: " + s.getQuantitat());

            for (Producte p : llistaProductes) {
                if (s.getCodiProducte().equals(p.getCodiProducte())) {
                    System.out.println("Nom: " + p.getNom());
                }
            }
            System.out.println("==============================");
        }
    }
}
