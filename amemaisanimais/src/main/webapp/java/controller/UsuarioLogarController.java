/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UsuarioDAO;
import java.io.IOException;
import java.sql.SQLException;
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
public class UsuarioLogarController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Usuario usuario = new Usuario();
            usuario.setUserName(request.getParameter("txtUsername"));
            usuario.setSenha(request.getParameter("pswPassword"));
            usuario = UsuarioDAO.getInstance().Logar(usuario);
            if (usuario != null) {
                request.getSession().setAttribute("usuario", usuario);
                response.sendRedirect(App.BASE_URL + "/private/");
            } else {
                request.setAttribute("erro", "Username e/ou senha inválidos!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ServletException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
