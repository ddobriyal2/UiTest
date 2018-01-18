package org.sly.uitest.pageobjects.salesprocess;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class represents the Forget Password Page, which can be navigated by
 * clicking 'Passwort vergessen?' in
 * http://fsuat.privemanagers.com/financesales/custom_login_page.html
 * 
 * @author Benny Leung
 * @date : 13 Sept, 2016
 * @company Prive Financial
 * 
 */
public class ForgetPasswordPage extends AbstractPage {
	Class<?> theClass = null;

	public ForgetPasswordPage(WebDriver webDriver, Class<?> theClass) {
		super();
		this.webDriver = webDriver;
		this.theClass = theClass;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("gwt-debug-ForgotPasswordPopupView-userLoginTextBox")));

		assertTrue(pageContainsStr("Passwort vergessen"));
	}

	/**
	 * edit user name in forget password page
	 * 
	 * @param username
	 * @return {@link ForgetPasswordPage}
	 */
	public ForgetPasswordPage editUsername(String username) {
		sendKeysToElement(By.id("gwt-debug-ForgotPasswordPopupView-userLoginTextBox"), username);
		return this;
	}

	/**
	 * click reset password button in forget password page
	 * 
	 * @return {@link ForgetPasswordPage}
	 */
	public ForgetPasswordPage clickResetPasswordButton() {
		clickElement(By.id("gwt-debug-ForgotPasswordPopupView-resetPasswordButton"));
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T clickCancelButton() throws Exception {
		clickElement(By.id("gwt-debug-ForgotPasswordPopupView-resetPasswordButton"));
		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);
	}
}
