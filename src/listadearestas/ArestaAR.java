package listadearestas;

public class ArestaAR {

    private VerticeAR origem, destino;
    private float peso;

    public ArestaAR(VerticeAR origem, VerticeAR destino, float peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public VerticeAR getOrigem() {
        return origem;
    }

    public void setOrigem(VerticeAR origem) {
        this.origem = origem;
    }

    public VerticeAR getDestino() {
        return destino;
    }

    public void setDestino(VerticeAR destino) {
        this.destino = destino;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
}
