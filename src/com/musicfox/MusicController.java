package com.musicfox;

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
	@SuppressWarnings("rawtypes")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		JavaBean mybean = new JavaBean();
		String searchQuery = "";
		Map<String, String> resultsMap = new HashMap<String, String>();

		if (request.getParameter("artist_id") != null) {
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
			if (results.hasNext()) {
				QuerySolution binding = results.nextSolution();
				Artist temp_artist = new Artist();
				temp_artist.setId(Integer
						.parseInt(binding.get("id").toString()));
				temp_artist.setName(binding.get("name").toString());
				temp_artist.setMainGenre(binding.get("maingenre").toString());
				temp_artist.setDecade(binding.get("decade").toString());
				temp_artist.setGender(binding.get("gender").toString());

				searchQuery = "SELECT ?id ?albumid ?albumtitle"
						+ "WHERE { ?id rdf:type music:Artist."
						+ "?id music:producesAlbum ?albumid."
						+ "?albumid music:hasTitle ?albumtitle"
						+ "FILTER(str(?id)=\"http://www.semanticweb.org/MusicOntology#"
						+ artist_id + "\")}";

				results = queryDB(searchQuery);
				Map<String, String> albums_list = new HashMap<String, String>();
				while (results.hasNext()) {
					binding = results.nextSolution();
					String temp_album_id = binding.get("albumid").toString();
					String temp_album_title = binding.get("albumtitle")
							.toString();
					albums_list.put(temp_album_id, temp_album_title);
				}

				temp_artist.setAlbumsMap(albums_list);
				mybean.setOption(null);
				request.setAttribute("mybean", mybean);
				request.setAttribute("artist", temp_artist);
				getServletContext().getRequestDispatcher("/HomeView.jsp")
						.forward(request, response);

			} else {
				getServletContext().getRequestDispatcher("/404.html").forward(
						request, response);
			}

		} else if (request.getParameter("album_id") != null) {
			// // mostrar pagina de album
			String album_id = request.getParameter("album_id");
			searchQuery = "SELECT ?id ?title ?releasedate ?numberoftracks ?decade"
					+ "WHERE { ?id rdf:type music:Album."
					+ "?id music:hasTitle ?title."
					+ "?id music:hasReleaseDate ?releasedate."
					+ "?id music:hasNumberOfTracks ?numberoftracks."
					+ "?id music:hasDecade ?decade"
					+ "FILTER(str(?id)=\"http://www.semanticweb.org/MusicOntology#"
					+ album_id + "\")}";

			ResultSet results = queryDB(searchQuery);
			if (results.hasNext()) {
				QuerySolution binding = results.nextSolution();
				Album temp_album = new Album();
				temp_album.setTitle(binding.get("title").toString());
				temp_album
						.setReleaseDate(binding.get("releasedate").toString());
				temp_album.setNumberOfTracks(Integer.parseInt(binding.get(
						"numberoftracks").toString()));
				temp_album.setDecade(binding.get("decade").toString());

				searchQuery = "SELECT ?trackid ?tracktitle ?trackindex ?duration"
						+ "WHERE { ?id rdf:type music:Album."
						+ "?trackid rdf:type music:Track."
						+ "?id music:hasTrack ?trackid."
						+ "?trackid music:hasTitle ?tracktitle."
						+ "?trackid music:hasTrackIndex ?trackindex."
						+ "?trackid music:hasDuration ?duration"
						+ "FILTER(str(?id)=\"http://www.semanticweb.org/MusicOntology#"
						+ album_id + "\")}";

				results = queryDB(searchQuery);
				Map<String, Map> tracks_list = new HashMap<String, Map>();
				Map<String, String> track_info = new HashMap<String, String>();
				while (results.hasNext()) {
					binding = results.nextSolution();
					track_info.put("tracktitle", binding.get("tracktitle")
							.toString());
					track_info.put("trackindex", binding.get("trackindex")
							.toString());
					track_info.put("duration", binding.get("duration")
							.toString());
					tracks_list.put(binding.get("trackid").toString(),
							track_info);
				}

				temp_album.setTracksMap(tracks_list);
				mybean.setOption(null);
				request.setAttribute("mybean", mybean);
				request.setAttribute("album", temp_album);
				getServletContext().getRequestDispatcher("/HomeView.jsp")
						.forward(request, response);

			} else {
				getServletContext().getRequestDispatcher("/404.html").forward(
						request, response);
			}

		} else if (request.getParameter("genre") != null) {
			// mostrar artistas por genero
			String genre_selected = request.getParameter("genre");
			System.out.println("param genre: "+genre_selected);
			searchQuery = "SELECT ?id ?name" + "WHERE {"
					+ "      ?id music:hasMainGenre \"" + genre_selected
					+ "\"^^xsd:string  ." + "?id music:hasName ?name }";

			System.out.println("quering >> "+ searchQuery);
			
			ResultSet results = queryDB(searchQuery);
			if (results.hasNext()) {
				while (results.hasNext()) {
					QuerySolution binding = results.nextSolution();
					String temp_artist_id = binding.get("id").toString();
					String temp_artist_name = binding.get("name").toString();
					resultsMap.put(temp_artist_id, temp_artist_name);

				}
				
				mybean.setOption(genre_selected);
				mybean.setResultsMap(resultsMap);
				request.setAttribute("mybean", mybean);
				getServletContext().getRequestDispatcher("/HomeView.jsp").forward(
						request, response);
				
			}
			else {
				getServletContext().getRequestDispatcher("/404.html").forward(
						request, response);
			}

			
		} else {
			mybean.setOption(null);
			request.setAttribute("mybean", mybean);
			getServletContext().getRequestDispatcher("/HomeView.jsp").forward(
					request, response);
		}

	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	public ResultSet queryDB(String qq) {
		System.out.println("public ResultSet queryDB(String qq) {");
		String inputFileName = "MusicOntologyWithIndividuals.owl";
		String SOURCE = "http://www.semanticweb.org/MusicOntology";
		final InputStream inputStream = FileManager.get().open(inputFileName);
		final OntModel model = ModelFactory.createOntologyModel(
				OntModelSpec.OWL_DL_MEM, null);
		
		System.out.println("trying to read owl to model");
		model.read(inputStream, SOURCE);
		System.out.println("owl was read to model");
		
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
