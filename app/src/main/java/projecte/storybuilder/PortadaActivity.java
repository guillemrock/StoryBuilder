package projecte.storybuilder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    private Button boton_izq, boton_der,boton_empezar,boton_anadirNombre;
    private EditText editText;

    private ViewPager viewpager;
    private PagerAdapter mPagerAdapter;
    private String Nombre = "";

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
        boton_empezar = findViewById(R.id.btn_empezar);
        boton_anadirNombre = findViewById(R.id.btn_anadirNombre);
        editText = findViewById(R.id.editText);
		
        boton_empezar.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
				paginaSiguiente("1", true);
				texto = findViewById(R.id.tV_conBotones);
				boton_der = findViewById(R.id.boton_der);
				boton_izq = findViewById(R.id.boton_izq);
				Pagina p1=libro.buscaPagina("1");
				Fragment f = PaginaBotonesFragment.newInstance(p1.getId(), p1.getTexto(),p1.getBoton_der(),p1.getBoton_izq(), true);
				texto.setText(p1.getTexto());
				boton_izq.setText(p1.getBoton_izq().getTexto());
				boton_der.setText(p1.getBoton_der().getTexto());
				boton_empezar.setVisibility(View.GONE);
				boton_anadirNombre.setVisibility(View.GONE);
				editText.setVisibility(View.GONE);
				ViewGroup.MarginLayoutParams marginparams = (ViewGroup.MarginLayoutParams) viewpager.getLayoutParams();
				marginparams.bottomMargin = 0;
             }
         });
        boton_anadirNombre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Nombre = editText.getText().toString();
            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                //code here
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //code here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {
                    String idPag = secuenciaPaginas.get(viewpager.getCurrentItem());
                    Pagina pag = libro.buscaPagina(idPag);
                    String id = pag.getId();
                    if (!pag.getBotonesActivos()){
                        boton_der = findViewById(R.id.boton_der);
                        boton_izq = findViewById(R.id.boton_izq);
                        boton_izq.setVisibility(View.INVISIBLE);
                        boton_der.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

/*    public void BorraDesdeId(String idPag){
        int index = 0;
        for (int i=0;i<secuenciaPaginas.size();i++){
            if (secuenciaPaginas.get(i).contentEquals(idPag))
                index= i;
        }
        while(secuenciaPaginas.size()>index+1)
            secuenciaPaginas.remove(secuenciaPaginas.size()-1);
        mPagerAdapter.notifyDataSetChanged();
        viewpager.setCurrentItem(secuenciaPaginas.size()-1, true);
    }
*/
    public void ActivarBotones(String idPag, boolean estado){
        Pagina pag = libro.buscaPagina(idPag);
        pag.ActivarBotones(estado);
    }

    public void paginaSiguiente(String idPag, boolean changePage) {
        if (!secuenciaPaginas.contains(idPag))
            secuenciaPaginas.add(idPag);
        mPagerAdapter.notifyDataSetChanged();
        if (changePage)
            viewpager.setCurrentItem(secuenciaPaginas.size()-1, true);
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
            switch (pag.getTipo()){
                case 0:
                case 2:
                    return PaginaSinBotonesFragment.newInstance(pag.getId(), pag.getTexto(),pag.getIdTarget());
                    //break;
                case 1:
                    return PaginaBotonesFragment.newInstance(pag.getId(), pag.getTexto(), pag.getBoton_der(), pag.getBoton_izq(), pag.getBotonesActivos());
                    //break;
                default:
                    return new Fragment();
                    //break;
            }
        }

        @Override
        public int getCount(){
            return secuenciaPaginas.size();
        }
    }

    private void cargaLibro() {
        try {
            InputStream stream = getAssets().open("caperucita.json");
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
				pagina.setIdTarget(pagina_json.getString("id_target"));
                pagina.setTipo(pagina_json.getInt("tipo"));

                if (pagina_json.has("boton")) {
                    JSONArray botones = pagina_json.getJSONArray("boton");

                    JSONObject boton0 = botones.getJSONObject(0);
                    pagina.setBoton_izq(new Boton(boton0.getString("texto_btn_izq"), boton0.getString("idTarget_izq")));
                    JSONObject boton1 = botones.getJSONObject(1);
                    pagina.setBoton_der(new Boton(boton1.getString("texto_btn_der"), boton1.getString("idTarget_der")));
                    pagina.ActivarBotones(true);
                }
                Log.i("StoryBuilder", "Pagina " + pagina.getId());

                libro.addPagina(pagina);
                libro.buscaPagina("id");
            }
        }
        catch (IOException e) {
            Toast.makeText(this, "No he podido leer el archivo json", Toast.LENGTH_SHORT)
                    .show();
        }
        catch (JSONException e) {
            Log.e("StoryBuilder", "Format JSON malament: ", e);
            Toast.makeText(this, "ERROR de JSON", Toast.LENGTH_SHORT).show();
        }
    }
}
