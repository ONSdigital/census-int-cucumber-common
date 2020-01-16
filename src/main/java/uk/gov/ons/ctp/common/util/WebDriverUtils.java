package uk.gov.ons.ctp.common.util;

import java.util.logging.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverUtils {

  public static WebDriver getWebDriver(
      final WebDriverType webDriverType, final boolean isHeadless, final Level loggingLevel) {
    LoggingPreferences logs = new LoggingPreferences();
    logs.enable(LogType.BROWSER, loggingLevel);
    logs.enable(LogType.CLIENT, loggingLevel);
    logs.enable(LogType.DRIVER, loggingLevel);
    logs.enable(LogType.PERFORMANCE, loggingLevel);
    logs.enable(LogType.PROFILER, loggingLevel);
    logs.enable(LogType.SERVER, loggingLevel);

    DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
    desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);

    WebDriver driver;
    final String os = System.getProperty("os.name").toLowerCase();

    switch (webDriverType) {
      case EDGE:
        setupEdgeOSWebdriver(os);
        driver = getEdgeDriver(isHeadless, os, desiredCapabilities);
        break;

      case CHROME:
        setupChromeOSWebdriver(os);
        driver = getChromeDriver(isHeadless, os, desiredCapabilities);
        break;

      default:
        setupFirefoxOSWebdriver(os);
        driver = getFirefoxDriver(isHeadless, os, desiredCapabilities);
    }
    return driver;
  }

  private static EdgeDriver getEdgeDriver(
      final boolean isHeadless, final String os, final DesiredCapabilities desiredCapabilities) {
    EdgeOptions options = new EdgeOptions();
    if (os.contains("linux")) {
      options.setCapability("headless", isHeadless);
      options.setCapability("binary", "/usr/bin/edge");
    }
    options.merge(desiredCapabilities);
    return new EdgeDriver(options);
  }

  private static FirefoxDriver getFirefoxDriver(
      final boolean isHeadless, final String os, final DesiredCapabilities desiredCapabilities) {
    FirefoxOptions options = new FirefoxOptions();
    if (os.contains("linux")) {
      options.setBinary("/usr/bin/firefox");
    }
    options.setHeadless(isHeadless);
    options.setLogLevel(FirefoxDriverLogLevel.DEBUG);
    options.merge(desiredCapabilities);
    return new FirefoxDriver(options);
  }

  private static ChromeDriver getChromeDriver(
      final boolean isHeadless, final String os, final DesiredCapabilities desiredCapabilities) {
    ChromeOptions options = new ChromeOptions();
    if (os.contains("linux")) {
      options.setBinary("/usr/bin/chrome");
    }
    options.setHeadless(isHeadless);
    options.merge(desiredCapabilities);
    return new ChromeDriver(options);
  }

  private static void setupFirefoxOSWebdriver(final String os) {
    if (os.contains("mac")) {
      System.setProperty(
          "webdriver.gecko.driver", "src/test/resources/geckodriver/geckodriver-v0.26.0.macos");
    } else if (os.contains("linux")) {
      System.setProperty(
          "webdriver.gecko.driver", "src/test/resources/geckodriver/geckodriver-v0.26.0.linux");
    } else {
      System.err.println(
          "Unsupported platform - gecko driver not available for platform [" + os + "]");
      System.exit(1);
    }
  }

  private static void setupChromeOSWebdriver(final String os) {
    if (os.contains("mac")) {
      System.setProperty(
          "webdriver.chrome.driver",
          "src/test/resources/chromedriver/chromedriver.79.0.3945.36.macos");
    } else if (os.contains("linux")) {
      System.setProperty(
          "webdriver.chrome.driver",
          "src/test/resources/chromedriver/chromedriver.79.0.3945.36.linux");
    } else {
      System.err.println(
          "Unsupported platform - gecko driver not available for platform [" + os + "]");
      System.exit(1);
    }
  }

  private static void setupEdgeOSWebdriver(final String os) {
    if (os.contains("mac")) {
      System.setProperty(
          "webdriver.gecko.driver", "src/test/resources/geckodriver/geckodriver-v0.26.0.macos");
    } else if (os.contains("linux")) {
      System.setProperty(
          "webdriver.gecko.driver", "src/test/resources/geckodriver/geckodriver-v0.26.0.linux");
    } else {
      System.err.println(
          "Unsupported platform - gecko driver not available for platform [" + os + "]");
      System.exit(1);
    }
  }
}
