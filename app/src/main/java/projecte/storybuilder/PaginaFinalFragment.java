package projecte.storybuilder;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PaginaFinalFragment extends Fragment {

    private static final String TEXTO = "texto";
    private static final String ID_TARGET = "id_target";
    private static final String ID = "id";

    private String texto;
    private String idTarget;
    private String id;

    public static PaginaFinalFragment newInstance(String id, String texto, String idTarget) {
        PaginaFinalFragment fragment = new PaginaFinalFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TEXTO, texto);
        bundle.putString(ID_TARGET, idTarget);
        bundle.putString(ID, id);
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
        this.idTarget =     (args != null) ? args.getString(ID_TARGET) : null;
        this.id =     (args != null) ? args.getString(ID) : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_pagina_final, container, false);
        TextView textView = rootView.findViewById(R.id.tV_final);
        Button btn = rootView.findViewById(R.id.btn_final);
        PortadaActivity portada = (PortadaActivity) getActivity();
        textView.setText(this.texto.replace("NOMBRE",portada.getNombre().toUpperCase()));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PortadaActivity portada = (PortadaActivity) getActivity();
                portada.Empezar();
            }
        });

        return rootView;
    }
}
