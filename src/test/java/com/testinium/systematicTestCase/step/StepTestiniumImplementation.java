package com.testinium.systematicTestCase.step;

import com.testinium.systematicTestCase.driver.Driver;
import com.testinium.systematicTestCase.methods.Methods;
import com.testinium.systematicTestCase.methods.MethodsUtil;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import org.apache.log4j.Level;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;
import java.util.List;
import java.util.Locale;

import static org.apache.log4j.Logger.getLogger;

public class StepTestiniumImplementation {

    private static final Logger logger = LoggerFactory.getLogger(StepTestiniumImplementation.class);
    Methods methods;
    MethodsUtil methodsUtil;

    public StepTestiniumImplementation() {

        methods = new Methods();
        methodsUtil = new MethodsUtil();
        getLogger(StepTestiniumImplementation.class).setLevel(Level.ALL);
    }

    @Step("Gecerli login yap <table>")
    public void loginWithTable(Table table) {

        List<String> listColumnNames =  table.getColumnNames();
        List<String> listEmail = table.getColumnValues(listColumnNames.get(0));
        List<String> listPassword = table.getColumnValues(listColumnNames.get(1));
        for(int i = 0; i < listEmail.size(); i++){
            methods.checkElementVisible(methods.getBy("userNameInLoginPage"),30);
            methods.checkElementVisible(methods.getBy("passwordInLoginPage"),30);
            methods.checkElementVisible(methods.getBy("signInInLoginPage"),30);
            methods.sendKeys(methods.getBy("userNameInLoginPage"),listEmail.get(i));
            methods.sendKeys(methods.getBy("passwordInLoginPage"),listPassword.get(i));
            methods.checkElementClickable(methods.getBy("signInInLoginPage"),30);
            methods.clickElement(methods.getBy("signInInLoginPage"));
            methods.doesUrl("https://clouddev.testinium.io/home?code=",80,"contain");
            methods.checkElementVisible(methods.getBy("logoDirName"),30);
            Assertions.assertEquals("Dashboard", methods.getText(methods.getBy("logoDirName")));
            methods.checkElementVisible(methods.getBy("btnUserDropdown"),30);
            methods.focusElementJs(methods.getBy("btnUserDropdown"));
            methods.clickElement(methods.getBy("btnUserDropdown"));
            methods.isElementVisible(methods.getBy("btnLogOut"),15);
            methods.focusElementJs(methods.getBy("btnLogOut"));
            methods.clickElement(methods.getBy("btnLogOut"));
            methods.doesUrl("https://accountdev.testinium.com/uaa/login",80,"equal");
        }
    }

    @Step("<key> element listesinin <index> sıradaki elemanına tıkla")
    public void clickElementByIndex(String key, String index) {
        index = index.endsWith("KeyValue") ? Driver.TestMap.get(index).toString() : index;
        int ind = Integer.parseInt(index);
        methods.findElements(methods.getBy(key)).get(ind - 1).click();
        logger.info("Element listesinin " + ind + ". elemanına tıklandı.");
    }

    @Step("<key> element listesinin text'inde <text> kelimesi var mı kontrol et")
    public void controlElemenListText(String key, String text){
        List<WebElement> elements =methods.findElements(methods.getBy(key));
        for (WebElement element: elements) {
            String elementText= element.getText();
            logger.info("deger " +elementText);
            Assert.assertTrue("Listede" + text + "kelimesi yok",elementText.toLowerCase(Locale.ROOT).contains(text));
            logger.info("Listede " + text + " kelimesi var");
        }
    }

    @Step("<key> elementi zorunlu mu kontrol et")
    public void checkElementRequired(String key) {
        String attributeInfo = methods.getAttribute(methods.getBy(key), "required");
        Assert.assertEquals("Alan zorunlu degil", "true", attributeInfo);
    }

    @Step("Accept Alert")
    public void acceptAlert(){

        methods.acceptAlert();
    }

    @Step("Verify Plan Running")
    public void verifyPlanRunning() {
        methods.checkElementVisible(methods.getBy("lastExecutionInDailyRun"), 1000);
        String urlPlan = methods.getCurrentUrl().trim();
        methods.checkElementVisible(methods.getBy("lastExecutionResultInDailyRun"), 1000);
        methods.waitBySeconds(1);
        //methodsPage.checkElementExistWithUrl(methodsPage.getBy("testRunningInNewSuite"),10,20, urlPlan,"Test başlatılamadı.");
        methods.checkElementExistWithUrl(methods.getBy("testRunningResultInDailyRun"), 500, 500, urlPlan, "Test beklenen sürede tamamlanamadı.");
    }

    @Step("Verify Plan Running Enterprise")
    public void verifyPlanRunningEnterprise() {
        String urlPlan = methods.getCurrentUrl().trim();
        methods.waitBySeconds(1);
        methods.checkElementExistWithUrl(methods.getBy("testResult_PlanList_DailyRun"), 20, 15, urlPlan, "Test beklenen sürede tamamlanamadı.");
    }

    @Step("Planın çalıştığını ve success aldığını doğrula Enterprise")
    public void verifyPlanRunningAndSuccessEnterprise() {
        String urlPlan = methods.getCurrentUrl().trim();
        methods.waitBySeconds(1);
        methods.testSuccessControl(methods.getBy("testResult_PlanList_DailyRun"),20,15,urlPlan,"Test beklenen sürede success almadı.");
    }

    @Step({"<key> elementinin element sayısını <mapKey> değerinde tut"})
    public void findElementsSizeForMap(String key, String mapKey){

        int elementsSize = methods.findElementsWithOutError(methods.getBy(key)).size();
        logger.info("Element sayısı: " + elementsSize);
        Driver.TestMap.put(mapKey, elementsSize);
    }

    @Step({"<mapKey> ve <map2Key> değerleri birbirine <condition> durumunu sağlıyor mu"})
    public void controlValueWithMap(String mapKey, String map2Key, String condition){

        Assert.assertTrue("", methodsUtil.valueControl(Driver.TestMap.get(mapKey).toString()
                , Driver.TestMap.get(map2Key).toString(),condition));
    }

    @Step("<key> select elementinin text değerini <mapKey> değerinde tut")
    public void getSelectedElementSaveText(String key, String mapKey){

        String value = methods.getSelectedElement(methods.getBy(key)).getText().trim();
        logger.info(value);
        Driver.TestMap.put(mapKey,value);
    }

    @Step("<text> değerine rastgele <line> karakter ekle ve <mapKey> keyinde tut")
    public void setRandomStringWithMap(String text, int line, String mapKey){

        text = text + methodsUtil.randomString(line);
        Driver.TestMap.put(mapKey,text);
    }

    //Node-2-node=9: Login to Dashboard: Valid login using regular login
//------------------------------------------------------------------
//Tags:ValidLoginNode2node9

    //   |        emailValue      | passwordValue |
    // |------------------------|---------------|
    // |  citrosusti@desoz.com  |    12345678   |
    // |testiniumtest1@mynet.com|   sifre12345  |

//* <emailValue> ve <passwordValue> bilgileriyle gecerli login yap

}
