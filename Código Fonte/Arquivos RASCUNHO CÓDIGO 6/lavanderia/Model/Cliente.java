package lavanderia.Model;

public class Cliente extends Usuario implements Autenticavel{
    Cliente(String nomeCompleto, int matricula, String senha) {
        super(nomeCompleto, matricula, senha);
    }
    
    @Override
    public boolean login(int matricula, String senha) {
        if(this.getMatricula() == matricula && this.getSenha().equals(senha)) {
            return true;
        } else {
            return false;
        }
    }
}
