/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.CategoriaDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Categoria;
import util.App;

/**
 *
 * @author Jenniferson
 */
public class CategoriaController extends HttpServlet {

    private static final String INSERIR_OU_ALTERAR = "categoria.jsp";
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

                List<Categoria> list = Filtro(request, response);
                if (saida == null) {
                    request.setAttribute("listaCategoria", list);
                } else {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(new Gson().toJson(list));
                    return;
                }
            } else if (acao.equals("existeCategoria")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(CategoriaDAO.getInstance().existeCategoria(request.getParameter("txtID"), request.getParameter("txtDescricao")));
                return;
            } else if (acao.equals("excluir")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new Gson().toJson(CategoriaDAO.getInstance().excluir(Integer.parseInt(request.getParameter("id")))));
                return;
            } else if (acao.equals("alterar")) {
                redireciona = INSERIR_OU_ALTERAR;
                Categoria categoria = CategoriaDAO.getInstance().buscaCategoria(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("categoria", categoria);
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
                List<Categoria> list = Filtro(request, response);
                request.setAttribute("listaCategoria", list);
            } else {
                redireciona = INSERIR_OU_ALTERAR;
                Categoria categoria = new Categoria();
                categoria.setId(request.getParameter("txtID").equals("") || request.getParameter("txtID") == null ? 0 : Integer.parseInt(request.getParameter("txtID")));
                categoria.setDescricao(request.getParameter("txtDescricao"));

                if (categoria.getId() == 0) {
                    CategoriaDAO.getInstance().inserir(categoria);
                    request.getSession().setAttribute("msg", new Object[]{true, "Categoria cadastrada"});
                } else {
                    CategoriaDAO.getInstance().alterar(categoria);
                    request.getSession().setAttribute("msg", new Object[]{true, "Categoria alterada"});
                }
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("msg", new Object[]{true, e.getMessage()});
            request.setAttribute("exception", true);
        } finally {
            if (forward != null) {
                request.getRequestDispatcher(forward).forward(request, response);
            } else {
                response.sendRedirect(App.BASE_URL + "/private/categorias/");
            }
        }
    }

    public List<Categoria> Filtro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pagina = 1;
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }
        List<Categoria> list = null;

        if (acao == null) {
            list = CategoriaDAO.getInstance().getListaCategorias((pagina - 1) * 20, 20, null);

        } else if (acao.equals("filtrar")) {
            Categoria c = new Categoria();
            c.setDescricao(request.getParameter("txtDescricao"));
            list = CategoriaDAO.getInstance().getListaCategorias((pagina - 1) * 20, 20, c);
        }
        return list;
    }
}
