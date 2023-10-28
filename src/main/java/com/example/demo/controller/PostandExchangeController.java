package com.example.demo.controller;


import com.example.demo.entite.Annonce;
import com.example.demo.tools.JenaEngine;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

@RestController

@RequestMapping("/annonce")
@CrossOrigin("http://localhost:5173")
public class PostandExchangeController {

    @GetMapping("/Posts")
    public String getPosts() {
        String ontologyFile = "data/sem.owl";


        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?post ?title ?description  ?date ?image ?advertisedByUser_id ?exchangerUser_id ?exchangeForEchangeobjectdesc  ?exchangeForExchangeobjectimage\n" +
                "WHERE {\n" +
                "  ?post rdf:type ns:Object .\n" +
                "  ?post ns:title ?title .\n" +
                "  ?post ns:PostDescription ?description .\n" +
                "  ?post ns:date ?date .\n" +
                "  ?post ns:postimage ?image .\n" +
                "  ?post  ns:postAdvertisedBy ?postAdvertisedBy .\n" +
                "  ?postAdvertisedBy ns:user_id ?advertisedByUser_id .\n" +
                "  ?post ns:has_exchanger ?has_exchanger .\n" +
                "  ?has_exchanger ns:user_id ?exchangerUser_id .\n" +

                "  ?post ns:exchangeFor ?exchangeFor .\n" +
                "   ?exchangeFor (ns:echangeservicedescription|ns:echangeobjectdesc) ?exchangeForEchangeobjectdesc .\n" +
                "  ?exchangeFor (ns:echangeserviceimage|ns:exchangeobjectimage) ?exchangeForExchangeobjectimage ." +
                "}";


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
            JSONObject postObject = new JSONObject();

            // Retrieve and add individual attributes to the JSON structure
            postObject.put("title", solution.get("title").toString());
            postObject.put("description", solution.get("description").toString());
            postObject.put("date", solution.get("date").toString());

            postObject.put("image", solution.get("image").toString());
            postObject.put("advertisedBy", solution.get("advertisedByUser_id").toString());
            postObject.put("exchanger", solution.get("exchangerUser_id").toString());
            postObject.put("exchangedescription", solution.get("exchangeForEchangeobjectdesc").toString());
            postObject.put("exchangeimage", solution.get("exchangeForExchangeobjectimage").toString());

            resultArray.put(postObject);
        }

        return resultArray.toString();
    }

    @GetMapping("/exchanger/{id}")
    public String getEchangerbyid(@PathVariable(value = "id") int id) {
        String ontologyFile = "data/sem.owl";


        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?SimpleUser ?birthdate ?phonenum  ?email ?lastname ?profileimage  ?user_id\n" +
                "WHERE {\n" +
                "  ?SimpleUser rdf:type ns:SimpleUser .\n" +
                "  ?SimpleUser ns:birthdate ?birthdate .\n" +
                "  ?SimpleUser ns:phonenum ?phonenum .\n" +
                "  ?SimpleUser ns:email ?email .\n" +
                "  ?SimpleUser ns:lastname ?lastname .\n" +
                "  ?SimpleUser ns:profileimage ?profileimage .\n" +
                "  ?SimpleUser ns:user_id ?user_id .\n" +

                "FILTER ( ( ?user_id = '"+id+"' )  ) " +

                "}";


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
            JSONObject userObject = new JSONObject();

            // Retrieve and add individual attributes to the JSON structure
            userObject.put("birthdate", solution.get("birthdate").toString());
            userObject.put("phonenum", solution.get("phonenum").toString());
            userObject.put("email", solution.get("email").toString());

            userObject.put("profileimage", solution.get("profileimage").toString());
            userObject.put("user_id", solution.get("user_id").toString());
            userObject.put("lastname", solution.get("lastname").toString());


            resultArray.put(userObject);
        }

        return resultArray.toString();
    }

    @GetMapping("/userpost/{id}")
    public String getuserposts(@PathVariable(value = "id") int id) {
        String ontologyFile = "data/sem.owl";


        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?post ?title ?description  ?date ?image ?advertisedByUser_id ?exchangerUser_id ?exchangeForEchangeobjectdesc  ?exchangeForExchangeobjectimage\n" +
                "WHERE {\n" +
                "  ?post rdf:type ns:Object .\n" +
                "  ?post ns:title ?title .\n" +
                "  ?post ns:PostDescription ?description .\n" +
                "  ?post ns:date ?date .\n" +
                "  ?post ns:postimage ?image .\n" +
                "  ?post  ns:postAdvertisedBy ?postAdvertisedBy .\n" +
                "  ?postAdvertisedBy ns:user_id ?advertisedByUser_id .\n" +
                "  ?post ns:has_exchanger ?has_exchanger .\n" +
                "  ?has_exchanger ns:user_id ?exchangerUser_id .\n" +

                "  ?post ns:exchangeFor ?exchangeFor .\n" +
                "   ?exchangeFor (ns:echangeservicedescription|ns:echangeobjectdesc) ?exchangeForEchangeobjectdesc .\n" +
                "  ?exchangeFor (ns:echangeserviceimage|ns:exchangeobjectimage) ?exchangeForExchangeobjectimage .\n" +
                "FILTER ( ?advertisedByUser_id = '" + id + "' )\n" +
                "}";


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
            JSONObject postObject = new JSONObject();

            // Retrieve and add individual attributes to the JSON structure
            postObject.put("title", solution.get("title").toString());
            postObject.put("description", solution.get("description").toString());
            postObject.put("date", solution.get("date").toString());

            postObject.put("image", solution.get("image").toString());
            postObject.put("advertisedBy", solution.get("advertisedByUser_id").toString());
            postObject.put("exchanger", solution.get("exchangerUser_id").toString());
            postObject.put("exchangedescription", solution.get("exchangeForEchangeobjectdesc").toString());
            postObject.put("exchangeimage", solution.get("exchangeForExchangeobjectimage").toString());

            resultArray.put(postObject);
        }

        return resultArray.toString();
    }



}


