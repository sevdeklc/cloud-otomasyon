package com.testinium.systematicTestCase.driver;

import com.google.common.base.Splitter;
import com.testinium.systematicTestCase.helper.ElementHelper;
import com.testinium.systematicTestCase.helper.StoreHelper;
import com.testinium.systematicTestCase.methods.*;
import com.testinium.systematicTestCase.utils.ReadProperties;
import com.thoughtworks.gauge.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import static org.apache.log4j.Logger.getLogger;
import static org.apache.log4j.Logger.getRootLogger;

public class Driver {

    private static final Logger logger = LoggerFactory.getLogger(Driver.class);
    public static String osName = FindOS.getOperationSystemName();
    public static ConcurrentHashMap<String,Object> TestMap;
    public static ConcurrentHashMap<String, ConcurrentHashMap<String,Object>> apiMap;
    public static String slash = osName.equals("WINDOWS") ? "\\": "/";
    public static String TestCaseName = "";
    public static String TestClassName = "";
    public static boolean isTestinium = false;
    public static String userDir = System.getProperty("user.dir");
    public static ResourceBundle ConfigurationProp = ReadProperties.readProp("Configuration.properties");
    public static String browserName;
    public static boolean isFullScreen;
    public static WebDriver driver;
    public static String baseUrl;
    public static String platformName;
    public static boolean chromeZoomCondition = false;
    public static boolean firefoxZoomCondition = false;
    public static boolean isSafari = false;
    public static boolean zoomCondition = false;

    @BeforeSuite
    public void beforeSuite(ExecutionContext executionContext) {

        logger.info("*************************************************************************");
        logger.info("------------------------TEST PLAN-------------------------");
        beforePlan();
        if(!isTestinium){
            System.out.println(Locale.getDefault());
            Locale.setDefault(Locale.ENGLISH);
            System.out.println(Locale.getDefault());
        }
    }

    @BeforeSpec
    public void beforeSpec(ExecutionContext executionContext) {

        logger.info("=========================================================================");
        logger.info("------------------------SPEC-------------------------");
        String fileName = executionContext.getCurrentSpecification().getFileName();
        TestClassName = fileName.replace(userDir,"");
        logger.info("SPEC FILE NAME: " + fileName);
        logger.info("SPEC NAME: " + executionContext.getCurrentSpecification().getName());
        logger.info("SPEC TAGS: " + executionContext.getCurrentSpecification().getTags());
    }

    @BeforeScenario
    public void beforeScenario(ExecutionContext executionContext) throws MalformedURLException, Exception {
        logger.info(System.getenv("otomasyon"));
        logger.info("_________________________________________________________________________");
        logger.info("------------------------SCENARIO-------------------------");
        TestCaseName = executionContext.getCurrentScenario().getName();
        logger.info("SCENARIO NAME: " + TestCaseName);
        logger.info("SCENARIO TAG: " + executionContext.getCurrentScenario().getTags().toString());
        logger.info("testbaşladı");
        String sena = System.getenv("sena");
        logger.info("System Name 1 " + sena);
        String test2 = System.getProperty("test2");
        logger.info("System Name 2 " + test2);
        String test3 = System.getenv("test2");
        logger.info("System Name 3 " + test3);
        beforeTest();
    }

    @BeforeStep
    public void beforeStep(ExecutionContext executionContext) {

        //logger.info("═════════  " + executionContext.getCurrentStep().getDynamicText() + "  ═════════");
    }

    @AfterStep
    public void afterStep(ExecutionContext executionContext) throws IOException {

        if (executionContext.getCurrentStep().getIsFailing()) {

            logger.error(executionContext.getCurrentSpecification().getFileName());
            //logger.error(executionContext.getCurrentStep().getStackTrace()); // 0.6.5
            //logger.error("Message: " + executionContext.getCurrentStep().getErrorMessage() + "\r\n"
                  //  + executionContext.getCurrentStep().getStackTrace());
        }
        logger.info("══════════════════════════════════════════════════════════════════════════════════════════════════════");
    }

    @AfterScenario
    public void afterScenario(ExecutionContext executionContext) {

        afterTest();
        if (executionContext.getCurrentScenario().getIsFailing()) {

            logger.info("TEST BAŞARISIZ");
        } else {

            logger.info("TEST BAŞARILI");
        }

        logger.info("_________________________________________________________________________");
    }

    @AfterSpec
    public void afterSpec(ExecutionContext executionContext) {

        logger.info("=========================================================================");
    }

    @AfterSuite
    public void afterSuite(ExecutionContext executionContext) {

        afterPlan();
        logger.info("*************************************************************************");
    }

    public void beforePlan(){

        String dir = "/src/test/resources/log4j.properties";
        if(!slash.equals("/")) {
            dir = dir.replace("/", "\\");
        }
        PropertyConfigurator.configure(userDir + dir);

        TestMap = new ConcurrentHashMap<String, Object>();
        apiMap = new ConcurrentHashMap<String, ConcurrentHashMap<String,Object>>();
        String logLevel = ConfigurationProp.getString("logLevel");
        getRootLogger().setLevel(Level.toLevel(logLevel));

        if(!logLevel.equals("ALL")) {
            String methodsClassLogLevel = ConfigurationProp.getString("methodsClassLogLevel");
            String elementHelperLogLevel = ConfigurationProp.getString("elementHelperLogLevel");
            getLogger(Driver.class).setLevel(Level.ALL);
            getLogger(TestiniumBrowserExec.class).setLevel(Level.ALL);
            getLogger(LocalBrowserExec.class).setLevel(Level.ALL);
            getLogger(FindOS.class).setLevel(Level.ALL);
            getLogger(StoreHelper.class).setLevel(Level.toLevel(elementHelperLogLevel));
            getLogger(ElementHelper.class).setLevel(Level.toLevel(elementHelperLogLevel));
            getLogger(Methods.class).setLevel(Level.toLevel(methodsClassLogLevel));
            getLogger(JsMethods.class).setLevel(Level.toLevel(methodsClassLogLevel));
            getLogger(ActionMethods.class).setLevel(Level.toLevel(methodsClassLogLevel));
            getLogger(ApiMethods.class).setLevel(Level.toLevel(methodsClassLogLevel));
            getLogger(MethodsUtil.class).setLevel(Level.toLevel(methodsClassLogLevel));
        }
    }

    public void beforeTest(){

        try {
            createDriver();
        }catch (Throwable e) {

            StackTraceElement[] stackTraceElements = e.getStackTrace();
            String error = e.toString() + "\r\n";
            for (int i = 0; i < stackTraceElements.length; i++) {

                error = error + "\r\n" + stackTraceElements[i].toString();
            }
            throw new SessionNotCreatedException(error);
        }
    }

    public void afterTest() {

        if (isTestinium || Boolean.parseBoolean(ConfigurationProp.getString("localQuitDriverActive"))) {
            quitDriver();
        }
    }


    public void afterPlan(){

        System.out.println("");
    }

    public void createDriver() throws Exception {

        String key = System.getenv("key");
        browserName = ConfigurationProp.getString("browserName");
        baseUrl = ConfigurationProp.getString("baseUrl");
        isFullScreen = Boolean.parseBoolean(ConfigurationProp.getString("isFullScreen"));
        if(StringUtils.isEmpty(key)) {
            isTestinium = false;
            platformName = FindOS.getOperationSystemNameExpanded();
            driver = LocalBrowserExec.LocalExec(browserName);
        }
        else {
            isTestinium = true;
            driver = TestiniumBrowserExec.TestiniumExec(key);
        }

        logger.info("Driver ayağa kaldırıldı.");
        isSafari = browserName.equalsIgnoreCase("safari");
        zoomCondition = (browserName.equalsIgnoreCase("chrome") && chromeZoomCondition)
                || (browserName.equalsIgnoreCase("firefox") && firefoxZoomCondition);
        driver.get(baseUrl);
    }

    public void quitDriver() {

        if(driver != null){
            driver.quit();
            logger.info("Driver kapatıldı.");
        }
    }

    /**
     System.out.println(System.getProperty("user.dir"));
     System.out.println(System.getProperty("user.home"));
     System.out.println(System.getProperty("user.name"));
     System.out.println(System.getProperty("file.separator"));
     System.out.println(System.getProperty("file.encoding"));
     */

}
