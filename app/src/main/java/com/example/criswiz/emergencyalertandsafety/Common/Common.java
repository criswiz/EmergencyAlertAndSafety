package com.example.criswiz.emergencyalertandsafety.Common;

import com.example.criswiz.emergencyalertandsafety.Interface.IconBetterIdeaService;
import com.example.criswiz.emergencyalertandsafety.Interface.NewsService;
import com.example.criswiz.emergencyalertandsafety.Remote.IconBetterIdeaClient;
import com.example.criswiz.emergencyalertandsafety.Remote.RetrofitClient;

public class Common {
    private static final String BASE_URL="https://newsapi.org/";

    public static final String API_KEY="070a40f5dd6f48e7bc3106bd5b191787";

    public static NewsService getNewsService(){
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService(){
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

    public static String getAPIUrl(String source, String sortBy, String apiKey){
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/top-headlines?sources");
        return apiUrl.append(source)
                .append("&apiKeys")
                .append(apiKey)
                .toString();
    }
}
