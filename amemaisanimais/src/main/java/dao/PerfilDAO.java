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
import model.Perfil;
import util.NamedParameterStatement;
import util.PoolConexao;

/**
 *
 * @author Jenniferson
 */
public class PerfilDAO {

    private static PerfilDAO instance;
    private Connection conexao;
    private Integer qtdRegistros;

    public static PerfilDAO getInstance() {
        if (instance == null) {
            instance = new PerfilDAO();
        }

        return instance;
    }

    public Perfil inserir(Perfil perfil) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "INSERT INTO tab_perfil (descricao) VALUES (?)";

        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, perfil.getDescricao());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                perfil.setId(rs.getInt(1));
            }
            conexao.commit();

        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            throw new SQLException(e);
        } finally {
            PoolConexao.desconectar(conexao, stmt, rs);
        }

        return perfil;
    }

    public Perfil alterar(Perfil perfil) throws SQLException {
        NamedParameterStatement stmt = null;
        String sql = "update tab_perfil set descricao = :descricao where id = :id";
        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("descricao", perfil.getDescricao());
            stmt.setInt("id", perfil.getId());
            stmt.executeUpdate();
            conexao.commit();
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(conexao, stmt);
        }

        return perfil;
    }

    public List<Perfil> pesquisar(int offset, int qtdRegistros, Perfil perfil) {
        NamedParameterStatement ps = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        List<Perfil> perfilList = new ArrayList<Perfil>();

        String sql = "SELECT SQL_CALC_FOUND_ROWS p.id perfil_id, p.descricao perfil_descricao  from tab_perfil p where 1 = 1";

        if (perfil != null) {
            sql += perfil.getDescricao().trim().equals("") ? "" : " AND p.descricao LIKE :descricao ";
        }

        sql += " limit :offset, :qtdRegistros";

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            ps = new NamedParameterStatement(this.conexao, sql);
            ps.setInt("offset", offset);
            ps.setInt("qtdRegistros", qtdRegistros);

            if (perfil != null) {

                if (!perfil.getDescricao().trim().equals("")) {
                    ps.setString("descricao", perfil.getDescricao() + "%");
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Perfil p = new Perfil();
                p.setId(rs.getInt("perfil_id"));
                p.setDescricao(rs.getString("perfil_descricao"));
                perfilList.add(p);
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

        return perfilList;
    }

    public String existePerfil(String codigo, String descricao) {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Perfil perfil = null;

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            String sql = " select id, descricao from tab_perfil where descricao = :descricao ";
            sql += !codigo.equals("0") && !codigo.equals("") ? " and id != :id " : "";
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("descricao", descricao);
            if (!codigo.equals("0") && !codigo.equals("")) {
                stmt.setString("id", codigo);
            }
            rs = stmt.executeQuery();

            if (rs.next()) {
                perfil = new Perfil();
                perfil.setId(rs.getInt("id"));
                perfil.setDescricao(rs.getString("descricao"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return new Gson().toJson(perfil);
    }

    public Perfil buscaPerfil(int id) {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Perfil perfil = new Perfil();

        try {
            String sql = "select p.id perfil_id, p.descricao perfil_descricao  from tab_perfil p  where p.id = :id ";
            this.conexao = PoolConexao.getInstance().getConexao();
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setInt("id", id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                perfil.setId(rs.getInt("perfil_id"));
                perfil.setDescricao(rs.getString("perfil_descricao"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return perfil;
    }

    public List<Perfil> getPerfis(boolean pesquisa) throws SQLException {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;

        List<Perfil> perfils = new ArrayList<Perfil>();
        String sql = "SELECT id,descricao FROM tab_perfil";

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            stmt = new NamedParameterStatement(this.conexao, sql);
            rs = stmt.executeQuery();

            if (pesquisa) {
                Perfil perfil = new Perfil();
                perfil.setId(0);
                perfil.setDescricao("Todos");
                perfils.add(perfil);
            }

            while (rs.next()) {
                Perfil p = new Perfil();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                perfils.add(p);
            }
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            throw new SQLException(e);
        } finally {
            PoolConexao.desconectar(conexao, stmt, rs);
        }

        return perfils;
    }

}
