package br.com;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import br.com.formatter.TextFormatter;

/**
 * Algoritmo irá formatar as linhas de um arquivo e justifica-las de acordo com o número de caracteres inserido pelo usuário
 * @author Massao
 */
public class TextFormatterApplication {

	private static TextFormatter formatter;
	
	public static void main(String[] args) {
		formatter = new TextFormatter();
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o local de origem do arquivo: ");
		String originPath = scanner.nextLine();

		System.out.println("Digite a quantidade de caracteres por linha:");
		String nCharacters = scanner.nextLine();

		while(StringUtils.isEmpty(nCharacters) || !StringUtils.isNumeric(nCharacters)
				|| (StringUtils.isNumeric(nCharacters) && Long.parseLong(nCharacters)<0) ) {
			System.out.println("Dado inválido, digite novamente: ");
			nCharacters=scanner.nextLine();
		}

		System.out.println("Digite o nome e caminho do novo arquivo formatado: ");
		String newFileJustified = scanner.nextLine();

		try {
			formatter.formartText(Integer.parseInt(nCharacters), originPath, newFileJustified);	
		}catch(Exception e) {
			Logger.getLogger(TextFormatter.class.getSimpleName()).log(Level.SEVERE, "Erro na formatação do arquivo", e);
		}
	}
}
