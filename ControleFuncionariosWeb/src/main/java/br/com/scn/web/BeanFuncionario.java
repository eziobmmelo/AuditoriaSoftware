package br.com.scn.web;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.scn.model.Funcionario;
import br.com.scn.util.ConnectionFactory;

@ManagedBean(name = "bFuncionario")
@RequestScoped
public class BeanFuncionario implements Serializable {

	private static final long serialVersionUID = 1L;
	private Funcionario funcionario = new Funcionario();

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String actionInsert() {
		Connection conexao = null;
		PreparedStatement psInsert = null;
		String sql = "INSERT INTO funcionario (nome, salario, bonus, desconto)" + "VALUES (?, ?, ?, ?)";
		try {
			conexao = ConnectionFactory.getConnection();
			psInsert = conexao.prepareStatement(sql);
			psInsert.setString(1, funcionario.getNome());
			psInsert.setFloat(2, funcionario.getSalario());
			psInsert.setFloat(3, funcionario.getBonus());
			psInsert.setFloat(4, funcionario.getDesconto());
			psInsert.executeUpdate();
			funcionario = new Funcionario();
		} catch (Exception e) {
			System.err.println("Erro no insert");
			e.printStackTrace();
		}
		return "lista_funcionarios";
	}

	public List<Funcionario> getListagem() {
		Connection conexao = null;
		PreparedStatement psSelect = null;
		ResultSet rsSelect = null;
		String sql = "SELECT id, nome, salario, bonus, desconto " 
				+ "FROM funcionario";
		List<Funcionario> lsFuncionarios = null;
		try {
			conexao = ConnectionFactory.getConnection();
			psSelect = conexao.prepareStatement(sql);
			rsSelect = psSelect.executeQuery();
			lsFuncionarios = new ArrayList<Funcionario>();
			while (rsSelect.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rsSelect.getLong("id"));
				funcionario.setNome(rsSelect.getString("nome"));
				funcionario.setSalario(rsSelect.getFloat("salario"));
				funcionario.setBonus(rsSelect.getFloat("bonus"));
				funcionario.setDesconto(rsSelect.getFloat("desconto"));
				lsFuncionarios.add(funcionario);
			}
		} catch (Exception e) {
			System.err.println("---------------------------------------");
			System.err.println("Erro no select");
			e.printStackTrace();
		}
		return lsFuncionarios;
	}
}
