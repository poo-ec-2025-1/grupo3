import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import com.j256.ormlite.table.TableUtils;
import java.util.List;
import java.util.ArrayList;

public class AparelhoRepository {
    private static Database database;
    private static Dao<Aparelho, Integer> dao;
    
    public AparelhoRepository(Database database) {
        AparelhoRepository.setDatabase(database);
    }
    
    public static void setDatabase(Database database) {
        AparelhoRepository.database = database;
        try {
            dao = DaoManager.createDao(database.getConnection(), Aparelho.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Aparelho.class);
        }
        catch(SQLException e) {
            System.out.println(e);
        }            
    }
    
    public Aparelho create(Aparelho aparelho) {
        int nrows = 0;
        try {
            nrows = dao.create(aparelho);
            if ( nrows == 0 )
                throw new SQLException("Erro ao criar o objeto.");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return aparelho;
    }    

    public void update(Aparelho aparelho) {
      try {
            dao.update(aparelho);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o aparelho: " + e.getMessage());
        }
    }

    public void delete(Aparelho aparelho) {
      try {
          dao.delete(aparelho);
      } catch(SQLException e) {
          System.err.println("Erro ao deletar o aparelho: " + e.getMessage());
      }
    }
    
    public void deletePorId(int id) {
        try {
            dao.deleteById(id);
        } catch(SQLException e) {
            System.err.println("Erro ao deletar o aparelho: " +e.getMessage());
        }
    }
    
    public Aparelho loadFromId(int id) {
        Aparelho loadedAparelho = new Aparelho();
        try {
            loadedAparelho = dao.queryForId(id);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return loadedAparelho;
    }    
    
    public List<Aparelho> loadAll() {
        Aparelho loadedAparelho = new Aparelho();
        List<Aparelho> loadedAparelhos = new ArrayList<Aparelho>();
        try {
            loadedAparelhos =  dao.queryForAll();
            if (loadedAparelhos.size() != 0)
                loadedAparelho = loadedAparelhos.get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return loadedAparelhos;
    }
}