import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

class IntervaloDeUsoRepository {
    private static Database database;
    private static Dao<IntervaloDeUso, Integer> dao;
    private List<IntervaloDeUso> loadedIntervalos;
    private IntervaloDeUso loadedIntervalo;
    
    public IntervaloDeUsoRepository(Database database) {
        IntervaloDeUsoRepository.setDatabase(database);
        loadedIntervalos = new ArrayList<IntervaloDeUso>();
    }
    
    public static void setDatabase(Database database) {
        IntervaloDeUsoRepository.database = database;
        try {
            dao = DaoManager.createDao(database.getConnection(), IntervaloDeUso.class);
            TableUtils.createTableIfNotExists(database.getConnection(), IntervaloDeUso.class);
        } catch(SQLException e) {
            System.err.println("Error setting up IntervaloDeUso database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public IntervaloDeUso create(IntervaloDeUso intervalo) {
        if (intervalo == null) {
            throw new IllegalArgumentException("IntervaloDeUso não pode ser nulo");
        }
        
        try {
            int nrows = dao.create(intervalo);
            if (nrows == 0) {
                throw new SQLException("Erro: objeto não foi salvo");
            }
            this.loadedIntervalo = intervalo;
            loadedIntervalos.add(intervalo);
        } catch (SQLException e) {
            System.err.println("Erro ao criar intervalo de uso: " + e.getMessage());
            e.printStackTrace();
        }
        return intervalo;
    }
    
    public boolean update(IntervaloDeUso intervalo) {
        if (intervalo == null) {
            throw new IllegalArgumentException("IntervaloDeUso não pode ser nulo");
        }
        
        try {
            int nrows = dao.update(intervalo);
            if (nrows == 0) {
                return false;
            }
            
            if (this.loadedIntervalo != null && this.loadedIntervalo.getId() == intervalo.getId()) {
                this.loadedIntervalo = intervalo;
            }
            
            for (int i = 0; i < loadedIntervalos.size(); i++) {
                if (loadedIntervalos.get(i).getId() == intervalo.getId()) {
                    loadedIntervalos.set(i, intervalo);
                    break;
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar intervalo de uso: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(IntervaloDeUso intervalo) {
        if (intervalo == null) {
            throw new IllegalArgumentException("IntervaloDeUso não pode ser nulo");
        }
        
        try {
            int nrows = dao.delete(intervalo);
            if (nrows == 0) {
                return false;
            }
            
            if (this.loadedIntervalo != null && this.loadedIntervalo.getId() == intervalo.getId()) {
                this.loadedIntervalo = null;
            }
            
            loadedIntervalos.removeIf(i -> i.getId() == intervalo.getId());
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar intervalo de uso: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public IntervaloDeUso loadFromId(int id) {
        try {
            this.loadedIntervalo = dao.queryForId(id);
            if (this.loadedIntervalo != null) {
                boolean alreadyLoaded = loadedIntervalos.stream()
                    .anyMatch(i -> i.getId() == this.loadedIntervalo.getId());
                if (!alreadyLoaded) {
                    this.loadedIntervalos.add(this.loadedIntervalo);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar intervalo de uso por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return this.loadedIntervalo;
    }
    
    public List<IntervaloDeUso> loadAll() {
        try {
            this.loadedIntervalos = dao.queryForAll();
            if (this.loadedIntervalos.size() != 0) {
                this.loadedIntervalo = this.loadedIntervalos.get(0);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar todos os intervalos de uso: " + e.getMessage());
            e.printStackTrace();
        }
        return this.loadedIntervalos;
    }
    
    public List<IntervaloDeUso> findByAparelho(Aparelho aparelho) {
        try {
            return dao.queryForEq("aparelho", aparelho);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar intervalos por aparelho: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public List<IntervaloDeUso> findByStatus(IntervaloDeUso.Status status) {
        try {
            return dao.queryForEq("status", status);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar intervalos por status: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // Getters
    public List<IntervaloDeUso> getLoadedIntervalos() { return loadedIntervalos; }
    public IntervaloDeUso getLoadedIntervalo() { return loadedIntervalo; }
}
