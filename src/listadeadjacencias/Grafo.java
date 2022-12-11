package listadeadjacencias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class Grafo<T> {
    // O grafo eh formado por uma lista de vertices (cidades, nesse caso)
    private ArrayList<Vertice<T>> vertices = new ArrayList<>(); // Inicializa lista

    // Pode criar um grafo vazio e depois acrescentar os vertices dele
    public Grafo() { }

    // Pode criar um grafo com a lista de vertices ja definida
    public Grafo(ArrayList<Vertice<T>> vertices) {
        this.vertices = vertices;
    }

    // get e set para acesso a lista de vertices (cidades)
    public ArrayList<Vertice<T>> getVertices() {
        return vertices;
    }
    public void setVertices(ArrayList<Vertice<T>> vertices) {
        this.vertices = vertices;
    }

    // Metodo para adicionar um novo vertice na lista de vertices do grafo
    public Vertice<T> adicionarVertice(T valor) {
        Vertice<T> novo = new Vertice<>(valor);
        this.vertices.add(novo);

        return novo;
    }

    public Vertice<T> adicionarVerticeComPeso(Vertice<T> vertice) {
        this.vertices.add(vertice);

        return vertice;
    }

    // Metodo para procurar/retornar um vertice especifico do grafo
    private Vertice<T> obterVertice(T valor) {
        Vertice<T> v;

        for (Vertice<T> vertex : this.vertices) {
            v = vertex;

            if (v.getValor().equals(valor)) {
                return v;
            }
        }

        // Se chegou aqui eh pq nao existe um vertice com esse valor
        return null;
    }

    // Metodo para checar se existe com o valor entrado
    // Esse metodo foi criado por ser feita tal checagem nos dois metodos de adicionarAresta
    private boolean checaVerticeExiste(T valor) {
        // Busco o vertice pelo valor de entrada
        Vertice<T> vertice = obterVertice(valor);

        if (vertice == null) { // Se nao for encontrado, retorna falso
            return false;
        }

        return true;
    }

    // Metodo para adicionar uma aresta com peso a um dos vertices do grafo
    public void adicionarArestaComPeso(T origem, T destino, float peso) {
        Vertice<T> verticeOrigem = new Vertice<>(), verticeDestino = new Vertice<>();

        // Se ainda nao existe vertice com o valor da origem, vou criar o vertice
        if(!checaVerticeExiste(origem)) {
            verticeOrigem = adicionarVertice(origem);
        }

        // Se ainda nao existe vertice com o valor do destino, vou criar o vertice
        if(!checaVerticeExiste(destino)) {
            verticeDestino = adicionarVertice(destino);
        }

        // Vou adicionar a aresta com peso a lista de adjacencia do vertice de origem
        verticeOrigem.adicionarDestino(new Aresta<>(verticeDestino, peso));
    }

    // Metodo para adicionar uma aresta sem peso a um dos vertices do grafo
    public void adicionarArestaSemPeso(T origem, T destino) {
        Vertice<T> verticeOrigem = new Vertice<>(), verticeDestino = new Vertice<>();

        // Se ainda nao existe vertice com o valor da origem, vou criar o vertice
        if(!checaVerticeExiste(origem)) {
            verticeOrigem = adicionarVertice(origem);
        }

        // Se ainda nao existe vertice com o valor do destino, vou criar o vertice
        if(!checaVerticeExiste(destino)) {
            verticeDestino = adicionarVertice(destino);
        }

        // Vou adicionar a aresta sem peso a lista de adjacencia do vertice de origem
        verticeOrigem.adicionarDestino(new Aresta<>(verticeDestino));
    }

    // Metodo para calcular o caminho minimo entre dois vertices
    // (ta feio, professor, desculpa)
    // De forma resumida, segue mais ou menos conforme o slide disponibilizado no ava, tem as listas de distancias e a de predecessores,
    // alem de salvar o ultimo no que foi rotulado (aquele que levar a uma distancia menor)
    // Faz a interacao com o vertice de origem definindo as distancias que existirem com o valor,
    // adicionando a origem como predecessor nesses casos, e os outros vertices ficam com distancia infinita e predecessor nulo
    // Tambem vai montar uma fila com os vertices que foram alcancados, sempre colocando na primeira posicao o que tiver menor valor de distancia
    // E guarda numa lista os vertices que fizerem parte do caminho minimo (tanto essa lista quanto a
    // da fila tiveram algo de inpiracao no codigo de busca em largura da entrega 1)
    // As demais iteracoes depois da origem irao acontecer ate que o vertice que leva a um menor caminho seja o proprio vertice de destino
    public ArrayList<Vertice<T>> calculaCaminhoMinimo(Vertice<T> origem, Vertice<T> destino) {
        int quantidadeVertices = this.vertices.size(); // Pega a quantidade de vertices do grafo
        float distancias[] = new float[quantidadeVertices]; // Cria uma variavel para guardar a distancia minima de cada vertice segundo a posicao dele no grafo

        // Cria uma lista para guardar os vertices predecessores de cada ultimoNoRotulado, seguindo a explicacao do slide disponibilizado no ava
        ArrayList<Vertice<T>> predecessores = new ArrayList<>();
        Vertice<T> ultimoNoRotulado = origem; // Define o ultimoNoRotulado como o vertice de origem

        // Cria uma lista para colocar os vertices que sao destino do vertice atual, e ainda nao foram visitados
        ArrayList<Vertice<T>> fila = new ArrayList<>();
        // Cria uma lista para colocar os vertices que fazem parte do caminho minimo
        ArrayList<Vertice<T>> marcados = new ArrayList<>();

        Vertice<T> verticeP = new Vertice<>(); // Cria um vertice vazio

        float distanciaAtual = Float.POSITIVE_INFINITY; // Define uma variavel distanciaAtual como infinita
        float distanciaPred; // Variavel que vai guardar a distancia anterior entre dois vertices

        // Realiza a primeira iteracao para o vertice de origem
        for (int posicao = 0; posicao < quantidadeVertices; posicao++) {

            // ...Se tem caminho a partir do vertice de origem
            if (origem.getDestinos().get(posicao).getPeso() > 0) {
                distanciaPred = distanciaAtual; // Guarda a ultima distancia a partir do vertice de origem
                distanciaAtual = origem.getDestinos().get(posicao).getPeso(); // Define a distancia atual do vertice de origem para o da posicao atual

                // Guarda a distancia atual ate o vertice na lista das distancias, segundo a posicao dele
                distancias[posicao] = distanciaAtual;
                predecessores.add(posicao, origem); // Adiciona o vertice de origem como atual predecessor do vertice que ele tem caminho

                // Se a distancia entre a origem e o vertice atual for menor do que a distancia da origem pro vertice anterior
                if (distanciaAtual < distanciaPred) {
                    fila.add(0, origem.getDestinos().get(posicao).getDestino()); // Eh menor, coloca ele no comeco da fila
                }
                else if (distanciaAtual == distanciaPred && origem.getDestinos().get(posicao).getDestino() == destino) { // Mas se a opcao eh o vertice destino, ele tem prioridade
                    fila.add(0, origem.getDestinos().get(posicao).getDestino());
                }
                else {
                    fila.add(1, origem.getDestinos().get(posicao).getDestino()); // Nao eh menor, coloca ele no final da fila
                    distanciaAtual = distanciaPred; // Define a distancia atual como a distancia anterior, que eh menor
                }
            }
            // Se nao tem caminho da origem pro vertice atual
            else {
                distancias[posicao] = Float.POSITIVE_INFINITY; // A distancia eh definida como infinita
                predecessores.add(posicao, verticeP); // Define o predecessor do vertice sem caminho como o vertice nulo
            }
        }
        distancias[this.vertices.indexOf(origem)] = 0; // Coloca a distancia do no de origem para ele mesmo como 0

        ultimoNoRotulado = fila.get(0); // O ultimo no rotulado eh o primeiro da fila, que teve o menor caminho
        marcados.add(origem); // Adiciona o vertice de origem na lista dos marcados
        marcados.add(ultimoNoRotulado); // E adiciona o ultimo no rotulado na lista dos marcados tambem, porque agora estamos nele

        // Proximas iteracoes, agora que a base foi feita pelo vertice de origem, seguimos para os outros
        // ...Enquanto o ultimo no rotulado nao for o no de destino
        while (ultimoNoRotulado != destino) {
            // Sempre que partirmos de um vertice novo para comparacao, a distancia atual eh definida como infinita
            distanciaAtual = Float.POSITIVE_INFINITY;

            // Vai passar por todos os destinos do vertice marcado atualmente
            for (int posicao = 0; posicao < quantidadeVertices; posicao++) {

                // Se houver caminho e o vertice nao tiver sido marcado ainda
                if (ultimoNoRotulado.getDestinos().get(posicao).getPeso() > 0 && !marcados.contains(ultimoNoRotulado.getDestinos().get(posicao).getDestino())) {

                    distanciaPred = distanciaAtual; // Como visto la em cima, distanciaPred garda a distancia anterior

                    // Distancia atual soma a distancia que ja foi percorrida pelo ultimoNoRotulado com a distancia dele para o vertice atual
                    distanciaAtual = distancias[this.vertices.indexOf(ultimoNoRotulado)] + ultimoNoRotulado.getDestinos().get(posicao).getPeso();

                    float distAntiga = distancias[posicao]; // Pega o valor da distancia que ja tinha sido guardado para o vertice da posicao atual

                    // ...Se a distancia atual para o vertice der um valor menor do que o valor que ele ja tinha guardado
                    if (distanciaAtual < distAntiga) {
                        distancias[posicao] = distanciaAtual; // Substitui a distancia dele pelo menor valor de agora
                        predecessores.set(posicao, ultimoNoRotulado); // Atualiza o predecessor dele
                    }

                    // ...Se a distancia da iteracao atual for menor que a da anterior
                    if (distanciaAtual < distanciaPred) {
                        fila.add(0, ultimoNoRotulado.getDestinos().get(posicao).getDestino()); // Poe o vertice atual no inicio da fila
                    }
                    else if (distanciaAtual == distanciaPred && ultimoNoRotulado.getDestinos().get(posicao).getDestino() == destino) { // Mas se a opcao eh o vertice destino, ele tem prioridade
                        fila.add(0, ultimoNoRotulado.getDestinos().get(posicao).getDestino());
                    }
                    else {
                        fila.add(1, ultimoNoRotulado.getDestinos().get(posicao).getDestino()); // Se nao, poe o vertice depois na fila
                        distanciaAtual = distanciaPred; // E modifica a distancia atual para o valor da anterior que era menor
                    }
                }
            }
            // Quando acaba de passar pelos destinos possiveis do vertice, remove o ultimoNoRotulado da fila
            fila.remove(ultimoNoRotulado);
            // E marca como ultimo no rotulado o primeiro da fila atualizada
            ultimoNoRotulado = fila.get(0);

            // E se ele nao estiver na lista de vertices que ja foram marcados, adiciona
            if (!marcados.contains(ultimoNoRotulado)) {
                marcados.add(ultimoNoRotulado);
            }
        }
        predecessores.set(0, origem); // No final de tudo, coloca o vertice de origem como predecessor dele mesmo

        // Retorna a lista com todos os vertices que resultaram no menor caminho da origem ate o destino
        return marcados;
    }

    // Metodo que checa se a aresta entre dois vertices forma um ciclo na arvore
    private boolean checaCiclo(Grafo<T> grafoAGM, T origem, T destino){

        ArrayList<Vertice<T>> fila = new ArrayList<>(); // Usaremos uma lista para ter controle de quais vertices estamos verificando
        Vertice<T> checa = grafoAGM.obterVertice(origem); // O vertice sendo checado, comeca com o valor de origem da entrada
        fila.add(checa); // Adicionamos ele na fila

        // Para cada vertice na fila, verificaremos se um dos destinos que ele possui leva ao valor destino da entrada
        for (int i = 0; i < fila.size(); i++) {
            checa = grafoAGM.obterVertice(fila.get(i).getValor()); // A variavel vai pegar cada vertice da fila

            // Verifica os destinos
            for (int j = 0; j < checa.getDestinos().size(); j++) {

                // Se o destino do vertice sendo conferido tiver o mesmo valor que o destino da entrada
                if (checa.getDestinos().get(j).getDestino().getValor() == destino) {
                    return true; // Ciclo confirmado, sai do metodo e nao adiciona a aresta atual no grafoAGM
                }
                // E se nao for igual
                else {
                    // Adiciona esse destino na fila para checagem, caso ele ja nao esteja la
                    if (!fila.contains(checa.getDestinos().get(j).getDestino())) {
                        fila.add(checa.getDestinos().get(j).getDestino());
                    }
                }
            }
        }

        // Se tivermos passado por todos os vertices da fila e nenhum dos destinos deles tiveram o mesmo valor que o destino da
        // entrada, retornamos falso, ou seja, nao ha ciclo e pode adicionar essa aresta no grafoAGM
        return false;
    }

    // Implementado o Algoritmo de Kruskal
    // Metodo para construcao da arvore geradora minima
    // (eh tanta repeticao de codigo que nao cabe no cartaz, mas o medo de estragar tudo ajeitando e a falta de tempo impedem de corrigir isso no momento)
    public ArrayList calculaArvoreGeradoraMinima (Grafo<T> grafo) {
        ArrayList retorno = new ArrayList<>(); // No final vai armazenar a arvore geradora minima e a soma dos pesos dela
        Grafo<T> grafoAGM = new Grafo<>(); // Vai guardar a arvore geradora minima
        float somaTotalPesos = 0.0f; // Vai guardar a soma dos pesos das arestas

        ArrayList<Aresta<T>> destinosOrdenados = new ArrayList<>(); // Cria uma lista que ira armazenar todas as arestas com peso maior que 0 do grafo
        // Para simplificar a identificacao dos vertices de origem de cada aresta dessa lista,foi criada a variavel Vertice<T> origem na classe Aresta
        // com os metodos de get e set para acessa-la

        ArrayList<Aresta<T>> novaListaDestino = new ArrayList<>(); // Cria uma lista que vai guardar os novos destinos de cada vertice
        Aresta<T> novoDestino = new Aresta<>(); // Para adicionar um novo destino em um vertice existente na arvore sera usada essa variavel

        // Aqui passaremos por cada destino do grafo, adicionando todas as arestas que tiverem peso maior que 0 na lista destinosOrdenados
        for (int i = 0; i < grafo.getVertices().size(); i++) { // Pega um vertice
            for (int j = 0; j < grafo.getVertices().get(i).getDestinos().size(); j++) { // Pega um destino desse vertice
                Aresta<T> nova = new Aresta<>(); // O caminho entre os vertices

                // Se o caminho for maior que 0
                if (grafo.getVertices().get(i).getDestinos().get(j).getPeso() > 0) {
                    nova.setOrigem(grafo.getVertices().get(i)); // Define a origem desse caminho
                    nova.setDestino(grafo.getVertices().get(i).getDestinos().get(j).getDestino()); // Define o destino desse caminho
                    nova.setPeso(grafo.getVertices().get(i).getDestinos().get(j).getPeso()); // Define o peso do caminho

                    destinosOrdenados.add(nova); // Adiciona essa aresta do caminho na lista

                    // Eh utilizada uma nova aresta para que seja possivel separar as informacoes de cada aresta sem mexermos na original
                }
            }
        }

        // Caso essa lista com todas as arestas seja maior que 0
        if (destinosOrdenados.size() > 0) {
            destinosOrdenados.sort(Comparator.comparing(Aresta::getPeso)); // Faz jus ao nome e ordena todos os destinos que foram adicionados nela pelo peso

            // Adiciona primeira aresta, que eh a menor e, portanto, primeira na lista ordenada
            // Para adicionar a aresta com peso no grafo da arvore geradora minima, passamos em ordem: origem, destino, peso
            grafoAGM.adicionarArestaComPeso(destinosOrdenados.get(0).getOrigem().getValor(), destinosOrdenados.get(0).getDestino().getValor(), destinosOrdenados.get(0).getPeso());

            // Como os grafos utilizados ate agora nesse trabalho foram nao direcionado, adicionamos tambem a aresta que seria no sentido destino -> origem, de mesmo peso
            novoDestino.setDestino(destinosOrdenados.get(0).getOrigem()); // O destino do destino fica sendo a origem
            novoDestino.setPeso(destinosOrdenados.get(0).getPeso()); // Pegamos o peso da aresta
            novaListaDestino.add(novoDestino); // Adicionamos essa aresta numa novaListaDestino vazia (sao os primeiros vertices do grafoAGM)

            // Quando adicionamos a aresta com peso, criamos os dois vertices dentro do grafoAGM, mas o destino ficou sem uma aresta naquele momento
            // Com o obterVertice encontramos o vertice de destino no grafoAGM e definimos o destino dele como a novaListaDestino que criamos ali em cima
            grafoAGM.obterVertice(destinosOrdenados.get(0).getDestino().getValor()).setDestinos(novaListaDestino);

            // E se adicionamos uma aresta, somamos o peso dela na variavel
            somaTotalPesos += destinosOrdenados.get(0).getPeso();

            // Agora passamos por cada aresta naquela lista que foi ordenada, e por estar ordenada nao precisamos nos preocupar
            // em comparar valor antigo com atual pra saber qual eh o menor antes de adicionar
            // Se o vertice de origem ou destino da aresta nao existir no grafoAGM, ele sera adicionado, assim como o caminho para ele
            // Se os dois vertices existem, vai checar se adicionar a aresta deles vai formar um ciclo com as outras que foram adicionadas anteriormente, se
            // nao formar ciclo, adiciona esse caminho, se formar ciclo, ignora e segue para a proxima aresta
            for (int i = 1; i < destinosOrdenados.size(); i++) {

                // As checagens a seguir de se o vertice existe sao feitas pelos valores de cada vertice, pq os vertices do grafo de entra possuem
                // mais informacoes na lista de destinos, enquanto os vertices do grafoAGM tem as aresta de destino simplificadas, ou seja, nao sao iguais
                // para comparar pelo vertice inteiro, entao fazemos apenas pelo valor do vertice

                // Se no grafoAGM nao tem o vertice de origem da aresta atual, mas tem o vertice de destino
                if (!grafoAGM.checaVerticeExiste(destinosOrdenados.get(i).getOrigem().getValor()) && grafoAGM.checaVerticeExiste(destinosOrdenados.get(i).getDestino().getValor())) {
                    // Vai adicionar esse caminho para a origem na lista de destinos do vertice de destino que ja existe no grafoAGM

                    novoDestino = new Aresta<>(); // Caminho novo

                    novoDestino.setDestino(destinosOrdenados.get(i).getOrigem()); // Destino -> Origem
                    novoDestino.setPeso(destinosOrdenados.get(i).getPeso()); // Define o peso do caminho

                    // Encontramos o vertice no grafo e adicionamos esse novo destino na lista de destinos dele
                    grafoAGM.obterVertice(destinosOrdenados.get(i).getDestino().getValor()).adicionarDestino(novoDestino);

                    // Adiciona o vertice de origem no grafoAGM, mas sem a aresta de destino dele daqui
                    grafoAGM.adicionarVertice(destinosOrdenados.get(i).getOrigem().getValor());

                    // Como feito em cima, adiciona o caminho para o destino na lista de destinos do vertice de origem que agora ja existe no grafoAGM
                    novoDestino = new Aresta<>(); // Caminho novo

                    novoDestino.setDestino(destinosOrdenados.get(i).getDestino()); // Origem -> Destino
                    novoDestino.setPeso(destinosOrdenados.get(i).getPeso()); // Define o peso do caminho

                    // Encontramos o vertice no grafo e adicionamos esse novo destino na lista de destinos dele
                    grafoAGM.obterVertice(destinosOrdenados.get(i).getOrigem().getValor()).adicionarDestino(novoDestino);

                    // Somamos o peso da aresta na variavel
                    somaTotalPesos += destinosOrdenados.get(i).getPeso();
                }
                // Se no grafoAGM tem o vertice de origem da aresta atual, mas nao tem o vertice de destino
                else if (grafoAGM.checaVerticeExiste(destinosOrdenados.get(i).getOrigem().getValor()) && !grafoAGM.checaVerticeExiste(destinosOrdenados.get(i).getDestino().getValor())) {
                    // O contrario da anterior, comeca adicionando o caminho para o destino na lista de destinos do vertice de
                    // origem que ja existe no grafoAGM

                    novoDestino = new Aresta<>(); // Caminho novo

                    novoDestino.setDestino(destinosOrdenados.get(i).getDestino()); // Origem -> Destino
                    novoDestino.setPeso(destinosOrdenados.get(i).getPeso()); // Define o peso do caminho

                    // Encontramos o vertice no grafo e adicionamos esse novo destino na lista de destinos dele
                    grafoAGM.obterVertice(destinosOrdenados.get(i).getOrigem().getValor()).adicionarDestino(novoDestino);

                    // Adiciona o vertice de destino no grafoAGM, mas sem a aresta de destino dele daqui
                    grafoAGM.adicionarVertice(destinosOrdenados.get(i).getDestino().getValor());

                    // ...E vamos adicionar o destino dele
                    novoDestino = new Aresta<>(); // Caminho novo

                    novoDestino.setDestino(destinosOrdenados.get(i).getOrigem()); // Destino -> Origem
                    novoDestino.setPeso(destinosOrdenados.get(i).getPeso()); // Define o peso do caminho

                    // Encontramos o vertice no grafo e adicionamos esse novo destino na lista de destinos dele
                    grafoAGM.obterVertice(destinosOrdenados.get(i).getDestino().getValor()).adicionarDestino(novoDestino);

                    // Somamos o peso da aresta na variavel
                    somaTotalPesos += destinosOrdenados.get(i).getPeso();
                }
                // Se no grafoAGM nao tem o vertice de origem da aresta atual e nem o vertice de destino
                else if (!grafoAGM.checaVerticeExiste(destinosOrdenados.get(i).getOrigem().getValor()) && !grafoAGM.checaVerticeExiste(destinosOrdenados.get(i).getDestino().getValor())) {
                    // Para adicionar a aresta nova com peso, passamos em ordem: origem, destino, peso
                    grafoAGM.adicionarArestaComPeso(destinosOrdenados.get(i).getOrigem().getValor(), destinosOrdenados.get(i).getDestino().getValor(), destinosOrdenados.get(i).getPeso());

                    novaListaDestino = new ArrayList<>(); // Lista de destinos nova
                    novoDestino = new Aresta<>(); // Caminho novo

                    novoDestino.setDestino(destinosOrdenados.get(i).getOrigem()); // Destino -> Origem
                    novoDestino.setPeso(destinosOrdenados.get(i).getPeso()); // Define o peso do caminho
                    novaListaDestino.add(novoDestino); // Adicionamos essa aresta numa novaListaDestino vazia

                    // Encontramos o vertice no grafo e adicionamos esse novo destino na lista de destinos dele
                    grafoAGM.obterVertice(destinosOrdenados.get(i).getDestino().getValor()).setDestinos(novaListaDestino);

                    // Somamos o peso da aresta na variavel
                    somaTotalPesos += destinosOrdenados.get(i).getPeso();
                }
                // Se no grafoAGM tem o vertice de origem da aresta atual e tambem o vertice de destino
                else {
                    // Chama o metodo para checar se a aresta atual forma ciclo, passando o grafoAGM, e os vertices dele que possuem
                    // esses mesmos valores da lista ordenada na origem e no destino
                    // Se retornar falso, eh porque nao forma ciclo e o caminho eh adicionado
                    if (!checaCiclo(grafoAGM, grafoAGM.obterVertice(destinosOrdenados.get(i).getOrigem().getValor()).getValor(), grafoAGM.obterVertice(destinosOrdenados.get(i).getDestino().getValor()).getValor())) {
                        novoDestino = new Aresta<>(); // Caminho novo

                        novoDestino.setDestino(destinosOrdenados.get(i).getDestino()); // Origem -> Destino
                        novoDestino.setPeso(destinosOrdenados.get(i).getPeso()); // Define o peso do caminho

                        // Encontramos o vertice no grafo e adicionamos esse novo destino na lista de destinos dele
                        grafoAGM.obterVertice(destinosOrdenados.get(i).getOrigem().getValor()).adicionarDestino(novoDestino);

                        novoDestino = new Aresta<>(); // Caminho novo

                        novoDestino.setDestino(destinosOrdenados.get(i).getOrigem()); // Destino -> Origem
                        novoDestino.setPeso(destinosOrdenados.get(i).getPeso()); // Define o peso do caminho

                        // Encontramos o vertice no grafo e adicionamos esse novo destino na lista de destinos dele
                        grafoAGM.obterVertice(destinosOrdenados.get(i).getDestino().getValor()).adicionarDestino(novoDestino);

                        // Somamos o peso da aresta na variavel
                        somaTotalPesos += destinosOrdenados.get(i).getPeso();
                    }
                }
            }
        }

        // Ao final, o grafoAGM esta montado e passamos tanto ele quando a soma das suas arestas para a variavel de retorno
        retorno.add(grafoAGM);
        retorno.add(somaTotalPesos);

        return retorno;
    }

    public float calculaFluxosCaminhos (float fluxoMaximo, ArrayList<Vertice<T>> verticesCaminho, ArrayList<Aresta<T>> caminho, Vertice<T> origem, Vertice<T> destino) {

        if (!origem.getDestinos().stream().filter(v -> v.getPeso() > 0).anyMatch(a -> a.getDestino().equals(destino))) {

            for (int i = 0; i < origem.getDestinos().size(); i++) {
                Vertice<T> finalOrigem = origem;
                int finalI = i;
                if (origem.getDestinos().get(i).getPeso() > 0 && !verticesCaminho.contains(origem.getDestinos().get(i).getDestino())) {
                    verticesCaminho.add(origem.getDestinos().get(i).getDestino());

                    Aresta<T> novaAresta = origem.getDestinos().get(i);
                    caminho.add(novaAresta);

                    origem = origem.getDestinos().get(i).getDestino();


                    System.out.println(Arrays.deepToString((Object[]) novaAresta.getDestino().getValor()));
                    return calculaFluxosCaminhos(fluxoMaximo, verticesCaminho, caminho, origem, destino);
                }
            }

            caminho = new ArrayList<>();
        } else {
            Aresta<T> novaAresta = origem.getDestinos().stream().filter(d -> d.getDestino().equals(destino)).findAny().orElse(null);
            System.out.println(Arrays.deepToString((Object[]) novaAresta.getDestino().getValor()));

            caminho.add(novaAresta);

            ArrayList<Aresta<T>> caminhoReverso = new ArrayList<>();
            for (int i = 0; i < caminho.size(); i++) {
                novaAresta = new Aresta<>();
                ArrayList<Aresta<T>> finalCaminho = caminho;
                int finalI = i;
                novaAresta = caminho.get(i).getDestino().getDestinos().stream().filter(d -> d.getDestino().equals(finalCaminho.get(finalI).getOrigem())).findAny().get();
                caminhoReverso.add(novaAresta);
            }

            float menorPeso = caminho.stream().min(Comparator.comparing(Aresta<T>::getPeso)).get().getPeso();
            fluxoMaximo += menorPeso;

            System.out.println(menorPeso);
            for (int i = 0; i < caminho.size(); i++) {
                caminho.get(i).setPeso(caminho.get(i).getPeso() - menorPeso);
                caminhoReverso.get(i).setPeso(caminhoReverso.get(i).getPeso() + menorPeso);

                System.out.println("-> " + caminho.get(i).getPeso() + ", <- " + caminhoReverso.get(i).getPeso());
            }
        }



        return fluxoMaximo;
    }
    // Metodo para determinar o fluxo maximo entre dois vertices
    public void calculaFluxoMaximo (Vertice<T> o, Vertice<T> d) {
        ArrayList<Aresta<T>> caminho = new ArrayList<>();
        ArrayList<Vertice<T>> verticesCaminho = new ArrayList<>();
        Vertice<T> origem = o;
        Vertice<T> destino = d;
        float fluxoMaximo = 0.0f;

        verticesCaminho.add(origem);

        fluxoMaximo = calculaFluxosCaminhos(fluxoMaximo, verticesCaminho, caminho, origem, destino);

        System.out.println(fluxoMaximo);

    }
}
