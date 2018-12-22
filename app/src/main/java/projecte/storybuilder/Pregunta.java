package projecte.storybuilder;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Pregunta extends Fragment {

    private static final String TEXTO = "texto";
    private static final String RESPUESTA1 = "respuesta1";
    private static final String RESPUESTA2 = "respuesta2";
    private static final String RESPUESTA3 = "respuesta3";


    private String texto;
    private String respuesta1;
    private String respuesta2;
    private String respuesta3;

    public static Pregunta newInstance(String texto, String respuesta1, String respuesta2, String respuesta3) {
        Pregunta fragment = new Pregunta();
        Bundle bundle = new Bundle();
        bundle.putString(TEXTO, texto);
        bundle.putString(RESPUESTA1, respuesta1);
        bundle.putString(RESPUESTA2, respuesta2);
        bundle.putString(RESPUESTA3, respuesta3);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        this.texto =     (args != null) ? args.getString(TEXTO) : null;
        this.respuesta1 =     (args != null) ? args.getString(RESPUESTA1) : null;
        this.respuesta2 =     (args != null) ? args.getString(RESPUESTA2) : null;
        this.respuesta3 =     (args != null) ? args.getString(RESPUESTA3) : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.pregunta, container, false);
        TextView textView = rootView.findViewById(R.id.tV_sinBotones);
        textView.setText(this.texto);
        return rootView;
    }
}
