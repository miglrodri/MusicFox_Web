package com.musicfox;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MusicController
 */
@WebServlet("/MusicController")
public class MusicController extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private List<com.echonest.api.v4.Song> albums;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MusicController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		JavaBean bean = Results.search(request,null);
		
		request.setAttribute("mybean", bean);
		
		//System.out.println(bean.getNumberItems());
		getServletContext().getRequestDispatcher("/HomeView.jsp").forward(request, response);
		
		

	}

	

}
