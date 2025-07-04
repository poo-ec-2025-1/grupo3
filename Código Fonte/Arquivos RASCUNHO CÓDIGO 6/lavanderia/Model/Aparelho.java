package lavanderia.Model;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

public class Aparelho {
    @DatabaseField(generatedId = true, dataType=DataType.INTEGER)
    private int id;
    
    @DatabaseField(dataType=DataType.STRING)
    private String modelo;
    
    @DatabaseField(dataType=DataType.STRING)
    private String descricao;
    
    @DatabaseField(dataType=DataType.INTEGER)
    private int capacidadeKg;
    
    @DatabaseField(dataType=DataType.BOOLEAN)
    private boolean agendavel;
    
    @DatabaseField(dataType=DataType.BOOLEAN)
    private boolean disponivel;
    
    @DatabaseField(dataType=DataType.BOOLEAN)
    private boolean reservado;
    
    @DatabaseField(dataType=DataType.DOUBLE)
    private double custo;
    
    Aparelho() {
    }
    
    Aparelho(String modelo, String descricao, int capacidadeKg, boolean agendavel, double custo) {
        this.modelo = modelo;
        this.descricao = descricao;
        this.capacidadeKg = capacidadeKg;
        this.agendavel = agendavel;
        this.disponivel = false;
        this.custo = 0.0;
    }
    
    public boolean reservar() {
        if (getAgendavel() && getDisponivel() && !getReservado()) {
            setReservado(true);
            setDisponivel(false);
            String mensagemDeAviso = ("Aparelho " + getModelo() + " (ID: " + getId() + ") reservada com sucesso.");
            return true;
        } else {
            String mensagemDeAviso = ("Aparelho " + getModelo() + " (ID: " + getId() + ") não pode ser reservada. Estado atual: Agendável=" + getAgendavel() + ", Disponível=" + getDisponivel() + ", Reservado=" + getReservado());
            return false;
        }
    }

    public void liberar() {
        if (getReservado() == true) {
            setReservado(false);
            setDisponivel(true);
            String mensagemDeAviso = ("Aparelho " + getModelo() + " (ID: " + getId() + ") liberada.");
        } else {
            String mensagemDeAviso = ("Aparelho " + getModelo() + " (ID: " + getId() + ") não estava reservada.");
        }
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
    
    /**SET Method Propertie modelo*/
    public void setModelo(String modelo) {
        this.modelo = modelo;   
    }
    
    /**GET Method Propertie modelo*/
    public String getModelo() {
        return this.modelo;
    }
    
    /**SET Method Propertie descricao*/
    public void setDescricao(String descricao) {
        this.descricao = descricao;   
    }
    
    /**GET Method Propertie descricao*/
    public String getDescricao() {
        return this.descricao;
    }
    
    /**SET Method Propertie capacidadeKg*/
    public void setCapacidadeKg(int capacidadeKg) {
        this.capacidadeKg = capacidadeKg;
    }
    
    /**GET Method Propertie capacidadeKg*/
    public int getCapacidadeKg() {
        return capacidadeKg;
    }
    
    /**SET Method Propertie agendavel*/
    public void setAgendavel(boolean agendavel) {
        this.agendavel = agendavel;   
    }
    
    /**GET Method Propertie agendavel*/
    public boolean getAgendavel() {
        return this.agendavel;
    }
    
    /**SET Method Propertie disponivel*/
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;   
    }
    
    /**GET Method Propertie disponivel*/
    public boolean getDisponivel() {
        return this.disponivel;
    }
    
    /**SET Method Propertie reservado*/
    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }
    
    /**GET Method Propertie reservado*/
    public boolean getReservado() {
        return this.reservado;
    }
    
    /**SET Method Propertie custo*/
    public void setCusto(double custo) {
        this.custo = custo;
    }
    
    /**GET Method Propertie custo*/
    public double getCusto() {
        return this.custo;
    }
    
    @Override
    public String toString() {
        return ""+this.modelo;
    }
    
//End GetterSetterExtension Source Code
}
