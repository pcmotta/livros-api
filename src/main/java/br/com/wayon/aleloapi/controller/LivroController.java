package br.com.wayon.aleloapi.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wayon.aleloapi.model.Livro;
import br.com.wayon.aleloapi.service.LivroService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/livros")
public class LivroController
{
    @Autowired
    private LivroService service;
    
    @ApiOperation(value = "Retorna todos os livros")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(value = { "ADMIN", "USER" })
    public ResponseEntity<List<Livro>> todosLivros( )
    {
        List<Livro> livros = service.buscarTodos( );
        
        return ResponseEntity.ok( livros );
    }
    
    @ApiOperation(value = "Cadastra um livro")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed("ADMIN")
    public ResponseEntity<Livro> cadastrar( @RequestBody @Valid Livro livro )
    {
        Livro livroSalvo = service.salvar( livro );
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( livroSalvo );
    }
    
    @ApiOperation(value = "Atualizar um livro")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed("ADMIN")
    public ResponseEntity<Livro> atualizar( @RequestBody @Valid Livro livro, @PathVariable("id") Long id )
    {
        Livro livroSalvo = service.atualizar( livro, id );
        
        return ResponseEntity.ok( livroSalvo );
    }
    
    @ApiOperation(value = "Busca um livro por id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(value = { "ADMIN", "USER" })
    public ResponseEntity<Livro> buscarLivro( @PathVariable("id") Long id )
    {
        Livro livro = service.buscarPorId( id );
        
        return ResponseEntity.ok( livro );
    }
    
    @ApiOperation(value = "Exclui um livro")
    @DeleteMapping(value = "/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> excluir( @PathVariable("id") Long id )
    {
        service.excluir( id );
        
        return ResponseEntity.ok( ).build( );
    }
}
