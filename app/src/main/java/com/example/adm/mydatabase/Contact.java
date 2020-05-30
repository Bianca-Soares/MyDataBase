package com.example.adm.mydatabase;

/**
 * Created by ADM on 26/03/2018.
 */

public class Contact
{
    int id;
    String name;
    String idade;
    String endereco;

    public Contact()
    {
    }

    public Contact(String name, String idade, String endereco)
    {
        this.name = name;
        this.idade = idade;
        this.endereco = endereco;

    }

    public Contact(int id, String name, String idade, String endereco)
    {
        this.id = id;
        this.name = name;
        this.idade = idade;
        this.endereco = endereco;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
