import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;

public class UsuarioRepository {
    private Dao<Usuario, Integer> usuarioDao;

    public UsuarioRepository(Database database) {
        try {
            ConnectionSource connection = database.getConnection();
            usuarioDao = DaoManager.createDao(connection, Usuario.class);
            TableUtils.createTableIfNotExists(connection, Usuario.class);
            TableUtils.createTableIfNotExists(connection, Cliente.class);
            TableUtils.createTableIfNotExists(connection, Administrador.class);
        } catch (SQLException e) {
            System.err.println("Erro ao criar UsuarioRepository: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void create(Usuario usuario) {
        try {
            usuarioDao.create(usuario);
        } catch (SQLException e) {
            System.err.println("Erro ao criar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Usuario> loadAll() {
        try {
            return usuarioDao.queryForAll();
        } catch (SQLException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}