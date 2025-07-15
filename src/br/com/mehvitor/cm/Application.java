package br.com.mehvitor.cm;

import br.com.mehvitor.cm.model.Tabuleiro;
import br.com.mehvitor.cm.view.TabuleiroConsole;

public class Application {

	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		new TabuleiroConsole(tabuleiro);

	}

}
