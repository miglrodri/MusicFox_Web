// TODO quando é feito um pedido para mostrar artista por id, se esse artista nao tiver a info das stats toda completa a query cai por terra!!
// solução seria fazer uma BD que populasse todos os dados, mesmo com 0 ou nulls. do lado do sparql teria que se andar a fazer vários pedidos, a cada estatística..
// no entanto ainda devemos investigar porque acho que dá para declarar campos opcionais na pesquisa sparql, no entanto nao sei como funca e se dá mais trab.

// TODO falta paginação \ e pequenos pormenores de navegação

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
		
		System.out.println(bean.getNumberItems());
		getServletContext().getRequestDispatcher("/HomeView.jsp").forward(request, response);
		
		

	}

	

}
