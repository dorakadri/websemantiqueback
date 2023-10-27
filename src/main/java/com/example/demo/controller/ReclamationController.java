package com.example.demo.controller;

import java.io.ByteArrayOutputStream;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.tools.JenaEngine;

//import com.example.demo.tools.JenaEngine;

@RestController

@RequestMapping("/troc")
@CrossOrigin(origins = "http://localhost:3001")
public class ReclamationController {


    @GetMapping("/reclamation")
    public String getReclamations() {
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file

        // Define the SPARQL query to retrieve data for individual reclamations
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?reclamation ?content ?status ?hasResponseContent\n" +  // Changed the name to hasResponseContent
                "WHERE {\n" +
                "  ?reclamation rdf:type ns:Reclamation .\n" +
                "  ?reclamation ns:content ?content .\n" +
                "  ?reclamation ns:status ?status .\n" +
                "  ?reclamation ns:hasResponse ?hasResponse .\n" +  // Get the URI of the response
                "  ?hasResponse ns:content ?hasResponseContent .\n" +  // Get the content of the response
                "}";

        // Load the ontology model
        Model model = FileManager.get().loadModel(ontologyFile);

        // Create a QueryExecution to execute the SPARQL query
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);

        // Execute the query and get the results
        ResultSet results = qexec.execSelect();

        // Convert the ResultSet to JSON
        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject reclamationObject = new JSONObject();

            // Retrieve and add individual attributes to the JSON structure
            reclamationObject.put("content", solution.get("content").toString());
            reclamationObject.put("status", solution.get("status").toString());

            // Get the content of the response
            reclamationObject.put("hasResponseContent", solution.get("hasResponseContent").toString());

            resultArray.put(reclamationObject);
        }

        return resultArray.toString();
    }

    @GetMapping("/reponse")
    public String getReponses() {
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file

        // Define the SPARQL query to retrieve data for individual reponses
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?reponse ?content\n" +
                "WHERE {\n" +
                "  ?reponse rdf:type ns:Reponse .\n" +
                "  ?reponse ns:content ?content .\n" +
                "}";

        // Load the ontology model
        Model model = FileManager.get().loadModel(ontologyFile);

        // Create a QueryExecution to execute the SPARQL query
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);

        // Execute the query and get the results
        ResultSet results = qexec.execSelect();

        // Convert the ResultSet to JSON
        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject reponseObject = new JSONObject();

            // Retrieve and add the "content" attribute to the JSON structure
            reponseObject.put("content", solution.get("content").toString());

            resultArray.put(reponseObject);
        }

        return resultArray.toString();
    }


}
