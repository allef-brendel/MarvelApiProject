package com.e.marvelapiproject.gravador;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gravador {

    //Grava os arquivos na pasta File
    public void gravarArquivo(String nomeArquivo, String texto){
        try {
            OutputStreamWriter buff=new OutputStreamWriter(new FileOutputStream("/data/data/com.e.marvelapiproject/files/"+nomeArquivo),"UTF-8");
            buff.write(texto);
            buff.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ler os arquivos da pasta File
    public String lerArquivo(String nomeArquivo){
        String s="";
        BufferedReader buff;
        try {
            buff= new BufferedReader(new InputStreamReader(new FileInputStream("/data/data/com.e.marvelapiproject/files/"+nomeArquivo),"UTF-8"));
            String linha=buff.readLine();
            while(linha!=null){
                s+=linha;
                linha=buff.readLine();
            }
            buff.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    // Ler o arquivo dos quadrinhos salvos do JSON
    public String[][] lerQuadrinhos(){
        String arquivo = lerArquivo("infoQuadrinhos.txt");
        Scanner sc=new Scanner(arquivo).useDelimiter("\\|");
        String[][] lista=new String[lerQuantidadeItens()][6];
        for (String[] a:lista){
            a[0]=sc.next();
            a[1]=sc.next();
            a[2]=sc.next();
            a[3]=sc.next();
            a[4]=sc.next();
            a[5]=sc.next();

        }
        sc.close();
        return lista;
    }

    // Salva as informaçoes dos quadrinhos para serem usadas nas Intents
    public void salvarInfoQuadrinhos(String[][] lista){
        String s="";
        for (String[] a:lista){
            s+="|"+a[0];
            s+="|"+a[1];
            s+="|"+a[2];
            s+="|"+a[3];
            s+="|"+a[4];
            s+="|"+a[5];
        }
        gravarArquivo("infoQuadrinhos.txt",s);
    }

    // Salva a quantidade de quadrinhos
    public void salvarQuantItens(int quant){
        String s=""+quant;
        gravarArquivo("quantItens.txt",s);
    }

    // Ler a quantidade de quadrinhos
    public int lerQuantidadeItens(){
        String arquivo = lerArquivo("quantItens.txt");
        String novo=arquivo;
        int i=Integer.parseInt(novo);
        return i;
    }

    // Salva os dados do carrinho de compras
    public void salvarDadosCarrinho(List<String[]> list){

        String s = "";
        for (String[] info : list  ){
            s += "|" + info[0];
            s += "|" + info[1];
            s += "|" + info[2];

        }
        gravarArquivo("DadosCarrinho.txt",s);

    }

    //Ler os dados do carrinho de compras
    public List<String[]> lerDadosCarrinho(){
        String arquivo = lerArquivo("DadosCarrinho.txt");
        Scanner sc=new Scanner(arquivo).useDelimiter("\\|");

        List<String[]> dados = new ArrayList<String[]>();

        while (sc.hasNext()){
            String[] lista= new String[3];
            lista[0] = sc.next();
            lista[1]=sc.next();
            lista[2]=sc.next();
            dados.add(lista);
        }

        sc.close();
        return dados;
    }
}
