package com.e.marvelapiproject;

import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.e.marvelapiproject.contentprovider.Authority;
import com.e.marvelapiproject.db.Database;
import com.e.marvelapiproject.pojo.Quadrinho;

import java.util.ArrayList;
import java.util.List;


public class QuadrinhoDetalheActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvdescription;
    private TextView tvprice;
    private TextView tvpagcount;
    private TextView tvtitle;

    private Button button2;
    private EditText editText;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quadrinho_detalhe);

        toolbar();
        idConfiguration();
        setTextQuery();
    }

        public void setTextQuery(){
            Intent it = getIntent();
            int position = it.getIntExtra("position", 0);

            List<Quadrinho> listAux = new ArrayList<>();

            String[] colunas = new String[]{Database.TITLE, Database.DESCRIPITION, Database.PRICE, Database.PAGECOUNT, Database.URL};

            Cursor cursor = getContentResolver().query(Authority.CONTENT_URI , colunas, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex(Database.TITLE));
                    String descripition = cursor.getString(cursor.getColumnIndex(Database.DESCRIPITION));
                    String price = cursor.getString(cursor.getColumnIndex(Database.PRICE));
                    String pagcount = cursor.getString(cursor.getColumnIndex(Database.PAGECOUNT));
                    String url = cursor.getString(cursor.getColumnIndex(Database.URL));

                    listAux.add(new Quadrinho(title, descripition, price, null, pagcount, url));
                } while (cursor.moveToNext());
            }

                Glide.with(this).load(listAux.get(position).getUrl() + "/portrait_medium.jpg").into(imageView);

                tvtitle.setText(listAux.get(position).getTitulo());
                tvdescription.setText(listAux.get(position).getDescricao());
                tvprice.setText("R$ " + listAux.get(position).getPrecos());
                tvpagcount.setText("Pag: " + listAux.get(position).getQuantPaginas());

                intent = new Intent(QuadrinhoDetalheActivity.this, CarrinhoDeCompras.class);
                intent.putExtra("titulo", listAux.get(position).getTitulo());
                intent.putExtra("preco", listAux.get(position).getPrecos());

                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("quant", editText.getText().toString());
                        startActivity(intent);
                    }
                });
            cursor.close();
        }

        // Toolbar da tela de Detalhe dos quadrinhos
        public void toolbar(){
            Toolbar mToolbar = findViewById(R.id.tb_main);
            mToolbar.setTitle(" Marvel Comics");
            mToolbar.setSubtitle(" Descrição Quadrinho");
            mToolbar.setLogo(R.drawable.marvel_simbolo);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        public void idConfiguration(){
            imageView = findViewById(R.id.imageVIewQuadrinho);
            tvdescription = findViewById(R.id.textViewDescricao);
            tvprice = findViewById(R.id.precoSecond);
            tvpagcount = findViewById(R.id.quantPag);
            tvtitle = findViewById(R.id.textTitulo);
            editText = findViewById(R.id.editTextQuantidade);
            button2 = findViewById(R.id.botaoCarrinho);
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

