package com.testinium.systematicTestCase.step;

import com.testinium.systematicTestCase.driver.Driver;
import com.testinium.systematicTestCase.methods.MethodsUtil;
import com.thoughtworks.gauge.Step;
import org.apache.log4j.Level;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ResourceBundle;

import static org.apache.log4j.Logger.getLogger;
import static org.junit.jupiter.api.Assertions.*;

public class StepUtilsImplementation {

    private static final Logger logger = LoggerFactory.getLogger(StepUtilsImplementation.class);
    MethodsUtil methodsUtil;

    public StepUtilsImplementation() {

        methodsUtil = new MethodsUtil();
        getLogger(StepUtilsImplementation.class).setLevel(Level.ALL);
    }

    @Step("Get time millis and save <mapKey>")
    public void getMillisAndSave(String mapKey) {

        long currentTime = methodsUtil.currentTimeMillis();
        logger.info(String.valueOf(currentTime));
        Driver.TestMap.put(mapKey, currentTime);

    }

    @Step("Zamanı milisaniye olarak al ve <mapKey> de tut")
    public void saveCurrentTimeMillis(String mapKey){
        logger.info(String.valueOf(methodsUtil.currentTimeMillis()));
        Driver.TestMap.put(mapKey, methodsUtil.currentTimeMillis());
    }

    @Step("Zamanı DateTime olarak al ve <mapKey> de tut")
    public void saveCurrentDateTime(String mapKey){
        logger.info(methodsUtil.getDateTimeFormat());
        Driver.TestMap.put(mapKey, methodsUtil.getDateTimeFormat());
    }

    @Step("<timeMillis> milisaniye tipindeki zamanı <format> formatındaki zamana çevir ve <mapKey> de tut")
    public void saveTimeFromCurrentTimeMillis(String timeMillis, String format, String mapKey){

        if(timeMillis.endsWith("KeyValue")){
            timeMillis = Driver.TestMap.get(timeMillis).toString();
        }
        //String time = methodsUtil.getTimeFromMillisWithZoneId(format, Long.parseLong(timeMillis),"Europe/Istanbul");
        String time = methodsUtil.getTimeFromMillis(format, Long.parseLong(timeMillis));
        logger.info(timeMillis + " " + time);
        Driver.TestMap.put(mapKey, time);
    }

    @Step("<timeMillis> milisaniye tipindeki zamanı <format> formatını <year> year, <month> month, <day> day <hour>:<minute>:<second> degerlerini kullanarak <forOffsetHours> zamanı donustur ve <mapKey> de tut")
    public void saveTimeFromCurrentTimeMillis(String timeMillis, String format, int year, int month
            , int day, int hour, int minute, int second, String forOffsetHours, String mapKey){

        if(timeMillis.endsWith("KeyValue")){
            timeMillis = Driver.TestMap.get(timeMillis).toString();
        }
        //String time = methodsUtil.getTimeFromMillisWithZoneId(format, Long.parseLong(timeMillis),"Europe/Istanbul");
        String time = methodsUtil.getTimeFromMillisPlus(format, Long.parseLong(timeMillis)
                , year, month, day, hour, minute, second,0, forOffsetHours,"null");
        logger.info(timeMillis + " " + time);
        Driver.TestMap.put(mapKey, time);
    }

    @Step("<timeMillis> milisaniye tipindeki zamanı <language> language <format> formatını <year> year, <month> month, <day> day <hour>:<minute>:<second> degerlerini kullanarak <forOffsetHours> zamanı donustur ve <mapKey> de tut")
    public void saveTimeFromCurrentTimeMillis(String timeMillis, String language, String format, int year, int month
            , int day, int hour, int minute, int second, String forOffsetHours, String mapKey){

        if(timeMillis.endsWith("KeyValue")){
            timeMillis = Driver.TestMap.get(timeMillis).toString();
        }
        //String time = methodsUtil.getTimeFromMillisWithZoneId(format, Long.parseLong(timeMillis),"Europe/Istanbul");
        String time = methodsUtil.getTimeFromMillisPlus(format, Long.parseLong(timeMillis)
                , year, month, day, hour, minute, second,0, forOffsetHours, language.toUpperCase());
        logger.info(timeMillis + " " + time);
        Driver.TestMap.put(mapKey, time);
    }

    @Step("<timeMillis> milisaniye tipindeki zamanı <format> formatındaki zamana <forOffsetHour> offset le çevir ve <mapKey> de tut")
    public void saveTimeFromCurrentTimeMillis(String timeMillis, String format, int forOffsetHour, String mapKey){

        if(timeMillis.endsWith("KeyValue")){
            timeMillis = Driver.TestMap.get(timeMillis).toString();
        }
        //String time = methodsUtil.getTimeFromMillisWithZoneId(format, Long.parseLong(timeMillis),"Europe/Istanbul");
        String time = methodsUtil.getTimeFromMillis(format, Long.parseLong(timeMillis), forOffsetHour);
        logger.info(timeMillis + " " + time);
        Driver.TestMap.put(mapKey, time);
    }

    @Step("<time> zamanı <format> formatından milisaniyeye çevir ve <mapKey> de tut")
    public void saveTimeMillisFromTime(String time, String format, String mapKey){

        if(time.endsWith("KeyValue")){
            time = Driver.TestMap.get(time).toString();
        }
        //Long timeMillis = methodsUtil.getTimeMillisFromTime(time.trim(), format,"Europe/Istanbul");
        Long timeMillis = methodsUtil.getTimeMillisFromTime(time.trim(), format);
        logger.info(time + " " + timeMillis);
        Driver.TestMap.put(mapKey, timeMillis);
    }

    @Step("<time> zamanı <format> formatından milisaniyeye <forOffsetHour> offset le çevir ve <mapKey> de tut")
    public void saveTimeMillisFromTime(String time, String format, int forOffsetHour, String mapKey){

        if(time.endsWith("KeyValue")){
            time = Driver.TestMap.get(time).toString();
        }
        Long timeMillis = methodsUtil.getTimeMillisFromTime(time.trim(), format, forOffsetHour);
        logger.info(time + " " + timeMillis);
        Driver.TestMap.put(mapKey, timeMillis);
    }

    @Step("<value> textinin <oldValue> degeriyle <newValue> degerini degistir ve <mapKey> değerinde tut")
    public void replaceText(String value, String oldValue, String newValue, String mapKey){

        value = value.endsWith("KeyValue") ? Driver.TestMap.get(value).toString() : value;
        value = value.replace(oldValue,newValue);
        Driver.TestMap.put(mapKey, value);
    }

    @Step("<jsonFileLocation> json dosyasini string olarak <mapKey> keyinde tut")
    public void readJsonFile(String jsonFileLocation, String mapKey){

        Driver.TestMap.put(mapKey, methodsUtil.getJsonStringWithBufferedReader(jsonFileLocation));
    }

    @Step("<jsonFileLocation> json dosyasinin degerlerini doldur ve string olarak <mapKey> keyinde tut")
    public void readAndSetJson(String jsonFileLocation, String mapKey){

        String json = methodsUtil.getJsonStringWithBufferedReader(jsonFileLocation);
        json = methodsUtil.setJsonWithMapKey(json);
        logger.info(json);
        Driver.TestMap.put(mapKey, json);
    }

    @Step("<xmlFileLocation> xml dosyasini string olarak <mapKey> keyinde tut")
    public void readXmlFile(String xmlFileLocation, String mapKey){

        Driver.TestMap.put(mapKey, methodsUtil.getStringXmlFile(xmlFileLocation));
    }

    @Step("<jsonString> json textini <values> degerleriyle <types> degistir ve <mapKey> keyinde tut")
    public void setJsonString(String jsonString, String values, String types, String mapKey){

        jsonString = jsonString.endsWith("KeyValue") ? Driver.TestMap.get(jsonString).toString() : jsonString;
        Driver.TestMap.put(mapKey, methodsUtil.setStringJson(jsonString,"!!", values, types));
    }

    @Step("<value> değeriyle <propertiesMapKey> properties dosyasında karşılık gelen değeri <mapKey> keyinde tut")
    public void setValueWithPropertiesFileAndSave(String value, String propertiesMapKey, String mapKey){

        ResourceBundle resourceBundle = (ResourceBundle) Driver.TestMap.get(propertiesMapKey);
        if(value.endsWith("KeyValue")){
            value = Driver.TestMap.get(value).toString();
        }
        logger.info(resourceBundle.getString(value));
        Driver.TestMap.put(mapKey, resourceBundle.getString(value));
    }

    @Step("<envKey> keyiyle enviroment ta tutulan bir deger varsa <mapKey> keyinde tut")
    public void saveMapTestiniumGetEnv(String envKey, String mapKey){

        String env = System.getenv(envKey);
        if(StringUtils.isNotBlank(env)){
            Driver.TestMap.put(mapKey, env);
        }
    }

    @Step("<envKey> keyiyle sysytem property ta tutulan bir deger varsa <mapKey> keyinde tut")
    public void saveMapTestiniumGetProperty(String envKey, String mapKey){

        String env = System.getProperty(envKey);
        if(StringUtils.isNotBlank(env)){
            Driver.TestMap.put(mapKey, env);
        }
    }

    @Step("<value> degerini bigdecimal olarak al <scale> scale ve <roundingMode> modunda yuvarla <mapKey> keyinde tut")
    public void bigDecimalRoundingMode(String value, String scale, String roundingMode, String mapKey){

        // FLOOR  HALF_UP   HALF_DOWN   HALF_EVEN   UP   DOWN
        value = value.endsWith("KeyValue") ? Driver.TestMap.get(value).toString() : value;
        String newValue = new BigDecimal(value).setScale(Integer.parseInt(scale), RoundingMode.valueOf(roundingMode)).toPlainString();
        Driver.TestMap.put(mapKey, newValue);
    }

    @Step("<islemTipi> islem tipiyle <value1> ve <value2> degerleriyle islemi gerçeklestir <scale> ve <roundingMode> modunda yuvarla <mapKey> keyinde tut")
    public void bigDecimalProcesses(String islemTipi, String value1, String value2, String scale, String roundingMode, String mapKey){

        // FLOOR  HALF_UP   HALF_DOWN   HALF_EVEN   UP   DOWN --> rounding mode yerine koyulacaklar (yukarı-aşağı yuvarla vs...)
        value1 = value1.endsWith("KeyValue") ? Driver.TestMap.get(value1).toString() : value1;
        value2 = value2.endsWith("KeyValue") ? Driver.TestMap.get(value2).toString() : value2;
        int newScale = Integer.parseInt(scale);
        String newValue = "";
        switch (islemTipi){

            case "topla":
                newValue = new BigDecimal(value1).add(new BigDecimal(value2)).setScale(newScale, RoundingMode.valueOf(roundingMode)).toPlainString();
                break;
            case "cikar":
                newValue = new BigDecimal(value1).add(new BigDecimal("-" + value2)).setScale(newScale, RoundingMode.valueOf(roundingMode)).toPlainString();
                break;
            case "carp":
                newValue = new BigDecimal(value1).multiply(new BigDecimal(value2)).setScale(newScale, RoundingMode.valueOf(roundingMode)).toPlainString();
                break;
            case "bol":
                newValue = new BigDecimal(value1).divide(new BigDecimal(value2), newScale, RoundingMode.valueOf(roundingMode)).setScale(newScale, RoundingMode.valueOf(roundingMode)).toPlainString();
                break;
            default:
                fail("İşlem Tipi Hatalı " + islemTipi);
        }

        System.out.println(newValue);
        Driver.TestMap.put(mapKey, newValue);
    }

    @Step("<expectedValue> expectedValue ile <actualValue> actualValue eşit mi")
    public void controlValuesEqual(String expectedValue, String actualValue) {

        expectedValue = expectedValue.endsWith("KeyValue") ? Driver.TestMap.get(expectedValue).toString() : expectedValue;
        actualValue = actualValue.endsWith("KeyValue") ? Driver.TestMap.get(actualValue).toString() : actualValue;
        logger.info("Beklenen deger: " + expectedValue);
        logger.info("Alınan deger: " + actualValue);
        assertEquals(expectedValue, actualValue,"Değerler eşit değil");
        logger.info("Değerler eşit");
    }

    @Step("<length> uzunluğunda <charType> tipinde karakteri random olarak oluştur <mapkey> inde tut")
    public void randomChar(int length, String charType, String mapKey) {

        String value = methodsUtil.randomStringExtended(length, charType,"",0);
        Driver.TestMap.put(mapKey, value);
    }

    @Step("<value> text değerlerini birleştir <mapKey> keyinde tut")
    public void stringBuilderAndSave(String value, String mapKey){

        Driver.TestMap.put(mapKey, methodsUtil
                .getSplitStringBuilder(value,"?!","KeyValue"));
        logger.info(Driver.TestMap.get(mapKey).toString());
    }

    @Step("<number> rastgele sayı üret <mapKey> keyinde tut")
    public void randomNumber(String number, String mapKey){

        number = number.endsWith("KeyValue") ? Driver.TestMap.get(number).toString() : number;
        int value = methodsUtil.randomNumber(Integer.parseInt(number));
        System.out.println(value);
        Driver.TestMap.put(mapKey, value);
    }

    @Step("<number> rastgele varyant ürün seç ve <mapKey> keyinde tut")
    public void randomChoosing(String number, String mapKey){

        number = number.endsWith("KeyValue") ? Driver.TestMap.get(number).toString() : number;
        int numberOfRandomProduct = methodsUtil.randomNumber(Integer.parseInt(number));

        int value = methodsUtil.randomNumber(Integer.parseInt(number));
        System.out.println(value);
        Driver.TestMap.put(mapKey, value);
    }

    @Step("<value> değeri <replaceValues> degerlerini temizle ve <mapKey> değerinde tut <trim>")
    public void replaceText(String value, String replaceValues, String mapKey, boolean trim){

        value = value.endsWith("KeyValue") ? Driver.TestMap.get(value).toString() : value;
        String[] splitValues = replaceValues.split("\\?!");
        for (String splitValue: splitValues){
            if (!splitValue.equals("")){
                value = value.replace(splitValue,"");
            }
        }
        value = trim ? value.trim() : value;
        Driver.TestMap.put(mapKey, value);
    }

    @Step("<value> değerini <splitValue> degeriyle parçala ve <mapPrefixKey> değerinde tut <trim>")
    public void splitAndSave(String value, String splitValue, String mapPrefixKey, boolean trim){

        value = value.endsWith("KeyValue") ? Driver.TestMap.get(value).toString() : value;
        String[] values = value.split(splitValue);
        int i = 0;
        for (String a : values){
            i++;
            a = trim ? a.trim() : a;
            Driver.TestMap.put(mapPrefixKey + i + "KeyValue", a);
        }
    }

    @Step("<islemTipi> işlem tipiyle <value1> ve <value2> integer değerleriyle işlemi gerçekleştir ve <mapKey> keyinde tut")
    public void integerProcess(String islemTipi, String value1, String value2, String mapKey){

        value1 = value1.endsWith("KeyValue") ? Driver.TestMap.get(value1).toString() : value1;
        value2 = value2.endsWith("KeyValue") ? Driver.TestMap.get(value2).toString() : value2;
        int total = methodsUtil.integerProcess(islemTipi, value1, value2);
        Driver.TestMap.put(mapKey, total);
    }

    @Step("<value> degerini <mapKey> keyinde tut")
    public void saveData(String value, String mapKey) {

        value = value.endsWith("KeyValue") ? Driver.TestMap.get(value).toString() : value;
        Driver.TestMap.put(mapKey, value);
    }

    @Step("<stringLength> uzunluğunda <charType> tipinde rastgele string üret ve <mapKey> keyinde tut")
    public void randomStringExtended(int stringLength, String charType, String mapKey) {

        String randomString = methodsUtil.randomStringExtended(stringLength, charType,"",0);
        logger.info(randomString);
        Driver.TestMap.put(mapKey, randomString);
    }

    @Step("<seconds> saniye bekle")
    public void waitBySeconds(long seconds) {

        methodsUtil.waitBySeconds(seconds);
    }

    @Step("<milliseconds> milisaniye bekle")
    public void waitByMilliSeconds(long milliseconds) {

        methodsUtil.waitByMilliSeconds(milliseconds);
    }

    /**@Step("<minioPath> dosyasını minio dan <localPath> yoluna indir")
    public void downloadMinioFile(String minioPath, String localPath){

    methodsUtil.downloadFileMinio("https://minio.migrosnext.com/","minio","devminio123"
    , "testscenarios", minioPath,System.getProperty("user.dir") + localPath);
    } */

}
