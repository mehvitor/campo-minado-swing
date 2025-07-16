package br.com.mehvitor.cm.model;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean campoAberto = false;
	private boolean minado = false;
	private boolean campoMarcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservador> observadores = new ArrayList<>();
	
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public void registrarObservador(CampoObservador observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(CampoEvento evento) {
		observadores.stream()
		.forEach(o -> o.eventoOcorreu(this, evento));
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaTotal = deltaColuna + deltaLinha;
		
		if(deltaTotal == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if(deltaTotal == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}
	
	public void alterMarcacao() {
		if(!campoAberto) {
			campoMarcado = !campoMarcado; 
			
			if(campoMarcado) {
				notificarObservadores(CampoEvento.MARCAR);
			} else {
				notificarObservadores(CampoEvento.DESCARMAR);
			}
		}
	}
	
	public boolean abrirCampo() {
		if(!campoAberto && !campoMarcado) {
			
			if(minado) {
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}
			
			setCampoAberto(true);
			
			
			if(vizinhaSegura()) {
				vizinhos.forEach(v -> v.abrirCampo());
			}
			return true;
		}else {
			return false;
		}
	}
	
	public boolean vizinhaSegura() {
		return vizinhos.stream()
				.noneMatch(v -> v.minado);
	}
	
	public boolean isMarcado() {
		return campoMarcado;
	}
	
	void minar() {
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	public boolean isCampoAberto() {
		return campoAberto;
	
	}

	void setCampoAberto(boolean campoAberto) {
		this.campoAberto = campoAberto;
		
		if(campoAberto) {
			notificarObservadores(CampoEvento.ABRIR);
		}
	}

	public boolean isAberto() {
		return campoAberto;
	}
	
	public boolean isFechado() {
		return !isAberto();
	}
	
	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && campoAberto;
		boolean protegido = minado && campoMarcado;
		return desvendado || protegido;
	}
	
	public int minasNaVizinhanca() {
		return (int) vizinhos.stream()
				.filter(v -> v.minado)
				.count();
	}
	
	void reiniciarJogo() {
		campoAberto = false;
		campoMarcado = false;
		minado = false;	
		notificarObservadores(CampoEvento.REINICIAR);
	}

	
	
}
