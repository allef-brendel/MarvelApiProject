package com.e.marvelapiproject.objects;

public class Quadrinho {

    public String titulo;
    public String precos;
    public String quantPaginas;
    public String descricao;
    public String url;

    public int foto;

    public Quadrinho(String titulo, String precos, String url) {
        this.titulo = titulo;
        this.precos = precos;
        this.url = url;
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

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getDescricao() {
        return descricao;
    }
}
