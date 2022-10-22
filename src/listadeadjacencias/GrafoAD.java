package listadeadjacencias;

import java.util.ArrayList;

public class GrafoAD<T> {
    private ArrayList<VerticeAD<T>> vertices;

    public VerticeAD<T> adicionarVertice(T valor) {
        VerticeAD<T> novo = new VerticeAD<T>(valor);
        this.vertices.add(novo);

        return novo;
    }

    private VerticeAD obterVertice(T valor) {
        VerticeAD v;

        for(int i = 0; i < this.vertices.size(); i++) {
            v = this.vertices.get(i);

            if(v.getValor().equals(valor)) {
                return v;
            }
        }

        // Se chegou aqui eh pq nao existe um vertice com esse valor
        return null;
    }

    public void adicionarAresta(T origem, T destino, float peso) {
        VerticeAD verticeOrigem, verticeDestino;

        // Busco o vertice com o valor de origem
        verticeOrigem = obterVertice(origem);

        // Se ainda nao existe vertice com o valor da origem, vou criar o vertice
        if(verticeOrigem == null) {
            verticeOrigem = adicionarVertice(origem);
        }

        // Busco o vertice com o valor de destino
        verticeDestino = obterVertice(destino);

        // Se ainda nao existe vertice com o valor do destino, vou criar o vertice
        if(verticeDestino == null) {
            verticeDestino = adicionarVertice(destino);
        }

        // Vou adicionar a aresta a lista de adjacencia do vertice de origem
        verticeOrigem.adicionarDestino(new ArestaAD(verticeDestino, peso));
    }
}
