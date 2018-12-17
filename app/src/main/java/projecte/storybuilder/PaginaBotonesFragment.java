package projecte.storybuilder;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PaginaBotonesFragment extends Fragment {


    private static final String TEXTO = "texto";
    private static final String BOTON_DER = "boton derecho";
    private static final String BOTON_IZQ = "boton izquierdo";
    private static final String ID_TARGET_DER = "id_target_der";
    private static final String ID_TARGET_IZQ = "id_target_izq";

    private String texto;
    private String boton_izq;
    private String boton_der;
    private String idTargerIzq;
    private String idTargetDer;

    public static PaginaBotonesFragment newInstance(String texto, Boton boton_der, Boton boton_izq) {
        PaginaBotonesFragment fragment = new PaginaBotonesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TEXTO, texto);
        bundle.putString(BOTON_DER, boton_der.getTexto());
        bundle.putString(BOTON_IZQ, boton_izq.getTexto());
        bundle.putString(ID_TARGET_DER, boton_der.getIdTarget());
        bundle.putString(ID_TARGET_IZQ, boton_izq.getIdTarget());
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
        this.idTargerIzq = (args != null) ? args.getString(ID_TARGET_IZQ) : null;
        this.idTargetDer = (args != null) ? args.getString(ID_TARGET_DER) : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_pagina_botones, container, false);
        TextView textView = rootView.findViewById(R.id.tV_conBotones);
        Button btn_der = rootView.findViewById(R.id.boton_der);
        Button btn_izq = rootView.findViewById(R.id.boton_izq);

        btn_der.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PortadaActivity portada = (PortadaActivity) getActivity();
                portada.paginaSiguiente(idTargetDer);
            }
         });

        btn_izq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PortadaActivity portada = (PortadaActivity) getActivity();
                portada.paginaSiguiente(idTargerIzq);
            }
        });

        textView.setText(this.texto);
        btn_der.setText(this.boton_der);
        btn_izq.setText(this.boton_izq);

        return rootView;
    }
}
