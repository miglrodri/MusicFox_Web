package com.musicfox;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

		
		ArrayList<String> dirtyResultsArray = new ArrayList<String>();
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

			String[] fav_genre = getFav(json, "genre");
			String[] fav_decade = getFav(json, "decade");

			/**
			 * Perform the query to get recommended artists
			 */
			
			String searchQuery = "SELECT DISTINCT ?name " + "WHERE {"
					+ " ?id rdf:type music:Artist. "
					+ " ?id music:hasName ?name. "
					+ " ?id music:hasMainGenre ?genre. "
					+ " ?id music:hasDecade ?decade. "
					+ " FILTER ( (regex(?genre, \"" + fav_genre[0]
					+ "\", \"i\") || regex(?genre, \"" + fav_genre[1]
					+ "\", \"i\")) && ( regex(?decade, \"" + fav_decade[0]
					+ "\", \"i\") || regex(?decade, \"" + fav_decade[1]
					+ "\", \"i\") ) ) }" + " ORDER BY ?name LIMIT 50 ";

			//System.out.println("recommendation query for artists: "	+ searchQuery);
			QueryExecution qe = Results.queryDB(searchQuery);
			ResultSet results = qe.execSelect();
			
			int temp_counter = 1;
			if (results.hasNext()) {
				while (results.hasNext()) {
					if (temp_counter > 20) {
						break;
					}
					
					QuerySolution binding = results.nextSolution();
					String temp_artist_name = binding.get("name").toString();
					String temp_artist_id = getArtistIdFromName(Results
							.cleanLiteral(temp_artist_name));
					String temp_url = "MusicController?artistid="
							+ Results.cleanId(temp_artist_id);

					// resultsArray.put("a"+Results.cleanLiteral(temp_artist_name),
					// "xx");
					
					//System.out.println(">> " + temp_artist_name);
					
					if (!dirtyResultsArray.contains(Results.cleanLiteral(temp_artist_name).toLowerCase())) {
						resultsArray.put(
								"a" + Results.cleanLiteral(temp_artist_name),
								temp_url);
						dirtyResultsArray.add(Results.cleanLiteral(temp_artist_name).toLowerCase());
						//System.out.println(">>>> added: |" + Results.cleanLiteral(temp_artist_name) + "|");
						temp_counter++;
					}
					
				}

			} else {
				// 0 results
			}
			qe.close();
			//System.out.println("# of results: " + temp_counter);
			 
	
			
			/**
			 * Perform the query to get recommended tracks
			 */
			
			searchQuery = "SELECT DISTINCT ?name " + "WHERE {"
					+ " ?s rdf:type music:Artist. "
					+ " ?s music:hasMainGenre ?genre. "
					+ " ?s music:hasDecade ?decade. "
					+ " ?s music:writesTrack ?id. "
					+ " ?id music:hasTitle ?name. "
					+ " FILTER ( (regex(?genre, \"" + fav_genre[0]
					+ "\", \"i\") || regex(?genre, \"" + fav_genre[1]
					+ "\", \"i\")) && ( regex(?decade, \"" + fav_decade[0]
					+ "\", \"i\") || regex(?decade, \"" + fav_decade[1]
					+ "\", \"i\") ) ) }" + " ORDER BY ?name LIMIT 50 ";

			//System.out.println("recommendation query for tracks: "					+ searchQuery);

			temp_counter = 1;
			qe = Results.queryDB(searchQuery);
			results = qe.execSelect();
			if (results.hasNext()) {
				while (results.hasNext()) {
					if (temp_counter > 20) {
						break;
					}
					
					QuerySolution binding = results.nextSolution();
					String temp_track_name = binding.get("name").toString();
					String temp_track_id = getTrackIdFromName(Results
							.cleanLiteral(temp_track_name));
					String temp_url = "MusicController?trackid=" + Results.cleanId(temp_track_id);

					//System.out.println(">> " + temp_track_name);
					
					if (!dirtyResultsArray.contains(Results.cleanLiteral(temp_track_name).toLowerCase()) && !temp_track_id.isEmpty()) {
						resultsArray.put(
								"b" + Results.cleanLiteral(temp_track_name),
								temp_url);
						dirtyResultsArray.add(Results.cleanLiteral(temp_track_name).toLowerCase());
						//System.out.println(">>>> added: |" + Results.cleanLiteral(temp_track_name) + "|");
						temp_counter++;
					}
				}

			} else {
				// 0 results
			}
			//System.out.println("# of results: " + temp_counter);
			qe.close();

	

			writer.print(resultsArray);
			System.out.println("wrote message to the response!");
			writer.close();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	private String getTrackIdFromName(String temp_track_name) {
		String searchQuery = "SELECT ?id " + "WHERE {"
				+ " ?id rdf:type music:Track. " + " ?id music:hasTitle ?name. "
				+ " FILTER regex(?name, \"" + temp_track_name.replace("\"", "") + "\", \"i\") }";
		//System.out.println("query for track id: "+ searchQuery);
		String result = "";
		try {
			QueryExecution qe = Results.queryDB(searchQuery);
			ResultSet results = qe.execSelect();
			while (results.hasNext()) {
				QuerySolution binding = results.nextSolution();
				result = binding.get("id").toString();
				//System.out.println(">> got some results for name: " + temp_track_name + " # "+ result);
				return result;
			}
			qe.close();
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(">>" + e.getMessage());1\1
		}
		return result;
	}
	
	private String getArtistIdFromName(String temp_artist_name) {
		String searchQuery = "SELECT ?id " + "WHERE {"
				+ " ?id rdf:type music:Artist. " + " ?id music:hasName ?name. "
				+ " FILTER regex(?name, \"" + temp_artist_name + "\", \"i\") }";
		//System.out.println("query for track id: "+ searchQuery);
		String result = "";
		try {
			QueryExecution qe = Results.queryDB(searchQuery);
			ResultSet results = qe.execSelect();
			while (results.hasNext()) {
				QuerySolution binding = results.nextSolution();
				result = binding.get("id").toString();
				//System.out.println(">> got some results for name: " + temp_track_name + " # "+ result);
				return result;
			}
			qe.close();
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(">>" + e.getMessage());
		}
		return result;
	}

	private String[] getFav(JSONObject json, String option) {
		JSONObject option_temp = (JSONObject) json.get(option);
		String fav_option = "";
		String fav2_option = "";
		long max_option = 0;
		for (Object item : option_temp.keySet()) {
			// System.out.println(item);
			long t1 = (long) option_temp.get(item);
			// System.out.println(t1);
			if (t1 > max_option) {
				max_option = t1;
				fav2_option = fav_option;
				fav_option = (String) item;
			}
		}
		//System.out.println("fav_" + option + ": " + fav_option);
		return new String[]{fav_option, fav2_option};
	}

}
