package lavanderia.Model;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

public class Aparelho {
    @DatabaseField(generatedId = true, dataType=DataType.INTEGER)
    private int id;
    
    @DatabaseField(dataType=DataType.STRING)
    private String modelo;
    
    @DatabaseField(dataType=DataType.INTEGER)
    private int capacidadeKg;
    
    @DatabaseField(dataType=DataType.STRING)
    private String descricao;
    
    @DatabaseField(dataType=DataType.BOOLEAN)
    private boolean disponivel;
    
    @DatabaseField(dataType=DataType.DOUBLE)
    private double custo;
    
    Aparelho() {
    }
    
    Aparelho(String modelo, int capacidadeKg, String descricao, boolean disponivel, double custo) {
        this.modelo = modelo;
        this.capacidadeKg = capacidadeKg;
        this.descricao = descricao;
        this.disponivel = true;
        this.custo = custo;
    }
    
    // os dois proximos metodos vão servir pro admin alterar a disponibilidade da maquina, caso ela entre em manutenção ou algo do tipo
    public void disponibilizarMaquina() {
        this.setDisponivel(true);
    }
    
    public void indisponibilizarMaquina() {
        this.setDisponivel(false);
    }
 
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;   
    }
    
    public String getModelo() {
        return this.modelo;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;   
    }
    
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setCapacidadeKg(int capacidadeKg) {
        this.capacidadeKg = capacidadeKg;
    }
    
    public int getCapacidadeKg() {
        return capacidadeKg;
    }
    
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;   
    }
    
    public boolean getDisponivel() {
        return this.disponivel;
    }
    
    public void setCusto(double custo) {
        this.custo = custo;
    }
    
    public double getCusto() {
        return this.custo;
    }
    
    @Override
    public String toString() {
        return ""+this.modelo;
    }
    
}
