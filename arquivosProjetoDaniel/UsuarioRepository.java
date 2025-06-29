import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import com.j256.ormlite.table.TableUtils;
import java.util.List;
import java.util.ArrayList;

public class UsuarioRepository {
    private static Database database;
    private static Dao<Usuario, Integer> dao;
    private List<Usuario> loadedUsuarios;
    private Usuario loadedUsuario;
    
    public UsuarioRepository(Database database) {
        UsuarioRepository.setDatabase(database);
        loadedUsuarios = new ArrayList<Usuario>();
    }
    
    public static void setDatabase(Database database) {
        UsuarioRepository.database = database;
        try {
            dao = DaoManager.createDao(database.getConnection(), Usuario.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Usuario.class);
        }
        catch(SQLException e) {
            System.out.println(e);
        }            
    }
    
    public Usuario create(Usuario usuario) {
        int nrows = 0;
        try {
            nrows = dao.create(usuario);
            if ( nrows == 0 )
                throw new SQLException("Error: object not saved");
            this.loadedUsuario = usuario;
            loadedUsuarios.add(usuario);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return usuario;
    }    

    public void update(Usuario usuario) throws SQLException {
      try {
            dao.update(usuario);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o usuário: " + e.getMessage());
            throw new SQLException();
        }
    }

    public void delete(Usuario usuario) {
      try {
            dao.delete(usuario);
        } catch (SQLException e) {
            System.err.println("Erro ao deletar o usuário: " + e.getMessage());
        }
    }
    
    public void deletePorId(int id) {
        try {
            dao.deleteById(id);
        } catch(SQLException e) {
            System.err.println("Erro ao deletar o usuário: " +e.getMessage());
        }
    }
    
    public Usuario findFromId(int id) {
        try {
            this.loadedUsuario = dao.queryForId(id);
            if(id == 0) {
                System.out.println("ID inválido.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedUsuario;
    }
    
    public Usuario loadFromId(int id) {
        try {
            this.loadedUsuario = dao.queryForId(id);
            if (this.loadedUsuario != null)
                this.loadedUsuarios.add(this.loadedUsuario);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedUsuario;
    }    
    
    public List<Usuario> loadAll() {
        try {
            this.loadedUsuarios =  dao.queryForAll();
            if (this.loadedUsuarios.size() != 0)
                this.loadedUsuario = this.loadedUsuarios.get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedUsuarios;
    }
}
