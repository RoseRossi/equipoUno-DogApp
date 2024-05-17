package com.equipo1.dogapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface DogApiService {
    @get:GET("breeds/list/all")
    val dogBreeds: Call<DogBreedsResponse?>?
    @GET("breed/{breed}/images/random")
    fun getRandomDogImageByBreed(@Path("breed") breed: String): Call<DogImageResponse>
}
