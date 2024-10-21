package br.com.fiap.games.dao;

import br.com.fiap.games.exception.EntidadeNaoEncontradaException;
import br.com.fiap.games.model.Jogo;
import br.com.fiap.games.model.Plataforma;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JogoDao {

    private static final String SQL_SELECT_ALL = "select * from tb_jogo";
    private static final String SQL_SELECT_BY_ID = "select * from tb_jogo where cd_jogo = ?";
    private static final String SQL_INSERT = "insert into tb_jogo (cd_jogo, nm_jogo, ds_jogo, ds_plataforma, dt_lancamento, " +
            "vl_jogo, st_multiplayer, dt_cadastro, img_url) values (sq_tb_jogo.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "update tb_jogo set nm_jogo = ?, ds_jogo = ?, ds_plataforma = ?, " +
            "dt_lancamento = ?, vl_jogo = ?, st_multiplayer = ?, dt_atualizacao = ?, img_url = ? " +
            "where cd_jogo = ?";
    private static final String SQL_DELETE = "delete from tb_jogo where cd_jogo = ?";

    private Connection connection;

    public JogoDao(Connection connection){
        this.connection = connection;
    }

    public void cadastrar(Jogo jogo) throws SQLException {
        try (PreparedStatement stm = connection.prepareStatement(SQL_INSERT, new String[]{"cd_jogo"})){
            preencherStatment(jogo, stm);
            stm.executeUpdate();
            try (ResultSet generatedKeys = stm.getGeneratedKeys()){
                generatedKeys.next();
                jogo.setCodigo(generatedKeys.getInt(1));
            }
        }
    }

    public void atualizar(Jogo jogo) throws SQLException, EntidadeNaoEncontradaException {
        try (PreparedStatement stm = connection.prepareStatement(SQL_UPDATE)){
            preencherStatment(jogo, stm);
            stm.setInt(9 , jogo.getCodigo());
            if (stm.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("Jogo não encontrada para ser removido");
        }
    }


    public List<Jogo> listar() throws SQLException {
        try (PreparedStatement stm= connection.prepareStatement(SQL_SELECT_ALL)){
            List<Jogo> lista = new ArrayList<>();
            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                    Jogo jogo = parseJogo(resultSet);
                    lista.add(jogo);
                }
            }
            return lista;
        }
    }

    public Jogo pesquisarPorId(Integer id) throws SQLException, EntidadeNaoEncontradaException {
        try (PreparedStatement stm = connection.prepareStatement(SQL_SELECT_BY_ID)){
            stm.setInt(1, id);
            try (ResultSet resultSet = stm.executeQuery()) {
                if (resultSet.next()) {
                    return parseJogo(resultSet);
                } else{
                    throw new EntidadeNaoEncontradaException("Jogo não encontrado");
                }
            }
        }
    }

    public void remover(Integer id) throws SQLException, EntidadeNaoEncontradaException {
        try (PreparedStatement stm = connection.prepareStatement(SQL_DELETE)){
            stm.setInt(1, id);
            if (stm.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("Jogo não encontrada para ser removido");
        }
    }

    private static Jogo parseJogo(ResultSet resultSet) throws SQLException {
        Jogo jogo = new Jogo();
        jogo.setCodigo(resultSet.getInt("cd_jogo"));
        jogo.setNome(resultSet.getString("nm_jogo"));
        jogo.setDescricao(resultSet.getString("ds_jogo"));
        jogo.setValor(resultSet.getDouble("vl_jogo"));
        jogo.setImgUrl(resultSet.getString("img_url"));
        jogo.setDataCadastro(resultSet.getTimestamp("dt_cadastro").toLocalDateTime());
        if (resultSet.getTimestamp("dt_atualizacao") != null)
            jogo.setDataAtualizacao(resultSet.getTimestamp("dt_atualizacao").toLocalDateTime());
        jogo.setDataLancamento(resultSet.getDate("dt_lancamento").toLocalDate());
        jogo.setMultiplayer(resultSet.getBoolean("st_multiplayer"));
        jogo.setPlataforma(Plataforma.valueOf(resultSet.getString("ds_plataforma")));
        return jogo;
    }

    private static void preencherStatment(Jogo jogo, PreparedStatement stm) throws SQLException {
        stm.setString(1, jogo.getNome());
        stm.setString(2, jogo.getDescricao());
        stm.setString(3, jogo.getPlataforma().name());
        stm.setDate(4, Date.valueOf(jogo.getDataLancamento()));
        stm.setDouble(5, jogo.getValor());
        stm.setBoolean(6, jogo.getMultiplayer());
        stm.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        stm.setString(8, jogo.getImgUrl());
    }

}
