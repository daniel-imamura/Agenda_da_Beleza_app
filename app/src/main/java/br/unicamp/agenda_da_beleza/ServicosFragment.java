package br.unicamp.agenda_da_beleza;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServicosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServicosFragment extends Fragment {

    Button btnConfirmarValor, btnTerminar;
    Spinner dropdown;
    EditText edtValor;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "idSalao";

    // TODO: Rename and change types of parameters
    private int idSalao;

    public ServicosFragment() {
        // Required empty public constructor
    }
/*
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);
    }*/

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     *
     * @return A new instance of fragment ServicosFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static ServicosFragment newInstance(int id) {
        ServicosFragment fragment = new ServicosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idSalao = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_servicos, container, false);

        btnConfirmarValor = (Button) view.findViewById(R.id.btnConfirmarValor);
        btnTerminar = (Button) view.findViewById(R.id.btnTerminar);
        dropdown = (Spinner) view.findViewById(R.id.spinServicos);
        edtValor = (EditText) view.findViewById(R.id.edtValor);

        String[] items = new String[]{"Alongamento de Cílios", "Maquiagem", "Tintura", "Corte de Cabelo", "Penteado", "Progressiva", "Luzes", "Limpeza de Pele", "Design de Sobrancelha", "Hidratação", "Unha"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
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
                            Toast.makeText(getContext(), "Erro ao buscar valor", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Servicos> call, Throwable t) {
                        Toast.makeText(getContext(), "Erro ao buscar valor", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                int idSalao = getArguments().getInt("idSalao");

                //int idSalao = 29;
                Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                Call<Servicos> call = service.alterarServico(idSalao, Float.parseFloat(edtValor.getText().toString()), (int) dropdown.getSelectedItemId());
                call.enqueue(new Callback<Servicos>() {
                    @Override
                    public void onResponse(Call<Servicos> call, Response<Servicos> response) {
                        if(response.isSuccessful())
                            Toast.makeText(getContext(), "Valor inserido!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(), "Valor inválido!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Servicos> call, Throwable t) {
                        Toast.makeText(getContext(), "Erro na inserção!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;
    }
}