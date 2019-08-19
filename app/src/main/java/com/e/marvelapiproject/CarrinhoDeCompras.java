package com.e.marvelapiproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CarrinhoDeCompras extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView precoQuadrinho;
    private TextView titulo;
    private TextView quantQuadrinhos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_de_compras);

        toolbar();

        precoQuadrinho = findViewById(R.id.textPrecoProduto);
        titulo = findViewById(R.id.textCarrinhoTitulo);
        quantQuadrinhos = findViewById(R.id.textQuantProduto);

        //StringExtra
        Intent it = getIntent();
        String tituloCarrinho = it.getStringExtra("titulo");
        String precoCarrinho = it.getStringExtra("preco");
        String quantidadeQuadrinhos = it.getStringExtra("quant");

        titulo.setText("Título: " + tituloCarrinho);
        precoQuadrinho.setText(precoCarrinho);
        quantQuadrinhos.setText(quantidadeQuadrinhos);
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

