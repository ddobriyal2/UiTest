package org.sly.uitest.sections.basesystem;



import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.lang.model.util.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Getalltags {
	public static void main (String args[]) throws InterruptedException{
	
	WebDriver driver = new FirefoxDriver();
	
	//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	// for simplicity i have used below URl as solution
	driver.get("http://192.168.1.107:8080/SlyAWS/?locale=en&viewMode=MATERIAL#login");
	
	
	Thread.sleep(20000);
	// take each and every tag which have id attribute inside the list
	List<WebElement> myTagsWithId = driver.findElements(By.id("gwt-debug-UserLoginView-emailAddress"));
	//List<WebElement> myTagsWithId = driver.findElements(By.id("[contains(@id,'gwt-debug-UserLoginView')]"));
	// if in case you want to work with xpath please use By.xpath("//*[@id]") By.cssSelector("[id]"
	
	
	
	Thread.sleep(10000);
	// Print the size of the tags
	System.out.println("Total tags with id as one of the attribute is : " + myTagsWithId.size());
	
	
	// now printing all id values one by one
	for(int i =0;i<myTagsWithId.size();i++){
	System.out.println("Id Value is : " + myTagsWithId.get(i).getAttribute("gwt-debug-UserLoginView-emailAddress"));
	        }

} }