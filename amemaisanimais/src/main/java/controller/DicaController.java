/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.DicaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Dica;
import util.App;

/**
 *
 * @author Jenniferson
 */
public class DicaController extends HttpServlet {

    private static final String INSERIR_OU_ALTERAR = "dica.jsp";
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

                List<Dica> list = Filtro(request, response);
                if (saida == null) {
                    request.setAttribute("listaDica", list);
                } else {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(new Gson().toJson(list));
                    return;
                }
            } else if (acao.equals("existeDica")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(DicaDAO.getInstance().existeDica(request.getParameter("txtID"), request.getParameter("txtTitulo")));
                return;
            } else if (acao.equals("excluir")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new Gson().toJson(DicaDAO.getInstance().excluir(Integer.parseInt(request.getParameter("id")))));
                return;
            } else if (acao.equals("alterar")) {
                redireciona = INSERIR_OU_ALTERAR;
                Dica dica = DicaDAO.getInstance().buscaDica(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("dica", dica);
            } else if (acao.equals("logout")) {
                request.getSession().invalidate();
                response.sendRedirect(App.BASE_URL);
                return;
            } else {
                redireciona = INSERIR_OU_ALTERAR;
            }
        } catch (ServletException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
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
                List<Dica> list = Filtro(request, response);
                request.setAttribute("listaDica", list);
            } else {
                redireciona = INSERIR_OU_ALTERAR;
                Dica dica = new Dica();
                dica.setId(request.getParameter("txtID").equals("") || request.getParameter("txtID") == null ? 0 : Integer.parseInt(request.getParameter("txtID")));
                dica.setTitulo(request.getParameter("txtTitulo"));
                dica.setDescricao(request.getParameter("txtDescricao"));
                dica.getCategoria().setId(Integer.parseInt(request.getParameter("selCategoria")));

                if (dica.getId() == 0) {
                    DicaDAO.getInstance().inserir(dica);
                    request.getSession().setAttribute("msg", new Object[]{true, "Dica cadastrada"});
                } else {
                    DicaDAO.getInstance().alterar(dica);
                    request.getSession().setAttribute("msg", new Object[]{true, "Dica alterada"});
                }
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("msg", new Object[]{true, e.getMessage()});
            request.setAttribute("exception", true);
        } finally {
            if (forward != null) {
                request.getRequestDispatcher(forward).forward(request, response);
            } else {
                response.sendRedirect(App.BASE_URL + "/private/dicas/");
            }
        }
    }

    public List<Dica> Filtro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pagina = 1;
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }
        List<Dica> list = null;

        if (acao == null) {
            list = DicaDAO.getInstance().getListaDicas((pagina - 1) * 20, 20, null);

        } else if (acao.equals("filtrar")) {
            Dica d = new Dica();
            d.setTitulo(request.getParameter("txtTitulo"));
            d.getCategoria().setId(Integer.parseInt(request.getParameter("selCategoria")));
            list = DicaDAO.getInstance().getListaDicas((pagina - 1) * 20, 20, d);
        }
        return list;
    }
}
