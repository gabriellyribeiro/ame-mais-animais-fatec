package util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class PoolConexao {

    private static PoolConexao dataSource;
    private final ComboPooledDataSource cpds;

    private PoolConexao() throws PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://mysql.jlrv.com.br:3306/amemaisanimais");
        cpds.setUser("amemaisanimais");
        cpds.setPassword("amemaisanimais3");

        cpds.setInitialPoolSize(5);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(10);
        cpds.setMaxStatements(0);
        cpds.setMaxIdleTimeExcessConnections(150);
        cpds.setMaxIdleTime(1800);
        cpds.setUnreturnedConnectionTimeout(1800);
    }

    public static PoolConexao getInstance() {
        try {
            if (dataSource == null) {
                dataSource = new PoolConexao();
            }
        } catch (PropertyVetoException e) {
            JOptionPane.showMessageDialog(null, "Erro ao configurar conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return dataSource;
    }

    public Connection getConexao() throws SQLException {
        return this.cpds.getConnection();
    }

    public static void desconectar(Connection conexao) {
        try {
            if (conexao != null) {
                conexao.setAutoCommit(true);
                conexao.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão com o banco de dados: " + e.toString());
        }
    }

    public static void desconectar(Connection conexao, Statement stmt) {
        try {
            if (conexao != null) {
                desconectar(conexao);
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão com o banco de dados: " + e.toString());
        }
    }

    public static void desconectar(Connection conexao, Statement stmt, ResultSet rs) {
        try {
            if (conexao != null) {
                desconectar(conexao);
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão com o banco de dados: " + e.toString());
        }
    }

    public static void desconectar(Connection conexao, NamedParameterStatement stmt, ResultSet rs) {
        try {
            if (conexao != null) {
                desconectar(conexao);
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão com o banco de dados: " + e.toString());
        }
    }

    public static void desconectar(Connection conexao, NamedParameterStatement stmt) {
        try {
            if (conexao != null) {
                desconectar(conexao);
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão com o banco de dados: " + e.toString());
        }
    }

    public static void rollback(Connection conexao) {
        try {
            conexao.rollback();
        } catch (SQLException e) {
            System.out.println("Erro ao realizar rollback: " + e.toString());
        }
    }

}
