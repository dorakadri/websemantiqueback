package com.example.demo.controller;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.FileManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/troc")
@CrossOrigin("http://localhost:5173")
public class DonationController {

    @GetMapping("/donations")
    public String getDonations() {
        String ontologyFile = "data/sem.owl";
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
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();

        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject donationObject = new JSONObject();
            donationObject.put("Donation_Amount", solution.get("Donation_Amount").toString());
            donationObject.put("Donor", solution.get("Donor").toString());
            donationObject.put("note", solution.get("note").toString());
            donationObject.put("Donation_Date", solution.get("Donation_Date").toString());
            resultArray.put(donationObject);
        }
        return resultArray.toString();
    }

    @GetMapping("/charityevents")
    public String getCharityEvents() {
        String ontologyFile = "data/sem.owl";
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?CharityEvent ?Target_Fundraising_Goal ?Event_Description ?Participants ?Organizer ?Event_Date ?Donations_Received ?Event_Name ?receivesDonation\n" +
                "WHERE {\n" +
                "  ?CharityEvent rdf:type ns:CharityEvents .\n" +
                "  ?CharityEvent ns:Target_Fundraising_Goal ?Target_Fundraising_Goal .\n" +
                "  ?CharityEvent ns:Event_Description ?Event_Description .\n" +
                "  ?CharityEvent ns:Participants ?Participants .\n" +
                "  ?CharityEvent ns:Organizer ?Organizer .\n" +
                "  ?CharityEvent ns:Event_Date ?Event_Date .\n" +
                "  ?CharityEvent ns:Donations_Received ?Donations_Received .\n" +
                "  ?CharityEvent ns:Event_Name ?Event_Name .\n" +
                "  ?CharityEvent ns:receivesDonation ?receivesDonation .\n" +
                "}";

        Model model = FileManager.get().loadModel(ontologyFile);
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();

        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject eventObject = new JSONObject();
            eventObject.put("Target_Fundraising_Goal", solution.get("Target_Fundraising_Goal").toString());
            eventObject.put("Event_Description", solution.get("Event_Description").toString());
            eventObject.put("Participants", solution.get("Participants").toString());
            eventObject.put("Organizer", solution.get("Organizer").toString());
            eventObject.put("Event_Date", solution.get("Event_Date").toString());
            eventObject.put("Donations_Received", solution.get("Donations_Received").toString());
            eventObject.put("Event_Name", solution.get("Event_Name").toString());


            String donationURL = solution.get("receivesDonation").toString();

            String donationQuery ="PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>" +
                    "SELECT ?Donation_Amount ?Donor ?Donation_Date ?note " +
                    "WHERE { <" + donationURL + "> ns:Donation_Amount ?Donation_Amount . " +
                    "<" + donationURL + "> ns:Donor ?Donor . " +
                    "<" + donationURL + "> ns:Donation_Date ?Donation_Date . " +
                    "<" + donationURL + "> ns:note ?note . }";
            Query donationSparql = QueryFactory.create(donationQuery);
            QueryExecution donationQExec = QueryExecutionFactory.create(donationSparql, model);
            ResultSet donationResults = donationQExec.execSelect();
            if (donationResults.hasNext()) {
                QuerySolution donationSolution = donationResults.nextSolution();
                eventObject.put("Donation_Amount", donationSolution.get("Donation_Amount").toString());
                eventObject.put("Donor", donationSolution.get("Donor").toString());
                eventObject.put("Donation_Date", donationSolution.get("Donation_Date").toString());
                eventObject.put("note", donationSolution.get("note").toString());
            }

            resultArray.put(eventObject);
        }

        return resultArray.toString();
    }

    @GetMapping("/donationsByAmount")
    public String getDonationsByAmount(@RequestParam("amount") float minAmount) {
        String ontologyFile = "data/sem.owl";
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "SELECT ?donation ?Donation_Amount ?Donor ?Donation_Date ?note\n" +
                "WHERE {\n" +
                "  ?donation rdf:type ns:donation .\n" +
                "  ?donation ns:Donation_Amount ?Donation_AmountStr .\n" +
                "  BIND (xsd:float(?Donation_AmountStr) AS ?Donation_Amount) .\n" +
                "  ?donation ns:Donor ?Donor .\n" +
                "  ?donation ns:note ?note .\n" +
                "  ?donation ns:Donation_Date ?Donation_Date .\n" +
                "  FILTER (?Donation_Amount > " + minAmount + ") .\n" +
                "}";

        Model model = FileManager.get().loadModel(ontologyFile);
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();

        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject donationObject = new JSONObject();
            donationObject.put("Donation_Amount", solution.get("Donation_Amount").toString());
            donationObject.put("Donor", solution.get("Donor").toString());
            donationObject.put("note", solution.get("note").toString());
            donationObject.put("Donation_Date", solution.get("Donation_Date").toString());
            resultArray.put(donationObject);
        }
        return resultArray.toString();
    }

    @GetMapping("/donationsByYear")
    public String getDonationsByYear(@RequestParam("year") int targetYear) {
        String ontologyFile = "data/sem.owl";
        String sparqlQuery = "PREFIX ns: <http://www.semanticweb.org/user/ontologies/2023/9/TrocAPP-14#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "SELECT ?donation ?Donation_Amount ?Donor ?Donation_Date ?note\n" +
                "WHERE {\n" +
                "  ?donation rdf:type ns:donation .\n" +
                "  ?donation ns:Donation_Amount ?Donation_Amount .\n" +
                "  ?donation ns:Donor ?Donor .\n" +
                "  ?donation ns:note ?note .\n" +
                "  ?donation ns:Donation_Date ?Donation_DateString .\n" +
                "  BIND (strbefore(?Donation_DateString, '/') AS ?dayMonth) .\n" +
                "  BIND (xsd:integer(strafter(strafter(?Donation_DateString, '/'), '/')) AS ?year) .\n" +
                "  FILTER (?year = " + targetYear + ") .\n" +
                "}";

        Model model = FileManager.get().loadModel(ontologyFile);
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();

        JSONArray resultArray = new JSONArray();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            JSONObject donationObject = new JSONObject();
            donationObject.put("Donation_Amount", solution.get("Donation_Amount").toString());
            donationObject.put("Donor", solution.get("Donor").toString());
            donationObject.put("note", solution.get("note").toString());

            // Obtenir la vraie valeur de Donation_Date si elle est disponible
            if (solution.contains("Donation_Date")) {
                RDFNode donationDateNode = solution.get("Donation_Date");
                donationObject.put("Donation_Date", donationDateNode.toString());
            }

            resultArray.put(donationObject);
        }
        return resultArray.toString();
    }
}
