import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import com.j256.ormlite.table.TableUtils;
import java.util.List;
import java.util.ArrayList;

public class AparelhoRepository {
    private static Database database;
    private static Dao<Aparelho, Integer> dao;
    private List<Aparelho> loadedAparelhos;
    private Aparelho loadedAparelho;
    
    public AparelhoRepository(Database database) {
        AparelhoRepository.setDatabase(database);
        loadedAparelhos = new ArrayList<Aparelho>();
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
                throw new SQLException("Error: object not saved");
            this.loadedAparelho = aparelho;
            loadedAparelhos.add(aparelho);
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
    
    public Aparelho findFromId(int id) {
        try {
            this.loadedAparelho = dao.queryForId(id);
            if(id == 0) {
                System.out.println("ID inválido.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedAparelho;
    }
    
    public Aparelho loadFromId(int id) {
        try {
            this.loadedAparelho = dao.queryForId(id);
            if (this.loadedAparelho != null)
                this.loadedAparelhos.add(this.loadedAparelho);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedAparelho;
    }    
    
    public List<Aparelho> loadAll() {
        try {
            this.loadedAparelhos =  dao.queryForAll();
            if (this.loadedAparelhos.size() != 0)
                this.loadedAparelho = this.loadedAparelhos.get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedAparelhos;
    }
}
