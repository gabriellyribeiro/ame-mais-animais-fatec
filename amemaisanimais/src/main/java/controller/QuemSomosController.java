/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.QuemSomosDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.QuemSomos;
import util.App;

/**
 *
 * @author Jenniferson
 */
public class QuemSomosController extends HttpServlet {

    private static final String INSERIR_OU_ALTERAR = "quemsomos.jsp";
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
            QuemSomos quemSomos = QuemSomosDAO.getInstance().buscaQuemSomos();
            request.setAttribute("quemSomos", quemSomos);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        RequestDispatcher view = request.getRequestDispatcher(redireciona);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        redireciona = null;
        acao = request.getParameter("acao");

        try {
            redireciona = INSERIR_OU_ALTERAR;
            QuemSomos quemSomos = new QuemSomos();
            quemSomos.setId(request.getParameter("txtID").equals("") || request.getParameter("txtID") == null ? 0 : Integer.parseInt(request.getParameter("txtID")));
            quemSomos.setDescricao(request.getParameter("txtDescricao"));

            if (quemSomos.getId() == 0) {
                QuemSomosDAO.getInstance().inserir(quemSomos);
                request.getSession().setAttribute("msg", new Object[]{true, "Quem somos cadastrado"});
            } else {
                QuemSomosDAO.getInstance().alterar(quemSomos);
                request.getSession().setAttribute("msg", new Object[]{true, "Quem somos alterado"});
            }

        } catch (SQLException e) {
            request.getSession().setAttribute("msg", new Object[]{true, e.getMessage()});
            request.setAttribute("exception", true);
        } finally {
            if (forward != null) {
                request.getRequestDispatcher(forward).forward(request, response);
            } else {
                response.sendRedirect(App.BASE_URL + "/private/quemsomos/");
            }
        }
    }

    public List<QuemSomos> Filtro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pagina = 1;
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }
        List<QuemSomos> list = null;

        if (acao == null) {
            list = QuemSomosDAO.getInstance().pesquisar((pagina - 1) * 20, 20, null);

        } else if (acao.equals("filtrar")) {
            QuemSomos qs = new QuemSomos();
            qs.setDescricao(request.getParameter("txtDescricao"));
            list = QuemSomosDAO.getInstance().pesquisar((pagina - 1) * 20, 20, qs);
        }
        return list;
    }
}
