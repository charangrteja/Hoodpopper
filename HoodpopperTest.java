import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

// Initiating the class to test the Ruby compilation visualizer, Hoodpopper 
public class HoodpopperTest {
	static WebDriver driver = new HtmlUnitDriver();
	
	// Start at the home page for Hoodpopper for each test
	@Before
	public void setUp() throws Exception {
		driver.get("http://lit-bayou-7912.herokuapp.com/");
	}
	/************************************************************************************/
	/**
	 * As a user,
	 * I would like to verify identifiers of my tokenized code
	 * So that I can know different objects of my code is getting appropriate identifiers
	 */
	
	// Given that I am on the main page
	// When I view the title
	// Then I see that it contains the word "Hoodpopper"
	@Test
	public void testShowsCorrectTitle() {
		
		// Simply check that the title contains the word "Hoodpopper"
		
		String title = driver.getTitle();
		assertTrue(title.contains("Hoodpopper"));
	}
	
	// Given that I am on main page
	// When I click on the Tokenize button
	// I should be navigated to a new page and I should see a "Back" button to navigate back
	@Test
	public void testTokenizeAndBackButton(){
		// Find the Tokenize button and click it
		WebElement e = driver.findElement(By.name("commit"));
		e.click();
		// Find the linktext "Back"
		try{
			driver.findElement(By.linkText("Back"));
		}
		catch (NoSuchElementException nseex) {
		fail();
	}
	}
	
	// Given that I am on the main page
	// When I declare a variable "a" with int value 1234 and click on tokenize
	// Then I should see the identifiers "on_ident" for variable a, "on_op" for "=" and "on_int" for the int value declared.
	@Test
	public void testTokenization(){
		// Find the code input area and enter the text a=1234
		driver.findElement(By.id("code_code")).sendKeys("a=1234");
		WebElement e = driver.findElement(By.name("commit"));
		// Click on Tokenize button
		e.click();
		// Convert the result to string and check for identifiers
		String result = driver.findElement(By.tagName("body")).getText();
		assertTrue(result.contains("on_ident"));
		assertTrue(result.contains("on_op"));
		assertTrue(result.contains("on_int"));
	}
	
	// Given that I am on the main page
	// When I enter the "puts" command on a new line of code box and click tokenize
	// Then I should see the identifiers "on_ident" for puts , "on_sp" and "on_nl" for spaces 
	// and new line respectively and "on_op" for the + operator.
	@Test
	public void testTokenization2(){
		// Find the code input area and enter the text "a=1234 \n puts \"The Value is\"+a"
		driver.findElement(By.id("code_code")).sendKeys("a=1234 \n puts \"The Value is\"+a");
		// Find and  Click on Tokenize button
		WebElement e = driver.findElement(By.name("commit"));
		e.click();
		// Capure the results displayed on new page and assign it to String variable
		String result = driver.findElement(By.tagName("body")).getText();
		// Check for identifiers using assertions
		assertTrue(result.contains("on_ident"));
		assertTrue(result.contains("on_sp"));
		assertTrue(result.contains("on_nl"));
		assertTrue(result.contains("on_op"));
	}
	/***********************************************************************************************/
	/**
	 * As a user,
	 * I would like to verify the tokens that are being parsed
	 * So that I can know only the appropriate tokens are being parsed and appear in the AST
	 */
	
	// Given that I am on main page
	// When I click on the Parse button
	// I should be navigated to a new page and I should see a "Back" button to navigate back
	@Test
	public void testParseAndBackButton(){
		// Find the Parse button and click it
		WebElement e = driver.findElement(By.xpath("(//input[@name='commit'])[2]"));
		e.click();
		// Find the linktext "Back"
		try{
			driver.findElement(By.linkText("Back"));
		}
		catch (NoSuchElementException nseex) {
		fail();
	}
	}
	// Given that I am on the main page
	// When I declare a variable "a" with int value 1234 and click on Parse
	// Then I should be navigated to the results page and I should see that the result contains 
	// "@ident" for the variable a and @tstring_content for the String "1234" 
	@Test
	public void testParsing(){
		driver.findElement(By.id("code_code")).sendKeys("a = \"1234\"");
		WebElement e = driver.findElement(By.xpath("(//input[@name='commit'])[2]"));
		e.click();
		String result = driver.findElement(By.tagName("body")).getText();
		assertTrue(result.contains("@ident"));
		assertTrue(result.contains("@tstring_content"));
	}
	
	// Given that I am on the main page
	// When I enter the "puts" command on a new line of code box and click parse
	// Then I should be navigated to the results page and I should see only non-white space token identifiers in AST
	@Test
	public void testParsing2(){
		driver.findElement(By.id("code_code")).sendKeys("a=1234 \n puts \"The Value is\"+a");
		WebElement e = driver.findElement(By.xpath("(//input[@name='commit'])[2]"));
		e.click();
		String result = driver.findElement(By.tagName("body")).getText();
		assertTrue(result.contains("@ident"));
		assertTrue(result.contains("+"));
		assertTrue(result.contains("@tstring_content"));
		assertFalse(result.contains("sp"));
	}
	
	// Given that I am on the main page
	// When I enter the spaces and new lines as input to the code and click Parse
	// Then I should not see any white spaces in result and should return void_stmt
	@Test
	public void testParsingNonWhiteSpaces(){
		driver.findElement(By.id("code_code")).sendKeys("  \n ");
		WebElement e = driver.findElement(By.xpath("(//input[@name='commit'])[2]"));
		e.click();
		String result = driver.findElement(By.tagName("body")).getText();
		System.out.println(result);
		assertTrue(result.contains("void_stmt"));
		
	}
	
	/*********************************************************************************************/
	/**
	 * As a user,
	 * I would like to verify operations of my compiled code
	 * So that I can know all the functions of my code have appropriate operations.
	 */
	// Given that I am on main page
	// When I click on the Compile button
	// I should be navigated to a new page and I should see a "Back" button to navigate back
	@Test
	public void testCompileAndBackButton(){
		// Find the Compile button and click it
		WebElement e = driver.findElement(By.xpath("(//input[@name='commit'])[3]"));
		e.click();
		// Find the linktext "Back"
		try{
			driver.findElement(By.linkText("Back"));
		}
		catch (NoSuchElementException nseex) {
		fail();
	}
	}
	
	// Given that I am on main page
	// When I enter the puts function and click on compile
	// Then I should see the putobject in the result for int value supplied, putstring for puts function
	// and opt_plus operator for the + 
	@Test
	public void testCompilePuts(){
		driver.findElement(By.id("code_code")).sendKeys("a=1234 \n puts \"The Value is\"+a");
		WebElement e = driver.findElement(By.xpath("(//input[@name='commit'])[3]"));
		e.click();
		String result = driver.findElement(By.tagName("body")).getText();
		assertTrue(result.contains("putobject"));
		assertTrue(result.contains("putstring"));
		assertTrue(result.contains("opt_plus"));
		}
	// Given that I am on main page
	// When I enter the input with various arithmetic operations and click compile
	// Then I should see the opt_mult, opt_div, opt_plus and opt_minus for *,/,+,- operators respectively.
	@Test
	public void testCompileArithmetic(){
		driver.findElement(By.id("code_code")).sendKeys("a=b+c-d*e/f");
		WebElement e = driver.findElement(By.xpath("(//input[@name='commit'])[3]"));
		e.click();
		String result = driver.findElement(By.tagName("body")).getText();
		assertTrue(result.contains("opt_mult"));
		assertTrue(result.contains("opt_div"));
		assertTrue(result.contains("opt_plus"));
		assertTrue(result.contains("opt_minus"));
		}
	

}
