package br.com.fiap.games.resource;

import br.com.fiap.games.dao.JogoDao;
import br.com.fiap.games.dto.jogo.AtualizacaoJogoDto;
import br.com.fiap.games.dto.jogo.CadastroJogoDto;
import br.com.fiap.games.dto.jogo.DetalhesJogoDto;
import br.com.fiap.games.exception.EntidadeNaoEncontradaException;
import br.com.fiap.games.factory.ConnectionFactory;
import br.com.fiap.games.model.Jogo;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;

@Path("jogos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JogoResource {

    private JogoDao jogoDao;

    private ModelMapper modelMapper;

    public JogoResource() throws SQLException {
        this.jogoDao = new JogoDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroJogoDto dto, @Context UriInfo uriInfo) throws SQLException {
        Jogo jogo = modelMapper.map(dto, Jogo.class);
        jogoDao.cadastrar(jogo);
        UriBuilder uri = uriInfo.getAbsolutePathBuilder();
        uri.path(String.valueOf(jogo.getCodigo()));
        return Response.created(uri.build())
                .entity(modelMapper.map(jogo, DetalhesJogoDto.class)).build();
    }

    @GET //localhost:8080/jogos/1
    @Path("{id}")
    public Jogo buscar(@PathParam("id") Integer id) throws EntidadeNaoEncontradaException, SQLException {
        return jogoDao.pesquisarPorId(id);
    }

    @GET
    public List<Jogo> listar() throws SQLException {
        return jogoDao.listar();
    }

    @PUT
    @Path("{id}")
    public Response atualizar(@Valid AtualizacaoJogoDto dto, @PathParam("id") int id) throws EntidadeNaoEncontradaException, SQLException {
        Jogo jogo = modelMapper.map(dto, Jogo.class);
        jogo.setCodigo(id);
        jogoDao.atualizar(jogo);
        return Response.ok().entity(modelMapper.map(jogo, DetalhesJogoDto.class)).build();
    }

    @DELETE
    @Path("{id}")
    public void remover(@PathParam("id") int id) throws EntidadeNaoEncontradaException, SQLException {
        jogoDao.remover(id);
    }

}
