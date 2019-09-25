package com.formato.isp.GestionDocumental;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.formato.isp.Adapter.AdapterListBasic;
import com.formato.isp.R;
import com.formato.isp.data.DataGenerator;
import com.formato.isp.model.People;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class Gestion_documental extends Fragment {

    private View parent_view;
    private View root;

    private RecyclerView recyclerView;
    private AdapterListBasic mAdapter;
    private GestionDocumentalViewModel mViewModel;

    public static Gestion_documental newInstance() {
        return new Gestion_documental();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.gestion_documental_fragment, container, false);
        parent_view = root.findViewById(android.R.id.content);
        initComponent(root);
        return root;
    }

    private void initComponent(final View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.Idrecycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        List<People> items = DataGenerator.getPeopleData(root.getContext());
        //items.addAll(DataGenerator.getPeopleData(root.getContext()));

        //set data and list adapter
        mAdapter = new AdapterListBasic(root.getContext(), items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
            @Override
            public void onItemClick(View view, People obj, int position) {
                //Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
                Snackbar.make(root, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("nombre", obj.name);
                bundle.putString("email", obj.email);
                bundle.putInt("foto",obj.image);
                //bundle.putString("foto", obj.image);

                Navigation.findNavController(root).navigate(R.id.detalle_gestion,bundle);
            }
        });

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(GestionDocumentalViewModel.class);
        // TODO: Use the ViewModel
    }

}
