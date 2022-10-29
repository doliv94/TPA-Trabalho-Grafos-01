// TPA - 1a Etapa do Trabalho Prático de Grafos - prof. Victorio Carvalho
// Ifes Serra 2022/2
// Dupla: Brunna Dalzini e Rafaela Amorim Pessim

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import geradorarquivos.*;
import listadeadjacencias.*;


public class Main {

    // Metodo de lista de adjacencias
    // chamar na opcao 2 do menu - falta revisar e consertar
    public void buscaEmLargura(Vertice<String[]> cidadeOrigem) {
        ArrayList<Vertice<String[]>> marcados = new ArrayList<>();
        ArrayList<Vertice<String[]>> fila = new ArrayList<>();

        // Pego o primeiro vertice como ponto de partida e o coloco na fila
        // Poderia escolher qualquer outro...
        // Mas note que dependendo do grafo pode ser que vc nao caminhe por todos os vertices
        Vertice<String[]> atual = cidadeOrigem;
        fila.add(atual);

        // Enquanto houver vertice na fila...
        while (fila.size() > 0) {
            // Pego o proximo da fila, marco como visitado e o imprimo
            atual = fila.get(0);
            fila.remove(0);
            marcados.add(atual);

            System.out.println(Arrays.toString(atual.getValor()));

            // Depois pego a lista de adjacencia do no e se o no adjacente ainda
            // nao tiver sido visitado, o coloco na fila
            ArrayList<Aresta<String[]>> destinos = atual.getDestinos();
            Vertice<String[]> proximo;

            for (Aresta<String[]> destino : destinos) {
                proximo = destino.getDestino();

                if (!marcados.contains(proximo)) {
                    fila.add(proximo);
                }
            }
        }
    }

    public static void obterCaminhos(Grafo<String[]> grafo, int entrada) {
        Scanner voltar = new Scanner(System.in);

        System.out.println("obterCaminhos");

        System.out.println("\nAperte enter para voltar ao Menu");
        voltar.nextLine();
    }

    public static void obterCidadesVizinhas(Grafo<String[]> grafo, int entrada) {
        Scanner voltar = new Scanner(System.in);

        Vertice<String[]> cidadeOrigem = grafo.getVertices().get(entrada);
        System.out.println("Cidade de Origem: " + Arrays.deepToString(cidadeOrigem.getValor()));

        imprimeDestinos(cidadeOrigem, false);

        System.out.println("\nAperte enter para voltar ao Menu");
        voltar.nextLine();
    }

    //
    private static void imprimeDestinos(Vertice<String[]> cidade, boolean todas) {
        System.out.print("____Destino: \n");

        for(int contador = 0; contador < cidade.getDestinos().size(); contador++) {
            if(todas || cidade.getDestinos().get(contador).getPeso() > 0) {
                System.out.print("    Cidade de Destino: ");
                System.out.println(Arrays.deepToString(cidade.getDestinos().get(contador).getDestino().getValor()));
                System.out.print("      Distância: ");
                System.out.println(cidade.getDestinos().get(contador).getPeso());
            }
        }
    }

    private static void imprimeCidades(Grafo<String[]> grafo) {
        System.out.println("\nGrafo - Relação das Distâncias entre as Cidades");

        for(Vertice<String[]> cidade: grafo.getVertices()) {
            System.out.print("\n__Cidade Origem: ");
            System.out.println(Arrays.deepToString(cidade.getValor()));

            imprimeDestinos(cidade, true);
        }
    }

    //
    private static Grafo<String[]> montaGrafo(ArrayList<Vertice<String[]>> cidades, ArrayList<String[]> pesos) {
        Grafo<String[]> grafo = new Grafo<>();


        for(int cidade = 0; cidade < cidades.size(); cidade++) {
            ArrayList<Aresta<String[]>> cidadesDestino = new ArrayList<>();

            for(int contador = 0; contador < pesos.size(); contador++) {
                Aresta<String[]> cidadeDestino = new Aresta<>();

                cidadeDestino.setDestino(cidades.get(contador));
                cidadeDestino.setPeso(Float.parseFloat(pesos.get(cidade)[contador].replace(',', '.')));

                cidadesDestino.add(cidadeDestino);
            }
            cidades.get(cidade).setDestinos(cidadesDestino);
        }

        grafo.setVertices(cidades);

        return grafo;

        //cidadeDestino.setDestino(cidade); // coloca o vertice da cidade como um destino da cidade na aresta
        //distanciasCidades.add(distanciaCidade); // adiciona a aresta com a cidade de destino na lista de arestas

    }

    // abre arquivo que foi gerado com as cidades e distancias
    // faz a chamada para gerar o grafo
    private static Grafo<String[]> abreArquivo(String nomeArquivoEntrada) throws IOException {
        String caminhoEntradaRegistros = "_entrada_registros/";
        String entradaRegistro = caminhoEntradaRegistros + nomeArquivoEntrada;

        FileReader arquivo = new FileReader(entradaRegistro);
        BufferedReader leitor = new BufferedReader(arquivo);

        // primeira linha do arquivo
        String linha = leitor.readLine();
        int qtdRegistros = Integer.parseInt(linha);

        //
        ArrayList<Vertice<String[]>> cidades = new ArrayList<>();
        ArrayList<String[]> pesos = new ArrayList<>();

        for(int contador = 0; contador < qtdRegistros * 2; contador++) {
            Vertice<String[]> cidade = new Vertice<>();

            linha = leitor.readLine();
            String[] conteudo = linha.split(";");

            // adiciona primeiro as cidades
            if(contador < qtdRegistros) {

                cidade.setValor(conteudo); // coloca o codigo e o nome da cidade no vertice
                cidades.add(cidade); // adiciona o vertice da cidade na lista dos vertices de cidades do grafo
            }
            // depois adiciona as distancias
            else {
                pesos.add(conteudo);
                //ver se precisa fazer um for para pegar cada float da linha de distancias do arquivo
            }
        }

        arquivo.close();

        return montaGrafo(cidades, pesos);
    }

    // metodo para salvar arquivo de saída, precisa ser revisado e consertado antes de usar
    // fazer ele criar/salvar o arquivo de retorno das opcoes 1 e 2 do menu principal
    private static void salvaArquivo(String arquivo, ArrayList<String[]> arquivoRegistros) throws IOException {
        String caminhoPasta = "_saida_registros/";
        String saidaRegistro = "_" + arquivo;

        File pastaSaida = new File(caminhoPasta);
        if (!pastaSaida.exists()){
            pastaSaida.mkdir();
        }

        FileWriter novoRegistro = new FileWriter(new File(pastaSaida, saidaRegistro));

        for(String[] linha: arquivoRegistros) {
            String conteudo = "";

            for(int dado = 0; dado < linha.length; dado++) {
                StringBuilder sb = new StringBuilder();
                if(dado == linha.length - 1) {
                    conteudo = sb.append(conteudo).append(linha[dado]).toString();
                }
                else {
                    conteudo = sb.append(conteudo).append(linha[dado]).append(";").toString();
                }
            }
            novoRegistro.write(conteudo + "\n");
        }

        novoRegistro.close();

        System.out.println("\n\nRegistro salvo.");
    }

    // Limpa o console
    private static void limpaTela() throws IOException, InterruptedException {
        if(System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
    }


    // Menu de opcoes do trabalho - falta revisar e consertar
    private static void mostraMenu(Grafo<String[]> grafo) throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        int opcao = -1;
        int entrada;

        while (opcao != 3) {
            System.out.println("\n***********************************\n");
            System.out.println("     ESCOLHA A OPÇÃO");
            System.out.println("\n --- 1: OBTER CIDADES VIZINHAS");
            System.out.println(" --- 2: OBTER TODOS OS CAMINHOS DA CIDADE");
            System.out.println("\n --- 3: SAIR");
            System.out.println("\n***********************************\n");
            System.out.print("-> ");
            opcao = input.nextInt();

            limpaTela();

            if (opcao == 1) {
                System.out.println("OBTER CIDADES VIZINHAS");
                System.out.println("Digite o código da cidade de origem (0 a " + grafo.getVertices().size() + "):");
                System.out.print("-> ");
                entrada = input.nextInt();

                obterCidadesVizinhas(grafo, entrada);
            }
            else if (opcao == 2) {
                System.out.println("OBTER TODOS OS CAMINHOS DA CIDADE");
                System.out.println("Digite o código da cidade de origem (0 a " + grafo.getVertices().size() + "):");
                System.out.print("-> ");
                entrada = input.nextInt();

                obterCaminhos(grafo, entrada);
            }
            else if (opcao == 3) {
                System.out.println("Tchauzim");
            }
        }
    }

    // Main
    public static void main(String[] args) throws IOException, InterruptedException {
        // instancia um grafo como variavel global que vai receber suas informacoes de um arquivo
        Grafo<String[]> grafo;

        GeradorArquivosGrafo g = new GeradorArquivosGrafo(); // instancia um gerador de arquivos
        String nomeArquivoEntrada; // cria variavel que vai ter o nome do arquivo gerado

        // gera um arquivo com a quantidade definida de cidades e distancias
        // passa o nome do arquivo gerado para a variavel
        nomeArquivoEntrada = g.geraArquivo(10);

        // le o arquivo gerado
        grafo = abreArquivo(nomeArquivoEntrada); // consertar e fazer direito

        imprimeCidades(grafo);

        // chama o menu de opcoes para o usuario
        mostraMenu(grafo);
    }
}