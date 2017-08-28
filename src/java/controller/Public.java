/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PublicDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UserProfile;
import model.User;
import utilities.Encryption;

/**
 *
 * @author carlos
 */
public class Public extends HttpServlet {

    private static final String HOME = "view/public/home.jsp";
    private static final String PROFILE = "view/public/profile.jsp";
    private static final String ERROR404 = "view/public/error404.jsp";
    private static final String PATH = "/JSPsServletsTemplate/";
    private static final String NAME = "JSPs Servlets Template";
    private String forward;
    private PublicDAO dao;
    private Encryption encryption;

    public Public() {
        dao = PublicDAO.getInstance();
        encryption = new Encryption();
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("path", PATH);
        request.setAttribute("name", NAME);
        if (request.getParameter("profile") != null && request.getParameter("profile").length() <= 25) {
            UserProfile profile = dao.profile(request.getParameter("profile"));
            if (profile.getId() > 0) {
                request.setAttribute("title", profile.getName());
                forward = PROFILE;
            } else {
                forward = ERROR404;
            }
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (request.getSession().getAttribute("s_id") == null) {
            request.setAttribute("title", "Inicio");
            RequestDispatcher view = request.getRequestDispatcher(HOME);
            view.forward(request, response);
        } else {
            response.sendRedirect("Session?page=dashboard");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String form = request.getParameter("form") != null ? request.getParameter("form") : "";
        switch (form) {
            case "sign-in":
                UserProfile profile = dao.signIn(request.getParameter("email") != null ? request.getParameter("email") : "");
                if (profile.getId() > 0 && encryption.encryptPassword(request.getParameter("password") != null ? request.getParameter("password") : "").equals(profile.getPassword())) {
                    request.getSession().setAttribute("s_id", profile.getId());
                    request.getSession().setAttribute("s_email", profile.getEmail());
                    request.getSession().setAttribute("s_name", profile.getName());
                    request.getSession().setAttribute("s_user", profile.getUser());
                    response.sendRedirect("Session?page=dashboard");
                } else {
                    request.getSession().setAttribute("mensaje", "Correo o contraseña incorrectos");
                    response.sendRedirect(PATH);
                }
                break;
            case "sign-up":
                String password = request.getParameter("password") != null ? request.getParameter("password") : "";
                String rePassword = request.getParameter("re_password") != null ? request.getParameter("re_password") : "";
                String email = request.getParameter("email") != null ? request.getParameter("email") : "";
                if (password.equals(rePassword) && email.length() <= 50 && password.length() <= 50) {
                    User user = new User();
                    user.setEmail(email);
                    user.setPassword(encryption.encryptPassword(password));
                    int r = dao.signUp(user);
                    if (r == 1) {
                        request.getSession().setAttribute("mensaje", "El registro se realizo exitosamente");
                    } else {
                        request.getSession().setAttribute("mensaje", "Ocurrio un error al realizar el registro");
                    }
                } else {
                    request.getSession().setAttribute("mensaje", "Error en la validacíon de los datos");
                }
                response.sendRedirect(PATH);
                break;
            default:
                response.sendRedirect(PATH);
                break;
        }
    }

}
