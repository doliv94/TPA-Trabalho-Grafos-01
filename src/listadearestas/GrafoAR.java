package listadearestas;

import java.util.ArrayList;

public class GrafoAR {
    private ArrayList<ArestaAR> arestas;
    private ArrayList<VerticeAR> vertices;

    public VerticeAR adicionarVertice(T valor) {

        VerticeAR novo = new VerticeAR<>(valor);
        this.vertices.add(novo);

        return novo;
    }

    private VerticeAR obterVertice(T valor) {
        VerticeAR v;

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
        VerticeAR verticeOrigem, verticeDestino;
        ArestaAR novaAresta;

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

        // Agora crio a aresta que vai da origem ao destino
        novaAresta = new ArestaAR(verticeOrigem, verticeDestino, peso);

        // Adicionei a aresta a lista do Grafo
        this.arestas.add(novaAresta);
    }
}
