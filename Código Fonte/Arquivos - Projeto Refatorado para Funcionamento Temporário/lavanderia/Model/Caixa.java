package lavanderia.Model;
 

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;

/** Essa classe é a responsável por fazer os serviços relacionados às operações
 *  de adicionar/debitar saldo do Usuário.
 * 
 */

public class Caixa {
    private Dao<Usuario, Integer> usuarioDao;
    private Dao<Aparelho, Integer> aparelhoDao;
    private String mensagemDeAviso;
    
    public Caixa() {
        try {
            DatabaseManager.init();
            UsuarioRepository usuarioRepo = new UsuarioRepository();
            usuarioDao = usuarioRepo.getDao();
            AparelhoRepository aparelhoRepo = new AparelhoRepository();
            aparelhoDao = aparelhoRepo.getDao();
        } catch (SQLException e) {
            System.err.println("Erro ao iniciar o caixa.");
        }
    }
    
    public boolean adicionarSaldo(int usuarioId, double valor) {
        try {
            DatabaseManager.init();
            UsuarioRepository usuarioRepo = new UsuarioRepository();
            Usuario usuario = usuarioRepo.loadFromId(usuarioId);
            if(usuario == null) {
                mensagemDeAviso = ("Erro: Usuário com ID: " +usuarioId+ " não encontrado.");
                return false;
            }
            usuario.depositar(valor);
            usuarioRepo.update(usuario);
            mensagemDeAviso = ("Depósito feito com sucesso!");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro no depósito.");
            return false;
        }
    }
    
    public boolean debitarSaldo(int usuarioId, int aparelhoId) {
        try {
            DatabaseManager.init();
            UsuarioRepository usuarioRepo = new UsuarioRepository();
            Usuario usuario = usuarioRepo.loadFromId(usuarioId);
            AparelhoRepository aparelhoRepo = new AparelhoRepository();
            Aparelho aparelho = aparelhoRepo.loadFromId(aparelhoId);
            double valor = aparelho.getCusto();
            if(usuario == null) {
                mensagemDeAviso = ("Erro: Usuário com ID: " +usuarioId+ " não encontrado.");
                return false;
            }
            if(usuario.getSaldo() >= valor) {
                usuario.debitar(valor);
                usuarioRepo.update(usuario);
                mensagemDeAviso = ("Pagamento feito com sucesso!");
                return true;
            } else {
                mensagemDeAviso = ("Saldo insuficiente.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro no pagamento.");
            return false;
        }
    }
}
