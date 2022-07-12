// Classe onde será implementado os métodos de venda
import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Venda {
    private String caminhoArquivo = "vendas.txt";

    public void salvarVenda(String tipo, String categoria, String quantidade, String produto) throws IOException {
        //Este procedimento está gerando o relatório de venda individual sem encerrar o programa
        try {
            File file = new File(this.caminhoArquivo);
            FileWriter fw = new FileWriter(file, true);

            String body = tipo + ";" + categoria + ";" + quantidade + ";" + produto + ";" + LocalDate.now().toString()
                    + "\n";

            fw.write(body);
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    

    public void gerarRelatorio() {
        // Neste procedimento estamos criando o relatório final de venda com a quantidade vendida de cada produto e 
        // a quantidade total de lucros no dia  
        float total = 0.0f;
        try {
            File file = new File(this.caminhoArquivo);
            Scanner ler = new Scanner(file);
            String leftAlignFormat = "| %-10s | %-10s | %-10s | %-10s | %-10s |%n";
            String body = "|----------------------------------------------------------------|\n";
            body += String.format(leftAlignFormat, "Tipo", "Categoria", "Quantidade", "Valor", "Data");
            body += "|----------------------------------------------------------------|\n";
            while (ler.hasNextLine()) {
                String[] valores = ler.nextLine().split(";");
                int quantidadeVendida = Integer.parseInt(valores[2]);
                float valorUnitario = Float.parseFloat(valores[3]);
                total += (float) (quantidadeVendida * valorUnitario);
                body += String.format(leftAlignFormat, valores);
                body += "|----------------------------------------------------------------|\n";
            }
            ler.close();
            body += String.format("\nTotal de Lucro do dia: %.2f", total);
            FileWriter fw = new FileWriter("Relatório Diario de Vendas" + LocalDate.now().toString() + ".txt");
            fw.write(body);
            fw.close();
            System.out.println("Relatório de vendas gerado com sucesso.");
        } catch (Exception e) {
         System.err.println(e.getMessage());
        }

     
    

    }

}
