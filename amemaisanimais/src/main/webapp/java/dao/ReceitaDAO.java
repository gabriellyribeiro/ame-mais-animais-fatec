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
import model.Receita;
import util.NamedParameterStatement;
import util.PoolConexao;

/**
 *
 * @author Jenniferson
 */
public class ReceitaDAO {

    private static ReceitaDAO instance;
    private Connection conexao;
    private Integer qtdRegistros;

    public static ReceitaDAO getInstance() {
        if (instance == null) {
            instance = new ReceitaDAO();
        }

        return instance;
    }

    public Receita inserir(Receita receita) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "INSERT INTO tab_receita (titulo, ingrediente, modopreparo) VALUES (?, ?, ?)";

        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, receita.getTitulo());
            stmt.setString(2, receita.getIngrediente());
            stmt.setString(3, receita.getModoPreparo());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                receita.setId(rs.getInt(1));
            }
            conexao.commit();

        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            throw new SQLException(e);
        } finally {
            PoolConexao.desconectar(conexao, stmt, rs);
        }

        return receita;
    }

    public Receita alterar(Receita receita) throws SQLException {
        NamedParameterStatement stmt = null;
        String sql = "UPDATE tab_receita set titulo = :titulo, ingrediente = :ingrediente, modopreparo = :modopreparo WHERE id = :id";
        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("titulo", receita.getTitulo());
            stmt.setString("ingrediente", receita.getIngrediente());
            stmt.setString("modopreparo", receita.getModoPreparo());
            stmt.setInt("id", receita.getId());
            stmt.executeUpdate();
            conexao.commit();
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(conexao, stmt);
        }

        return receita;
    }

    public boolean excluir(int id) throws SQLException {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        boolean excluido = false;

        String sql = "DELETE FROM tab_receita WHERE id = :id ";

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

    public List<Receita> pesquisar(int offset, int qtdRegistros, Receita receita) {
        NamedParameterStatement ps = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        List<Receita> receitaList = new ArrayList<Receita>();

        String sql = "SELECT SQL_CALC_FOUND_ROWS "
                + " r.id receita_id, r.titulo receita_titulo, r.ingrediente receita_ingrediente, r.modopreparo receita_modo_preparo "
                + " FROM  "
                + " tab_receita r "
                + " WHERE 1 = 1 ";

        if (receita != null) {
            sql += receita.getTitulo().trim().equals("") ? "" : " AND r.titulo LIKE :titulo ";
        }

        sql += " limit :offset, :qtdRegistros";

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            ps = new NamedParameterStatement(this.conexao, sql);
            ps.setInt("offset", offset);
            ps.setInt("qtdRegistros", qtdRegistros);

            if (receita != null) {
                if (!receita.getTitulo().trim().equals("")) {
                    ps.setString("titulo", receita.getTitulo() + "%");
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Receita r = new Receita();
                r.setId(rs.getInt("receita_id"));
                r.setTitulo(rs.getString("receita_titulo"));
                r.setIngrediente(rs.getString("receita_ingrediente"));
                r.setModoPreparo(rs.getString("receita_modo_preparo"));
                receitaList.add(r);
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

        return receitaList;
    }

    public List<Receita> getReceita() {
        NamedParameterStatement ps = null;
        ResultSet rs = null;

        List<Receita> receitaList = new ArrayList<Receita>();

        String sql = "SELECT "
                + " r.id receita_id, r.titulo receita_titulo, r.ingrediente receita_ingrediente, r.modopreparo receita_modo_preparo "
                + " FROM "
                + " tab_receita r "
                + " WHERE 1 = 1 ";

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            ps = new NamedParameterStatement(this.conexao, sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Receita r = new Receita();
                r.setId(rs.getInt("receita_id"));
                r.setTitulo(rs.getString("receita_titulo"));
                r.setIngrediente(rs.getString("receita_ingrediente"));
                r.setModoPreparo(rs.getString("receita_modo_preparo"));
                receitaList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, ps, rs);
        }

        return receitaList;
    }

    public String existeReceita(String codigo, String titulo) {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Receita receita = null;

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            String sql = " select id, titulo, ingrediente, modopreparo from  tab_receita where titulo = :titulo ";
            sql += !codigo.equals("0") && codigo.equals("") ? " and id != :id " : "";
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("titulo", titulo);
            if (!codigo.equals("0") && !codigo.equals("")) {
                stmt.setString("id", codigo);
            }
            rs = stmt.executeQuery();

            if (rs.next()) {
                receita = new Receita();
                receita.setId(rs.getInt("id"));
                receita.setTitulo(rs.getString("titulo"));
                receita.setIngrediente(rs.getString("ingrediente"));
                receita.setModoPreparo(rs.getString("modopreparo"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return new Gson().toJson(receita);
    }

    public Receita buscaReceita(int id) {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Receita receita = new Receita();

        try {
            String sql = "SELECT id receita_id, titulo receita_titulo, ingrediente receita_ingrediente,"
                    + " modopreparo receita_modo_preparo "
                    + " FROM tab_receita "
                    + " WHERE id = :id ";
            this.conexao = PoolConexao.getInstance().getConexao();
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setInt("id", id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                receita.setId(rs.getInt("receita_id"));
                receita.setTitulo(rs.getString("receita_titulo"));
                receita.setIngrediente(rs.getString("receita_ingrediente"));
                receita.setModoPreparo(rs.getString("receita_modo_preparo"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return receita;
    }

}
