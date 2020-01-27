/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Contato;
import util.NamedParameterStatement;
import util.PoolConexao;

/**
 *
 * @author Gaby
 */
public class EnderecoDAO {

    private static EnderecoDAO instance;
    private Integer qtdRegistros;

    public static EnderecoDAO getInstance() {
        if (instance == null) {
            instance = new EnderecoDAO();
        }

        return instance;
    }

    public Contato inserir(Contato contato, Connection conexao) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "INSERT INTO tab_contato_endereco (contato, cep, logradouro, numero, bairro, cidade, uf) "
                + "VALUES (?,?,?,?,?,?,?)";

        try {
            stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, contato.getId());
            stmt.setString(2, contato.getEndereco().getCep());
            stmt.setString(3, contato.getEndereco().getLogradouro());
            stmt.setInt(4, contato.getEndereco().getNumero());
            stmt.setString(5, contato.getEndereco().getBairro());
            stmt.setString(6, contato.getEndereco().getCidade());
            stmt.setString(7, contato.getEndereco().getUf());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                contato.getEndereco().setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            throw new SQLException(e);
        }

        return contato;
    }

    public Contato alterar(Contato contato, Connection conexao) throws SQLException {
        NamedParameterStatement stmt = null;
        String sql = "UPDATE tab_contato_endereco set cep = :cep, logradouro = :logradouro,"
                + "  numero = :numero, bairro = :bairro, cidade = :cidade, uf = :uf "
                + " WHERE id = :id";
        try {
            stmt = new NamedParameterStatement(conexao, sql);
            stmt.setString("cep", contato.getEndereco().getCep());
            stmt.setString("logradouro", contato.getEndereco().getLogradouro());
            stmt.setInt("numero", contato.getEndereco().getNumero());
            stmt.setString("bairro", contato.getEndereco().getBairro());
            stmt.setString("cidade", contato.getEndereco().getCidade());
            stmt.setString("uf", contato.getEndereco().getUf());
            stmt.setInt("id", contato.getId());
            stmt.executeUpdate();
            conexao.commit();
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            System.out.println(e.getMessage());
        }

        return contato;

    }
}
