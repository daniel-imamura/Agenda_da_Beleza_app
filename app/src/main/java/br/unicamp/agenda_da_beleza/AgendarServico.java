package br.unicamp.agenda_da_beleza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendarServico extends AppCompatActivity {

    Button btnConfirmarAgendamento;
    Calendar calendario;
    TextView tvData, tvServicoNome, tvAgendar, tvPreco, tvLabelData;
    Toolbar toolbar;

    int ano, mes, dia, horas, minutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_servico);

        toolbar = (Toolbar) findViewById(R.id.tbAgendarServico);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvAgendar = (TextView) findViewById(R.id.tvAgendar);
        tvData = (TextView) findViewById(R.id.tvData);
        tvServicoNome = (TextView) findViewById(R.id.tvServicoNome);
        tvPreco = (TextView) findViewById(R.id.tvPreco);
        tvLabelData = (TextView) findViewById(R.id.tvLabelData);
        btnConfirmarAgendamento = (Button) findViewById(R.id.btnConfirmarAgendamento);

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        Intent intent = getIntent();
        tvServicoNome.setText(intent.getExtras().getString("nomeServico"));
        tvPreco.setText("R$ " + intent.getExtras().getString("precoServico"));

        tvAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendario = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(AgendarServico.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hours, int minute) {
                        calendario.set(ano, mes, dia);
                        if(calendario.AM_PM == Calendar.PM)
                            horas = hours + 12;
                        else
                            horas = hours;

                        minutos = minute;

                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String data = df.format(calendario.getTime());
                        String min = String.valueOf(minutos);
                        String hh = String.valueOf(horas);

                        if(min.length() < 2)
                            min = "0" + min;
                        if(hh.length() < 2)
                            hh = "0" + hh;

                        String dataFormatada = data.concat("\tàs\t" + hh + ":" + min);

                        tvLabelData.setText("Data:");
                        tvData.setText(dataFormatada);
                    }
                }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, false);
                timePickerDialog.show();

                DatePickerDialog datePickerDialog = new DatePickerDialog(AgendarServico.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        ano = year;
                        mes = month;
                        dia = day;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnConfirmarAgendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ano != 0 && mes != 0)
                {
                    int idCliente = intent.getExtras().getInt("idCliente");
                    int idSalao = intent.getExtras().getInt("idSalao");
                    String str = intent.getExtras().getString("nomeServico");

                    String strAno = String.valueOf(ano);
                    String strMes = String.valueOf(mes+1);
                    String strDia = String.valueOf(dia);
                    String strHoras = String.valueOf(horas);
                    String strMinutos = String.valueOf(minutos);

                    if (strAno.length() < 2)
                        strAno = "0" + strAno;
                    if (strMes.length() < 2)
                        strMes = "0" + strMes;
                    if (strDia.length() < 2)
                        strDia = "0" + strDia;
                    if (strHoras.length() < 2)
                        strHoras = "0" + strHoras;
                    if (strMinutos.length() < 2)
                        strMinutos = "0" + strMinutos;

                    String data = strAno + "-" + strMes + "-" + strDia + "T" + strHoras + ":" + strMinutos + ":" + "00";

                    Agendamento agenda = new Agendamento(idCliente, idSalao, formatarNomeServico(str), data);

                    /*
                    Toast.makeText(AgendarServico.this, String.valueOf(idCliente), Toast.LENGTH_SHORT).show();
                    Toast.makeText(AgendarServico.this, String.valueOf(idSalao), Toast.LENGTH_SHORT).show();
                    Toast.makeText(AgendarServico.this, formatarNomeServico(str), Toast.LENGTH_SHORT).show();*/
                    Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                    Call<Agendamento> call = service.incluirAgendamento(agenda);
                    call.enqueue(new Callback<Agendamento>() {
                        @Override
                        public void onResponse(Call<Agendamento> call, Response<Agendamento> response) {
                            if(response.isSuccessful())
                            {
                                finish();
                                onBackPressed();
                            }
                            else
                                Toast.makeText(AgendarServico.this, "NÂO", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Agendamento> call, Throwable t) {
                            Toast.makeText(AgendarServico.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
    private static String formatarNomeServico(String str)
    {
        String palavras[] = str.split("\\s");
        String palavrasMaiusculas = "";

        for(String palavra : palavras)
        {
            String primeiraLetra = palavra.substring(0, 1);
            String depoisDaPrimeiraLetra = palavra.substring(1);
            palavrasMaiusculas += primeiraLetra.toUpperCase() + depoisDaPrimeiraLetra + " ";
        }
        String semEspaco = palavrasMaiusculas.replace(" ", "");
        String formatado = semEspaco.replace(semEspaco.substring(0,1), semEspaco.substring(0, 1).toLowerCase());

        return formatado;
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