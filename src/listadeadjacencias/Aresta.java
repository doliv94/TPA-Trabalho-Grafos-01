package listadeadjacencias;

public class Aresta {
    private Vertice destino;
    private float peso;

    public Aresta(){ }

    public Aresta(Vertice destino) {
        this.destino = destino;
    }

    public Aresta(Vertice destino, float peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
}
