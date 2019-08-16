package com.e.marvelapiproject;

import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.e.marvelapiproject.gravador.Gravador;


public class QuadrinhoDetalheActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private ImageView imageView;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    private Button button2;
    private EditText editText;

    Gravador gravador;

    String[][] lista;

    Glide glide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        gravador = new Gravador();
        lista = gravador.lerQuadrinhos();

        // Toolbar da tela de Detalhe dos quadrinhos
        mToolbar = findViewById(R.id.tb_main);
        mToolbar.setTitle(" Marvel Comics");
        mToolbar.setSubtitle(" Descrição Quadrinho");
        mToolbar.setLogo(R.drawable.marvel_simbolo);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.imageVIewQuadrinho);

        textView = findViewById(R.id.textViewDescricao);
        textView2 = findViewById(R.id.precoSecond);
        textView3 = findViewById(R.id.quantPag);
        textView4 = findViewById(R.id.textTitulo);

        editText = findViewById(R.id.editTextQuantidade);

        button2 = findViewById(R.id.botaoCarrinho);

        Intent it = getIntent();
        int position = it.getIntExtra("position", -1);

        // Usando o Glide para mostrar as imagens na tela de detalhe do quadrinho, usando gravador para recuperar as URL's pegas do JSON
        glide.with(this).load(lista[position][5] + "/portrait_medium.jpg").into(imageView);

        textView4.setText(lista[position][0]);
        textView.setText(lista[position][1]);
        textView2.setText("R$ " + lista[position][2]);
        textView3.setText("Pag: " + lista[position][4]);

        // Botão de Adicionar ao carrinho
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = getIntent();
                int position = it.getIntExtra("position", -1);

                Intent intent = new Intent(QuadrinhoDetalheActivity.this, CarrinhoDeCompras.class);
                intent.putExtra("titulo", lista[position][0]);
                intent.putExtra("preco", lista[position][2]);

                intent.putExtra("quant", editText.getText().toString());
                startActivity(intent);
            }
        });

    }

        // inflate dos itens da toolbar
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_second, menu);
            return true;
        }

        // botao da seta de voltar da toolbar
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            int id = item.getItemId();

            if (id == android.R.id.home) {
                finish();
            }

            return true;
        }
    }

