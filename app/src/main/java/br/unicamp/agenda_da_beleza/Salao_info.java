package br.unicamp.agenda_da_beleza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Salao_info extends AppCompatActivity {

    TextView tvNome, tvEndereco, tvTelefone;
    LinearLayout layout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salao_info);

        toolbar = (Toolbar) findViewById(R.id.tbSalaoInfo);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentSalao = getIntent();
        Salao salao =  (Salao) intentSalao.getSerializableExtra("SerializableSalao");

        tvNome = (TextView) findViewById(R.id.tvNomeSalao);
        tvEndereco = (TextView) findViewById(R.id.tvEnderecoSalao);
        tvTelefone = (TextView) findViewById(R.id.tvTelefoneSalao);
        layout = (LinearLayout) findViewById(R.id.layout);

        tvNome.setText(salao.getNome());
        tvEndereco.setText(salao.getEndereco());
        tvTelefone.setText(salao.getTelefone());

        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<Servicos> call = service.buscarServicoPorIdSalao(salao.getIdSalao());
        call.enqueue(new Callback<Servicos>() {
            @Override
            public void onResponse(Call<Servicos> call, Response<Servicos> response) {
                if(response.isSuccessful())
                {
                    ArrayList<Float> listaDeValores = response.body().checarServicos();

                    for(int i=0; i<=10; i++)
                    {
                        if(listaDeValores.get(i) != 0)
                        {
                            View linha = new View(Salao_info.this);
                            LinearLayout.LayoutParams layoutParamsLinha = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                            linha.setLayoutParams(layoutParamsLinha);
                            linha.setBackgroundColor(Color.LTGRAY);
                            layout.addView(linha);

                            TextView textView = new TextView(Salao_info.this);

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(60,10,30,10);
                            textView.setLayoutParams(params);
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(18);

                            int finalI = i;
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Salao_info.this, AgendarServico.class);
                                    String nomeServico = textView.getText().toString();
                                    int indiceDoisPontos = nomeServico.indexOf(":");
                                    String formatado = nomeServico.substring(0, indiceDoisPontos);

                                    intent.putExtra("nomeServico", formatado);
                                    intent.putExtra("precoServico", response.body().formatarValor(listaDeValores.get(finalI)));
                                    intent.putExtra("idCliente", intentSalao.getExtras().getInt("idCliente"));
                                    intent.putExtra("idSalao", salao.getIdSalao());
                                    startActivity(intent);
                                }
                            });
                            switch (i)
                            {
                                case 0:
                                    textView.setText("Alongamento de Cílios:\t\t" + "R$ " + response.body().formatarValor(listaDeValores.get(i)));
                                    break;
                                case 1:
                                    textView.setText("Maquiagem:\t" + "R$ " + response.body().formatarValor(listaDeValores.get(i)));
                                    break;
                                case 2:
                                    textView.setText("Tintura:\t " + "R$ " + response.body().formatarValor(listaDeValores.get(i)));
                                    break;
                                case 3:
                                    textView.setText("Corte de Cabelo:\t " + "R$ " + response.body().formatarValor(listaDeValores.get(i)));
                                    break;
                                case 4:
                                    textView.setText("Penteado:\t" + "R$ " + response.body().formatarValor(listaDeValores.get(i)));
                                    break;
                                case 5:
                                    textView.setText("Progressiva:\t" + "R$ " + response.body().formatarValor(listaDeValores.get(i)));
                                    break;
                                case 6:
                                    textView.setText("Luzes:\t" + "R$ " + response.body().formatarValor(listaDeValores.get(i)));
                                    break;
                                case 7:
                                    textView.setText("Limpeza de Pele:\t" + "R$ " + response.body().formatarValor(listaDeValores.get(i)));
                                    break;
                                case 8:
                                    textView.setText("Design de Sobrancelha:\t" + "R$ " + response.body().formatarValor(listaDeValores.get(i)));
                                    break;
                                case 9:
                                    textView.setText("Hidratação:\t" + "R$ " + response.body().formatarValor(listaDeValores.get(i)));
                                    break;
                                case 10:
                                    textView.setText("Unha:\t" + "R$ " + response.body().formatarValor(listaDeValores.get(i)));
                                    break;
                            }
                            layout.addView(textView);
                        }
                    }
                    View linha = new View(Salao_info.this);
                    LinearLayout.LayoutParams layoutParamsLinha = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    linha.setLayoutParams(layoutParamsLinha);
                    linha.setBackgroundColor(Color.LTGRAY);
                    layout.addView(linha);
                }
            }

            @Override
            public void onFailure(Call<Servicos> call, Throwable t) {

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