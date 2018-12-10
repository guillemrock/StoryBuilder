package projecte.storybuilder;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PaginaBotonesFragment extends Fragment {

    private static final String TEXTO = "texto";
    private static final String BOTON_DER = "boton derecho";
    private static final String BOTON_IZQ = "boton izquierdo";

    private String texto;
    private String boton_izq;
    private String boton_der;

    public static PaginaBotonesFragment newInstance(String texto, String boton_der, String boton_izq) {
        PaginaBotonesFragment fragment = new PaginaBotonesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TEXTO, texto);
        bundle.putString(BOTON_DER, boton_der);
        bundle.putString(BOTON_IZQ, boton_izq);
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
        this.boton_izq = (args != null) ? args.getString(BOTON_IZQ) : null;
        this.boton_der = (args != null) ? args.getString(BOTON_DER) : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_pagina_botones, container, false);
        TextView textView = rootView.findViewById(R.id.texto);
        Button btn_der = rootView.findViewById(R.id.boton_der);
        Button btn_izq = rootView.findViewById(R.id.boton_izq);

        btn_der.setOnClickListener();

        textView.setText(this.texto);
        btn_der.setText(this.boton_der);
        btn_izq.setText(this.boton_izq);

        return rootView;
    }
}
