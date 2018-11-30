package projecte.storybuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PortadaActivity extends AppCompatActivity {

    private Libro libro;
    private TextView texto;
    private Button boton_izq, boton_der;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

        libro = new Libro();

        cargaLibro();

        texto = findViewById(R.id.texto);
        boton_der = findViewById(R.id.boton_der);
        boton_izq = findViewById(R.id.boton_izq);

        Pagina p0=libro.buscaPagina("1");
        texto.setText(p0.getTexto());
        boton_izq.setText(p0.getBoton_izq());
        boton_der.setText(p0.getBoton_der());

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
                pagina.setBoton_der(pagina_json.getString("boton_der"));
                pagina.setBoton_izq(pagina_json.getString("boton_izq"));
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
