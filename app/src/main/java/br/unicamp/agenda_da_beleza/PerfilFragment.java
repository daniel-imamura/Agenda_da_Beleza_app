package br.unicamp.agenda_da_beleza;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String nomeUsuario = "nome";
    private static final String emailUsuario = "email";
    private static final String telefoneUsuario = "telefone";
    private static final String senhaUsuario = "senha";
    private static final String enderecoUsuario = "endereco";
    private static final String idUsuario = "id";
    private static final String flagUsuario = "flag";

    // TODO: Rename and change types of parameters
    private String flag;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private String endereco;
    private int id;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cliente Parameter 1.
     * @param flag Parameter 2
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(Cliente cliente, String flag) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(flagUsuario, flag);
        args.putInt(idUsuario, cliente.getIdCliente());
        args.putString(nomeUsuario, cliente.getNome());
        args.putString(emailUsuario, cliente.getEmail());
        args.putString(telefoneUsuario, cliente.getTelefone());
        args.putString(senhaUsuario, cliente.getSenha());
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * @param salao Parameter 1
     * @param flag Parameter 2
     * @return A new instance of fragment ProfileFragment.7
     * */
    public static PerfilFragment newInstance(Salao salao, String flag) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(flagUsuario, flag);
        args.putInt(idUsuario, salao.getIdSalao());
        args.putString(nomeUsuario, salao.getNome());
        args.putString(emailUsuario, salao.getEmail());
        args.putString(telefoneUsuario, salao.getTelefone());
        args.putString(senhaUsuario, salao.getSenha());
        args.putString(enderecoUsuario, salao.getEndereco());
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            flag = getArguments().getString(flagUsuario);
            id = getArguments().getInt(idUsuario);
            nome = getArguments().getString(nomeUsuario);
            email = getArguments().getString(emailUsuario);
            telefone = getArguments().getString(telefoneUsuario);
            senha = getArguments().getString(senhaUsuario);
            endereco = getArguments().getString(enderecoUsuario);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        ConstraintLayout layout = (ConstraintLayout) view.findViewById(R.id.layoutPerfil);

        EditText edtNome = (EditText) view.findViewById(R.id.edtNomePerfil);
        EditText edtEmail = (EditText) view.findViewById(R.id.edtEmailPerfil);
        EditText edtTelefone = (EditText) view.findViewById(R.id.edtTelefonePerfil);
        EditText edtSenha = (EditText) view.findViewById(R.id.edtSenhaPerfil);
        EditText edtEndereco = (EditText) view.findViewById(R.id.edtEnderecoPerfil);

        ImageView icNome = (ImageView) view.findViewById(R.id.icNome);
        ImageView icEmail = (ImageView) view.findViewById(R.id.icEmail);
        ImageView icTelefone = (ImageView) view.findViewById(R.id.icTelefone);
        ImageView icSenha = (ImageView) view.findViewById(R.id.icSenha);
        ImageView icEndereco = (ImageView) view.findViewById(R.id.icEndereco);

        TextView tvEndereco = (TextView) view.findViewById(R.id.tvEnderecoPerfil);

        edtNome.setText(nome);
        edtEmail.setText(email);
        edtTelefone.setText(telefone);
        edtSenha.setText(senha);
        edtEndereco.setText(endereco);

        edtNome.setEnabled(false);
        edtEmail.setEnabled(false);
        edtTelefone.setEnabled(false);
        edtSenha.setEnabled(false);
        edtEndereco.setEnabled(false);

        if(flag.equals("Cliente"))
        {
            layout.removeView(edtEndereco);
            layout.removeView(icEndereco);
            layout.removeView(tvEndereco);
        }

        icNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtNome.isEnabled()) {
                    edtNome.setEnabled(true);
                    edtNome.requestFocus();
                    edtNome.setSelection(edtNome.getText().length());
                    edtNome.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material);
                    icNome.setPadding(0, 0, 0, 10);
                    icNome.setImageResource(R.drawable.ic_check);
                }
                else if(!edtNome.getText().toString().equals(""))
                {
                    nome = edtNome.getText().toString();
                    edtNome.setEnabled(false);
                    edtNome.setPadding(0, 0, 0, 0);
                    edtNome.setBackgroundResource(android.R.color.transparent);
                    edtNome.setSelection(0);
                    icNome.setImageResource(R.drawable.ic_edit);

                    if(flag.equals("Cliente"))
                    {
                        Cliente cliente = new Cliente(nome, email, telefone, senha);
                        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                        Call<Cliente> call = service.alterarCliente(id, cliente);
                        call.enqueue(new Callback<Cliente>() {
                            @Override
                            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getContext(), "Nome atualizado!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Cliente> call, Throwable t) {
                                Toast.makeText(getContext(), "Failure!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        Salao salao = new Salao(nome, email, endereco, telefone, senha);
                        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                        Call<Salao> call = service.alterarSalao(id, salao);
                        call.enqueue(new Callback<Salao>() {
                            @Override
                            public void onResponse(Call<Salao> call, Response<Salao> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getContext(), "Nome atualizado!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Salao> call, Throwable t) {
                                Toast.makeText(getContext(), "Failure!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
                else
                    Toast.makeText(getContext(), "Nome inválido!", Toast.LENGTH_SHORT).show();
            }
        });
        icEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtEmail.isEnabled())
                {
                    edtEmail.setEnabled(true);
                    edtEmail.requestFocus();
                    edtEmail.setSelection(edtEmail.getText().length());
                    edtEmail.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material);
                    icEmail.setPadding(0, 0, 0, 10);
                    icEmail.setImageResource(R.drawable.ic_check);
                }
                else if(android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())
                {
                    email = edtEmail.getText().toString();
                    edtEmail.setEnabled(false);
                    edtEmail.setPadding(0, 0, 0, 0);
                    edtEmail.setBackgroundResource(android.R.color.transparent);
                    edtEmail.setSelection(0);
                    icEmail.setImageResource(R.drawable.ic_edit);


                    if(flag.equals("Cliente"))
                    {
                        Cliente cliente = new Cliente(nome, email, telefone, senha);
                        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                        Call<Cliente> call = service.alterarCliente(id, cliente);
                        call.enqueue(new Callback<Cliente>() {
                            @Override
                            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getContext(), "Email atualizado!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Cliente> call, Throwable t) {
                                Toast.makeText(getContext(), "Failure!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        Salao salao = new Salao(nome, email, endereco, telefone, senha);
                        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                        Call<Salao> call = service.alterarSalao(id, salao);
                        call.enqueue(new Callback<Salao>() {
                            @Override
                            public void onResponse(Call<Salao> call, Response<Salao> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getContext(), "Email atualizado!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Salao> call, Throwable t) {
                                Toast.makeText(getContext(), "Failure!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else
                    Toast.makeText(getContext(), "Email inválido!", Toast.LENGTH_SHORT).show();
            }
        });
        icTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtTelefone.isEnabled())
                {
                    edtTelefone.setEnabled(true);
                    edtTelefone.requestFocus();
                    edtTelefone.setSelection(edtTelefone.getText().length());
                    edtTelefone.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material);
                    icTelefone.setPadding(0, 0, 0, 10);
                    icTelefone.setImageResource(R.drawable.ic_check);
                }
                else if(!edtTelefone.getText().toString().equals(""))
                {
                    telefone = edtTelefone.getText().toString();
                    edtTelefone.setEnabled(false);
                    edtTelefone.setPadding(0, 0, 0, 0);
                    edtTelefone.setBackgroundResource(android.R.color.transparent);
                    edtTelefone.setSelection(0);
                    icTelefone.setImageResource(R.drawable.ic_edit);

                    if(flag.equals("Cliente"))
                    {
                        Cliente cliente = new Cliente(nome, email, telefone, senha);
                        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                        Call<Cliente> call = service.alterarCliente(id, cliente);
                        call.enqueue(new Callback<Cliente>() {
                            @Override
                            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getContext(), "Email atualizado!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Cliente> call, Throwable t) {
                                Toast.makeText(getContext(), "Failure!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        Salao salao = new Salao(nome, email, endereco, telefone, senha);
                        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                        Call<Salao> call = service.alterarSalao(id, salao);
                        call.enqueue(new Callback<Salao>() {
                            @Override
                            public void onResponse(Call<Salao> call, Response<Salao> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getContext(), "Email atualizado!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Salao> call, Throwable t) {
                                Toast.makeText(getContext(), "Failure!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else
                    Toast.makeText(getContext(), "telefone inválido!", Toast.LENGTH_SHORT).show();
            }
        });
        icSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtSenha.isEnabled())
                {
                    edtSenha.setEnabled(true);
                    edtSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtSenha.requestFocus();
                    edtSenha.setSelection(edtSenha.getText().length());
                    edtSenha.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material);
                    icSenha.setPadding(0, 0, 0, 10);
                    icSenha.setImageResource(R.drawable.ic_check);
                }
                else if(!edtSenha.getText().toString().equals(""))
                {
                    senha = edtSenha.getText().toString();
                    edtSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtSenha.setEnabled(false);
                    edtSenha.setPadding(0, 0, 0, 0);
                    edtSenha.setBackgroundResource(android.R.color.transparent);
                    edtSenha.setSelection(0);
                    icSenha.setImageResource(R.drawable.ic_edit);

                    if(flag.equals("Cliente"))
                    {
                        Cliente cliente = new Cliente(nome, email, telefone, senha);
                        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                        Call<Cliente> call = service.alterarCliente(id, cliente);
                        call.enqueue(new Callback<Cliente>() {
                            @Override
                            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getContext(), "Senha atualizado!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Cliente> call, Throwable t) {
                                Toast.makeText(getContext(), "Failure!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        Salao salao = new Salao(nome, email, endereco, telefone, senha);
                        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                        Call<Salao> call = service.alterarSalao(id, salao);
                        call.enqueue(new Callback<Salao>() {
                            @Override
                            public void onResponse(Call<Salao> call, Response<Salao> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getContext(), "Senha atualizado!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Salao> call, Throwable t) {
                                Toast.makeText(getContext(), "Failure!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else
                    Toast.makeText(getContext(), "senha inválida", Toast.LENGTH_SHORT).show();
            }
        });

        icEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtEndereco.isEnabled())
                {
                    edtEndereco.setEnabled(true);
                    edtEndereco.requestFocus();
                    edtEndereco.setSelection(edtEndereco.getText().length());
                    edtEndereco.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material);
                    icEndereco.setPadding(0, 0, 0, 10);
                    icEndereco.setImageResource(R.drawable.ic_check);
                }
                else if(!edtEndereco.getText().toString().equals(""))
                {
                    endereco = edtEndereco.getText().toString();
                    edtEndereco.setEnabled(false);
                    edtEndereco.setPadding(0, 0, 0, 0);
                    edtEndereco.setBackgroundResource(android.R.color.transparent);
                    edtEndereco.setSelection(0);
                    icEndereco.setImageResource(R.drawable.ic_edit);

                    Salao salao = new Salao(nome, email, endereco, telefone, senha);
                    Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                    Call<Salao> call = service.alterarSalao(id, salao);
                    call.enqueue(new Callback<Salao>() {
                        @Override
                        public void onResponse(Call<Salao> call, Response<Salao> response) {
                            if(response.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Endereço atualizado!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Salao> call, Throwable t) {
                            Toast.makeText(getContext(), "Failure!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                    Toast.makeText(getContext(), "Endereço inválido", Toast.LENGTH_SHORT).show();
            }
        });
        /*
        tvNome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (tvNome.getRight() - tvNome.getCompoundDrawables()[2].getBounds().width())) {
                        Toast.makeText(getContext(), "Clicou!", Toast.LENGTH_SHORT).show();
                        tvNome.setEnabled(true);
                        return true;
                    }
                }
                return false;
            }
        });*/
        return view;
    }
}