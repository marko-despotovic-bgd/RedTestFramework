package com.red.testframework.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;

import com.red.testframework.utils.TestConfiguration;

public class WebDriverFactory {


	public static WebDriver initDriver(TestConfiguration configuration) throws Exception {

		switch (configuration.getTestExecutionEnvironment()) {
		case LOCAL:
			switch (configuration.getBrowserType()) {
			case CHROME:
				final File chromeFile = new File(configuration.getChromeDriverPath());
				chromeFile.setExecutable(true,false);
				System.setProperty("webdriver.chrome.driver", configuration.getChromeDriverPath());
				return new ChromeDriver();
			case FIREFOX:
				final File firefoxFile = new File(configuration.getFirefoxDriverPath());
				firefoxFile.setExecutable(true,false);
				System.setProperty("webdriver.gecko.driver", configuration.getFirefoxDriverPath());
				return new FirefoxDriver();
			case IE:
				final File ieFile = new File(configuration.getIeDriverPath());
				ieFile.setExecutable(true,false);
				System.setProperty("webdriver.ie.driver", configuration.getIeDriverPath());
				return new InternetExplorerDriver();
			case EDGE:
				final File edgeFile = new File(configuration.getEdgeDriverPath());
				edgeFile.setExecutable(true,false);
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
