package lavanderia.Model;

import java.util.Date;
import java.text.SimpleDateFormat;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

@DatabaseTable(tableName = "usuario")

public class Usuario {
    @DatabaseField(generatedId = true, dataType=DataType.INTEGER)
    private int id;
    
    @DatabaseField(canBeNull = false, dataType=DataType.STRING)
    private String nomeCompleto;
    
    @DatabaseField(canBeNull = false, unique = true, dataType=DataType.INTEGER)
    private int matricula;
    
    @DatabaseField(canBeNull = false, dataType=DataType.STRING)
    private String senha;
    
    @DatabaseField(dataType=DataType.DOUBLE)
    private double saldo;
    
    /* Esse atributo ainda não foi implementado; no futuro ele seria responsável por
    * armazenar o email de contato do Usuario. 
    * @DatabaseField(canBeNull = false, unique = true, dataType=DataType.STRING)
    * private String email;
    */
    
    public Usuario() {
    }
    
    public Usuario(String nomeCompleto, int matricula, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.matricula = matricula;
        this.senha = senha;
        this.saldo = 0.00;
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
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setNomeCompleto(String nome) {
        this.nomeCompleto = nome;
    }
    
    public String getNomeCompleto() {
        return this.nomeCompleto;
    }
    
    public void setMatricula(int matricula){
        this.matricula = matricula;
    }
   
    public int getMatricula() {
        return this.matricula;
    }
    
    public String getStringMatricula() {
        return ""+this.matricula;
    }
   
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getSenha() {
        return this.senha;
    }
    
    public double getSaldo() {
        return this.saldo;
    }
    
}
