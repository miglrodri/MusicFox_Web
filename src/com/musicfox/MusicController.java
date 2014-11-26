package com.musicfox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

/**
 * Servlet implementation class MusicController
 */
@WebServlet("/MusicController")
public class MusicController extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		JavaBean mybean = new JavaBean();
		String searchQuery = "";
		Map<String, String> resultsMap = new HashMap<String, String>();

		if (request.getParameter("artist_id").isEmpty()) {
			// mostrar página de artist
			String artist_id = request.getParameter("artist_id");
			searchQuery = "SELECT ?id ?name ?maingenre ?decade ?gender"
					+ "WHERE {"
					+ "      ?id rdf:type music:Artist."
					+ "?id music:hasName ?name"
					+ "?id music:hasMainGenre ?maingenre"
					+ "?id music:hasDecade ?decade"
					+ "?id music:hasGender ?gender"
					+ "FILTER(str(?id)=\"http://www.semanticweb.org/MusicOntology#"
					+ artist_id + "\")}";

			ResultSet results = queryDB(searchQuery);
			while (results.hasNext()) {
				QuerySolution binding = results.nextSolution();
				String temp_artist_id = binding.get("id").toString();
				String temp_artist_name = binding.get("name").toString();
				String temp_artist_maingenre = binding.get("maingenre")
						.toString();
				String temp_artist_decade = binding.get("decade").toString();
				String temp_artist_gender = binding.get("gender").toString();
				System.out.println();
				resultsMap.put(temp_artist_id, temp_artist_name);

			}

			mybean.setOption(null);
		} else if (request.getParameter("album_id").isEmpty()) {
			// // mostrar pagina de album
			// String genre_selected = request.getParameter("genre");
			// searchQuery = "SELECT ?id ?name"
			// + "WHERE {"
			// + "      ?id music:hasMainGenre \""
			// + genre_selected
			// + "\"^^xsd:string  ." + "?id music:hasName ?name }";
			//
			// ResultSet results = queryDB(searchQuery);
			// while (results.hasNext()) {
			// QuerySolution binding = results.nextSolution();
			// String temp_artist_id = binding.get("id").toString();
			// String temp_artist_name = binding.get("name").toString();
			// System.out.println();
			// resultsMap.put(temp_artist_id, temp_artist_name);
			//
			// }
			//
			// mybean.setOption(genre_selected);
		} else if (request.getParameter("genre").isEmpty()) {
			// mostrar artistas por genero
			String genre_selected = request.getParameter("genre");
			searchQuery = "SELECT ?id ?name" + "WHERE {"
					+ "      ?id music:hasMainGenre \"" + genre_selected
					+ "\"^^xsd:string  ." + "?id music:hasName ?name }";

			ResultSet results = queryDB(searchQuery);
			while (results.hasNext()) {
				QuerySolution binding = results.nextSolution();
				String temp_artist_id = binding.get("id").toString();
				String temp_artist_name = binding.get("name").toString();
				System.out.println();
				resultsMap.put(temp_artist_id, temp_artist_name);

			}

			mybean.setOption(genre_selected);
		}

		mybean.setResultsMap(resultsMap);

		request.setAttribute("mybean", mybean);

		getServletContext().getRequestDispatcher("/HomeView.jsp").forward(
				request, response);

	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	public ResultSet queryDB(String qq) {

		String inputFileName = "MusicOntology.owl";
		String SOURCE = "http://www.semanticweb.org/MusicOntology";
		String NS = SOURCE + "#";
		String outFileName = "MusicOntologyWithIndividuals.owl";
		final InputStream inputStream = FileManager.get().open(inputFileName);
		final OntModel model = ModelFactory.createOntologyModel(
				OntModelSpec.OWL_DL_MEM, null);
		model.read(inputStream, SOURCE);

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX music: <http://www.semanticweb.org/MusicOntology#>"
				+ qq;

		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);

		ResultSet results = qe.execSelect();

		// Important - free up resources used running the query
		qe.close();

		return results;
	}

}
