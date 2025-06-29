import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao
.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

class ReservaRepository {
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
            TableUtils.createTableIfNotExists(database.getConnection(), Reserva.class);
            dao = DaoManager.createDao(database.getConnection(), Reserva.class);
        } catch(SQLException e) {
            System.err.println("Error setting up Reserva database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Reserva create(Reserva reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não pode ser nula");
        }
        
        try {
            int nrows = dao.create(reserva);
            if (nrows == 0) {
                throw new SQLException("Erro: objeto não foi salvo");
            }
            this.loadedReserva = reserva;
            loadedReservas.add(reserva);
        } catch (SQLException e) {
            System.err.println("Erro ao criar reserva: " + e.getMessage());
            e.printStackTrace();
        }
        return reserva;
    }
    
    public boolean update(Reserva reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não pode ser nula");
        }
        
        try {
            int nrows = dao.update(reserva);
            return nrows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar reserva: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(Reserva reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não pode ser nula");
        }
        
        try {
            int nrows = dao.delete(reserva);
            if (nrows > 0) {
                loadedReservas.remove(reserva);
                if (this.loadedReserva == reserva) {
                    this.loadedReserva = null;
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar reserva: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public Reserva loadFromId(int id) {
        try {
            this.loadedReserva = dao.queryForId(id);
            if (this.loadedReserva != null) {
                boolean alreadyLoaded = loadedReservas.contains(this.loadedReserva);
                if (!alreadyLoaded) {
                    this.loadedReservas.add(this.loadedReserva);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar reserva por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return this.loadedReserva;
    }
    
    public List<Reserva> loadAll() {
        try {
            this.loadedReservas = dao.queryForAll();
            if (this.loadedReservas.size() != 0) {
                this.loadedReserva = this.loadedReservas.get(0);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar todas as reservas: " + e.getMessage());
            e.printStackTrace();
        }
        return this.loadedReservas;
    }
    
    public List<Reserva> findByUsuario(Usuario usuario) {
        try {
            return dao.queryForEq("user", usuario);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar reservas por usuário: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // Getters
    public List<Reserva> getLoadedReservas() { return loadedReservas; }
    public Reserva getLoadedReserva() { return loadedReserva; }
}
