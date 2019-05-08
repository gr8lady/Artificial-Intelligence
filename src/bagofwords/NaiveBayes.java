/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bagofwords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import static java.lang.System.err;
import static java.time.Clock.system;
import java.util.Enumeration;
import java.util.StringTokenizer;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

/**
 *
 * @author heizel
 */
public class NaiveBayes {
 Instances data;
 
    public void NaiveBayes(){
    // constructor of this class. 
    };
    
   
   public static void convertToCSV(String inputFileName ,String CSVFileName) throws IOException{
       // creamos el archivo de salida
        PrintWriter outputCSVFile = new PrintWriter(new FileWriter(CSVFileName, true));
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
    String line;
    String record=null;
     while ((line = br.readLine()) != null) {
      // I will proceed to fix the record to the csv format separated with commas. 
      String buffer1 = line.trim();
      StringTokenizer buffer =new StringTokenizer(buffer1,"\\|");
        while (buffer.hasMoreTokens()) {
             String phrase = buffer.nextToken();  // we get the phrase and the type
             String type = buffer.nextToken();
             phrase = phrase.replaceAll("\\t"," ");
             System.out.println(phrase + " phrase");
             System.out.println(type + " type");
               // now I need to tokenize the phase
               StringTokenizer PhraseBuffer =new StringTokenizer(phrase," ");
                 while(PhraseBuffer.hasMoreTokens()){
                       String word = PhraseBuffer.nextToken();
                        record = word.trim() + "\t" + type.trim(); 
                       System.out.println(record + " record");
                       outputCSVFile.println(record); // we save the file
                 } // end while phrase
                    } // end other while
         } // end of while read file
     br.close();
    }
    // we close the files as CSV format for processing
      outputCSVFile.close();         
} // end of ConvertToCSV function.
   
   public static Instances CreateARFF(String CSVFileName, String ARFFFile) throws IOException{
       // we assign local variables for handling the files.

       String CSVInputFile = CSVFileName;
       CSVLoader loader = new CSVLoader();
       Instances data;
       
    //   PrintWriter ARFFOutput = new PrintWriter(new FileWriter(ARFFFile)); // we create the ARRF file.
       
       // we set the settings for the loader
       loader.setNoHeaderRowPresent(true);  // the csv file does not have a header
       loader.setFieldSeparator("\t");
       loader.setSource(new File(CSVInputFile)); // we set the CSV file to convert
       data = loader.getDataSet();
       System.setErr(err);
       data.setClassIndex(data.numAttributes()-1);// the last field is the attributes
       
       // now we are going to print the file
       System.out.println("Attributes" + data.numAttributes());

  System.out.println("Instances : " + data.numInstances());
  System.out.println("Name : " + data.classAttribute().toString());
  // To scan through all the records of the CSV file
  Enumeration all = data.enumerateInstances();
  while (all.hasMoreElements()) {  // only to show the records are ok
        Instance rec = (Instance) all.nextElement();  // we get eacj record.
        System.out.println("Instance : " + rec.classValue() + ": " + rec.toString());
  } // end of while
       return data;
   } // end of loadingARFF
   
   public static void SaveARFF(Instances data, String ArffFileName) throws IOException{
       String ArffFile = ArffFileName;
       Instances dataArff = data;
       ArffSaver arffSaverInstance = new ArffSaver();  // we create an instance to save the ARFF file....
       arffSaverInstance.setInstances(dataArff);
       arffSaverInstance.setFile(new File(ArffFile));
       arffSaverInstance.writeBatch();
   } // end of SaveARFF records.... 
   
    }; // end of NaiveBayes class 


    

