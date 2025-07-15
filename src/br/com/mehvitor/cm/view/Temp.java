package br.com.mehvitor.cm.view;

import br.com.mehvitor.cm.model.Tabuleiro;

public class Temp {

	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(3, 3, 9);
		
		tabuleiro.registrarObservador(e -> {
			if(e.isGanhou()) {
			System.out.println("Ganhou!!");
		} else {
			System.out.println("Perdeu!!");
		}
		});

		
		tabuleiro.alterMarcacao(0, 0);
		tabuleiro.alterMarcacao(0, 1);
		tabuleiro.alterMarcacao(0, 2);
		tabuleiro.alterMarcacao(1, 0);
		tabuleiro.alterMarcacao(1, 1);
		tabuleiro.alterMarcacao(1, 2);
		tabuleiro.alterMarcacao(2, 0);
		tabuleiro.alterMarcacao(2, 1);
		tabuleiro.abrirCampo(2, 2);
		//tabuleiro.alterMarcacao(2, 2);
		
	}

}
