import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import com.j256.ormlite.table.TableUtils;
import java.util.List;
import java.util.ArrayList;

public class ReservaRepository {
    private static Database database;
    private static Dao<Reserva, Integer> dao;
    
    public ReservaRepository(Database database) {
        ReservaRepository.setDatabase(database);
    }
    
    public static void setDatabase(Database database) {
        ReservaRepository.database = database;
        try {
            dao = DaoManager.createDao(database.getConnection(), Reserva.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Reserva.class);
        }
        catch(SQLException e) {
            System.out.println(e);
        }            
    }
    
    public Reserva create(Reserva reserva) {
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

    public void update(Reserva reserva) {
      try {
            dao.update(reserva);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar a reserva: " + e.getMessage());
        }
    }

    public void delete(Reserva reserva) {
      try {
          dao.delete(reserva);
      } catch(SQLException e) {
          System.err.println("Erro ao deletar a reserva: " + e.getMessage());
      }
    }
    
    public void deletePorId(int id) {
        try {
            dao.deleteById(id);
        } catch(SQLException e) {
            System.err.println("Erro ao deletar o reserva: " +e.getMessage());
        }
    }
    
    public Reserva loadFromId(int id) {
        Reserva loadedReserva = new Reserva();
        try {
            loadedReserva = dao.queryForId(id);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return loadedReserva;
    }    
    
    public List<Reserva> loadAll() {
        Reserva loadedReserva = new Reserva();
        List<Reserva> loadedReservas = new ArrayList<Reserva>();
        try {
            loadedReservas =  dao.queryForAll();
            if (loadedReservas.size() != 0)
                loadedReserva = loadedReservas.get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return loadedReservas;
    }
}
