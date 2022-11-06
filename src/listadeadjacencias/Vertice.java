package listadeadjacencias;

import java.util.ArrayList;

public class Vertice<T> {
    // Valor do vertice, no caso dessa entrega serao
    // as informacoes de codigo e nome da cidade
    private T valor;

    // Lista de destinos, vai conter o vertice que representa
    // a cidade de destino e o peso da aresta (nesse caso, distancia ate a cidade)
    private ArrayList<Aresta<T>> destinos;

    // Pode criar um vertice vazio para colocar os valores depois
    public Vertice(){ }

    // Pode criar um vertice que tem um valor definido
    public Vertice(T valor){
        this.valor = valor;
    }

    // Pode criar um vertice completo com o valor e destinos ja definidos
    public Vertice(T valor, ArrayList<Aresta<T>> destinos){
        this.valor = valor;
        this.destinos = destinos;
    }

    // get e set para acesso ao valor do vertice
    public T getValor() {
        return valor;
    }
    public void setValor(T valor) {
        this.valor = valor;
    }

    // get e set para acesso a lista de destinos do vertice atual
    public ArrayList<Aresta<T>> getDestinos() {
        return destinos;
    }
    public void setDestinos(ArrayList<Aresta<T>> destinos) {
        this.destinos = destinos;
    }

    // Metodo para adicionar um novo destino a lista de destinos do vertice
    public void adicionarDestino(Aresta<T> aresta) {
        this.destinos.add(aresta);
    }
}
