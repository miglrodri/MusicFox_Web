package com.musicfox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.cookie.Cookie;

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
	private static int temp;

	static JavaBean search(HttpServletRequest request, String[] query) {

		mybean = new JavaBean();

		// 404 artists 114041 albums 220406 tracks

		/**
		 * Pedidos GET
		 */
		if (request != null && request.getParameter("artistid") != null) {
			String artist_id = request.getParameter("artistid");
			artistIDSearch(artist_id);
		} else if (request != null && request.getParameter("albumid") != null) {
			String album_id = request.getParameter("albumid");
			albumIDSearch(album_id);
		} else if (request != null && request.getParameter("trackid") != null) {
			String track_id = request.getParameter("trackid");
			trackIDSearch(track_id);
		} else if (request != null && request.getParameter("genre") != null) {
			String genre_selected;
			genre_selected = request.getParameter("genre");
			if (genre_selected.toLowerCase().equals("rnb")) {
				genre_selected = "R&B";
			}
			genreOrDecadeSearch(genre_selected, "genre");
		} else if (request != null && request.getParameter("decade") != null) {
			String decade_selected;
			decade_selected = request.getParameter("decade");
			genreOrDecadeSearch(decade_selected, "decade");
		}
		/**
		 * Pedidos POST (semantic search)
		 */
		else if (query != null && query[0].equals("new")) {
			semanticSearch(query[1]);
		} else {
			mybean.setPageType("homepage");
			mybean.setOption(null);
		}
		return mybean;
	}

	/**
	 * Method to construct SPARQL query
	 * 
	 * @param POST
	 * @return
	 */
	public static JavaBean semanticSearch(String query) {
		/**
		 * QUERY SPARQL example: PEARL(nothing) JAM(nothing) ALBUM(class)
		 * ROCK(property=genre) 1992(property=decade)
		 */

		/**
		 * TODO Implementar caso queiramos procurar sintáticamente:
		 * "keit country" -> interpretar tudo dentro das aspas como uma string
		 */

		System.out.println("\n\n\n\n########semanticSearch(String " + query
				+ ")");

		String SPARQL_QUERY = "";
		String select = " SELECT ?s ";
		String where = " WHERE { ";
		String filter = " FILTER ( ";
		String order = " ORDER BY ";
		String nothing_filter = "";

		/**
		 * Code to get exact searches! example query: <rock "track">
		 */
		int firstIndex = 0;
		int lastIndex = 0;
		String exactSearch = "";
		boolean error = false;
		try {
			firstIndex = query.indexOf("\"", 0);
			lastIndex = query.indexOf("\"", firstIndex + 1);
			exactSearch = query.substring(firstIndex + 1, lastIndex);
		} catch (Exception e) {
			// e.printStackTrace();
			error = true;
			exactSearch = "";
		}
		if (!error) {
			nothing_filter = exactSearch;
		}
		
		/**
		 * Code to retrieve all genre types available
		 */
		// searchQuery =
		// " SELECT DISTINCT   ?value  WHERE {  ?s music:hasMainGenre ?value   } ";
		// qe = queryDB(searchQuery);
		// results = qe.execSelect();
		// while (results.hasNext()) {
		// QuerySolution binding = results.nextSolution();
		// String temp_value = binding.get("value").toString();
		// System.out.println("genre value: "+temp_value);
		// }
		// qe.close();

		ArrayList<String> ontology_genres = new ArrayList<String>(
				Arrays.asList("reggae", "ska", "vocals", "jazz", "country",
						"pop", "soul", "r&b", "rock", "rap", "hip hop",
						"latin", "electronica", "dance", "alternative",
						"indie", "soundtracks", "world", "blues", "classical",
						"opera", "new age", "folk"));

		// datatypeProperties
		// String QUERY_TO_GET_ALL_PROPERTIES =
		// "SELECT DISTINCT ?s WHERE {  ?s rdf:type owl:DatatypeProperty   } ";

		String active_class = "";
		ArrayList<String> queriesToExecute = new ArrayList<String>();
		boolean allClassSearch = false;
		boolean isClass;
		boolean isProperty;
		boolean isDecade = false;
		boolean isGenre = false;
		boolean isNothing = false;
		boolean firstNothing = true;
		boolean firstFilter = true;
		int genre_index = 0;
		int decade_index = 0;

		/**
		 * For each token
		 */
		for (String token : query.split(" ")) {
			System.out.println(token);
			isClass = false;
			isProperty = false;
			isNothing = false;
			
			if (token.contains("\"")) {
				continue;
			}

			/**
			 * Verify if its a class
			 */
			if (token.equals("artist") || token.equals("artists")) {
				System.out.println(token + " is class music:Artist");
				active_class = "artist";
				isClass = true;
				where += " ?s rdf:type music:Artist. ";
				where += " ?s music:hasName ?artistvalue. ";
				select += " ?artistvalue ";
				order += " ?artistvalue ";

			} else if (token.equals("album") || token.equals("albums")) {
				System.out.println(token + " is class music:Album");
				active_class = "album";
				isClass = true;
				where += " ?s rdf:type music:Album. ";
				where += " ?s music:hasTitle ?albumvalue. ";
				select += " ?albumvalue ";
				order += " ?albumvalue ";

			} else if (token.equals("track") || token.equals("tracks")) {
				System.out.println(token + " is class music:Track");
				active_class = "track";
				isClass = true;
				where += " ?s rdf:type music:Track. ";
				where += " ?s music:hasTitle ?trackvalue. ";
				select += " ?trackvalue ";
				order += " ?trackvalue ";
			}

			/**
			 * Verify if its a property
			 */
			// verify if its a genre
			else if (ontology_genres.contains(token)) {
				System.out.println(token + " is property(genre)");
				isGenre = true;
				isProperty = true;
				where += " ?s music:hasMainGenre ?genre" + genre_index + ". ";
				if (!firstFilter) {
					filter += " && ";
				}
				filter += " regex( ?genre" + genre_index + ", \"" + token
						+ "\", \"i\" ) ";
				firstFilter = false;
				genre_index++;
			}
			// verify if its a DECADE
			else if (isDecadeQuestion(token)) {
				System.out.println(token + " is property(decade)");
				isDecade = true;
				isProperty = true;
				where += " ?s music:hasDecade ?decade" + decade_index + ". ";
				if (!firstFilter) {
					filter += " && ";
				}
				filter += " regex( ?decade" + decade_index + ", \"" + token
						+ "\", \"i\" ) ";
				firstFilter = false;
				decade_index++;
			}

			/**
			 * Verify if its a value
			 */
			// verify if its a NOTHING
			else if (!isClass && !isProperty) {
				isNothing = true;
				System.out.println(token + " is nothing(value)");
				if (firstNothing) {
					nothing_filter = token;
				} else {
					nothing_filter += " " + token;
				}
				firstNothing = false;
			}

		}

		/**
		 * here has finished reading all tokens!
		 */

		/**
		 * Verify if its a genre or decade
		 */
		if (isGenre || isDecade) {
			/**
			 * Verify if its an artist, album or track
			 */
			// artist class
			if (active_class.equals("artist")) {
				// do nothing for now
			}

			// album class
			else if (active_class.equals("album")) {
				where = " WHERE { ";
				for (int i = 0; i < genre_index; i++) {
					where += " ?other music:hasMainGenre ?genre" + i + ". ";
				}
				for (int i = 0; i < decade_index; i++) {
					where += " ?s music:hasDecade ?decade" + i + ". ";
				}
				if (isGenre) {
					where += " ?other rdf:type music:Artist. ";
					where += " ?other music:producesAlbum ?s. ";
					where += " ?s music:hasTitle ?albumvalue. ";
				} else if (isDecade) {
					where += " ?s music:hasTitle ?albumvalue. ";
				}
			}

			// track class
			else if (active_class.equals("track")) {
				where = " WHERE { ";
				for (int i = 0; i < genre_index; i++) {
					where += " ?other music:hasMainGenre ?genre" + i + ". ";
				}
				for (int i = 0; i < decade_index; i++) {
					where += " ?other music:hasDecade ?decade" + i + ". ";
				}
				if (isGenre) {
					where += " ?other rdf:type music:Artist. ";
					where += " ?other music:writesTrack ?s. ";
					where += " ?s music:hasTitle ?trackvalue. ";
				} else if (isDecade) {
					where += " ?other rdf:type music:Album. ";
					where += " ?other music:hasTrack ?s. ";
					where += " ?s music:hasTitle ?trackvalue. ";
				}
			}

			// no classes associated
			else {
				System.out
						.println("perform 3 queries!! all classes. but look for nothing content too.");
				allClassSearch = true;

				// reset
				String temp_where = "";
				String temp_select = "";
				String temp_filter = "";
				String temp_query = "";
				String temp_order = "";

				// prepare artists query
				temp_where = " WHERE { ";
				for (int i = 0; i < genre_index; i++) {
					temp_where += " ?s music:hasMainGenre ?genre" + i + ". ";
				}
				for (int i = 0; i < decade_index; i++) {
					temp_where += " ?s music:hasDecade ?decade" + i + ". ";
				}
				temp_where += " ?s rdf:type music:Artist. ";
				temp_where += " ?s music:hasName ?artistvalue. ";
				temp_select += " ?artistvalue ";
				temp_order += " ?artistvalue ";

				if (!nothing_filter.isEmpty()) {
					if (!firstFilter) {
						temp_filter += " && ";
					}
					temp_filter += " regex( ?artistvalue, \"" + nothing_filter
							+ "\", \"i\" ) ";
				}

				temp_query = select + temp_select + temp_where + filter
						+ temp_filter + " ) " + " } " + order + temp_order;

				System.out.println("added query: " + temp_query);
				queriesToExecute.add(temp_query);

				// reset
				temp_where = "";
				temp_select = "";
				temp_filter = "";
				temp_query = "";
				temp_order = "";

				// prepare albums query
				temp_where = " WHERE { ";
				for (int i = 0; i < genre_index; i++) {
					temp_where += " ?other music:hasMainGenre ?genre" + i
							+ ". ";
				}
				for (int i = 0; i < decade_index; i++) {
					temp_where += " ?s music:hasDecade ?decade" + i + ". ";
				}
				temp_where += " ?other rdf:type music:Artist. ";
				temp_where += " ?other music:producesAlbum ?s. ";
				temp_where += " ?s rdf:type music:Album. ";
				temp_where += " ?s music:hasTitle ?albumvalue. ";
				temp_select += " ?albumvalue ";
				temp_order += " ?albumvalue ";

				if (!nothing_filter.isEmpty()) {
					if (!firstFilter) {
						temp_filter += " && ";
					}
					temp_filter += " regex( ?albumvalue, \"" + nothing_filter
							+ "\", \"i\" ) ";
				}

				temp_query = select + temp_select + temp_where + filter
						+ temp_filter + " ) " + " } " + order + temp_order;

				System.out.println("added query: " + temp_query);
				queriesToExecute.add(temp_query);

				// reset
				temp_where = "";
				temp_select = "";
				temp_filter = "";
				temp_query = "";
				temp_order = "";

				// prepare tracks query
				temp_where = " WHERE { ";
				for (int i = 0; i < genre_index; i++) {
					temp_where += " ?other music:hasMainGenre ?genre" + i
							+ ". ";
				}
				for (int i = 0; i < decade_index; i++) {
					temp_where += " ?other music:hasDecade ?decade" + i + ". ";
				}
				if (isGenre) {
					temp_where += " ?other rdf:type music:Artist. ";
					temp_where += " ?other music:writesTrack ?s. ";
					temp_where += " ?s rdf:type music:Track. ";
					temp_where += " ?s music:hasTitle ?trackvalue. ";
					temp_select += " ?trackvalue ";
					temp_order += " ?trackvalue ";
				} else if (isDecade) {
					temp_where += " ?other rdf:type music:Album. ";
					temp_where += " ?other music:hasTrack ?s. ";
					temp_where += " ?s rdf:type music:Track. ";
					temp_where += " ?s music:hasTitle ?trackvalue. ";
					temp_select += " ?trackvalue ";
					temp_order += " ?trackvalue ";
				}

				if (!nothing_filter.isEmpty()) {
					if (!firstFilter) {
						temp_filter += " && ";
					}
					temp_filter += " regex( ?trackvalue, \"" + nothing_filter
							+ "\", \"i\" ) ";

				}

				temp_query = select + temp_select + temp_where + filter
						+ temp_filter + " ) " + " } " + order + temp_order;

				System.out.println("added query: " + temp_query);
				queriesToExecute.add(temp_query);
			}
		}

		/**
		 * Verify if nothing variable has some value
		 */
		if (!nothing_filter.isEmpty()) {
			/**
			 * Verify if its an artist, album or track
			 */
			// artist class
			if (active_class.equals("artist")) {
				if (!firstFilter) {
					filter += " && ";
				}
				filter += " regex( ?artistvalue, \"" + nothing_filter
						+ "\", \"i\" ) ";
				firstFilter = false;
			}

			// album class
			else if (active_class.equals("album")) {
				if (!firstFilter) {
					filter += " && ";
				}
				filter += " regex( ?albumvalue, \"" + nothing_filter
						+ "\", \"i\" ) ";
				firstFilter = false;
			}

			// track class
			else if (active_class.equals("track")) {
				if (!firstFilter) {
					filter += " && ";
				}
				filter += " regex( ?trackvalue, \"" + nothing_filter
						+ "\", \"i\" ) ";
				firstFilter = false;
			}

			// no classes associated
			else {
				System.out
						.println("perform 3 queries!! all classes. check if nothing was already set earlier.");
				if (allClassSearch == false) {
					allClassSearch = true;

					// reset
					String temp_where = "";
					String temp_select = "";
					String temp_filter = "";
					String temp_query = "";
					String temp_order = "";

					// prepare artists query
					temp_where += " ?s rdf:type music:Artist. ";
					temp_where += " ?s music:hasName ?artistvalue. ";
					temp_select += " ?artistvalue ";
					temp_order += " ?artistvalue ";
					if (!firstFilter) {
						temp_filter += " && ";
					}
					temp_filter += " regex( ?artistvalue, \"" + nothing_filter
							+ "\", \"i\" ) ";
					temp_query = select + temp_select + where + temp_where
							+ filter + temp_filter + " ) " + " } " + order
							+ temp_order;

					System.out.println("added query: " + temp_query);
					queriesToExecute.add(temp_query);

					// reset
					temp_where = "";
					temp_select = "";
					temp_filter = "";
					temp_query = "";
					temp_order = "";

					// prepare albums query
					temp_where += " ?s rdf:type music:Album. ";
					temp_where += " ?s music:hasTitle ?albumvalue. ";
					temp_select += " ?albumvalue ";
					temp_order += " ?albumvalue ";
					if (!firstFilter) {
						temp_filter += " && ";
					}
					temp_filter += " regex( ?albumvalue, \"" + nothing_filter
							+ "\", \"i\" ) ";
					temp_query = select + temp_select + where + temp_where
							+ filter + temp_filter + " ) " + " } " + order
							+ temp_order;

					System.out.println("added query: " + temp_query);
					queriesToExecute.add(temp_query);

					// reset
					temp_where = "";
					temp_select = "";
					temp_filter = "";
					temp_query = "";
					temp_order = "";

					// prepare tracks query
					temp_where += " ?s rdf:type music:Track. ";
					temp_where += " ?s music:hasTitle ?trackvalue. ";
					temp_select += " ?trackvalue ";
					temp_order += " ?trackvalue ";
					if (!firstFilter) {
						temp_filter += " && ";
					}
					temp_filter += " regex( ?trackvalue, \"" + nothing_filter
							+ "\", \"i\" ) ";
					temp_query = select + temp_select + where + temp_where
							+ filter + temp_filter + " ) " + " } " + order
							+ temp_order;

					System.out.println("added query: " + temp_query);
					queriesToExecute.add(temp_query);
				}
			}
		}

		// prepare final query
		if (!allClassSearch) {
			SPARQL_QUERY = select + where + filter + " ) " + " } " + order;
			System.out.println("SPARQL QUERY: " + SPARQL_QUERY);
			queriesToExecute.add(SPARQL_QUERY);
		}

		/**
		 * Going to run all queries!
		 */
		temp = 0;
		for (int i = 0; i < queriesToExecute.size(); i++) {
			searchQuery = queriesToExecute.get(i);
			qe = queryDB(searchQuery);
			results = qe.execSelect();

			/**
			 * For each query!
			 */
			while (results.hasNext()) {
				// System.out.println("got results from the query!");
				QuerySolution binding = results.nextSolution();
				boolean isArtistResult = false;
				boolean isAlbumResult = false;
				boolean isTrackResult = false;

				String temp_id = binding.get("s").toString();
				String temp_value = "";

				try {
					temp_value = binding.get("artistvalue").toString();
					isArtistResult = true;
				} catch (Exception e) {
					// e.printStackTrace();
				}
				try {
					temp_value = binding.get("albumvalue").toString();
					isAlbumResult = true;
				} catch (Exception e) {
					// e.printStackTrace();
				}
				try {
					temp_value = binding.get("trackvalue").toString();
					isTrackResult = true;
				} catch (Exception e) {
					// e.printStackTrace();
				}

				SemanticResult sresult = new SemanticResult();
				if (isArtistResult) {
					sresult.setResource_name("a" + cleanLiteral(temp_value));
					sresult.setResource_url("MusicController?artistid="
							+ cleanId(temp_id));
				} else if (isAlbumResult) {
					sresult.setResource_name("b" + cleanLiteral(temp_value));
					sresult.setResource_url("MusicController?albumid="
							+ cleanId(temp_id));
				} else if (isTrackResult) {
					sresult.setResource_name("c" + cleanLiteral(temp_value));
					sresult.setResource_url("MusicController?trackid="
							+ cleanId(temp_id));
				}

				temp++;
				mybean.addToSemanticArray(sresult);
			}
		}
		mybean.setPageType("semantic_search_page");
		mybean.setOption(query);
		mybean.setNumberItems(temp);

		qe.close();

		return mybean;

	}

	/**
	 * Check if the token is a Decade
	 * 
	 * @param token
	 * @return
	 */
	private static boolean isDecadeQuestion(String token) {
		int numero;
		try {
			numero = Integer.parseInt(token);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			return false;
		}
		if (numero > 1900 && numero < 2020 && numero%10==0) {
			return true;
		}
		
		return false;
	}

	/**
	 * Gather information about artists from some genre or decade
	 * 
	 * @param GET
	 *            parameter value
	 * @return
	 */
	private static JavaBean genreOrDecadeSearch(String value, String option) {

		if (option.equals("genre")) {
			searchQuery = "SELECT ?id ?name " + "WHERE {"
					+ "?id music:hasMainGenre ?genre ."
					+ " ?id music:hasName ?name ." + " FILTER regex(?genre, \""
					+ value + "\", \"i\") }" + "ORDER BY ?name";
		} else {
			searchQuery = "SELECT ?id ?name " + "WHERE {"
					+ "?id music:hasDecade ?decade ."
					+ " ?id music:hasName ?name ."
					+ " FILTER regex(?decade, \"" + value + "\", \"i\") }"
					+ "ORDER BY ?name";
		}

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
				temp_artist.setVevoViewsTotal(RecommendationController.getArtistVevoViewsTotalFromName(temp_artist.getName()));
				
				mybean.addToArtistsArray(temp_artist);
				temp++; // incremente o contador do nr de resultados
						// encontrados

			}
			
			mybean.setArtistsArray(orderByViews(mybean.getArtistsArray()));
			
			mybean.setPageType("artist_list_page");
			mybean.setOption(value);
			mybean.setNumberItems(temp);

		} else {
			mybean.setNumberItems(temp);

		}
		qe.close();
		return mybean;
	}

	/**
	 * Gather information about a specific Track!
	 * 
	 * @param track_id
	 * @return
	 */
	private static JavaBean trackIDSearch(String track_id) {
		searchQuery = "SELECT ?id ?title ?index ?duration  WHERE { ?id rdf:type music:Track. 	?id music:hasTitle ?title. ?id music:hasTrackIndex ?index.  ?id music:hasDuration ?duration.	FILTER regex( str(?id), \""
				+ track_id + "\" ) 	}";
		qe = queryDB(searchQuery);
		results = qe.execSelect();
		if (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			Track temp_track = new Track();
			temp_track.setId(cleanId(binding.get("id").toString()));
			temp_track.setTitle(cleanLiteral(binding.get("title").toString()));
			temp_track.setTrackIndex(cleanLiteral(binding.get("index")
					.toString()));
			temp_track.setDuration(cleanLiteral(binding.get("duration")
					.toString()));
			searchQuery = "SELECT ?id ?albumid ?albumtitle ?artistid ?artistname  WHERE { ?id rdf:type music:Track.   ?id music:isPartOf ?albumid.  ?albumid music:hasTitle ?albumtitle.  ?albumid music:isAssembledBy ?artistid.  ?artistid music:hasName ?artistname	FILTER regex( str(?id), \""
					+ track_id + "\" ) 	}";
			QueryExecution qe2 = queryDB(searchQuery);
			ResultSet results2 = qe2.execSelect();
			while (results2.hasNext()) {
				QuerySolution binding2 = results2.nextSolution();

				temp_track.setAlbumId(cleanId(binding2.get("albumid")
						.toString()));
				temp_track.setAlbumName(cleanLiteral(binding2.get("albumtitle")
						.toString()));
				temp_track.setArtistId(cleanId(binding2.get("artistid")
						.toString()));
				temp_track.setArtistName(cleanLiteral(binding2
						.get("artistname").toString()));
			}
			qe2.close();
			mybean.setPageType("track_page");
			mybean.setTrack_information(temp_track);
			mybean.setOption(null);
		}
		qe.close();
		return mybean;
	}

	/**
	 * Gather information about a specific Album!
	 * 
	 * @param album_id
	 * @return
	 */
	private static JavaBean albumIDSearch(String album_id) {
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
			searchQuery = "SELECT ?id ?artistid ?artistname  WHERE { ?id rdf:type music:Album.   ?id music:isAssembledBy ?artistid. ?artistid music:hasName ?artistname	FILTER regex( str(?id), \""
					+ album_id + "\" ) 	}";
			QueryExecution qe2 = queryDB(searchQuery);
			ResultSet results2 = qe2.execSelect();
			while (results2.hasNext()) {
				QuerySolution binding2 = results2.nextSolution();
				temp_album.setArtistId(cleanId(binding2.get("artistid")
						.toString()));
				temp_album.setArtistName(cleanLiteral(binding2
						.get("artistname").toString()));
			}
			qe2.close();
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

	/**
	 * Gather information about a specific Artist!
	 * 
	 * @param artist_id
	 * @return
	 */
	private static JavaBean artistIDSearch(String artist_id) {
		searchQuery = "SELECT ?id ?name ?maingenre ?decade ?gender  WHERE { ?id rdf:type music:Artist. 	?id music:hasName ?name. ?id music:hasMainGenre ?maingenre.  ?id music:hasDecade ?decade. 		?id music:hasGender ?gender.	FILTER regex( str(?id), \""
				+ artist_id + "\" ) 	}";
		qe = queryDB(searchQuery);
		results = qe.execSelect();
		if (results.hasNext()) {
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
			
			// TODO QUESTION? : Aqui só vai haver o retorno de 1 artist, não faz sentido estar a fazer order.
			
			mybean.setArtistsArray(orderByViews(mybean.getArtistsArray()));
		}
		
		
		qe.close();
		return mybean;
	}

	/**
	 * Perform the query to the DataBase
	 * 
	 * @param query
	 * @return
	 */
	public static QueryExecution queryDB(String qq) {
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

	/**
	 * Clean the string removing the prefixes from the ontology ID
	 * 
	 * @param dirty
	 * @return
	 */
	public static String cleanId(String dirty) {
		return dirty.substring(dirty.indexOf("#") + 1, dirty.length());
	}

	/**
	 * Clean the string removing info about rdf:values
	 * 
	 * @param dirty
	 * @return
	 */
	public static String cleanLiteral(String dirty) {
		return dirty.substring(0, dirty.indexOf("^^"));
	}
	
	public static ArrayList<Artist> orderByViews(ArrayList<Artist> artistsArray){
		
		Collections.sort(artistsArray, new CustomComparator());
		
		return artistsArray;
		
	}
	
	public static class CustomComparator implements Comparator<Artist> {

		@Override
		public int compare(Artist arg0, Artist arg1) {
			// TODO Auto-generated method stub
			
			int countA = arg0.getFacebookLikes() + 
					arg0.getFacebookPeopleTalkingAbout() + 
					arg0.getLastFMListeners() + 
					arg0.getLastFMPlayCount() + 
					arg0.getTwitterFollowers() + 
					arg0.getVevoViewsTotal();
			
			int countB = arg1.getFacebookLikes() + 
					arg1.getFacebookPeopleTalkingAbout() + 
					arg1.getLastFMListeners() + 
					arg1.getLastFMPlayCount() + 
					arg1.getTwitterFollowers() + 
					arg1.getVevoViewsTotal();
			
			return countB - countA;
		}
	}
	
}
