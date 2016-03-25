package br.com.marcospcruz.controleestoque.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import br.com.marcospcruz.controleestoque.dao.Crud;
import br.com.marcospcruz.controleestoque.dao.CrudDao;
import br.com.marcospcruz.controleestoque.model.SubTipoProduto;
import br.com.marcospcruz.controleestoque.model.TipoProduto;
import br.com.marcospcruz.controleestoque.util.TipoProdutoNotFoundException;

/**
 * Classe respons�vel em gerenciar regras de neg�cio para Tipos de Pe�as de
 * Roupas.
 * 
 * @author Marcos
 * 
 */
public class TipoProdutoController extends AbstractController {

	private static final String RESULTADO_NAO_ENCONTRADO = "Tipo de Produto n�o encontrado.";

	private static final String TIPO_ITEM_INVALIDO = "Tipo de Produto Inv�lido";

	private static final String REMOCAO_INVALIDA_EXCEPTION = "Necess�rio efetuar a busca do Elemento antes de Excluir.";

	private static final String SELECIONE_TIPO_PRODUTO_EXCEPTION = "Selecione um Tipo de Produto v�lido!";

	private static final String TIPO_PRODUTO_JA_CADASTRADO = "Tipo Produto j� Cadastrado com esta Descri��o!";

	private static final String REMOCAO_TIPO_POPULADO = "Este Tipo de Produto cont�m Produtos associados a ele.";

	private static final String REMOCAO_SUPERTIPO_POPULADO = "Este Tipo de Produto cont�m Sub-Tipos de Produtos associados a ele.";

	private SubTipoProduto tipoProduto;

	private Crud<SubTipoProduto> tipoProdutoDao;

	private List tiposProdutos;

	/**
	 * M�todo Construtor sem Par�metros.
	 */
	public TipoProdutoController() {

		super();

		tipoProdutoDao = new CrudDao<SubTipoProduto>();

	}

	/**
	 * M�todo respons�vel em salvar novo tipo de roupa ou atualizar o j�
	 * existente.
	 * 
	 * @param descricao
	 * @param subTipo
	 * @param superTipoProduto
	 * @param sexo
	 * @throws Exception
	 */

	public void salva(String descricao, boolean subTipo, Object superTipoProduto)
			throws Exception {

		// validaTipoRoupa(descricao);

		if (descricao.length() == 0) {

			throw new Exception(TIPO_ITEM_INVALIDO);

		}

		validaCriaTipoProduto(subTipo, superTipoProduto, descricao);

		tipoProdutoDao.update(tipoProduto);

		setTipoProduto(null);

	}

	private void validaCriaTipoProduto(boolean subTipo,
			Object superTipoProduto, String descricao) throws Exception {

		try {

			tipoProduto = tipoProdutoDao.busca("tipoProduto.readParametro",
					"descricao", descricao.toUpperCase());

			throw new Exception(TIPO_PRODUTO_JA_CADASTRADO);

		} catch (NoResultException e) {

			e.printStackTrace();

			if (tipoProduto == null)
				tipoProduto = new SubTipoProduto();

		}

		if (subTipo) {

			validaCamposFormulario(superTipoProduto);

			tipoProduto.setSuperTipoProduto((SubTipoProduto) superTipoProduto);

			// tipoProduto.setSexo(sexo.toString());

		}

		tipoProduto.setDescricaoTipo(descricao);

	}

	private void validaTipoRoupa(String descricao) throws Exception {

		if (tipoProduto == null) {

			busca(descricao);

			if (tipoProduto != null) {

				tipoProduto = null;

				throw new Exception("Tipo de Roupa " + descricao
						+ " j� cadastrado!");

			}

		}

	}

	private void validaCamposFormulario(Object tipoProduto) throws Exception {

		// if (sexo == null) {
		//
		// throw new Exception("Selecione o Sexo para este tipo de Roupa");
		//
		// }

		if (tipoProduto == null)

			throw new Exception(SELECIONE_TIPO_PRODUTO_EXCEPTION);

	}

	public List<TipoProduto> getTiposProdutos() {

		if (tiposProdutos == null || tiposProdutos.size() < 1)

			carregaTiposPecasRoupa();

		return tiposProdutos;

	}

	public void setTiposProduto(List tiposProdutos) {
		this.tiposProdutos = tiposProdutos;
	}

	public SubTipoProduto getTipoPeca() {
		return tipoProduto;
	}

	public void setTipoProduto(SubTipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public void carregaTiposPecasRoupa() {

		setTiposProduto(tipoProdutoDao.busca("tipoProduto.readtiposabstratos"));

	}

	/**
	 * Busca Tipo de roupa
	 * 
	 * @param parametro
	 * @throws Exception
	 */
	// public void busca(String parametro) {
	//
	// try {
	//
	// tipoProduto = tipoProdutoDao.busca("tipopecaroupa.readParametro",
	// "descricao", parametro);
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	//
	// tipoProduto = null;
	//
	// }
	//
	// }
	/**
	 * 
	 * @param parametro
	 * @throws Exception
	 */
	public void busca(String parametro) throws Exception {

		zeraAtributos();

		if (parametro.length() == 0) {

			throw new Exception(BUSCA_INVALIDA);

		}

		if (contemAcentuacao(parametro)) {

			buscaInWorkAround(parametro);

		} else {

			String valor = "%" + parametro.toUpperCase() + "%";

			tiposProdutos = tipoProdutoDao.buscaList(
					"tipoProduto.readParametroLike", "descricao", valor);

		}

		if (tiposProdutos.size() >= 1)

			tipoProduto = (SubTipoProduto) tiposProdutos.get(0);

		else if (tiposProdutos.size() == 0)

			throw new TipoProdutoNotFoundException(RESULTADO_NAO_ENCONTRADO);

	}

	/**
	 * 
	 * @param parametro
	 */
	private void buscaInWorkAround(String parametro) {

		List<SubTipoProduto> tmp = tipoProdutoDao.busca("tipoProduto.readAll");

		tiposProdutos = new ArrayList<SubTipoProduto>();

		for (SubTipoProduto peca : tmp) {

			String descricao = peca.getDescricaoTipo().toLowerCase();

			if (descricao.contains(parametro.toLowerCase())) {

				tiposProdutos.add(peca);

			}

		}

	}

	private void zeraAtributos() {

		tiposProdutos = null;
		tipoProduto = null;

	}

	/**
	 * @throws Exception
	 * 
	 */
	public void excluir() throws Exception {

		if (tipoProduto == null || tipoProduto.getIdTipoItem() == null) {

			throw new Exception(REMOCAO_INVALIDA_EXCEPTION);

		}

		if (tipoProduto.getProdutos().size() > 0)

			throw new Exception(REMOCAO_TIPO_POPULADO);

		if (tipoProduto.getSubTiposProduto().size() > 0)
			throw new Exception(REMOCAO_SUPERTIPO_POPULADO);

		tipoProdutoDao.delete(tipoProduto);

		setTipoProduto(null);

		setTiposProduto(null);

	}

	/**
	 * 
	 * @param idSubProduto
	 */
	public void busca(int idSubProduto) {

		tipoProduto = tipoProdutoDao.busca(SubTipoProduto.class, idSubProduto);

	}

	public List buscaTodos() {

		return tipoProdutoDao.busca("tipoProduto.readtiposabstratos");

	}

	// public void iniciaTipoPecaRoupa() {
	//
	// tipoProduto = new SubTipoProduto();
	//
	// }

}
