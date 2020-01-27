/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.PerfilDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Perfil;
import util.App;

/**
 *
 * @author Jenniferson
 */
public class PerfilController extends HttpServlet {

    private static final String INSERIR_OU_ALTERAR = "perfil.jsp";
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

                List<Perfil> list = Filtro(request, response);
                if (saida == null) {
                    request.setAttribute("listaPerfil", list);
                } else {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(new Gson().toJson(list));
                    return;
                }
            } else if (acao.equals("existePerfil")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(PerfilDAO.getInstance().existePerfil(request.getParameter("txtID"), request.getParameter("txtDescricao")));
                return;
            } else if (acao.equals("alterar")) {
                redireciona = INSERIR_OU_ALTERAR;
                Perfil perfil = PerfilDAO.getInstance().buscaPerfil(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("perfil", perfil);
            } else {
                redireciona = INSERIR_OU_ALTERAR;
            }
        } catch (ServletException e) {
            System.err.println(e.getMessage());
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
            if (acao != null && acao.equals("filtrar")) {
                forward = LISTAR_OU_FILTRAR;
                List<Perfil> list = Filtro(request, response);
                request.setAttribute("listaPerfil", list);
            } else {
                redireciona = INSERIR_OU_ALTERAR;
                Perfil perfil = new Perfil();
                perfil.setId(request.getParameter("txtID").equals("") || request.getParameter("txtID") == null ? 0 : Integer.parseInt(request.getParameter("txtID")));
                perfil.setDescricao(request.getParameter("txtDescricao"));

                if (perfil.getId() == 0) {
                    PerfilDAO.getInstance().inserir(perfil);
                    request.getSession().setAttribute("msg", new Object[]{true, "Perfil cadastrado"});
                } else {
                    PerfilDAO.getInstance().alterar(perfil);
                    request.getSession().setAttribute("msg", new Object[]{true, "Perfil alterado"});
                }
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("msg", new Object[]{true, e.getMessage()});
            request.setAttribute("exception", true);
        } finally {
            if (forward != null) {
                request.getRequestDispatcher(forward).forward(request, response);
            } else {
                response.sendRedirect(App.BASE_URL + "/private/perfis/");
            }
        }
    }

    public List<Perfil> Filtro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pagina = 1;
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }
        List<Perfil> list = null;

        if (acao == null) {
            list = PerfilDAO.getInstance().pesquisar((pagina - 1) * 20, 20, null);

        } else if (acao.equals("filtrar")) {
            Perfil p = new Perfil();
            p.setDescricao(request.getParameter("txtDescricao"));
            list = PerfilDAO.getInstance().pesquisar((pagina - 1) * 20, 20, p);
        }
        return list;
    }
}
