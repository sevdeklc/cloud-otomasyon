package com.testinium.systematicTestCase.methods;

import com.google.common.base.Splitter;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;
import com.testinium.systematicTestCase.driver.Driver;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;

public class MethodsUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(MethodsUtil.class);
    
    public MethodsUtil(){

    }

    public String getSplitStringBuilder(String value, String splitValue, String mapKeySuffix){

        String[] values = Splitter.on(splitValue).splitToList(value).toArray(new String[0]);
        StringBuilder stringBuilder = new StringBuilder();
        for (String text: values){
            if(text.endsWith(mapKeySuffix)){
                text = Driver.TestMap.get(text).toString();
            }
            stringBuilder.append(text);
        }
        return stringBuilder.toString();
    }

    public void setStepJsonObjectValue(JsonObject jsonObject, String property, String value, String valueType) {

        if(!(valueType.equals("JsonArray") || valueType.equals("JsonObject") || valueType.equals("JsonElement"))
                && value.endsWith("KeyValue")){

            value = Driver.TestMap.get(value).toString();
        }
        String bigDecimal = value;
        switch (valueType) {
            case "":
                break;
            case "JsonArray":
                jsonObject.add(property, (JsonArray) Driver.TestMap.get(value));
                break;
            case "JsonObject":
                jsonObject.add(property, (JsonObject) Driver.TestMap.get(value));
                break;
            case "JsonElement":
                jsonObject.add(property, (JsonElement) Driver.TestMap.get(value));
                break;
            case "String":
                jsonObject.addProperty(property, value);
                break;
            case "Boolean":
                jsonObject.addProperty(property, Boolean.parseBoolean(value));
                break;
            case "Integer":
                jsonObject.addProperty(property, Integer.parseInt(value));
                break;
            case "Long":
                jsonObject.addProperty(property, Long.parseLong(value));
                break;
            case "Double":
                Number number = new Number() {
                    @Override
                    public int intValue() {
                        return 0;
                    }

                    @Override
                    public long longValue() {
                        return 0;
                    }

                    @Override
                    public float floatValue() {
                        return 0;
                    }

                    @Override
                    public double doubleValue() {
                        return 0;
                    }

                    @Override
                    public String toString() {
                        return new BigDecimal(String.valueOf(bigDecimal)).toPlainString();
                    }
                };
                jsonObject.addProperty(property, number);
                break;
            default:
                fail("Data tipi bulunamadı: " + valueType);
        }
    }

    public void setStepJsonArrayValue(JsonArray jsonArray, String value, String valueType) {

        if(!(valueType.equals("JsonArray") || valueType.equals("JsonObject") || valueType.equals("JsonElement"))
                && value.endsWith("KeyValue")){

            value = Driver.TestMap.get(value).toString();
        }
        String bigDecimal = value;
        switch (valueType) {
            case "":
                break;
            case "JsonArray":
                jsonArray.add((JsonArray) Driver.TestMap.get(value));
                break;
            case "JsonObject":
                jsonArray.add((JsonObject) Driver.TestMap.get(value));
                break;
            case "JsonElement":
                jsonArray.add((JsonElement) Driver.TestMap.get(value));
                break;
            case "String":
                jsonArray.add(value);
                break;
            case "Boolean":
                jsonArray.add(Boolean.parseBoolean(value));
                break;
            case "Integer":
                jsonArray.add(Integer.parseInt(value));
                break;
            case "Long":
                jsonArray.add(Long.parseLong(value));
                break;
            case "Double":
                Number number = new Number() {
                    @Override
                    public int intValue() {
                        return 0;
                    }

                    @Override
                    public long longValue() {
                        return 0;
                    }

                    @Override
                    public float floatValue() {
                        return 0;
                    }

                    @Override
                    public double doubleValue() {
                        return 0;
                    }

                    @Override
                    public String toString() {
                        return new BigDecimal(String.valueOf(bigDecimal)).toPlainString();
                    }
                };
                jsonArray.add(number);
                break;
            default:
                fail("Data tipi bulunamadı: " + valueType);
        }
    }

    public String setStringJson(String value, String splitValue, String newValues, String types){

        String[] arrayValue = Splitter.on(splitValue).splitToList(newValues).toArray(new String[0]);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arrayValue.length; i++){
            list.add("data" + (i+1) + "KeyValue");
        }
        char[] arrayTypes = types.toCharArray();
        String newValue = String.format(value, list.toArray(new String[0]));
        String a = "";
        for (int i=0; i < arrayValue.length; i++){
            a = arrayValue[i];
            a = a.endsWith("KeyValue") ? Driver.TestMap.get(a).toString() : a;
            switch (arrayTypes[i]){
                case 'S':
                    newValue = newValue.replace(list.get(i), a);
                    break;
                case 'B':
                case 'N':
                    newValue = newValue.replace("\"" + list.get(i) + "\"", a);
                    break;
                default:
                    fail("Hata");
            }
        }
        System.out.println(newValue);
        return newValue;
    }

    public String getTime(String format){

        return DateTime.now()//.withZone(DateTimeZone.UTC)
                .toString(format);//"dd/MM/yyyy HH:mm:ss,SSS");
    }
    public String getDateTimeFormat(){

        return DateTime.now()//.withZone(DateTimeZone.UTC)
                .toString();//"dd/MM/yyyy HH:mm:ss,SSS");
    }
    public long getTimeMillis(){

        DateTime utc = new DateTime();
        return utc.getMillis();
    }

    public long currentTimeMillis(){

        return System.currentTimeMillis();
    }

    public Long getTimeMillisFromTime(String time, String format){

        return org.joda.time.LocalDateTime.parse(time, DateTimeFormat.forPattern(format))
                .toDateTime().getMillis();
    }

    public Long getTimeMillisFromTime(String time, String format, int forOffsetHours){

        return org.joda.time.LocalDateTime.parse(time, DateTimeFormat.forPattern(format))
                .toDateTime(DateTimeZone.forOffsetHours(forOffsetHours)).getMillis();
    }

    public Long getTimeMillisFromTime(String time, String format, String zoneID){

        return org.joda.time.LocalDateTime.parse(time, DateTimeFormat.forPattern(format))
                .toDateTime(DateTimeZone.forID(zoneID)).getMillis();
    }

    public String getTimeFromMillis(String format, long millis){

        DateTime dateTime = new DateTime(millis);
        //"dd/MM/yyyy HH:mm:ss,SSS"
        return dateTime.toString(format);
    }

    public String getTimeFromMillisPlus(String format, long millis, int year, int month
            , int day, int hour, int minute, int second, int millisecond){

        DateTime dateTime = new DateTime(millis);
        //"dd/MM/yyyy HH:mm:ss,SSS"
        return dateTime.plusYears(year).plusMonths(month).plusDays(day).plusHours(hour)
                .plusMinutes(minute).plusSeconds(second).plusMillis(millisecond).toString(format);
    }

    public String getTimeFromMillisPlus(String format, long millis, int year, int month
            , int day, int hour, int minute, int second, int millisecond, String forOffsetHours, String language){

        DateTime dateTime = null;
        if (forOffsetHours.equals("null")){
            dateTime = new DateTime(millis);
        }else {
            dateTime = new DateTime(millis, DateTimeZone.forOffsetHours(Integer.parseInt(forOffsetHours)));
        }
        dateTime = dateTime.plusYears(year).plusMonths(month).plusDays(day).plusHours(hour)
                .plusMinutes(minute).plusSeconds(second).plusMillis(millisecond);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss");
        //"dd/MM/yyyy HH:mm:ss,SSS"
        String time = "";
        if (language.equals("null")){
            time = dateTimeFormatter.withLocale(Locale.getDefault()).print(dateTime);
        }else {
            time = dateTimeFormatter.withLocale(Locale.forLanguageTag(language)).print(dateTime);
        }
        return time;
    }

    public String getTimeFromMillis(String format, long millis, int forOffsetHours){

        DateTime dateTime = new DateTime(millis, DateTimeZone.forOffsetHours(forOffsetHours));
        //"dd/MM/yyyy HH:mm:ss,SSS"
        return dateTime.toString(format);
    }

    public String getTimeFromMillisWithZoneId(String format, long millis, String zoneID){

        DateTime dateTime = new DateTime(millis, DateTimeZone.forID(zoneID));
        //"dd/MM/yyyy HH:mm:ss,SSS"
        return dateTime.toString(format);
    }

    public String getTimeWithZoneId(String format, String zoneID){

        //"Europe/Istanbul"
        return DateTime.now(DateTimeZone.forID(zoneID))//.withZone(DateTimeZone.UTC)
                .toString(format);//"dd/MM/yyyy HH:mm:ss,SSS");
    }

    public String getTimeWithForOffsetHours(String format, int forOffsetHours){

        return DateTime.now(DateTimeZone.forOffsetHours(forOffsetHours))
                .toString(format);
    }

    private void setDoubleGsonFormat(GsonBuilder gsonBuilder){

        gsonBuilder.registerTypeAdapter(BigDecimal.class, (JsonSerializer<BigDecimal>) (src, typeOfSrc, context) -> {

            Number n = //src.scale() >= 8 ? (
                    new Number() {

                        @Override
                        public long longValue() {
                            return 0;
                        }

                        @Override
                        public int intValue() {
                            return 0;
                        }

                        @Override
                        public float floatValue() {
                            return 0;
                        }

                        @Override
                        public double doubleValue() {
                            return 0;
                        }

                        @Override
                        public String toString() {
                            return new BigDecimal(String.valueOf(src)).toPlainString();
                        }
                    }; //) : src
            return new JsonPrimitive(n);
        });
    }

    public Boolean writeJson(String jsonString, String fileLocation, boolean prettyPrint, boolean serializeNulls, boolean isAppend){

        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(Driver.userDir
                            + (Driver.slash.equals("/") ? fileLocation
                            : fileLocation.replace("/","\\"))
                            , isAppend), StandardCharsets.UTF_8));
            JsonElement element = JsonParser.parseString(jsonString);
            GsonBuilder gsonBuilder = new GsonBuilder();
            setDoubleGsonFormat(gsonBuilder);
            if (prettyPrint) { gsonBuilder.setPrettyPrinting(); }
            if (serializeNulls) { gsonBuilder.serializeNulls(); }
            Gson gson = gsonBuilder.create();
            gson.toJson(element, writer);
            writer.close();
            return true;
        } catch (IOException e) {
            logger.error(getStackTraceLog(e));
        }
        return false;
    }

    public <PANDA> Boolean writeJson(PANDA panda, String fileLocation, boolean prettyPrint, boolean serializeNulls, boolean isAppend){

        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(Driver.userDir
                            + (Driver.slash.equals("/") ? fileLocation
                            : fileLocation.replace("/","\\"))
                            , isAppend), StandardCharsets.UTF_8));
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.disableHtmlEscaping();
            setDoubleGsonFormat(gsonBuilder);
            if (prettyPrint) { gsonBuilder.setPrettyPrinting(); }
            if (serializeNulls) { gsonBuilder.serializeNulls(); }
            Gson gson = gsonBuilder.create();
            gson.toJson(panda, writer);
            writer.close();
            return true;
        } catch (IOException e) {
            logger.error(getStackTraceLog(e));
        }
        return false;
    }

    public <PANDA> PANDA readJson(Type type, String fileLocationOrStringJson, boolean isFile){

        GsonBuilder gsonBuilder = new GsonBuilder();
        setDoubleGsonFormat(gsonBuilder);
        Gson gson = gsonBuilder.create();
        try {
            if(isFile){
                FileReader fileReader = new FileReader(new File(Driver.userDir
                        + (Driver.slash.equals("/") ? fileLocationOrStringJson
                        : fileLocationOrStringJson.replace("/","\\"))));
                return gson.fromJson(fileReader, type);
            }
            return gson.fromJson(fileLocationOrStringJson, type);
        } catch (FileNotFoundException e) {
            logger.error(getStackTraceLog(e));
        }
        return null;
    }

    public Type getClassTypeToken(Type panda, Type... pandaClasses){
        // Type elementType = new TypeToken<>(){}.getType();
        if (pandaClasses.length != 0)
            return TypeToken.getParameterized(panda, pandaClasses).getType();
        return TypeToken.getParameterized(panda).getType();
    }

    public String getJsonStringWithBufferedReader(String fileLocation){

        StringBuilder jsonStringBuilder = new StringBuilder();
        InputStream propertiesStream = null;
        try {
            propertiesStream = new FileInputStream(Driver.userDir
                    + (Driver.slash.equals("/") ? fileLocation : fileLocation.replace("/","\\")));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(propertiesStream, StandardCharsets.UTF_8));
            String jsonString;
            while(true){
                if ((jsonString = bufferedReader.readLine()) == null) break;
                jsonStringBuilder.append(jsonString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStringBuilder.toString();
    }

    public String getStringXmlFile(String fileLocation){

        String xmlString = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(Driver.userDir
                    + (Driver.slash.equals("/") ? fileLocation : fileLocation.replace("/","\\")));

            xmlString = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return xmlString;
    }

    public int getRandomNumber(int length){

        Random random = new Random();
        return random.nextInt(length);
    }

    public Integer getRandomInt(int origin, int bound){

        return getRandomIteratorInt(origin, bound).nextInt();
    }

    public PrimitiveIterator.OfInt getRandomIteratorInt(int origin, int bound){

        Random random = new Random();
        return random.ints(origin, bound).iterator();
    }

    public Long getRandomLong(int origin, int bound){

        return getRandomIteratorLong(origin, bound).nextLong();
    }

    public PrimitiveIterator.OfLong getRandomIteratorLong(long origin, long bound){

        Random random = new Random();
        return random.longs(origin, bound).iterator();
    }

    public Integer randomNumber(int origin, int bound){

        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    public Double randomNumber(double origin, double bound){

        return ThreadLocalRandom.current().nextDouble(origin, bound);
    }

    public String calcHmacSha256(String secretKey, String value) {

        byte[] secretKeyDecodeByte = Base64.getDecoder().decode(secretKey.getBytes(StandardCharsets.UTF_8));
        byte[] valueByte = value.getBytes(StandardCharsets.UTF_8);
        byte[] hmacSha256 = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyDecodeByte, "HmacSHA256");
            mac.init(secretKeySpec);
            hmacSha256 = mac.doFinal(valueByte);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }
        return Base64.getEncoder().encodeToString(hmacSha256);
    }

    public String calcHmacSha1(String secretKey) {

        byte[] secretKeyDecodeByte = new Base32().decode(secretKey.getBytes(StandardCharsets.UTF_8));
        String googleAuthCode = "";
        try {
            Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
            long currentTimeSeconds = calendar.getTimeInMillis() / 1000L;
            long timeLong = currentTimeSeconds / (long)30;
            byte[] time = ByteBuffer.allocate(8).putLong(timeLong).array();
            Mac mac = Mac.getInstance("HMACSHA1");
            SecretKeySpec macKey = new SecretKeySpec(secretKeyDecodeByte,"RAW");
            mac.init(macKey);
            byte[] hash = mac.doFinal(time);
            int offset = hash[hash.length - 1] & 15;
            int binary = (hash[offset] & 127) << 24 | (hash[offset + 1] & 255) << 16 | (hash[offset + 2] & 255) << 8 | hash[offset + 3] & 255;
            int a = binary % 1000000;
            googleAuthCode = String.format("%06d", a);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hmac-sha1", e);
        }
        return googleAuthCode;
    }

    public String getStackTraceLog(Throwable e){

        StackTraceElement[] stackTraceElements = e.getStackTrace();
        String error = e.toString() + "\r\n";
        for (int i = 0; i < stackTraceElements.length; i++) {

            error = error + "\r\n" + stackTraceElements[i].toString();
        }
        return error;
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

    public Executable executableAll(String allName, List<Executable> executables){

        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {

                assertAll(allName, executables);
            }
        };
        return executable;
    }

    public String randomString(int stringLength){

        Random random = new Random();
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUWVXYZabcdefghijklmnopqrstuwvxyz0123456789".toCharArray();
        String stringRandom = "";
        for (int i = 0 ; i < stringLength ; i++){

            stringRandom = stringRandom + String.valueOf(chars[random.nextInt(chars.length)]);
        }

        return stringRandom;
    }

    public String randomStringExtended(int stringLength, String charType, String startCharType, int startCharLength){

        Random random = new Random();
        StringBuilder stringRandom = new StringBuilder();
        char[] chars = null;

        if(!startCharType.equals("")){

            chars = getChars(startCharType);
            for (int i = 0 ; i < startCharLength; i++) {
                stringRandom.append(String.valueOf(chars[random.nextInt(chars.length)]));
            }
            stringLength = stringLength - startCharLength;
        }

        chars = getChars(charType);

        for (int i = 0 ; i < stringLength; i++){

            stringRandom.append(String.valueOf(chars[random.nextInt(chars.length)]));
        }

        return stringRandom.toString();
    }

    public char[] getChars(String condition){

        String upperChars = "ABCDEFGHIJKLMNOPQRSTUWVXYZ";
        String lowerChars = "abcdefghijklmnopqrstuwvxyz";
        String numbers = "0123456789";
        char[] chars = null;
        switch (condition){
            case "upper":
                chars = upperChars.toCharArray();
                break;
            case "lower":
                chars = lowerChars.toCharArray();
                break;
            case "char":
                chars = (upperChars + lowerChars).toCharArray();
                break;
            case "numeric":
                chars = numbers.toCharArray();
                break;
            case "all":
                chars = (upperChars + lowerChars + numbers).toCharArray();
                break;
            default:
                fail("");
        }

        return chars;
    }

    public String getCopiedText() {

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        String copiedText = null;
        try {
            copiedText = contents.getTransferData(DataFlavor.stringFlavor).toString();
        } catch (IOException | UnsupportedFlavorException e) {
            logger.error(getStackTraceLog(e));
        }
        return copiedText;
    }

    public String setValueWithMap(String value){

        Matcher matcher3 = Pattern.compile("\\{[A-Za-z0-9_\\-?=.%+$&/()<>|ıİüÜöÖşŞçÇğĞ]+\\}").matcher(value);
        while (matcher3.find()){
            String t = matcher3.group();
            value = value.replace(t, Driver.TestMap
                    .get(t.replace("{","").replace("}","")).toString());
            System.out.println(t);
        }
        return value;
    }

    public String setValueWithMapKey(String value){

        Matcher matcher3 = Pattern.compile("\\{[A-Za-z0-9_\\-?=.%+$&/()<>|ıİüÜöÖşŞçÇğĞ]+KeyValue\\}").matcher(value);
        while (matcher3.find()){
            String t = matcher3.group();
            value = value.replace(t, Driver.TestMap
                    .get(t.replace("{","").replace("}","")).toString());
            System.out.println(t);
        }
        return value;
    }

    public String setJsonWithMapKey(String value){

        Matcher matcher3 = Pattern.compile("\\{[A-Za-z0-9_\\-?=.%+$&/()<>|ıİüÜöÖşŞçÇğĞ]+KeyValue\\}").matcher(value);
        while (matcher3.find()){
            String t = matcher3.group();
            String mapValue = t.replace("{","").replace("}","");
            t = mapValue.endsWith("0KeyValue") ? "\"" + t + "\"" : t;
            value = value.replace(t, Driver.TestMap.get(mapValue).toString());
        }
        return value;
    }

    public int randomNumber(int number){

        Random random = new Random();
        return random.nextInt(number);
    }

    public int integerProcess(String islemTipi, String value1, String value2){

        int total = 0;
        switch (islemTipi){

            case "topla":
                total = Integer.parseInt(value1) + Integer.parseInt(value2);
                break;
            case "cikar":
                total = Integer.parseInt(value1) - Integer.parseInt(value2);
                break;
            case "carp":
                total = Integer.parseInt(value1) * Integer.parseInt(value2);
                break;
            case "bol":
                total = Integer.parseInt(value1) / Integer.parseInt(value2);
                break;
            default:
                fail("İşlem Tipi Hatalı " + islemTipi);
        }

        return total;
    }

    public <T> T readJsonValue(String jsonString, String jsonPath){

        return JsonPath.read(jsonString, jsonPath);
    }

    public int getJsonListNumber(String jsonValue, String key, String value, int size){

        int number = 0;
        for (int i = 0; i < size; i++) {
            String json = readJsonValue(jsonValue,"$[" + i + "]." + key).toString();
            if (json.equals(value)) {
              number = i;
            }
        }
        return number;
    }

    public boolean valueControl(String expectedValue, String actualValue,String condition){

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
            case "equalInt":
                result = Integer.parseInt(actualValue) == Integer.parseInt(expectedValue);
                break;
            default:
                Assert.fail("hatali durum: " + condition);
        }
        return result;
    }

}
