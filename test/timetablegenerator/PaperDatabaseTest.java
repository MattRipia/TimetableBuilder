/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetablegenerator;

import java.util.ArrayList;
import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.internal.ArrayComparisonFailure;

/**
 *
 * @author gjr5542
 */
public class PaperDatabaseTest {
    
    public PaperDatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of removePaperAtIndex method, of class PaperDatabase.
     */
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void testRemovePaperAtIndexNegativeIndex() {
        System.out.println("removePaperAtIndexNegitiveIndex");
        int i = -5;
        PaperDatabase instance = new PaperDatabase();
        instance.removePaperAtIndex(i);
    }
    
    @Test (expected = IndexOutOfBoundsException.class)
    public void testRemovePaperAtIndexPostiveIndex() {
        System.out.println("removePaperAtIndexPositive");
        int i = 5;
        PaperDatabase instance = new PaperDatabase();
        instance.removePaperAtIndex(i);
    }

    /**
     * Test of getPaperTitles method, of class PaperDatabase.
     */
    @Test
    public void testGetPaperTitles() {
        System.out.println("getPaperTitles");
        ArrayList<Paper> paperArrayList = new ArrayList();
        PaperDatabase instance = new PaperDatabase();
        Paper aPaper = new Paper();
        paperArrayList.add(aPaper);
        String[] expResult = {"unknown"};
        String[] result = instance.getPaperTitles(paperArrayList);
        assertArrayEquals(expResult, result);
    }
    
    @Test (expected = ArrayComparisonFailure.class)
    public void testGetPaperTitlesAssertFail() {
        System.out.println("getPaperTitles");
        ArrayList<Paper> paperArrayList = new ArrayList();
        PaperDatabase instance = new PaperDatabase();
        Paper aPaper = new Paper();
        paperArrayList.add(aPaper);
        String[] expResult = {""};
        String[] result = instance.getPaperTitles(paperArrayList);
        assertArrayEquals(expResult, result);
    }
}
