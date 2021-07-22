package br.com.wayon.aleloapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "livro")
public class Livro
{
    public Livro( )
    {
        
    }
    
    public Livro( Long id,
            @NotNull(message = "titulo é obrigatório") @Size(max = 50, message = "titulo deve ter no máximo 50 caracteres") String titulo,
            @NotNull(message = "autor é obrigatório") String autor, int edicao,
            @NotNull(message = "ano é obrigatório") Integer ano )
    {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.edicao = edicao;
        this.ano = ano;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("Título do livro")
    @NotNull(message = "titulo é obrigatório")
    @Size(max = 50, message = "titulo deve ter no máximo 50 caracteres")
    private String titulo;
    
    @ApiModelProperty("Autor do livro")
    @NotNull(message = "autor é obrigatório")
    private String autor;
    
    @ApiModelProperty("Edição do livro")
    private int edicao;
    
    @ApiModelProperty("Ano de Lançamento")
    @NotNull(message = "ano é obrigatório")
    private Integer ano;

    public Long getId( )
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getTitulo( )
    {
        return titulo;
    }

    public void setTitulo( String titulo )
    {
        this.titulo = titulo;
    }

    public String getAutor( )
    {
        return autor;
    }

    public void setAutor( String autor )
    {
        this.autor = autor;
    }

    public int getEdicao( )
    {
        return edicao;
    }

    public void setEdicao( int edicao )
    {
        this.edicao = edicao;
    }

    public Integer getAno( )
    {
        return ano;
    }

    public void setAno( Integer ano )
    {
        this.ano = ano;
    }
}
