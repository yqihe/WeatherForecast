package com.weatherforecast.android.logic.chat;

import com.alibaba.dashscope.app.*;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;

public class WeatherAssistant {

    public static void callAgentApp()
            throws ApiException, NoApiKeyException, InputRequiredException {

        ApplicationParam param = ApplicationParam.builder()
                .apiKey("MY_API_KEY")
                .appId("MY_APP_ID")
                .prompt("根据当前的天气状况，为用户提供穿衣建议、出行提示和健康建议")
                .build();

        Application application = new Application();
        ApplicationResult result = application.call(param);

        System.out.printf("Request ID: %s\nResponse Text: %s\nFinish Reason: %s\n",
                result.getRequestId(), result.getOutput().getText(), result.getOutput().getFinishReason());
    }

    public static void main(String[] args) {
        try {
            callAgentApp();
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.out.printf("Exception encountered: %s\n", e.getMessage());
        }
        System.exit(0);
    }
}
