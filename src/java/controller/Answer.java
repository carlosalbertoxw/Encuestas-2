/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AnswerDAO;
import dao.PollDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AnswerMDL;
import model.PollMDL;
import model.UserMDL;
import model.UserProfileMDL;

/**
 *
 * @author carlos
 */
public class Answer extends HttpServlet {

    private static final String VIEWANSWERS = "view/session/answer/viewAnswers.jsp";
    private static final String ANSWER = "view/public/answer/answer.jsp";
    private static final String ERROR404 = "view/error404.jsp";
    private static final String PATH = "/Encuestas/";
    private static final String NAME = "Encuestas";
    private AnswerDAO dao;

    public Answer() {
        dao = AnswerDAO.getInstance();
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
                case "view-answers":
                    this.viewAnswer(request, response);
                    break;
                case "add-answer":
                    this.addAnswer(request, response);
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
                case "add-answer":
                    this.addAnswerForm(request, response);
                    break;
                default:
                    response.sendRedirect(PATH);
                    break;
            }
        } else {
            response.sendRedirect(PATH);
        }
    }

    private void addAnswerForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int stars = request.getParameter("stars") != null ? Integer.parseInt(request.getParameter("stars")) : 0;
        String comment = request.getParameter("comment") != null ? request.getParameter("comment") : "";
        String user = request.getParameter("user") != null ? request.getParameter("user") : "";
        int user_key = request.getParameter("user_key") != null ? Integer.parseInt(request.getParameter("user_key")) : 0;
        int poll_key = request.getParameter("poll_key") != null ? Integer.parseInt(request.getParameter("poll_key")) : 0;
        if (stars != 0 && String.valueOf(stars).length() == 1 && !comment.equals("") && comment.length() <= 1000 && !user.equals("") && user.length() <= 25 && user_key != 0 && String.valueOf(user_key).length() <= 10 && poll_key != 0 && String.valueOf(poll_key).length() <= 10) {
            AnswerMDL answerMDL = new AnswerMDL();
            PollMDL pollMDL = new PollMDL();
            UserProfileMDL userProfileMDL = new UserProfileMDL();
            UserMDL userMDL = new UserMDL();

            answerMDL.setStars(stars);
            answerMDL.setComment(comment);
            pollMDL.setKey(poll_key);
            answerMDL.setPollMDL(pollMDL);
            userMDL.setKey(user_key);
            userProfileMDL.setUserMDL(userMDL);
            answerMDL.setUserProfileMDL(userProfileMDL);
            int r = dao.addAnswer(answerMDL);
            if (r == 1) {
                request.getSession().setAttribute("mensaje", "Los datos se guardaron exitosamente");
            } else {
                request.getSession().setAttribute("mensaje", "Ocurrio un error al guardar los datos");
            }
        } else {
            request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
        }
        response.sendRedirect(PATH + "?profile=" + user);
    }

    private void addAnswer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int key = request.getParameter("key") != null ? Integer.parseInt(request.getParameter("key")) : 0;
        if (key != 0 && String.valueOf(key).length() <= 10) {
            PollDAO pollDAO = PollDAO.getInstance();
            PollMDL pollMDL = pollDAO.getPoll(key);
            request.setAttribute("poll", pollMDL);
            request.setAttribute("title", "Responder - " + pollMDL.getTitle());
            RequestDispatcher view = request.getRequestDispatcher(ANSWER);
            view.forward(request, response);
        } else {
            request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
        }
        response.sendRedirect(PATH + "Poll?page=dashboard");
    }

    private void viewAnswer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pollKey = request.getParameter("key") != null ? Integer.parseInt(request.getParameter("key")) : 0;
        if (pollKey != 0 && String.valueOf(pollKey).length() <= 10) {
            PollDAO pollDAO = PollDAO.getInstance();
            PollMDL pollMDL = pollDAO.getPoll(pollKey);
            request.setAttribute("poll", pollMDL);
            request.setAttribute("title", "Respuestas - " + pollMDL.getTitle());
            request.setAttribute("answers", dao.getAnswers(Integer.parseInt(request.getSession().getAttribute("s_id").toString()), pollKey));
            RequestDispatcher view = request.getRequestDispatcher(VIEWANSWERS);
            view.forward(request, response);
        } else {
            request.getSession().setAttribute("mensaje", "Ocurrio un error en la validación de los datos");
        }
        response.sendRedirect(PATH + "Poll?page=dashboard");
    }

    private void error404(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher(ERROR404);
        view.forward(request, response);
    }

}
