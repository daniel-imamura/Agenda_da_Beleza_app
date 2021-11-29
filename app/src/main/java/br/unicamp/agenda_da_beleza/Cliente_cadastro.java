package br.unicamp.agenda_da_beleza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cliente_cadastro extends AppCompatActivity {

    Button btnCadastrarCliente;

    EditText edtNome, edtEmail, edtTelefone, edtSenha;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_cadastro);

        toolbar = (Toolbar) findViewById(R.id.tbCadastroCliente);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        btnCadastrarCliente = (Button) findViewById(R.id.btnCadastrarCliente);

        edtNome = (EditText) findViewById(R.id.edtNomeCadastroCliente);
        edtEmail = (EditText) findViewById(R.id.edtEmailCadastroCliente);
        edtTelefone = (EditText) findViewById(R.id.edtTelefoneCadastroCliente);
        edtSenha = (EditText) findViewById(R.id.edtSenhaCadastroCliente);

        btnCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNome.getText().toString().equals("") || edtEmail.getText().toString().equals("") || edtTelefone.getText().toString().equals("") || edtSenha.getText().toString().equals(""))
                    Toast.makeText(Cliente_cadastro.this, "Preecnha todos os campos!", Toast.LENGTH_LONG).show();
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())
                    Toast.makeText(Cliente_cadastro.this, "Email incorreto", Toast.LENGTH_LONG).show();
                else
                    inserirCliente();
            }
        });

    }
    public void inserirCliente() {
        String strNome = edtNome.getText().toString();
        String strEmail = edtEmail.getText().toString();
        String strTelefone = edtTelefone.getText().toString();
        String strSenha = edtSenha.getText().toString();
        Cliente cliente = new Cliente(strNome, strEmail, strTelefone, strSenha);

        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<Cliente> call = service.incluirCliente(cliente);

        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(Cliente_cadastro.this, Home.class);
                    intent.putExtra("flag", "Cliente");
                    intent.putExtra("idCliente", response.body().getIdCliente());
                    intent.putExtra("SerializableCliente", (Serializable) response.body());

                    finish();
                    startActivity(intent);
                }
                else
                    Toast.makeText(Cliente_cadastro.this, "Dados inválidos!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Toast.makeText(Cliente_cadastro.this, "Erro no cadastro!", Toast.LENGTH_LONG).show();
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