package com.musicfox;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

/**
 * Servlet implementation class RecommendationController
 */
@WebServlet("/RecommendationController")
public class RecommendationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecommendationController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/HomeView.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		try {
			System.out.println("reading json data");
			JSONObject json = (JSONObject) new JSONParser().parse(request
					.getParameter("track"));

			/**
			 * Prepare recommendation response
			 */

			// ?retirar artistas já vistos?
			// ?filtrar / order by total views?

			JSONObject resultsArray = new JSONObject();

			String fav_genre = getFav(json, "genre");
			String fav_decade = getFav(json, "decade");

			String searchQuery = "SELECT ?id ?name " + "WHERE {"
					+ " ?id rdf:type music:Artist. "
					+ " ?id music:hasMainGenre ?genre. "
					+ " ?id music:hasDecade ?decade. "
					+ " ?id music:hasName ?name. " + " FILTER ( regex(?genre, \""
					+ fav_genre + "\", \"i\") && regex(?decade, \""
					+ fav_decade + "\", \"i\") ) }" + " ORDER BY ?name LIMIT 4 ";

			QueryExecution qe = Results.queryDB(searchQuery);
			ResultSet results = qe.execSelect();
			if (results.hasNext()) {
				while (results.hasNext()) {
					QuerySolution binding = results.nextSolution();
					String temp_artist_id = binding.get("id").toString();
					String temp_artist_name = binding.get("name").toString();
					
					resultsArray.put(Results.cleanLiteral(temp_artist_name), "MusicController?artistid="
							+ Results.cleanId(temp_artist_id));
				}

			} else {
				// 0 results
			}
			qe.close();

			

			//
			// System.out.println("json: " + json.toJSONString());
			writer.print(resultsArray);
			System.out.println("wrote message to the response!");
			writer.close();
		} catch (Exception ex) {
			ex.getStackTrace();
			System.out.println(ex);
		}
	}

	private String getFav(JSONObject json, String option) {
		JSONObject option_temp = (JSONObject) json.get(option);
		String fav_option = "";
		long max_option = 0;
		for (Object item : option_temp.keySet()) {
			// System.out.println(item);
			long t1 = (long) option_temp.get(item);
			// System.out.println(t1);
			if (t1 > max_option) {
				max_option = t1;
				fav_option = (String) item;
			}

		}
		System.out.println("fav_" + option + ": " + fav_option);
		return fav_option;
	}

}
