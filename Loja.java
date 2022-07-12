// OBJETIVO DO PROGRAMA: Este programa tem como objetvo auxiliar um produtor em sua loja levando até ele a praticidade
// e agilidade no seu processo, além de um controle de vendas com relatórios diários de estoque, vendas e lucro

// NOME DOS PROGRAMADORES: Breno Henrique Silva Carvalho, Davi Gomes dos Santos, Gustavo Costa Pereira

// DATA DE ESCRITA: 28/06/2022

// ÚLTIMA ATUALIZAÇÃO: 03/07/2022

import java.util.Scanner;

public class Loja {
    private static Estoque estoque;
    private static Controlador controlador;

    public static void menu() throws Exception {
        // Neste procedimento estamos utilizando da recursividade para a exibição contínua do menu de acordo com 
        // nossa lógica de implementação
        Scanner ent= new Scanner(System.in);
        int opc;
        System.out.println("1-Fazer venda");
        System.out.println("2-Exibir estoque");
        System.out.println("3-Terminar dia e salvar relatório diário");
        opc = ent.nextInt();
        if (opc == 1) {
            controlador.executarVenda();
        } else if (opc == 2) {
            controlador.exibirProdutos();
        } else if (opc == 3) {
            controlador.gerarRelatorio();
        } else {
            System.out.println("Opção inválida");
        }
        if (opc != 3) {
            System.out.println();
            menu();
        }
        ent.close();
    }

    public static void main(String[] args) throws Exception {
        // Executável do sistema chamando as devidas classes
        estoque = new Estoque();
        Venda venda = new Venda();
        controlador = new Controlador(estoque, venda);
        menu();
    }
}
