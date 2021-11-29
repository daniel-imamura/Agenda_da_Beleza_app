package br.unicamp.agenda_da_beleza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Salao_login extends AppCompatActivity {

    TextView tvCadastro;
    EditText edtEmail, edtSenha;
    Button btnEntrar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salao_login);

        toolbar = (Toolbar) findViewById(R.id.tbLoginSalao);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtEmail = (EditText) findViewById(R.id.edtEmailSalaoLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenhaSalaoLogin);
        btnEntrar = (Button) findViewById(R.id.btnEntrarSalao);
        tvCadastro = (TextView) findViewById(R.id.tvCadastro);

        tvCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Salao_login.this, Salao_cadastro.class);
                startActivity(intent);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtEmail.getText().toString().equals("") || edtSenha.getText().toString().equals(""))
                    Toast.makeText(Salao_login.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())
                    Toast.makeText(Salao_login.this, "Email incorreto", Toast.LENGTH_SHORT).show();
                else
                    login();
            }
        });
    }
    public void login() {
        String strEmail = edtEmail.getText().toString();
        String strSenha = edtSenha.getText().toString();

        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<Salao> call = service.buscarSalaoPorEmail(strEmail, strSenha);
        call.enqueue(new Callback<Salao>() {
            @Override
            public void onResponse(Call<Salao> call, Response<Salao> response) {
                if(response.isSuccessful())
                {
                    Intent intent = new Intent(Salao_login.this, Home.class);
                    intent.putExtra("idCliente", response.body().getIdSalao());
                    intent.putExtra("SerializableSalao", (Serializable) response.body());
                    intent.putExtra("flag", "Salao");
                    finish();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Salao_login.this, "Dados inválidos!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Salao> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
                Toast.makeText(Salao_login.this, messageProblem, Toast.LENGTH_SHORT).show();
                Toast.makeText(Salao_login.this, "Erro no login!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home ) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}