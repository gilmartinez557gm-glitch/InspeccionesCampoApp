package com.ucenm.inspeccionescampoapp.activities;


import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ucenm.inspeccionescampoapp.R;
import com.ucenm.inspeccionescampoapp.adapters.InspeccionAdapter;
import com.ucenm.inspeccionescampoapp.api.ApiClient;
import com.ucenm.inspeccionescampoapp.api.ApiService;
import com.ucenm.inspeccionescampoapp.models.Inspeccion;


import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ListaInspeccionesActivity extends AppCompatActivity {


    RecyclerView recyclerInspecciones;

    ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_inspecciones);


        recyclerInspecciones =
                findViewById(R.id.recyclerInspecciones);


        recyclerInspecciones.setLayoutManager(
                new LinearLayoutManager(this)
        );


        apiService = ApiClient.getClient()
                .create(ApiService.class);


        cargarInspecciones();


    }

    private void cargarInspecciones(){


        apiService.listarInspecciones()
                .enqueue(new Callback<List<Inspeccion>>() {


                    @Override
                    public void onResponse(
                            Call<List<Inspeccion>> call,
                            Response<List<Inspeccion>> response) {


                        if(response.isSuccessful()){


                            List<Inspeccion> lista =
                                    response.body();


                            InspeccionAdapter adapter =
                                    new InspeccionAdapter(lista);


                            recyclerInspecciones.setAdapter(adapter);


                        }


                    }


                    @Override
                    public void onFailure(
                            Call<List<Inspeccion>> call,
                            Throwable t) {


                        Toast.makeText(
                                ListaInspeccionesActivity.this,
                                "Error: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();


                    }

                });


    }


}