package cl.bcs.scheduler.service;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BcsJobManagerInit
 */
@WebServlet("/BcsJobManagerInit")
public class BcsJobManagerInit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BcsJobManagerImpl c  = new BcsJobManagerImpl();
	 

    /**
     * Default constructor. 
     */
    public BcsJobManagerInit() {
    	 
       System.out.println("Iniciado con el servidor");
       c.poolJobs();
       
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
