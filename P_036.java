package com.cj.pc;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.cj.util.SmartProperties;

/**
 * 
 * @author 조성주 Date : 2017-06-13
 * Subject : CJ Mall 운영 
 * Name : TC_36
 * Scenario :임의의 상품 > 장바구니 버튼 선택 > 알럿 확인 버튼 선택 > ID / PW 입력 > 로그인
 * Assertion : 장바구니 Text 체크
 *
 */

public class P_036 {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	private String P_URL = null;
	private String PRODUCT = null;

	@Before
	public void setUp() throws Exception {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


		SmartProperties sp = SmartProperties.getInstance();
		P_URL = sp.getProperty("P_URL");
		PRODUCT = sp.getProperty("PRODUCT");
	}

	@Test
	public void p_036() throws Exception {
		driver.get(P_URL);

		// 임의상품진입
		driver.findElement(By.id("srh_keyword")).clear();
		driver.findElement(By.id("srh_keyword")).sendKeys(PRODUCT);
		driver.findElement(By.cssSelector("button._search")).click();
		System.out.println("임의상품 진입 성공");
		Thread.sleep(3000);
		// 장바구니 담기 클릭
		driver.findElement(By.xpath("//*[@id=\"cont_listing0\"]/div[6]/ul/li/a")).click();
		Thread.sleep(3000);
		WebElement searchBtn = driver.findElement(By.xpath("//*[@id=\"_RECOMMEND\"]/div[1]/h4"));
		Actions action = new Actions(driver);
		action.moveToElement(searchBtn).perform();

		boolean isExist1 = false;
		String cart = null;
		isExist1 = existElement(driver, By.xpath("//*[@id=\"content\"]/div[2]/div[1]/div[2]/div[3]/div[2]/a"), "찜");
		if (isExist1) {
			cart = "//*[@id=\"content\"]/div[2]/div[1]/div[2]/div[3]/div[2]/a";
			System.out.println("1");
		} else {
			cart = "//*[@id='content']/div[2]/div[1]/div[2]/div[2]/div[2]/a";
			System.out.println("2");
		}
		driver.findElement(By.xpath(cart)).click();
		// 얼럿닫기
		Alert alert = driver.switchTo().alert();
		alert.accept();
		Thread.sleep(3000);
		System.out.println("얼럿닫기 성공");
		// 장바구니 1개 담김 체크
		driver.findElement(By.xpath("//*[@id='id_input']")).clear();
		driver.findElement(By.xpath("//*[@id='id_input']")).sendKeys("homecart1");
		driver.findElement(By.xpath(".//*[@id='password_input']")).clear();
		driver.findElement(By.xpath(".//*[@id='password_input']")).sendKeys("stasta1!");
		driver.findElement(By.xpath(".//*[@id='loginSubmit']")).click();
		Thread.sleep(3000);
		System.out.println("장바구니 진입 성공");
		driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[1]/h1/a")).click();
		Thread.sleep(3000);
		System.out.println("로고클릭 성공");
		// 장바구니 1개 담김 체크
		if ("1".equals(driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[1]/div[5]/ul/li[3]/a/strong")).getText())) {
			//장바구니 진입
			driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[1]/div[5]/ul/li[3]/a")).click();
			//삭제
			driver.findElement(By.xpath("//*[@id='_fullBasket']/div[2]/div/div[1]/div[1]/button")).click();
			Thread.sleep(3000);
			
			System.out.println("TC_36 Pass");
			assertTrue(true);
			return;
		} else {
			System.out.println("TC_36 Fail");
			assertTrue(false);
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	public boolean existElement(WebDriver wd, By by, String meaning) {
		WebDriverWait wait = new WebDriverWait(wd, 2);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));

		} catch (TimeoutException e) {

			System.out.println("[" + meaning + "] WebElement does not Exist. time out ");
			return false;
		}
		System.out.println("[" + meaning + "] WebElement Exist.");
		return true;
	}

}