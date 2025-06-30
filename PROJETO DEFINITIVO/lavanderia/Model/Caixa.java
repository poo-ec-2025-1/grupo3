package lavanderia.Model;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Caixa {
    @com.j256.ormlite.table.DatabaseTable(tableName = "transactions")
    public static class Transaction {
        @com.j256.ormlite.field.DatabaseField(generatedId = true)
        private int id;

        @com.j256.ormlite.field.DatabaseField
        private int matricula;

        @com.j256.ormlite.field.DatabaseField
        private double valor;

        @com.j256.ormlite.field.DatabaseField
        private String data;

        @com.j256.ormlite.field.DatabaseField(foreign = true, foreignAutoRefresh = true)
        private Aparelho aparelho;

        Transaction() {
            // Construtor vazio necessário para ORMLite
        }

        Transaction(int matricula, double valor, Aparelho aparelho) {
            this.matricula = matricula;
            this.valor = valor;
            this.data = LocalDateTime.now().toString();
            this.aparelho = aparelho;
        }

        // Getters
        public int getMatricula() {
            return matricula;
        }

        public double getValor() {
            return valor;
        }

        public String getData() {
            return data;
        }

        public Aparelho getAparelho() {
            return aparelho;
        }
    }

    private static Database database = new Database("lavanderia.db");
    private static Dao<Transaction, Integer> transactionDao;
    private static AparelhoRepository aparelhoRepository;

    static {
        try {
            ConnectionSource connection = database.getConnection();
            transactionDao = DaoManager.createDao(connection, Transaction.class);
            aparelhoRepository = new AparelhoRepository(database);
            System.out.println("Tabela transactions e aparelhos inicializadas.");
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar Caixa: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void cobrar(Usuario usuario, Aparelho aparelho) {
        if (usuario instanceof Autenticavel && ((Autenticavel) usuario).login(usuario.getMatricula(), usuario.getSenha())) {
            double valor = aparelho.getCusto();
            System.out.println("Matrícula: " + usuario.getMatricula());
            System.out.println("Total a pagar: R$ " + String.format("%.2f", valor));

            try {
                Transaction transacao = new Transaction(usuario.getMatricula(), valor, aparelho);
                transactionDao.create(transacao);
                System.out.println("Transação registrada no banco em " + LocalDateTime.now());
            } catch (SQLException e) {
                System.err.println("Erro ao registrar transação: " + e.getMessage());
                e.printStackTrace();
                System.out.println("Falha ao registrar transação em " + LocalDateTime.now());
            }
        } else {
            System.out.println("Autenticação falhou para matrícula " + usuario.getMatricula());
        }
    }

    public static List<Transaction> getTransacoesPorMatricula(int matricula) {
        try {
            return transactionDao.queryForEq("matricula", matricula);
        } catch (SQLException e) {
            System.err.println("Erro ao consultar transações: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection() {
        database.close();
    }
}
