package com.e.marvelapiproject.retrofit;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.e.marvelapiproject.MainActivity;
import com.e.marvelapiproject.R;
import com.e.marvelapiproject.contentprovider.Authority;
import com.e.marvelapiproject.db.Database;
import com.e.marvelapiproject.dagger.customclass.ModuleApplication;
import com.e.marvelapiproject.pojo.QuadrinhosResposta;
import com.e.marvelapiproject.pojo.Result;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CarregarDadosJSON extends AppCompatActivity {

    public static final String PUBLIC_KEY = "d4d1b4b87a003b45f81347d210a68f3c";
    public static final String PRIVATE_KEY = "2f75c7c06252fab7b2df86dd19974e075fcd5bd0";

    private static final String TAG = "COMICS";

    private ProgressDialog pdia;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregar_dados_json);

        pdia = new ProgressDialog(CarregarDadosJSON.this);
        pdia.setMessage("Carregando...");
        pdia.show();

        verificarBDVazio();
    }

    private void verificarBDVazio() {
        Database bd = new Database(CarregarDadosJSON.this);
        SQLiteDatabase db = bd.getWritableDatabase();

        String select = "SELECT count(*) FROM " + Database.QUADRINHO_TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);
        cursor.moveToFirst();

        int count = cursor.getInt(0);

        if(count>0){
            pdia.cancel();
            startIntent();
        }else {
            obterDados();
        }
    }

    // Metodo para obter os dados da API

        private void obterDados() {

            String ts = Long.toString(System.currentTimeMillis() / 1000);
            ((ModuleApplication)getApplication()).getNetworkComponent().inject(this);
            String hash = md5(ts + PRIVATE_KEY + PUBLIC_KEY);

        APIInterface service = retrofit.create(APIInterface.class);
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
                ContentValues values = new ContentValues();

                for( int i = 0; i<results.size(); i++) {
                    String title = results.get(i).getTitle();
                    String description = results.get(i).getDescription();
                    String price = results.get(i).getPrices().get(0).getPrice();
                    String id = results.get(i).getId();
                    String pageCount = results.get(i).getPageCount();
                    String url = results.get(i).getThumbnail().getPath();

                    Log.d(TAG, "onResponse: \n" +
                            "Titulo: " + results.get(i).getTitle() + "\n" +
                            "Descrição: " + results.get(i).getDescription() + "\n" +
                            "Preço: " + results.get(i).getPrices().get(0).getPrice()+ "\n" +
                            "QuantPaginas: " + results.get(i).getPageCount() + "\n" +
                            "ID: " + results.get(i).getId() + "\n" +
                            "URL Foto: " + results.get(i).getThumbnail().getPath() + "\n" +
                            "-------------------------------------------------------------------------\n\n");

                    values.put(Database.TITLE, title);
                    values.put(Database.DESCRIPITION, description);
                    values.put(Database.PRICE, price);
                    values.put(Database.ID, id);
                    values.put(Database.PAGECOUNT, pageCount);
                    values.put(Database.URL, url);

                    getContentResolver().insert(Authority.CONTENT_URI, values);
                }
                pdia.cancel();
                startIntent();
            }

            @Override
            public void onFailure(Call<QuadrinhosResposta> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void startIntent(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Metodo feita para converter as keys da api para md5
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Criar MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

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