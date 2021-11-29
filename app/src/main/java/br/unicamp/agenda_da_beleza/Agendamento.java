package br.unicamp.agenda_da_beleza;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Agendamento implements Serializable {

    @SerializedName("idAgendamento")
    private int idAgendamento;
    @SerializedName("idCliente")
    private int idCliente;
    @SerializedName("idSalao")
    private int idSalao;
    @SerializedName("servico")
    private String servico;
    @SerializedName("dataAgendada")
    private String dataAgendada;

    public Agendamento(int idCliente, int idSalao, String servico, String dataAgendada) {
        this.idCliente = idCliente;
        this.idSalao = idSalao;
        this.servico = servico;
        this.dataAgendada = dataAgendada;
    }

    public int getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdSalao() {
        return idSalao;
    }

    public void setIdSalao(int idSalao) {
        this.idSalao = idSalao;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(String dataAgendada) {
        this.dataAgendada = dataAgendada;
    }
}