package lavanderia.Model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class IntervaloDeUsoRepository {
        private Dao<IntervaloDeUso, Integer> dao;

        public IntervaloDeUsoRepository() {
            this.dao = DatabaseManager.getIntervaloDeUsoDao();
        }
        
        public IntervaloDeUsoRepository(Database database) {
            this.dao = DatabaseManager.getIntervaloDeUsoDao();
        }
        
        public Dao<IntervaloDeUso, Integer> getDao() {
        return this.dao;
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

        public IntervaloDeUso loadFromId(int id) {
            try {
                return dao.queryForId(id);
            } catch (SQLException e) {
                System.err.println("Erro ao carregar intervalo de uso por ID: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        
        /*
         * Encontra todos os intervalos de uso com base na data
         * e no id de um aparelho específico a ser reservado.
         * Esse método retorna uma lista de IntervaloDeUso já reservados.
         */
        
        public List<IntervaloDeUso> loadReservadosPorDiaEAparelho(LocalDate data, int aparelhoId) {
            try {
                String dataComoString = data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                QueryBuilder<IntervaloDeUso, Integer> queryBuilder = dao.queryBuilder();
                queryBuilder.where()
                    .eq("aparelho_id", aparelhoId)
                    .and()
                    .like("inicio", dataComoString + "%"); // Busca registros que começam com a data
                return queryBuilder.query();
            } catch (SQLException e) {
                System.err.println("Erro ao buscar intervalos reservados: " + e.getMessage());
                e.printStackTrace();
                return new ArrayList<>();
            }
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
    
}