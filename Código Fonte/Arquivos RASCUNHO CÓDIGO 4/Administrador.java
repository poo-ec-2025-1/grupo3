public class Administrador extends Usuario {
    public Administrador() {
        // Construtor sem argumentos necessário para ORMLite
    }

    public Administrador(String nomeCompleto, int matricula, String senha) {
        super(nomeCompleto, matricula, senha);
    }
}