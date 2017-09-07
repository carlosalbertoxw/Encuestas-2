/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlos
 */
public class Poll extends HttpServlet {

    private static final String DASHBOARD = "view/session/poll/dashboard.jsp";
    private static final String ERROR404 = "view/public/error404.jsp";
    private static final String PATH = "/Encuestas/";
    private static final String NAME = "Encuestas";

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
                default:
                    response.sendRedirect(PATH);
                    break;
            }
        } else {
            response.sendRedirect(PATH);
        }
    }

    private void dashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Tablero");
        RequestDispatcher view = request.getRequestDispatcher(DASHBOARD);
        view.forward(request, response);
    }

    private void error404(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher(ERROR404);
        view.forward(request, response);
    }
}
