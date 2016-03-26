package br.com.marcospcruz.controleestoque.util;

public enum ConstantesEnum {
	SIMBOLO_MONETARIO_BR(0), LBL_BTN_RELATORIO(1), PRODUTO_LABEL(2), LBL_BTN_TIPO_PRODUTO(
			3), LBL_BTN_NOVO_ITEM_ESTOQUE(4), LBL_BTN_LIMPAR(5), CONSULTA_ESTOQUE(
			6), DESCRICAO_ITEM_LABEL(7), RELATORIO_TITLE_BORDER(8), CODIGO_LABEL(
			9), CATEGORIA_LABEL(10), TIPO_ITEM_LABEL(11), QUANTIDADE_LABEL(12), VALOR_UNITARIO_LABEL(
			13), VALOR_TOTAL_LABEL(14), DESCRICAO_LABEL(15), SUB_DESCRICAO_LABEL(
			16), CADASTRO_TIPO_PRODUTO_TITLE(17), TIPO_PRODUTO_LABEL(18), TIPOS_PRODUTOS_LABEL(
			19), CONFIRMACAO_REGISTRO_ATUALIZADO(20), CONFIRMANDO_ATUALIZACAO_MSG_TITLE(
			21), SUB_TIPO_DE_LABEL(22), SELECIONE_PRIMEIRA_MSG(23), CONFIRMACAO_EXCLUSAO_TITLE(
			24), CONFIRMACAO_EXCLUSAO(25), UNIDADE_MEDIDA_LABEL(26), SELECAO_TIPO_PRODUTO_INVALIDA_EXCEPTION_MESSAGE(
			27), SELECAO_SUB_TIPO_PRODUTO_INVALIDA_EXCEPTION_MESSAGE(28), PRODUTO_JA_EXISTENTE_MESSAGE_EXCEPTION(
			29), PRODUTO_NECESSARIO_SELECIONAR_ALERTA(30), SELECAO_INVALIDA(31);
	private int index;

	ConstantesEnum(int index) {
		this.index = index;
	}

	private Object values[] = { "R$",// 0
			"Relatório de Estoque (PDF)",// 1
			"Produto",// 2
			"Tipos de Produto",// 3
			"Novo Item Estoque",// 4
			"Limpar",// 5
			"Consulta Estoque",// 6
			"Descrição Item",// 7
			"Relatórios",// 8
			"Código",// 9
			"Categoria",// 10
			"Tipo Item",// 11
			"Quantidade",// 12
			"Valor Unitário",// 13
			"Valor Total",// 14
			"Descrição",// 15
			"Sub-Descrição",// 16
			"Cadastro de Tipo de Produto",// 17
			"Tipo de Produto",// 18
			"Tipos de Produtos",// 19
			"Registro atualizado com Sucesso.",// 20
			"Confirmando Atualização",// 21
			"Sub-Tipo de:",// 22
			"Selecione uma Opção",// 23
			"Confirmar Exclusão",// 24
			"Deseja realmente excluir este item?",// 25
			"Un. Medida",// 26
			"Seleção de Tipo de Produto inválida.",// 27
			"Seleção de Sub-Tipo de Produto inválida",// 28
			"Produto já existente no Estoque!",// 29
			"É necessário selecionar um Produto na Lista.",// 30
			"Seleção inválida!" };

	public Object getValue() {
		return values[index];
	}
}
