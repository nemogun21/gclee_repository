package com.cj.mobile;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import java.net.URL;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.cj.util.SmartProperties;

public class M_Shock_001 {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	WebElement element = null;
	boolean setupSuccess = true;
	private String M_URL = null;
	boolean isExist1 = false;
	boolean isExist2 = false;
	/**
	 * 
	 * @author 김명환
	 * Date : 2018-05-03  
	 * Subject : CJ Mall 운영  
	 * Name : M_Shock_001  
	 * Scenario : CJmall > 쇼크라이브 > 편성표  
	 * Assertion : 타이틀 "쇼크LIVE 편성표" 확인
	 *   
	 */

	@Before
	public void setUp() throws Exception {

		SmartProperties sp = SmartProperties.getInstance();
		M_URL = sp.getProperty("M_URL");

		Thread.sleep(5000);
		DesiredCapabilities caps = new DesiredCapabilities();
		caps = DesiredCapabilities.android();

		// device name은 큰 의미없음. LG폰도 이 옵션으로 수행됨
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "LGF460S859d639d");
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		caps.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");

		URL appiumUrl = new URL("http://127.0.0.1:4723/wd/hub");

		System.out.println("Start driver.");
		driver = new AndroidDriver<WebElement>(appiumUrl, caps);

	}

	@Test
	
	
	public void schedule_01() throws InterruptedException {
		
			driver.get(M_URL);
		
			// 알럿 발생확인
			isExist1 = existElement(driver, By.xpath(".//*[@id='notToday']/label"), "오늘 하루 보이지 않기");
			if (isExist1) {
				// 오늘 하루 보지 않기 버튼 클릭
				driver.findElement(By.xpath(".//*[@id='notToday']/label")).click();
				System.out.println("오늘 하루 보지 않기 버튼 클릭");
				Thread.sleep(3000);
			} else {
				// 알럿이 없는 경우
				System.out.println("알럿 없음 다음 내용 수행");
				Thread.sleep(3000);
			}

			// 쇼크라이브
			driver.findElement(By.linkText("쇼크LIVE")).click();
			System.out.println("쇼크LIVE 진입");
			Thread.sleep(3000);
					
	        WebElement searchBtn = driver.findElement(By.xpath("//*[@id=\"moduleArea\"]/div[5]/div/div/a/div[1]"));
	        Actions action = new Actions(driver);
	        action.moveToElement(searchBtn).build().perform();
	        Thread.sleep(3000);
	        
			// 편성표 보기 버튼 클릭
			driver.findElement(By.xpath("//*[@id=\"moduleArea\"]/div[4]/div/a/img")).click();
			System.out.println("편성표 보기 클릭. 편성표 진입");
			Thread.sleep(3000);
			
			//편성표 진입 확인
			if ("쇼크LIVE 편성표".equals(driver.findElement(By.xpath("//*[@id=\"header\"]/div/h1")).getText())) {
				System.out.println("쇼크LIVE 편성표 진입 확인. 종료 합니다.");
				Thread.sleep(3000);
				assertTrue(true);
				return;
			} else {
				System.out.println("쇼크LIVE 편성표 진입 안됨");
				Thread.sleep(3000);
				assertTrue(false);
				return;
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
		// wait.ignoring(NoSuchElementException.class);

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
