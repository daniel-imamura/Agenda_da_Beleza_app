package br.unicamp.agenda_da_beleza;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuscaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "idCliente";

    // TODO: Rename and change types of parameters
    private int idCliente;

    public BuscaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     *
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuscaFragment newInstance(int id) {
        BuscaFragment fragment = new BuscaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idCliente = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_busca, container, false);

        EditText edtBusca = view.findViewById(R.id.edtBusca);

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
                                        Intent intent = new Intent(getContext(), SaloesBuscados.class);
                                        intent.putExtra("nomeSalao", strNome);
                                        intent.putExtra("idCliente", idCliente);
                                        startActivity(intent);
                                    }
                                    else
                                        Toast.makeText(getContext(), "Caiu no Else", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Salao> call, Throwable t) {
                                    Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        return true;
                    }
                });

        return view;
    }
}