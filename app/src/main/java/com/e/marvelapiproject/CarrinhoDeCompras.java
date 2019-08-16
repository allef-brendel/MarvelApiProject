package com.e.marvelapiproject;

import java.text.DecimalFormat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.e.marvelapiproject.gravador.Gravador;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeCompras extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView precoQuadrinho;
    private TextView titulo;
    private TextView quantQuadrinhos;
    private Gravador gravador;

    private ListView listView;

    private Button button;
    private Button button2;

    private List<String> produtos;
    private List<String> produtos2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_de_compras);

        produtos = new ArrayList<>();

        gravador = new Gravador();

        // Toolbar
        mToolbar = findViewById(R.id.tb_main);
        mToolbar.setTitle(" Marvel Comics");
        mToolbar.setSubtitle(" Descrição Quadrinho");
        mToolbar.setLogo(R.drawable.marvel_simbolo);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        precoQuadrinho = findViewById(R.id.textPrecoProduto);
        titulo = findViewById(R.id.textCarrinhoTitulo);
        quantQuadrinhos = findViewById(R.id.textQuantProduto);
        listView = findViewById(R.id.listViewCarrinho);
        button = findViewById(R.id.botaoAdicionarCarrinho);
        button2 = findViewById(R.id.botaoComprarCarrinho);

        //StringExtra para pega os dados da activity de detalhes do quadrinho
        Intent it = getIntent();
        String tituloCarrinho = it.getStringExtra("titulo");
        String precoCarrinho = it.getStringExtra("preco");
        String quantidadeQuadrinhos = it.getStringExtra("quant");

        // SetText dos itens do carrinhgo
        titulo.setText("Título: " + tituloCarrinho);
        precoQuadrinho.setText(precoCarrinho);
        quantQuadrinhos.setText(quantidadeQuadrinhos);


        // Listener do Botao Comprar
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String[]> lista = gravador.lerDadosCarrinho();

                DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

                double total = 0;

                for(int i = 0; i < lista.size(); i++) {
                    double result =(Double.parseDouble(lista.get(i)[1])*Integer.parseInt(lista.get(i)[2]));

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

                                DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

                                Intent it = getIntent();
                                String tituloCarrinho = it.getStringExtra("titulo");
                                String precoCarrinho = it.getStringExtra("preco");
                                String quantidadeQuadrinhos = it.getStringExtra("quant");

                                List<String[]> lista = gravador.lerDadosCarrinho();

                                String[] strings = new String[3];
                                strings[0]=tituloCarrinho;
                                strings[1]=precoCarrinho;
                                strings[2]=quantidadeQuadrinhos;

                                lista.add(strings);

                                gravador.salvarDadosCarrinho(lista);

                                for(int i = 0; i < lista.size(); i++) {
                                    String total = ""+ decimalFormat.format(Double.parseDouble(lista.get(i)[1])*Integer.parseInt(lista.get(i)[2]));

                                    produtos.add("Título: " + lista.get(i)[0] + "\n" +
                                            "Preço: " + lista.get(i)[1] + " $" + "\n" +
                                            "Quantidade Desejada: " + lista.get(i)[2] + "\n" +
                                            "Total a Pagar: " + total + " $" + "\n" +
                                            "_____________________________");
                                }
                                final ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),R.layout.my_text_size, android.R.id.text1, produtos);
                                listView.setAdapter(adapter);

                                //Onclick do listView para remover o item da lista
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

                                        List<String[]> lista = gravador.lerDadosCarrinho();

                                        try{
                                            lista.remove(position);
                                            gravador.salvarDadosCarrinho(lista);

                                            Toast.makeText(CarrinhoDeCompras.this, "Item removido, atualize a lista", Toast.LENGTH_SHORT).show();

                                            listView.setAdapter(adapter);

                                        }catch (Exception e){
                                            Toast.makeText(CarrinhoDeCompras.this, "Informe um indice valido", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
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

    // Inflate das opçoes da toolbar de carrinho de compras
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    // Botao da Seta de voltar da Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return true;
    }
}

