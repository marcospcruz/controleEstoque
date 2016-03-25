package br.com.marcospcruz.controleestoque.controller.relatorio;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.marcospcruz.controleestoque.App;
import br.com.marcospcruz.controleestoque.controller.EstoqueController;
import br.com.marcospcruz.controleestoque.model.ItemEstoque;

public class RelatorioEstoqueGeral {

	private final static String PASTA_PROJETO = App.CONTROLE_ESTOQUE_HOME
			+ "\\";

	private final static String ARQUIVO_JASPER = "/relatorios_jaspers/relatorio_estoque_geral.jasper";

	private static final String RELATORIO_GERADO = "relatorio_estoque.pdf";

	private static final String NO_DATA_FOUND = "Não há Dados no Estoque.";

	private static final Object BRAZIL = new Locale("pt", "BR");

	public void gerarRelatorio() throws Exception {

		Collection<ItemEstoque> itensEstoque = listaEstoque();

		if (itensEstoque == null || itensEstoque.size() == 0)

			throw new Exception(NO_DATA_FOUND);

		JRDataSource jrDataSource = new JRBeanCollectionDataSource(itensEstoque);

		Map parametros = new HashMap();

		parametros.put(JRParameter.REPORT_LOCALE, BRAZIL);

		gerarRelatorio(jrDataSource, parametros);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void gerarRelatorio(JRDataSource jrDataSource, Map parametros)
			throws Exception {

		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("META-INF" + ARQUIVO_JASPER);

		FileOutputStream outPut = null;

		File arquivoRelatorio = new File(PASTA_PROJETO + RELATORIO_GERADO);

		try {

			if (arquivoRelatorio.exists()) {

				arquivoRelatorio.delete();

			}

			JasperPrint print = JasperFillManager.fillReport(inputStream,
					parametros, jrDataSource);

			outPut = new FileOutputStream(arquivoRelatorio);

			JasperExportManager.exportReportToPdfStream(print, outPut);

			outPut.close();

			Desktop.getDesktop().open(arquivoRelatorio);

		} catch (FileNotFoundException e) {

			e.printStackTrace();

			throw new Exception(e.getMessage());

		} catch (JRException e) {

			e.printStackTrace();

			throw new Exception("Logo Marca n�o encontrada. ");

		}

	}

	public static Collection<ItemEstoque> listaEstoque() {

		EstoqueController controller = new EstoqueController();

		return controller.getItensEstoque();

	}

	public static void main(String args[]) {

		try {

			new RelatorioEstoqueGeral().gerarRelatorio();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
