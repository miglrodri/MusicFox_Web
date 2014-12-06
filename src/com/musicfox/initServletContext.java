package com.musicfox;

import java.io.InputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class initServletContext implements ServletContextListener {

	static OntModel model;
	final String file = "/MusicOntologyWithIndividuals.owl";
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Servlet Context initialized");
		InputStream in = FileManager.get().open(arg0.getServletContext().getRealPath(file));
		model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
	
		model.read(in,null);
		
	}

}
