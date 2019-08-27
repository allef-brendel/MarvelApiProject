package com.e.marvelapiproject.objects;

public class Quadrinho {

    public String titulo;
    public String precos;
    public String quantPaginas;
    public String descricao;
    public String url;
    public String id;

//    public Quadrinho(String titulo, String precos, String url) {
//        this.titulo = titulo;
//        this.precos = precos;
//        this.url = url;
//    }

    public Quadrinho(String title, String description, String price, String id, String pagCount, String url) {
        this.titulo = title;
        this.descricao = description;
        this.precos = price;
        this.quantPaginas = pagCount;
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrecos() {
        return precos;
    }

    public void setPrecos(String precos) {
        this.precos = precos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String nome) {
        this.titulo = titulo;
    }

    public String getQuantPaginas() {
        return quantPaginas;
    }

    public void setQuantPaginas(String clan) {
        this.quantPaginas = quantPaginas;
    }

    public String getDescricao() {
        return descricao;
    }
}
