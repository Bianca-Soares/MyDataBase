package com.example.adm.mydatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // principais botoes
    Button btCadastro;
    Button btTotal;
    Button btMostrarTodos;

    Button btCadastrar;
    Button btConsultar;
    Button btAlterar;
    Button btDeletar;

    LinearLayout layoutBuscarId;
    LinearLayout layoutRegistrar;
    LinearLayout layoutBotoes;

    TextView exibirTodos;

    EditText editNome;
    EditText editIdade;
    EditText editEnd;

    EditText editBuscar;

    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Menu Principal
        btCadastro = (Button) findViewById(R.id.btCadastro);
        btTotal = (Button) findViewById(R.id.btTotal);
        btMostrarTodos = (Button) findViewById(R.id.btMostrarTodos);

        //Buscar
        layoutBuscarId = (LinearLayout) findViewById(R.id.layoutBuscarId);
        editBuscar = (EditText) findViewById(R.id.editBuscar);
        btConsultar = (Button) findViewById(R.id.btConsultar);

        //Registrar
        layoutRegistrar = (LinearLayout) findViewById(R.id.layoutRegistrar);
        btCadastrar = (Button) findViewById(R.id.btCadastrar);
        editNome = (EditText) findViewById(R.id.editNome);
        editIdade = (EditText) findViewById(R.id.editIdade);
        editEnd = (EditText) findViewById(R.id.editEnd);

        //Alterar Deletar
        layoutBotoes = (LinearLayout) findViewById(R.id.layoutBotoes);
        btAlterar = (Button) findViewById(R.id.btAlterar);
        btDeletar = (Button) findViewById(R.id.btDeletar);

        exibirTodos = (TextView) findViewById(R.id.exibirTodos);

        //Banco de Dados
        final DatabaseHelper db = new DatabaseHelper(this);

        if (db.quantDeRegistros() == 0)
            layoutBuscarId.setVisibility(View.INVISIBLE);

        //Limpar tela
        limparTudo();

        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             limparTudo();
             limparCampos();
             layoutRegistrar.setVisibility(View.VISIBLE);
             btCadastrar.setVisibility(View.VISIBLE);
             layoutBotoes.setVisibility(View.VISIBLE);
            }
        });
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(editNome.getText().toString().isEmpty() || editIdade.getText().toString().isEmpty() || editEnd.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), " Campos em branco", Toast.LENGTH_SHORT).show();

            }else{
                db.addContact(new Contact(editNome.getText().toString(), editIdade.getText().toString(), editEnd.getText().toString()));
                limparCampos();
                Toast.makeText(getApplicationContext(), " Registro cadastrado!", Toast.LENGTH_SHORT).show();
                layoutBuscarId.setVisibility(View.VISIBLE);
            }
            }
        });

        btTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             limparTudo();
              text = "Total de Registros: " + db.quantDeRegistros();

              if (db.quantDeRegistros() < 1)
                  Toast.makeText(getApplicationContext(), "Sem Registros", Toast.LENGTH_SHORT).show();

              exibirTodos.setVisibility(View.VISIBLE);
              exibirTodos.setText(text);
            }
        });

        btMostrarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             limparTudo();
             List<Contact> contacts = db.getAllContacts();
                if (db.quantDeRegistros() >= 1) {
                    text = " ";

                    for (Contact c : contacts) {
                        String log = "ID: " + c.getId() + ", NAME: " + c.getName() + ", IDADE: " + c.getIdade() + "\n";
                        text = text + log;
                    }

                } else {
                    text = "Nada encontrado";
                    Toast.makeText(getApplicationContext(), "Sem Registros", Toast.LENGTH_SHORT).show();
            }
            exibirTodos.setVisibility(View.VISIBLE);
            exibirTodos.setText(text);
            }
        });

        btConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (editBuscar.getText().toString().isEmpty()) {
                  Toast.makeText(getApplicationContext(), "Id vazio!", Toast.LENGTH_SHORT).show();
             }else {

                 int id = Integer.parseInt(editBuscar.getText().toString());
                 Contact c = db.getContact(id);
                 if (c != null) {
                     limparTudo();
                     layoutRegistrar.setVisibility(View.VISIBLE);
                     layoutBotoes.setVisibility(View.VISIBLE);
                     btAlterar.setVisibility(View.VISIBLE);
                     btDeletar.setVisibility(View.VISIBLE);

                     editNome.setText(c.name);
                     editIdade.setText(c.idade);
                     editEnd.setText(c.endereco);

                 } else {
                         Toast.makeText(getApplicationContext(), "Nenhum Registro encontrado com o ID ", Toast.LENGTH_SHORT).show();
                     }
                }
            }
        });

        btDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(editBuscar.getText().toString());
                db.deleteRegistro(id);
                limparTudo();
                Toast.makeText(getApplicationContext(), "Registro Apagado!", Toast.LENGTH_SHORT).show();
                editBuscar.setText("");
                if (db.quantDeRegistros() == 0) {
                    layoutBuscarId.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Todos Os Registros Apagados!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editNome.getText().toString().isEmpty() || editIdade.getText().toString().isEmpty() || editEnd.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Campos em branco", Toast.LENGTH_SHORT).show();

                }else {
                    int id = Integer.parseInt(editBuscar.getText().toString());
                    String nome = editNome.getText().toString();
                    String idade = editIdade.getText().toString();
                    String endereco = editEnd.getText().toString();

                    db.updateContact(new Contact(id, nome, idade, endereco));

                    Toast.makeText(getApplicationContext(), nome + " Alterado!", Toast.LENGTH_SHORT).show();
                    limparTudo();
                }
            }
        });

    }
    void limparTudo(){
        exibirTodos.setVisibility(View.INVISIBLE);
        layoutBotoes.setVisibility(View.INVISIBLE);
        btCadastrar.setVisibility(View.INVISIBLE);
        btAlterar.setVisibility(View.INVISIBLE);
        btDeletar.setVisibility(View.INVISIBLE);
        layoutRegistrar.setVisibility(View.INVISIBLE);
    }
    void limparCampos () {

        editBuscar.setText("");
        editNome.setText("");
        editIdade.setText("");
        editEnd.setText("");
    }
}
