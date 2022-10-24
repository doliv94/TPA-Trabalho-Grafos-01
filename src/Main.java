// TPA - 1a Etapa do Trabalho Prático de Grafos - prof. Victorio Carvalho
// Ifes Serra 2022/2
// Dupla: Brunna Dalzini e Rafaela Amorim Pessim

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import geradorarquivos.*;

import listadeadjacencias.ArestaAD;
import listadeadjacencias.GrafoAD;
import listadeadjacencias.VerticeAD;

import listadearestas.ArestaAR;
import listadearestas.GrafoAR;
import listadearestas.VerticeAR;

import matrizdeadjacencias.GrafoMA;
import matrizdeadjacencias.VerticeMA;

public class Main<T> {
    private GrafoAR<T> grafoAR;

/*
    // Metodos para Matriz de Adjacencias
    public void buscaEmLarguraMA() {

        boolean marcados[] = new boolean[this.quantVertices];
        int atual = 0;
        ArrayList<Integer> fila = new ArrayList<Integer>();

        fila.add(atual);

        while(fila.size() > 0) {
            atual = fila.get(0);
            fila.remove(0);
            marcados[atual] = true;

            System.out.println(this.vertices.get(atual).getValor());

            for(int dest = 0; dest < this.quantVertices; dest++) {
                // Se o no eh adjacente
                if(arestas[atual][dest] > 0) {
                    // Se ele ainda nao foi visitado o coloco na fila
                    if(!marcados[dest]) {
                        fila.add(dest);
                    }
                }
            }
        }
    }



    // Metodos para Lista de Adjacencias
    public ArrayList<ArestaAD> getDestinosAD() {

        return this.destinos;
    }

    public void buscaEmLarguraAD() {
        ArrayList<VerticeAD> marcados = new ArrayList<VerticeAD>();
        ArrayList<VerticeAD> fila = new ArrayList<VerticeAD>();

        // Pego o primeiro vertice como ponto de partida e o coloco na fila
        // Poderia escolher qualquer outro...
        // Mas note que dependendo do grafo pode ser que vc nao caminhe por todos os vertices

        VerticeAD atual = this.vertices.get(0);
        fila.add(atual);

        // Enquanto houver vertice na fila...
        while (fila.size() > 0) {
            // Pego o proximo da fila, marco como visitado e o imprimo
            atual = fila.get(0);
            fila.remove(0);
            marcados.add(atual);

            System.out.println(atual.getValor());

            // Depois pego a lista de adjacencia do no e se o no adjacente ainda
            // nao tiver sido visitado, o coloco na fila
            ArrayList<ArestaAD> destinos = atual.getDestinosAD();
            VerticeAD proximo;

            for(int i = 0; i < destinos.size(); i++) {
                proximo = destinos.get(i).getDestinosAD();

                if(!marcados.contains(proximo)) {
                    fila.add(proximo);
                }
            }
        }
    }


*/
    // Metodos para Lista de Arestas
    private ArrayList<ArestaAR> obterDestinosAR(VerticeAR v) {
        ArrayList<ArestaAR> destinos = new ArrayList<ArestaAR>();
        ArestaAR atual;

        for(int i = 0; i < this.arestas.size(); i++) {
            atual = this.arestas.get(i);

            if(atual.getOrigem().equals(v)) {
                destinos.add(atual);
            }
        }
        return destinos;
    }

    public void buscaEmLarguraAR() {
        ArrayList<VerticeAR> marcados = new ArrayList<VerticeAR>();
        ArrayList<VerticeAR> fila = new ArrayList<VerticeAR>();
        VerticeAR atual = this.vertices.get(0);
        fila.add(atual);

        while (fila.size() > 0) {
            atual = fila.get(0);
            fila.remove(0);
            marcados.add(atual);

            System.out.println(atual.getValor());

            ArrayList<ArestaAR> destinos = this.obterDestinosAR(atual);
            VerticeAR proximo;

            for(int i = 0; i < destinos.size(); i++) {
                proximo = destinos.get(i).getDestino();

                if(!marcados.contains(proximo)) {
                    fila.add(proximo);
                }
            }
        }
    }



    //
    public static void salvaArquivo(String arquivo, ArrayList<String[]> arquivoRegistros) throws IOException {
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



    public ArrayList<String[]> abreArquivo(String nomeArquivoEntrada) throws IOException {
        String caminhoEntradaRegistros = "_entrada_registros/";
        String entradaRegistro = caminhoEntradaRegistros + nomeArquivoEntrada;

        ArrayList<String[]> arquivoRegistros = new ArrayList<String[]>();

        FileReader arquivo = new FileReader(entradaRegistro);
        BufferedReader leitor = new BufferedReader(arquivo);

        // primeira linha
        String linha = leitor.readLine();
        int qtdRegistros = Integer.parseInt(linha);

        VerticeAR<String[]> cidade;
        ArestaAR distanciaCidades;
        float valorDistancia;

        //String[] primeiraLinha = { linha };
        //arquivoRegistros.add(primeiraLinha);

        // gera lista de arestas
        //
        for(int contador = 0; contador < qtdRegistros * 2; contador++) {
            linha = leitor.readLine();
            String[] conteudo = linha.split(";");
            //arquivoRegistros.add(conteudo);
            if(contador < qtdRegistros) {
                cidade.setValor(conteudo);

                distanciaCidades.setOrigem(cidade);
                distanciaCidades.setDestino();
            } else {
                valorDistancia = Float.parseFloat(conteudo[]);

            }
        }

        arquivo.close();

        System.out.println(Arrays.deepToString(arquivoRegistros.toArray()));

        return arquivoRegistros;
    }

    public static void limpaTela() throws IOException, InterruptedException {
        if(System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
    }



    //
    private static void mostraMenu() throws IOException, InterruptedException {
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
                System.out.println("Digite o código da cidade de origem:");
                System.out.print("-> ");
                entrada = input.nextInt();

            } else if (opcao == 2) {
                System.out.println("OBTER TODOS OS CAMINHOS DA CIDADE");
                System.out.println("Digite o código da cidade de origem:");
                System.out.print("-> ");
                entrada = input.nextInt();

            } else if (opcao == 3) {
                System.out.println("Tchauzim");

            }
        }
    }

    // Main
    public void main(String[] args) throws IOException, InterruptedException {
        GeradorArquivosGrafo g = new GeradorArquivosGrafo();
        ArrayList<String[]> arquivoRegistros;
        String nomeArquivoEntrada = "";

        nomeArquivoEntrada = g.geraArquivo(10);
        arquivoRegistros = abreArquivo(nomeArquivoEntrada); // consertar isso para converter a entrada para os 3 tipos



        mostraMenu();
    }
}