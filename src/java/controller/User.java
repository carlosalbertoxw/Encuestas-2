/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UserProfile;
import utilities.Encryption;

/**
 *
 * @author carlos
 */
public class User extends HttpServlet {

    private static final String EDITPROFILE = "view/user/editProfile.jsp";
    private static final String CHANGEEMAIL = "view/user/changeEmail.jsp";
    private static final String CHANGEPASSWORD = "view/user/changePassword.jsp";
    private static final String DELETEACCOUNT = "view/user/deleteAccount.jsp";
    private static final String ERROR404 = "view/public/error404.jsp";
    private static final String PATH = "/JSPsServletsTemplate/";
    private static final String NAME = "JSPs Servlets Template";
    private String forward;
    private UserDAO dao;
    private Encryption encryption;

    public User() {
        dao = UserDAO.getInstance();
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
        if (request.getSession().getAttribute("s_id") != null) {
            String page = request.getParameter("page") != null ? request.getParameter("page") : "";
            if (page.equals("close-session")) {
                request.getSession().setAttribute("s_id", null);
                request.getSession().setAttribute("s_email", null);
                request.getSession().setAttribute("s_name", null);
                request.getSession().setAttribute("s_user", null);
                response.sendRedirect(PATH);
            } else {
                request.setAttribute("path", PATH);
                request.setAttribute("name", NAME);
                switch (page) {
                    case "edit-profile":
                        request.setAttribute("title", "Editar perfil");
                        forward = EDITPROFILE;
                        break;
                    case "change-email":
                        request.setAttribute("title", "Cambiar correo electrónico");
                        forward = CHANGEEMAIL;
                        break;
                    case "change-password":
                        request.setAttribute("title", "Cambiar contraseña");
                        forward = CHANGEPASSWORD;
                        break;
                    case "delete-account":
                        request.setAttribute("title", "Eliminar cuenta");
                        forward = DELETEACCOUNT;
                        break;
                    default:
                        forward = ERROR404;
                        break;
                }
                RequestDispatcher view = request.getRequestDispatcher(forward);
                view.forward(request, response);
            }
        } else {
            response.sendRedirect(PATH);
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
        if (request.getSession().getAttribute("s_id") != null) {
            String form = request.getParameter("form") != null ? request.getParameter("form") : "";
            UserProfile profile = new UserProfile();
            String name = request.getParameter("name") != null ? request.getParameter("name") : "";
            String user = request.getParameter("user") != null ? request.getParameter("user") : "";
            String email = request.getParameter("email") != null ? request.getParameter("email") : "";
            String password = request.getParameter("password") != null ? request.getParameter("password") : "";
            String newPassword = request.getParameter("new_password") != null ? request.getParameter("new_password") : "";
            String reNewPassword = request.getParameter("re_new_password") != null ? request.getParameter("re_new_password") : "";
            switch (form) {
                case "edit-profile":
                    if (name.length() <= 50 && user.length() <= 25) {
                        profile.setName(name);
                        profile.setUser(user);
                        profile.setId(Integer.parseInt(request.getSession().getAttribute("s_id").toString()));
                        int r = dao.editProfile(profile);
                        if (r == 1) {
                            request.getSession().setAttribute("mensaje", "Los datos se actualizaron exitosamente");
                            request.getSession().setAttribute("s_name", profile.getName());
                            request.getSession().setAttribute("s_user", profile.getUser());
                        } else {
                            request.getSession().setAttribute("mensaje", "Ocurrio un error al actualizar los datos");
                        }
                    } else {
                        request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
                    }
                    response.sendRedirect("Session?page=dashboard");
                    break;
                case "change-email":
                    if (email.length() <= 50 && password.length() <= 50) {
                        profile.setEmail(email);
                        profile.setId(Integer.parseInt(request.getSession().getAttribute("s_id").toString()));
                        if (dao.getUserPassword(profile.getId()).equals(encryption.encryptPassword(password))) {
                            int r = dao.changeEmail(profile);
                            if (r == 1) {
                                request.getSession().setAttribute("mensaje", "Los datos se actualizaron exitosamente");
                                request.getSession().setAttribute("s_email", profile.getEmail());
                            } else {
                                request.getSession().setAttribute("mensaje", "Ocurrio un error al actualizar los datos");
                            }
                        } else {
                            request.getSession().setAttribute("mensaje", "La contraseña es incorrecta");
                        }
                    } else {
                        request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
                    }
                    response.sendRedirect("Session?page=dashboard");
                    break;
                case "change-password":
                    if (password.length() <= 50 && newPassword.length() <= 50 && reNewPassword.length() <= 50 && newPassword.equals(reNewPassword)) {
                        profile.setPassword(encryption.encryptPassword(newPassword));
                        profile.setId(Integer.parseInt(request.getSession().getAttribute("s_id").toString()));
                        if (dao.getUserPassword(profile.getId()).equals(encryption.encryptPassword(password))) {
                            int r = dao.changePassword(profile);
                            if (r == 1) {
                                request.getSession().setAttribute("mensaje", "Los datos se actualizaron exitosamente");
                            } else {
                                request.getSession().setAttribute("mensaje", "Ocurrio un error al actualizar los datos");
                            }
                        } else {
                            request.getSession().setAttribute("mensaje", "La contraseña es incorrecta");
                        }
                    } else {
                        request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
                    }
                    response.sendRedirect("Session?page=dashboard");
                    break;
                case "delete-account":
                    if (password.length() <= 50) {
                        profile.setId(Integer.parseInt(request.getSession().getAttribute("s_id").toString()));
                        if (dao.getUserPassword(profile.getId()).equals(encryption.encryptPassword(password))) {
                            int r = dao.deleteAccount(profile);
                            if (r == 1) {
                                request.getSession().setAttribute("s_id", null);
                                request.getSession().setAttribute("s_email", null);
                                request.getSession().setAttribute("s_name", null);
                                request.getSession().setAttribute("s_user", null);
                                request.getSession().setAttribute("mensaje", "La cuenta se elimino exitosamente");
                            } else {
                                request.getSession().setAttribute("mensaje", "Ocurrio un error al eliminar la cuenta");
                            }
                        } else {
                            request.getSession().setAttribute("mensaje", "La contraseña es incorrecta");
                        }
                    } else {
                        request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
                    }
                    response.sendRedirect(PATH);
                    break;
                default:
                    response.sendRedirect(PATH);
                    break;
            }
        } else {
            response.sendRedirect(PATH);
        }
    }

}
