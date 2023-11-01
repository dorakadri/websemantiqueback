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
@CrossOrigin("http://localhost:5173")
public class ForumController {


    @GetMapping("/forum")
    public String getForums() {


            String ontologyFile = "data/sem.owl";


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


            Model model = FileManager.get().loadModel(ontologyFile);


            Query query = QueryFactory.create(sparqlQuery);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);


            ResultSet results = qexec.execSelect();


            JSONArray resultArray = new JSONArray();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                JSONObject ForumpostObject = new JSONObject();


                ForumpostObject.put("PostDescription", solution.get("PostDescription").toString());
                ForumpostObject.put("postimage", solution.get("postimage").toString());
                ForumpostObject.put("title", solution.get("title").toString());

                ForumpostObject.put("hasCommentContent", solution.get("hasCommentContent").toString());


                resultArray.put(ForumpostObject);
            }

            return resultArray.toString();
        }

    @GetMapping("/comment")
    public String getComments() {
        String ontologyFile = "data/sem.owl";


        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?comment ?content \n" +
                "WHERE {\n" +

                "  ?comment ns:content ?content .\n" +

                "}";

        // Load the ontology model
        Model model = FileManager.get().loadModel(ontologyFile);


        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);


        ResultSet results = qexec.execSelect();


        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject commentObject = new JSONObject();


            commentObject.put("content", solution.get("content").toString());



            resultArray.put(commentObject);
        }

        return resultArray.toString();
    }

    @GetMapping("/search/forum/{title}")
    public String getForumPostsByTitle(@PathVariable(value = "title") String title) {
        String ontologyFile = "data/sem.owl";

        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?Forumpost ?PostDescription ?postimage ?title ?hasCommentContent\n" +
                "WHERE {\n" +
                "  ?Forumpost rdf:type ns:Object .\n" +
                "  ?Forumpost ns:PostDescription ?PostDescription .\n" +
                "  ?Forumpost ns:postimage ?postimage .\n" +
                "  ?Forumpost ns:title ?title .\n" +
                "  OPTIONAL {\n" +
                "    ?Forumpost ns:hasComment ?hasComment .\n" +
                "    ?hasComment ns:content ?hasCommentContent .\n" +
                "  }\n" +
                "  FILTER (str(?title) = '" + title + "') .\n" +
                "}";

        Model model = FileManager.get().loadModel(ontologyFile);
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();

        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject ForumpostObject = new JSONObject();

            ForumpostObject.put("PostDescription", solution.get("PostDescription").toString());
            ForumpostObject.put("postimage", solution.get("postimage").toString());
            ForumpostObject.put("title", solution.get("title").toString());

            // Check if there are comments for this post
            if (solution.contains("hasCommentContent")) {
                ForumpostObject.put("hasCommentContent", solution.get("hasCommentContent").toString());
            }

            resultArray.put(ForumpostObject);
        }

        return resultArray.toString();
    }



}