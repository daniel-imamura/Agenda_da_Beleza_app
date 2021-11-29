package br.unicamp.agenda_da_beleza;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Salao implements Serializable {

    @SerializedName("idSalao")
    private int idSalao;
    @SerializedName("nome")
    private String nome;
    @SerializedName("email")
    private String email;
    @SerializedName("endereco")
    private String endereco;
    @SerializedName("senha")
    private String senha;

    @SerializedName("telefone")
    private String telefone;

    public Salao(String nome, String email, String endereco,String telefone, String senha) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.senha = senha;
        this.telefone = telefone;
    }

    public Salao(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Salao(Salao salao) {
        setNome(salao.getNome());
        setEmail(salao.getEmail());
        setSenha(salao.getSenha());
        setTelefone(salao.getTelefone());
    }

    public int getIdSalao() { return idSalao; }

    public void setIdSalao(int idSalao) {
        this.idSalao = idSalao;
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

    public String getEndereco() { return endereco; }

    public void setEndereco(String endereco) { this.endereco = endereco; }

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