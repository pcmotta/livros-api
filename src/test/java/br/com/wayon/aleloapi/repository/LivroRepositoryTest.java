package br.com.wayon.aleloapi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.wayon.aleloapi.model.Livro;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LivroRepositoryTest
{
    @Autowired
    private LivroRepository repository;
    
    @Test
    public void inserirLivro( )
    {
        Livro livro = criarLivro( );
        
        repository.save( livro );
        Integer livros = repository.findAll( ).size( );
        
        assertEquals( 1, livros );
    }
    
    @Test
    public void atualizarLivro( )
    {
        Livro livro = criarLivro( );
        
        Livro livroSalvo = repository.save( livro );
        
        livroSalvo.setAno( 2020 );
        Livro livroAlterado = repository.save( livro );
        
        assertEquals( 2020, livroAlterado.getAno( ) );
    }
    
    @Test
    public void buscarLivroPorCodigo( )
    {
        Livro livro = criarLivro( );
        
        Livro livroSalvo = repository.save( livro );
        
        Optional<Livro> livroPorId = repository.findById( livroSalvo.getId( ) );
        
        Livro livroRecuperado = livroPorId.get( );
        
        assertEquals( livroSalvo.getId( ), livroRecuperado.getId( ) );
        assertEquals( livroSalvo.getAutor( ), livroRecuperado.getAutor( ) );
        assertEquals( livroSalvo.getTitulo( ), livroRecuperado.getTitulo( ) );
        assertEquals( livroSalvo.getEdicao( ), livroRecuperado.getEdicao( ) );
        assertEquals( livroSalvo.getAno( ), livroRecuperado.getAno( ) );
    }
    
    @Test
    public void excluirLivro( )
    {
        Livro livro = criarLivro( );
        Livro livroSalvo = repository.save( livro );
        
        Optional<Livro> livroPorId = repository.findById( livroSalvo.getId( ) );
        Livro livroRecuperado = livroPorId.get( );
        
        assertEquals( livroSalvo.getId( ), livroRecuperado.getId( ) );
        
        repository.delete( livroRecuperado );
        
        livroPorId = repository.findById( livroSalvo.getId( ) );
        
        assertEquals( livroPorId.isEmpty( ), true );
    }
    
    public Livro criarLivro( )
    {
        Livro livro = new Livro( );
        
        livro.setTitulo( "Livro 1" );
        livro.setAutor( "Autor 1" );
        livro.setEdicao( 1 );
        livro.setAno( 2021 );
        
        return livro;
    }
}
