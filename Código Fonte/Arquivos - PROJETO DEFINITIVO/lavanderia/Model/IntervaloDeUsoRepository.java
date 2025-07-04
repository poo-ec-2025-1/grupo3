package lavanderia.Model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Escreva uma descrição da classe IntervaloDeUsoRepository aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class IntervaloDeUsoRepository {
    private Dao<IntervaloDeUso, Integer> dao;
    
    public IntervaloDeUsoRepository(Database database) {
        try {
            ConnectionSource connection = database.getConnection();
            dao = DaoManager.createDao(connection, IntervaloDeUso.class);
            TableUtils.createTableIfNotExists(connection, IntervaloDeUso.class);
        } catch (SQLException e) {
            System.err.println("Erro ao criar IntervaloDeUsoRepository: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public IntervaloDeUso create(IntervaloDeUso intervalo) {
        int nrows = 0;
        try {
            nrows = dao.create(intervalo);
            if ( nrows == 0 )
                throw new SQLException("Erro ao criar o objeto.");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return intervalo;
    }
    
    public List<IntervaloDeUso> loadAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            System.err.println("Erro ao carregar intervalos de uso: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean update(IntervaloDeUso intervalo) {
        try {
            int nrows = dao.update(intervalo);
            return nrows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar intervalo de uso: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(IntervaloDeUso intervalo) {
        try {
            int nrows = dao.delete(intervalo);
            return nrows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar intervalo de uso: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public IntervaloDeUso loadFromId(int id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            System.err.println("Erro ao carregar intervalo de uso por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
