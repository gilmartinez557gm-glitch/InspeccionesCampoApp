package com.ucenm.inspeccionescampoapp.api;

import com.ucenm.inspeccionescampoapp.models.Inspeccion;
import com.ucenm.inspeccionescampoapp.responses.InspeccionResponse;
import com.ucenm.inspeccionescampoapp.responses.LoginResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import com.ucenm.inspeccionescampoapp.models.Observacion;
import retrofit2.http.Query;
import com.ucenm.inspeccionescampoapp.models.Audio;



public interface ApiService {

    @FormUrlEncoded
    @POST("observaciones/crear.php")
    Call<InspeccionResponse> crearObservacion(
            @Field("inspeccionId") String inspeccionId,
            @Field("comentario") String comentario
    );

    @GET("observaciones/listar.php")
    Call<List<Observacion>> listarObservaciones(
            @Query("inspeccionId") String inspeccionId
    );
    @FormUrlEncoded
    @POST("usuarios/login.php")
    Call<LoginResponse> login(
            @Field("correo") String correo,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("inspecciones/crear.php")
    Call<InspeccionResponse> crearInspeccion(
            @Field("usuarioId") String usuarioId,
            @Field("titulo") String titulo,
            @Field("descripcion") String descripcion,
            @Field("latitud") String latitud,
            @Field("longitud") String longitud,
            @Field("estado") String estado
    );


    @GET("inspecciones/listar.php")
    Call<List<Inspeccion>> listarInspecciones();
    @Multipart
    @POST("fotografias/upload.php")
    Call<InspeccionResponse> subirFotografia(
            @Part("inspeccionId") RequestBody inspeccionId,
            @Part MultipartBody.Part imagen
    );
    @Multipart
    @POST("audios/upload.php")
    Call<InspeccionResponse> subirAudio(
            @Part("inspeccionId") RequestBody inspeccionId,
            @Part("duracion") RequestBody duracion,
            @Part MultipartBody.Part audio
    );

    @GET("audios/listar.php")
    Call<List<Audio>> listarAudios(
            @Query("inspeccionId") String inspeccionId
    );

    @FormUrlEncoded
    @POST("inspecciones/editar.php")
    Call<InspeccionResponse> editarInspeccion(

            @Field("id") String id,
            @Field("titulo") String titulo,
            @Field("descripcion") String descripcion,
            @Field("latitud") String latitud,
            @Field("longitud") String longitud,
            @Field("estado") String estado

    );

    @FormUrlEncoded
    @POST("inspecciones/eliminar.php")
    Call<InspeccionResponse> eliminarInspeccion(

            @Field("id") String id

    );

}