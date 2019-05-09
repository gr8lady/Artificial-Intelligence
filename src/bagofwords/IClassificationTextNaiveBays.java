/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bagofwords;

import java.io.File;
import java.io.IOException;
import bagofwords.Classification;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author heizel
 */
public interface IClassificationTextNaiveBays {
    void LoadDataset(File arffFileName) throws IOException;
	List<Classification> EvaluateNaiveBays() throws Exception;
	void LearnNaiveBays() throws Exception;
	void SaveModel(String modelName) throws FileNotFoundException, IOException;
    
}
