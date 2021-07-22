package br.com.wayon.aleloapi.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.wayon.aleloapi.AleloApi1Application;
import br.com.wayon.aleloapi.model.Livro;
import br.com.wayon.aleloapi.service.LivroService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AleloApi1Application.class)
@AutoConfigureMockMvc
public class LivroControllerTest
{
    @Autowired
    private MockMvc mock;
    
    @Autowired
    private LivroService service;
    
    private ObjectMapper objectMapper = new ObjectMapper( );
    
    @Test
    public void criarLivro( ) throws Exception
    {
        final Livro livro = new Livro( null, "Titulo", "Autor", 1, 2021 );
        
        mock.perform( post( "/livros" )
                .header( "Authorization", "Basic YWRtaW5pc3RyYWRvcjphZG1pbmlzdHJhZG9yc2VuaGE=" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( objectMapper.writeValueAsString( livro ) ) )
            .andExpect( status( ).isCreated( ) )
            .andExpect( jsonPath( "$.titulo", is( livro.getTitulo( ) ) ) )
            .andExpect( jsonPath( "$.autor", is( livro.getAutor( ) ) ) )
            .andExpect( jsonPath( "$.edicao", is( livro.getEdicao( ) ) ) )
            .andExpect( jsonPath( "$.ano", is( livro.getAno( ) ) ) );
    }
    
    @Test
    public void criarLivroSemPermissao( ) throws Exception
    {
        final Livro livro = new Livro( null, "Titulo", "Autor", 1, 2021 );
        
        mock.perform( post( "/livros" )
                .header( "Authorization", "Basic dXN1YXJpbzp1c3Vhcmlvc2VuaGE=" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( objectMapper.writeValueAsString( livro ) ) )
            .andExpect( status( ).isForbidden( ) );
    }
    
    @Test
    public void alterarLivro( ) throws Exception
    {
        final Livro livroAntigo = new Livro( null, "Titulo", "Autor", 1, 2021 );
        final Livro livro = new Livro( null, "Novo Titulo", "Novo Autor", 1, 2021 );
        
        Livro livroSalvo = service.salvar( livroAntigo );
        livro.setId( livroSalvo.getId( ) );
        
        mock.perform( put( "/livros/{id}", livro.getId( ) )
                .header( "Authorization", "Basic YWRtaW5pc3RyYWRvcjphZG1pbmlzdHJhZG9yc2VuaGE=" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( objectMapper.writeValueAsString( livro ) ) )
            .andExpect( status( ).is( HttpStatus.OK.value( ) ) )
            .andExpect( jsonPath( "$.titulo", is( livro.getTitulo( ) ) ) )
            .andExpect( jsonPath( "$.autor", is( livro.getAutor( ) ) ) )
            .andExpect( jsonPath( "$.edicao", is( livro.getEdicao( ) ) ) )
            .andExpect( jsonPath( "$.ano", is( livro.getAno( ) ) ) );
    }
    
    @Test
    public void buscarLivroPorId( ) throws Exception
    {
        final Livro livro = new Livro( null, "Novo Titulo", "Novo Autor", 1, 2021 );
        
        Livro livroSalvo =  service.salvar( livro );
            
        mock.perform( get( "/livros/{id}", livroSalvo.getId( ) )
                .header( "Authorization", "Basic YWRtaW5pc3RyYWRvcjphZG1pbmlzdHJhZG9yc2VuaGE=" )
                .contentType( MediaType.APPLICATION_JSON ) )
            .andExpect( status( ).is( HttpStatus.OK.value( ) ) )
            .andExpect( jsonPath( "$.titulo", is( livro.getTitulo( ) ) ) )
            .andExpect( jsonPath( "$.autor", is( livro.getAutor( ) ) ) )
            .andExpect( jsonPath( "$.edicao", is( livro.getEdicao( ) ) ) )
            .andExpect( jsonPath( "$.ano", is( livro.getAno( ) ) ) );
    }
    
    
    @Test
    public void buscarTodosLivros( ) throws Exception
    {
        mock.perform( get( "/livros" )
                .header( "Authorization", "Basic YWRtaW5pc3RyYWRvcjphZG1pbmlzdHJhZG9yc2VuaGE=" )
                .contentType( MediaType.APPLICATION_JSON ) )
            .andExpect( status( ).is( HttpStatus.OK.value( ) ) )
            .andExpect( jsonPath( "$", isA( List.class ) ) );
    }
    
    @Test
    public void excluirLivro( ) throws Exception
    {
        final Livro livro = new Livro( null, "Novo Titulo", "Novo Autor", 1, 2021 );
        Livro livroSalvo =  service.salvar( livro );    
        
        mock.perform( get( "/livros/{id}", livroSalvo.getId( ) )
                .header( "Authorization", "Basic YWRtaW5pc3RyYWRvcjphZG1pbmlzdHJhZG9yc2VuaGE=" )
                .contentType( MediaType.APPLICATION_JSON ) )
            .andExpect( status( ).isOk( ) );
        
        mock.perform( delete( "/livros/{id}", livroSalvo.getId( ) )
                .header( "Authorization", "Basic YWRtaW5pc3RyYWRvcjphZG1pbmlzdHJhZG9yc2VuaGE=" )
                .contentType( MediaType.APPLICATION_JSON ) )
            .andExpect( status( ).is( HttpStatus.OK.value( ) ) );
        
        mock.perform( get( "/livros/{id}", livroSalvo.getId( ) )
                .header( "Authorization", "Basic YWRtaW5pc3RyYWRvcjphZG1pbmlzdHJhZG9yc2VuaGE=" )
                .contentType( MediaType.APPLICATION_JSON ) )
            .andExpect( status( ).isBadRequest( ) );
    }
}
