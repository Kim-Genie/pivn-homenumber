package com.homenumber.print_2.retrofit2;

import com.homenumber.print_2.GlobalApplication;
import com.homenumber.print_2.userdata.UserData;
import com.homenumber.print_2.userdata.UserDataManager;
import com.homenumber.print_2.util.LogUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {


    protected UserDataManager userDataManager;
    protected UserData userData;

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        userDataManager = new UserDataManager(new GlobalApplication().mContext);
        userData = userDataManager.getUserData();

        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            Set<String> cookies = new HashSet<>();


            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }



            LogUtil.e("ReceivedCookiesInterceptor.cookies = " + cookies);

//            userData.setUserCookie(cookies);

        }

        return originalResponse;
    }
}
