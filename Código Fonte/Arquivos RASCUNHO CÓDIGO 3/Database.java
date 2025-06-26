import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

public class Database {
    private ConnectionSource connection;

    public Database(String databaseName) {
        try {
            String databaseUrl = "jdbc:sqlite:" + databaseName;
            this.connection = new JdbcConnectionSource(databaseUrl);
            System.out.println("Opened database successfully");
        } catch (SQLException e) {
            System.err.println("Error opening database: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }

    public ConnectionSource getConnection() {
        return connection;
    }

    public void close() {
        try {
            connection.close();
            System.out.println("Database connection closed.");
        } catch (Exception e) {
            System.err.println("Error closing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}