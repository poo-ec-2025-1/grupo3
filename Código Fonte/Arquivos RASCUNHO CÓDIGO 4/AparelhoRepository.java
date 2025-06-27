import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;

public class AparelhoRepository {
    private Dao<Aparelho, Integer> aparelhoDao;

    public AparelhoRepository(Database database) {
        try {
            ConnectionSource connection = database.getConnection();
            aparelhoDao = DaoManager.createDao(connection, Aparelho.class);
            TableUtils.createTableIfNotExists(connection, Aparelho.class);
            TableUtils.createTableIfNotExists(connection, MaquinaDeLavar.class);
            TableUtils.createTableIfNotExists(connection, Secadora.class);
        } catch (SQLException e) {
            System.err.println("Erro ao criar AparelhoRepository: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void create(Aparelho aparelho) {
        try {
            aparelhoDao.create(aparelho);
        } catch (SQLException e) {
            System.err.println("Erro ao criar aparelho: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Aparelho> loadAll() {
        try {
            return aparelhoDao.queryForAll();
        } catch (SQLException e) {
            System.err.println("Erro ao carregar aparelhos: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}