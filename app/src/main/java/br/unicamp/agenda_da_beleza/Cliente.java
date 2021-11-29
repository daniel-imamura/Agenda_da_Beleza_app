package br.unicamp.agenda_da_beleza;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Cliente implements Serializable {

    @SerializedName("idCliente")
    private int idCliente;
    @SerializedName("nome")
    private String nome;
    @SerializedName("email")
    private String email;
    @SerializedName("senha")
    private String senha;

    @SerializedName("telefone")
    private String telefone;

    public Cliente(String nome, String email, String telefone, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
    }

    public Cliente(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Cliente(Cliente cliente) {
        setNome(cliente.getNome());
        setEmail(cliente.getEmail());
        setSenha(cliente.getSenha());
        setTelefone(cliente.getTelefone());
    }
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
