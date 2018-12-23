package projecte.storybuilder;

import android.content.Context;
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
import android.widget.RadioButton;
import android.view.Gravity;
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
                Fragment f = PaginaBotonesFragment.newInstance(p1.getId(), p1.getTexto(),p1.getBoton_der(),p1.getBoton_izq(), 0);
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
                actualizaEstados();
            }
        });
    }

    public void actualizaEstados(){
        String idPag = secuenciaPaginas.get(viewpager.getCurrentItem());
        Pagina pag = libro.buscaPagina(idPag);
        String id = pag.getId();
        if (pag.getTipo() == 1){
            boton_der = findViewById(R.id.boton_der);
            boton_izq = findViewById(R.id.boton_izq);
            texto = findViewById(R.id.tV_conBotones);
            if ((boton_der.getVisibility()==View.VISIBLE) &&  (pag.getBotonesActivos() >0)){
                boton_izq.setVisibility(View.INVISIBLE);
                boton_der.setVisibility(View.INVISIBLE);
                if (pag.getBotonesActivos() == 1) {
                    texto.append("\n\n\n\n" + boton_izq.getText() + "   (ELEGIDO)\n");
                    texto.append(boton_der.getText());
                }
                if (pag.getBotonesActivos() == 2) {
                    texto.append("\n\n\n\n" + boton_izq.getText() + "\n");
                    texto.append(boton_der.getText() + "   (ELEGIDO)\n");
                }
            }
        }
        if (pag.getTipo() == 2){
            paginaSiguiente(pag.getIdTarget(),false);
        }
        if (pag.getTipo() == 3){
            TextView textView = findViewById(R.id.tV_pregunta);
            Button btn = findViewById(R.id.check_btn);
            RadioButton r1 = findViewById(R.id.respuesta1);
            RadioButton r2 = findViewById(R.id.respuesta2);
            RadioButton r3 = findViewById(R.id.respuesta3);
            if ((btn.getVisibility()==View.VISIBLE) &&  (pag.getPreguntaOK() >0)){
                //textView.setText(textView.getText()+"\n\nMuy bien!  Pasa de página " + Nombre);
                btn.setVisibility(View.INVISIBLE);
                if (pag.getPreguntaOK() == 1) {
                    r2.setVisibility(View.INVISIBLE);
                    r3.setVisibility(View.INVISIBLE);
                }
                if (pag.getPreguntaOK() == 2) {
                    r1.setVisibility(View.INVISIBLE);
                    r3.setVisibility(View.INVISIBLE);
                }
                if (pag.getPreguntaOK() == 3) {
                    r1.setVisibility(View.INVISIBLE);
                    r2.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public void ActivarBotones(String idPag, int estado){
        Pagina pag = libro.buscaPagina(idPag);
        pag.ActivarBotones(estado);
        actualizaEstados();
    }

    public void setPreguntaOK(String idPag, int ok){
        Pagina pag = libro.buscaPagina(idPag);
        pag.setPreguntaOK(ok);
        actualizaEstados();
    }

    public int getPreguntaOK(String idPag){
        Pagina pag = libro.buscaPagina(idPag);
        return pag.getPreguntaOK();
    }

    public void muestraToast(String texto, int duracion){
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, texto, duracion);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public String getNombre(){
        return Nombre;
    }

    public void paginaSiguiente(String idPag, boolean changePage) {
        if (!secuenciaPaginas.contains(idPag)){
            secuenciaPaginas.add(idPag);
            mPagerAdapter.notifyDataSetChanged();
            if (changePage)
                viewpager.setCurrentItem(secuenciaPaginas.size()-1, true);
        }
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
                case 3:
                    return Pregunta.newInstance(pag.getId(), pag.getIdTarget(), pag.getTexto(), pag.getResp1(), pag.getResp2(), pag.getResp3(), pag.getRespOK());
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
                int tipo = pagina_json.getInt("tipo");
                pagina.setTipo(tipo);
                if (tipo == 1) {
                    JSONArray botones = pagina_json.getJSONArray("boton");

                    JSONObject boton0 = botones.getJSONObject(0);
                    pagina.setBoton_izq(new Boton(boton0.getString("texto_btn_izq"), boton0.getString("idTarget_izq")));
                    JSONObject boton1 = botones.getJSONObject(1);
                    pagina.setBoton_der(new Boton(boton1.getString("texto_btn_der"), boton1.getString("idTarget_der")));
                    pagina.ActivarBotones(0);
                }
                if (tipo == 3) {
                    pagina.setRespuestas(pagina_json.getString("respuesta1"),
                            pagina_json.getString("respuesta2"),
                            pagina_json.getString("respuesta3"),
                            pagina_json.getString("respuestaCorrecta"));
                    pagina.setPreguntaOK(0);
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
