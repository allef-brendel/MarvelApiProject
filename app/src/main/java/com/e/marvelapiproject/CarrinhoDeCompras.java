package com.e.marvelapiproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.marvelapiproject.contentprovider.Authority;
import com.e.marvelapiproject.db.Database;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeCompras extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView precoQuadrinho;
    private TextView titulo;
    private TextView quantQuadrinhos;

    private ListView listView;

    private Button button;
    private Button button2;

    private Intent it;

    private List<String[]> listAdd;
    private List<String> produtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_de_compras);

        idConfiguration();
        toolbar();

        //StringExtra
        it = getIntent();
        String tituloCarrinho = it.getStringExtra("titulo");
        String precoCarrinho = it.getStringExtra("preco");
        String quantidadeQuadrinhos = it.getStringExtra("quant");

        titulo.setText("Título: " + tituloCarrinho);
        precoQuadrinho.setText(precoCarrinho);
        quantQuadrinhos.setText(quantidadeQuadrinhos);

        itemListView();
        buttonsCarrinho();
    }

    public void itemListView(){
        List<String> listAux = queryDadosContent();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),R.layout.my_text_size, android.R.id.text1, listAux);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String[] colunas = new String[]{ Database.ITEM};

                Uri uri = Uri.parse(Authority.URL + (position + 21));

                getContentResolver().delete(uri, null, null);

                Cursor cursor = getContentResolver().query(uri, colunas, null, null, null);

                if (cursor.moveToFirst()) {
                    do {
                        String item = cursor.getString(cursor.getColumnIndex(Database.ITEM));
                        Log.e("raff", item);
                    } while (cursor.moveToNext());
                }
            }
        });
    }

    // Toolbar
    public void toolbar(){
        mToolbar = findViewById(R.id.tb_main);
        mToolbar.setTitle(" Marvel Comics");
        mToolbar.setSubtitle(" Descrição Quadrinho");
        mToolbar.setLogo(R.drawable.marvel_simbolo);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void buttonsCarrinho(){

        // Listener do Botao Comprar
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantidadeQuadrinhos = it.getStringExtra("quant");
                String precoCarrinho = it.getStringExtra("preco");

                DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

                double total = 0;

                for(int i = 0; i < produtos.size(); i++) {
                    double result =(Double.parseDouble(quantidadeQuadrinhos)*Integer.parseInt(precoCarrinho));

                    total += result;
                }

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CarrinhoDeCompras.this);
                alertDialogBuilder.setMessage("Deseja Comprar?" +"\n"+
                        "Total a ser pago: " + ""+decimalFormat.format(total) + " $");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(CarrinhoDeCompras.this, "Compra Realizada com Sucesso", Toast.LENGTH_SHORT).show();
                            }
                        });
                alertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
           }
     });

        // Listener do Botao de Adicionar item no carrinho
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CarrinhoDeCompras.this);
                alertDialogBuilder.setMessage("Deseja adicionar ao carrinho?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int x) {
                                addItemBD();
                            }
                        });
                alertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    public List<String> queryDadosContent() {
        String[] colunas = new String[]{ Database.ITEM};

        Uri uri = Uri.parse(Authority.URL);

        List<String> listAux = new ArrayList<>();
        Cursor cursor = getContentResolver().query(uri, colunas, null, null, null);

        if (cursor.move(21)) {
            do {
                String item = cursor.getString(cursor.getColumnIndex(Database.ITEM));

                listAux.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listAux;
    }

    public void addItemBD(){
        ContentValues values = new ContentValues();

        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

        String tituloCarrinho = it.getStringExtra("titulo");
        String precoCarrinho = it.getStringExtra("preco");
        String quantidadeQuadrinhos = it.getStringExtra("quant");

        String total = "" + decimalFormat.format(Double.parseDouble(precoCarrinho) * Integer.parseInt(quantidadeQuadrinhos));

        String string = ("Título: " + tituloCarrinho + "\n" +
                "Preço: " + precoCarrinho + " $" + "\n" +
                "Quantidade Desejada: " + quantidadeQuadrinhos + "\n" +
                "Total a Pagar: " + total + " $" + "\n" +
                "_____________________________");

        values.put(Database.ITEM, string);

        getContentResolver().insert(Authority.CONTENT_URI, values);
    }

    public void idConfiguration(){
        precoQuadrinho = findViewById(R.id.textPrecoProduto);
        titulo = findViewById(R.id.textCarrinhoTitulo);
        quantQuadrinhos = findViewById(R.id.textQuantProduto);
        listView = findViewById(R.id.listViewCarrinho);
        button = findViewById(R.id.botaoAdicionarCarrinho);
        button2 = findViewById(R.id.botaoComprarCarrinho);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return true;
    }
}

