package org.sly.uitest.sections.basesystem;



import java.util.List;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Getalltags {
	public static void main (String args[]){
	
	WebDriver driver = new FirefoxDriver();
	
	//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	// for simplicity i have used below URl as solution
	driver.get("http://192.168.1.107:8080/SlyAWS/?locale=en&viewMode=MATERIAL#login");
	
	

	// take each and every tag which have id attribute inside the list
	List<WebElement> myTagsWithId = driver.findElements(By.cssSelector("[id]"));
	// if in case you want to work with xpath please use By.xpath("//*[@id]")

	// Print the size of the tags
	System.out.println("Total tags with id as one of the attribute is : " + myTagsWithId.size());

	// now printing all id values one by one
	for(int i =0;i<myTagsWithId.size();i++){
	System.out.println("Id Value is : " + myTagsWithId.get(i).getAttribute("id"));
	        }

} }