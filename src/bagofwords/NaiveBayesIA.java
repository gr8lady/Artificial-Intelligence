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
import java.util.Collections.*;
import static java.lang.System.err;
import static java.nio.file.Files.list;
import static java.rmi.Naming.list;
import static java.time.Clock.system;
import static java.util.Collections.list;
import java.util.Enumeration;
import java.util.StringTokenizer;
import weka.classifiers.Classifier;
import weka.classifiers.EnsembleLibrary;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Debug.Random;
import weka.filters.unsupervised.attribute.StringToWordVector;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
//import bagofwords.unUsedClasses.Classification;
//import bagofwords.unUsedClasses.IClassificationTextNaiveBays;
import static com.oracle.nio.BufferSecrets.instance;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.stemmers.LovinsStemmer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;





/**
 *
 * @author heizel Gonzalez 12021-01 
 */
public class NaiveBayesIA  {
// private variables...
 private Instances data;
 FilteredClassifier classifier;
 private Instances trainData;
 EnsembleLibrary ensembleLib;
 Classifier baselineNB;
 Instances trainedData;
 StringToWordVector filter; 
// Classification cls;
private Instances test;
 


 
    public void NaiveBayes(){
     //initialize all the variables that will be needed for this Bayes IA class
         classifier = new FilteredClassifier();
         classifier.setClassifier(new NaiveBayesMultinomial());
         ensembleLib.addModel("weka.classifiers.bayes.NaiveBayes");
         EnsembleLibrary ensembleLib = new EnsembleLibrary();
      //   cls= new Classification();     
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
                        word = word.toLowerCase();
                        record = word.trim() + "\t" + type.trim(); 
                        record = record.toLowerCase();
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
   
   public static Instances loadCsvData(String CSVFileName) throws IOException{
       // When we first rime run a csv file and turn it into a ARFF file :)
       String CSVInputFile = CSVFileName;
       CSVLoader loader = new CSVLoader();
       Instances data;
       loader.setNoHeaderRowPresent(true);  // the csv file does not have a header
       loader.setFieldSeparator("\t");
       loader.setSource(new File(CSVInputFile)); // we set the CSV file to convert
       data = loader.getDataSet();
       System.setErr(err);
       data.setClassIndex(data.numAttributes()-1);// the last field is the attributes          
        
  // To scan through all the records of the CSV file
       Enumeration all = data.enumerateInstances();
       
       return data; // return data loaded from the CSV file
   } // end of createARFF
   
   public static void SaveARFF(Instances data, String ArffFileName) throws IOException{
       String ArffFile = ArffFileName;
       Instances dataArff = data;
       ArffSaver arffSaverInstance = new ArffSaver();  // we create an instance to save the ARFF file....
       dataArff.renameAttribute(0, "palabras");
       dataArff.renameAttribute(1,"idioma");
       arffSaverInstance.setInstances(dataArff);
       arffSaverInstance.setFile(new File(ArffFile));
       arffSaverInstance.writeBatch();
   } // end of SaveARFF records.... 
   
   public static Instances LoadARFF(String Filename) throws FileNotFoundException, IOException{
     // loads a ARFF file previously saved.   
       String file = Filename; 
       Instances dataset; 
       BufferedReader reader = new BufferedReader(new FileReader(file));
       ArffReader arff = new ArffReader(reader, 1000);
       dataset = arff.getStructure();
       dataset.setClassIndex(dataset.numAttributes() - 1);
       Instance inst;
        while ((inst = arff.readInstance(dataset)) != null) {
                      dataset.add(inst);
        }// end of while dataset...
        
       reader.close();
       
     return dataset;
     
   } // end of void LOADARFF

   
   public void loadModel(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            Object tmp = in.readObject();
            classifier = (FilteredClassifier) tmp; in.close();
           
}


   public static void  LoadData( String ArffFile) throws IOException, Exception{
       Classifier baselineNB = new NaiveBayes();
       Instances mainData = LoadARFF(ArffFile); // we load the ArffFile file to the instance format
        ClassifierWithFilter(baselineNB, mainData, ArffFile);
   }// end of Load Data 
   
   
      public static void  LoadData1( String ArffFile) throws IOException, Exception{
       Classifier baselineNB = new NaiveBayes();
       Instances mainData = LoadARFF(ArffFile); // we load the ArffFile file to the instance format
       ClassifierWithFilter(baselineNB, mainData, ArffFile);
     //    evaluate(baselineNB, mainData);
   }// end of Load Data 

   
      public static void  LoadNewData(String inputFile, String CSVFile, String ArffFile) throws IOException, Exception{
       String inputxt = inputFile;
       String CSV = CSVFile;
       String ARFF = ArffFile;
       Classifier baselineNB = new NaiveBayes();
       convertToCSV(inputxt,CSVFile);  // first we convert the input txt file to CSV format
       Instances mainData = loadCsvData(CSVFile); // we load the CSV file to the instance format
       SaveARFF(mainData, ArffFile);  // we save the Arff File format
       ClassifierWithFilter(baselineNB, mainData, ArffFile);
       
   }// end of Load New Data 
   
      public static void evaluate(Classifier model, Instances data) throws Exception {
          // this code shows the Confussion Matrix ....
			Instances train_data = data;
                        model.buildClassifier(data);
			// cross-validate the data
                        train_data.setClassIndex(data.numAttributes() - 1);
			Evaluation eval = new Evaluation(train_data);
			eval.crossValidateModel(model, train_data, 5, new Random(1), new Object[] {});
                        System.out.println(eval.toSummaryString()); 
                        System.out.println(eval.toMatrixString());
	} // end of void evaluate
        
         
      public void SaveModel(String modelName) throws FileNotFoundException, IOException
	{
		ObjectOutputStream output=new ObjectOutputStream(
                new FileOutputStream(modelName));
        output.writeObject(classifier);
        output.close();
	} // end of SaveModel void
      
      
  public static void ClassifierWithFilter(Classifier model, Instances data, String FileName ) throws Exception{
           //load dataset
    DataSource source = new DataSource(FileName);
    Instances train_data = data; /// accuracy of model
    Instances dataset = source.getDataSet();
     NaiveBayes nb = new NaiveBayes();
    dataset.setClassIndex(dataset.numAttributes()-1); //set class index to the last attribute
    train_data.setClassIndex(data.numAttributes() - 1);
    nb.buildClassifier(train_data);
			// cross-validate the data
    Evaluation eval = new Evaluation(train_data);
    eval.crossValidateModel(model, train_data, 5, new Random(1), new Object[] {});
    System.out.println("Model performance:\n"+eval.toSummaryString());
    System.out.println(eval.toMatrixString());
    System.out.println(nb.toString());

      }// end of void ClassifierWithFilter
  
  /*public static void ClassifierWithFilter() throws Exception{
           //load dataset
    DataSource source = new DataSource("test/file.arff");
    Instances train_data = source.getDataSet(); /// accuracy of model
    Instances dataset = source.getDataSet();
    dataset.setClassIndex(dataset.numAttributes()-1); //set class index to the last attribute
    J48 tree = new J48();//the base classifier
    NaiveBayes nb = new NaiveBayes();
    nb.buildClassifier(train_data);
    StringToWordVector filter = new StringToWordVector();    //the filter
    filter.setInputFormat(dataset);
    filter.setIDFTransform(true);
  
    LovinsStemmer stemmer = new LovinsStemmer(); //  filter.setUseStoplist(true);
    filter.setStemmer(stemmer);
    filter.setLowerCaseTokens(true);
    FilteredClassifier fc = new FilteredClassifier(); //Create the FilteredClassifier object
    fc.setFilter(filter); //specify filter
    fc.setClassifier(tree);  //specify base classifier
    fc.buildClassifier(dataset);  //Build the meta-classifier
    			// cross-validate the data
   // Evaluation eval = new Evaluation(train_data);
  //  eval.crossValidateModel(model, train_data, 5, new Random(1), new Object[] {});
    
   //    System.out.println("Model performance:\n"+eval.toSummaryString());
  // System.out.println(eval.toMatrixString());
    System.out.println(nb.toString());
    
    System.out.println(tree.graph());
    System.out.println(tree);
    
    
    //return tree;
  
      }// end of void ClassifierWithFilter*/
  
}; // end of NaiveBayes class 


    
