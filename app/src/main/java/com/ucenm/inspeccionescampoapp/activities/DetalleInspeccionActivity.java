package com.ucenm.inspeccionescampoapp.activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.ucenm.inspeccionescampoapp.R;
import com.ucenm.inspeccionescampoapp.api.ApiClient;
import com.ucenm.inspeccionescampoapp.api.ApiService;
import com.ucenm.inspeccionescampoapp.responses.InspeccionResponse;
import java.io.File;
import java.io.FileOutputStream;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.media.MediaPlayer;
import java.io.IOException;
import android.widget.LinearLayout;
public class DetalleInspeccionActivity extends AppCompatActivity {
    TextView tvTituloDetalle;
    TextView tvDescripcionDetalle;
    TextView tvEstadoDetalle;
    TextView tvListaObservaciones;
    TextView tvListaAudios;
    Button btnSubirFoto;
    Button btnGuardarObservacion;
    Button btnGrabarAudio;
    Button btnDetenerAudio;
    Button btnSubirAudio;
    Button btnEditarInspeccion;
    Button btnEliminarInspeccion;
    ImageView imgFoto;
    EditText edtObservacion;
    String inspeccionId;
    Bitmap imagenBitmap;
    ActivityResultLauncher<Intent> camaraLauncher;
    ApiService apiService;
    MediaRecorder mediaRecorder;
    String rutaAudio;
    MediaPlayer mediaPlayer;
    LinearLayout layoutAudios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_inspeccion);
        tvTituloDetalle =
                findViewById(R.id.tvTituloDetalle);
        tvDescripcionDetalle =
                findViewById(R.id.tvDescripcionDetalle);
        tvEstadoDetalle =
                findViewById(R.id.tvEstadoDetalle);
        btnSubirFoto =
                findViewById(R.id.btnSubirFoto);
        imgFoto =
                findViewById(R.id.imgFoto);
        edtObservacion =
                findViewById(R.id.edtObservacion);
        btnGuardarObservacion =
                findViewById(R.id.btnGuardarObservacion);
        tvListaObservaciones =
                findViewById(R.id.tvListaObservaciones);
        tvListaAudios =
                findViewById(R.id.tvListaAudios);
        layoutAudios =
                findViewById(R.id.layoutAudios);
        btnGrabarAudio =
                findViewById(R.id.btnGrabarAudio);
        btnDetenerAudio =
                findViewById(R.id.btnDetenerAudio);
        btnSubirAudio =
                findViewById(R.id.btnSubirAudio);
        btnEditarInspeccion =
                findViewById(R.id.btnEditarInspeccion);

        btnEliminarInspeccion =
                findViewById(R.id.btnEliminarInspeccion);
        apiService =
                ApiClient.getClient()
                        .create(ApiService.class);
        inspeccionId =
                getIntent().getStringExtra("Id");
        tvTituloDetalle.setText(
                getIntent().getStringExtra("Titulo")
        );
        tvDescripcionDetalle.setText(
                getIntent().getStringExtra("Descripcion")
        );
        tvEstadoDetalle.setText(
                "Estado: "
                        + getIntent().getStringExtra("Estado")
        );
        cargarObservaciones();
        cargarAudios();

        camaraLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        resultado -> {
                            if(resultado.getResultCode()
                                    == RESULT_OK){
                                Intent data =
                                        resultado.getData();
                                imagenBitmap =
                                        (Bitmap)data.getExtras()
                                                .get("data");
                                imgFoto.setImageBitmap(
                                        imagenBitmap
                                );
                                subirFotografia(imagenBitmap);
                            }
                        });
        btnSubirFoto.setOnClickListener(v -> {
            if(ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
            )
                    == PackageManager.PERMISSION_GRANTED){
                abrirCamara();
            }else{
                requestPermissions(
                        new String[]{
                                Manifest.permission.CAMERA
                        },
                        100
                );
            }
        });
        btnGuardarObservacion.setOnClickListener(v -> {
            String comentario =
                    edtObservacion.getText()
                            .toString()
                            .trim();
            if(comentario.isEmpty()){
                Toast.makeText(
                        this,
                        "Escriba una observación",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
            apiService.crearObservacion(
                    inspeccionId,
                    comentario
            ).enqueue(new Callback<InspeccionResponse>() {

                @Override
                public void onResponse(
                        Call<InspeccionResponse> call,
                        Response<InspeccionResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(
                                DetalleInspeccionActivity.this,
                                "Observación guardada",
                                Toast.LENGTH_SHORT
                        ).show();
                        edtObservacion.setText("");
                        cargarObservaciones();
                    }
                }

                @Override
                public void onFailure(
                        Call<InspeccionResponse> call,
                        Throwable t) {
                    Toast.makeText(
                            DetalleInspeccionActivity.this,
                            t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        });
        btnDetenerAudio.setOnClickListener(v -> {

            detenerGrabacion();

        });
        btnSubirAudio.setOnClickListener(v -> {

            if (rutaAudio != null) {

                subirAudio();

            } else {

                Toast.makeText(
                        this,
                        "Primero grabe un audio",
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

        btnGrabarAudio.setOnClickListener(v -> {


            if(ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED){


                iniciarGrabacion();


            }else{


                requestPermissions(
                        new String[]{
                                Manifest.permission.RECORD_AUDIO
                        },
                        200
                );


            }


        });
        btnEditarInspeccion.setOnClickListener(v -> {

            editarInspeccion();

        });
        btnEliminarInspeccion.setOnClickListener(v -> {

            new androidx.appcompat.app.AlertDialog.Builder(this)

                    .setTitle("Eliminar inspección")

                    .setMessage("¿Desea eliminar esta inspección?")

                    .setPositiveButton("Sí", (dialog, which) -> {

                        eliminarInspeccion();

                    })

                    .setNegativeButton("No", null)

                    .show();

        });
    }
    private void abrirCamara(){


        Intent intent =
                new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE
                );


        camaraLauncher.launch(intent);


    }
    private void subirFotografia(Bitmap bitmap) {


        try {


            File archivo =
                    new File(
                            getCacheDir(),
                            "foto.jpg"
                    );


            FileOutputStream salida =
                    new FileOutputStream(archivo);



            bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    100,
                    salida
            );


            salida.flush();
            salida.close();



            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("image/jpeg"),
                            archivo
                    );



            MultipartBody.Part imagen =
                    MultipartBody.Part.createFormData(
                            "imagen",
                            archivo.getName(),
                            requestFile
                    );



            RequestBody inspeccionIdBody =
                    RequestBody.create(
                            MediaType.parse("text/plain"),
                            inspeccionId
                    );



            apiService.subirFotografia(
                    inspeccionIdBody,
                    imagen
            ).enqueue(new Callback<InspeccionResponse>() {



                @Override
                public void onResponse(
                        Call<InspeccionResponse> call,
                        Response<InspeccionResponse> response) {


                    if(response.isSuccessful()){


                        Toast.makeText(
                                DetalleInspeccionActivity.this,
                                "Fotografía subida correctamente",
                                Toast.LENGTH_LONG
                        ).show();


                    }


                }



                @Override
                public void onFailure(
                        Call<InspeccionResponse> call,
                        Throwable t) {


                    Toast.makeText(
                            DetalleInspeccionActivity.this,
                            "Error: " + t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();


                }

            });



        }catch(Exception e){


            e.printStackTrace();


        }


    }
    private void cargarObservaciones(){


        apiService.listarObservaciones(
                inspeccionId
        ).enqueue(new Callback<java.util.List<com.ucenm.inspeccionescampoapp.models.Observacion>>() {



            @Override
            public void onResponse(
                    Call<java.util.List<com.ucenm.inspeccionescampoapp.models.Observacion>> call,
                    Response<java.util.List<com.ucenm.inspeccionescampoapp.models.Observacion>> response) {



                if(response.isSuccessful()
                        && response.body()!=null){



                    String texto =
                            "Observaciones:\n\n";



                    for(com.ucenm.inspeccionescampoapp.models.Observacion o :
                            response.body()){



                        texto +=
                                "• "
                                        + o.getComentario()
                                        + "\n\n";


                    }



                    tvListaObservaciones.setText(texto);



                }



            }



            @Override
            public void onFailure(
                    Call<java.util.List<com.ucenm.inspeccionescampoapp.models.Observacion>> call,
                    Throwable t) {


            }



        });


    }
    private void iniciarGrabacion(){

        try {

            File archivo =
                    new File(
                            getExternalCacheDir(),
                            "audio_" + System.currentTimeMillis() + ".m4a"
                    );


            rutaAudio = archivo.getAbsolutePath();


            mediaRecorder = new MediaRecorder();


            mediaRecorder.setAudioSource(
                    MediaRecorder.AudioSource.MIC
            );


            mediaRecorder.setOutputFormat(
                    MediaRecorder.OutputFormat.MPEG_4
            );


            mediaRecorder.setAudioEncoder(
                    MediaRecorder.AudioEncoder.AAC
            );


            mediaRecorder.setOutputFile(
                    rutaAudio
            );


            mediaRecorder.prepare();


            mediaRecorder.start();


            Toast.makeText(
                    this,
                    "Grabando audio...",
                    Toast.LENGTH_SHORT
            ).show();


        }catch(Exception e){

            e.printStackTrace();

            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();

        }

    }
    private void detenerGrabacion(){


        try {



            if(mediaRecorder != null){



                mediaRecorder.stop();



                mediaRecorder.release();



                mediaRecorder = null;



            }



        }catch(Exception e){


            e.printStackTrace();


        }


    }
    private void subirAudio() {

        try {

            File archivo = new File(rutaAudio);

            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("audio/3gpp"),
                            archivo
                    );

            MultipartBody.Part audio =
                    MultipartBody.Part.createFormData(
                            "audio",
                            archivo.getName(),
                            requestFile
                    );

            RequestBody inspeccionIdBody =
                    RequestBody.create(
                            MediaType.parse("text/plain"),
                            inspeccionId
                    );


            RequestBody duracionBody =
                    RequestBody.create(
                            MediaType.parse("text/plain"),
                            "10"
                    );

            apiService.subirAudio(
                    inspeccionIdBody,
                    duracionBody,
                    audio
            ).enqueue(new Callback<InspeccionResponse>() {

                @Override
                public void onResponse(Call<InspeccionResponse> call,
                                       Response<InspeccionResponse> response) {

                    if (response.isSuccessful()) {

                        Toast.makeText(
                                DetalleInspeccionActivity.this,
                                "Audio subido correctamente",
                                Toast.LENGTH_LONG
                        ).show();

                    } else {

                        Toast.makeText(
                                DetalleInspeccionActivity.this,
                                "Error al subir audio",
                                Toast.LENGTH_LONG
                        ).show();

                    }

                }

                @Override
                public void onFailure(Call<InspeccionResponse> call,
                                      Throwable t) {

                    Toast.makeText(
                            DetalleInspeccionActivity.this,
                            "Error: " + t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();

                }

            });

        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();

        }

    }
    private void cargarAudios(){

        Toast.makeText(
                    this,
                    "Cargando audios ID: " + inspeccionId,
                    Toast.LENGTH_SHORT
            ).show();

            apiService.listarAudios(
                    inspeccionId
            ).enqueue(new Callback<java.util.List<com.ucenm.inspeccionescampoapp.models.Audio>>() {


            @Override
            public void onResponse(
                    Call<java.util.List<com.ucenm.inspeccionescampoapp.models.Audio>> call,
                    Response<java.util.List<com.ucenm.inspeccionescampoapp.models.Audio>> response) {


                if(response.isSuccessful()
                        && response.body()!=null){


                    String texto =
                            "Audios:";

                    tvListaAudios.setText(texto);


                    layoutAudios.removeAllViews();


                    for(com.ucenm.inspeccionescampoapp.models.Audio audio :
                            response.body()){


                        Button botonAudio =
                                new Button(
                                        DetalleInspeccionActivity.this
                                );


                        botonAudio.setText(
                                "▶ Reproducir audio "
                                        + audio.getId()
                        );


                        botonAudio.setOnClickListener(v -> {

                            reproducirAudio(
                                    audio.getRutaAudio()
                            );

                        });


                        layoutAudios.addView(
                                botonAudio
                        );


                    }


                    tvListaAudios.setText(texto);


                }


            }


            @Override
            public void onFailure(
                    Call<java.util.List<com.ucenm.inspeccionescampoapp.models.Audio>> call,
                    Throwable t) {


            }


        });


    }
    private void reproducirAudio(String ruta){

        try {

            if(mediaPlayer != null){
                mediaPlayer.release();
            }


            String url =
                    "http://10.0.2.2/APIInspecciones/"
                            + ruta;

            mediaPlayer = new MediaPlayer();

            mediaPlayer.setDataSource(url);

            mediaPlayer.prepareAsync();


            mediaPlayer.setOnPreparedListener(mp -> {

                mediaPlayer.start();
                mediaPlayer.setVolume(1.0f, 1.0f);

                Toast.makeText(
                        this,
                        "Reproduciendo audio",
                        Toast.LENGTH_SHORT
                ).show();

            });


        } catch(Exception e){

            Toast.makeText(
                    this,
                    "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();

        }

    }
    private void eliminarInspeccion(){

        apiService.eliminarInspeccion(inspeccionId)

                .enqueue(new Callback<InspeccionResponse>() {

                    @Override
                    public void onResponse(
                            Call<InspeccionResponse> call,
                            Response<InspeccionResponse> response) {

                        if(response.isSuccessful()
                                && response.body()!=null){

                            Toast.makeText(
                                    DetalleInspeccionActivity.this,
                                    response.body().getMensaje(),
                                    Toast.LENGTH_LONG
                            ).show();

                            finish();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<InspeccionResponse> call,
                            Throwable t) {

                        Toast.makeText(
                                DetalleInspeccionActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }
    private void editarInspeccion(){

        Intent intent = new Intent(
                DetalleInspeccionActivity.this,
                EditarInspeccionActivity.class
        );


        intent.putExtra("id", inspeccionId);


        intent.putExtra(
                "titulo",
                tvTituloDetalle.getText().toString()
        );


        intent.putExtra(
                "descripcion",
                tvDescripcionDetalle.getText().toString()
        );


        intent.putExtra(
                "estado",
                tvEstadoDetalle.getText().toString()
        );


        startActivity(intent);

    }

}
