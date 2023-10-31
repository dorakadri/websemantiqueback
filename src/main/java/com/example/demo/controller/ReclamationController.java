package com.example.demo.controller;

import java.io.ByteArrayOutputStream;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.tools.JenaEngine;

//import com.example.demo.tools.JenaEngine;

@RestController

@RequestMapping("/troc")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class ReclamationController {
    @GetMapping("/reclamation/exchange")
    public String getExchangesReclamations() {
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file

        // Define the SPARQL query to retrieve data for individual reclamations
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?Exchangesclaims ?content ?status ?hasResponseContent ?title\n" +  // Include the title in the query
                "WHERE {\n" +
                "  ?Exchangesclaims rdf:type ns:Exchangesclaims .\n" +
                "  ?Exchangesclaims ns:content ?content .\n" +
                "  ?Exchangesclaims ns:status ?status .\n" +
                "  ?Exchangesclaims ns:hasResponse ?hasResponse .\n" +
                "  ?hasResponse ns:content ?hasResponseContent .\n" +
                "  ?Exchangesclaims ns:title ?title .\n" +  // Retrieve the title
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
            reclamationObject.put("title", solution.get("title").toString()); // Add the title
            reclamationObject.put("content", solution.get("content").toString());
            reclamationObject.put("status", solution.get("status").toString());
            reclamationObject.put("hasResponseContent", solution.get("hasResponseContent").toString());

            resultArray.put(reclamationObject);
        }

        return resultArray.toString();
    }

    @GetMapping("/reclamation/post")
    public String getpostReclamations() {
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file

        // Define the SPARQL query to retrieve data for individual reclamations
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?PostsClaims ?content ?status ?hasResponseContent ?title\n" +  // Include the title in the query
                "WHERE {\n" +
                "  ?PostsClaims rdf:type ns:PostsClaims .\n" +
                "  ?PostsClaims ns:content ?content .\n" +
                "  ?PostsClaims ns:status ?status .\n" +
                "  ?PostsClaims ns:hasResponse ?hasResponse .\n" +
                "  ?hasResponse ns:content ?hasResponseContent .\n" +
                "  ?PostsClaims ns:title ?title .\n" +  // Retrieve the title
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
            reclamationObject.put("title", solution.get("title").toString()); // Add the title
            reclamationObject.put("content", solution.get("content").toString());
            reclamationObject.put("status", solution.get("status").toString());
            reclamationObject.put("hasResponseContent", solution.get("hasResponseContent").toString());

            resultArray.put(reclamationObject);
        }

        return resultArray.toString();
    }
    @GetMapping("/reclamation/exchange/search")
    public String getReclamationsExchanges(@RequestParam(name = "title", required = false) String titleToSearch) {
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file

        // Define the SPARQL query to retrieve data for individual reclamations with optional title filtering
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?Exchangesclaims ?content ?status ?hasResponseContent ?title\n" +  // Include the title in the query
                "WHERE {\n" +
                "  ?Exchangesclaims rdf:type ns:Exchangesclaims .\n" +
                "  ?Exchangesclaims ns:content ?content .\n" +
                "  ?Exchangesclaims ns:status ?status .\n" +
                "  ?Exchangesclaims ns:hasResponse ?hasResponse .\n" +
                "  ?hasResponse ns:content ?hasResponseContent .\n" +
                "  ?Exchangesclaims ns:title ?title .\n" +  // Retrieve the title
                (titleToSearch != null ? "  FILTER(contains(?title, \"" + titleToSearch + "\"))\n" : "") + // Filter by title if provided
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
            reclamationObject.put("title", solution.get("title").toString()); // Add the title
            reclamationObject.put("content", solution.get("content").toString());
            reclamationObject.put("status", solution.get("status").toString());
            reclamationObject.put("hasResponseContent", solution.get("hasResponseContent").toString());

            resultArray.put(reclamationObject);
        }

        return resultArray.toString();
    }

    @GetMapping("/reclamation/post/search")
    public String getReclamationsPosts(@RequestParam(name = "title", required = false) String titleToSearch) {
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file

        // Define the SPARQL query to retrieve data for individual reclamations with optional title filtering
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?PostsClaims ?content ?status ?hasResponseContent ?title\n" +  // Include the title in the query
                "WHERE {\n" +
                "  ?PostsClaims rdf:type ns:PostsClaims .\n" +
                "  ?PostsClaims ns:content ?content .\n" +
                "  ?PostsClaims ns:status ?status .\n" +
                "  ?PostsClaims ns:hasResponse ?hasResponse .\n" +
                "  ?hasResponse ns:content ?hasResponseContent .\n" +
                "  ?PostsClaims ns:title ?title .\n" +  // Retrieve the title
                (titleToSearch != null ? "  FILTER(contains(?title, \"" + titleToSearch + "\"))\n" : "") + // Filter by title if provided
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
            reclamationObject.put("title", solution.get("title").toString()); // Add the title
            reclamationObject.put("content", solution.get("content").toString());
            reclamationObject.put("status", solution.get("status").toString());
            reclamationObject.put("hasResponseContent", solution.get("hasResponseContent").toString());

            resultArray.put(reclamationObject);
        }

        return resultArray.toString();
    }



    @GetMapping("/reclamation/exchange/byStatus")
    public String getReclamationsExchangesByStatus(@RequestParam(value = "status", required = false) String status) {
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file

        // Check if the "status" query parameter is provided
        if (status == null || status.isEmpty()) {
            // Handle the case where "status" is not provided or is empty
            return "Please provide a valid 'status' parameter.";
        }

        // Define the SPARQL query to retrieve data for individual reclamations by status
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?Exchangesclaims ?title ?content ?status ?hasResponseContent\n" +
                "WHERE {\n" +
                "  ?Exchangesclaims rdf:type ns:Exchangesclaims .\n" +
                "  ?Exchangesclaims ns:title ?title .\n" +
                "  ?Exchangesclaims ns:content ?content .\n" +
                "  ?Exchangesclaims ns:status ?status .\n" +
                "  FILTER (?status = '" + status + "') .\n" + // Filter by the specified status
                "  ?Exchangesclaims ns:hasResponse ?hasResponse .\n" +
                "  ?hasResponse ns:content ?hasResponseContent .\n" +
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
            reclamationObject.put("title", solution.get("title").toString());
            reclamationObject.put("content", solution.get("content").toString());
            reclamationObject.put("status", solution.get("status").toString());
            reclamationObject.put("hasResponseContent", solution.get("hasResponseContent").toString());

            resultArray.put(reclamationObject);
        }

        return resultArray.toString();
    }

    @GetMapping("/reclamation/post/byStatus")
    public String getReclamationspostsByStatus(@RequestParam(value = "status", required = false) String status) {
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file

        // Check if the "status" query parameter is provided
        if (status == null || status.isEmpty()) {
            // Handle the case where "status" is not provided or is empty
            return "Please provide a valid 'status' parameter.";
        }

        // Define the SPARQL query to retrieve data for individual reclamations by status
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?PostsClaims ?title ?content ?status ?hasResponseContent\n" +
                "WHERE {\n" +
                "  ?PostsClaims rdf:type ns:PostsClaims .\n" +
                "  ?PostsClaims ns:title ?title .\n" +
                "  ?PostsClaims ns:content ?content .\n" +
                "  ?PostsClaims ns:status ?status .\n" +
                "  FILTER (?status = '" + status + "') .\n" + // Filter by the specified status
                "  ?PostsClaims ns:hasResponse ?hasResponse .\n" +
                "  ?hasResponse ns:content ?hasResponseContent .\n" +
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
            reclamationObject.put("title", solution.get("title").toString());
            reclamationObject.put("content", solution.get("content").toString());
            reclamationObject.put("status", solution.get("status").toString());
            reclamationObject.put("hasResponseContent", solution.get("hasResponseContent").toString());

            resultArray.put(reclamationObject);
        }

        return resultArray.toString();
    }
    @GetMapping("/reponse")
    public String getReponses() {
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file

        // Define the SPARQL query to retrieve data for individual responses and their associated reclamations
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?reponse ?content ?reclamation ?reclamationContent\n" +
                "WHERE {\n" +
                "  ?reponse rdf:type ns:Reponse .\n" +
                "  ?reponse ns:content ?content .\n" +
                "  ?reponse ns:hasReclamation ?reclamation .\n" +  // Get the URI of the associated reclamation
                "  ?reclamation ns:content ?reclamationContent .\n" +  // Get the content of the associated reclamation
                "}";

        // Load the ontology model
        Model model = FileManager.get().loadModel(ontologyFile);

        // Create a QueryExecution to execute the SPARQL query
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);

        // Execute the query and get the results
        ResultSet results = qexec.execSelect();

        // Create a JSON array to store the results
        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject reponseObject = new JSONObject();

            // Retrieve and add individual attributes to the JSON structure
            reponseObject.put("content", solution.get("content").toString());

            // Create a JSON object to store the reclamation content
            JSONObject reclamationObject = new JSONObject();
            reclamationObject.put("content", solution.get("reclamationContent").toString());

            // Add the reclamation object to the response object
            reponseObject.put("reclamation", reclamationObject);

            resultArray.put(reponseObject);
        }

        return resultArray.toString();
    }
}
