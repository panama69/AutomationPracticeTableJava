package com.hpe.lft;

import static org.junit.Assert.*;

import java.io.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.hp.lft.sdk.*;
import com.hp.lft.sdk.web.*;
import com.hp.lft.report.*;
import com.hp.lft.verifications.*;
import unittesting.*;
/* Goal as desribed on: http://toolsqa.com/selenium-webdriver/handle-dynamic-webtables-in-selenium-webdriver/
 * 
 * 1) Launch new Browser
 * 2) Open URL “http://toolsqa.com/automation-practice-table/”
 * 3) Get the value from cell ‘Dubai’ with using dynamic xpath
 * 4) Print all the column values of row ‘Clock Tower Hotel’
 * 
 * This test was inspired when I came across the above example and figured
 * there had to be a better an easier way in LFT to accomplish this rather
 * than the cumbersome and in flexible methed shown using Xpath in Selenium.
 * Yes, there are other ways to accomplish this in Selenium without using
 * Xpath but from my research it is still cumbersome as it doesn't provide
 * you with a mechanism to find a row based on its contents and then process
 * the row easily.
 * 
 * When I say easily, I am referring to the fact that LFT can accomplish this
 * without needing to knwo the row and column counts or iteratively loop
 * through to find ID, tags or even resort to CSS which still needs to know
 * column number value.
 */

public class LeanFtTest extends UnitTestClassBase {

	public LeanFtTest() {
		//Change this constructor to private if you supply your own public constructor
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		instance = new LeanFtTest();
		globalSetup(LeanFtTest.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		globalTearDown();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void AutomationPracticeTable() throws Exception {
		
        //reporter.setSnapshotCaptureLevel();

        Browser browser = BrowserFactory.launch(BrowserType.CHROME);
        browser.navigate("http://toolsqa.com/automation-practice-table/");

        //3) Get the value from cell ‘Dubai’ with using dynamic xpath
        String mySearchValue = "Dubai";
        String myCellValue = browser.describe(WebElement.class,
        		new WebElementDescription.Builder().tagName("TD").innerText("Dubai").build()).getInnerHTML();

        Reporter.reportEvent("Cell value:" + myCellValue, myCellValue);
        System.out.println("Cell value:"+myCellValue);
        
        //4) Print all the column values of row ‘Clock Tower Hotel’
        mySearchValue = "Clock Tower Hotel";
        TableRow myRow = browser.describe(Table.class, 
        		new TableDescription.Builder().tagName("TABLE").index(0).build()).findRowWithCellText(mySearchValue);
        
        Reporter.reportEvent("Contents for row:" + mySearchValue, "");
        System.out.println("Conents for row: "+mySearchValue);
        
        Boolean firstEntry = true;
        
        for (TableCell myCell : myRow.getCells()){
            if (firstEntry)
                firstEntry = false;
            else
            	try{
            		Reporter.reportEvent("Row contents:" + myCell.getText().toString(), myCell.getText().toString());
            		System.out.println("Row Conents: "+myCell.getText().toString());
                }catch (ReportException e){}
        }
        
        browser.close();
	}
}