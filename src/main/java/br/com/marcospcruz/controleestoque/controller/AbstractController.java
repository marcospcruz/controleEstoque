package br.com.marcospcruz.controleestoque.controller;

public class AbstractController {

	protected static final String BUSCA_INVALIDA = "Busca Inv·lida";

	protected boolean contemAcentuacao(String parametro) {

		String pattern = "Á„ıÈ·ÌÛ˙Ò";

		for (char caractere : pattern.toCharArray())

			for (char c : parametro.toCharArray())

				if (c == caractere)

					return true;

		return false;

	}

}
