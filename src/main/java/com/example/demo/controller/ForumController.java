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
public class ForumController {


    @GetMapping("/forum")
    public String getForums() {


            String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file

            // Define the SPARQL query to retrieve data for individual reclamations
            String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "SELECT ?Forumpost ?PostDescription ?postimage ?title ?hasCommentContent\n" +
                    "WHERE {\n" +

                    "  ?Forumpost ns:PostDescription ?PostDescription .\n" +
                    "  ?Forumpost ns:postimage ?postimage .\n" +
                    "  ?Forumpost ns:title ?title .\n" +
                    "?Forumpost ns:hasComment ?hasComment .\n"+
                    "?hasComment ns:content ?hasCommentContent .\n"+

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
                JSONObject ForumpostObject = new JSONObject();

                // Retrieve and add individual attributes to the JSON structure
                ForumpostObject.put("PostDescription", solution.get("PostDescription").toString());
                ForumpostObject.put("postimage", solution.get("postimage").toString());
                ForumpostObject.put("title", solution.get("title").toString());
                    //get comment of post
                ForumpostObject.put("hasCommentContent", solution.get("hasCommentContent").toString());


                resultArray.put(ForumpostObject);
            }

            return resultArray.toString();
        }

    @GetMapping("/comment")
    public String getComments() {
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file

        // Define the SPARQL query to retrieve data for individual comments and their content
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?comment ?content \n" +
                "WHERE {\n" +

                "  ?comment ns:content ?content .\n" +

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
            JSONObject commentObject = new JSONObject();

            // Retrieve and add individual attributes to the JSON structure
            commentObject.put("content", solution.get("content").toString());


            // Add the comment object to the response array
            resultArray.put(commentObject);
        }

        return resultArray.toString();
    }


}