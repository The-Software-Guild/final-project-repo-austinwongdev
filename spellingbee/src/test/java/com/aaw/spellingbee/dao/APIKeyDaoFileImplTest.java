/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aaw.spellingbee.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Austin Wong
 */

@SpringBootTest
public class APIKeyDaoFileImplTest {
    
    @Autowired
    private APIKeyDao apiKeyDao;
    
    public APIKeyDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getMerriamWebsterAPIKey method, of class APIKeyDaoFileImpl.
     */
    @Test
    public void testGetMerriamWebsterAPIKey() {
        
        String apiKey = apiKeyDao.getMerriamWebsterAPIKey();
        assertNotNull(apiKey);
        assertTrue(apiKey.length() > 0);
        
    }
    
}
