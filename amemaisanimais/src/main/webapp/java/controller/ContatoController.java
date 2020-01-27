/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ContatoDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Contato;
import util.App;

/**
 *
 * @author Jenniferson
 */
public class ContatoController extends HttpServlet {

    private static final String INSERIR_OU_ALTERAR = "contato.jsp";
    private String redireciona = null;
    private Integer pagina = 0;
    private String acao = null;
    private String forward = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            redireciona = INSERIR_OU_ALTERAR;
            Contato contato = ContatoDAO.getInstance().buscaContato();
            request.setAttribute("contato", contato);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        RequestDispatcher view = request.getRequestDispatcher(redireciona);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        redireciona = null;
        acao = request.getParameter("acao");

        try {
            redireciona = INSERIR_OU_ALTERAR;
            Contato contato = new Contato();
            contato.setId(request.getParameter("txtID").equals("") || request.getParameter("txtID") == null ? 0 : Integer.parseInt(request.getParameter("txtID")));
            contato.setEmail(request.getParameter("emaEmail"));
            contato.setTelefone(request.getParameter("telTelefone").replaceAll("\\D",""));
            contato.setCelular(request.getParameter("telCelular").replaceAll("\\D",""));
            contato.getEndereco().setCep(request.getParameter("txtCEP").replaceAll("\\D", ""));
            contato.getEndereco().setLogradouro(request.getParameter("txtLogradouro"));
            contato.getEndereco().setNumero(Integer.parseInt(request.getParameter("txtNumero")));
            contato.getEndereco().setCidade(request.getParameter("txtCidade"));
            contato.getEndereco().setBairro(request.getParameter("txtBairro"));
            contato.getEndereco().setUf(request.getParameter("txtUF"));
            
            if (contato.getId() == 0) {
                ContatoDAO.getInstance().inserir(contato);
                request.getSession().setAttribute("msg", new Object[]{true, "Contato cadastrado"});
            } else {
                ContatoDAO.getInstance().alterar(contato);
                request.getSession().setAttribute("msg", new Object[]{true, "Contato alterado"});
            }

        } catch (SQLException e) {
            request.getSession().setAttribute("msg", new Object[]{true, e.getMessage()});
            request.setAttribute("exception", true);
        } finally {
            if (forward != null) {
                request.getRequestDispatcher(forward).forward(request, response);
            } else {
                response.sendRedirect(App.BASE_URL + "/private/contatos/");
            }
        }
    }
    
    public List<Contato> Filtro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pagina = 1;
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }
        List<Contato> list = null;

        if (acao == null) {
            list = ContatoDAO.getInstance().pesquisar((pagina - 1) * 20, 20, null);

        } else if (acao.equals("filtrar")) {
            Contato c = new Contato();
            c.setEmail(request.getParameter("emaEmail"));
            list = ContatoDAO.getInstance().pesquisar((pagina - 1) * 20, 20, c);
        }
        return list;
    }

}
