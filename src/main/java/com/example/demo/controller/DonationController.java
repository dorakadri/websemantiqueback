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

public class DonationController {


    @GetMapping("/donations")
    public String getDonations() {
        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?donation ?Donation_Amount ?Donor ?Donation_Date ?note\n" +
                "WHERE {\n" +
                "  ?donation rdf:type ns:donation .\n" +
                "  ?donation ns:Donation_Amount ?Donation_Amount .\n" +
                "  ?donation ns:Donor ?Donor .\n" +
                "  ?donation ns:note ?note .\n" +
                "  ?donation ns:Donation_Date ?Donation_Date .\n" +

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
            JSONObject donationObject = new JSONObject();
            // Retrieve and add individual attributes to the JSON structure
            donationObject.put("Donation_Amount", solution.get("Donation_Amount").toString());
            donationObject.put("Donor", solution.get("Donor").toString());
            donationObject.put("note", solution.get("note").toString());
            donationObject.put("Donation_Date", solution.get("Donation_Date").toString());



            resultArray.put(donationObject);

        }
        return resultArray.toString();

    }
//    @GetMapping("/charityevent")
//    public String getCharityEvents() {
//        String ontologyFile = "data/sem.owl"; // Replace with the actual path to your ontology file
//        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
//                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//                "SELECT ?CharityEvents ?Target_Fundraising_Goal ?Event_Description ?Participants ?Organizer ?Event_Date ?Donations_Received ?Event_Name ?receivesDonation\n" +
//                "WHERE {\n" +
//                "  ?CharityEvents rdf:type ns:CharityEvents .\n" +
//                "  ?CharityEvents ns:Participants ?Participants .\n" +
//                "  ?CharityEvents ns:Organizer ?Organizer .\n" +
//                "  ?CharityEvents ns:Event_Date ?Event_Date .\n" +
//                "  ?CharityEvents ns:Donations_Received ?Donations_Received .\n" +
//                "  ?CharityEvents ns:Event_Name ?Event_Name .\n" +
//                "  ?CharityEvents ns:receivesDonation ?receivesDonation .\n" +
//                "}";
//
//        Model model = FileManager.get().loadModel(ontologyFile);
//
//        // Create a QueryExecution to execute the SPARQL query
//        Query query = QueryFactory.create(sparqlQuery);
//        QueryExecution qexec = QueryExecutionFactory.create(query, model);
//
//        // Execute the query and get the results
//        ResultSet results = qexec.execSelect();
//
//        // Convert the ResultSet to JSON
//        JSONArray resultArray = new JSONArray();
//        while (results.hasNext()) {
//
//            QuerySolution solution = results.nextSolution();
//            JSONObject eventObject = new JSONObject();
//            // Retrieve and add individual attributes to the JSON structure
//            if (solution.contains("Participants")) {
//                eventObject.put("Participants", solution.get("Participants").toString());
//                eventObject.put("Organizer", solution.get("Organizer").toString());
//                eventObject.put("Event_Date", solution.get("Event_Date").toString());
//                eventObject.put("Donations_Received", solution.get("Donations_Received").toString());
//                eventObject.put("Event_Name", solution.get("Event_Name").toString());
//                eventObject.put("receivesDonation", solution.get("receivesDonation").toString());
//
//                resultArray.put(eventObject);
//            }
//
//                if (resultArray.isEmpty()) {
//                    return "No charity events found";
//                }
//
//
//
//
//
//        }
//        return resultArray.toString();
//
//    }

}