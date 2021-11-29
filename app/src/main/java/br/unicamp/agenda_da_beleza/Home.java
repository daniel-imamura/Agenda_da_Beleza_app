package br.unicamp.agenda_da_beleza;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.tbHome);

        setSupportActionBar(toolbar);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, BuscaFragment.newInstance(getIntent().getExtras().getInt("idCliente")));
        transaction.commit();
        //ServicosFragment.newInstance(29);
/*
        Bundle bundle = new Bundle();
        bundle.putInt("idSalao", 29);
        ServicosFragment fragment = (ServicosFragment) new Fragment();
        fragment.setArguments(bundle);
*/
        //FragmentManager fm = getFragmentManager();
        //ServicosFragment fragobj = new ServicosFragment();
        /*
        fm.beginTransaction()
                .replace(placeholder, fragment, tabId)
                .commit();*/
        //val graph = navInflater.inflate(R.navigation.nav_graph)
        /*
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavInflater navInflater = navController.getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.my_nav);

        NavArgument navArgument1=new NavArgument.Builder().setDefaultValue(29).build();
        graph.addArgument("idSalao", navArgument1);
        navController.setGraph(graph);
        ServicosFragment.newInstance(29);
        */


        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        bottomNavigationView = findViewById(R.id.bottomNV);

        if(getIntent().getExtras().getString("flag").equals("Cliente")) {
            bottomNavigationView.getMenu().removeItem(R.id.servicosFragment);
        }

        //bottomNavigationView.getMenu().getItem(R.id.servicosFragment).setIconTintList(ColorStateList.valueOf(Color.BLUE));
        //NavigationUI.setupWithNavController(toolbar, navController);
        //NavigationUI.setupActionBarWithNavController(Home.this, navController);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                int idCliente = getIntent().getExtras().getInt("idCliente");

                switch (item.getItemId()) {
                    case R.id.buscaFragment:
                        transaction.replace(R.id.fragmentContainerView, BuscaFragment.newInstance(idCliente));
                        break;
                    case R.id.servicosFragment:
                        transaction.replace(R.id.fragmentContainerView, ServicosFragment.newInstance(idCliente));
                        break;
                    case R.id.perfilFragment:
                        if(getIntent().getExtras().getString("flag").equals("Cliente"))
                            transaction.replace(R.id.fragmentContainerView, PerfilFragment.newInstance((Cliente) getIntent().getSerializableExtra("SerializableCliente"), "Cliente"));
                        else
                            transaction.replace(R.id.fragmentContainerView, PerfilFragment.newInstance((Salao) getIntent().getSerializableExtra("SerializableSalao"), "Salao"));
                        break;
                }
                transaction.commit();
/*
                if(getIntent().getExtras().getString("flag").equals("Cliente") && item.getItemId() == R.id.perfilFragment)
                {
                    transaction.replace(R.id.fragmentContainerView, PerfilFragment.newInstance((Cliente) getIntent().getSerializableExtra("SerializableCliente"), "Cliente"));
                    transaction.commit();
                }
                else
                {
                    transaction.replace(R.id.fragmentContainerView, PerfilFragment.newInstance((Salao) getIntent().getSerializableExtra("SerializableSalao"), "Salao"));
                    transaction.commit();
                }
*/

                return false;
            }
        });


        //NavigationUI.setupWithNavController(bottomNavigationView, navController);
        //NavigationUI.setupWithNavController(bottomNavigationView, navController);
/*
        MenuItem item = (MenuItem) findViewById(R.id.servicosFragment);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                    Bundle bundle = new Bundle();
                    ServicosFragment fragment = new ServicosFragment();
                    bundle.putInt("param1", 29);
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction();
                    //getFragmentManager().beginTransaction().add(.R.id.contenandroidt, bundle).commit();
                return false;
            }
        });*/
        /*
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

            }
        }, 10000);   */
        /*
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

            }
        });*/
    }
}