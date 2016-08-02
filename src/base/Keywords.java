package base;


import io.appium.java_client.android.AndroidKeyCode;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import jxl.Sheet;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import scripts.TestReport;
import utils.TestConstants;

public class Keywords extends FunctionLibrary {

	 TestConstants tc;	
	/**
	 * Retrieving objects form control sheet
	 * @param i
	 * @param colom
	 * @param tdshetnum
	 * @param csheet
	 * @param fileName 
	 * @param keyword2 
	 * @param stepDescription 
	 * @throws Exception 
	 */
	 public String controlScript(int row, int colom, String tdshetnum, Sheet csheet, String testcaseid, String stepDescription, String keyword2, String fileName) throws Exception{
		 controlshet=csheet;
		 steps = new ArrayList<TestReport>();
		 Robot r=new Robot();
		 String result = "Pass";
		 int webtableCounter = 0;
		 for (int k = 1; k < controlshet.getRows(); k++) {
			 tr=new TestReport();
			 TestConstants tc;
			 String testLinkID=controlshet.getCell(0, k).getContents();
			 fileName=testLinkID;
			 String desc=controlshet.getCell(1, k).getContents();
			 String keyword=controlshet.getCell(2, k).getContents().toUpperCase();
			 String keywordtype=controlshet.getCell(3,k).getContents();
			 String objectProp=controlshet.getCell(4,k).getContents();
			 String object=OR.getProperty(objectProp);
			 String data=null;
			 Object testdata=null;
			 String webObject=null;
			 try{
				 switch(keyword){
				 
				 case "CLICK":
					 log.info("Clicking on Button " + object);
					 tc=actionElement(keyword,keywordtype,object,data);
					 result="Pass";
					 break;
				
				 
				 case "INPUT":
					 log.info("Entering Data into "+object);
					 data=null;
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 tc=actionElement(keyword,keywordtype,object,data);
					 result="Pass";
					 colom++;
					 break;  
				
				 case "WAIT":
					 log.info("Loading Page");
					 Thread.sleep(10000);
					 result="Pass";
					 break;
				 case "APPIUM":
					 Appium();
					 break;	 
				 case"flick":
					//  Object testdata6= testData(colom,row,tdshetnum);
					   data=(String) testdata;
					  Flick(data);
					  colom++;
					  break;
				 case"indexinput":
					  //Object testdata= testData(colom,row,tdshetnum);
					   data=(String) testdata;
					  Indexinput(object, data);
					  colom++;
					  break;
				 case"HIDEKEYBOARD":
					  hideKeyboard();
					  break;
				 case"ZOOM":
					 Zoom();
					  break;
				 //case"enter":
					//  enter();
					  //break;
				  case"indexclick":
					  //Object testdata= testData(colom,row,tdshetnum);
					   data=(String) testdata;
					  Indexclick(object, data);
					  colom++;
					  break;
				  
				  
				 default:
					 break;
				 }
				 reportSteps(result,desc,keyword,testLinkID + ".png",object,testcaseid,testLinkID);
				 
			 }catch(UnhandledAlertException e){
				 driver.switchTo().alert().accept();
			 }catch(Exception e){	
				 result="Fail";
				 log.info("",e);
				 failcount++;
				 report(result,desc,keyword,testLinkID,object,testcaseid);
				 e.printStackTrace();
				 tr.result=result;
				 tr.desc=desc;
				 tr.keyword=keyword;
				 tr.fileName=testLinkID;
				 tr.object=object;
				 tr.testcaseid=testcaseid;
				 steps.add(tr);
				 break;
			 }
		 }
		 reportEmailMain(result,fileName,testcaseid,failcount);
		 for (int i = 0; i < steps.size(); i++) {
			 String re=steps.get(i).result;
			 String ds=steps.get(i).desc;
			 String testLinkID=steps.get(i).testLinkID;
			 System.out.println("test Link ID for Email Report=====>>>"+testLinkID);
			 switch (re) {
			 case "Fail":
				 EmailReportUtil.addTestCaseSteps(ds,re,testLinkID);
				 break;
			 case "Pass": case "res":
				 EmailReportUtil.addTestCaseSteps(ds,re,testLinkID);
				 break;
			 default:
				 break;
			 }
		 }
		 return result;
	 }
	 public TestConstants actionElement(String keyword, String keywordtype,String object,String data) throws Exception  {
		 tc=new TestConstants();
		 try{
			 tc.welement=welement(keywordtype, object);
			 switch (keyword.toUpperCase()) {
			 case "CLICK":
				 tc. welement.click();
				 break;
			// case "ENTER":
				//	driver.sendKeyEvent(AndroidKeyCode.ENTER);
				 //break;
			 case "CHECKBOX": case "RADIOBUTTON":
				 tc.welement.click();
				 break;	 
			 case "CLICKESC": 
				 log.info("Clicking on Escape Button");
				 tc.welement.sendKeys(Keys.ESCAPE);
				 break;
			 case "CLICKENTER": 
				 tc.welement.sendKeys(Keys.ENTER);
				 break;
			 case "CLICKTAB":
				 tc.welement.sendKeys(Keys.TAB);
				 break;
			 case "GETELEMENTTEXT":
				 String pageText=tc.welement.getText();
				 log.info("Get Element Text-----------"+pageText);
//				 pastevalue=  getText(tc.welement);
				 break; 
			 case "INPUT":
				 System.out.println(data);
				 tc. welement.sendKeys(data);
				 //tc.welement.clear();
				 /*if (!tc.welement.getAttribute("value").isEmpty()) {
					 tc.welement.sendKeys(Keys.chord(Keys.CONTROL, "a"), data);
				}else{
					System.out.println(data);
				 tc. welement.sendKeys(data);
				}*/
				 break;
			 case "KEYDOWN": 
				 tc.welement.sendKeys(Keys.DOWN);
				 break;		 
			 case "MOUSEOVER":
				 mouseover(tc.welement);
				 break;	
			 case "PASTE": 
				 tc.welement.sendKeys(pastevalue);
				 break;	
			 case "SCROLL":
				 scrollBar(tc.welement);
				 break;	
			 case "SELECT":
				 new Select(tc.welement).selectByVisibleText(data);
				 break;
			 case "WEBSELECT":
				 new Select(tc.welement).selectByVisibleText(data);
				 break;
			 case "SELECTFRAME":
				 frame(tc.welement);
				 break;
			 case "WAIT":
				 Thread.sleep(Long.parseLong(data));
				 break;
			 case "WEBINPUT":
				 tc.welement.clear();
				 tc. welement.sendKeys(data);
				 break;
			 default:
				 break;
			 }
			 tc.result="Pass";
		 }catch(Exception e){
			 tc.result="Fail";
		 }
		 return tc;
	 }
	 public WebElement welement(String keywordtype, String object) throws Exception{
		 WebElement welement =null;
		 driverWait=new WebDriverWait(driver, 10);
		try{	
			switch (keywordtype.toUpperCase()) {
			case "ID":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(object)));
				welement=driver.findElement(By.id(object));
				return welement ;
			case "XPATH":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)));
				welement=driver.findElement(By.xpath(object));
				return welement ;
			case "LINKTEXT":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(object)));
				welement=driver.findElement(By.linkText(object));
				return welement ;
			case "PLINK":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(object)));
				welement=driver.findElement(By.partialLinkText(object));
				return welement ;
			case "CLASSNAME":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(object)));
				welement=driver.findElement(By.className((object)));
				return welement ;
			case "NAME":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(object)));
				welement=driver.findElement(By.name((object)));
				return welement ;	
			case "CSS":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(object)));
				welement=driver.findElement(By.cssSelector((object)));
				return welement ;
			default:
				break;
			}
		}catch(Exception e){
			switch (keywordtype.toUpperCase()) {
			case "ID":
				Thread.sleep(900);
				welement=driver.findElement(By.id(object));
				return welement ;
			case "XPATH":
				Thread.sleep(900);
				welement=driver.findElement(By.xpath(object));
				return welement ;
			case "LINKTEXT":
				Thread.sleep(900);
				welement=driver.findElement(By.linkText(object));
				return welement ;
			case "PLINK":
				Thread.sleep(900);
				welement=driver.findElement(By.partialLinkText(object));
				return welement ;
			case "CLASSNAME":
				Thread.sleep(900);
				welement=driver.findElement(By.className((object)));
				return welement ;
			case "NAME":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(object)));
				welement=driver.findElement(By.name((object)));
				return welement ;
			case "CSS":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(object)));
				welement=driver.findElement(By.cssSelector((object)));
				return welement ;
			default:
				break;
			
			}
			e.getMessage();
		}
		return welement;
	 }

}
