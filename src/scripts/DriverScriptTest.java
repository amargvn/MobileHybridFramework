package scripts;


import io.appium.java_client.android.AndroidDriver;
import jxl.Sheet;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.TestUtil;
import base.EmailReportUtil;
import base.Keywords;
import base.ReportUtil;

public class DriverScriptTest extends Keywords {
public static AndroidDriver driver;
	public static String keyword;
	public static String stepDescription;
	public static String result;
	public static DriverScriptTest dstest;
	static float totalTestCaseCount, runTestCaseCount = 0, failedTestCases;

	@BeforeClass
	public void beforeClass() throws Exception {
		initialize();
		log.info("Initialized All Resources Files");
		setTestClassName(DriverScriptTest.this.getClass().getName());
		log.info("Creatng Driver Script Object");
		dstest = new DriverScriptTest();
		log.info("Creating Test Suite");
		startTesting();
		log.info("Creating Test Suite For Email Report");
		emailStartTesting();

	}

	@Test

	public void controlscript()
			throws Exception {
		
		System.out.println("test");
		
		int colom=3, m=1;
		controlshet = controllerwb.getSheet("Suite");
		for (int i = 0; i < controlshet.getRows(); i++) {
			String tsrunmode=controlshet.getCell(2,i).getContents();
			if (tsrunmode.equalsIgnoreCase("Y")) {
				String tcaseid=controlshet.getCell(0,i).getContents();
				Sheet tdsheet1=testdatawb.getSheet(tcaseid);
				//control sheet
				Sheet controlshet=controllerwb.getSheet(tcaseid);
				String fileName=null;
				for (int j = 1; j < tdsheet1.getRows(); j++) {
					String tcaserunmode=tdsheet1.getCell(2,j).getContents();
					if (tcaserunmode.equalsIgnoreCase("y")) {
						String testcaseid=tdsheet1.getCell(0,j).getContents();
						String testdesc=tdsheet1.getCell(1,j).getContents();
						stepDescription=testdesc;
						keyword=testcaseid;
						
						//closeBrowser();
						//createLabel(m, testcaseid, testdesc, "Pass");
						controlshet = controllerwb.getSheet(tcaseid);
						System.out.println("filename is---==>."+fileName);
						log.info("Passing Parameters Driver Script to ContolScript");
						String result=controlScript(j, colom, tcaseid,controlshet,testcaseid,stepDescription,keyword,fileName);
						//report(result,fileName);
						if (failcount >= 1 || rptFailCnt >= 1) {
							result = "Fail";
							log.info("Test Scenario Result --" + result);
							if (failcount == 0) {
								failedTestCases += rptFailCnt;
							} else {
								failedTestCases += failcount;
							}
							mainReport(keyword, result, fileName);
							rptFailCnt = 0;
							failcount = 0;
						} else {
							result = "Pass";
							log.info("Test Scenario Result --" + result);
							mainReport(keyword, result, fileName);
						}

					}
					m++;
				}
			}
			tdsheet=testdatawb.getSheet("Suite");
		}
		
	}
	

	@AfterSuite
	public static void endScript() throws Exception {
		ReportUtil.updateEndTime(TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"));
		EmailReportUtil.updateEndTime(TestUtil
				.now("dd.MMMMM.yyyy hh.mm.ss aaa"));
		// mail();
	}
}