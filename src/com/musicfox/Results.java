package com.musicfox;

import javax.servlet.http.HttpServletRequest;

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

public class Results {

	private static String searchQuery;
	private static QueryExecution qe;
	private static ResultSet results;
	private static JavaBean mybean;
	private static EchoNestAPI echoNest = new EchoNestAPI("IE2ZINGXDYWASD9NH");
	private static java.util.List<com.echonest.api.v4.Artist> artists = null;
	private static int temp;

	static JavaBean search(HttpServletRequest request, String[] query) {

		System.out.println("SEMANTIC SEARCH");
		mybean = new JavaBean();
		
		/**
		 * TODO TER CUIDADO COM A PESQUISA S� COM O NOME DA CLASSE:
		 * Exemplo:
		 * Query = "artist" ou Query = "decade"
		 * Mostra todos os artists. � o esperado?
		 */

		// disponibiliza um bean com artistsArray contendo 1 �nico artist
		if ((request != null && request.getParameter("artistid") != null)
				|| (query != null && query[0].equals("artistid"))) {
			// mostrar p�gina de artist
			String artist_id = request.getParameter("artistid");
			artistIDSearch(artist_id);

		} else if (request != null && request.getParameter("albumid") != null) {
			// // mostrar pagina de album
			String album_id = request.getParameter("albumid");
			albumIDSearch(album_id);

		} else if ((request != null && request.getParameter("genre") != null)
				|| (query != null && query[0].equals("genre"))) {
			// mostrar artistas por genero
			String genre_selected;
			if (request != null)
				genre_selected = request.getParameter("genre");
			else
				genre_selected = query[1];
			if (genre_selected.toLowerCase().equals("rnb")) {
				genre_selected = "R&B";
			}
			genreSearch(genre_selected);

		} else if ((request != null && request.getParameter("decade") != null)
				|| (query != null && query[0].equals("decade"))) {
			String decade_selected;
			if (request != null)
				decade_selected = request.getParameter("decade");
			else
				decade_selected = query[1];
			decadeSearch(decade_selected);

		} else if (query != null && query[0].equals("artist")) {
			artistSearch(query[1]);
		} else if (query != null && query[0].equals("all")) {
			// search foi feito sem nenhuma classe associada
			allClassesSearch(query[1]);
		} else {

			mybean.setPageType("homepage");
			mybean.setOption(null);

		}

		return mybean;
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	public static QueryExecution queryDB(String qq) {
		// System.out.println("public ResultSet queryDB(String qq) {");

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

	public static String cleanId(String dirty) {
		return dirty.substring(dirty.indexOf("#") + 1, dirty.length());
	}

	public static String cleanLiteral(String dirty) {
		return dirty.substring(0, dirty.indexOf("^^"));
	}

	private static JavaBean artistSearch(String name) {

		searchQuery = "SELECT ?id ?name WHERE { ?id rdf:type music:Artist. ?id music:hasName ?name. FILTER regex( ?name, \""
				+ name + "\", \"i\" )} ORDER BY ?name";

		qe = queryDB(searchQuery);
		results = qe.execSelect();
		temp = 0;

		while (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			Artist temp_artist = new Artist();
			String temp_artist_id = binding.get("id").toString();
			String temp_artist_name = binding.get("name").toString();

			temp_artist.setId(cleanId(temp_artist_id));
			temp_artist.setName(cleanLiteral(temp_artist_name));
			temp_artist.setCover(getImages(temp_artist_name));
			temp++; // incremente o contador do nr de resultados
			// encontrados
			mybean.addToArtistsArray(temp_artist);
		}

		mybean.setPageType("artist_list_page");
		mybean.setOption(name);
		mybean.setNumberItems(temp);

		qe.close();

		return mybean;
	}
	
	private static JavaBean allClassesSearch(String query) {
		
		System.out.println("private static JavaBean allClassesSearch(String query) { :: " + query);
		
		/**
		 * Gather information about artists
		 */
		
		searchQuery = "SELECT ?id ?name WHERE { ?id rdf:type music:Artist. ?id music:hasName ?name. FILTER regex( ?name, \""
				+ query + "\", \"i\" )} ORDER BY ?name";

		qe = queryDB(searchQuery);
		results = qe.execSelect();
		temp = 0;

		while (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			String temp_artist_id = binding.get("id").toString();
			String temp_artist_name = binding.get("name").toString();

			SemanticResult sresult = new SemanticResult();
			sresult.setResource_name("a" + cleanLiteral(temp_artist_name));
			sresult.setResource_url("/MusicController?artistid=" + cleanId(temp_artist_id));
			temp++; // incremente o contador do nr de resultados
			// encontrados
			mybean.addToSemanticArray(sresult);
		}

		/**
		 * Gather information about albums
		 * TODO implement
		 */

		searchQuery = "SELECT ?id ?name WHERE { ?id rdf:type music:Album. ?id music:hasTitle ?name. FILTER regex( ?name, \""
				+ query + "\", \"i\" )} ORDER BY ?name";

		qe = queryDB(searchQuery);
		results = qe.execSelect();

		while (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			String temp_album_id = binding.get("id").toString();
			String temp_album_name = binding.get("name").toString();

			SemanticResult sresult = new SemanticResult();
			sresult.setResource_name("b" + cleanLiteral(temp_album_name));
			sresult.setResource_url("/MusicController?albumid=" + cleanId(temp_album_id));
			temp++; // incremente o contador do nr de resultados
			// encontrados
			mybean.addToSemanticArray(sresult);
		}
		
		/**
		 * Gather information about tracks
		 * TODO implement
		 */
		
		searchQuery = "SELECT ?id ?name WHERE { ?id rdf:type music:Track. ?id music:hasTitle ?name. FILTER regex( ?name, \""
				+ query + "\", \"i\" )} ORDER BY ?name";

		qe = queryDB(searchQuery);
		results = qe.execSelect();
		
		while (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			String temp_track_id = binding.get("id").toString();
			String temp_track_name = binding.get("name").toString();

			SemanticResult sresult = new SemanticResult();
			sresult.setResource_name("c" + cleanLiteral(temp_track_name));
			sresult.setResource_url("/MusicController?trackid=" + cleanId(temp_track_id));
			temp++; // incremente o contador do nr de resultados
			// encontrados
			mybean.addToSemanticArray(sresult);
		}
		
		mybean.setPageType("semantic_search_page");
		mybean.setOption(query);
		mybean.setNumberItems(temp);

		qe.close();

		return mybean;
	}

	private static JavaBean decadeSearch(String decade_selected) {
		// mostrar artistas por decade
		// System.out.println("param decade: " + decade_selected);
		searchQuery = "SELECT ?id ?name " + "WHERE {"
				+ "?id music:hasDecade ?decade ."
				+ " ?id music:hasName ?name ." + " FILTER regex(?decade, \""
				+ decade_selected + "\", \"i\") }" + "ORDER BY ?name";

		qe = queryDB(searchQuery);
		results = qe.execSelect();
		temp = 0;
		if (results.hasNext()) {
			while (results.hasNext()) {
				QuerySolution binding = results.nextSolution();
				String temp_artist_id = binding.get("id").toString();
				String temp_artist_name = binding.get("name").toString();

				Artist temp_artist = new Artist();
				temp_artist.setId(cleanId(temp_artist_id));
				temp_artist.setName(cleanLiteral(temp_artist_name));
				temp_artist.setCover(getImages(temp_artist_name));

				mybean.addToArtistsArray(temp_artist);
				temp++; // incremente o contador do nr de resultados
						// encontrados
			}

			mybean.setPageType("artist_list_page");
			mybean.setOption(decade_selected);
			mybean.setNumberItems(temp);

		} else {
			mybean.setNumberItems(temp); // devolve que encontrou 0 items

		}
		qe.close();

		return mybean;
	}

	private static JavaBean genreSearch(String genre_selected) {
		// System.out.println("param genre: " + genre_selected);
		searchQuery = "SELECT ?id ?name " + "WHERE {"
				+ "?id music:hasMainGenre ?genre ."
				+ " ?id music:hasName ?name ." + " FILTER regex(?genre, \""
				+ genre_selected + "\", \"i\") }" + "ORDER BY ?name";

		qe = queryDB(searchQuery);
		results = qe.execSelect();

		temp = 0;
		if (results.hasNext()) {
			while (results.hasNext()) {
				QuerySolution binding = results.nextSolution();
				String temp_artist_id = binding.get("id").toString();
				String temp_artist_name = binding.get("name").toString();

				Artist temp_artist = new Artist();
				temp_artist.setId(cleanId(temp_artist_id));
				temp_artist.setName(cleanLiteral(temp_artist_name));
				temp_artist.setCover(getImages(temp_artist_name));

				mybean.addToArtistsArray(temp_artist);
				temp++; // incremente o contador do nr de resultados
						// encontrados

			}

			mybean.setPageType("artist_list_page");
			mybean.setOption(genre_selected);
			mybean.setNumberItems(temp);

		} else {
			mybean.setNumberItems(temp);

		}
		qe.close();
		return mybean;
	}

	private static JavaBean albumIDSearch(String album_id) {
		// System.out.println("param album_id = " + album_id);
		searchQuery = "SELECT ?id ?title ?release ?ntracks ?decade  WHERE { ?id rdf:type music:Album. 	?id music:hasTitle ?title. ?id music:hasReleaseDate ?release.  ?id music:hasNumberOfTracks ?ntracks.	?id music:hasDecade ?decade.	FILTER regex( str(?id), \""
				+ album_id + "\" ) 	}";

		qe = queryDB(searchQuery);
		results = qe.execSelect();
		if (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			Album temp_album = new Album();
			temp_album.setId(cleanId(binding.get("id").toString()));
			temp_album.setTitle(cleanLiteral(binding.get("title").toString()));
			temp_album.setReleaseDate(cleanLiteral(binding.get("release")
					.toString()));
			temp_album.setNumberOfTracks(Integer.parseInt(cleanLiteral(binding
					.get("ntracks").toString())));
			temp_album
					.setDecade(cleanLiteral(binding.get("decade").toString()));

			searchQuery = "SELECT ?trackid ?tracktitle ?trackindex ?duration 	WHERE { ?id rdf:type music:Album. 	?trackid rdf:type music:Track.  ?id music:hasTrack ?trackid. 	?trackid music:hasTitle ?tracktitle. ?trackid music:hasTrackIndex ?trackindex. ?trackid music:hasDuration ?duration  	FILTER(str(?id)=\"http://www.semanticweb.org/MusicOntology#"
					+ album_id + "\") } ORDER BY ?trackindex";

			QueryExecution qe1 = queryDB(searchQuery);
			ResultSet results1 = qe1.execSelect();
			while (results1.hasNext()) {
				QuerySolution binding1 = results1.nextSolution();
				Track temp_track = new Track();
				temp_track.setId(cleanId(binding1.get("trackid").toString()));
				temp_track.setTitle(cleanLiteral(binding1.get("tracktitle")
						.toString()));
				temp_track.setTrackIndex(cleanLiteral(binding1
						.get("trackindex").toString()));
				temp_track.setDuration(cleanLiteral(binding1.get("duration")
						.toString()));
				temp_album.addToTracksArray(temp_track);
			}
			qe1.close();

			mybean.setPageType("album_page");
			mybean.setAlbum_information(temp_album);
			mybean.setOption(null);
		}
		qe.close();
		return mybean;
	}

	private static JavaBean artistIDSearch(String artist_id) {
		// System.out.println("param artist_id = " + artist_id);
		searchQuery = "SELECT ?id ?name ?maingenre ?decade ?gender  WHERE { ?id rdf:type music:Artist. 	?id music:hasName ?name. ?id music:hasMainGenre ?maingenre.  ?id music:hasDecade ?decade. 		?id music:hasGender ?gender.	FILTER regex( str(?id), \""
				+ artist_id + "\" ) 	}";

		qe = queryDB(searchQuery);
		results = qe.execSelect();
		// System.out.println(searchQuery);

		if (results.hasNext()) {
			// System.out.println("######tem dados de artista para mostrar, stats");
			QuerySolution binding = results.nextSolution();
			Artist temp_artist = new Artist();

			temp_artist.setId(cleanId(binding.get("id").toString()));
			temp_artist.setName(cleanLiteral(binding.get("name").toString()));
			temp_artist.setMainGenre(cleanLiteral(binding.get("maingenre")
					.toString()));
			temp_artist
					.setDecade(cleanLiteral(binding.get("decade").toString()));
			temp_artist
					.setGender(cleanLiteral(binding.get("gender").toString()));

			searchQuery = "SELECT ?vevourl ?vevovtotal ?vevolm ?twitterurl ?twitterfoll ?fburl ?fbpta ?fblikes  WHERE { ?id rdf:type music:Artist.   ?id music:hasVevoUrl ?vevourl. 	?id music:hasVevoViewsLastMonth ?vevolm. ?id music:hasVevoViewsTotal ?vevovtotal.  ?id music:hasTwitterUrl ?twitterurl. ?id music:hasTwitterFollowers ?twitterfoll. ?id music:hasFacebookUrl ?fburl.  ?id music:hasFacebookPeopleTalkingAbout ?fbpta. ?id music:hasFacebookLikes ?fblikes.	?id music:hasLastFMUrl ?lastfmurl. ?id music:hasLastFMListeners ?lastfmlist. ?id music:hasLastFMPlayCount ?lastfmcount.	FILTER regex( str(?id), \""
					+ artist_id + "\" ) 	}";

			QueryExecution qe1 = queryDB(searchQuery);
			ResultSet results1 = qe1.execSelect();

			if (results1.hasNext()) {
				binding = results1.nextSolution();

				temp_artist.setVevoUrl(cleanLiteral(binding.get("vevourl")
						.toString()));
				temp_artist
						.setVevoViewsLastMonth(Integer
								.parseInt(cleanLiteral(binding.get("vevolm")
										.toString())));
				temp_artist.setVevoViewsTotal(Integer
						.parseInt(cleanLiteral(binding.get("vevovtotal")
								.toString())));
				temp_artist.setTwitterUrl(cleanLiteral(binding
						.get("twitterurl").toString()));
				temp_artist.setTwitterFollowers(Integer
						.parseInt(cleanLiteral(binding.get("twitterfoll")
								.toString())));
				temp_artist.setFacebookUrl(cleanLiteral(binding.get("fburl")
						.toString()));
				temp_artist
						.setFacebookPeopleTalkingAbout(Integer
								.parseInt(cleanLiteral(binding.get("fbpta")
										.toString())));
				temp_artist.setFacebookLikes(Integer
						.parseInt(cleanLiteral(binding.get("fblikes")
								.toString())));
			}

			searchQuery = "SELECT ?albumid ?albumtitle WHERE { ?id rdf:type music:Artist. ?id music:producesAlbum ?albumid. ?albumid music:hasTitle ?albumtitle FILTER(str(?id)=\"http://www.semanticweb.org/MusicOntology#"
					+ artist_id + "\") }";

			qe1.close();

			qe1 = queryDB(searchQuery);
			results1 = qe1.execSelect();

			while (results1.hasNext()) {

				binding = results1.nextSolution();
				String temp_album_id = binding.get("albumid").toString();
				String temp_album_title = binding.get("albumtitle").toString();

				Album temp_album = new Album();
				temp_album.setId(cleanId(temp_album_id));
				temp_album.setTitle(cleanLiteral(temp_album_title));

				temp_artist.addToAlbumsArray(temp_album);
			}

			qe1.close();

			mybean.setPageType("artist_page");
			mybean.addToArtistsArray(temp_artist);
			mybean.setOption(null);
		}
		qe.close();
		return mybean;
	}

	private static String getImages(String temp_artist_name) {
		String image = null;
		// try {
		// String query1 = temp_artist_name.substring(0,
		// temp_artist_name.indexOf("^^"));
		// artists = echoNest.searchArtists(query1);
		// if (artists.size() > 0) {
		// java.util.List<Image> images = artists.get(0).getImages();
		// if (images.size() > 0) {
		// image = images.get(0).getURL();
		// System.out.println(image);
		// }
		// }
		// } catch (EchoNestException e) {
		// e.printStackTrace();
		// }

		return image;
	}
}
