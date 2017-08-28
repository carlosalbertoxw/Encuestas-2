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
public class Session extends HttpServlet {

    private static final String DASHBOARD = "view/session/dashboard.jsp";
    private static final String ERROR404 = "view/public/error404.jsp";
    private static final String PATH = "/JSPsServletsTemplate/";
    private static final String NAME = "JSPs Servlets Template";
    private String forward;

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
            switch (page) {
                case "dashboard":
                    request.setAttribute("path", PATH);
                    request.setAttribute("title", "Tablero");
                    request.setAttribute("name", NAME);
                    forward = DASHBOARD;
                    break;
                default:
                    forward = ERROR404;
                    break;
            }
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
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
            switch (form) {
                default:
                    response.sendRedirect(PATH);
                    break;
            }
        } else {
            response.sendRedirect(PATH);
        }
    }

}
