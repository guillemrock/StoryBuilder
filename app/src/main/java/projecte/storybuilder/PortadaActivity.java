package projecte.storybuilder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PortadaActivity extends FragmentActivity {

    private Boton btn_izq, btn_der;
    private Libro libro;
    private List<String> secuenciaPaginas;

    private TextView texto;
    private Button boton_izq, boton_der;
    private ViewPager viewpager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);


        libro = new Libro();
        secuenciaPaginas = new ArrayList<>();
        secuenciaPaginas.add("0");


        cargaLibro();

        viewpager=findViewById(R.id.viewpager);
        mPagerAdapter=new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mPagerAdapter);

        /*
        texto = findViewById(R.id.texto);
        boton_der = findViewById(R.id.boton_der);
        boton_izq = findViewById(R.id.boton_izq);

        Pagina p0=libro.buscaPagina("1");
        texto.setText(p0.getTexto());
        boton_izq.setText(p0.getBoton_izq());
        boton_der.setText(p0.getBoton_der());
        */

        // Fragment f = PaginaBotonesFragment.newInstance("titlulo", "derecha", "izquierda");
    }

    public void paginaSiguiente(String idPag) {
        secuenciaPaginas.add(idPag);
        mPagerAdapter.notifyDataSetChanged();
        viewpager.setCurrentItem(secuenciaPaginas.size(), true);
    }

    @Override
    public void onBackPressed(){
        if(viewpager.getCurrentItem()==0){
            super.onBackPressed();
        }else{
            viewpager.setCurrentItem(viewpager.getCurrentItem()-1);
        }
    }
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{
        public ScreenSlidePagerAdapter (FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem (int position) {
            String idPag = secuenciaPaginas.get(position);
            Pagina pag = libro.buscaPagina(idPag);
            if (pag.getBoton_der() == null && pag.getBoton_izq() == null) {
                return PaginaSinBotonesFragment.newInstance(pag.getTexto());
            } else {
                return PaginaBotonesFragment.newInstance(pag.getTexto(), pag.getBoton_der(), pag.getBoton_izq());
            }
        }

        @Override
        public int getCount(){
            return secuenciaPaginas.size();
        }
    }

    private void cargaLibro() {
        try {
            InputStream stream = getAssets().open("pagina.json");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader in = new BufferedReader(reader);
            String str;
            StringBuilder buf = new StringBuilder();
            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
            in.close();
            JSONTokener tokener = new JSONTokener(buf.toString());
            JSONObject root = (JSONObject) tokener.nextValue();
            JSONArray paginas = root.getJSONArray("Paginas");

            for(int i=0; i<paginas.length(); i++){
                JSONObject pagina_json = paginas.getJSONObject(i);
                Pagina pagina = new Pagina();
                pagina.setId(pagina_json.getString("id"));
                pagina.setTexto(pagina_json.getString("texto"));

                if (pagina_json.has("boton")) {
                    JSONArray botones = pagina_json.getJSONArray("boton");

                    JSONObject boton0 = botones.getJSONObject(0);
                    pagina.setBoton_izq(new Boton(boton0.getString("texto_btn_izq"), boton0.getString("idTarget_izq")));
                    JSONObject boton1 = botones.getJSONObject(1);
                    pagina.setBoton_der(new Boton(boton1.getString("texto_btn_der"), boton1.getString("idTarget_der")));
                }
                Log.i("StoryBuilder", "Pagina " + pagina.getId());

                libro.addPagina(pagina);
                libro.buscaPagina("id");
            }
        }
        catch (IOException e) {
            Toast.makeText(this, "No he podido leer lord.json", Toast.LENGTH_SHORT)
                    .show();
        }
        catch (JSONException e) {
            Log.e("StoryBuilder", "Format JSON malament: ", e);
            Toast.makeText(this, "ERROR de JSON", Toast.LENGTH_SHORT).show();
        }
    }
}
