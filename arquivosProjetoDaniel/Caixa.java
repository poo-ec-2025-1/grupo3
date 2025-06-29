import java.sql.SQLException;

public class Caixa {
    private final UsuarioRepository usuarioRepository;
    private final AparelhoRepository aparelhoRepository;
    private String mensagemDeAviso;
    
    public Caixa(UsuarioRepository usuarioRepository, AparelhoRepository aparelhoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.aparelhoRepository = aparelhoRepository;
    }
    
    public boolean adicionarSaldo(int usuarioId, double valor, MetodoDePagamento listaMetodos) {
        try {
            Usuario usuario = usuarioRepository.findFromId(usuarioId);
            if(usuario == null) {
                mensagemDeAviso = ("Erro: Usuário com ID: " +usuarioId+ " não encontrado.");
                return false;
            }
            usuario.depositar(valor);
            usuarioRepository.update(usuario);
            mensagemDeAviso = ("Depósito feito com sucesso!");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro no depósito.");
            return false;
        }
    }
    
    public boolean debitarSaldo(int usuarioId, int aparelhoId) {
        try {
            Usuario usuario = usuarioRepository.findFromId(usuarioId);
            Aparelho aparelho = aparelhoRepository.findFromId(aparelhoId);
            double valor = aparelho.getCusto();
            if(usuario == null) {
                mensagemDeAviso = ("Erro: Usuário com ID: " +usuarioId+ " não encontrado.");
                return false;
            }
            if(usuario.getSaldo() >= valor) {
                usuario.debitar(valor);
                usuarioRepository.update(usuario);
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