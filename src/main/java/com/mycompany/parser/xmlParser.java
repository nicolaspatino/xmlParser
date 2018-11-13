/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parser;

//Java DOM parser classes
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//These classes read the sample XML file and manage output:
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

//Finally, import the W3C definitions for a DOM, DOM exceptions, entities and nodes:
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author daniel
 */
public class xmlParser {
    public Element process;
    public List<Element> actors = new ArrayList<Element>();
    public List<Element> businessRules = new ArrayList<Element>();
    public List<Element> events = new ArrayList<Element>();
    public List<Element> taskes = new ArrayList<Element>();

    static final String outputEncoding = "UTF-8";

    private static void usage() {
        System.out.println("Usage: xmlechoparser file1");
    }

    public void parse(String filename) throws Exception {
        FileOutputStream archivoHTML;
        PrintStream p = null;
        try{
            File inputFile = new File(filename);
            archivoHTML= new FileOutputStream("documentacion.html");
            p = new PrintStream(archivoHTML);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList collaboration = doc.getElementsByTagName("model:collaboration");
            Node collaborators = collaboration.item(0);
            NodeList group = collaborators.getChildNodes();
            NodeList process = doc.getElementsByTagName("model:process");
            Node components = process.item(0);
            NodeList items = components.getChildNodes();
            
            for (int i = 0; i < group.getLength(); i++) {
                Node nNode = group.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    if (element.hasAttribute("processRef")) {
                        this.process = element;
                    } else {
                        actors.add(element);
                    }
                }
            }
            for (int i = 0; i < items.getLength(); i++) {
                Node nNode = items.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    if (element.getNodeName().contains("Task") || element.getNodeName().equals("model:callActivity")) {
                        taskes.add(element);
                    }
                    if (element.getNodeName().contains("Event")) {
                        events.add(element);
                    }
                    if (element.getNodeName().contains("exclusiveGateway")) {
                        businessRules.add(element);
                    }
                }
            }
            
        }catch(Exception e){
            
        }
        
    }
    public void buildHtml(){
        actors.forEach((actor) -> {
            System.out.println(actor);
        });
    }
    private void generate(FileOutputStream archivoHTML, PrintStream p){
                p.println("<HTML>\n" +
            "    <HEAD>\n" +
            "        <TITLE>BPMNDoc</TITLE>\n" +
            "    </HEAD>\n" +
            "\n" +
            "    <BODY>\n" +
            "        <h1>BPMNDoc</h1>\n"+
            "        <ol type='I'>" +    
            " \n" 
            );
    }

    private static void navigate(Node n) {
        navigate(n, "");
    }

    private static void navigate(Node n, String prefix) {
    
        
    }
    public static void  agregarSEPHTML(FileOutputStream archivoHTML, PrintStream p){
        p.println("<ul class='inside' type='square'>");    
    }
    public static void  cerrarSEPHTML(FileOutputStream archivoHTML, PrintStream p){
        p.println("</ul> \n");    
    }            
    public static void  agregarAHTML(FileOutputStream archivoHTML, PrintStream p, String cad){
        p.println("<li>"+cad+"</li>");    
    }
    public static void agregarDEHTML(FileOutputStream archivoHTML, PrintStream p, String cad){
        p.println("<dd>"+cad+"</dd>");    
    }
    
    
    public static void cerrarHTML(FileOutputStream archivoHTML, PrintStream p){
        p.println("      </OL>\n"+
                "    </BODY>\n" +
            "</HTML>");
        p.close();
    
    }
}