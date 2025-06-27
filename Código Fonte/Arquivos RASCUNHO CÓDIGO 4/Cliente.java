public class Cliente extends Usuario {
    public Cliente() {
        // Construtor sem argumentos necessário para ORMLite
    }

    public Cliente(String nomeCompleto, int matricula, String senha) {
        super(nomeCompleto, matricula, senha);
    }
}