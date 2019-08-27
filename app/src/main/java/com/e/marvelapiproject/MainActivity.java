package com.e.marvelapiproject;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.e.marvelapiproject.db.Database;
import com.e.marvelapiproject.contentprovider.Authority;
import com.e.marvelapiproject.fragment.QuadrinhoFragments;
import com.e.marvelapiproject.objects.Quadrinho;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        criarPastaFile();
        
        toolbar();
        criarPastaFile();
        fragment();
    }

        // Toolbar da tela de listagem
        public void toolbar(){
            Toolbar mToolbar = findViewById(R.id.tb_main);
            mToolbar.setTitle("    Marvel Comics");
            mToolbar.setSubtitle("    Quadrinhos Marvel");
            mToolbar.setLogo(R.drawable.marvel_simbolo);
            setSupportActionBar(mToolbar);
        }

    //  Fragment
        public void fragment(){
            QuadrinhoFragments frag = (QuadrinhoFragments) getSupportFragmentManager().findFragmentByTag("person_frag");
            if (frag == null) {
                frag = new QuadrinhoFragments();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.rl_fragment_container, frag, "person_frag");
                ft.commit();
            }
        }

    public List<Quadrinho> queryDadosContent (int quant ){
        String[] colunas = new String[]{Database.TITLE, Database.DESCRIPITION, Database.PRICE, Database.ID, Database.PAGECOUNT, Database.URL};

        List<Quadrinho> listAux = new ArrayList<>();
        Cursor cursor = getContentResolver().query(Authority.CONTENT_URI, colunas, null, null, null);

        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                String title = cursor.getString(cursor.getColumnIndex(Database.TITLE));
                String descripition = cursor.getString(cursor.getColumnIndex(Database.DESCRIPITION));
                String price = cursor.getString(cursor.getColumnIndex(Database.PRICE));
                String id = cursor.getString(cursor.getColumnIndex(Database.ID));
                String pagcount = cursor.getString(cursor.getColumnIndex(Database.PAGECOUNT));
                String url = cursor.getString(cursor.getColumnIndex(Database.URL));

                listAux.add(new Quadrinho(title, descripition, price, id, pagcount, url));
            }
        }
        cursor.close();
        return listAux;
    }

        // Cria o diretorio file
        public void criarPastaFile () {
            try {
                FileOutputStream file = openFileOutput("teste.txt", MODE_PRIVATE);
                String s = "asd";
                file.write(s.getBytes());
                file.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        Toast.makeText(this, "Botão Configurações Precionado", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    }

