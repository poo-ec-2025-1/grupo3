import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "aparelho")
public abstract class Aparelho implements Reservavel {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String modelo;

    @DatabaseField
    private String descricao;

    @DatabaseField
    private boolean agendavel;

    @DatabaseField
    private boolean disponivel;

    public Aparelho() {
    }

    public Aparelho(String modelo, String descricao, boolean agendavel) {
        this.modelo = modelo;
        this.descricao = descricao;
        this.agendavel = agendavel;
        this.disponivel = true;
    }

    public int getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean getAgendavel() {
        return agendavel;
    }

    public boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public boolean reservar(){
    // Implementação simplificada, a ser ajustada com ReservaRepository
        if (getAgendavel() && getDisponivel()){
            setDisponivel(false);
            return true;
        }
        return false;
    }

    @Override
    public void liberar(){
        if(!getDisponivel()){
            setDisponivel(true);
        }
    }

    @Override
    public boolean estaReservado(){
        return !getDisponivel();
    }

    public abstract double getCusto();

    public abstract void setCusto(double custo);
}
