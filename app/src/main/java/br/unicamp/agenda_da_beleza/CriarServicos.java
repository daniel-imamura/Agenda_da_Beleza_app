package br.unicamp.agenda_da_beleza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.math.BigDecimal;
import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CriarServicos extends AppCompatActivity {

    Button btnConfirmarValor, btnTerminar;
    Spinner dropdown;
    EditText edtValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_servicos);

        btnConfirmarValor = (Button) findViewById(R.id.btnConfirmarValor);
        btnTerminar = (Button) findViewById(R.id.btnTerminar);
        dropdown = (Spinner) findViewById(R.id.spinServicos);
        edtValor = (EditText) findViewById(R.id.edtValor);

        String[] items = new String[]{"Alongamento de Cílios", "Maquiagem", "Tintura", "Corte de Cabelo", "Penteado", "Progressiva", "Luzes", "Limpeza de Pele", "Design de Sobrancelha", "Hidratação", "Unha"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Intent intent = getIntent();

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                int idSalao = intent.getExtras().getInt("idSalao");

                Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                Call<Servicos> call = service.buscarServicoPorIdSalao(idSalao);
                call.enqueue(new Callback<Servicos>() {
                    @Override
                    public void onResponse(Call<Servicos> call, Response<Servicos> response) {
                        if(response.isSuccessful())
                        {
                            switch ((int)id)
                            {
                                case 0:
                                    edtValor.setText(String.valueOf(response.body().getAlongamentoDeCilios()));
                                    break;
                                case 1:
                                    edtValor.setText(String.valueOf(response.body().getMaquiagem()));
                                    break;
                                case 2:
                                    edtValor.setText(String.valueOf(response.body().getTintura()));
                                    break;
                                case 3:
                                    edtValor.setText(String.valueOf(response.body().getCorteDeCabelo()));
                                    break;
                                case 4:
                                    edtValor.setText(String.valueOf(response.body().getPenteado()));
                                    break;
                                case 5:
                                    edtValor.setText(String.valueOf(response.body().getProgressiva()));
                                    break;
                                case 6:
                                    edtValor.setText(String.valueOf(response.body().getLuzes()));
                                    break;
                                case 7:
                                    edtValor.setText(String.valueOf(response.body().getLimpezaDePele()));
                                    break;
                                case 8:
                                    edtValor.setText(String.valueOf(response.body().getDesignDeSobrancelha()));
                                    break;
                                case 9:
                                    edtValor.setText(String.valueOf(response.body().getHidratacao()));
                                    break;
                                case 10:
                                    edtValor.setText(String.valueOf(response.body().getUnha()));
                                    break;
                                default:
                                    break;
                            }
                        }
                        else
                            Toast.makeText(CriarServicos.this, "Erro ao buscar valor", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Servicos> call, Throwable t) {
                        Toast.makeText(CriarServicos.this, "Erro ao buscar valor", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CriarServicos.this, Busca.class);
                finish();
                startActivity(intent);
            }
        });

        edtValor.addTextChangedListener(new TextWatcher() {
            boolean _ignore = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(_ignore)
                    return;

                _ignore = true;

                String str = edtValor.getText().toString();
                int indicePonto = str.indexOf(".");
                if(indicePonto != -1)
                {
                    String subPosPonto = str.substring(indicePonto);
                    if(subPosPonto.length() > 3)
                    {
                        String sub = str.substring(0, indicePonto + 3);
                        edtValor.setText(sub);
                        edtValor.setSelection(sub.length());
                    }
                }

                _ignore = false;
            }
        });

        btnConfirmarValor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idSalao = intent.getExtras().getInt("idSalao");

                Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                Call<Servicos> call = service.alterarServico(idSalao, Float.parseFloat(edtValor.getText().toString()), (int) dropdown.getSelectedItemId());
                call.enqueue(new Callback<Servicos>() {
                    @Override
                    public void onResponse(Call<Servicos> call, Response<Servicos> response) {
                        if(response.isSuccessful())
                            Toast.makeText(CriarServicos.this, "Valor inserido!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(CriarServicos.this, "Valor inválido!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Servicos> call, Throwable t) {
                        Toast.makeText(CriarServicos.this, "Erro na inserção!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}