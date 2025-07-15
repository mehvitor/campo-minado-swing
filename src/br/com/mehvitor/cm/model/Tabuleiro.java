package br.com.mehvitor.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.mehvitor.cm.excecao.ExplosaoException;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();
	
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void abrirCampo(int linha, int coluna) {
		try {
			campos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrirCampo());;
		} catch (ExplosaoException e) {
			campos.forEach(c -> c.setCampoAberto(true));
			throw e;
		}
	}

	public void alterMarcacao(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alterMarcacao());;
	}

	private void gerarCampos() {
		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				campos.add(new Campo(i, j));
			}
			
		}
	}
	
	private void associarVizinhos() {
		for(Campo c1 : campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
		
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream()
					.filter(minado)
					.count();
			
		} while (minasArmadas < minas);
		
	}

	public boolean objetivoAlcancado() {
		return campos.stream()
				.allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciarJogo() {
		campos.stream()
		.forEach(c -> c.reiniciarJogo());
		sortearMinas();
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("   ");
		for (int c = 0; c < colunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		
		sb.append("\n");
		
		int i = 0;
		for (int l = 0; l < linhas; l++) {
			sb.append(" ");
			sb.append(l);
			sb.append(" ");
			for (int c = 0; c < colunas; c++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		
		
		return sb.toString();
	}
	
	
}
