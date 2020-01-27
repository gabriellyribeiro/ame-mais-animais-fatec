/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Legislacao;
import util.NamedParameterStatement;
import util.PoolConexao;

/**
 *
 * @author Jenniferson
 */
public class LegislacaoDAO {

    private static LegislacaoDAO instance;
    private Connection conexao;
    private Integer qtdRegistros;

    public static LegislacaoDAO getInstance() {
        if (instance == null) {
            instance = new LegislacaoDAO();
        }

        return instance;
    }

    public Legislacao inserir(Legislacao legislacao) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "INSERT INTO tab_legislacao (titulo, descricao) VALUES (?, ?)";

        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, legislacao.getTitulo());
            stmt.setString(2, legislacao.getDescricao());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                legislacao.setId(rs.getInt(1));
            }
            conexao.commit();

        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            throw new SQLException(e);
        } finally {
            PoolConexao.desconectar(conexao, stmt, rs);
        }

        return legislacao;
    }

    public Legislacao alterar(Legislacao legislacao) throws SQLException {
        NamedParameterStatement stmt = null;
        String sql = "UPDATE tab_legislacao set titulo = :titulo, descricao = :descricao WHERE id = :id";
        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("titulo", legislacao.getTitulo());
            stmt.setString("descricao", legislacao.getDescricao());
            stmt.setInt("id", legislacao.getId());
            stmt.executeUpdate();
            conexao.commit();
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(conexao, stmt);
        }

        return legislacao;
    }

    public boolean excluir(int id) throws SQLException {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        boolean excluido = false;

        String sql = "DELETE FROM tab_legislacao WHERE id = :id ";

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

    public List<Legislacao> pesquisar(int offset, int qtdRegistros, Legislacao legislacao) {
        NamedParameterStatement ps = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        List<Legislacao> legislacaoList = new ArrayList<Legislacao>();

        String sql = "SELECT SQL_CALC_FOUND_ROWS "
                + " l.id legislacao_id, l.titulo legislacao_titulo, l.descricao legislacao_descricao "
                + " FROM "
                + " tab_legislacao l "
                + " WHERE 1 = 1 ";

        if (legislacao != null) {
            sql += legislacao.getTitulo().trim().equals("") ? "" : " AND l.titulo LIKE :titulo ";
            sql += legislacao.getDescricao().trim().equals("") ? "" : " AND l.descricao LIKE :descricao ";
        }

        sql += " limit :offset, :qtdRegistros";

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            ps = new NamedParameterStatement(this.conexao, sql);
            ps.setInt("offset", offset);
            ps.setInt("qtdRegistros", qtdRegistros);

            if (legislacao != null) {
                if (!legislacao.getTitulo().trim().equals("")) {
                    ps.setString("titulo", legislacao.getTitulo() + "%");
                }

                if (!legislacao.getDescricao().trim().equals("")) {
                    ps.setString("descricao", legislacao.getDescricao() + "%");
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Legislacao l = new Legislacao();
                l.setId(rs.getInt("legislacao_id"));
                l.setTitulo(rs.getString("legislacao_titulo"));
                l.setDescricao(rs.getString("legislacao_descricao"));
                legislacaoList.add(l);
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

        return legislacaoList;
    }
    
    public List<Legislacao> getLegislacao() {
        NamedParameterStatement ps = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        List<Legislacao> legislacaoList = new ArrayList<Legislacao>();

        String sql = "SELECT "
                + " l.id legislacao_id, l.titulo legislacao_titulo, l.descricao legislacao_descricao "
                + " FROM "
                + " tab_legislacao l "
                + " WHERE 1 = 1 ";

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            ps = new NamedParameterStatement(this.conexao, sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Legislacao l = new Legislacao();
                l.setId(rs.getInt("legislacao_id"));
                l.setTitulo(rs.getString("legislacao_titulo"));
                l.setDescricao(rs.getString("legislacao_descricao"));
                legislacaoList.add(l);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, ps, rs);
        }

        return legislacaoList;
    }

    public String existeLegislacao(String codigo, String descricao) {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Legislacao legislacao = null;

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            String sql = " select id, titulo, descricao from tab_legislacao where titulo = :titulo ";
            sql += !codigo.equals("0") && !codigo.equals("") ? " and id != :id " : "";
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("titulo", descricao);
            if (!codigo.equals("0") && !codigo.equals("")) {
                stmt.setString("id", codigo);
            }
            rs = stmt.executeQuery();

            if (rs.next()) {
                legislacao = new Legislacao();
                legislacao.setId(rs.getInt("id"));
                legislacao.setTitulo(rs.getString("titulo"));
                legislacao.setDescricao(rs.getString("descricao"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return new Gson().toJson(legislacao);
    }

    public Legislacao buscaLegislacao(int id) {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Legislacao legislacao = new Legislacao();

        try {
            String sql = "SELECT id legislacao_id, titulo legislacao_titulo, descricao legislacao_descricao FROM tab_legislacao WHERE id = :id ";
            this.conexao = PoolConexao.getInstance().getConexao();
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setInt("id", id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                legislacao.setId(rs.getInt("legislacao_id"));
                legislacao.setTitulo(rs.getString("legislacao_titulo"));
                legislacao.setDescricao(rs.getString("legislacao_descricao"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return legislacao;
    }

}
