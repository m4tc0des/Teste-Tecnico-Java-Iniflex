import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999, 9, 24), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        funcionarios.removeIf(x -> x.getNome().equals("João"));

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        System.out.println("--- Lista de Funcionários ---");
        funcionarios.forEach(x -> System.out.println(
            x.getNome() + " | " + x.getDataNascimento().format(df) +
            " | R$ " + nf.format(x.getSalario()) + " | " + x.getFuncao()));

        funcionarios.forEach(x -> x.setSalario(x.getSalario().multiply(new BigDecimal("1.10"))));

        Map<String, List<Funcionario>> agrupados = funcionarios.stream()
            .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\n--- Agrupados por Função ---");
        agrupados.forEach((funcao, lista) -> {
            System.out.println(funcao + ": " + lista.stream().map(Funcionario::getNome).collect(Collectors.joining(", ")));
        });

        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        int idade = LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear();
        System.out.println("\n--- Maior Idade ---");
        System.out.println("Nome: " + maisVelho.getNome() + " | Idade: " + idade);

        System.out.println("\n--- Ordem Alfabética ---");
        funcionarios.stream().sorted(Comparator.comparing(Funcionario::getNome)).forEach(f -> System.out.println(f.getNome()));

        BigDecimal totalSalarios = funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos Salários: R$ " + nf.format(totalSalarios));

        BigDecimal salMin = new BigDecimal("1212.00");
        System.out.println("\n--- Qtd Salários Mínimos ---");
        funcionarios.forEach(f -> {
            BigDecimal qtd = f.getSalario().divide(salMin, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + ": " + qtd);
        });
    }
}