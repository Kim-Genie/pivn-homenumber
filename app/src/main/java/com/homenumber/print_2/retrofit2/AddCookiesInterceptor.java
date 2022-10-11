package com.homenumber.print_2.retrofit2;

import com.homenumber.print_2.GlobalApplication;
import com.homenumber.print_2.userdata.UserData;
import com.homenumber.print_2.userdata.UserDataManager;
import com.homenumber.print_2.util.LogUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {



    protected UserDataManager userDataManager;
    protected UserData userData;

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        userDataManager = new UserDataManager(new GlobalApplication().mContext);
        userData = userDataManager.getUserData();

        Request.Builder builder = chain.request().newBuilder();

        try {

            Set<String> pf = userData.getUserCookie();

            for (String cookie : pf) {
                builder.addHeader("Set-Cookie",cookie);
            }

        } catch (NullPointerException e) {
            LogUtil.e("NullPointerException = 쿠키없음 " + e);
        }

//        builder.removeHeader("User-Agent").addHeader("User-Agent", "Android");

        return chain.proceed(builder.build());
    }
}
