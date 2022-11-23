package listadeadjacencias;

public class Aresta<T> {
    // Vertice (cidade) de destino
    private Vertice<T> destino = new Vertice<>(); // Inicializa vertice

    // Distancia entre a cidade de origem e a de destino
    private float peso;

    // Pode criar uma aresta vazia e depois adicionar o vertice e o peso
    public Aresta(){ }

    // Pode criar uma aresta com um destino definido sem peso
    public Aresta(Vertice<T> destino) {
        this.destino = destino;
    }

    // Pode criar uma aresta com destino e peso definidos
    public Aresta(Vertice<T> destino, float peso) {
        this.destino = destino;
        this.peso = peso;
    }

    // get e set para acesso ao vertice de destino
    public Vertice<T> getDestino() {
        return destino;
    }
    public void setDestino(Vertice<T> destino) {
        this.destino = destino;
    }

    // get e set para acesso ao peso da aresta
    public float getPeso() {
        return peso;
    }
    public void setPeso(float peso) {
        this.peso = peso;
    }
}
