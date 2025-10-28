package com.example.trabalho.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cidade;
    private Integer anoFundacao;

    // Armazena apenas o NOME do arquivo (ex: "flamengo.png" ou "uuid-gerado.png")
    // NUNCA armazene o arquivo binário no banco de dados para um sistema web.
    private String nomeEscudo;

    // Getters e Setters
    // (Omitidos por brevidade, mas você deve gerá-los na sua IDE)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public Integer getAnoFundacao() { return anoFundacao; }
    public void setAnoFundacao(Integer anoFundacao) { this.anoFundacao = anoFundacao; }
    public String getNomeEscudo() { return nomeEscudo; }
    public void setNomeEscudo(String nomeEscudo) { this.nomeEscudo = nomeEscudo; }
}