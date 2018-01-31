package org.sly.uitest.sections.basesystem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class test {
	public static void main (String args[]) throws InterruptedException{
		
		 System.setProperty("webdriver.chrome.driver", "src/chromedriver");

		  WebDriver driver = new ChromeDriver();
		  driver.manage().window().maximize();
	//WebDriver driver= new FirefoxDriver();
	
	driver.get("http://192.168.1.107:8080/SlyAWS/?locale=en&viewMode=MATERIAL#login");
	Thread.sleep(5000);
	driver.findElement(By.id("gwt-debug-UserLoginView-emailAddress")).sendKeys("JarlAdvisor");
	                  
	
	driver.findElement(By.id("gwt-debug-UserLoginView-password")).sendKeys("JarlAdvisor");
	
	driver.findElement(By.id("gwt-debug-UserLoginView-loginButton")).click();
	//driver.findElement(By.xpath("//*[@id=\"gwt-debug-UserLoginView-password\"]")).sendKeys("JarlAdvisor");
	//driver.findElement(By.xpath("//*[@id=\"gwt-debug-UserLoginView-loginButton\"]")).click();
	}}
