/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bagofwords;
import java.io.IOException;
import weka.core.Instances;

/**
 *
 * @author heizel
 */
public class BagOfWords {

    /**
     * @param args the command line arguments
     */
    
    // we declare the NaiveBayes class here 
    NaiveBayes Bayes = new NaiveBayes();
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        // we do call the void to create the csv file
        NaiveBayes.convertToCSV("test/input.txt", "test/output.csv");
        Instances data=NaiveBayes.CreateARFF("test/output.csv","test/output.arff");
        NaiveBayes.SaveARFF(data, "test/weka.arff");
    }
    
}
