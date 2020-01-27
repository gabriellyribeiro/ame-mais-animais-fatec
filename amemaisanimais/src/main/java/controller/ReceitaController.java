/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.ReceitaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Receita;
import util.App;

/**
 *
 * @author Jenniferson
 */
public class ReceitaController extends HttpServlet {

    private static final String INSERIR_OU_ALTERAR = "receita.jsp";
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

                List<Receita> list = Filtro(request, response);
                if (saida == null) {
                    request.setAttribute("listaReceita", list);
                } else {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(new Gson().toJson(list));
                    return;
                }
            } else if (acao.equals("existeReceita")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(ReceitaDAO.getInstance().existeReceita(request.getParameter("txtID"), request.getParameter("txtTitulo")));
                return;
            } else if (acao.equals("excluir")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new Gson().toJson(ReceitaDAO.getInstance().excluir(Integer.parseInt(request.getParameter("id")))));
                return;
            } else if (acao.equals("alterar")) {
                redireciona = INSERIR_OU_ALTERAR;
                Receita receita = ReceitaDAO.getInstance().buscaReceita(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("receita", receita);
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
                List<Receita> list = Filtro(request, response);
                request.setAttribute("listaReceita", list);
            } else {
                redireciona = INSERIR_OU_ALTERAR;
                Receita receita = new Receita();
                receita.setId(request.getParameter("txtID").equals("") || request.getParameter("txtID") == null ? 0 : Integer.parseInt(request.getParameter("txtID")));
                receita.setTitulo(request.getParameter("txtTitulo"));
                receita.setIngrediente(request.getParameter("txtIngrediente"));
                receita.setModoPreparo(request.getParameter("txtModoPreparo"));

                if (receita.getId() == 0) {
                    ReceitaDAO.getInstance().inserir(receita);
                    request.getSession().setAttribute("msg", new Object[]{true, "Receita cadastrada"});
                } else {
                    ReceitaDAO.getInstance().alterar(receita);
                    request.getSession().setAttribute("msg", new Object[]{true, "Receita alterada"});
                }
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("msg", new Object[]{true, e.getMessage()});
            request.setAttribute("exception", true);
        } finally {
            if (forward != null) {
                request.getRequestDispatcher(forward).forward(request, response);
            } else {
                response.sendRedirect(App.BASE_URL + "/private/receitas/");
            }
        }
    }

    public List<Receita> Filtro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pagina = 1;
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }
        List<Receita> list = null;

        if (acao == null) {
            list = ReceitaDAO.getInstance().pesquisar((pagina - 1) * 20, 20, null);

        } else if (acao.equals("filtrar")) {
            Receita r = new Receita();
            r.setTitulo(request.getParameter("txtTitulo"));
            list = ReceitaDAO.getInstance().pesquisar((pagina - 1) * 20, 20, r);
        }
        return list;
    }

}
