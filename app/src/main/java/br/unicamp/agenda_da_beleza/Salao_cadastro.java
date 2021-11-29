package br.unicamp.agenda_da_beleza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Salao_cadastro extends AppCompatActivity {

    EditText edtNome, edtEmail, edtEndereco, edtTelefone, edtSenha;
    Button btnCadastrar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salao_cadastro);

        toolbar = (Toolbar) findViewById(R.id.tbCadastroSalao);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtNome = (EditText) findViewById(R.id.edtNomeSalaoCadastro);
        edtEmail = (EditText) findViewById(R.id.edtEmailSalaoCadastro);
        edtEndereco = (EditText) findViewById(R.id.edtEnderecoSalaoCadastro);
        edtTelefone = (EditText) findViewById(R.id.edtTelefoneSalaoCadastro);
        edtSenha = (EditText) findViewById(R.id.edtSenhaSalaoCadastro);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrarSalao);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNome.getText().toString().equals("") || edtEmail.getText().toString().equals("") || edtTelefone.getText().toString().equals("") || edtSenha.getText().toString().equals(""))
                    Toast.makeText(Salao_cadastro.this, "Preecnha todos os campos!", Toast.LENGTH_LONG).show();
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())
                    Toast.makeText(Salao_cadastro.this, "Email incorreto!", Toast.LENGTH_LONG).show();
                else
                    inserirSalao();
            }
        });
    }
    public void inserirSalao() {
        String strNome = edtNome.getText().toString();
        String strEmail = edtEmail.getText().toString();
        String strEndereco = edtEndereco.getText().toString();
        String strTelefone = edtTelefone.getText().toString();
        String strSenha = edtSenha.getText().toString();
        Salao salao = new Salao(strNome, strEmail, strEndereco, strTelefone, strSenha);

        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<Salao> call = service.incluirSalao(salao);

        call.enqueue(new Callback<Salao>() {
            @Override
            public void onResponse(Call<Salao> call, Response<Salao> responseSalao) {
                if (responseSalao.isSuccessful()) {
                    Servicos servico = new Servicos(responseSalao.body().getIdSalao(), 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                    Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                    Call<Servicos> callServico = service.incluirServico(servico);
                    callServico.enqueue(new Callback<Servicos>() {
                        @Override
                        public void onResponse(Call<Servicos> call, Response<Servicos> responseServico) {
                            if(responseServico.isSuccessful())
                            {
                                Intent intent = new Intent(Salao_cadastro.this, Home.class);
                                intent.putExtra("idCliente", responseSalao.body().getIdSalao());
                                intent.putExtra("SerializableSalao", (Serializable) responseSalao.body());
                                intent.putExtra("flag", "Salao");
                                finish();
                                startActivity(intent);
                                /*
                                FragmentManager fm = getSupportFragmentManager();
                                FragmentTransaction transaction = fm.beginTransaction();
                                transaction.replace(R.id.fragmentContainerView, BuscaFragment.newInstance(responseSalao.body().getIdSalao()));
                                transaction.commit();
                                Intent intent = new Intent(Salao_cadastro.this, Home.class);
                                intent.putExtra("idSalao", responseSalao.body().getIdSalao());
                                intent.putExtra("flag", "Salao");
                                finish();
                                startActivity(intent);*/
                            }
                        }

                        @Override
                        public void onFailure(Call<Servicos> call, Throwable t) {
                            Toast.makeText(Salao_cadastro.this, "Erro no cadastro do salão", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<Salao> call, Throwable t) {
                Toast.makeText(Salao_cadastro.this, "Erro no cadastro do salão", Toast.LENGTH_LONG).show();
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