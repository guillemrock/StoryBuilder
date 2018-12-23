package projecte.storybuilder;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PaginaSinBotonesFragment extends Fragment {

    private static final String TEXTO = "texto";
    private static final String ID_TARGET = "id_target";
    private static final String ID = "id";

    private String texto;
    private String idTarget;
    private String id;

    public static PaginaSinBotonesFragment newInstance(String id, String texto, String idTarget) {
        PaginaSinBotonesFragment fragment = new PaginaSinBotonesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_pagina_nobotones, container, false);
        TextView textView = rootView.findViewById(R.id.tV_sinBotones);
        textView.setText(this.texto);
        return rootView;
    }
}
