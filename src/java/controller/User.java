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
import model.UserProfileMDL;
import model.UserMDL;
import utilities.Encryption;

/**
 *
 * @author carlos
 */
public class User extends HttpServlet {

    private static final String HOME = "view/public/user/home.jsp";
    private static final String PROFILE = "view/public/user/profile.jsp";
    private static final String EDITPROFILE = "view/session/user/editProfile.jsp";
    private static final String CHANGEEMAIL = "view/session/user/changeEmail.jsp";
    private static final String CHANGEUSER = "view/session/user/changeUser.jsp";
    private static final String CHANGEPASSWORD = "view/session/user/changePassword.jsp";
    private static final String DELETEACCOUNT = "view/session/user/deleteAccount.jsp";
    private static final String ERROR404 = "view/public/error404.jsp";
    private static final String PATH = "/Encuestas/";
    private static final String NAME = "Encuestas";
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
        String page = request.getParameter("page") != null ? request.getParameter("page") : "";
        String profile = request.getParameter("profile") != null ? request.getParameter("profile") : "";
        request.setAttribute("path", PATH);
        request.setAttribute("name", NAME);
        if (request.getSession().getAttribute("s_id") == null && profile.equals("")) {
            switch (page) {
                case "":
                    this.home(request, response);
                    break;
                default:
                    this.error404(request, response);
                    break;
            }
        } else if (request.getSession().getAttribute("s_id") != null && profile.equals("")) {
            switch (page) {
                case "close-session":
                    this.closeSession(request, response);
                    break;
                case "edit-profile":
                    this.editProfile(request, response);
                    break;
                case "change-user":
                    this.changeUser(request, response);
                    break;
                case "change-email":
                    this.changeEmail(request, response);
                    break;
                case "change-password":
                    this.changePassword(request, response);
                    break;
                case "delete-account":
                    this.deleteAccount(request, response);
                    break;
                case "":
                    response.sendRedirect(PATH + "Poll?page=dashboard");
                    break;
                default:
                    this.error404(request, response);
                    break;
            }
        } else {
            this.profile(request, response);
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
        String page = request.getParameter("form") != null ? request.getParameter("form") : "";
        if (request.getSession().getAttribute("s_id") == null) {
            switch (page) {
                case "sign-up":
                    this.signUp(request, response);
                    break;
                case "sign-in":
                    this.signIn(request, response);
                    break;
                default:
                    response.sendRedirect(PATH);
                    break;
            }
        } else {
            switch (page) {
                case "edit-profile":
                    this.editProfileForm(request, response);
                    break;
                case "change-user":
                    this.changeUserForm(request, response);
                    break;
                case "change-email":
                    this.changeEmailForm(request, response);
                    break;
                case "change-password":
                    this.changePasswordForm(request, response);
                    break;
                case "delete-account":
                    this.deleteAccountForm(request, response);
                    break;
                default:
                    response.sendRedirect(PATH);
                    break;
            }
        }
    }

    private void profile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String profile = request.getParameter("profile") != null ? request.getParameter("profile") : "";
        String forward;
        if (!profile.equals("") && profile.length() <= 25) {
            UserProfileMDL userProfileMDL = dao.profile(profile);
            if (userProfileMDL.getId() > 0) {
                request.setAttribute("title", userProfileMDL.getName());
                request.setAttribute("user", userProfileMDL.getUser());
                forward = PROFILE;
            } else {
                forward = ERROR404;
            }
        } else {
            forward = ERROR404;
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    private void editProfileForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name") != null ? request.getParameter("name") : "";
        if (!name.equals("") && name.length() <= 50) {
            UserProfileMDL userProfileMDL = new UserProfileMDL();
            userProfileMDL.setName(name);
            userProfileMDL.setId(Integer.parseInt(request.getSession().getAttribute("s_id").toString()));
            int r = dao.editProfile(userProfileMDL);
            if (r == 1) {
                request.getSession().setAttribute("mensaje", "Los datos se actualizaron exitosamente");
                request.getSession().setAttribute("s_name", userProfileMDL.getName());
            } else {
                request.getSession().setAttribute("mensaje", "Ocurrio un error al actualizar los datos");
            }
        } else {
            request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
        }
        response.sendRedirect(PATH + "Poll?page=dashboard");
    }

    private void changeUserForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("user") != null ? request.getParameter("user") : "";
        String password = request.getParameter("password") != null ? request.getParameter("password") : "";
        if (!user.equals("") && !password.equals("") && user.length() <= 25 && password.length() <= 50) {
            UserProfileMDL userProfileMDL = new UserProfileMDL();
            userProfileMDL.setUser(user);
            userProfileMDL.setId(Integer.parseInt(request.getSession().getAttribute("s_id").toString()));
            if (dao.getUserPassword(userProfileMDL.getId()).equals(encryption.encryptPassword(password))) {
                int r = dao.changeUser(userProfileMDL);
                if (r == 1) {
                    request.getSession().setAttribute("mensaje", "Los datos se actualizaron exitosamente");
                    request.getSession().setAttribute("s_user", userProfileMDL.getUser());
                } else {
                    request.getSession().setAttribute("mensaje", "Ocurrio un error al actualizar los datos");
                }
            } else {
                request.getSession().setAttribute("mensaje", "La contraseña es incorrecta");
            }
        } else {
            request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
        }
        response.sendRedirect(PATH + "Poll?page=dashboard");
    }

    private void changeEmailForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email") != null ? request.getParameter("email") : "";
        String password = request.getParameter("password") != null ? request.getParameter("password") : "";
        if (!email.equals("") && !password.equals("") && email.length() <= 50 && password.length() <= 50) {
            UserMDL userMDL = new UserMDL();
            userMDL.setEmail(email);
            userMDL.setId(Integer.parseInt(request.getSession().getAttribute("s_id").toString()));
            if (dao.getUserPassword(userMDL.getId()).equals(encryption.encryptPassword(password))) {
                int r = dao.changeEmail(userMDL);
                if (r == 1) {
                    request.getSession().setAttribute("mensaje", "Los datos se actualizaron exitosamente");
                    request.getSession().setAttribute("s_email", userMDL.getEmail());
                } else {
                    request.getSession().setAttribute("mensaje", "Ocurrio un error al actualizar los datos");
                }
            } else {
                request.getSession().setAttribute("mensaje", "La contraseña es incorrecta");
            }
        } else {
            request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
        }
        response.sendRedirect(PATH + "Poll?page=dashboard");
    }

    private void changePasswordForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String password = request.getParameter("password") != null ? request.getParameter("password") : "";
        String newPassword = request.getParameter("new_password") != null ? request.getParameter("new_password") : "";
        String reNewPassword = request.getParameter("re_new_password") != null ? request.getParameter("re_new_password") : "";
        if (!password.equals("") && !newPassword.equals("") && !reNewPassword.equals("") && password.length() <= 50 && newPassword.length() <= 50 && reNewPassword.length() <= 50 && newPassword.equals(reNewPassword)) {
            UserMDL userMDL = new UserMDL();
            userMDL.setPassword(encryption.encryptPassword(newPassword));
            userMDL.setId(Integer.parseInt(request.getSession().getAttribute("s_id").toString()));
            if (dao.getUserPassword(userMDL.getId()).equals(encryption.encryptPassword(password))) {
                int r = dao.changePassword(userMDL);
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
        response.sendRedirect(PATH + "Poll?page=dashboard");
    }

    private void deleteAccountForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String password = request.getParameter("password") != null ? request.getParameter("password") : "";
        if (!password.equals("") && password.length() <= 50) {
            UserMDL userMDL = new UserMDL();
            userMDL.setId(Integer.parseInt(request.getSession().getAttribute("s_id").toString()));
            if (dao.getUserPassword(userMDL.getId()).equals(encryption.encryptPassword(password))) {
                int r = dao.deleteAccount(userMDL);
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
    }

    private void editProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Editar perfil");
        RequestDispatcher view = request.getRequestDispatcher(EDITPROFILE);
        view.forward(request, response);
    }

    private void changeUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Cambiar usuario");
        RequestDispatcher view = request.getRequestDispatcher(CHANGEUSER);
        view.forward(request, response);
    }

    private void changeEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Cambiar correo electrónico");
        RequestDispatcher view = request.getRequestDispatcher(CHANGEEMAIL);
        view.forward(request, response);
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Cambiar contraseña");
        RequestDispatcher view = request.getRequestDispatcher(CHANGEPASSWORD);
        view.forward(request, response);
    }

    private void deleteAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Eliminar cuenta");
        RequestDispatcher view = request.getRequestDispatcher(DELETEACCOUNT);
        view.forward(request, response);
    }

    private void closeSession(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().setAttribute("s_id", null);
        request.getSession().setAttribute("s_email", null);
        request.getSession().setAttribute("s_name", null);
        request.getSession().setAttribute("s_user", null);
        response.sendRedirect(PATH);
    }

    private void signIn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email") != null ? request.getParameter("email") : "";
        String password = request.getParameter("password") != null ? request.getParameter("password") : "";
        if (!email.equals("") && email.length() <= 50 && !password.equals("") && password.length() <= 50) {
            UserProfileMDL profileMDL = dao.signIn(email);
            if (profileMDL.getId() > 0 && encryption.encryptPassword(password).equals(profileMDL.getPassword())) {
                request.getSession().setAttribute("s_id", profileMDL.getId());
                request.getSession().setAttribute("s_email", profileMDL.getEmail());
                request.getSession().setAttribute("s_name", profileMDL.getName());
                request.getSession().setAttribute("s_user", profileMDL.getUser());
                response.sendRedirect("Poll?page=dashboard");
            } else {
                request.getSession().setAttribute("mensaje", "Correo o contraseña incorrectos");
                response.sendRedirect(PATH);
            }
        } else {
            request.getSession().setAttribute("mensaje", "Correo o contraseña incorrectos");
            response.sendRedirect(PATH);
        }
    }

    private void signUp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String password = request.getParameter("password") != null ? request.getParameter("password") : "";
        String rePassword = request.getParameter("re_password") != null ? request.getParameter("re_password") : "";
        String email = request.getParameter("email") != null ? request.getParameter("email") : "";
        if (!password.equals("") && !rePassword.equals("") && !email.equals("") && password.equals(rePassword) && email.length() <= 50 && password.length() <= 50) {
            UserMDL userMDL = new UserMDL();
            userMDL.setEmail(email);
            userMDL.setPassword(encryption.encryptPassword(password));
            int r = dao.signUp(userMDL);
            if (r == 1) {
                request.getSession().setAttribute("mensaje", "El registro se realizo exitosamente");
            } else {
                request.getSession().setAttribute("mensaje", "Ocurrio un error al realizar el registro");
            }
        } else {
            request.getSession().setAttribute("mensaje", "Error en la validacíon de los datos");
        }
        response.sendRedirect(PATH);
    }

    private void error404(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher(ERROR404);
        view.forward(request, response);
    }

    private void home(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Inicio");
        RequestDispatcher view = request.getRequestDispatcher(HOME);
        view.forward(request, response);
    }

}
