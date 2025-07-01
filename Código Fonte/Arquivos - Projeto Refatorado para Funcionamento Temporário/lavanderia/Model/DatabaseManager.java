package lavanderia.Model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class DatabaseManager {

    // Use jdbc:sqlite: para criar um arquivo no diretório do projeto. É mais robusto.
    private static final String DATABASE_URL = "jdbc:sqlite:lavanderia.db";
    private static ConnectionSource connectionSource;

    // DAOs para cada entidade
    private static Dao<Usuario, Integer> usuarioDao;
    private static Dao<Aparelho, Integer> aparelhoDao;
    private static Dao<Reserva, Integer> reservaDao;
    
    public static void init() throws SQLException {
        if (connectionSource != null) return;

        // 1. Criar a fonte de conexão
        connectionSource = new JdbcConnectionSource(DATABASE_URL);

        // 2. Criar as tabelas se elas não existirem
        TableUtils.createTableIfNotExists(connectionSource, Usuario.class);
        TableUtils.createTableIfNotExists(connectionSource, Aparelho.class);
        TableUtils.createTableIfNotExists(connectionSource, Reserva.class);
        

        // 3. Criar os DAOs diretamente a partir do DaoManager
        usuarioDao = DaoManager.createDao(connectionSource, Usuario.class);
        aparelhoDao = DaoManager.createDao(connectionSource, Aparelho.class);
        reservaDao = DaoManager.createDao(connectionSource, Reserva.class);
    }

    // Métodos para obter os DAOs
    public static Dao<Usuario, Integer> getUsuarioDao() {
        if (usuarioDao == null) throw new IllegalStateException("DatabaseManager não foi inicializado.");
        return usuarioDao;
    }
    
    public static Dao<Aparelho, Integer> getAparelhoDao() {
        if (aparelhoDao == null) throw new IllegalStateException("DatabaseManager não foi inicializado.");
        return aparelhoDao;
    }

    public static Dao<Reserva, Integer> getReservaDao() {
        if (reservaDao == null) throw new IllegalStateException("DatabaseManager não foi inicializado.");
        return reservaDao;
    }

    public static void close() throws Exception {
        if (connectionSource != null) {
            connectionSource.close();
        }
    }
}