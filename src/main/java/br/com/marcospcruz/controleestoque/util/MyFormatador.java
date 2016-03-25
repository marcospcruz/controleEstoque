package br.com.marcospcruz.controleestoque.util;

import java.text.DecimalFormat;

/**
 * Utilitario de formatação.
 * 
 * @author Marcos
 * 
 */
public class MyFormatador {
	/**
	 * formatador casas decimais.
	 * 
	 * @param valor
	 * @return
	 */
	public static String formataStringDecimais(float valor) {

		DecimalFormat formatadorNumero = new DecimalFormat("#.00");

		return formatadorNumero.format((double) valor);

	}

}
