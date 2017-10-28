/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sandrlab.listcase.di.app;

import android.support.annotation.NonNull;

import com.sandrlab.listcase.BuildConfig;
import com.sandrlab.listcase.data.network.GitHubApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
@Module
public final class NetworkModule {

    @Provides
    @NonNull
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel((BuildConfig.DEBUG) ?
                HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @NonNull
    @Singleton
    public Retrofit provideRetrofit(@NonNull OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @NonNull
    @Singleton
    public GitHubApi provideAccountService(@NonNull Retrofit retrofit) {
        return retrofit.create(GitHubApi.class);
    }
}
