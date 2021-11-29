package br.unicamp.agenda_da_beleza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaloesBuscados extends AppCompatActivity {

    EditText edtBusca;
    TextView tvNome;
    CardView cardSalao;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloes_buscados);

        toolbar = (Toolbar) findViewById(R.id.tbSaloesBuscados);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String strNome = intent.getExtras().getString("nomeSalao");

        cardSalao = (CardView) findViewById(R.id.cardSalao);
        tvNome = (TextView) findViewById(R.id.tvNomeSalao);
        edtBusca = (EditText) findViewById(R.id.edtBusca2);

        edtBusca.setText(strNome);
        tvNome.setText(strNome);

        edtBusca.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == 66 && !edtBusca.getText().toString().equals(""))
                {
                    String strNome = edtBusca.getText().toString();

                    Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                    Call<Salao> call = service.buscarSalaoPorNome(strNome);
                    call.enqueue(new Callback<Salao>() {
                        @Override
                        public void onResponse(Call<Salao> call, Response<Salao> response) {
                            if(response.isSuccessful())
                            {
                                tvNome.setText(response.body().getNome());
                                cardSalao.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                Toast.makeText(SaloesBuscados.this, "Não funcionou", Toast.LENGTH_LONG).show();
                                cardSalao.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onFailure(Call<Salao> call, Throwable t) {
                            Toast.makeText(SaloesBuscados.this, "Não funcionou, Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return true;
            }
        });
        cardSalao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrarSalao();
            }
        });
    }
    void entrarSalao()
    {
        String strNome = edtBusca.getText().toString();

        Intent getId = getIntent();

        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<Salao> call = service.buscarSalaoPorNome(strNome);
        call.enqueue(new Callback<Salao>() {
            @Override
            public void onResponse(Call<Salao> call, Response<Salao> response) {
                if(response.isSuccessful())
                {
                    Intent intent = new Intent(SaloesBuscados.this, Salao_info.class);
                    intent.putExtra("SerializableSalao", (Serializable) response.body());
                    intent.putExtra("idCliente", getId.getExtras().getInt("idCliente"));
                    startActivity(intent);
                }
                else
                    Toast.makeText(SaloesBuscados.this, "Não funcionou", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Salao> call, Throwable t) {
                Toast.makeText(SaloesBuscados.this, "Não funcionou, Failure", Toast.LENGTH_SHORT).show();
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