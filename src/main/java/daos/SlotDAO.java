package daos;

import model.Producte;
import model.Slot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public interface SlotDAO {
    public void createSlot(Slot s) throws SQLException;

    public Slot readSlot() throws SQLException;

    public ArrayList<Slot> readSlots() throws SQLException;

    public void updateSlots(Slot s) throws SQLException;

    public void deleteSlots(Slot s) throws SQLException;

    public void deleteSlot(int posicio) throws SQLException;


    float modificarQuantitat(String nom) throws SQLException;

    public void modificarStock(int slot, int posicioSlot) throws SQLException;
}
