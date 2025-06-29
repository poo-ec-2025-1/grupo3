import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import com.j256.ormlite.table.TableUtils;
import java.util.List;
import java.util.ArrayList;

public class ReservaRepository {
    private static Database database;
    private static Dao<Reserva, Integer> dao;
    private List<Reserva> loadedReservas;
    private Reserva loadedReserva;
    
    public ReservaRepository(Database database) {
        ReservaRepository.setDatabase(database);
        loadedReservas = new ArrayList<Reserva>();
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
                throw new SQLException("Error: object not saved");
            this.loadedReserva = reserva;
            loadedReservas.add(reserva);
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
    
    public Reserva loadFromId(int id) {
        try {
            this.loadedReserva = dao.queryForId(id);
            if (this.loadedReserva != null)
                this.loadedReservas.add(this.loadedReserva);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedReserva;
    }    
    
    public List<Reserva> loadAll() {
        try {
            this.loadedReservas =  dao.queryForAll();
            if (this.loadedReservas.size() != 0)
                this.loadedReserva = this.loadedReservas.get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedReservas;
    }
}
