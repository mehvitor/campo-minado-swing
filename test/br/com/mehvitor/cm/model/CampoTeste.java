package br.com.mehvitor.cm.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.mehvitor.cm.excecao.ExplosaoException;

public class CampoTeste {

	private Campo campo;
	
	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}
	
	@Test
	void testeVizinhoRealEsquerda() {
		Campo vizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoRealDireita() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoRealCima() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoRealBaixo() {
		Campo vizinho = new Campo(4, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoRealDiagonal() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoFalso() {
		Campo vizinho = new Campo(1, 1);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	@Test
	void testeValorPadraoAtributoMarcado() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacao() {
		campo.alterMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacaoDuasChamadas() {
		campo.alterMarcacao();
		campo.alterMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrirCampo());
	}
	
	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.alterMarcacao();
		assertFalse(campo.abrirCampo());
	}
	
	@Test
	void testeAbrirMinadoMarcado() {
		campo.alterMarcacao();
		campo.minar();
		assertFalse(campo.abrirCampo());
	}
	
	@Test
	void testeAbrirMinadoNaoMarcado() {
		campo.minar();
		
		assertThrows(ExplosaoException.class, () -> { 
			campo.abrirCampo();
		});
		
		assertFalse(campo.abrirCampo());
	}
	
	@Test
	void testeAbrirComVizinhos() {
		Campo campo22 = new Campo(2, 2);
		Campo campo11 = new Campo(1, 1);
	
		campo22.adicionarVizinho(campo11);
		
		campo.adicionarVizinho(campo22);
		campo.abrirCampo();
	
		assertTrue(campo22.isAberto() && campo11.isAberto());
	}
	
	@Test
	void testeAbrirComVizinhos2() {
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 1);
		campo12.minar();
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo12);
		campo22.adicionarVizinho(campo11);
		
		campo.adicionarVizinho(campo22);
		campo.abrirCampo();
	
		assertTrue(campo22.isAberto() && campo11.isFechado());
	}
	
	
}
