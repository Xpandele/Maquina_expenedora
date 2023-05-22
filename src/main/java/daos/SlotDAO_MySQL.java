package daos;

import model.Slot;

import java.sql.*;
import java.util.ArrayList;

public class SlotDAO_MySQL implements SlotDAO {
    //Dades de connexió a la base de dades
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_ROUTE = "jdbc:mysql://localhost:3306/expenedora";
    private static final String DB_USER = "root";
    private static final String DB_PWD = "Xavieronyk123";

    private Connection conn = null;

    public SlotDAO_MySQL() {

        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_ROUTE, DB_USER, DB_PWD);
            System.out.println("Conexió establerta satisfactoriament");
        } catch (Exception e) {
            System.out.println("S'ha produit un error en intentar connectar amb la base de dades. Revisa els paràmetres");
            System.out.println(e);
        }
    }

    @Override
    public void createSlot(Slot s) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO slot VALUES (?,?,?)");
        ps.setInt(1, s.getPosicio());
        ps.setInt(2, s.getQuantitat());
        ps.setString(3, s.getCodiProducte());
        ps.executeUpdate();
    }

    @Override
    public Slot readSlot() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Slot> readSlots() throws SQLException {
        ArrayList<Slot> llistaSlots = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM slot");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Slot s = new Slot();
            s.setPosicio(Integer.parseInt(rs.getString(1)));
            s.setQuantitat(Integer.parseInt(rs.getString(2)));
            s.setCodiProducte(rs.getString(3));

            llistaSlots.add(s);
        }
        return llistaSlots;
    }

    @Override
    public void updateSlots(Slot s) throws SQLException {
    }

    @Override
    public void deleteSlots(Slot s) throws SQLException {
    }

    @Override
    public void deleteSlot(int posicio) throws SQLException {
    }
    @Override
    public float modificarQuantitat(String nom) throws SQLException {
        float benef = 0;
        PreparedStatement ps = conn.prepareStatement("SELECT quantitat FROM slot, producte WHERE nom = ?");
        ps.setString(1, nom);
        ResultSet rs = ps.executeQuery();
        rs.next();

        String quant = rs.getString(1);
        if (!quant.equals("0")) {
            ps = conn.prepareStatement("UPDATE slot SET quantitat = quantitat-1  WHERE codi_producte = (SELECT codi_producte FROM producte WHERE nom = ?)");
            ps.setString(1, nom);
            ps.executeUpdate();
        }

        ps = conn.prepareStatement("SELECT preu_venta - preu_copmra as Benefici FROM producte WHERE nom = ?");
        ps.setString(1, nom);
        rs = ps.executeQuery();

        if(rs.next()) {
            benef = rs.getFloat("Benefici");
        }

        return benef;
    }
    @Override
    public void modificarStock(int stock, int posicio) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT quantitat FROM slot WHERE posicio = ?");
        ps.setInt(1, posicio);
        ResultSet rs = ps.executeQuery();
        rs.next();

        ps = conn.prepareStatement("UPDATE slot SET quantitat = ? WHERE posicio = ?");
        ps.setInt(1, stock);
        ps.setInt(2, posicio);
        ps.executeUpdate();
    }

    public void modificarPosicio(int posicioActual, int posicioNova) throws SQLException {
        PreparedStatement pr = conn.prepareStatement("SELECT posicio FROM slot WHERE posicio = ?");
        pr.setInt(1, posicioActual);
        ResultSet rs = pr.executeQuery();
        rs.next();

        pr = conn.prepareStatement("UPDATE slot SET posicio = ? WHERE posicio = ?");
        pr.setInt(1, posicioNova);
        pr.setInt(2, posicioActual);
        pr.executeUpdate();
    }
}
