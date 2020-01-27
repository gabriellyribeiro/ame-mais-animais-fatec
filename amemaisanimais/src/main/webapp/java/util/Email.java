package util;

import model.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import java.beans.PropertyVetoException;

public class Email {

    private static Email instance;
    private Connection conexao;

    public static Email getInstance() {
        if (instance == null) {
            instance = new Email();
        }

        return instance;
    }

    public Boolean sendEmail(Usuario user, String Assunto) throws EmailException, SQLException {
        UUID id = UUID.randomUUID();
        //user.setToken(id.toString());
        Boolean retorno = false;

        //try {
            String sql = "SELECT * FROM vVerificaEmail WHERE bloqueado = :bloqueado AND excluido = :excluido AND email = :email";
            //this.conexao = PoolConexao.getInstance().getConexao();
            NamedParameterStatement ps = new NamedParameterStatement(this.conexao, sql);
            ps.setInt("bloqueado", 0);
            ps.setInt("excluido", 0);
            //ps.setString("email", user.getEmail());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                //user.setIdUsuario(rs.getInt("id"));
                user.setNome(rs.getString("nome"));
                SimpleEmail email = new SimpleEmail();
                email.setHostName("smtp.gmail.com");
                email.setSmtpPort(465);
                //email.addTo(user.getEmail(), user.getNome());
                email.setFrom("jenniferson27@gmail.com", "Jenniferson");
                email.setSubject(Assunto);
                /*email.setMsg("teste de envio de e-mail teste de envio de e-mail com a biblioteca commons-email, activion, mail"
                        + " " + App.BASE_URL + "/alterarsenha.jsp?token=" + id);*/
                email.setSSL(true);
                email.setAuthentication("jenniferson27@gmail.com", "rodrigues27");
                email.send();
                //DaoUsuario daoUsuario = new DaoUsuario();
                //daoUsuario.inserirToken(user);
                retorno = true;
            }
        //} /*catch (PropertyVetoException | SQLException | EmailException ex)*/ {
            //new Log().Gravar(ex.toString());
        //}
        
        return retorno;
    }
}
