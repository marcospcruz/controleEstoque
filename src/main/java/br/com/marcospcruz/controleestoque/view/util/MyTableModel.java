package br.com.marcospcruz.controleestoque.view.util;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

	private List linhas;

	private Object[] colunas;

	public MyTableModel(List linhas, Object[] colunas) {

		this.linhas = linhas;

		this.colunas = colunas;

		fireTableDataChanged();

	}

	@Override
	public int getColumnCount() {

		return colunas.length;
	}

	@Override
	public int getRowCount() {

		return linhas.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {

		Object[] array = (Object[]) linhas.get(linha);

		return array[coluna];

	}

	@Override
	public String getColumnName(int col) {

		return colunas[col].toString();

	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {

		// return super.isCellEditable(rowIndex, columnIndex);

		return false;

	}

}
