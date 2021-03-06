package com.innovus.doomi.Consumir;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.appspot.domi_app.doomiUsuarios.DoomiUsuarios;
import com.appspot.domi_app.doomiUsuarios.model.Sucursal;
import com.appspot.domi_app.doomiUsuarios.model.SucursalCollection;

import com.innovus.doomi.R;
import com.innovus.doomi.adapters.EmpresasAdapter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Janeth Arcos on 19/02/2015.
 */
public class HttpRequestTask extends AsyncTask<Void, Void, List<Sucursal>> {
   // private static com.appspot.domi_app.domi.Domi myApiService = null;
   private com.appspot.domi_app.doomiUsuarios.DoomiUsuarios myApiService = null;
    private Exception exception;


    private Activity activity;

    public static void build(Context context) {
        //myApiService = buildServiceHandler(context);
    }

    public HttpRequestTask(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    protected List<Sucursal> doInBackground(Void... params) {
        if (myApiService == null) { // Only do this once
            myApiService = AppConstants.buildServiceHandlerUsuarios();
        }
        // Domi.ConsultaEmpresa queryEmpresas = myApiService.consultaEmpresa();

        try {
            DoomiUsuarios.ConsultaSucursales querySucursales = myApiService.consultaSucursales();

            SucursalCollection sucursalCollection = querySucursales.execute();
            if (sucursalCollection != null && sucursalCollection.getItems() != null) {
                List<Sucursal> sucursales = sucursalCollection.getItems();

                return sucursales;
            }
            //return Collections.EMPTY_LIST;
            Log.e("entra","si" );
            return myApiService.consultaSucursales().execute().getItems();


        } catch (IOException e) {
            Log.e("Errorooooooooroeee", e.getMessage());


            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<Sucursal> result) {

          RecyclerView recyclerView = (RecyclerView)  activity.findViewById(R.id.my_recycler_view);
            recyclerView.setHasFixedSize(true);//que todo lo optimize
             recyclerView.setAdapter(new EmpresasAdapter(result, R.layout.row_empresas,activity));
            recyclerView.setLayoutManager(new LinearLayoutManager (activity));//linear x q es lienas o si no tambn grillas
            recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
