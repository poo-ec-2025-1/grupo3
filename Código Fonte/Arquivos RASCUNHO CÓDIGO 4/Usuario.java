import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "usuario")
public abstract class Usuario implements Autenticavel {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String nomeCompleto;

    @DatabaseField
    private int matricula;

    @DatabaseField
    private String senha;

    public Usuario() {
    }

    public Usuario(String nomeCompleto, int matricula, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.matricula = matricula;
        this.senha = senha;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public int getMatricula() {
        return matricula;
    }

    public String getSenha() {
        return senha;
    }

    public String mostrarDados() {
        return "Nome: " + nomeCompleto + "\nMatricula: " + matricula;
    }

    @Override
    public boolean login(int matricula, String senha) {
        return this.matricula == matricula && this.senha.equals(senha);
    }
}