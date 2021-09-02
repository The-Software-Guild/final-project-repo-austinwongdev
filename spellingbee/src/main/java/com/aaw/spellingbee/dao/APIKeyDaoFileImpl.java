/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Sep 2, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Austin Wong
 */

@Component
public class APIKeyDaoFileImpl implements APIKeyDao {

    private static final String API_KEY_FILE = "src/main/resources/merriamWebsterAPIKey.txt";
    private String merriamWebsterAPIKey;
    
    public APIKeyDaoFileImpl() throws FileNotFoundException{
        loadMerriamWebsterAPIKey();
    }
    
    @Override
    public String getMerriamWebsterAPIKey(){
        return merriamWebsterAPIKey;
    }
    
    private void loadMerriamWebsterAPIKey() throws FileNotFoundException {
        
        Scanner scanner;

        try{
            scanner = new Scanner(
                    new BufferedReader(new FileReader(API_KEY_FILE)));
        }
        catch (FileNotFoundException ex){
            throw new FileNotFoundException("Could not load Merriam-Webster API Key");
        }

        String currentLine;

        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            merriamWebsterAPIKey = currentLine;
        }

        scanner.close();
            
    }
    
}
