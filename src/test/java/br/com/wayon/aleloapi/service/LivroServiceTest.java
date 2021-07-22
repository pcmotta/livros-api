package br.com.wayon.aleloapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.wayon.aleloapi.exception.NegocioException;
import br.com.wayon.aleloapi.model.Livro;
import br.com.wayon.aleloapi.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest
{
    @Mock
    private LivroRepository repository;
    
    @InjectMocks
    private LivroService service;
    
    @Test
    public void inserirLivro( )
    {
        final Livro livro = new Livro( null, "Titulo", "Autor", 1, 2021 );
        
        when( repository.save( livro ) ).then( invocation -> invocation.getArgument( 0 ) );
        
        Livro livroSalvo = service.salvar( livro );
        
        assertThat( livroSalvo ).isNotNull( );
        assertEquals( livroSalvo.getTitulo( ), livro.getTitulo( ) );
    }
    
    @Test
    public void alterarLivro( )
    {
        final Livro livro = new Livro( 1L, "Titulo", "Novo Autor", 1, 2021 );
        
        when( repository.save( livro ) ).thenReturn( livro );
        when( repository.findById( livro.getId( ) ) ).thenReturn( Optional.of( livro ) );
        
        Livro livroAlterado = service.atualizar( livro, 1L );
        
        assertThat( livroAlterado ).isNotNull( );
        assertEquals( "Novo Autor", livroAlterado.getAutor( ) );
    }
    
    @Test
    public void buscarLivroPorId( )
    {
        final Livro livro = new Livro( 1L, "Titulo", "Autor", 1, 2021 );
        
        when( repository.findById( livro.getId( ) ) ).thenReturn( Optional.of( livro ) );
        
        Livro livroBuscado = service.buscarPorId( 1L );
        
        assertThat( livroBuscado ).isNotNull( );
        assertEquals( 1L, livroBuscado.getId( ) );
        assertEquals( "Autor", livroBuscado.getAutor( ) );
    }
    
    @Test
    public void buscarLivroPorIdNaoExistente( )
    {
        when( repository.findById( 10L ) ).thenReturn( Optional.empty( ) );
        
        assertThrows( NegocioException.class, ( ) -> {
            service.buscarPorId( 10L );
        });
    }
    
    @Test
    public void buscarTodosLivros( )
    {
        List<Livro> livros = Arrays.asList( 
                new Livro( 1L, "Titulo", "Autor", 1, 2021 ),
                new Livro( 2L, "Titulo", "Autor", 1, 2021 ),
                new Livro( 3L, "Titulo", "Autor", 1, 2021 ) );
        
        when( repository.findAll( ) ).thenReturn( livros );
        
        List<Livro> livrosBuscados = service.buscarTodos( );
        
        assertThat( livrosBuscados ).isNotNull( );
        assertEquals( 3, livros.size( ) );
    }
    
    @Test
    public void excluirLivro( )
    {
        final Livro livro = new Livro( 1L, "Titulo", "Autor", 1, 2021 );
        
        when( repository.findById( livro.getId( ) ) ).thenReturn( Optional.of( livro ) );
        
        service.excluir( livro.getId( ) );
        
        verify( repository, times( 1 ) ).delete( livro );
    }
}
