package com.red.testframework.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;

import com.red.testframework.testconfiguration.TestConfiguration;


public class WebDriverFactory {
	public static WebDriver initDriver(TestConfiguration configuration) throws Exception {
		final File file = new File(configuration.getChromeDriverPath());
		file.setExecutable(true,false);
		switch (configuration.getTestExecutionEnvironment()) {
		case LOCAL:
			switch (configuration.getBrowserType()) {
			case CHROME:
				System.setProperty("webdriver.chrome.driver", configuration.getChromeDriverPath());

				return new ChromeDriver();
			case FIREFOX:
				System.setProperty("webdriver.gecko.driver", configuration.getFirefoxDriverPath());
				return new FirefoxDriver();
			case IE:
				System.setProperty("webdriver.ie.driver", configuration.getIeDriverPath());
				return new InternetExplorerDriver();
			case EDGE:
				System.setProperty("webdriver.edge.driver", configuration.getEdgeDriverPath());
				return new EdgeDriver();
			case CHROME_HEADLESS:
				System.setProperty("webdriver.chrome.driver", configuration.getChromeDriverPath());
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--no-sandbox");
				options.addArguments("--whitelisted-ips=''");
				options.addArguments("--headless");
				return new ChromeDriver(options);
			default:
				System.setProperty("webdriver.chrome.driver", configuration.getChromeDriverPath());
				return new ChromeDriver();
			}
			
//		case REMOTE:
//		case DOCKER:
//		case BROWSERSTACK:

		default:
			System.out.println("Executing default");
			return new ChromeDriver();
		}
	}
}
