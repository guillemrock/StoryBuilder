package projecte.storybuilder;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;

public class Pregunta extends Fragment {

    private static final String ID = "id";
    private static final String ID_TARGET = "id_target";
    private static final String TEXTO = "texto";
    private static final String RESPUESTA1 = "respuesta1";
    private static final String RESPUESTA2 = "respuesta2";
    private static final String RESPUESTA3 = "respuesta3";
    private static final String RESPUESTAOK = "respuestaCorrecta";

    private String id;
    private String idTarget;
    private String texto;
    private String respuesta1;
    private String respuesta2;
    private String respuesta3;
    private String respuestaCorrecta;

    public static Pregunta newInstance(String id, String idTarget, String texto, String respuesta1, String respuesta2, String respuesta3, String respuestaCorrecta) {
        Pregunta fragment = new Pregunta();
        Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        bundle.putString(ID_TARGET, idTarget);
        bundle.putString(TEXTO, texto);
        bundle.putString(RESPUESTA1, respuesta1);
        bundle.putString(RESPUESTA2, respuesta2);
        bundle.putString(RESPUESTA3, respuesta3);
        bundle.putString(RESPUESTAOK, respuestaCorrecta);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        this.idTarget =     (args != null) ? args.getString(ID_TARGET) : null;
        this.id =     (args != null) ? args.getString(ID) : null;
        this.texto =     (args != null) ? args.getString(TEXTO) : null;
        this.respuesta1 =     (args != null) ? args.getString(RESPUESTA1) : null;
        this.respuesta2 =     (args != null) ? args.getString(RESPUESTA2) : null;
        this.respuesta3 =     (args != null) ? args.getString(RESPUESTA3) : null;
        this.respuestaCorrecta =     (args != null) ? args.getString(RESPUESTAOK) : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.pregunta, container, false);
        final TextView textView = rootView.findViewById(R.id.tV_pregunta);
        textView.setText(this.texto);
        textView.setMovementMethod(new ScrollingMovementMethod());
        final Button btn = rootView.findViewById(R.id.check_btn);
        final RadioButton r1 = rootView.findViewById(R.id.respuesta1);
        final RadioButton r2 = rootView.findViewById(R.id.respuesta2);
        final RadioButton r3 = rootView.findViewById(R.id.respuesta3);
        r1.setText(this.respuesta1);
        r2.setText(this.respuesta2);
        r3.setText(this.respuesta3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PortadaActivity portada = (PortadaActivity) getActivity();
                if (r1.isChecked()){
                    if (respuestaCorrecta.contentEquals("respuesta1")){
                        portada.setPreguntaOK(id,1);
                    }
                }
                if (r2.isChecked()){
                    if (respuestaCorrecta.contentEquals("respuesta2")){
                        portada.setPreguntaOK(id,2);
                    }
                }
                if (r3.isChecked()){
                    if (respuestaCorrecta.contentEquals("respuesta3")){
                        portada.setPreguntaOK(id,3);
                    }
                }
                if (portada.getPreguntaOK(id) > 0){
                    portada.paginaSiguiente(idTarget, true);
                    portada.muestraToast("Respuesta correcta " + portada.getNombre().toUpperCase() + "!",4);
                } else {
                    portada.paginaAnterior();
                    portada.muestraToast("Respuesta incorrecta,\n vuelve a leerlo " + portada.getNombre().toUpperCase() + "!",4);
                }
            }
        });

        return rootView;
    }
}
