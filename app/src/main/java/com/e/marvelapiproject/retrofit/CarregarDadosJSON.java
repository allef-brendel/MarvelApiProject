package com.e.marvelapiproject.retrofit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.e.marvelapiproject.MainActivity;
import com.e.marvelapiproject.R;
import com.e.marvelapiproject.customclass.CustomApplication;
import com.e.marvelapiproject.objects.QuadrinhosResposta;
import com.e.marvelapiproject.objects.Result;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarregarDadosJSON extends AppCompatActivity {

    public static final String PUBLIC_KEY = "d4d1b4b87a003b45f81347d210a68f3c";
    public static final String PRIVATE_KEY = "2f75c7c06252fab7b2df86dd19974e075fcd5bd0";

    public static final String TIMESTAMP = "ts";
    public static final String API_KEY = "apikey";
    public static final String HASH = "hash";

    private static final String TAG = "COMICS";
    private APIInterface service;

    private ProgressDialog pdia;

    private String[][] listaDados;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregar_dados_json);

        ((CustomApplication)getApplication()).getNetworkComponent().inject(CarregarDadosJSON.this);

        listaDados = new String[20][6];

        pdia = new ProgressDialog(CarregarDadosJSON.this);
        pdia.setMessage("Carregando...");
        pdia.show();

        obterDados();

    }

    // Metodo para obter os dados da API
    private void obterDados() {

        String ts = Long.toString(System.currentTimeMillis() / 1000);
        String hash = md5(ts + PRIVATE_KEY + PUBLIC_KEY);

        service = retrofit.create(APIInterface.class);
        Call<QuadrinhosResposta> call = service.getQuadrinhos(PUBLIC_KEY, ts, hash);

        call.enqueue(new Callback<QuadrinhosResposta>() {
            @Override
            public void onResponse(Call<QuadrinhosResposta> call, Response<QuadrinhosResposta> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG,"Code: " + response.code());
                    return;
                }

                // Chamada para pegar os dados do JSON
                ArrayList<Result> results = response.body().getData().getResults();
                for( int i = 0; i<results.size(); i++) {

                    listaDados[i][0] = results.get(i).getTitle();
                    listaDados[i][1] = results.get(i).getDescription();
                    listaDados[i][2] = results.get(i).getPrices().get(0).getPrice();
                    listaDados[i][3] = results.get(i).getId();
                    listaDados[i][4] = results.get(i).getPageCount();
                    listaDados[i][5] = results.get(i).getThumbnail().getPath();

                    Log.d(TAG, "onResponse: \n" +
                            "Titulo: " + results.get(i).getTitle() + "\n" +
                            "Descrição: " + results.get(i).getDescription() + "\n" +
                            "Preço: " + results.get(i).getPrices().get(0).getPrice()+ "\n" +
                            "QuantPaginas: " + results.get(i).getPageCount() + "\n" +
                            "ID: " + results.get(i).getId() + "\n" +
                            "URL Foto: " + results.get(i).getThumbnail().getPath() + "\n" +
                            "-------------------------------------------------------------------------\n\n");
                }
                pdia.cancel();
                iniciarActivity();
            }

            @Override
            public void onFailure(Call<QuadrinhosResposta> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    // Metodo para salvar os dados carregados da API no putExtra
    private void iniciarActivity() {
        Intent a=new Intent(this, MainActivity.class);
        for (int x=0;x<listaDados.length;x++) {
            a.putExtra("title"+x,listaDados[x][0]);
            a.putExtra("descrição"+x,listaDados[x][1]);
            a.putExtra("preço"+x,listaDados[x][2]);
            a.putExtra("id"+x,listaDados[x][3]);
            a.putExtra("pag"+x,listaDados[x][4]);
            a.putExtra("thumbnails"+x, listaDados[x][5]);
        }
        startActivity(a);
    }


    // Metodo feito para alinhar as keys nos lugares certos
    public static String genKeyUser() {
        String ts = Long.toString(System.currentTimeMillis() / 1000);
        String hash = md5(ts + PRIVATE_KEY + PUBLIC_KEY);
        return TIMESTAMP + "=" + ts + "&" + API_KEY + "=" + PUBLIC_KEY + "&" +
                HASH + "=" + hash;
    }

    // Metodo feita para converter as keys da api para md5
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Criar MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Criar Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}