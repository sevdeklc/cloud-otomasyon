package com.testinium.systematicTestCase.methods;

import com.google.common.base.Splitter;
import com.testinium.systematicTestCase.driver.Driver;
import com.testinium.systematicTestCase.helper.ElementHelper;
import com.testinium.systematicTestCase.helper.StoreHelper;
import com.testinium.systematicTestCase.model.ElementInfo;
import com.testinium.systematicTestCase.utils.ImageColor;
import com.thoughtworks.gauge.Step;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class Methods {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    WebDriver driver;
    FluentWait<WebDriver> wait;
    JsMethods jsMethods;
    ActionMethods actionMethods;
    MethodsUtil methodsUtil;
    long waitElementTimeout = 30;
    long pollingEveryValue = 300;

    public Methods(){

        this.driver = Driver.driver;
        setWaitElementTimeout();
        setPollingEveryValue();
        wait = setFluentWait(waitElementTimeout);
        jsMethods = new JsMethods(driver);
        actionMethods = new ActionMethods(driver);
        methodsUtil = new MethodsUtil();
    }

    private void setWaitElementTimeout(){

        waitElementTimeout = Driver.isTestinium ? Long.parseLong(Driver.ConfigurationProp
                .getString("testiniumWaitElementTimeout")) : Long.parseLong(Driver.ConfigurationProp
                .getString("localWaitElementTimeout"));
    }

    private void setPollingEveryValue(){

        pollingEveryValue = Driver.isTestinium ? Long.parseLong(Driver.ConfigurationProp
                .getString("testiniumPollingEveryMilliSecond")) : Long.parseLong(Driver.ConfigurationProp
                .getString("localPollingEveryMilliSecond"));
    }

    public FluentWait<WebDriver> setFluentWait(long timeout){

        FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(pollingEveryValue))
                .ignoring(NoSuchElementException.class);
        return fluentWait;
    }

    public ElementInfo getElementInfo(String key){

        return StoreHelper.INSTANCE.findElementInfoByKey(key);
    }

    public boolean containsKeyInElementInfoMap(String key){

        return StoreHelper.INSTANCE.containsKey(key);
    }

    public void duplicateKeyControlInElementInfoMap(boolean printAllData){

        StoreHelper.INSTANCE.duplicateKeyControl(printAllData);
    }

    public void createElementInfo(String key, String value, String type){

        ElementInfo elementInfo = new ElementInfo();
        elementInfo.setKey(key);
        elementInfo.setValue(value);
        elementInfo.setType(type);
        StoreHelper.INSTANCE.addElementInfoByKey(key,elementInfo);
    }

    public WebDriver getDriver(){

        return driver;
    }

    public By getBy(String key){

        By by = ElementHelper.getElementInfoToBy(getElementInfo(key));
        logger.info(key + " elementi " + by.toString() + " by değerine sahip");
        return by;
    }

    public List<String> getByValueAndSelectorType(By by){

        List<String> list = new ArrayList<String>();
        String stringBy = by.toString();
        Matcher matcher = Pattern.compile("[By\\.A-Za-z]+: ").matcher(stringBy);
        matcher.find();
        String t = matcher.group();
        String type = t.replace(": ","").trim();
        list.add(stringBy.replace(t,"").trim());
        list.add(getSelectorTypeName(type.replace("By.","").trim()));
        return list;
    }

    public Object jsExecuteScript(String script, boolean isScriptAsync, Object... args){

        return isScriptAsync ? jsMethods.jsExecuteAsyncScript(script, args) : jsMethods.jsExecuteScript(script, args);
    }

    public JsMethods getJsMethods(){

        return jsMethods;
    }

    public ActionMethods getActionMethods(){

        return actionMethods;
    }

    public Boolean isElementEnabled(By by){

        return isElementEnabled(by,10);
    }

    public Boolean isElementEnabled(By by, int loopCount){

        for (int i = 0; i < loopCount; i++) {
            try {
                if (driver.findElement(by).isDisplayed() && driver.findElement(by).isEnabled()) {
                    return true;
                }
            }catch (Exception e){
            }
            waitByMilliSeconds(400);
        }
        return false;
    }

    public void clickElementForStaleElement(By by){

        try {
            clickElement(by);
        } catch (StaleElementReferenceException e) {
            waitByMilliSeconds(400);
            waitUntilWithoutStaleElement(by);
            clickElement(by);
        }
    }

    public void waitUntilWithoutStaleElement(By by){

        waitUntilWithoutStaleElement(by,30);
    }

    public void waitUntilWithoutStaleElement(By by, long timeout){

        setFluentWait(timeout).until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(findElement(by))));
    }

    public WebElement findElement(By by){

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement findElementWithoutWait(By by){

        return driver.findElement(by);
    }

    public List<WebElement> findElements(By by){

        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public List<WebElement> findElementsWithOutError(By by){

        List<WebElement> list = new ArrayList<>();
        try {
            list.addAll(wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by)));
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<WebElement> findElementsWithoutWait(By by){

        return driver.findElements(by);
    }

    public void clickElement(By by){

        findElement(by).click();
        logger.info("Elemente tıklandı.");
    }

    public void clearElement(By by){

        findElement(by).clear();
        logger.info("Elementin text alanı temizlendi.");
    }

    public void clearElementWithBackSpace(By by, String value){

        clearElement(by);
        waitByMilliSeconds(100);
        sendKeys(by,value);
        waitByMilliSeconds(100);
        sendKeysWithKeys(by,"BACK_SPACE");
        waitByMilliSeconds(100);
    }

    public Dimension getSize(By by){

        return findElement(by).getSize();
    }

    public Point getLocation(By by){

        return findElement(by).getLocation();
    }

    public Rectangle getRect(By by){

        return findElement(by).getRect();
    }

    public void sendKeys(By by, String text){

        findElement(by).sendKeys(text);
        logger.info("Elemente " + text + " texti yazıldı.");
    }

    public void sendKeysWithKeys(By by, String text){

        findElement(by).sendKeys(Keys.valueOf(text));
    }

    public void sendKeysWithNumpad(By by, String text){

        WebElement webElement = findElement(by);
        char[] textArray = text.toCharArray();
        for(int i = 0; i < textArray.length; i++){

            webElement.sendKeys(Keys.valueOf("NUMPAD" + String.valueOf(textArray[i])));
        }
        logger.info("Elemente " + text + " texti yazıldı.");
    }

    public void sendKeysJs(By by, String text, String type){

        jsMethods.sendKeys(findElementForJs(by,type), text);
        logger.info("Elemente " + text + " texti yazıldı.");
    }

    public String getText(By by){

        return findElement(by).getText();
    }

    public String getTextContentJs(By by, String type){

        return jsMethods.getText(findElementForJs(by,type),"textContent");
    }

    public String getInnerTextJs(By by, String type){

        return jsMethods.getText(findElementForJs(by,type),"innerText");
    }

    public String getOuterTextJs(By by, String type){

        return jsMethods.getText(findElementForJs(by,type),"outerText");
    }

    public void mouseOverJs(By by, String type){

        jsMethods.mouseOver(findElementForJs(by,type));
        logger.info("mouseover " + by);
    }

    public void mouseOutJs(By by, String type){

        jsMethods.mouseOut(findElementForJs(by,type));
        logger.info("mouseout " + by);
    }

    public String getAttribute(By by, String attribute){

        return findElement(by).getAttribute(attribute);
    }

    public String getAttributeJs(By by, String attribute, String type){

        return jsMethods.getAttribute(findElementForJs(by,type), attribute);
    }

    public String getValueJs(By by, String type){

        return jsMethods.getValue(findElementForJs(by,type));
    }

    public String getCssValue(By by, String attribute){

        return findElement(by).getCssValue(attribute);
    }

    public String getHexCssValue(By by, String attribute){

        return Color.fromString(getCssValue(by, attribute)).asHex();
    }

    public String getCssValueJs(By by, String attribute, String type){

        return jsMethods.getCssValue(findElementForJs(by,type), attribute);
    }

    public String getHexCssValueJs(By by, String attribute, String type){

        return Color.fromString(getCssValueJs(by, attribute, type)).asHex();
    }

    @SuppressWarnings("unchecked")
    public void takeScreenshot(){

        TakesScreenshot scrShot = ((TakesScreenshot)driver);
        File srcFile =  scrShot.getScreenshotAs(OutputType.FILE);
        String path = Driver.slash + "Screenshots"
                + Driver.slash + "screenshot-" + methodsUtil.getTime("dd_MM_yyyy-HH_mm_ss_SSS") + ".jpg";
        String fileLocation = Driver.userDir + path;
        File destFile = new File(fileLocation);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            logger.error(methodsUtil.getStackTraceLog(e));
        }
        if(!containsKeyInTestMap("screenshots")){
            List<String> fileLocationList = new ArrayList<String>();
            fileLocationList.add(fileLocation);
            putValueInTestMap("screenshots", fileLocationList);
        }else {
            if(!(getValueInTestMap("screenshots") instanceof List)) {
                List<String> fileLocationList = new ArrayList<String>();
                putValueInTestMap("screenshots", fileLocationList);
            }
            ((List<String>) getValueInTestMap("screenshots")).add(path);
        }
    }

    @SuppressWarnings("unchecked")
    public void takeScreenshotForElement(By by, String elementSsLocationMapKey){

        File srcFile =  findElement(by).getScreenshotAs(OutputType.FILE);
        String path = Driver.slash + "Screenshots"
                + Driver.slash + "screenshotElement-" + methodsUtil.getTime("dd_MM_yyyy-HH_mm_ss_SSS") + ".jpg";
        String fileLocation = Driver.userDir + path;
        File destFile = new File(fileLocation);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            logger.error(methodsUtil.getStackTraceLog(e));
        }
        putValueInTestMap(elementSsLocationMapKey, path);
        if(!containsKeyInTestMap("screenshots")){
            List<String> fileLocationList = new ArrayList<String>();
            fileLocationList.add(fileLocation);
            putValueInTestMap("screenshots", fileLocationList);
        }else {
            if(!(getValueInTestMap("screenshots") instanceof List)) {
                List<String> fileLocationList = new ArrayList<String>();
                putValueInTestMap("screenshots", fileLocationList);
            }
            ((List<String>) getValueInTestMap("screenshots")).add(path);
        }
    }

    public ImageColor getColor(By by, int widthPercent, int heightPercent) {

        File srcFile =  findElement(by).getScreenshotAs(OutputType.FILE);
        BufferedImage image = null;
        try {
            image = ImageIO.read(srcFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int x = (image.getWidth() * widthPercent)/100;
        int y = (image.getHeight() * heightPercent)/100;
        System.out.println("Element Screenshot coordinate x: " + x + " y: " + y);

        int clr = image.getRGB(x,y);
        int red   = (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue  =  clr & 0x000000ff;
        ImageColor imageColor = new ImageColor(red, green, blue);
        System.out.println("Red Color value = " + red);
        System.out.println("Green Color value = " + green);
        System.out.println("Blue Color value = " + blue);
        return imageColor;
    }

    /**
     Driver TestMap
     */

    public Boolean containsKeyInTestMap(String key){

        return Driver.TestMap.containsKey(key);
    }

    public Object getValueInTestMap(String key){

        return Driver.TestMap.get(key);
    }

    public void putValueInTestMap(String key, Object object){

        Driver.TestMap.put(key, object);
    }

    public String getPageSource(){

        return driver.getPageSource();
    }

    public String getCurrentUrl(){

        return driver.getCurrentUrl();
    }

    public void openNewTabJs(String url){

        jsMethods.openNewTab(url);
        logger.info("Yeni tab açılıyor..." + " Url: " + url);
    }

    public void acceptAlert(){

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public List<String> listTabs(){
        List<String> list = new ArrayList<String>();
        for (String window: driver.getWindowHandles()){
            list.add(window);
        }
        return list;
    }

    public void close(){

        driver.close();
    }

    public void switchTab(int tabNumber){

        driver.switchTo().window(listTabs().get(tabNumber));
    }

    public void switchFrame(int frameNumber){

        driver.switchTo().frame(frameNumber);
    }

    public void switchFrame(String frameName){

        driver.switchTo().frame(frameName);
    }

    public void switchFrameWithKey(By by){

        WebElement webElement = findElement(by);
        driver.switchTo().frame(webElement);
    }

    public void switchParentFrame(){

        driver.switchTo().parentFrame();
    }

    public void switchDefaultContent(){

        driver.switchTo().defaultContent();
    }

    public void navigateTo(String url){

        driver.navigate().to(url);
    }

    public void navigateToBack(){

        driver.navigate().back();
    }

    public void navigateToForward(){

        driver.navigate().forward();
    }

    public void navigateToRefresh(){

        driver.navigate().refresh();
    }

    public Set<Cookie> getCookies(){

        return driver.manage().getCookies();
    }

    public void deleteAllCookies(){

        driver.manage().deleteAllCookies();
    }

    public Select getSelect(By by){

        return new Select(findElement(by));
    }

    public WebElement getSelectedElement(By by){

        Select select = new Select(findElement(by));
        return select.getFirstSelectedOption();
    }

    public void selectByValue(By by, String value){

        getSelect(by).selectByValue(value);
    }

    public void selectByVisibleText(By by, String text){

        getSelect(by).selectByVisibleText(text);
    }

    public void selectByIndex(By by, int index){

        getSelect(by).selectByIndex(index);
    }
    public void selectItemByIndex(By by, int index){
        WebElement element = findElement(by);
        actionMethods.selectItemByIndex(element, index);
    }

    public List<WebElement> getSelectOptions(By by){

        return getSelect(by).getOptions();
    }

    public WebElement getFirstSelectedOption(By by){

        return getSelect(by).getFirstSelectedOption();
    }

    public List<WebElement> getAllSelectedOptions(By by){

        return getSelect(by).getAllSelectedOptions();
    }

    public void selectByIndexJs(By by, int index, String type){

        jsMethods.selectWithIndex(findElementForJs(by,type), index);
    }

    public void selectByTextJs(By by, String text, String type){

        jsMethods.selectWithText(findElementForJs(by,type), text);
    }

    public void selectByValueJs(By by, String value, String type){

        jsMethods.selectWithValue(findElementForJs(by,type), value);
    }

    public int getSelectedOptionIndexJs(By by, String type){

        return jsMethods.getSelectedOptionIndex(findElementForJs(by,type));
    }

    public String getSelectedOptionTextJs(By by, String type){

        return jsMethods.getSelectedOptionText(findElementForJs(by,type));
    }

    public String getSelectedOptionValueJs(By by, String type){

        return jsMethods.getSelectedOptionValue(findElementForJs(by,type));
    }

    public void scrollElementJs(By by, String type){

        jsMethods.scrollElement(findElementForJs(by,type));
    }

    public void scrollIntoViewIfNeededJs(By by, String type){

        jsMethods.scrollIntoViewIfNeeded(findElementForJs(by,type));
    }

    public void scrollElementCenterJs(By by,String type){

        jsMethods.scrollElementCenter(findElementForJs(by,type));
    }

    public void focusElementJs(By by){

        WebElement webElement = findElementForJs(by,"1");
        jsMethods.scrollElement(webElement);
        jsMethods.focusElement(webElement);
    }

    public void jsExecutorWithBy(String script, By by){

        jsMethods.jsExecuteScript(script, findElementForJs(by,"3"));
    }

    public void waitByMilliSeconds(long milliSeconds){

        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitBySeconds(long seconds){

        waitByMilliSeconds(seconds*1000);
    }

    public boolean isElementClickable(By by, long timeout){

        try {
            setFluentWait(timeout).until(ExpectedConditions.elementToBeClickable(by));
            logger.info("true");
            return true;
        }
        catch (Exception e) {
            logger.info("false" + " " + e.getMessage());
            return false;
        }
    }

    public void checkElementClickable(By by, long timeout){

        assertTrue(isElementClickable(by, timeout),by.toString() + " elementi tıklanabilir değil.");
    }

    public void checkElementClickable(By by){
        checkElementClickable(by,30);
    }

    public boolean isElementVisible(By by, long timeout){

        try {
            setFluentWait(timeout).until(ExpectedConditions.visibilityOfElementLocated(by));
            logger.info("true");
            return true;
        } catch (Exception e) {
            logger.info("false" + " " + e.getMessage());
            return false;
        }
    }

    public void checkElementVisible(By by, long timeout) {

        assertTrue(isElementVisible(by, timeout), by.toString() + " elementi görüntülenemedi.");
    }

    public void checkElementVisible(By by){
        checkElementVisible(by,30);
    }

    public boolean isElementInVisible(By by, long timeout){

        try {
            setFluentWait(timeout).until(ExpectedConditions.invisibilityOfElementLocated(by));
            logger.info("true");
            return true;
        } catch (Exception e) {
            logger.info("false" + " " + e.getMessage());
            return false;
        }
    }

    public void checkElementInVisible(By by, long timeout) {

        assertTrue(isElementInVisible(by, timeout),by.toString() + " elementi görünür");
    }

    public void checkElementInVisible(By by) {

        checkElementInVisible(by,30);
    }

    public boolean isElementLocated(By by, long timeout){

        try {
            setFluentWait(timeout).until(ExpectedConditions.presenceOfElementLocated(by));
            logger.info("true");
            return true;
        } catch (Exception e) {
            logger.info("false" + " " + e.getMessage());
            return false;
        }
    }

    public void checkElementLocated(By by, long timeout) {

        assertTrue(isElementVisible(by, timeout),by.toString() + " element located error.");
    }

    public void checkElementLocated(By by){
        checkElementLocated(by,30);
    }

    public void hoverElementAction(By by, boolean isScrollElement) {

        WebElement webElement = findElementForJs(by,"1");
        if(isScrollElement){
            jsMethods.scrollElement(webElement);
        }
        actionMethods.hoverElement(webElement);
    }

    public void moveAndClickElement(By by, boolean isScrollElement) {

        WebElement webElement = findElementForJs(by,"1");
        if(isScrollElement){
            jsMethods.scrollElement(webElement);
        }
        actionMethods.moveAndClickElement(webElement);
    }

    public void clickElementWithAction(By by, boolean isScrollElement){

        WebElement webElement = findElementForJs(by,"1");
        if(isScrollElement){
            jsMethods.scrollElement(webElement);
        }
        actionMethods.clickElement(webElement);
    }

    public void doubleClickElementWithAction(By by, boolean isScrollElement){

        WebElement webElement = findElementForJs(by,"1");
        if(isScrollElement){
            jsMethods.scrollElement(webElement);
        }
        actionMethods.doubleClickElement(webElement);
    }

    public void selectAction(By by, int optionIndex, boolean isScrollElement){

        WebElement webElement = findElementForJs(by,"1");
        if(isScrollElement){
            jsMethods.scrollElement(webElement);
        }
        actionMethods.select(webElement, optionIndex);
    }

    // 1 loop 400 ms
    public boolean isImageLoadingJs(By by, int loopCount){

        boolean isImageLoading = false;
        try {
            isImageLoading = jsMethods.jsImageLoading(findElementForJs(by,"1"), loopCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return isImageLoading;
    }

    public void waitPageLoadCompleteJs() {

        jsMethods.waitPageLoadComplete(setFluentWait(10));
    }

    public void waitForAngularLoadJs() {

        jsMethods.waitForAngularLoad(setFluentWait(10));
    }

    public void waitJQueryCompleteJs() {

        jsMethods.waitJQueryComplete(setFluentWait(10));
    }

    public void waitPageScrollingCompleteJs() {

        jsMethods.waitPageScrollingComplete(setFluentWait(10));
    }

    public void stopPageLoadJs() {

        jsMethods.stopPageLoad();
    }

    public boolean doesUrl(String url, int count, String condition){

        int againCount = 0;
        boolean isUrl = false;
        String takenUrl = "";
        logger.info("Beklenen url: " + url);
        while (!isUrl) {
            waitByMilliSeconds(250);
            if (againCount == count) {
                System.err.println("Expected url " + url + " doesn't equal current url " + takenUrl);
                logger.info("Alınan url: " + takenUrl);
                return false;
            }
            takenUrl = driver.getCurrentUrl();
            if (takenUrl != null) {
                isUrl = conditionValueControl(url,takenUrl,condition);
            }
            againCount++;
        }
        logger.info("Alınan url: " + takenUrl);
        return true;
    }

    public void clickElementJs(By by){

        jsMethods.clickByElement(findElementForJs(by,"3"));
    }

    public void clickElementJs(By by, boolean notClickByCoordinate){

        jsMethods.clickByElement(findElementForJs(by,"3"), notClickByCoordinate);
    }

    public void clickByCoordinateJs(int x, int y){

        jsMethods.clickByCoordinate(x, y);
    }

    public void clickByWebElementCoordinate(By by){

        jsMethods.clickByWebElementCoordinate(findElementForJs(by,"3"));
    }

    public void clickByWebElementCoordinate(By by, int x, int y){

        jsMethods.clickByWebElementCoordinate(findElementForJs(by,"3"), x, y);
    }

    public boolean doesAttributeValue(By by, String attribute, String value, String condition, int count){

        int againCount = 0;
        boolean attributeCondition = false;
        String actualAttributeValue = "";
        logger.info("Beklenen değer: " + attribute + " " + condition + " " + value);
        while (!attributeCondition) {
            waitByMilliSeconds(400);
            if (againCount == count) {
                logger.info("Alınan değer: " + actualAttributeValue);
                return false;
            }
            actualAttributeValue = findElement(by).getAttribute(attribute).trim();
            if (actualAttributeValue != null) {
                attributeCondition = conditionValueControl(value, actualAttributeValue, condition);
            }
            againCount++;
        }
        logger.info("Alınan değer: " + actualAttributeValue);
        return true;
    }

    public void checkElementExistWithUrl(By by, int elementControlCount, int repeatCount, String url, String errorMessage) {

        boolean isElementVisible = false;
        int countAgain = 0;
        int elementCount;
        while (!isElementVisible) {
            waitByMilliSeconds(400);
            if (countAgain == repeatCount*elementControlCount) {
                fail(errorMessage);
                break;
            }
            elementCount = driver.findElements(by)
                    .size();
            if (elementCount != 0) {
                isElementVisible = true;
            }
            if(countAgain % elementControlCount == 0) {
                driver.navigate().to(url);
                doesUrl(url,15,"contain");
            }
            countAgain++;
        }
    }

    public void testSuccessControl(By by, int elementControlCount, int repeatCount, String url, String errorMessage) {

        boolean success = false;
        int countAgain = 0;
        int elementCount;
        String text;
        while (!success) {
            waitByMilliSeconds(400);
            if (countAgain == repeatCount*elementControlCount) {
                fail(errorMessage);
                break;
            }
            elementCount = driver.findElements(by)
                    .size();
            if (elementCount != 0) {
                text = driver.findElement(by).getText();
                if(text.contains("SUC")){
                    success = true;
                }else{
                    elementCount = 0;
                }
            }
            if(countAgain % elementControlCount == 0) {
                driver.navigate().to(url);
                doesUrl(url,15,"contain");
            }
            countAgain++;
        }
    }

    public void checkElementExist(By by, int loopCount){

        assertTrue(doesElementExist(by,loopCount),"Element bulunamadı");
    }

    public boolean doesElementExist(By by, int loopCount){

        logger.info("Element " + by.toString() + " by değerine sahip");
        int countAgain = 0;
        int elementCount;
        while (true) {
            if (countAgain == loopCount) {
                return false;
            }
            elementCount = driver.findElements(by).size();
            if (elementCount != 0) {
                return true;
            }
            waitByMilliSeconds(250);
            countAgain++;
        }
    }

    public boolean doesElementNotExist(By by, int loopCount) {

        boolean isElementInvisible = false;
        int countAgain = 0;
        int elementCount;
        while (!isElementInvisible) {
            if (countAgain == loopCount) {
                return false;
            }
            elementCount = driver.findElements(by).size();
            if (elementCount == 0) {
                isElementInvisible = true;
            }
            waitByMilliSeconds(250);
            countAgain++;
        }
        return true;
    }

    public By getByWithKeySetValue(String key, String value){

        ElementInfo elementInfo = getElementInfo(key);
        String getValue = elementInfo.getValue();
        String type = elementInfo.getType();
        logger.info(value);
        String[] arrayValue = Splitter.on("!!").splitToList(value).toArray(new String[0]);
        String newValue = String.format(getValue, arrayValue);
        return ElementHelper.getElementInfoToBy(newValue,type);
    }

    public void keyValueChangerMethodWithNewElement(String key, String newKey, String value, String splitValue){

        ElementInfo elementInfo = getElementInfo(key);
        String getValue = elementInfo.getValue();
        String type = elementInfo.getType();
        logger.info(value);
        String[] arrayValue = Splitter.on(splitValue).splitToList(value).toArray(new String[0]);
        String newValue = String.format(getValue, arrayValue);
        logger.info(newValue);
        createElementInfo(newKey,newValue,type);
    }

    public String getKeyValueChangerStringBuilder(String value, String splitValue, String mapKeySuffix){

        if(value.contains(mapKeySuffix)) {
            String[] values = Splitter.on(splitValue).splitToList(value).toArray(new String[0]);
            StringBuilder stringBuilder = new StringBuilder();
            int valuesLength = values.length;
            for (int i = 0; i < valuesLength; i++) {
                String text = values[i];
                if (text.endsWith(mapKeySuffix)) {
                    text = Driver.TestMap.get(text).toString();
                }
                stringBuilder.append(text);
                if (i != valuesLength - 1) {
                    stringBuilder.append(splitValue);
                }
            }
            value = stringBuilder.toString();
        }
        return value;
    }

    public WebElement findElementForJs(By by, String type){

        WebElement webElement = null;
        switch (type){
            case "1":
                webElement = findElement(by);
                break;
            case "2":
                webElement = findElementWithoutWait(by);
                break;
            case "3":
                List<String> byValueList = getByValueAndSelectorType(by);
                webElement = jsMethods.findElement(byValueList.get(0),byValueList.get(1));
                break;
            default:
                fail("type hatalı");
        }
        return webElement;
    }

    public List<WebElement> findElementsForJs(By by, String type){

        List<WebElement> webElementList = null;
        switch (type){
            case "1":
                webElementList = findElements(by);
                break;
            case "2":
                webElementList = findElementsWithoutWait(by);
                break;
            case "3":
                List<String> byValueList = getByValueAndSelectorType(by);
                webElementList = jsMethods.findElements(byValueList.get(0),byValueList.get(1));
                break;
            default:
                fail("type hatalı");
        }
        return webElementList;
    }

    private String getSelectorTypeName(String type){

        String selectorType = "";
        switch (type) {

            case "id":
                selectorType = "id";
                break;

            case "name":
                selectorType = "name";
                break;

            case "className":
                selectorType = "class";
                break;

            case "cssSelector":
                selectorType = "css";
                break;

            case "xpath":
                selectorType = "xpath";
                break;

            default:
                fail("HATA");
                break;
        }
        return selectorType;
    }

    private boolean conditionValueControl(String expectedValue, String actualValue,String condition){

        boolean result = false;
        switch (condition){
            case "equal":
                result = actualValue.equals(expectedValue);
                break;
            case "contain":
                result = actualValue.contains(expectedValue);
                break;
            case "startWith":
                result = actualValue.startsWith(expectedValue);
                break;
            case "endWith":
                result = actualValue.endsWith(expectedValue);
                break;
            case "notEqual":
                result = !actualValue.equals(expectedValue);
                break;
            case "notContain":
                result = !actualValue.contains(expectedValue);
                break;
            case "notStartWith":
                result = !actualValue.startsWith(expectedValue);
                break;
            case "notEndWith":
                result = !actualValue.endsWith(expectedValue);
                break;
            default:
                fail("hatali durum: " + condition);
        }
        return result;
    }

    public void delay(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {

            logger.info("Delay de hata : " + e.getMessage(), "RED");
            Thread.currentThread().interrupt();
        }
    }

    public void uploadFilePath(  String dosya) {
        String path = null;
        WebElement chooseFile = driver.findElement(By.cssSelector("input[type=\"file\"]"));
        delay(2);
        switch (dosya) {
            case "apk":
                path = System.getProperty("user.dir") + "\\src\\test\\resources\\apkFiles\\FLO_v4.1.35_apkpure.com.apk";
                break;
            case "ipa":
                path = System.getProperty("user.dir") + "\\src\\test\\resources\\ipaFiles\\bitbar-ios-sample.ipa";
                break;
        }
        Assert.assertNotNull(path);
        chooseFile.sendKeys(path);
        delay(30);
        if (path.contains("apk")) {
            Assert.assertTrue(driver.findElement(By.xpath("//div[@class=\"panel file\"]/a[@id=\"ios-file\"]/div/p[text()='com.flo.ayakkabi']")).isDisplayed());
        } else if (path.contains("ipa")) {
            Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Uploading')]")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.cssSelector("div[class='process-bar-with-percentage']")).isDisplayed());
        } else {

        }
        logger.info("Yükleme başarıyla gerçekleştirildi...");

    }

    public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)", "");
        logger.info("Sayfa aşağı kaydırıldı!!");

    }

    public void nzselectIleTiklaJs(String text){
        jsMethods.nzselectIleTıkla(text);
    }


}
