package Projeto;

import java.util.List;

public class Pizza {
    private List<String> sabores;
    private double preco;
    private TamanhoPizza tamanho;

    public enum TamanhoPizza {
        BROTO,
        GRANDE,
        GIGA;

        public static TamanhoPizza getByIndex(int index) {
            TamanhoPizza[] tamanhos = TamanhoPizza.values();
            if (index >= 0 && index < tamanhos.length) {
                return tamanhos[index];
            } else {
                throw new IllegalArgumentException("Posição incorreta do index");
            }
        }
    }

    public Pizza(List<String> sabores, double preco, TamanhoPizza tamanho) {
        this.sabores = sabores;
        this.preco = preco;
        this.tamanho = tamanho;
    }

    public List<String> getSabores() {
        return sabores;
    }

    public double getPreco() {
        return preco;
    }

    public TamanhoPizza getTamanho() {
        return tamanho;
    }

    @Override
    public String toString() {
        return String.format("Pizza [Sabores: %s, Preço: R$ %.2f, Tamanho: %s]", String.join(", ", sabores), preco, tamanho);
    }
}
