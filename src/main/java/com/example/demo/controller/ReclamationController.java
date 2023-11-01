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
@CrossOrigin("http://localhost:5173")
public class ReclamationController {
    @GetMapping("/reclamation/exchange")
    public String getExchangesReclamations() {
        String ontologyFile = "data/sem.owl";


        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?Exchangesclaims ?content ?status ?hasResponseContent ?title\n" +
                "WHERE {\n" +
                "  ?Exchangesclaims rdf:type ns:Exchangesclaims .\n" +
                "  ?Exchangesclaims ns:content ?content .\n" +
                "  ?Exchangesclaims ns:status ?status .\n" +
                "  ?Exchangesclaims ns:hasResponse ?hasResponse .\n" +
                "  ?hasResponse ns:content ?hasResponseContent .\n" +
                "  ?Exchangesclaims ns:title ?title .\n" +
                "}";


        Model model = FileManager.get().loadModel(ontologyFile);


        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);


        ResultSet results = qexec.execSelect();


        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject reclamationObject = new JSONObject();


            reclamationObject.put("title", solution.get("title").toString());
            reclamationObject.put("content", solution.get("content").toString());
            reclamationObject.put("status", solution.get("status").toString());
            reclamationObject.put("hasResponseContent", solution.get("hasResponseContent").toString());

            resultArray.put(reclamationObject);
        }

        return resultArray.toString();
    }

    @GetMapping("/reclamation/post")
    public String getpostReclamations() {
        String ontologyFile = "data/sem.owl";


        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?PostsClaims ?content ?status ?hasResponseContent ?title\n" +
                "WHERE {\n" +
                "  ?PostsClaims rdf:type ns:PostsClaims .\n" +
                "  ?PostsClaims ns:content ?content .\n" +
                "  ?PostsClaims ns:status ?status .\n" +
                "  ?PostsClaims ns:hasResponse ?hasResponse .\n" +
                "  ?hasResponse ns:content ?hasResponseContent .\n" +
                "  ?PostsClaims ns:title ?title .\n" +
                "}";


        Model model = FileManager.get().loadModel(ontologyFile);


        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);


        ResultSet results = qexec.execSelect();


        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject reclamationObject = new JSONObject();


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
        String ontologyFile = "data/sem.owl";


        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?Exchangesclaims ?content ?status ?hasResponseContent ?title\n" +
                "WHERE {\n" +
                "  ?Exchangesclaims rdf:type ns:Exchangesclaims .\n" +
                "  ?Exchangesclaims ns:content ?content .\n" +
                "  ?Exchangesclaims ns:status ?status .\n" +
                "  ?Exchangesclaims ns:hasResponse ?hasResponse .\n" +
                "  ?hasResponse ns:content ?hasResponseContent .\n" +
                "  ?Exchangesclaims ns:title ?title .\n" +  // Retrieve the title
                (titleToSearch != null ? "  FILTER(contains(?title, \"" + titleToSearch + "\"))\n" : "") +
                "}";


        Model model = FileManager.get().loadModel(ontologyFile);


        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);


        ResultSet results = qexec.execSelect();


        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject reclamationObject = new JSONObject();


            reclamationObject.put("title", solution.get("title").toString());
            reclamationObject.put("content", solution.get("content").toString());
            reclamationObject.put("status", solution.get("status").toString());
            reclamationObject.put("hasResponseContent", solution.get("hasResponseContent").toString());

            resultArray.put(reclamationObject);
        }

        return resultArray.toString();
    }

    @GetMapping("/reclamation/post/search")
    public String getReclamationsPosts(@RequestParam(name = "title", required = false) String titleToSearch) {
        String ontologyFile = "data/sem.owl";


        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?PostsClaims ?content ?status ?hasResponseContent ?title\n" +
                "WHERE {\n" +
                "  ?PostsClaims rdf:type ns:PostsClaims .\n" +
                "  ?PostsClaims ns:content ?content .\n" +
                "  ?PostsClaims ns:status ?status .\n" +
                "  ?PostsClaims ns:hasResponse ?hasResponse .\n" +
                "  ?hasResponse ns:content ?hasResponseContent .\n" +
                "  ?PostsClaims ns:title ?title .\n" +  // Retrieve the title
                (titleToSearch != null ? "  FILTER(contains(?title, \"" + titleToSearch + "\"))\n" : "") +
                "}";


        Model model = FileManager.get().loadModel(ontologyFile);


        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);


        ResultSet results = qexec.execSelect();


        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject reclamationObject = new JSONObject();


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
        String ontologyFile = "data/sem.owl";


        if (status == null || status.isEmpty()) {

            return "Please provide a valid 'status' parameter.";
        }


        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?Exchangesclaims ?title ?content ?status ?hasResponseContent\n" +
                "WHERE {\n" +
                "  ?Exchangesclaims rdf:type ns:Exchangesclaims .\n" +
                "  ?Exchangesclaims ns:title ?title .\n" +
                "  ?Exchangesclaims ns:content ?content .\n" +
                "  ?Exchangesclaims ns:status ?status .\n" +
                "  FILTER (?status = '" + status + "') .\n" +
                "  ?Exchangesclaims ns:hasResponse ?hasResponse .\n" +
                "  ?hasResponse ns:content ?hasResponseContent .\n" +
                "}";


        Model model = FileManager.get().loadModel(ontologyFile);


        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);


        ResultSet results = qexec.execSelect();

        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject reclamationObject = new JSONObject();


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
        String ontologyFile = "data/sem.owl";


        if (status == null || status.isEmpty()) {

            return "Please provide a valid 'status' parameter.";
        }


        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?PostsClaims ?title ?content ?status ?hasResponseContent\n" +
                "WHERE {\n" +
                "  ?PostsClaims rdf:type ns:PostsClaims .\n" +
                "  ?PostsClaims ns:title ?title .\n" +
                "  ?PostsClaims ns:content ?content .\n" +
                "  ?PostsClaims ns:status ?status .\n" +
                "  FILTER (?status = '" + status + "') .\n" +
                "  ?PostsClaims ns:hasResponse ?hasResponse .\n" +
                "  ?hasResponse ns:content ?hasResponseContent .\n" +
                "}";


        Model model = FileManager.get().loadModel(ontologyFile);


        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);


        ResultSet results = qexec.execSelect();


        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject reclamationObject = new JSONObject();


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
        String ontologyFile = "data/sem.owl";


        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?reponse ?content ?reclamation ?reclamationContent\n" +
                "WHERE {\n" +
                "  ?reponse rdf:type ns:Reponse .\n" +
                "  ?reponse ns:content ?content .\n" +
                "  ?reponse ns:hasReclamation ?reclamation .\n" +
                "  ?reclamation ns:content ?reclamationContent .\n" +
                "}";


        Model model = FileManager.get().loadModel(ontologyFile);


        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);


        ResultSet results = qexec.execSelect();


        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject reponseObject = new JSONObject();


            reponseObject.put("content", solution.get("content").toString());


            JSONObject reclamationObject = new JSONObject();
            reclamationObject.put("content", solution.get("reclamationContent").toString());

            reponseObject.put("reclamation", reclamationObject);

            resultArray.put(reponseObject);
        }

        return resultArray.toString();
    }
}
