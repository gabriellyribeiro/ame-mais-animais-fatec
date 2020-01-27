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
import java.util.ArrayList;
import java.util.List;
import model.QuemSomos;
import util.NamedParameterStatement;
import util.PoolConexao;

/**
 *
 * @author Jenniferson
 */
public class QuemSomosDAO {

    private static QuemSomosDAO instance;
    private Connection conexao;
    private Integer qtdRegistros;

    public static QuemSomosDAO getInstance() {
        if (instance == null) {
            instance = new QuemSomosDAO();
        }

        return instance;
    }

    public QuemSomos inserir(QuemSomos quemSomos) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "INSERT INTO tab_quem_somos (descricao) VALUES (?)";

        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, quemSomos.getDescricao());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                quemSomos.setId(rs.getInt(1));
            }
            conexao.commit();

        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            throw new SQLException(e);
        } finally {
            PoolConexao.desconectar(conexao, stmt, rs);
        }

        return quemSomos;
    }

    public QuemSomos alterar(QuemSomos quemSomos) throws SQLException {
        NamedParameterStatement stmt = null;
        String sql = "UPDATE tab_quem_somos set descricao = :descricao WHERE id = :id";
        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("descricao", quemSomos.getDescricao());
            stmt.setInt("id", quemSomos.getId());
            stmt.executeUpdate();
            conexao.commit();
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(conexao, stmt);
        }

        return quemSomos;
    }

    public boolean excluir(int id) throws SQLException {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        boolean excluido = false;

        String sql = "DELETE FROM tab_quem_somos WHERE id = :id ";

        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setInt("id", id);
            stmt.executeUpdate();
            conexao.commit();
            excluido = true;
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            throw new SQLException(e);
        } finally {
            PoolConexao.desconectar(conexao, stmt, rs);
        }

        return excluido;
    }

    public List<QuemSomos> pesquisar(int offset, int qtdRegistros, QuemSomos quemSomos) {
        NamedParameterStatement ps = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<QuemSomos> quemSomosList = new ArrayList<QuemSomos>();

        String sql = "SELECT SQL_CALC_FOUND_ROWS "
                + " q.id quem_somos_id,"
                + " q.descricao quem_somos_descricao "
                + " FROM "
                + " tab_quem_somos q "
                + " WHERE 1 = 1 "
                + " limit :offset, :qtdRegistros";

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            ps = new NamedParameterStatement(this.conexao, sql);
            ps.setInt("offset", offset);
            ps.setInt("qtdRegistros", qtdRegistros);
            rs = ps.executeQuery();

            while (rs.next()) {
                QuemSomos qs = new QuemSomos();
                qs.setId(rs.getInt("quem_somos_id"));
                qs.setDescricao(rs.getString("quem_somos_descricao"));
                quemSomosList.add(qs);
            }

            stmt = this.conexao.prepareStatement("SELECT FOUND_ROWS()");
            rs = stmt.executeQuery();
            if (rs.next()) {
                this.qtdRegistros = rs.getInt(1);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, ps, rs);
        }

        return quemSomosList;
    }

    public QuemSomos buscaQuemSomos() {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        QuemSomos quemSomos = new QuemSomos();

        try {
            String sql = "SELECT id quem_somos_id, descricao quem_somos_descricao FROM tab_quem_somos limit 1 ";
            this.conexao = PoolConexao.getInstance().getConexao();
            stmt = new NamedParameterStatement(this.conexao, sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                quemSomos.setId(rs.getInt("quem_somos_id"));
                quemSomos.setDescricao(rs.getString("quem_somos_descricao"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return quemSomos;
    }

}
