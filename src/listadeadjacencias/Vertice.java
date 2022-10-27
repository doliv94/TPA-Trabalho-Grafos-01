package listadeadjacencias;

import java.util.ArrayList;

public class Vertice<T> {
    private T valor;

    // Estrategia de Armazenamento - Lista de Adjacencias
    private ArrayList<Aresta> destinos;

    public Vertice(){ }

    public Vertice(T valor){
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public ArrayList<Aresta> getDestinos() {
        return destinos;
    }

    public void setDestinos(ArrayList<Aresta> destinos) {
        this.destinos = destinos;
    }

    public void adicionarDestino(Aresta aresta) {
        this.destinos.add(aresta);
    }
}
