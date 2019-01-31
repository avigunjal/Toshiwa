package com.toshiwa.Utils;


import com.toshiwa.Constants.Constants;
import com.toshiwa.Interface.APIService;

public class ApiUtils {
    private ApiUtils() {}

  public static final String BASE_URL = Constants.SERVER_URL;

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }


}
