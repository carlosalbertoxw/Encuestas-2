/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PollDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.PollMDL;
import model.UserMDL;
import model.UserProfileMDL;

/**
 *
 * @author carlos
 */
public class Poll extends HttpServlet {

    private static final String DASHBOARD = "view/session/poll/dashboard.jsp";
    private static final String POLL = "view/session/poll/poll.jsp";
    private static final String ERROR404 = "view/error404.jsp";
    private static final String PATH = "/Encuestas/";
    private static final String NAME = "Encuestas";
    private PollDAO dao;

    public Poll() {
        dao = PollDAO.getInstance();
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
        request.setAttribute("path", PATH);
        request.setAttribute("name", NAME);
        if (request.getSession().getAttribute("s_id") != null) {
            switch (page) {
                case "dashboard":
                    this.dashboard(request, response);
                    break;
                case "add-poll":
                    this.addPoll(request, response);
                    break;
                case "edit-poll":
                    this.editPoll(request, response);
                    break;
                case "delete-poll":
                    this.deletePoll(request, response);
                    break;
                default:
                    this.error404(request, response);
                    break;
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
        String page = request.getParameter("form") != null ? request.getParameter("form") : "";
        if (request.getSession().getAttribute("s_id") != null) {
            switch (page) {
                case "add-poll":
                    this.addPollForm(request, response);
                    break;
                case "edit-poll":
                    this.editPollForm(request, response);
                    break;
                default:
                    response.sendRedirect(PATH);
                    break;
            }
        } else {
            response.sendRedirect(PATH);
        }
    }

    private void deletePoll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int key = request.getParameter("key") != null ? Integer.parseInt(request.getParameter("key")) : 0;
        if (key != 0 && String.valueOf(key).length() <= 10) {
            int r = dao.deletePoll(key, Integer.parseInt(request.getSession().getAttribute("s_id").toString()));
            if (r == 1) {
                request.getSession().setAttribute("mensaje", "Los datos se borraron exitosamente");
            } else {
                request.getSession().setAttribute("mensaje", "Ocurrio un error al borrar los datos");
            }
        } else {
            request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
        }
        response.sendRedirect(PATH + "Poll?page=dashboard");
    }

    private void editPollForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title") != null ? request.getParameter("title") : "";
        String description = request.getParameter("description") != null ? request.getParameter("description") : "";
        int position = request.getParameter("position") != null ? Integer.parseInt(request.getParameter("position")) : 0;
        int key = request.getParameter("key") != null ? Integer.parseInt(request.getParameter("key")) : 0;
        if (!title.equals("") && title.length() <= 250 && !description.equals("") && description.length() <= 500 && position != 0 && String.valueOf(position).length() <= 6 && key != 0 && String.valueOf(key).length() <= 10) {
            PollMDL pollMDL = new PollMDL();
            UserProfileMDL userProfileMDL = new UserProfileMDL();
            UserMDL userMDL = new UserMDL();
            pollMDL.setKey(key);
            pollMDL.setTitle(title);
            pollMDL.setDescription(description);
            pollMDL.setPosition(position);
            userMDL.setKey(Integer.parseInt(request.getSession().getAttribute("s_id").toString()));
            userProfileMDL.setUserMDL(userMDL);
            pollMDL.setUserProfileMDL(userProfileMDL);
            int r = dao.updatePoll(pollMDL);
            if (r == 1) {
                request.getSession().setAttribute("mensaje", "Los datos se actualizaron exitosamente");
            } else {
                request.getSession().setAttribute("mensaje", "Ocurrio un error al actualizar los datos");
            }
        } else {
            request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
        }
        response.sendRedirect(PATH + "Poll?page=dashboard");
    }

    private void editPoll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int key = request.getParameter("key") != null ? Integer.parseInt(request.getParameter("key")) : 0;
        if (key != 0 && String.valueOf(key).length() <= 10) {
            request.setAttribute("title", "Editar encuesta");
            request.setAttribute("poll", dao.getPoll(key));
            RequestDispatcher view = request.getRequestDispatcher(POLL);
            view.forward(request, response);
        } else {
            request.getSession().setAttribute("mensaje", "Error en la validacíon de los datos");
            response.sendRedirect(PATH);
        }
    }

    private void addPollForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title") != null ? request.getParameter("title") : "";
        String description = request.getParameter("description") != null ? request.getParameter("description") : "";
        int position = request.getParameter("position") != null ? Integer.parseInt(request.getParameter("position")) : 0;
        if (!title.equals("") && title.length() <= 250 && !description.equals("") && description.length() <= 500 && position != 0 && String.valueOf(position).length() <= 6) {
            PollMDL pollMDL = new PollMDL();
            UserProfileMDL userProfileMDL = new UserProfileMDL();
            UserMDL userMDL = new UserMDL();
            pollMDL.setTitle(title);
            pollMDL.setDescription(description);
            pollMDL.setPosition(position);
            userMDL.setKey(Integer.parseInt(request.getSession().getAttribute("s_id").toString()));
            userProfileMDL.setUserMDL(userMDL);
            pollMDL.setUserProfileMDL(userProfileMDL);
            int r = dao.addPoll(pollMDL);
            if (r == 1) {
                request.getSession().setAttribute("mensaje", "Los datos se guardaron exitosamente");
            } else {
                request.getSession().setAttribute("mensaje", "Ocurrio un error al guardar los datos");
            }
        } else {
            request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
        }
        response.sendRedirect(PATH + "Poll?page=dashboard");
    }

    private void addPoll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Agregar encuesta");
        RequestDispatcher view = request.getRequestDispatcher(POLL);
        view.forward(request, response);
    }

    private void dashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Tablero");
        request.setAttribute("polls", dao.getPolls(Integer.parseInt(request.getSession().getAttribute("s_id").toString())));
        RequestDispatcher view = request.getRequestDispatcher(DASHBOARD);
        view.forward(request, response);
    }

    private void error404(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher(ERROR404);
        view.forward(request, response);
    }
}
