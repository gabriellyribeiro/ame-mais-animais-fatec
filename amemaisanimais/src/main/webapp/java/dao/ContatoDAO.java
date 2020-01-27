/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.mysql.jdbc.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Contato;
import util.Mascaras;
import util.NamedParameterStatement;
import util.PoolConexao;

/**
 *
 * @author Jenniferson
 */
public class ContatoDAO {
    
    private static ContatoDAO instance;
    private Connection conexao;
    private Integer qtdRegistros;
    
    public static ContatoDAO getInstance() {
        if (instance == null) {
            instance = new ContatoDAO();
        }
        
        return instance;
    }
    
    public Contato inserir(Contato contato) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String sql = "INSERT INTO tab_contato (email, telefone, celular) VALUES (?,?,?)";
        
        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, contato.getEmail());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getCelular());
            stmt.executeUpdate();
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                contato.setId(rs.getInt(1));
                EnderecoDAO.getInstance().inserir(contato, conexao);
            }
            conexao.commit();
            
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            throw new SQLException(e);
        } finally {
            PoolConexao.desconectar(conexao, stmt, rs);
        }
        
        return contato;
    }
    
    public Contato alterar(Contato contato) throws SQLException {
        NamedParameterStatement stmt = null;
        String sql = "UPDATE tab_contato set email = :email, telefone = :telefone, celular = :celular WHERE id = :id";
        try {
            conexao = PoolConexao.getInstance().getConexao();
            conexao.setAutoCommit(false);
            stmt = new NamedParameterStatement(this.conexao, sql);
            stmt.setString("email", contato.getEmail());
            stmt.setString("telefone", contato.getTelefone());
            stmt.setString("celular", contato.getCelular());
            stmt.setInt("id", contato.getId());
            stmt.executeUpdate();
            
            EnderecoDAO.getInstance().alterar(contato, conexao);
            
            conexao.commit();
        } catch (SQLException e) {
            PoolConexao.rollback(conexao);
            System.out.println(e.getMessage());
        } finally {
            PoolConexao.desconectar(conexao, stmt);
        }
        
        return contato;
    }
    
    public boolean excluir(int id) throws SQLException {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        boolean excluido = false;
        
        String sql = "DELETE FROM tab_contato WHERE id = :id ";
        
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
    
    public List<Contato> pesquisar(int offset, int qtdRegistros, Contato contato) {
        NamedParameterStatement ps = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Contato> contatos = new ArrayList<Contato>();
        
        String sql = "SELECT SQL_CALC_FOUND_ROWS "
                + " c.id contato_id, c.email contato_email, "
                + " c.telefone contato_telefone, c.celular contato_celular "
                + " FROM "
                + " tab_contato c "
                + " WHERE 1 = 1 "
                + " limit :offset, :qtdRegistros";
        
        try {
            this.conexao = PoolConexao.getInstance().getConexao();
            ps = new NamedParameterStatement(this.conexao, sql);
            ps.setInt("offset", offset);
            ps.setInt("qtdRegistros", qtdRegistros);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Contato c = new Contato();
                c.setId(rs.getInt("contato_id"));
                c.setEmail(rs.getString("contato_email"));
                c.setTelefone(rs.getString("contato_telefone"));
                c.setCelular(rs.getString("contato_celular"));
                contatos.add(c);
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
        
        return contatos;
    }
    
    public Contato buscaContato() {
        NamedParameterStatement stmt = null;
        ResultSet rs = null;
        Contato contato = new Contato();
        
        try {
            String sql = "SELECT "
                    + " c.id contato_id, c.email contato_email, c.telefone contato_telefone, c.celular contato_celular,"
                    + " ce.cep contato_endereco_cep, ce.logradouro contato_endereco_logradouro, ce.numero contato_endereco_numero,"
                    + " ce.bairro contato_endereco_bairro, ce.cidade contato_endereco_cidade, ce.uf contato_endereco_uf "
                    + "FROM "
                    + " tab_contato c "
                    + " join tab_contato_endereco ce on ce.contato = c.id "
                    + "limit 1 ";
            this.conexao = PoolConexao.getInstance().getConexao();
            stmt = new NamedParameterStatement(this.conexao, sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                contato.setId(rs.getInt("contato_id"));
                contato.setEmail(rs.getString("contato_email"));
                contato.setTelefone(Mascaras.formatString(rs.getString("contato_telefone"), "(##)####-####"));
                contato.setCelular(Mascaras.formatString(rs.getString("contato_celular"), "(##)#####-####"));
                contato.getEndereco().setCep(Mascaras.formatString(rs.getString("contato_endereco_cep"), "#####-###"));
                contato.getEndereco().setLogradouro(rs.getString("contato_endereco_logradouro"));
                contato.getEndereco().setNumero(rs.getInt("contato_endereco_numero"));
                contato.getEndereco().setBairro(rs.getString("contato_endereco_bairro"));
                contato.getEndereco().setCidade(rs.getString("contato_endereco_cidade"));
                contato.getEndereco().setUf(rs.getString("contato_endereco_uf"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            PoolConexao.desconectar(this.conexao, stmt, rs);
        }
        
        return contato;
    }
    
}
