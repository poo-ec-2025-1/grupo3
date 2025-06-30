package lavanderia.Model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


class DiaDaReservaRepository {
    private static Database database;
    private static Dao<DiaDaReserva, Integer> dao;
    private List<DiaDaReserva> loadedDias;
    private DiaDaReserva loadedDia;
    
    public DiaDaReservaRepository(Database database) {
        DiaDaReservaRepository.setDatabase(database);
        loadedDias = new ArrayList<DiaDaReserva>();
    }
    
    public static void setDatabase(Database database) {
        DiaDaReservaRepository.database = database;
        try {
            dao = DaoManager.createDao(database.getConnection(), DiaDaReserva.class);
            TableUtils.createTableIfNotExists(database.getConnection(), DiaDaReserva.class);
        } catch(SQLException e) {
            System.err.println("Error setting up DiaDaReserva database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public DiaDaReserva create(DiaDaReserva dia) {
        if (dia == null) {
            throw new IllegalArgumentException("DiaDaReserva não pode ser nulo");
        }
        
        try {
            int nrows = dao.create(dia);
            if (nrows == 0) {
                throw new SQLException("Erro: objeto não foi salvo");
            }
            this.loadedDia = dia;
            loadedDias.add(dia);
        } catch (SQLException e) {
            System.err.println("Erro ao criar dia da reserva: " + e.getMessage());
            e.printStackTrace();
        }
        return dia;
    }
    
    public boolean update(DiaDaReserva dia) {
        if (dia == null) {
            throw new IllegalArgumentException("DiaDaReserva não pode ser nulo");
        }
        
        try {
            int nrows = dao.update(dia);
            if (nrows == 0) {
                return false;
            }
            
            if (this.loadedDia != null && this.loadedDia.getIdDiaDaReserva() == dia.getIdDiaDaReserva()) {
                this.loadedDia = dia;
            }
            
            for (int i = 0; i < loadedDias.size(); i++) {
                if (loadedDias.get(i).getIdDiaDaReserva() == dia.getIdDiaDaReserva()) {
                    loadedDias.set(i, dia);
                    break;
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar dia da reserva: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(DiaDaReserva dia) {
        if (dia == null) {
            throw new IllegalArgumentException("DiaDaReserva não pode ser nulo");
        }
        
        try {
            int nrows = dao.delete(dia);
            if (nrows == 0) {
                return false;
            }
            
            if (this.loadedDia != null && this.loadedDia.getIdDiaDaReserva() == dia.getIdDiaDaReserva()) {
                this.loadedDia = null;
            }
            
            loadedDias.removeIf(d -> d.getIdDiaDaReserva() == dia.getIdDiaDaReserva());
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar dia da reserva: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public DiaDaReserva loadFromId(int id) {
        try {
            this.loadedDia = dao.queryForId(id);
            if (this.loadedDia != null) {
                boolean alreadyLoaded = loadedDias.stream()
                    .anyMatch(d -> d.getIdDiaDaReserva() == this.loadedDia.getIdDiaDaReserva());
                if (!alreadyLoaded) {
                    this.loadedDias.add(this.loadedDia);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar dia da reserva por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return this.loadedDia;
    }
    
    public List<DiaDaReserva> loadAll() {
        try {
            this.loadedDias = dao.queryForAll();
            if (this.loadedDias.size() != 0) {
                this.loadedDia = this.loadedDias.get(0);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar todos os dias da reserva: " + e.getMessage());
            e.printStackTrace();
        }
        return this.loadedDias;
    }
    
    public List<DiaDaReserva> findByAparelho(Aparelho aparelho) {
        try {
            return dao.queryForEq("aparelho", aparelho);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar dias por aparelho: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public List<DiaDaReserva> findByAno(int ano) {
        try {
            return dao.queryForEq("ano", ano);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar dias por ano: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // Getters
    public List<DiaDaReserva> getLoadedDias() { return loadedDias; }
    public DiaDaReserva getLoadedDia() { return loadedDia; }
}
