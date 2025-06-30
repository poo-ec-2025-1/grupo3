package lavanderia.Model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ReservaRepository {
    private Dao<Reserva, Integer> dao;

    public ReservaRepository() {
        this.dao = DatabaseManager.getReservaDao();
    }
    
    public ReservaRepository(Database database) {
        this.dao = DatabaseManager.getReservaDao();
    }

    public Dao<Reserva, Integer> getDao() {
        return this.dao;
    }
    
    public Reserva create(Reserva reserva){
        int nrows = 0;
        try {
            nrows = dao.create(reserva);
            if ( nrows == 0 )
                throw new SQLException("Erro ao criar o objeto.");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return reserva;
    }

    public List<Reserva> loadAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            System.err.println("Erro ao carregar reservas: " + e.getMessage());
            return null;
        }
    }

    public boolean update(Reserva reserva) {
        try {
            int rows = dao.update(reserva);
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar reserva: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(Reserva reserva) {
        try {
            int rows = dao.delete(reserva);
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar reserva: " + e.getMessage());
            return false;
        }
    }

    public Reserva loadFromId(int id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            System.err.println("Erro ao carregar reserva por ID: " + e.getMessage());
            return null;
        }
    }
}
