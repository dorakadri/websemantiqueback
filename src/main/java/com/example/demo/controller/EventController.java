package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.tools.JenaEngine;
@RestController

@RequestMapping("/event")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class EventController {
    @GetMapping("/regularEvents")
    public String getRegularEvents() {
        String ontologyFile = "data/sem.owl";

         String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT  ?Event_Date ?Event_Description ?Event_Name ?Organizer  ?location\n" +
                "WHERE {\n" +
                "  ?event rdf:type ns:RegularEvents .\n" +
                "  ?event ns:Event_Date ?Event_Date .\n" +
                "  ?event ns:Event_Description ?Event_Description .\n" +
                "  ?event ns:Event_Name ?Event_Name .\n" +
                "  ?event ns:Organizer ?Organizer .\n" +
                "  ?event ns:location ?location .\n" +
                "}";

         Model model = FileManager.get().loadModel(ontologyFile);

         Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);

         ResultSet results = qexec.execSelect();

         JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject eventObject = new JSONObject();

            eventObject.put("Event_Date", solution.get("Event_Date").toString());
            eventObject.put("Event_Description", solution.get("Event_Description").toString());
            eventObject.put("Event_Name", solution.get("Event_Name").toString());
            eventObject.put("Organizer", solution.get("Organizer").toString());
            eventObject.put("location", solution.get("location").toString());


            resultArray.put(eventObject);
        }

        return resultArray.toString();
    }


    @GetMapping("/regularEventsSearchLocation")
    public String getRegularEvents(@RequestParam(value = "location", required = false) String location) {
        String ontologyFile = "data/sem.owl";

        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?Event_Date ?Event_Description ?Event_Name ?Organizer ?location\n" +
                "WHERE {\n" +
                "  ?event rdf:type ns:RegularEvents .\n" +
                "  ?event ns:Event_Date ?Event_Date .\n" +
                "  ?event ns:Event_Description ?Event_Description .\n" +
                "  ?event ns:Event_Name ?Event_Name .\n" +
                "  ?event ns:Organizer ?Organizer .\n" +
                "  ?event ns:location ?location .\n" +
                (location != null ? "  FILTER (?location = \"" + location + "\").\n" : "") +
                "}";

        Model model = FileManager.get().loadModel(ontologyFile);

        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);

        ResultSet results = qexec.execSelect();

        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject eventObject = new JSONObject();

            eventObject.put("Event_Date", solution.get("Event_Date").toString());
            eventObject.put("Event_Description", solution.get("Event_Description").toString());
            eventObject.put("Event_Name", solution.get("Event_Name").toString());
            eventObject.put("Organizer", solution.get("Organizer").toString());
            eventObject.put("location", solution.get("location").toString());

            resultArray.put(eventObject);
        }

        return resultArray.toString();
    }

    @GetMapping("/eventLocations")
    public List<String> getAllEventLocations() {
        String ontologyFile = "data/sem.owl";

        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT DISTINCT ?location\n" +
                "WHERE {\n" +
                "  ?event rdf:type ns:RegularEvents .\n" +
                "  ?event ns:location ?location .\n" +
                "}";

        Model model = FileManager.get().loadModel(ontologyFile);

        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);

        ResultSet results = qexec.execSelect();

        List<String> eventLocations = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            eventLocations.add(solution.get("location").toString());
        }

        return eventLocations;
    }


    @GetMapping("/regularEvents/search/filter")
    public String getRegularEvents(@RequestParam(value = "location", required = false) String location, @RequestParam(value = "title", required = false) String title) {
        String ontologyFile = "data/sem.owl";

        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT  ?Event_Date ?Event_Description ?Event_Name ?Organizer ?location\n" +
                "WHERE {\n" +
                "  ?event rdf:type ns:RegularEvents .\n" +
                "  ?event ns:Event_Date ?Event_Date .\n" +
                "  ?event ns:Event_Description ?Event_Description .\n" +
                "  ?event ns:Event_Name ?Event_Name .\n" +
                "  ?event ns:Organizer ?Organizer .\n" +
                "  ?event ns:location ?location .\n";

        if (location != null && !location.isEmpty()) {
            sparqlQuery += "  FILTER (?location = \"" + location + "\").\n";
        }

        if (title != null && !title.isEmpty()) {
            sparqlQuery += "  FILTER regex(?Event_Name, \"" + title + "\", \"i\").\n";
        }

        sparqlQuery += "}";

        Model model = FileManager.get().loadModel(ontologyFile);

        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);

        ResultSet results = qexec.execSelect();

        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject eventObject = new JSONObject();

            eventObject.put("Event_Date", solution.get("Event_Date").toString());
            eventObject.put("Event_Description", solution.get("Event_Description").toString());
            eventObject.put("Event_Name", solution.get("Event_Name").toString());
            eventObject.put("Organizer", solution.get("Organizer").toString());
            eventObject.put("location", solution.get("location").toString());

            resultArray.put(eventObject);
        }

        return resultArray.toString();
    }




}
