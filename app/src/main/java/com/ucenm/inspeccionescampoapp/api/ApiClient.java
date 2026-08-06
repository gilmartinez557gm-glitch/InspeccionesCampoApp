package com.ucenm.inspeccionescampoapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.ucenm.inspeccionescampoapp.utils.Constantes;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){

        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constantes.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }
}
