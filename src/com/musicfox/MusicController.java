package com.musicfox;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String genre_selected = request.getParameter("genre");
		JavaBean mybean = new JavaBean();
		mybean.setOption(genre_selected);
		request.setAttribute("mybean", mybean);
		
		getServletContext().getRequestDispatcher("/HomeView.jsp").forward(request, response);
		
		
		
		
		// Open the bloggers RDF graph from the filesystem
		InputStream in = new FileInputStream(new File("bloggers.rdf"));

		String inputFileName = "MusicOntology.owl";
		String SOURCE = "http://www.semanticweb.org/MusicOntology";
		String NS = SOURCE + "#";
		String outFileName = "MusicOntologyWithIndividuals.owl";
		final InputStream inputStream = FileManager.get().open(inputFileName);
		final OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, null);

		model.read(inputStream, SOURCE);

		// Create a new query
		String queryString = 
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
			"SELECT ?url " +
			"WHERE {" +
			"      ?contributor foaf:name \"Jon Foobar\" . " +
			"      ?contributor foaf:weblog ?url . " +
			"      }";

//		String simpleQuery =
//				"SELECT ?x  " +
//				"WHERE { ?x" +
//				"	<http://www.w3.org/2001/vcard-rdf/3.0#FN> John Smith";
//				" };";
		
		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();

		// Output query results	
		ResultSetFormatter.out(System.out, results, query);

		// Important - free up resources used running the query
		qe.close();
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//	}

}
