package lavanderia.Model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class InstanciarMaquinas {
    public void instanciar() {
        try {
            DatabaseManager.init();
            Aparelho aparelho = new Aparelho("Eletrolux", 15, "Funcionando", true, 16);
            Aparelho aparelho1 = new Aparelho("Midea", 12, "Funcionando", true, 12);
            Aparelho aparelho2 = new Aparelho("Brastemp", 18, "Funcionando", true, 20);
            
            AparelhoRepository aparelhoRepo = new AparelhoRepository();
            aparelhoRepo.create(aparelho);
            aparelhoRepo.create(aparelho1);
            aparelhoRepo.create(aparelho2);
        } catch (SQLException e) {
            System.err.println("Erro ao instanciar os aparelhos.");
        }
    }
}
