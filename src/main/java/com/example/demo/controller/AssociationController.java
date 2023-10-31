package com.example.demo.controller;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/troc")
@CrossOrigin(origins = "http://127.0.0.1:5173")

public class AssociationController {


    @GetMapping("/associations")
    public String getAssociations() {
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?association ?Associationdescription ?id ?email ?profileimage ?Association_adress ?Association_status ?phonenum ?Association_name ?belongs_to_an\n" +
                "WHERE {\n" +
                "  ?association rdf:type ns:Association .\n" +
                "  ?association ns:Associationdescription ?Associationdescription .\n" +
                "  ?association ns:id ?id .\n" +
                "  ?association ns:email ?email .\n" +
                "  ?association ns:profileimage ?profileimage .\n" +
                "  ?association ns:Association_adress ?Association_adress .\n" +
                "  ?association ns:Association_status ?Association_status .\n" +
                "  ?association ns:phonenum ?phonenum .\n" +
                "  ?association ns:Association_name ?Association_name .\n" +
                "  ?association ns:belongs_to_an ?belongs_to_an .\n" +

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
            JSONObject associationObject = new JSONObject();
            // Retrieve and add individual attributes to the JSON structure
            associationObject.put("Associationdescription", solution.get("Associationdescription").toString());
            associationObject.put("id", solution.get("id").toString());
            associationObject.put("email", solution.get("email").toString());
            associationObject.put("profileimage", solution.get("profileimage").toString());
            associationObject.put("Association_adress", solution.get("Association_adress").toString());
            associationObject.put("Association_status", solution.get("Association_status").toString());
            associationObject.put("phonenum", solution.get("phonenum").toString());
            associationObject.put("Association_name", solution.get("Association_name").toString());
            associationObject.put("belongs_to_an", solution.get("belongs_to_an").toString());


            resultArray.put(associationObject);

        }
        return resultArray.toString();

    }


    @GetMapping("AssociationOwner")
    public String getAssociationOwnerInfo(){
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file

        String sparqlQuery=" PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "SELECT ?role ?birthdate ?owner_id ?phonenum ?profileimage ?password ?email ?lastname ?Owns_an ?post_on_forum\n" +
                "WHERE {\n" +
                "    ?individual rdf:type ns:AssociationOwner .\n" +
                "    ?individual ns:role ?role .\n" +
                "    ?individual ns:birthdate ?birthdate .\n" +
                "    ?individual ns:owner_id ?owner_id .\n" +
                "    ?individual ns:phonenum ?phonenum .\n" +
                "    ?individual ns:profileimage ?profileimage .\n" +
                "    ?individual ns:password ?password .\n" +
                "    ?individual ns:email ?email .\n" +
                "    ?individual ns:lastname ?lastname .\n" +
                "    ?individual ns:Owns_an ?Owns_an .\n" +
                "    ?individual ns:post_on_forum ?post_on_forum .\n" +
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
            JSONObject OwnerObject = new JSONObject();
            // Retrieve and add individual attributes to the JSON structure
            OwnerObject.put("role", solution.get("role").toString());
            OwnerObject.put("birthdate", solution.get("birthdate").toString());
            OwnerObject.put("owner_id", solution.get("owner_id").toString());
            OwnerObject.put("phonenum", solution.get("phonenum").toString());
            OwnerObject.put("profileimage", solution.get("profileimage").toString());
            OwnerObject.put("password", solution.get("password").toString());
            OwnerObject.put("email", solution.get("email").toString());
            OwnerObject.put("lastname", solution.get("lastname").toString());
            OwnerObject.put("Owns_an", solution.get("Owns_an").toString());
            OwnerObject.put("post_on_forum",solution.get("post_on_forum").toString());

            resultArray.put(OwnerObject);

        }
        return  resultArray.toString();

    }
}
