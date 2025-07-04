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
 * Escreva uma descrição da classe DiaComReservaRepository aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class DiaComReservaRepository {
    
        private Dao<DiaComReserva, Integer> dao;
    
        public DiaComReservaRepository(Database database) {
            try {
                ConnectionSource connection = database.getConnection();
                dao = DaoManager.createDao(connection, DiaComReserva.class);
                TableUtils.createTableIfNotExists(connection, DiaComReserva.class);
            } catch (SQLException e) {
                System.err.println("Erro ao criar DiaComReservaRepository: " + e.getMessage());
                e.printStackTrace();
                System.exit(0);
            }
        }
    
        public DiaComReserva create(DiaComReserva dia) {
            int nrows = 0;
            try {
                nrows = dao.create(dia);
                if ( nrows == 0 )
                    throw new SQLException("Erro ao criar o objeto.");
            } catch (SQLException e) {
                System.out.println(e);
            }
            return dia;
        }
    
        public List<DiaComReserva> loadAll() {
            try {
                return dao.queryForAll();
            } catch (SQLException e) {
                System.err.println("Erro ao carregar dias da reserva: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    
        public boolean update(DiaComReserva dia) {
            try {
                int nrows = dao.update(dia);
                return nrows > 0;
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar dia da reserva: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    
        public boolean delete(DiaComReserva dia) {
            try {
                int nrows = dao.delete(dia);
                return nrows > 0;
            } catch (SQLException e) {
                System.err.println("Erro ao deletar dia da reserva: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    
        public DiaComReserva loadFromId(int id) {
            try {
                return dao.queryForId(id);
            } catch (SQLException e) {
                System.err.println("Erro ao carregar dia da reserva por ID: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    
}
