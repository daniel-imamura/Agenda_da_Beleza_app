package br.unicamp.agenda_da_beleza;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Servicos implements Serializable {

    @SerializedName("idServicos")
    private int idServicos;
    @SerializedName("idSalao")
    private int idSalao;
    @SerializedName("alongamentoDeCilios")
    private float alongamentoDeCilios;
    @SerializedName("maquiagem")
    private float maquiagem;
    @SerializedName("tintura")
    private float tintura;
    @SerializedName("corteDeCabelo")
    private float corteDeCabelo;
    @SerializedName("penteado")
    private float penteado;
    @SerializedName("progressiva")
    private float progressiva;
    @SerializedName("luzes")
    private float luzes ;
    @SerializedName("limpezaDePele")
    private float limpezaDePele;
    @SerializedName("designDeSobrancelha")
    private float designDeSobrancelha;
    @SerializedName("hidratacao")
    private float hidratacao;
    @SerializedName("unha")
    private float unha;

    public Servicos(int idSalao, float alongamentoDeCilios, float maquiagem, float tintura, float corteDeCabelo, float penteado, float progressiva, float luzes, float limpezaDePele, float designDeSobrancelha, float hidratacao, float unha) {
        this.idSalao = idSalao;
        this.alongamentoDeCilios = alongamentoDeCilios;
        this.maquiagem = maquiagem;
        this.tintura = tintura;
        this.corteDeCabelo = corteDeCabelo;
        this.penteado = penteado;
        this.progressiva = progressiva;
        this.luzes = luzes;
        this.limpezaDePele = limpezaDePele;
        this.designDeSobrancelha = designDeSobrancelha;
        this.hidratacao = hidratacao;
        this.unha = unha;
    }

    public ArrayList<Float> checarServicos(){
        ArrayList<Float> lista = new ArrayList<>();

        lista.add(this.alongamentoDeCilios);
        lista.add(this.maquiagem);
        lista.add(this.tintura);
        lista.add(this.corteDeCabelo);
        lista.add(this.penteado);
        lista.add(this.progressiva);
        lista.add(this.luzes);
        lista.add(this.limpezaDePele);
        lista.add(this.designDeSobrancelha);
        lista.add(this.hidratacao);
        lista.add(this.unha);

        return lista;
    }

    public String formatarValor(float num)
    {
        String str = String.valueOf(num);
        int indicePonto = str.indexOf(".");
        String sub = str.substring(indicePonto);

        if(sub.length() < 3)
            str = str.concat("0");

        str = str.replace(".", ",");
        return str;
    }


    public int getIdServicos() {
        return idServicos;
    }

    public void setIdServicos(int idServicos) {
        this.idServicos = idServicos;
    }

    public int getIdSalao() {
        return idSalao;
    }

    public void setIdSalao(int idSalao) {
        this.idSalao = idSalao;
    }

    public float getAlongamentoDeCilios() {
        return alongamentoDeCilios;
    }

    public void setAlongamentoDeCilios(float alongamentoDeCilios) {
        this.alongamentoDeCilios = alongamentoDeCilios;
    }

    public float getMaquiagem() {
        return maquiagem;
    }

    public void setMaquiagem(float maquiagem) {
        this.maquiagem = maquiagem;
    }

    public float getTintura() {
        return tintura;
    }

    public void setTintura(float tintura) {
        this.tintura = tintura;
    }

    public float getCorteDeCabelo() {
        return corteDeCabelo;
    }

    public void setCorteDeCabelo(float corteDeCabelo) {
        this.corteDeCabelo = corteDeCabelo;
    }

    public float getPenteado() {
        return penteado;
    }

    public void setPenteado(float penteado) {
        this.penteado = penteado;
    }

    public float getProgressiva() {
        return progressiva;
    }

    public void setProgressiva(float progressiva) {
        this.progressiva = progressiva;
    }

    public float getLuzes() {
        return luzes;
    }

    public void setLuzes(float luzes) {
        this.luzes = luzes;
    }

    public float getLimpezaDePele() {
        return limpezaDePele;
    }

    public void setLimpezaDePele(float limpezaDePele) {
        this.limpezaDePele = limpezaDePele;
    }

    public float getDesignDeSobrancelha() {
        return designDeSobrancelha;
    }

    public void setDesignDeSobrancelha(float designDeSobrancelha) {
        this.designDeSobrancelha = designDeSobrancelha;
    }

    public float getHidratacao() {
        return hidratacao;
    }

    public void setHidratacao(float hidratacao) {
        this.hidratacao = hidratacao;
    }

    public float getUnha() {
        return unha;
    }

    public void setUnha(float unha) {
        this.unha = unha;
    }
}