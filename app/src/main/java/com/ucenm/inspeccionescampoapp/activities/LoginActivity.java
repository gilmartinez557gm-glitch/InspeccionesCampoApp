package com.ucenm.inspeccionescampoapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ucenm.inspeccionescampoapp.R;
import com.ucenm.inspeccionescampoapp.api.ApiClient;
import com.ucenm.inspeccionescampoapp.api.ApiService;
import com.ucenm.inspeccionescampoapp.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.ucenm.inspeccionescampoapp.utils.SessionManager;
import android.content.Intent;
import com.ucenm.inspeccionescampoapp.activities.MainActivity;

public class LoginActivity extends AppCompatActivity {


    EditText etCorreo, etPassword;
    Button btnLogin;

    ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);


        apiService = ApiClient.getClient()
                .create(ApiService.class);


        btnLogin.setOnClickListener(v -> {

            String correo = etCorreo.getText().toString();
            String password = etPassword.getText().toString();


            login(correo, password);

        });

    }


    private void login(String correo, String password){


        apiService.login(correo, password)
                .enqueue(new Callback<LoginResponse>() {


                    @Override
                    public void onResponse(Call<LoginResponse> call,
                                           Response<LoginResponse> response) {


                        if(response.isSuccessful()){


                            if(response.isSuccessful()){

                                String id = response.body().getUsuario().getId();
                                String nombre = response.body().getUsuario().getNombre();
                                String correo = response.body().getUsuario().getCorreo();
                                String rol = response.body().getUsuario().getRol();


                                SessionManager session =
                                        new SessionManager(LoginActivity.this);


                                session.guardarUsuario(
                                        id,
                                        nombre,
                                        correo,
                                        rol
                                );


                                Toast.makeText(
                                        LoginActivity.this,
                                        "Bienvenido " + nombre,
                                        Toast.LENGTH_LONG
                                ).show();

                                Intent intent = new Intent(
                                        LoginActivity.this,
                                        MainActivity.class
                                );

                                startActivity(intent);

                                finish();

                            }


                        }


                    }


                    @Override
                    public void onFailure(Call<LoginResponse> call,
                                          Throwable t) {


                        Toast.makeText(
                                LoginActivity.this,
                                "Error: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();


                    }

                });


    }


}