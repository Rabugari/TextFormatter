package br.com.formatter;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Massao
 */
@RunWith(JUnit4.class)
public class TestFormatterTest {

	private StringBuilder sbLittleText;
	private StringBuilder sbMidleText;
	private StringBuilder sbBigText;
	private TextFormatter formatter;
	
	@Before
	public void setUp() {
		sbLittleText = new StringBuilder();
		sbLittleText.append("In the beginning God");
		
		sbMidleText = new StringBuilder();
		sbMidleText.append("In the beginning God created the heavens and the earth.");
		sbMidleText.append("Now the earth was formless and empty, darkness was over the ");
		sbMidleText.append("surface of the deep, and the Spirit of God was hovering over the waters.");
		sbMidleText.append(" And God said, \"Let there be light,\" and there was light.");
		
		sbBigText = new StringBuilder();
		sbBigText.append("In the beginning God created the heavens and the earth.");
		sbBigText.append("Now the earth was formless and empty, darkness was over the ");
		sbBigText.append("surface of the deep, and the Spirit of God was hovering over the waters.");
		sbBigText.append(" And God said, \"Let there be light,\" and there was light.");
		sbBigText.append("God saw that the light was good, and he separated the light ");
		sbBigText.append("from the darkness. God called the light \"day,\" and the darkness he called ");
		sbBigText.append("\"night.\" And there was evening, and there was morning - the first day.");
		
		formatter = new TextFormatter();
	}
	
	@Test(expected=IOException.class)
	public void testaArquivoInexistente() throws IOException {
		formatter.formartText(30, "Z:/file/text1.txt", "Z:/file/text2.text");
	}
	
	@Test
	public void testaFormatacaoPelaQuantidadeDeLinhasRetornadas() {
		StringBuilder formatedLittleText = formatter.putLimitOnLine(20, sbLittleText);
		assertEquals(1, formatedLittleText.toString().split("\n").length);
		
		StringBuilder formatedMidleText = formatter.putLimitOnLine(20, sbMidleText);
		assertEquals(13, formatedMidleText.toString().split("\n").length);
		
		StringBuilder formatedBigText = formatter.putLimitOnLine(20, sbBigText);
		assertEquals(24, formatedBigText.toString().split("\n").length);
	}
	
	@Test
	public void testaJustificacao() {
		StringBuilder formatedLittleText = formatter.justifyText(20, formatter.putLimitOnLine(20, sbLittleText).toString());
		assertEquals(1, formatedLittleText.toString().split("\n").length);
		assertEquals(20, formatedLittleText.toString().split("\n")[0].length());
		
		StringBuilder formatedMidleText = formatter.justifyText(20, formatter.putLimitOnLine(20, sbMidleText).toString());
		assertEquals(20, formatedMidleText.toString().split("\n")[0].length());
		
		StringBuilder formatedBigText = formatter.justifyText(20,formatter.putLimitOnLine(20, sbBigText).toString());
		assertEquals(20, formatedBigText.toString().split("\n")[0].length());
	}
}
