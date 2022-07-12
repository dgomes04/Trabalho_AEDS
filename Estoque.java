// Classe onde será implementado os métodos de estocagem e verificações de estoque, além do auxilio importante nos 
// outros métodos
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class Estoque {
    private String[][] produtos = new String[4][5];
    private String caminhoArquivo = "estoque.txt";

    Estoque() {
        // Neste construtor estamos iniciando o projeto nos certificando que o estoque
        // bruto não exista
        this.init();
    }

    private void init() {
        // Neste procedimento estamos carregando o estoque do arquivo na matriz e caso
        // não exista o arquivo usamos um
        // catch para tratar o erro e criar o arquivo com o estoque bruto
        try {
            this.carregarProdutos();
        } catch (Exception e) {
            this.criarProdutos();
        }
    }

    private void carregarProdutos() throws FileNotFoundException {
        // Neste procedimento estamos criando o arquivo de estoque bruto e salvando na
        // matriz
        File file = new File(this.caminhoArquivo);
        Scanner ler = new Scanner(file);
        int contador = 0;
        while (ler.hasNextLine()) {
            String[] valores = ler.nextLine().split(";");
            for (int j = 0; j < valores.length; j++) {
                this.produtos[contador][j] = valores[j];
            }
            contador++;
        }
        ler.close();
    }

    private void criarProdutos() {
        // Neste procedimento estamos criando o arquivo com os produtos no estoque bruto
        // caso ele não exista
        try {
            File file = new File(this.caminhoArquivo);
            PrintWriter pw = new PrintWriter(file);
            pw.print(
                    "1;adulto;estampada;10;2.50;\n2;adulto;lisa;55;3.50;\n3;infantil;estampada;30;3.20;\n4;infantil;lisa;12;7.60;");
            pw.close();
            this.carregarProdutos();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void atualizarArquivo() throws IOException {
        // Neste procedimento estamos atualizando o arquivo de estoque bruto inicial,
        // este método só vai ser chamado
        // após uma venda concretizada
        File file = new File(this.caminhoArquivo);
        PrintWriter pw = new PrintWriter(file);
        String body = "";
        for (int i = 0; i < produtos.length; i++) {
            for (int j = 0; j < produtos[i].length; j++) {
                body += produtos[i][j] + ";";
            }
            body += "\n";
        }
        pw.print(body);
        pw.close();
    }

    public void validarCodigoProduto(int codigoProduto) throws Exception {
        // Neste procedimento estamos validando o código digitado pelo produtor para a
        // escolha do produto a ser vendido
        if (codigoProduto > produtos.length || codigoProduto < 0) {
            throw new Exception("Codigo de produto inválido!");
        }
    }

    public void validarQuantidadeEstoque(int codigoProduto, int quantidade) throws Exception {
        // Neste procedimento estamos verificando o estoque atual em conjunto com a
        // classe venda
        // Estamos verificando o estoque para cumprir o requisito do projeto de só
        // permitir vendas de produto em
        // estoque
        if (codigoProduto > produtos.length || codigoProduto < 0) {
            throw new Exception("Codigo de produto inválido!");
        }

        int quantidadeSalva = Integer.parseInt(produtos[codigoProduto - 1][3]);

        if (quantidadeSalva <= 0) {
            throw new Exception("Produto indisponível");
        }

        if (quantidade <= 0) {
            throw new Exception("Quantidade inválida!");
        }

        if (quantidade > quantidadeSalva) {
            throw new Exception("Quantidade indisponível!");
        }
    }

    public void atualizarQuantidadeProduto(int codigoProduto, int quantidade) throws IOException {
        // Neste procedimento estamos atualizando o arquivo de estpque bruto inicial,
        // este método só vai ser chamado
        // após uma venda concretizada
        // Este procedimento trabalha em conjunto com com o procedimento "atualizar arquivo" 
        int quantidadeAtual = Integer.parseInt(produtos[codigoProduto - 1][3]);
        produtos[codigoProduto - 1][3] = String.valueOf(quantidadeAtual - quantidade);
        this.atualizarArquivo();
    }

    public String[] getProdutoPorCodigo(int codigo) throws Exception {
        // Nesta função estamos validando o código de produto que o produtor digitou
        if (codigo > produtos.length || codigo < 0) {
            throw new Exception("Codigo de produto inválido!");
        }

        return this.produtos[codigo - 1];
    }

    public void exibirProdutos() {
        // Neste procedimento estamos exibindo os produtos como uma tabela no terminal
        String leftAlignFormat = "| %-10s | %-10s | %-10s | %-10s | %-10s |%n";
        System.out.format("|----------------------------------------------------------------|%n");
        System.out.format(leftAlignFormat, "Código", "Tipo", "Categoria", "Estoque", "Preço");
        System.out.format("|----------------------------------------------------------------|%n");
        for (int i = 0; i < produtos.length; i++) {
            System.out.format(leftAlignFormat, this.produtos[i]);
        }
        System.out.format("|----------------------------------------------------------------|%n");
    }

    public void gerarRelatorio() {
        // Neste procedimento estamos criando o relatorio de estoque ao final do dia
        try {
            FileWriter pw = new FileWriter("Relatório Estoque " + LocalDate.now().toString() + ".txt");
            String body = "|--------------------------------------------------|\n";
            String leftAlignFormat = "| %-10s | %-10s | %-10s | %-10s|%n";
            body += String.format(leftAlignFormat, "Codigo", "Tipo", "Categoria", "Estoque");
            body += "|--------------------------------------------------|\n";
            for (int i = 0; i < this.produtos.length; i++) {
                body += String.format(leftAlignFormat, this.produtos[i]);
            }
            body += "|--------------------------------------------------|\n";
            pw.write(body);
            pw.close();
            System.out.println("Relatório de estoque gerado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] obterProdutoPorCodigo(int codigoProduto) throws IOException {
        // Esta função estamos retornando para outros procedimentos da classe o produto que o produtor escolheu
        return produtos[codigoProduto - 1];
    }
}