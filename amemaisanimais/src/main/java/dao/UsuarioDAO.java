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
import model.Usuario;
import util.NamedParameterStatement;
import util.PoolConexao;

/**
 *
 * @author Jenniferson
 */
public class UsuarioDAO {

    private static UsuarioDAO instance;
    private Connection conexao;
    private Integer qtdRegistros;

    public static UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }

        return instance;
    }

    public Usuario Logar(Usuario usuario) throws SQLException {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;

        String sql = "select "
                + "u.id usuario_id, u.nome usuario_nome, u.username usuario_username, u.senha usuario_senha, "
                + "p.id perfil_id, p.descricao perfil_descricao "
                + "from tab_usuario u  "
                + "join tab_perfil p on p.id = u.perfil "
                + "where u.username = :username and senha = :senha ";

        try {
            conexao = PoolConexao.getInstance().getConexao();
            stmt = new NamedParameterStatement(conexao, sql);
            stmt.setString("username", usuario.getUserName());
            stmt.setString("senha", usuario.getSenha());
            rs = stmt.executeQuery();
            if (rs.next()) {
                usuario.setId(rs.getInt("usuario_id"));
                usuario.setNome(rs.getString("usuario_nome"));
                usuario.setUserName(rs.getString("usuario_username"));
                usuario.getPerfil().setId(rs.getInt("perfil_id"));
                usuario.getPerfil().setDescricao(rs.getString("perfil_descricao"));
            } else {
                usuario = null;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            PoolConexao.desconectar(conexao, stmt, rs);
        }

        return usuario;

    }

    public Usuario inserir(Usuario usuario) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "INSERT INTO tab_usuario (nome, username, senha, perfil) VALUES (? ,?, ?, ?)";

        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getUserName());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getPerfil().getId());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getInt(1));
            }
            conexao.commit();

        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            throw new SQLException(e);
        } finally {
            PoolConexao.desconectar(conexao, stmt, rs);
        }

        return usuario;
    }

    public Usuario alterar(Usuario usuario) throws SQLException {
        NamedParameterStatement stmt = null;
        String sql = "update tab_usuario set nome = :nome, username = :username, senha = :senha, perfil = :perfil where id = :id";
        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("nome", usuario.getNome());
            stmt.setString("username", usuario.getUserName());
            stmt.setString("senha", usuario.getSenha());
            stmt.setInt("perfil", usuario.getPerfil().getId());
            stmt.setInt("id", usuario.getId());
            stmt.executeUpdate();
            conexao.commit();
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(conexao, stmt);
        }

        return usuario;
    }

    public boolean excluir(int idUsuario) throws SQLException {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        boolean excluido = false;

        String sql = "DELETE FROM tab_usuario WHERE id = :id ";

        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setInt("id", idUsuario);
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

    public List<Usuario> getListaUsuarios(int offset, int qtdRegistros, Usuario usuario) {
        NamedParameterStatement ps = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        List<Usuario> usuarioList = new ArrayList<Usuario>();

        String sql = "SELECT SQL_CALC_FOUND_ROWS u.id, u.nome, u.username, "
                + "p.id perfil_id, p.descricao perfil_descricao "
                + "FROM tab_usuario u "
                + "JOIN tab_perfil p ON p.id = u.perfil "
                + "WHERE 1 = 1 ";

        if (usuario != null) {
            sql += usuario.getNome().trim().equals("") ? "" : " AND u.nome LIKE :nome ";
            sql += usuario.getUserName().trim().equals("") ? "" : " AND u.username LIKE :username";
            sql += usuario.getPerfil().getId() == 0 ? "" : " AND u.perfil = :perfil";
        }

        sql += " limit :offset, :qtdRegistros";

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            ps = new NamedParameterStatement(this.conexao, sql);
            ps.setInt("offset", offset);
            ps.setInt("qtdRegistros", qtdRegistros);

            if (usuario != null) {

                if (!usuario.getNome().trim().equals("")) {
                    ps.setString("nome", usuario.getNome() + "%");
                }

                if (!usuario.getUserName().trim().equals("")) {
                    ps.setString("username", usuario.getUserName() + "%");
                }

                if (usuario.getPerfil().getId() != 0) {
                    ps.setInt("perfil", usuario.getPerfil().getId());
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setUserName(rs.getString("username"));
                u.getPerfil().setId(rs.getInt("perfil_id"));
                u.getPerfil().setDescricao(rs.getString("perfil_descricao"));
                usuarioList.add(u);
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

        return usuarioList;
    }

    public String existeUsuario(String codigo, String username) {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Usuario user = null;

        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            String sql = " select id, nome, username from tab_usuario where username = :username ";
            sql += !codigo.equals("0") && !codigo.equals("") ? " and id != :id " : "";
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("username", username);
            if (!codigo.equals("0") && !codigo.equals("")) {
                stmt.setString("id", codigo);
            }
            rs = stmt.executeQuery();

            if (rs.next()) {
                user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNome(rs.getString("nome"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return new Gson().toJson(user);
    }

    public Usuario buscaUsuario(int id) {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = new Usuario();

        try {
            String sql = "select "
                    + " u.id usuario_id, u.nome usuario_nome, u.username usuario_username, u.senha usuario_senha, "
                    + " p.id perfil_id, p.descricao perfil_descricao "
                    + " from tab_usuario u "
                    + " join tab_perfil p on p.id = u.perfil "
                    + " where u.id = :id ";

            this.conexao = PoolConexao.getInstance().getConexao();
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setInt("id", id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                usuario.setId(rs.getInt("usuario_id"));
                usuario.setNome(rs.getString("usuario_nome"));
                usuario.setUserName(rs.getString("usuario_username"));
                usuario.setSenha(rs.getString("usuario_senha"));
                usuario.getPerfil().setId(rs.getInt("perfil_id"));
                usuario.getPerfil().setDescricao(rs.getString("perfil_descricao"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }

        return usuario;
    }

}
