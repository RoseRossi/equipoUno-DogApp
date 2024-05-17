package com.equipo1.dogapp.api

import retrofit2.Call
import retrofit2.http.GET


interface DogApiService {
    @get:GET("breeds/list/all")
    val dogBreeds: Call<DogBreedsResponse?>?
}
