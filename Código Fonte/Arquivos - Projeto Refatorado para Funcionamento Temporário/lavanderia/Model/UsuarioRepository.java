package lavanderia.Model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class UsuarioRepository {
    private static Database database;
    private static Dao<Usuario, Integer> dao;
    
    
    public UsuarioRepository() {
        this.dao = DatabaseManager.getUsuarioDao();
    }
    
    public UsuarioRepository(Database database) {
        this.dao = DatabaseManager.getUsuarioDao();
    }
    
    public Dao<Usuario, Integer> getDao() {
        return this.dao;
    }
    
    public Usuario create(Usuario usuario) {
        int nrows = 0;
        try {
            nrows = dao.create(usuario);
            if ( nrows == 0 )
                throw new SQLException("Erro ao criar o objeto.");
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
    
    public Usuario buscarPorMatriculaESenha(int matricula, String senha) {
        try {
            return dao.queryBuilder()
                  .where()
                  .eq("matricula", matricula)
                  .and()
                  .eq("senha", senha)
                  .queryForFirst();
        } catch (SQLException e) {
        System.err.println("Erro ao buscar usuário por matrícula e senha: " + e.getMessage());
        return null;
        }
    }   
    
    public Usuario loadFromId(int id) {
        Usuario loadedUsuario = new Usuario();
        try {
            loadedUsuario = dao.queryForId(id);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return loadedUsuario;
    }    
    
    public List<Usuario> loadAll() {
        Usuario loadedUsuario = new Usuario();
        List<Usuario> loadedUsuarios = new ArrayList<Usuario>();
        try {
            loadedUsuarios =  dao.queryForAll();
            if (loadedUsuarios.size() != 0)
                loadedUsuario = loadedUsuarios.get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return loadedUsuarios;
    }
}
