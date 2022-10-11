package com.homenumber.print_2.retrofit2;

import com.google.gson.GsonBuilder;
import com.homenumber.print_2.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.homenumber.print_2.Constant.DEBUG_MODE;

/**
 * RetrofitUtil
 * @author 나비이쁜이
 * @since 2019.08.19
 */
public class RetrofitUtil {

    /**
     * Timeout
     */
    private static final int RETROFIT_TIMEOUT = 15;

    /**
     * OkHttp & Retrofit2 설정
     */
    public static <element> element createService(String requestApi, String cookie, final Class<element> service) {

        /**
         * okHttp3 LoggingInterceptor
         */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        AddCookiesInterceptor in2 = new AddCookiesInterceptor();
        ReceivedCookiesInterceptor in1 = new ReceivedCookiesInterceptor();

        /**
         * OkHttp3 Bulider
         */
        OkHttpClient okHttpClient;

        if(DEBUG_MODE) {

            okHttpClient = new OkHttpClient().newBuilder()
//                    .addInterceptor(chain -> {
//                        Request request = chain.request().newBuilder().addHeader(Constant.PREF_KEY_USER_COOKIE, cookie).build();
//                        return chain.proceed(request);
//                    })
//                    .addInterceptor(in1)
//                    .addInterceptor(in2)
                    .addInterceptor(interceptor)                                                                    // okHttp3 LoggingInterceptor
                    .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                             // 기본 연결 제한 시간
                    .readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                                // 쓰기 연결 제한 시간
                    .writeTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                               // 읽기 연결 제한 시간
                    .build();                                                                                       // Build
        } else {
            okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                             // 기본 연결 제한 시간
                    .readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                                // 쓰기 연결 제한 시간
                    .writeTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                               // 읽기 연결 제한 시간
                    .build();                                                                                       // Build
        }

        /**
         * Retrofit2 Builder
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(requestApi)                                                                                // RequestApi
                .client(okHttpClient)                                                                               // OkHttpClient
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))                      // Gson Create
                .build();                                                                                           // Build

        return retrofit.create(service);
    }

    /**
     * OkHttp & Retrofit2 & Header 설정
     */
    public static <element> element createHeaderService1(String requestApi, String token, String device_uuid, final Class<element> service) {

        /**
         * okHttp3 LoggingInterceptor
         */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /**
         * OkHttp3 Bulider
         */
        OkHttpClient okHttpClient;
        if(DEBUG_MODE) {
            okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder().addHeader(Constant.RK_HEADER_ACCESS_TOKEN, token).addHeader(Constant.RK_HEADER_DEVICE_UUID, Constant.DEVICE_UUID_HEADER + device_uuid).build();
                        return chain.proceed(request);
                    })
                    .addInterceptor(interceptor)                                                                    // okHttp3 LoggingInterceptor
                    .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                             // 기본 연결 제한 시간
                    .readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                                // 쓰기 연결 제한 시간
                    .writeTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                               // 읽기 연결 제한 시간
                    .build();                                                                                       // Build
        } else {
            okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder().addHeader(Constant.RK_HEADER_ACCESS_TOKEN, token).addHeader(Constant.RK_HEADER_DEVICE_UUID, Constant.DEVICE_UUID_HEADER + device_uuid).build();
                        return chain.proceed(request);
                    })
                    .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                             // 기본 연결 제한 시간
                    .readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                                // 쓰기 연결 제한 시간
                    .writeTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                               // 읽기 연결 제한 시간
                    .build();                                                                                       // Build
        }

        /**
         * Retrofit2 Builder
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(requestApi)                                                                                // RequestApi
                .client(okHttpClient)                                                                               // OkHttpClient
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))                      // Gson Create
                .build();                                                                                           // Build

        return retrofit.create(service);
    }

    /**
     * OkHttp & Retrofit2 & Header 설정
     */
    public static <element> element createHeaderService2(String requestApi, String token, String device_uuid, String phonenumber, final Class<element> service) {

        /**
         * okHttp3 LoggingInterceptor
         */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /**
         * OkHttp3 Bulider
         */
        OkHttpClient okHttpClient;
        if(DEBUG_MODE) {
            okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder().addHeader(Constant.RK_HEADER_ACCESS_TOKEN, token).addHeader(Constant.RK_HEADER_DEVICE_UUID, Constant.DEVICE_UUID_HEADER + device_uuid).addHeader(Constant.RK_MOBILE_NO, phonenumber).build();
                        return chain.proceed(request);
                    })
                    .addInterceptor(interceptor)                                                                    // okHttp3 LoggingInterceptor
                    .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                             // 기본 연결 제한 시간
                    .readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                                // 쓰기 연결 제한 시간
                    .writeTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                               // 읽기 연결 제한 시간
                    .build();                                                                                       // Build
        } else {
            okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder().addHeader(Constant.RK_HEADER_ACCESS_TOKEN, token).addHeader(Constant.RK_HEADER_DEVICE_UUID, Constant.DEVICE_UUID_HEADER + device_uuid).addHeader(Constant.RK_MOBILE_NO, phonenumber).build();
                        return chain.proceed(request);
                    })
                    .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                             // 기본 연결 제한 시간
                    .readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                                // 쓰기 연결 제한 시간
                    .writeTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                               // 읽기 연결 제한 시간
                    .build();                                                                                       // Build
        }

        /**
         * Retrofit2 Builder
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(requestApi)                                                                                // RequestApi
                .client(okHttpClient)                                                                               // OkHttpClient
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))                      // Gson Create
                .build();                                                                                           // Build

        return retrofit.create(service);
    }

    /**
     * OkHttp & Retrofit2 & Header 설정
     */
    public static <element> element createHeaderService3(String requestApi, String device_uuid, String phonenumber, final Class<element> service) {

        /**
         * okHttp3 LoggingInterceptor
         */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /**
         * OkHttp3 Bulider
         */
        OkHttpClient okHttpClient;
        if(DEBUG_MODE) {
            okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder().addHeader(Constant.RK_HEADER_DEVICE_UUID, Constant.DEVICE_UUID_HEADER + device_uuid).addHeader(Constant.RK_MOBILE_NO, phonenumber).build();
                        return chain.proceed(request);
                    })
                    .addInterceptor(interceptor)                                                                    // okHttp3 LoggingInterceptor
                    .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                             // 기본 연결 제한 시간
                    .readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                                // 쓰기 연결 제한 시간
                    .writeTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                               // 읽기 연결 제한 시간
                    .build();                                                                                       // Build
        } else {
            okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder().addHeader(Constant.RK_HEADER_DEVICE_UUID, Constant.DEVICE_UUID_HEADER + device_uuid).addHeader(Constant.RK_MOBILE_NO, phonenumber).build();
                        return chain.proceed(request);
                    })
                    .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                             // 기본 연결 제한 시간
                    .readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                                // 쓰기 연결 제한 시간
                    .writeTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                               // 읽기 연결 제한 시간
                    .build();                                                                                       // Build
        }

        /**
         * Retrofit2 Builder
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(requestApi)                                                                                // RequestApi
                .client(okHttpClient)                                                                               // OkHttpClient
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))                      // Gson Create
                .build();                                                                                           // Build

        return retrofit.create(service);
    }

    /**
     * OkHttp & Retrofit2 & Header 설정
     */
    public static <element> element createHeaderService4(String requestApi, String device_uuid, final Class<element> service) {

        /**
         * okHttp3 LoggingInterceptor
         */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /**
         * OkHttp3 Bulider
         */
        OkHttpClient okHttpClient;
        if(DEBUG_MODE) {
            okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder().addHeader(Constant.RK_HEADER_DEVICE_UUID, Constant.DEVICE_UUID_HEADER + device_uuid).build();
                        return chain.proceed(request);
                    })
                    .addInterceptor(interceptor)                                                                    // okHttp3 LoggingInterceptor
                    .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                             // 기본 연결 제한 시간
                    .readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                                // 쓰기 연결 제한 시간
                    .writeTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                               // 읽기 연결 제한 시간
                    .build();                                                                                       // Build
        } else {
            okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder().addHeader(Constant.RK_HEADER_DEVICE_UUID, Constant.DEVICE_UUID_HEADER + device_uuid).build();
                        return chain.proceed(request);
                    })
                    .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                             // 기본 연결 제한 시간
                    .readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                                // 쓰기 연결 제한 시간
                    .writeTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)                                               // 읽기 연결 제한 시간
                    .build();                                                                                       // Build
        }

        /**
         * Retrofit2 Builder
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(requestApi)                                                                                // RequestApi
                .client(okHttpClient)                                                                               // OkHttpClient
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))                      // Gson Create
                .build();                                                                                           // Build

        return retrofit.create(service);
    }
}
