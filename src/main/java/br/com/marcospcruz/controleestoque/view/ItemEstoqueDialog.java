package br.com.marcospcruz.controleestoque.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import br.com.marcospcruz.controleestoque.controller.EstoqueController;
import br.com.marcospcruz.controleestoque.controller.ProdutoController;
import br.com.marcospcruz.controleestoque.model.Produto;

public class ItemEstoqueDialog extends AbstractDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5400606071268553023L;

	private static final String MESSAGE_EXCEPTION = "Produto j� existente no Estoque!";
	private static final String MESSAGE_EXCEPTION_2 = "É necessário selecionar um Produto na Lista.";
	private static final String MESSAGE_EXCEPTION_3 = "Seleção inválida!";

	private JComboBox cmbProduto;
	private JFormattedTextField txtQuantidadeInicial;
	private JButton btnAdicionarItem;
	private EstoqueController controller;

	public ItemEstoqueDialog(EstoqueController controller, JFrame owner) {

		super(owner, "Item de Estoque", true);

		setSize(600, 200);

		this.controller = controller;

		setLayout(new FlowLayout(FlowLayout.CENTER));

		add(configuraJPanelNovoItem());

		btnAdicionarItem = inicializaJButton("Adicionar Item");

		add(btnAdicionarItem);

		setVisible(true);

	}

	private JPanel configuraJPanelNovoItem() {

		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(2, 2, 2, 5));

		panel.setBorder(new TitledBorder("Novo Item de Estoque"));

		panel.add(new JLabel("Produto"));

		cmbProduto = new JComboBox();

		cmbProduto.setModel(carregaComboProdutoModel());

		panel.add(cmbProduto);

		panel.add(new JLabel("Quantidade"));

		txtQuantidadeInicial = inicializaNumberField();

		panel.add(txtQuantidadeInicial);

		return panel;

	}

	/**
	 * 
	 * @return
	 */
	private ComboBoxModel carregaComboProdutoModel() {

		ProdutoController controller = new ProdutoController();

		List lista = controller.getProdutos();

		Object[] objetos = new Object[lista.size() + 1];

		objetos[0] = ITEM_ZERO_COMBO;

		for (int i = 0; i < lista.size(); i++) {

			objetos[i + 1] = lista.get(i);

		}

		// Object[] objetos = { "Selecione uma Op��o" };

		DefaultComboBoxModel model = new DefaultComboBoxModel(objetos);

		return model;

	}

	private void adicionarItemEstoque() throws Exception {

		Produto produto = null;

		try {

			produto = (Produto) cmbProduto.getSelectedItem();

		} catch (ClassCastException e) {

			e.printStackTrace();

			// atualizaView();

			throw new Exception(MESSAGE_EXCEPTION_2);

		}

		controller.buscaItemEstoque(produto.getDescricaoProduto());

		if (cmbProduto.getSelectedIndex() == 0) {

			throw new Exception(MESSAGE_EXCEPTION_3);

		}

		if (controller.getItemEstoque() != null) {

			controller.anulaAtributos();

			throw new Exception(MESSAGE_EXCEPTION);

		}

		controller.criaItemEstoque(produto, txtQuantidadeInicial.getText());

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String mensagem = "";

		int tipoMensagem = 0;

		try {

			adicionarItemEstoque();

			mensagem = "Item adicionado ao estoque com sucesso!";

			tipoMensagem = JOptionPane.INFORMATION_MESSAGE;

		} catch (Exception e) {

			e.printStackTrace();

			mensagem = e.getMessage();

			tipoMensagem = JOptionPane.ERROR_MESSAGE;

		}

		exibeMensagemRetorno(mensagem, tipoMensagem);

		if (tipoMensagem != JOptionPane.ERROR_MESSAGE)

			dispose();

	}

	private void exibeMensagemRetorno(String mensagem, int tipoMensagem) {

		JOptionPane.showMessageDialog(null, mensagem, "Alerta", tipoMensagem);

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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void excluiItem() throws Exception {
		// TODO Auto-generated method stub
		
	}

}