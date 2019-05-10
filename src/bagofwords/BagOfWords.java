/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bagofwords;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author heizel
 */
public class BagOfWords {

    /**
     * @param args the command line arguments
     */
    
    // we declare the NaiveBayes class here 
    NaiveBayesIA Bayes = new NaiveBayesIA();
    
    public static void main(String[] args) throws IOException, Exception {
     // declaring the files that will call everything....
     
     String CSVFile = "test/output.csv";
     String ARFFfile = "test/file.arff";
     
      Scanner myObj = new Scanner(System.in);
      System.out.println("ingrese el nombre del archivo a evaluar");
      String input = myObj.nextLine();
      NaiveBayesIA.LoadNewData(input, CSVFile, ARFFfile);
    }
    
}

    // NaiveBayesIA.LoadNewData(input, CSVFile, ARFFfile);
    //   NaiveBayesIA.ClassifierWithFilter();
//NaiveBayesIA.LoadData1(ARFFfile); 
   //   String input = "test/input3.txt";
  // NaiveBayesIA.LoadData1(ARFFfile, input);