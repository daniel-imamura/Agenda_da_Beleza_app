package br.unicamp.agenda_da_beleza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cliente_login extends AppCompatActivity {

    TextView tvCadastro;
    EditText edtEmail, edtSenha;
    Button btnEntrar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_login);

        toolbar = (Toolbar) findViewById(R.id.tbLoginCliente);

        setSupportActionBar(toolbar);

        setTitle("");
        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvCadastro = (TextView) findViewById(R.id.tvCadastro);
        edtEmail = (EditText) findViewById(R.id.edtEmailLoginCliente);
        edtSenha = (EditText) findViewById(R.id.edtSenhaLoginCliente);
        btnEntrar = (Button) findViewById(R.id.btnEntrarCliente);

        tvCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cliente_login.this, Cliente_cadastro.class);
                startActivity(intent);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtEmail.getText().toString().equals("") || edtSenha.getText().toString().equals(""))
                    Toast.makeText(Cliente_login.this, "Preecha todos os campos!", Toast.LENGTH_SHORT).show();
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())
                    Toast.makeText(Cliente_login.this, "Email incorreto", Toast.LENGTH_LONG).show();
                else
                    login();
            }
        });
    }
    public void login() {

        String strEmail = edtEmail.getText().toString();
        String strSenha = edtSenha.getText().toString();

        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<Cliente> call = service.buscarCliente(strEmail, strSenha);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if(response.isSuccessful())
                {
                    Intent intent = new Intent(Cliente_login.this, Home.class);
                    intent.putExtra("idCliente", response.body().getIdCliente());
                    intent.putExtra("SerializableCliente", (Serializable) response.body());
                    intent.putExtra("flag", "Cliente");
                    finish();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Cliente_login.this, "Dados inválidos!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
                Toast.makeText(Cliente_login.this, messageProblem, Toast.LENGTH_SHORT).show();
                Toast.makeText(Cliente_login.this, "Erro no login!", Toast.LENGTH_LONG).show();
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