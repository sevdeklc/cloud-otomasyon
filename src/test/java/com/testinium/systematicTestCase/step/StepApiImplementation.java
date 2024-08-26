package com.testinium.systematicTestCase.step;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.testinium.systematicTestCase.driver.Driver;
import com.testinium.systematicTestCase.methods.ApiMethods;
import com.testinium.systematicTestCase.methods.MethodsUtil;
import com.thoughtworks.gauge.Step;
import io.restassured.response.Response;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.log4j.Logger.getLogger;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepApiImplementation {

    private static final Logger logger = LoggerFactory.getLogger(StepApiImplementation.class);
    MethodsUtil methodsUtil;
    ApiMethods apiMethods;
    private static StepApiImplementation stepApiImplementation;

    public StepApiImplementation(){

        methodsUtil = new MethodsUtil();
        apiMethods = new ApiMethods();
        getLogger(StepApiImplementation.class).setLevel(Level.ALL);
    }

    public static StepApiImplementation getInstance(){
        if(stepApiImplementation==null){
            stepApiImplementation = new StepApiImplementation();
        }
        return stepApiImplementation;
    }

    @Step("<log> log olarak ekle")
    public void logText(String log){

        log = log.endsWith("KeyValue") ? Driver.TestMap.get(log).toString() : log;
        logger.info(log);
    }

    @Step("<apiMapKey> api testi için map key olustur")
    public void setKey(String apiMapKey){

        Driver.apiMap.put(apiMapKey, new ConcurrentHashMap<String, Object>());
    }

    @Step("<baseUri> baseUri ı <apiMapKey> e ekle")
    public void setKeyBaseUri(String baseUri, String apiMapKey){

        if(baseUri.endsWith("KeyValue")){
            baseUri = Driver.TestMap.get(baseUri).toString();
        }
        logger.info("BaseUri: " + baseUri);
        Driver.apiMap.get(apiMapKey).put("baseUri", baseUri);
    }

    @Step("<isRelaxedHTTPSValidation> relaxedHTTPSValidation ı <apiMapKey> e ekle")
    public void setRelaxedHTTPSValidation(Boolean isRelaxedHTTPSValidation, String apiMapKey){

        logger.info("isRelaxedHTTPSValidation: " + isRelaxedHTTPSValidation);
        Driver.apiMap.get(apiMapKey).put("isRelaxedHTTPSValidation", isRelaxedHTTPSValidation);
    }

    @Step("<acceptValue> accept degerini <apiMapKey> e ekle")
    public void setKeyAccept(String acceptValue, String apiMapKey){

        Driver.apiMap.get(apiMapKey).put("accept", acceptValue);
    }

    @Step("<contentType> contentType degerini <apiMapKey> e ekle")
    public void setKeyContentType(String contentType, String apiMapKey){

        if(contentType.endsWith("KeyValue")){
            contentType = Driver.TestMap.get(contentType).toString();
        }
        logger.info("Content-Type: " + contentType);
        Driver.apiMap.get(apiMapKey).put("contentType", contentType);
    }

    //unchecked
    @Step("<headerKey> <headerValue> header degerini <apiMapKey> e ekle")
    public void setKeyHeaders(String headerKey, String headerValue, String apiMapKey){

        if(headerKey.endsWith("KeyValue")){
            headerKey = Driver.TestMap.get(headerKey).toString();
        }
        if(headerValue.endsWith("KeyValue")){
            headerValue = Driver.TestMap.get(headerValue).toString();
        }
        headerValue = methodsUtil.setValueWithMapKey(headerValue);
        if (Driver.apiMap.get(apiMapKey).containsKey("headers")){

            ((ConcurrentHashMap<String, String>) Driver.apiMap.get(apiMapKey).get("headers"))
                    .put(headerKey, headerValue);
        }else {
            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
            map.put(headerKey, headerValue);
            Driver.apiMap.get(apiMapKey).put("headers", map);
        }
    }

    @Step("<username> <password> basic auth degerini <apiMapKey> e ekle preemptive <preemptive>")
    public void setBasicAuth(String username, String password, String apiMapKey, Boolean preemptive) {

        if (username.endsWith("KeyValue")) {
            username = Driver.TestMap.get(username).toString();
        }
        if (password.endsWith("KeyValue")) {
            password = Driver.TestMap.get(password).toString();
        }
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
        map.put("username", username);
        map.put("password", password);
        map.put("preemptive", preemptive);
        Driver.apiMap.get(apiMapKey).put("basicAuth", map);
    }

    @Step("<host> <port> <username> <password> yada <uri> degerleriyle proxy i <apiMapKey> e ekle")
    public void setProxy(String host, String port, String username, String password, String uri, String apiMapKey) {

            if (host.endsWith("KeyValue")) {
                host = Driver.TestMap.get(host).toString();
            }
            if (port.endsWith("KeyValue")) {
                port = Driver.TestMap.get(port).toString();
            }
            ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
            if (!host.equals("") && !port.equals("")) {
                map.put("host", host);
                map.put("port", port);
            }
            if (username.endsWith("KeyValue")) {
                username = Driver.TestMap.get(username).toString();
            }
            if (password.endsWith("KeyValue")) {
                password = Driver.TestMap.get(password).toString();
            }
            if (!username.equals("") && !password.equals("")) {
                map.put("username", username);
                map.put("password", password);
            }
            if (uri.endsWith("KeyValue")) {
                uri = Driver.TestMap.get(uri).toString();
            }
            if (!uri.equals("")) {
                map.put("uri", uri);
            }
            Driver.apiMap.get(apiMapKey).put("proxy", map);
    }

    @SuppressWarnings("unchecked")
    @Step("<cookiesKey> <cookiesValue> cookies degerini <apiMapKey> e ekle")
    public void setCookies(String cookiesKey, String cookiesValue, String apiMapKey){

        if(cookiesKey.endsWith("KeyValue")){
            cookiesKey = Driver.TestMap.get(cookiesKey).toString();
        }
        if(cookiesValue.endsWith("KeyValue")){
            cookiesValue = Driver.TestMap.get(cookiesValue).toString();
        }
        if (Driver.apiMap.get(apiMapKey).containsKey("cookies")){

            ((ConcurrentHashMap<String, String>) Driver.apiMap.get(apiMapKey).get("cookies"))
                    .put(cookiesKey, cookiesValue);
        }else {
            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
            map.put(cookiesKey, cookiesValue);
            Driver.apiMap.get(apiMapKey).put("cookies", map);
        }
    }

    @SuppressWarnings("unchecked")
    @Step("<paramKey> <paramValue> parametre degerini <apiMapKey> e ekle")
    public void setKeyParams(String paramKey, String paramValue, String apiMapKey){

        if(paramValue.endsWith("KeyValue")){
            paramValue = Driver.TestMap.get(paramValue).toString();
        }
        logger.info(paramKey + " " + paramValue);
        if (Driver.apiMap.get(apiMapKey).containsKey("params")){

            ((ConcurrentHashMap<String, Object>) Driver.apiMap.get(apiMapKey).get("params"))
                    .put(paramKey, paramValue);
        }else {
            ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
            map.put(paramKey, paramValue);
            Driver.apiMap.get(apiMapKey).put("params", map);
        }
    }

    @SuppressWarnings("unchecked")
    @Step("<paramKey> <paramValue> query parametre degerini <apiMapKey> e ekle")
    public void setKeyQueryParams(String paramKey, String paramValue, String apiMapKey){

        if(paramValue.endsWith("KeyValue")){
            paramValue = Driver.TestMap.get(paramValue).toString();
        }
        logger.info(paramKey + " " + paramValue);
        if (Driver.apiMap.get(apiMapKey).containsKey("queryParams")){

            ((ConcurrentHashMap<String, Object>) Driver.apiMap.get(apiMapKey).get("queryParams"))
                    .put(paramKey, paramValue);
        }else {
            ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
            map.put(paramKey, paramValue);
            Driver.apiMap.get(apiMapKey).put("queryParams", map);
        }
    }

    @SuppressWarnings("unchecked")
    @Step("<paramKey> <paramValue> form parametre degerini <apiMapKey> e ekle")
    public void setKeyFormParams(String paramKey, String paramValue, String apiMapKey){

        if(paramValue.endsWith("KeyValue")){
            paramValue = Driver.TestMap.get(paramValue).toString();
        }
        if (Driver.apiMap.get(apiMapKey).containsKey("formParams")){

            ((ConcurrentHashMap<String, Object>) Driver.apiMap.get(apiMapKey).get("formParams"))
                    .put(paramKey, paramValue);
        }else {
            ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
            map.put(paramKey, paramValue);
            Driver.apiMap.get(apiMapKey).put("formParams", map);
        }
    }

    @Step("<body> body degerini <bodyType> tipiyle <apiMapKey> e ekle")
    public void setKeyBody(String body, String bodyType, String apiMapKey){

        if(bodyType.equals("String") && body.endsWith("KeyValue")){
            body = Driver.TestMap.get(body).toString();
        }
        Driver.apiMap.get(apiMapKey).put("body", body);
        Driver.apiMap.get(apiMapKey).put("bodyType", "body" + bodyType);
        logger.info(Driver.apiMap.get(apiMapKey).get("body").toString());
    }

    @Step("<requestType> requestType ve <requestPath> i <apiMapKey> e ekle <requestPathType>")
    public void setKeyRequestType(String requestType, String requestPath, String apiMapKey, String requestPathType){

        requestPath = methodsUtil.setValueWithMap(requestPath);

        if(requestPath.endsWith("KeyValue")){
            requestPath = Driver.TestMap.get(requestPath).toString();
        }

        logger.info(requestPath);
        Driver.apiMap.get(apiMapKey).put("requestType", requestType);
        Driver.apiMap.get(apiMapKey).put("requestPathType", requestPathType);
        Driver.apiMap.get(apiMapKey).put("requestPath", requestPath);
    }

    @Step("<apiMapKey> api testi için istek at, log=<logActive> if <ifCondition>")
    public void sendRequest(String apiMapKey, boolean logActive, String ifCondition){

        if(ifCondition.endsWith("KeyValue")){
            ifCondition = Driver.TestMap.get(ifCondition).toString();
        }
        boolean condition = Boolean.parseBoolean(ifCondition);
        if(condition) {
            sendRequest(apiMapKey, logActive);
        }
    }

    @Step("<apiMapKey> api testi için istek at")
    public void sendRequest(String apiMapKey){

        sendRequest(apiMapKey,false);
    }

    @Step("<apiMapKey> api testi için istek at, log=<logActive>")
    public void sendRequest(String apiMapKey, boolean logActive){

        Response response = apiMethods.getResponse(Driver.apiMap.get(apiMapKey));
        logger.info(response.statusLine());
        if (logActive) {
            logger.info("" + response.statusCode());
            logger.info(response.asString());
            //logger.info("" + response.getTime());
            //logger.info("" + response.getCookies().toString());
            //logger.info(response.getHeaders().toString());
        }
        Driver.apiMap.get(apiMapKey).put("response", response);
    }

    @Step("<apiMapKey> api testi statusCode değeri <statusCode> değerine eşit mi")
    public void statusCodeControl(String apiMapKey, String statusCode){

        statusCode = statusCode.endsWith("KeyValue") ? Driver.TestMap.get(statusCode).toString() : statusCode;
        Response response = (Response) Driver.apiMap.get(apiMapKey).get("response");
        assertEquals(Integer.parseInt(statusCode), response.statusCode());
    }

    @Step("<property> <value> json objesi değeri olarak <mapJsonObjectKey> e ekle <valueType>")
    public void addJsonObject(String property, String value, String mapKey, String valueType){

        if(Driver.TestMap.containsKey(mapKey)){
            JsonObject jsonObject = (JsonObject) Driver.TestMap.get(mapKey);
            methodsUtil.setStepJsonObjectValue(jsonObject, property, value, valueType);
            logger.info("" + Driver.TestMap.get(mapKey).toString());
        }else {
            JsonObject jsonObject = new JsonObject();
            methodsUtil.setStepJsonObjectValue(jsonObject, property, value, valueType);
            Driver.TestMap.put(mapKey, jsonObject);
            logger.info("" + Driver.TestMap.get(mapKey).toString());
        }
    }

    @Step("<mapJsonObjectKey> json array olustur <isOverwrite>")
    public void createJsonArray(String mapKey, Boolean isOverwrite){

        if(isOverwrite || !Driver.TestMap.containsKey(mapKey)) {
            JsonArray jsonArray = new JsonArray();
            Driver.TestMap.put(mapKey, jsonArray);
        }
    }

    @Step("<value> json dizisi değerini <mapJsonObjectKey> e ekle <valueType>")
    public void addJsonArray(String value, String mapKey, String valueType){

        if(Driver.TestMap.containsKey(mapKey)){
            JsonArray jsonArray = (JsonArray) Driver.TestMap.get(mapKey);
            methodsUtil.setStepJsonArrayValue(jsonArray, value, valueType);
        }else {
            JsonArray jsonArray = new JsonArray();
            methodsUtil.setStepJsonArrayValue(jsonArray, value, valueType);
            Driver.TestMap.put(mapKey, jsonArray);
        }
    }

    @Step("<mapJsonObjectKey> de tutulan json objesini text olarak tut")
    public void saveJsonObjectToString(String mapKey){

        JsonObject jsonObject = (JsonObject) Driver.TestMap.get(mapKey);
        String jsonObjectString = jsonObject.toString();
        logger.info(jsonObjectString);
        Driver.TestMap.put(mapKey, jsonObjectString);
    }

    @Step("<mapJsonObjectKey> de tutulan json dizisini text olarak tut")
    public void saveJsonArrayToString(String mapKey){

        JsonArray jsonArray = (JsonArray) Driver.TestMap.get(mapKey);
        String jsonArrayString = jsonArray.toString();
        logger.info(jsonArrayString);
        Driver.TestMap.put(mapKey, jsonArrayString);
    }

    @Step("<mapKey> için bearer token ekle")
    public void setBearerToken(String mapKey){

        setKeyHeaders("Authorization","Bearer "
                + Driver.TestMap.get("token").toString(), mapKey);
    }

    @Step("<mapKey> keyini map ten sil")
    public void removeMapKey(String mapKey){

        logger.info("" + Driver.TestMap.containsKey(mapKey));
        Driver.TestMap.remove(mapKey);
        logger.info("" + Driver.TestMap.containsKey(mapKey));
    }

    @Step("Map i temizle")
    public void clearMap(){

        Driver.TestMap.clear();
        logger.info("" + Driver.TestMap.size());
    }

    @Step("<apiMapKey> keyini api map ten sil")
    public void removeApiMapKey(String apiMapKey){

        logger.info("" + Driver.apiMap.containsKey(apiMapKey));
        Driver.apiMap.remove(apiMapKey);
        logger.info("" + Driver.apiMap.containsKey(apiMapKey));
    }

    @Step("Api map i temizle")
    public void clearApiMap(){

        Driver.apiMap.clear();
        logger.info("" + Driver.apiMap.size());
    }

    @Step("<mapKey> json path <jsonPath> değerini <saveMapKey> keyinde tut")
    public void saveJsonValue(String mapKey, String jsonPath, String saveMapKey){

        String jsonString = mapKey;
        if(Driver.apiMap.containsKey(mapKey)){
            jsonString = ((Response)Driver.apiMap.get(mapKey).get("response")).asString();
        }
        if(mapKey.endsWith("KeyValue")){
            jsonString = Driver.TestMap.get(mapKey).toString();
        }
        jsonPath = methodsUtil.setValueWithMapKey(jsonPath);
        Object jsonValue = methodsUtil.readJsonValue(jsonString, jsonPath);
        Driver.TestMap.put(saveMapKey, jsonValue);
    }

    @Step("<mapKey> json path <jsonPath> değeri <expectedValue> değerine eşit mi")
    public void controlJsonValue(String mapKey, String jsonPath, String expectedValue){

        String jsonString = mapKey;
        if(Driver.apiMap.containsKey(mapKey)){
            jsonString = ((Response)Driver.apiMap.get(mapKey).get("response")).asString();
        }
        if(mapKey.endsWith("KeyValue")){
            jsonString = Driver.TestMap.get(mapKey).toString();
            JsonElement element = JsonParser.parseString(jsonString);
            if (element.isJsonArray()){
                jsonString = element.getAsJsonArray().toString();
            }
            if (element.isJsonObject()){
                jsonString = element.getAsJsonObject().toString();
            }
        }
        jsonPath = methodsUtil.setValueWithMapKey(jsonPath);
        String jsonValue = methodsUtil.readJsonValue(jsonString, jsonPath).toString();
        logger.info(jsonValue);
        if(expectedValue.endsWith("KeyValue")){
            expectedValue = Driver.TestMap.get(expectedValue).toString();
        }
        assertEquals(expectedValue,jsonValue);
    }

    @Step("<mapKey> json path <jsonPath> liste sayısını <saveMapKey> keyinde tut")
    public void saveJsonArraySize(String mapKey, String jsonPath, String saveMapKey){

        String jsonString = mapKey;
        if(Driver.apiMap.containsKey(mapKey)){
            jsonString = ((Response)Driver.apiMap.get(mapKey).get("response")).asString();
        }
        if(mapKey.endsWith("KeyValue")){
            jsonString = Driver.TestMap.get(mapKey).toString();
            JsonElement element = JsonParser.parseString(jsonString);
            if (element.isJsonArray()){
                jsonString = element.getAsJsonArray().toString();
            }
        }
        jsonPath = methodsUtil.setValueWithMapKey(jsonPath);
        String jsona = methodsUtil.readJsonValue(jsonString, jsonPath).toString();
        JsonElement element = JsonParser.parseString(jsona);
        int listSize = element.getAsJsonArray().size();
        System.out.println(listSize);
        Driver.TestMap.put(saveMapKey, listSize);
    }

    @Step("<mapKey> json path <jsonPath> liste sayısı <expectedValue> degerine eşit mi kontrol et")
    public void controlJsonArraySize(String mapKey, String jsonPath, String expectedValue){

        String jsonString = mapKey;
        if(Driver.apiMap.containsKey(mapKey)){
            jsonString = ((Response)Driver.apiMap.get(mapKey).get("response")).asString();
        }
        if(mapKey.endsWith("KeyValue")){
            jsonString = Driver.TestMap.get(mapKey).toString();
        }
        jsonPath = methodsUtil.setValueWithMapKey(jsonPath);
        int size = ((List<Object>) methodsUtil.readJsonValue(jsonString, jsonPath)).size();
        int expectedValueInt = Integer.parseInt(expectedValue);
        assertEquals(expectedValueInt,size);
    }

    @Step("<mapKey> json path <jsonPath> listesindeki <key> <value> degerine sahip elementin konumunu <saveMapKey> keyinde tut")
    public void saveJsonArray(String mapKey, String jsonPath, String key, String value, String saveMapKey){

        String jsonString = mapKey;
        if(Driver.apiMap.containsKey(mapKey)){
            jsonString = ((Response)Driver.apiMap.get(mapKey).get("response")).asString();
        }
        if(mapKey.endsWith("KeyValue")){
            jsonString = Driver.TestMap.get(mapKey).toString();
            JsonElement element = JsonParser.parseString(jsonString);
            if (element.isJsonArray()){
                jsonString = element.getAsJsonArray().toString();
            }
        }
        jsonPath = methodsUtil.setValueWithMapKey(jsonPath);
        String jsona = methodsUtil.readJsonValue(jsonString, jsonPath).toString();
        JsonElement element = JsonParser.parseString(jsona);
        JsonArray jsonArray = element.getAsJsonArray();
        int size = jsonArray.size();
        int number = methodsUtil.getJsonListNumber(jsonArray.toString(), key, value, size);
        Driver.TestMap.put(saveMapKey, number);
    }

}
