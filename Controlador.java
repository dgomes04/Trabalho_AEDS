// Classe onde será implementado os métodos de controle 
import java.util.Scanner;

public class Controlador {

    private Estoque estoque;
    private Venda venda;
    private Scanner entrada = new Scanner(System.in);

    Controlador(Estoque estoque, Venda venda) {
        this.estoque = estoque;
        this.venda = venda;
    }

    public void executarVenda () { 
        //Neste procedimento estamos fazendo a lógica de vender o produto e decrementar do estoque bruto chamando os 
        //procedimentos das classes auxiliadoras 

        System.out.println("Digite o código do produto: ");
        estoque.exibirProdutos();
        int codigoProduto = entrada.nextInt();
        try {
            estoque.validarCodigoProduto(codigoProduto);
            System.out.print("Digite a quantidade: ");
            int quantidade = entrada.nextInt();
            System.out.println();
            estoque.validarQuantidadeEstoque(codigoProduto, quantidade);
            System.out.println("Digite 1 para concluir venda ou digite 2 para cancelar");
            int confirm = entrada.nextInt();
            if (confirm == 1) {
                estoque.atualizarQuantidadeProduto(codigoProduto, quantidade);
                String produto [] = estoque.obterProdutoPorCodigo(codigoProduto);
                venda.salvarVenda(produto[1], produto[2], String.valueOf(quantidade), produto[4]);
                System.out.println("Venda feita com sucesso.");
            }
        } catch (Exception error) {
            System.err.println(error.getMessage());
        }
    }

    public void exibirProdutos() {
        // Este procedimento está somente exibindo pro produtor o estoque com as informações necessárias para executar
        // a venda
        this.estoque.exibirProdutos();
    }

    public void gerarRelatorio() {
        //Este procedimento está gerando o relatório de venda individual sem encerrar o programa e também gerando o re
        // latório de estoque final conforme requisito do projeto
        this.estoque.gerarRelatorio();
        this.venda.gerarRelatorio();
    }
}
