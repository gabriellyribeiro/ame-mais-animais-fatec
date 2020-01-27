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
import model.Dica;
import util.NamedParameterStatement;
import util.PoolConexao;

/**
 *
 * @author Jenniferson
 */
public class DicaDAO {

    private static DicaDAO instance;
    private Connection conexao;
    private Integer qtdRegistros;

    public static DicaDAO getInstance() {
        if (instance == null) {
            instance = new DicaDAO();
        }

        return instance;
    }

    public Dica inserir(Dica dica) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "INSERT INTO tab_dica (titulo, descricao, categoria) VALUES (?,?,?)";

        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, dica.getTitulo());
            stmt.setString(2, dica.getDescricao());
            stmt.setInt(3, dica.getCategoria().getId());

            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                dica.setId(rs.getInt(1));
            }
            conexao.commit();

        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            throw new SQLException(e);
        } finally {
            PoolConexao.desconectar(conexao, stmt, rs);
        }

        return dica;
    }

    public Dica alterar(Dica dica) throws SQLException {
        NamedParameterStatement stmt = null;
        String sql = "UPDATE tab_dica set titulo = :titulo, descricao = :descricao, categoria = :categoria WHERE id = :id";
        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("titulo", dica.getTitulo());
            stmt.setString("descricao", dica.getDescricao());
            stmt.setInt("categoria", dica.getCategoria().getId());
            stmt.setInt("id", dica.getId());
            stmt.executeUpdate();
            conexao.commit();
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(conexao, stmt);
        }

        return dica;
    }

    public boolean excluir(int id) throws SQLException {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        boolean excluido = false;

        String sql = "DELETE FROM tab_dica WHERE id = :id ";

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

    public List<Dica> getListaDicas(int offset, int qtdRegistros, Dica dica) {
        NamedParameterStatement ps = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        List<Dica> dicaList = new ArrayList<Dica>();

        String sql = " SELECT SQL_CALC_FOUND_ROWS  "
                + " d.id dica_id, d.titulo dica_titulo, d.descricao dica_descricao, "
                + " c.id categoria_id, c.descricao categoria_descricao "
                + " FROM  "
                + " tab_dica d "
                + " join tab_categoria c on c.id = d.categoria "
                + " WHERE 1 = 1 ";

        if (dica != null) {
            sql += dica.getTitulo().trim().equals("") ? "" : " AND d.titulo LIKE :titulo ";
            sql += dica.getDescricao().trim().equals("") ? "" : " AND d.descricao LIKE :descricao ";
            sql += dica.getCategoria().getId() == 0 ? "" : " AND d.categoria = :categoria ";
        }

        sql += " limit :offset, :qtdRegistros";

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            ps = new NamedParameterStatement(this.conexao, sql);
            ps.setInt("offset", offset);
            ps.setInt("qtdRegistros", qtdRegistros);

            if (dica != null) {
                if (!dica.getTitulo().trim().equals("")) {
                    ps.setString("titulo", dica.getTitulo() + "%");
                }

                if (!dica.getDescricao().trim().equals("")) {
                    ps.setString("descricao", dica.getDescricao() + "%");
                }

                if (dica.getCategoria().getId() != 0) {
                    ps.setInt("categoria", dica.getCategoria().getId());
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Dica d = new Dica();
                d.setId(rs.getInt("dica_id"));
                d.setTitulo(rs.getString("dica_titulo"));
                d.setDescricao(rs.getString("dica_descricao"));
                d.getCategoria().setId(rs.getInt("categoria_id"));
                d.getCategoria().setDescricao(rs.getString("categoria_descricao"));
                dicaList.add(d);
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

        return dicaList;
    }

    public String existeDica(String codigo, String descricao) {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Dica dica = null;

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            String sql = " select id, titulo, descricao from tab_dica where descricao = :descricao ";
            sql += !codigo.equals("0") && !codigo.equals("") ? " and id != :id " : "";
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("descricao", descricao);
            if (!codigo.equals("0") && !codigo.equals("")) {
                stmt.setString("id", codigo);
            }
            rs = stmt.executeQuery();

            if (rs.next()) {
                dica = new Dica();
                dica.setId(rs.getInt("id"));
                dica.setTitulo(rs.getString("titulo"));
                dica.setDescricao(rs.getString("descricao"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return new Gson().toJson(dica);
    }

    public Dica buscaDica(int id) {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Dica dica = new Dica();

        try {
            String sql = "SELECT d.id dica_id, d.titulo dica_titulo, d.descricao dica_descricao,"
                    + " c.id categoria_id, c.descricao categoria_descricao"
                    + " FROM "
                    + " tab_dica d "
                    + " join tab_categoria c on c.id = d.categoria "
                    + " WHERE id = :id ";
            this.conexao = PoolConexao.getInstance().getConexao();
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setInt("id", id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                dica.setId(rs.getInt("dica_id"));
                dica.setTitulo(rs.getString("dica_titulo"));
                dica.setDescricao(rs.getString("dica_descricao"));
                dica.getCategoria().setId(rs.getInt("categoria_id"));
                dica.getCategoria().setDescricao(rs.getString("categoria_descricao"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return dica;
    }

    public List<Dica> getDicas() {
        NamedParameterStatement ps = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        List<Dica> dicaList = new ArrayList<Dica>();

        String sql = " SELECT "
                + " d.id dica_id, d.titulo dica_titulo, d.descricao dica_descricao, "
                + " c.id categoria_id, c.descricao categoria_descricao "
                + " FROM  "
                + " tab_dica d "
                + " join tab_categoria c on c.id = d.categoria "
                + " WHERE 1 = 1 ";
        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            ps = new NamedParameterStatement(this.conexao, sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Dica d = new Dica();
                d.setId(rs.getInt("dica_id"));
                d.setTitulo(rs.getString("dica_titulo"));
                d.setDescricao(rs.getString("dica_descricao"));
                d.getCategoria().setId(rs.getInt("categoria_id"));
                d.getCategoria().setDescricao(rs.getString("categoria_descricao"));
                dicaList.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, ps, rs);
        }

        return dicaList;
    }

}
