import java.util.Date;
import java.text.SimpleDateFormat;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

@DatabaseTable(tableName = "usuario")

public class Usuario {
    @DatabaseField(generatedId = true, dataType=DataType.INTEGER)
    private int id;
    
    @DatabaseField(dataType=DataType.STRING)
    private String nomeCompleto;
    
    @DatabaseField(dataType=DataType.INTEGER)
    private int matricula;
    
    @DatabaseField(dataType=DataType.STRING)
    private String senha;
    
    @DatabaseField(dataType=DataType.DOUBLE)
    private double saldo;
    
    /* Esse atributo ainda não foi implementado; no futuro ele seria responsável por
    * armazenar o email de contato do Usuario. 
    * @DatabaseField
    * private String email;
    */
    
    public Usuario() {
    }
    
    Usuario(String nomeCompleto, int matricula, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.matricula = matricula;
        this.senha = senha;
        this.saldo = 0.0;
    }
   
    public boolean login(int matricula, String senha) {
        if(this.getMatricula() == matricula && this.getSenha().equals(senha)) {
            return true;
        } else {
            return false;
        }
    }
    
    public String mostrarDados() {
        return "Nome: "+this.getNomeCompleto()+"\nMatricula: "+this.getMatricula();
    }
    
    public void depositar(double valor) {
        if(valor <= 0) {
            throw new IllegalArgumentException("O valor a ser depositado deve ser positivo.");
        }
        this.saldo += valor;
    }
    
    public void debitar(double valor) {
        if(valor > this.saldo) {
            throw new IllegalStateException("Saldo insuficiente.");
        }
        this.saldo -= valor;
    }
    
//Start GetterSetterExtension Source Code
    
    /**SET Method Propertie id*/
    public void setId(int id) {
        this.id = id;
    }

    /**GET Method Propertie id*/
    public int getId() {
        return this.id;
    }

    /**SET Method Propertie nomeCompleto*/
    public void setNomeCompleto(String nome) {
        this.nomeCompleto = nome;
    }
    
    /**GET Method Propertie nomeCompleto*/
    public String getNomeCompleto() {
        return this.nomeCompleto;
    }
    
    /**SET Method Propertie matricula*/
    public void setMatricula(int matricula){
        this.matricula = matricula;
    }
   
    /**GET Method Propertie matricula*/
    public int getMatricula() {
        return this.matricula;
    }
   
    /**SET Method Propertie senha*/
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    /**GET Method Propertie senha*/
    public String getSenha() {
        return this.senha;
    }
    
    /**GET Method Propertie saldo*/
    public double getSaldo() {
        return this.saldo;
    }
    
//End GetterSetterExtension Source Code
}
