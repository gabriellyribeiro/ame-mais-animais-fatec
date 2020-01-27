/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.UsuarioDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;
import util.App;

/**
 *
 * @author Jenniferson
 */
public class UsuarioController extends HttpServlet {

    private static final String INSERIR_OU_ALTERAR = "usuario.jsp";
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

                List<Usuario> list = Filtro(request, response);
                if (saida == null) {
                    request.setAttribute("listaUsuario", list);
                } else {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(new Gson().toJson(list));
                    return;
                }
            } else if (acao.equals("existeUsuario")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(UsuarioDAO.getInstance().existeUsuario(request.getParameter("txtID"), request.getParameter("txtUsername")));
                return;
            } else if (acao.equals("excluir")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new Gson().toJson(UsuarioDAO.getInstance().excluir(Integer.parseInt(request.getParameter("id")))));
                return;
            } else if (acao.equals("alterar")) {
                redireciona = INSERIR_OU_ALTERAR;
                Usuario usuario = UsuarioDAO.getInstance().buscaUsuario(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("user", usuario);
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
                List<Usuario> list = Filtro(request, response);
                request.setAttribute("listaUsuario", list);
            } else {
                redireciona = INSERIR_OU_ALTERAR;
                Usuario usuario = new Usuario();
                usuario.setId(request.getParameter("txtID").equals("") || request.getParameter("txtID") == null ? 0 : Integer.parseInt(request.getParameter("txtID")));
                usuario.setNome(request.getParameter("txtNome"));
                usuario.setUserName(request.getParameter("txtUsername"));
                usuario.setSenha(request.getParameter("pswSenha"));
                usuario.getPerfil().setId(Integer.parseInt(request.getParameter("selPerfil")));

                if (usuario.getId() == 0) {
                    UsuarioDAO.getInstance().inserir(usuario);
                    request.getSession().setAttribute("msg", new Object[]{true, "Usuário cadastrado"});
                } else {
                    UsuarioDAO.getInstance().alterar(usuario);
                    request.getSession().setAttribute("msg", new Object[]{true, "Usuário alterado"});
                }
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("msg", new Object[]{true, e.getMessage()});
            request.setAttribute("exception", true);
        } finally {
            if (forward != null) {
                request.getRequestDispatcher(forward).forward(request, response);
            } else {
                response.sendRedirect(App.BASE_URL + "/private/usuarios/");
            }
        }
    }

    public List<Usuario> Filtro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pagina = 1;
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }
        List<Usuario> list = null;

        if (acao == null) {
            list = UsuarioDAO.getInstance().getListaUsuarios((pagina - 1) * 20, 20, null);

        } else if (acao.equals("filtrar")) {
            Usuario u = new Usuario();
            u.setNome(request.getParameter("txtNome"));
            u.setUserName(request.getParameter("txtUsername"));
            u.getPerfil().setId(Integer.parseInt(request.getParameter("selPerfil")));
            list = UsuarioDAO.getInstance().getListaUsuarios((pagina - 1) * 20, 20, u);
        }
        return list;
    }
}
