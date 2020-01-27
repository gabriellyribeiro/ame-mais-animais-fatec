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
import model.Categoria;
import model.Perfil;
import util.NamedParameterStatement;
import util.PoolConexao;

/**
 *
 * @author Jenniferson
 */
public class CategoriaDAO {

    private static CategoriaDAO instance;
    private Connection conexao;
    private Integer qtdRegistros;

    public static CategoriaDAO getInstance() {
        if (instance == null) {
            instance = new CategoriaDAO();
        }

        return instance;
    }

    public Categoria inserir(Categoria categoria) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "INSERT INTO tab_categoria (descricao) VALUES (?)";

        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, categoria.getDescricao());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                categoria.setId(rs.getInt(1));
            }
            conexao.commit();

        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            throw new SQLException(e);
        } finally {
            PoolConexao.desconectar(conexao, stmt, rs);
        }

        return categoria;
    }

    public Categoria alterar(Categoria categoria) throws SQLException {
        NamedParameterStatement stmt = null;
        String sql = "UPDATE tab_categoria set descricao = :descricao WHERE id = :id";
        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("descricao", categoria.getDescricao());
            stmt.setInt("id", categoria.getId());
            stmt.executeUpdate();
            conexao.commit();
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(conexao, stmt);
        }

        return categoria;
    }

    public boolean excluir(int id) throws SQLException {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        boolean excluido = false;

        String sql = "DELETE FROM tab_categoria WHERE id = :id ";

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

    public List<Categoria> getListaCategorias(int offset, int qtdRegistros, Categoria categoria) {
        NamedParameterStatement ps = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        List<Categoria> cateogriaList = new ArrayList<Categoria>();

        String sql = "SELECT SQL_CALC_FOUND_ROWS "
                + " c.id categoria_id, "
                + " c.descricao categoria_descricao "
                + " FROM "
                + " tab_categoria c "
                + " WHERE 1 = 1 ";

        if (categoria != null) {
            sql += categoria.getDescricao().trim().equals("") ? "" : " AND c.descricao LIKE :descricao ";
        }

        sql += " limit :offset, :qtdRegistros";

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            ps = new NamedParameterStatement(this.conexao, sql);
            ps.setInt("offset", offset);
            ps.setInt("qtdRegistros", qtdRegistros);

            if (categoria != null) {
                if (!categoria.getDescricao().trim().equals("")) {
                    ps.setString("descricao", categoria.getDescricao() + "%");
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("categoria_id"));
                c.setDescricao(rs.getString("categoria_descricao"));
                cateogriaList.add(c);
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

        return cateogriaList;
    }

    public String existeCategoria(String codigo, String descricao) {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Categoria categoria = null;

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            String sql = " select id, descricao from tab_categoria where descricao = :descricao ";
            sql += !codigo.equals("0") && !codigo.equals("") ? " and id != :id " : "";
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("descricao", descricao);
            if (!codigo.equals("0") && !codigo.equals("")) {
                stmt.setString("id", codigo);
            }
            rs = stmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setDescricao(rs.getString("descricao"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return new Gson().toJson(categoria);
    }

    public Categoria buscaCategoria(int id) {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Categoria categoria = new Categoria();

        try {
            String sql = "SELECT id categoria_id, descricao categoria_descricao FROM tab_categoria WHERE id = :id ";
            this.conexao = PoolConexao.getInstance().getConexao();
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setInt("id", id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                categoria.setId(rs.getInt("categoria_id"));
                categoria.setDescricao(rs.getString("categoria_descricao"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return categoria;
    }

    public List<Categoria> getCategorias(boolean pesquisa) throws SQLException {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;

        List<Categoria> categorias = new ArrayList<Categoria>();
        String sql = "SELECT id,descricao FROM tab_categoria";

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            stmt = new NamedParameterStatement(this.conexao, sql);
            rs = stmt.executeQuery();

            if (pesquisa) {
                Categoria c = new Categoria();
                c.setId(0);
                c.setDescricao("Todos");
                categorias.add(c);
            }

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setDescricao(rs.getString("descricao"));
                categorias.add(c);
            }
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            throw new SQLException(e);
        } finally {
            PoolConexao.desconectar(conexao, stmt, rs);
        }

        return categorias;
    }

}
