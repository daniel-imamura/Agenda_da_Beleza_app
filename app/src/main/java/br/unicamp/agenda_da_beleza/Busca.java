package br.unicamp.agenda_da_beleza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Busca extends AppCompatActivity {

    EditText edtBusca;
    /*Toolbar toolbar;
    ImageView imvBusca, imvServico;
    ConstraintLayout layout;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);


/*
        toolbar = (Toolbar) findViewById(R.id.tbBusca);
        setSupportActionBar(toolbar);
        setTitle("");
*/
        //layout = (ConstraintLayout) findViewById(R.id.layoutBusca);
        //      imvBusca = (ImageView) findViewById(R.id.imvBusca);
        //    imvServico = (ImageView) findViewById(R.id.imvServico);
        edtBusca = (EditText) findViewById(R.id.edtBusca);

        //  if(getIntent().getExtras().getString("flag").equals("Cliente"))
        //    layout.removeView(imvServico);

        edtBusca.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == 66 && !edtBusca.getText().toString().equals("")) {
                    Intent getId = getIntent();

                    String strNome = edtBusca.getText().toString();

                    Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                    Call<Salao> call = service.buscarSalaoPorNome(strNome);
                    call.enqueue(new Callback<Salao>() {
                        @Override
                        public void onResponse(Call<Salao> call, Response<Salao> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(Busca.this, SaloesBuscados.class);
                                intent.putExtra("nomeSalao", strNome);
                                intent.putExtra("idCliente", getId.getExtras().getInt("idCliente"));
                                startActivity(intent);
                            } else
                                Toast.makeText(Busca.this, "Caiu no Else", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Salao> call, Throwable t) {
                            Toast.makeText(Busca.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return true;
            }
        });
/*
        imvBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imvServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Busca.this, CriarServicos.class);
                intent.putExtra("idSalao", getIntent().getExtras().getInt("idCliente"));
                startActivity(intent);
            }
        });
    }
*/
/*
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem favoriteItem = menu.findItem(R.id.itemCriarServicos);
        Drawable newIcon = (Drawable)favoriteItem.getIcon();
        newIcon.mutate().setColorFilter(getResources().getColor(R.color.darker_green), PorterDuff.Mode.SRC_IN);
        favoriteItem.setIcon(newIcon);

        if(getIntent().getExtras().getString("flag") == "Cliente")
            menu.removeItem(0);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemCriarServicos && getIntent().getExtras().getString("flag").equals("Salao"))
        {
            Intent intent = new Intent(Busca.this, CriarServicos.class);
            intent.putExtra("idSalao", getIntent().getExtras().getInt("idCliente"));
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }*/
    }
}