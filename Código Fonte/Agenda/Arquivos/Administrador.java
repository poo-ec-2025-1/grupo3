public class Administrador extends Usuario implements Autenticavel {
    Administrador(String nomeCompleto, int matricula, String senha) {
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
