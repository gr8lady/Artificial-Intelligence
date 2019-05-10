/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bagofwords;
import java.io.IOException;

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
        
     String input = "test/input.txt";
     String CSVFile = "test/output.csv";
     String ARFFfile = "test/file.arff";
     NaiveBayesIA.LoadNewData(input, CSVFile, ARFFfile);
  //   NaiveBayesIA.LoadData1(ARFFfile); 
  //   NaiveBayesIA.ClassifierWithFilter();
    }
    
}
