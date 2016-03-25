package br.com.marcospcruz.controleestoque.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import br.com.marcospcruz.controleestoque.controller.EstoqueController;
import br.com.marcospcruz.controleestoque.model.ItemEstoque;
import br.com.marcospcruz.controleestoque.util.MyFormatador;

public class ItemDoEstoqueDialog extends AbstractDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6305347846351815631L;

	private static final String UNIDADE_MONETARIA = "R$";

	private static final String TITULO_VALOR_UNITARIO = "Valor Unitário";

	private static final String TITULO_BORDA_TIPO_PECA_ROUPA = "Tipo de Produto";

	private static final String TITULO_BORDA_PECA_ROUPA = "Produto";

	private static final String TITULO_BORDA_CODIGO = "Código";

	private JLabel lblCodigo;
	private JLabel lblDescricaoTipoRoupa;
	private JLabel lblDescricaoRoupa;
	private JLabel lblValorUnitario;
	private JFormattedTextField txtQuantidade;
	private JButton btnSalvar;
	private JButton btnApagar;

	private EstoqueController controller;

	private ItemDoEstoqueDialog(JFrame owner, String tituloJanela, boolean modal) {

		super(owner, tituloJanela, modal);

		setSize(600, 300);

		setLayout(new FlowLayout());

		add(carregaItemEstoquePnl());

		add(quantidadeField());

		add(carregaJButtons());

	}

	public ItemDoEstoqueDialog(JFrame owner, EstoqueController controller) {

		this(owner, "Item do Estoque: "
				+ controller.getItemEstoque().getProduto()
						.getDescricaoProduto(), true);

		this.controller = controller;

		populaFormulario(controller.getItemEstoque());

		setVisible(true);

	}

	private JPanel carregaJButtons() {

		JPanel panelBotoes = new JPanel();

		btnSalvar = inicializaJButton(SALVAR_BUTTON_LBL);

		panelBotoes.add(btnSalvar);

		btnApagar = inicializaJButton(EXCLUIR_BUTTON_LBL);

		panelBotoes.add(btnApagar);

		return panelBotoes;
	}

	private JPanel quantidadeField() {

		JPanel panel = new JPanel();

		panel.add(new JLabel("Quantidade"));

		txtQuantidade = inicializaNumberField();

		panel.add(txtQuantidade);

		return panel;
	}

	private JPanel carregaItemEstoquePnl() {

		// JPanel itemEstoquePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JPanel itemEstoquePnl = new JPanel(new GridLayout(2, 2));

		itemEstoquePnl.setPreferredSize(new Dimension(500, 150));

		// itemEstoquePnl.setBorder(new TitledBorder("Item de Estoque"));

		lblCodigo = new JLabel();

		lblCodigo.setBorder(new TitledBorder(TITULO_BORDA_CODIGO));

		itemEstoquePnl.add(lblCodigo);

		lblDescricaoTipoRoupa = new JLabel();

		lblDescricaoTipoRoupa.setBorder(new TitledBorder(
				TITULO_BORDA_TIPO_PECA_ROUPA));

		itemEstoquePnl.add(lblDescricaoTipoRoupa);

		lblDescricaoRoupa = new JLabel();

		lblDescricaoRoupa.setBorder(new TitledBorder(TITULO_BORDA_PECA_ROUPA));

		itemEstoquePnl.add(lblDescricaoRoupa);

		lblValorUnitario = new JLabel();

		lblValorUnitario.setBorder(new TitledBorder(TITULO_VALOR_UNITARIO));

		itemEstoquePnl.add(lblValorUnitario);

		return itemEstoquePnl;

	}

	private void preencheEspacosVazios(JLabel lblField, String tituloBorda) {

		char espaco = ' ';

		String espacos = "";

		for (int i = 0; i < tituloBorda.length() + 20; i++)

			espacos += espaco;

		lblField.setText(espacos);

	}

	private void populaFormulario(ItemEstoque item) {

		String codigo = item.getIdItemEstoque().toString() + getEspacos();

		String descricaoTipoRoupa = item.getProduto().getTipoProduto()
				.toString()
				+ getEspacos();

		String valorUnitario = UNIDADE_MONETARIA
				+ MyFormatador.formataStringDecimais(item.getProduto()
						.getValorUnitario()) + getEspacos();

		String quantidade = item.getQuantidade().toString();

		String descricaoRoupa = item.getProduto().getDescricaoProduto()
				+ getEspacos();

		preencheFormulario(codigo, descricaoTipoRoupa, descricaoRoupa,
				valorUnitario, quantidade);

	}

	private String getEspacos() {

		String espacos = "";

		for (int i = 0; i < 20; i++)

			espacos += ' ';

		return espacos;

	}

	private void preencheFormulario(String codigo, String descricaoTipoRoupa,
			String descricaoRoupa, String valorUnitario, String quantidade) {

		lblCodigo.setText(codigo);

		lblDescricaoTipoRoupa.setText(descricaoTipoRoupa);

		lblValorUnitario.setText(valorUnitario);

		txtQuantidade.setText(quantidade);

		lblDescricaoRoupa.setText(descricaoRoupa);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String actionCommand = arg0.getActionCommand();

		try {
			selecionaAcao(actionCommand);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * x
	 */
	private void atualizaItemEstoque() throws Exception {

		if (controller.getItemEstoque() == null) {

			throw new Exception(
					"� necess�rio selecionar um �tem de Estoque antes.");

		} else {

			int quantidade = new Integer(txtQuantidade.getText());

			controller.atualizaItem(quantidade);

			dispose();

		}

	}

	protected void excluiItem() throws Exception {

		if (controller.getItemEstoque() == null) {

			throw new Exception(
					"� necess�rio selecionar um �tem no Estoque antes de Excluir.");

		}

		int confirmacao = JOptionPane.showConfirmDialog(null,
				"Confirmar Exclus�o", "Deseja realmente excluir este item?",
				JOptionPane.YES_NO_OPTION);

		if (confirmacao == 0) {

			controller.apagaItem();

			dispose();

		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void configuraJPanel() {
		// TODO Auto-generated method stub

	}

	@Override
	protected JPanel carregaJPanelBusca() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected JPanel carregaJpanelFormulario() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected JPanel carregaJpanelTable(int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void atualizaTableModel(Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void carregaTableModel() {
		// TODO Auto-generated method stub

	}

	@Override
	protected List carregaLinhasTableModel(List lista) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void populaFormulario() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void selecionaAcao(String actionCommand) throws Exception {

		if (actionCommand.equals(SALVAR_BUTTON_LBL)) {

			atualizaItemEstoque();

		} else if (actionCommand.equals(EXCLUIR_BUTTON_LBL)) {

			excluiItem();

		}

	}

}
