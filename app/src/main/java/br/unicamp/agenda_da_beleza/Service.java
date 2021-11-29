package br.unicamp.agenda_da_beleza;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Service {

    //Cliente
    @GET("/api/cliente")
    Call<List<Cliente>> getCliente();

    @GET("/api/cliente/{email}/{senha}")
    Call<Cliente> buscarCliente(@Path("email") String email, @Path("senha") String senha);

    @POST("/api/cliente")
    Call<Cliente> incluirCliente(@Body Cliente cliente);

    @PUT("/api/cliente/{idCliente}")
    Call<Cliente> alterarCliente(@Path("idCliente") int idCliente, @Body Cliente cliente);
    //Salão
    @GET("/api/salao/{email}/{senha}")
    Call<Salao> buscarSalaoPorEmail(@Path("email") String email, @Path("senha") String senha);

    @GET("/api/salao/{nome}")
    Call<Salao> buscarSalaoPorNome(@Path("nome") String nome);

    @PUT("/api/salao/{idSalao}")
    Call<Salao> alterarSalao(@Path("idSalao") int idSalao, @Body Salao salao);

    @POST("/api/salao")
    Call<Salao> incluirSalao(@Body Salao salao);

    //Serviços
    @PUT("/api/servicos/{idSalao}/{valorNovo}/{indice}")
    Call<Servicos> alterarServico(@Path("idSalao") int idSalao, @Path("valorNovo") float valorNovo, @Path("indice") int indice);

    @POST("/api/servicos")
    Call<Servicos> incluirServico(@Body Servicos servico);

    @GET("/api/servicos/{idSalao}")
    Call<Servicos> buscarServicoPorIdSalao(@Path("idSalao") int idSalao);

    //Agendamento
    //@POST("/api/agendamento/{dataAgendada}")
    //Call<Agendamento> incluirAgendamento(@Path("dataAgendada") String dataAgendada, @Body Agendamento agendamento);
    @POST("/api/agendamento")
    Call<Agendamento> incluirAgendamento(@Body Agendamento agendamento);
}
