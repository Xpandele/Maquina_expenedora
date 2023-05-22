import daos.SlotDAO_MySQL;
import model.Producte;
import model.Slot;

import java.sql.SQLException;
import java.util.Scanner;

public class Input {
    private static Scanner sc = null;
    private static float benefici;
    public static  SlotDAO_MySQL slot = new SlotDAO_MySQL();
    public Input(){
        sc = new Scanner(System.in);
    }

    public static Producte addProducte() {
        String codiProducte;
        String nomProducte;
        String descripcioProducte;
        float preuCompra;
        float preuVenda;
        System.out.println("Intordueix el codi del producte: ");
        codiProducte = sc.nextLine();
        System.out.println("Introdueix el nom del producte: ");
        nomProducte = sc.nextLine();
        System.out.println("Introdueix la descripció del producte: ");
        descripcioProducte = sc.nextLine();
        System.out.println("Introdueix el preu de compra: ");
        preuCompra = Float.parseFloat(sc.nextLine());
        System.out.println("Introdueix el preu de venda: ");
        preuVenda = Float.parseFloat(sc.nextLine());

        return new Producte(codiProducte, nomProducte,descripcioProducte,preuCompra,preuVenda);
    }

    public void comprarProducte() throws SQLException
    {
        System.out.println("Introdueix el nom del producte que vols comprar: ");
        String nom = sc.nextLine();
        benefici = benefici + slot.modificarQuantitat(nom);
    }

    public void modificarMaquina() throws SQLException
    {
        System.out.println("Selecciona quina opció vols realitzar: ");
        System.out.println("1- Modificar posició");
        System.out.println("2- Modificar stock");
        System.out.println("3- Afegir slots");
        System.out.println("0- Sortir");
        int opcio = Integer.parseInt(sc.nextLine());

        switch (opcio) {
            case 1 -> {
                modificarPosicio();
                System.out.println("La posició s'ha modificat correctament");
            }
            case 2 -> {
                modificarStock();
                System.out.println("El stock s'ha modificat correctament");
            }
            case 3 -> {
                crearSlot();
                System.out.println("S'ha creat el slot satisfactoriament");
            }
            case 0 -> {
            }
        }
    }


    public void modificarPosicio() throws SQLException {
        System.out.println("Selecciona la posició que vols modificar:");
        int posicioActual = Integer.parseInt(sc.nextLine());

        System.out.println("Introdueix la nova posició:");
        int posicioNova = Integer.parseInt(sc.nextLine());

        slot.modificarPosicio(posicioActual, posicioNova);
    }

    public void modificarStock() throws SQLException {
        System.out.println("Introdueix el nou stock:");
        int stock = Integer.parseInt(sc.nextLine());
        System.out.println("Selecciona la posició que vols actualitzar:");
        int posicio = Integer.parseInt(sc.nextLine());
        slot.modificarStock(stock, posicio);
    }

    public void crearSlot() throws SQLException {
        System.out.println("Introdueix la posició:");
        int posicio = Integer.parseInt(sc.nextLine());
        System.out.println("Introdueix el stock del producte:");
        int quantitat = Integer.parseInt(sc.nextLine());
        System.out.println("Introdueix el codi del producte:");
        String codiProducte = sc.nextLine();
        Slot s = new Slot(posicio, quantitat,codiProducte);
        slot.createSlot(s);
    }

    public String readLine() {
        return sc.nextLine();
    }

    public void mostrarBenefici() {System.out.println("El benefici és " + benefici);}
}
