package com.example.iec.data.remote

import com.example.data.IECNote
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT


interface NoteRemote {

    @GET("094e8611-5810-4c79-bf97-da4e49ce87c2")
    fun getNotes(): Result<List<IECNote>>

    @POST("add-note")
    fun addNote(@Body note: List<IECNote>): Result<List<IECNote>>

    @PUT("update-note")
    fun updateNote(@Body note: IECNote): Result<IECNote>

    @POST("delete-note")
    fun deleteNote(@Body note: List<IECNote>): Result<List<IECNote>>
}