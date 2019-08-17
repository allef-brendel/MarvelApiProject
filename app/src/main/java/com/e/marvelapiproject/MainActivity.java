package com.e.marvelapiproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.e.marvelapiproject.fragment.QuadrinhoFragments;
import com.e.marvelapiproject.gravador.Gravador;
import com.e.marvelapiproject.objects.Quadrinho;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private String[][] listaDados;
    private Gravador gravador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        criarPastaFile();
        gravador = new Gravador();

        listaDados = recuperarLista();
        criarPastaFile();

        // Gravador para salvar informaçoes dos quadrinhos
        gravador.salvarInfoQuadrinhos(listaDados);
        gravador.salvarQuantItens(listaDados.length);

        // Toolbar da tela de listagem
        mToolbar = findViewById(R.id.tb_main);
        mToolbar.setTitle("    Marvel Comics");
        mToolbar.setSubtitle("    Quadrinhos Marvel");
        mToolbar.setLogo(R.drawable.marvel_simbolo);
        setSupportActionBar(mToolbar);

        //  Fragment
        QuadrinhoFragments frag = (QuadrinhoFragments) getSupportFragmentManager().findFragmentByTag("person_frag");
        if (frag == null) {
            frag = new QuadrinhoFragments();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "person_frag");
            ft.commit();
        }
    }

        // Metodo que manda os dados para o Adapter
        public List<Quadrinho> getSetQuadrinhoList ( int quant){

            String[] title = new String[listaDados.length];
            String[] descricao = new String[listaDados.length];
            String[] quantPaginas = new String[listaDados.length];
            String[] precos = new String[listaDados.length];
            String[] url = new String[listaDados.length];

            for (int i = 0; i < listaDados.length; i++) {
                title[i] = listaDados[i][0];
                descricao[i] = listaDados[i][1];
                precos[i] = listaDados[i][2];
                quantPaginas[i] = listaDados[i][4];
                url[i] = listaDados[i][5];

            }
            List<Quadrinho> listAux = new ArrayList<>();

            for (int i = 0; i < quant; i++) {
                Quadrinho quadrinho = new Quadrinho(listaDados[i][0], listaDados[i][2], listaDados[i][5]);
                listAux.add(quadrinho);
            }
            return listAux;
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

        // Metodo para recuperar dados de CarregarDadosJSON
        private String[][] recuperarLista () {
            String[][] lista = new String[20][6];
            for (int x = 0; x < lista.length; x++) {
                lista[x][0] = getIntent().getStringExtra("title" + x);
                lista[x][1] = getIntent().getStringExtra("descrição" + x);
                lista[x][2] = getIntent().getStringExtra("preço" + x);
                lista[x][3] = getIntent().getStringExtra("id" + x);
                lista[x][4] = getIntent().getStringExtra("pag" + x);
                lista[x][5] = getIntent().getStringExtra("thumbnails" + x);
            }

            return lista;
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
    }

