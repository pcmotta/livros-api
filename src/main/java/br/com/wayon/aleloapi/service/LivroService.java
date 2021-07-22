package br.com.wayon.aleloapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wayon.aleloapi.exception.NegocioException;
import br.com.wayon.aleloapi.model.Livro;
import br.com.wayon.aleloapi.repository.LivroRepository;

@Service
public class LivroService
{
    @Autowired
    private LivroRepository repository;
    
    public List<Livro> buscarTodos( )
    {
        return repository.findAll( );
    }
    
    public Livro salvar( Livro livro )
    {
        return repository.save( livro );
    }
    
    public Livro atualizar( Livro livro, Long id )
    {
        Livro livroSalvo = buscarPorId( id );
        
        BeanUtils.copyProperties( livro, livroSalvo, "id" );
        
        return salvar( livroSalvo );
    }
    
    public void excluir( Long id )
    {
        Livro livro = buscarPorId( id );
        
        repository.delete( livro );
    }
    
    public Livro buscarPorId( Long id )
    {
        Optional<Livro> livroSalvo = repository.findById( id );
        
        return livroSalvo.orElseThrow( ( ) -> new NegocioException( "Livro não existe" ) );
    }
}
