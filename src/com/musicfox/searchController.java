package com.musicfox;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class searchController
 */
@WebServlet("/searchController")
public class searchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Map<String, String[]> query = request.getParameterMap();
		if(query.get("query")!=null)
		{
			String search = query.get("query")[0], tmp;
			String[] q = null;
			
			if(search.contains("genre")){
				tmp = search.replaceAll("genre", "");
				q = new String[]{"genre",tmp.trim()};
			}
			else if(search.contains("track")){
				tmp = search.replaceAll("track", "");
				q = new String[]{"track",tmp.trim()};
			}
			else if(search.contains("album")){
				tmp = search.replaceAll("album", "");
				q = new String[]{"album",tmp.trim()};
			}
			else if(search.contains("artist")){
				tmp = search.replaceAll("artist", "");
				q = new String[]{"artist",tmp.trim()};
			}
			else if(search.contains("decade")){
				tmp = search.replaceAll("decade", "");
				q = new String[]{"decade",tmp.trim()};
			}
			else {
				// não se procurou por uma das classes anteriores
				// search: "black"
				// A ideia era mostrar os resultados por artista, albums e track. numa tabela.
				// vamos popular então o mybean->semanticArray(resource_id, resource_name)
				
				q = new String[]{"all",search};
				System.out.println("\\ALL : search: "+ search);
			}
			
			JavaBean bean = Results.search(null, q);
	
			request.setAttribute("mybean", bean);
			System.out.println(bean.getNumberItems()+" "+query.get("query")[0]);
		}
	
		getServletContext().getRequestDispatcher("/HomeView.jsp").forward(request, response);
		
		
		
		
	}

}
