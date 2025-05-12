import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioCompras {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java RelatorioCompras <arquivoEntrada> <arquivoSaida>");
            return;
        }

        Path caminhoEntrada = Paths.get(args[0]);
        Path caminhoSaida = Paths.get(args[1]);

        List<String> linhasLidas;
        try {
            linhasLidas = Files.readAllLines(caminhoEntrada);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de entrada: " + e.getMessage());
            return;
        }

        ArrayList<String> nomesClientes = new ArrayList<>();
        ArrayList<Double> totaisClientes = new ArrayList<>();

        for (String linha : linhasLidas) {
            if (linha.trim().isEmpty()) continue; 

            String[] partes = linha.split(",");

            if (partes.length < 3) {
                continue; 
            }

            String nomeCliente = partes[0].trim();
            String valorStr = partes[2].trim();
            double valor;

            try {
                valor = Double.parseDouble(valorStr);
            } catch (NumberFormatException e) {
                continue; 
            }

            int index = nomesClientes.indexOf(nomeCliente);
            if (index >= 0) {
                totaisClientes.set(index, totaisClientes.get(index) + valor);
            } else {
                nomesClientes.add(nomeCliente);
                totaisClientes.add(valor);
            }
        }

        List<String> linhasSaida = new ArrayList<>();
        for (int i = 0; i < nomesClientes.size(); i++) {
            String linha = nomesClientes.get(i) + ": " + String.format("%.2f", totaisClientes.get(i));
            linhasSaida.add(linha);
        }

        try {
            Files.write(caminhoSaida, linhasSaida);
            System.out.println("Relatório gerado com sucesso em: " + caminhoSaida);
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo de saída: " + e.getMessage());
        }
    }
}
