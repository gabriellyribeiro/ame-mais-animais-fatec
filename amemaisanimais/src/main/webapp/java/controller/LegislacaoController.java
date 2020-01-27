/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.LegislacaoDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Legislacao;
import util.App;

/**
 *
 * @author Jenniferson
 */
public class LegislacaoController extends HttpServlet {

    private static final String INSERIR_OU_ALTERAR = "legislacao.jsp";
    private static final String LISTAR_OU_FILTRAR = "index.jsp";
    private String redireciona = null;
    private String forward = null;
    private Integer pagina = 0;
    private String acao = null;
    private String saida = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            redireciona = "";
            acao = request.getParameter("acao");

            if ((acao == null) || (acao.equals("filtrar"))) {
                redireciona = LISTAR_OU_FILTRAR;
                saida = request.getParameter("saida");

                List<Legislacao> list = Filtro(request, response);
                if (saida == null) {
                    request.setAttribute("listaLegislacao", list);
                } else {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(new Gson().toJson(list));
                    return;
                }
            } else if (acao.equals("existeLegislacao")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(LegislacaoDAO.getInstance().existeLegislacao(request.getParameter("txtID"), request.getParameter("txtTitulo")));
                return;
            } else if (acao.equals("excluir")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new Gson().toJson(LegislacaoDAO.getInstance().excluir(Integer.parseInt(request.getParameter("id")))));
                return;
            } else if (acao.equals("alterar")) {
                redireciona = INSERIR_OU_ALTERAR;
                Legislacao legislacao = LegislacaoDAO.getInstance().buscaLegislacao(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("legislacao", legislacao);
            } else {
                redireciona = INSERIR_OU_ALTERAR;
            }
        } catch (ServletException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
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
            if (acao != null && acao.equals("filtrar")) {
                forward = LISTAR_OU_FILTRAR;
                List<Legislacao> list = Filtro(request, response);
                request.setAttribute("listaLegislacao", list);
            } else {
                redireciona = INSERIR_OU_ALTERAR;
                Legislacao legislacao = new Legislacao();
                legislacao.setId(request.getParameter("txtID").equals("") || request.getParameter("txtID") == null ? 0 : Integer.parseInt(request.getParameter("txtID")));
                legislacao.setTitulo(request.getParameter("txtTitulo"));
                legislacao.setDescricao(request.getParameter("txtDescricao"));

                if (legislacao.getId() == 0) {
                    LegislacaoDAO.getInstance().inserir(legislacao);
                    request.getSession().setAttribute("msg", new Object[]{true, "Legislação cadastrada"});
                } else {
                    LegislacaoDAO.getInstance().alterar(legislacao);
                    request.getSession().setAttribute("msg", new Object[]{true, "Legislação alterada"});
                }
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("msg", new Object[]{true, e.getMessage()});
            request.setAttribute("exception", true);
        } finally {
            if (forward != null) {
                request.getRequestDispatcher(forward).forward(request, response);
            } else {
                response.sendRedirect(App.BASE_URL + "/private/legislacoes/");
            }
        }
    }

    public List<Legislacao> Filtro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pagina = 1;
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }
        List<Legislacao> list = null;

        if (acao == null) {
            list = LegislacaoDAO.getInstance().pesquisar((pagina - 1) * 20, 20, null);

        } else if (acao.equals("filtrar")) {
            Legislacao l = new Legislacao();
            l.setTitulo(request.getParameter("txtTitulo"));
            l.setDescricao(request.getParameter("txtDescricao"));
            list = LegislacaoDAO.getInstance().pesquisar((pagina - 1) * 20, 20, l);
        }
        return list;
    }
}
