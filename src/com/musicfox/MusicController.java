// TODO quando é feito um pedido para mostrar artista por id, se esse artista nao tiver a info das stats toda completa a query cai por terra!!
// solução seria fazer uma BD que populasse todos os dados, mesmo com 0 ou nulls. do lado do sparql teria que se andar a fazer vários pedidos, a cada estatística..
// no entanto ainda devemos investigar porque acho que dá para declarar campos opcionais na pesquisa sparql, no entanto nao sei como funca e se dá mais trab.

// TODO teste ^^ Escolher género Electronica, Afrojck dá erro porque falta alguma stat, e Alesso dá na boa..

// TODO falta paginação \ e pequenos pormenores de navegação

package com.musicfox;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Image;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

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
		QueryExecution qe;
		ResultSet results = null;

		EchoNestAPI echoNest = new EchoNestAPI("IE2ZINGXDYWASD9NH");
		System.out.println("request to controller>>>>>>");

		// disponibiliza um bean com artistsArray contendo 1 único artist
		if (request.getParameter("artistid") != null) {
			// mostrar página de artist

			String artist_id = request.getParameter("artistid");
			System.out.println("param artist_id = " + artist_id);
			searchQuery = "SELECT ?id ?name ?maingenre ?decade ?gender ?vevourl ?vevovtotal ?vevolm ?twitterurl ?twitterfoll ?fburl ?fbpta ?fblikes  WHERE { ?id rdf:type music:Artist. 	?id music:hasName ?name. ?id music:hasMainGenre ?maingenre.  ?id music:hasDecade ?decade. 		?id music:hasGender ?gender.  ?id music:hasVevoUrl ?vevourl. 	?id music:hasVevoViewsLastMonth ?vevolm. ?id music:hasVevoViewsTotal ?vevovtotal.  ?id music:hasTwitterUrl ?twitterurl. ?id music:hasTwitterFollowers ?twitterfoll. ?id music:hasFacebookUrl ?fburl.  ?id music:hasFacebookPeopleTalkingAbout ?fbpta. ?id music:hasFacebookLikes ?fblikes.		FILTER regex( str(?id), \""+ artist_id +"\" ) 	}";


			qe = queryDB(searchQuery);
			results = qe.execSelect();
			System.out.println(searchQuery);

			if (results.hasNext()) {
				System.out.println("######tem dados de artista para mostrar, stats");
				QuerySolution binding = results.nextSolution();
				Artist temp_artist = new Artist();

				temp_artist.setId(cleanId(binding.get("id").toString()));
				temp_artist.setName(cleanLiteral(binding.get("name").toString()));
				temp_artist.setMainGenre(cleanLiteral(binding.get("maingenre").toString()));
				temp_artist.setDecade(cleanLiteral(binding.get("decade").toString()));
				temp_artist.setGender(cleanLiteral(binding.get("gender").toString()));
//				temp_artist.setVevoUrl(cleanLiteral(binding.get("vevourl").toString()));
//				temp_artist.setVevoViewsLastMonth(Integer.parseInt(cleanLiteral(binding.get("vevolm").toString())));
//				temp_artist.setVevoViewsTotal(Integer.parseInt(cleanLiteral(binding.get("vevovtotal").toString())));
//				temp_artist.setTwitterUrl(cleanLiteral(binding.get("twitterurl").toString()));
//				temp_artist.setTwitterFollowers(Integer.parseInt(cleanLiteral(binding.get("twitterfoll").toString())));
//				temp_artist.setFacebookUrl(cleanLiteral(binding.get("fburl").toString()));
//				temp_artist.setFacebookPeopleTalkingAbout(Integer.parseInt(cleanLiteral(binding.get("fbpta").toString())));
//				temp_artist.setFacebookLikes(Integer.parseInt(cleanLiteral(binding.get("fblikes").toString())));
				
//						?id music:hasLastFMUrl ?lastfmurl.
//						?id music:hasLastFMListeners ?lastfmlist.
//						?id music:hasLastFMPlayCount ?lastfmcount.
//				
				
				searchQuery = "SELECT ?albumid ?albumtitle WHERE { ?id rdf:type music:Artist. ?id music:producesAlbum ?albumid. ?albumid music:hasTitle ?albumtitle FILTER(str(?id)=\"http://www.semanticweb.org/MusicOntology#" + artist_id + "\") }";
				//System.out.println("quering >> " + searchQuery);

				qe = queryDB(searchQuery);
				results = qe.execSelect();
				
				while (results.hasNext()) {
					
					binding = results.nextSolution();
					String temp_album_id = binding.get("albumid").toString();
					String temp_album_title = binding.get("albumtitle")
							.toString();
					
//					System.out.print("albumid: "+temp_album_id);
//					System.out.println(" #albumtitle: "+temp_album_title);

					try {
						String query = temp_album_title.substring(0, temp_album_title.indexOf("^^"));
						albums = echoNest.searchArtists(query);
						if (artists.size() > 0) {
							java.util.List<Image> images = artists.get(0).getImages();
							if(images.size() > 0){
								temp_artist.setCover(images.get(0).getURL());
								System.out.println(images.get(0).getURL());
							}	
						}
					} catch (EchoNestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Album temp_album = new Album();
					temp_album.setId(cleanId(temp_album_id));
					temp_album.setTitle(cleanLiteral(temp_album_title));

					temp_artist.addToAlbumsArray(temp_album);
				}

				mybean.setPageType("artist_page");
				mybean.addToArtistsArray(temp_artist);
				mybean.setOption(null);
				request.setAttribute("mybean", mybean);
				getServletContext().getRequestDispatcher("/HomeView.jsp")
						.forward(request, response);

			} else {
				getServletContext().getRequestDispatcher("/404.html").forward(
						request, response);
			}
			qe.close();

		} else if (request.getParameter("albumid") != null) {
			// // mostrar pagina de album
			String album_id = request.getParameter("albumid");
			System.out.println("param album_id = " + album_id);
			searchQuery = "SELECT ?id ?title ?release ?ntracks ?decade  WHERE { ?id rdf:type music:Album. 	?id music:hasTitle ?title. ?id music:hasReleaseDate ?release.  ?id music:hasNumberOfTracks ?ntracks.	?id music:hasDecade ?decade.	FILTER regex( str(?id), \""+ album_id +"\" ) 	}";	
			
			qe = queryDB(searchQuery);
			results = qe.execSelect();
			if (results.hasNext()) {
				QuerySolution binding = results.nextSolution();
				Album temp_album = new Album();
				temp_album.setId(cleanId(binding.get("id").toString()));
				temp_album.setTitle(cleanLiteral(binding.get("title").toString()));
				temp_album.setReleaseDate(cleanLiteral(binding.get("release").toString()));
				temp_album.setNumberOfTracks(Integer.parseInt(cleanLiteral(binding.get("ntracks").toString())));
				temp_album.setDecade(cleanLiteral(binding.get("decade").toString()));

				searchQuery = "SELECT ?trackid ?tracktitle ?trackindex ?duration 	WHERE { ?id rdf:type music:Album. 	?trackid rdf:type music:Track.  ?id music:hasTrack ?trackid. 	?trackid music:hasTitle ?tracktitle. ?trackid music:hasTrackIndex ?trackindex. ?trackid music:hasDuration ?duration  	FILTER(str(?id)=\"http://www.semanticweb.org/MusicOntology#" + album_id + "\") }";
			
				QueryExecution qe1 = queryDB(searchQuery);
				ResultSet results1 = qe1.execSelect();
				while (results1.hasNext()) {
					QuerySolution binding1 = results1.nextSolution();
					Track temp_track = new Track();
					temp_track.setId(cleanId(binding1.get("trackid").toString()));
					temp_track.setTitle(cleanLiteral(binding1.get("tracktitle").toString()));
					temp_track.setTrackIndex(cleanLiteral(binding1.get("trackindex").toString()));
					temp_track.setDuration(cleanLiteral(binding1.get("duration").toString()));
					temp_album.addToTracksArray(temp_track);
				}
				qe1.close();

				mybean.setPageType("album_page");
				mybean.setAlbum_information(temp_album);
				mybean.setOption(null);
				request.setAttribute("mybean", mybean);
				getServletContext().getRequestDispatcher("/HomeView.jsp")
						.forward(request, response);

			} else {
				getServletContext().getRequestDispatcher("/404.html").forward(
						request, response);
			}
			qe.close();

		} else if (request.getParameter("genre") != null) { // disponibiliza 1
															// bean com o
															// aristsArray
															// preenchido com id
															// e nome de artista
															// somente
			// mostrar artistas por genero
			String genre_selected = request.getParameter("genre");
			if (genre_selected.toLowerCase().equals("rnb")) {
				genre_selected = "R&B";
			}
			System.out.println("param genre: " + genre_selected);
			searchQuery = "SELECT ?id ?name " + "WHERE {"
					+ "?id music:hasMainGenre ?genre ."
					+ " ?id music:hasName ?name ." + " FILTER regex(?genre, \""
					+ genre_selected + "\", \"i\") }" + "ORDER BY ?name";

			qe = queryDB(searchQuery);
			results = qe.execSelect();

			int temp = 0;
			if (results.hasNext()) {
				while (results.hasNext()) {
					QuerySolution binding = results.nextSolution();
					String temp_artist_id = binding.get("id").toString();
					String temp_artist_name = binding.get("name").toString();

					
					Artist temp_artist = new Artist();
					temp_artist.setId(cleanId(temp_artist_id));
					temp_artist.setName(cleanLiteral(temp_artist_name));
					
					try {
						String query = temp_artist_name.substring(0, temp_artist_name.indexOf("^^"));
						artists = echoNest.searchArtists(query);
						if (artists.size() > 0) {
							java.util.List<Image> images = artists.get(0).getImages();
							if(images.size() > 0){
								temp_artist.setCover(images.get(0).getURL());
								System.out.println(images.get(0).getURL());
							}	
						}
					} catch (EchoNestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					mybean.addToArtistsArray(temp_artist);
					temp++; // incremente o contador do nr de resultados
							// encontrados

				}

				mybean.setPageType("artist_list_page");
				mybean.setOption(genre_selected);
				mybean.setNumberItems(temp);
				request.setAttribute("mybean", mybean);
				getServletContext().getRequestDispatcher("/HomeView.jsp")
						.forward(request, response);

			} else {
				mybean.setNumberItems(temp);
				getServletContext().getRequestDispatcher("/404.html").forward(
						request, response);
			}
			qe.close();

		} else if (request.getParameter("decade") != null) { // disponibiliza 1
																// bean com o
																// aristsArray
																// preenchido
																// com id e nome
																// de artista
																// somente
			// mostrar artistas por decade
			String decade_selected = request.getParameter("decade");
			System.out.println("param decade: " + decade_selected);
			searchQuery = "SELECT ?id ?name " + "WHERE {"
					+ "?id music:hasDecade ?decade ."
					+ " ?id music:hasName ?name ."
					+ " FILTER regex(?decade, \"" + decade_selected
					+ "\", \"i\") }" + "ORDER BY ?name";


			qe = queryDB(searchQuery);
			results = qe.execSelect();

			int temp = 0;
			if (results.hasNext()) {
				while (results.hasNext()) {
					QuerySolution binding = results.nextSolution();
					String temp_artist_id = binding.get("id").toString();
					String temp_artist_name = binding.get("name").toString();

					Artist temp_artist = new Artist();
					temp_artist.setId(cleanId(temp_artist_id));
					temp_artist.setName(cleanLiteral(temp_artist_name));
					
					try {
						String query = temp_artist_name.substring(0, temp_artist_name.indexOf("^^"));
						artists = echoNest.searchArtists(query);
						if (artists.size() > 0) {
							java.util.List<Image> images = artists.get(0).getImages();
							if(images.size() > 0){
								temp_artist.setCover(images.get(0).getURL());
								System.out.println(images.get(0).getURL());
							}	
						}
					} catch (EchoNestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					mybean.addToArtistsArray(temp_artist);

					temp++; // incremente o contador do nr de resultados
							// encontrados

				}

				mybean.setPageType("artist_list_page");
				mybean.setOption(decade_selected);
				mybean.setNumberItems(temp);
				request.setAttribute("mybean", mybean);
				getServletContext().getRequestDispatcher("/HomeView.jsp")
						.forward(request, response);

			} else {
				mybean.setNumberItems(temp); // devolve que encontrou 0 items
				getServletContext().getRequestDispatcher("/404.html").forward(
						request, response);
			}
			qe.close();
		} else {
			mybean.setPageType("homepage");
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
	public QueryExecution queryDB(String qq) {
		System.out.println("public ResultSet queryDB(String qq) {");

		OntModel model = initServletContext.model;

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX music: <http://www.semanticweb.org/MusicOntology#> "
				+ qq;

		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);

		return qe;
	}
	
	public String cleanId(String dirty) {
		return dirty.substring( dirty.indexOf("#") + 1, dirty.length());
	}
	
	public String cleanLiteral(String dirty) {
		return dirty.substring(0, dirty.indexOf("^^"));
	}

}
