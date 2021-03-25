/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Converter1;


import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author Group Raihana, Fasya, Akmal, Syamimi
 */
public class XMLParser {
    private XMLInputFactory xif;
    private XMLEventReader parser;
    private FileReader infile;
    private static HashMap<String, Double> m = new HashMap<String, Double>();
    private String curr;
    private String rat;
    

    public void Init(){
        xif = XMLInputFactory.newInstance();
        try{
            infile = new FileReader("C:\\Users\\atif\\Desktop\\web service lab\\currency2.xml");
            parser = xif.createXMLEventReader(infile);
        }catch(Exception e){
            System.err.println(e);
        }
    }

    public HashMap<String, Double> getHashMap() {
        return m;
    }

    public void parseAll(){
        try{
            while(parser.hasNext()){
                XMLEvent e = parser.nextEvent();
                if(e.isStartElement()){
                    StartElement se = e.asStartElement();
                    String tag = se.getName().getLocalPart();
                    System.out.println("Start Tag Name = " + tag);
                    if(tag.equals("Cube")){
                        curr = null;
                        rat = null;
                    }

                    Iterator<Attribute> it = se.getAttributes();
                    while(it!=null && it.hasNext()){
                        Attribute att = it.next();
                        String AttName = att.getName().toString();
                        String AttVal = att.getValue();
                        System.out.println("Attribute Name = " + AttName);
                        System.out.println("Attribute Value = " + AttVal);
                        if(AttName.equals("rate")) rat = AttVal;
                        else if(AttName.equals("currency")) curr = AttVal;

                    }
                }

                if(e.isEndElement()){
                    EndElement ee = e.asEndElement();
                    String tag = ee.getName().getLocalPart();
                    System.out.println("End Tag Name = " + tag);
                    if(tag.equals("Cube")){
                        System.out.println();

                        if(curr != null && rat != null){
                            m.put(curr, new Double(rat));
                        }
                    }
                }
            }
        }catch(XMLStreamException e){
            System.err.println(e);
        } 
        try{
            infile.close();
        }catch(IOException ioe){
            System.err.println(ioe);
        }
    }

    public void parseProduct(String s){
        Init();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XMLParser xp = new XMLParser();
        xp.Init();
        xp.parseAll();
        System.out.println(m);
        System.out.println("**************************");
        xp.parseProduct("P2");
    }
}