package com.example.casa.proyectommmb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivityMMMB extends AppCompatActivity {
    DBHelperMMMB dbSQLITE;
    SQLiteDatabase db;
    EditText txtNombre, txtApellido, txtRecintoElectoral, txtAnoNacimiento, txtId;
    Button  btnInsertar, btnConsultaUno,btnModifcar, btnEliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mmmb);

        dbSQLITE=new DBHelperMMMB(this);

        //Consultar registro po Id
        btnConsultaUno = (Button) findViewById(R.id.btnConsultaUno);
        btnConsultaUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtId.getText().toString() == ""){
                    Toast.makeText(getBaseContext(), "Ingrese el ID", Toast.LENGTH_LONG).show();
                }
                else{
                    String sel[] = new String[]{
                            txtId.getText().toString()
                    };
                    Cursor c = db.rawQuery("SELECT * FROM VOTANTES_MMMB WHERE Id = ?",sel);
                    while(c.moveToNext()){
                        txtNombre.setText(c.getString(1));
                        txtApellido.setText(c.getString(2));
                        txtRecintoElectoral.setText(c.getString(3));
                        txtAnoNacimiento.setText(c.getInt(4));
                    }
                }
            }
        });

        //Modificar Registro
        btnModifcar = (Button)findViewById(R.id.btnModificar);
        btnModifcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object[] argumentos = new Object[]{
                        txtNombre.getText().toString(),
                        txtApellido.getText().toString(),
                        txtRecintoElectoral.getText().toString(),
                        Integer.parseInt(txtAnoNacimiento.getText().toString()),
                        Integer.parseInt(txtId.getText().toString())
                };
                db.execSQL("UPDATE VOTANTES_MMMB SET Nombre = ?, Apellido = ?, RecintoElectoral = ?, AnoNacimiento = ? WHERE Id = ?", argumentos);
                Toast.makeText(getBaseContext(), "Registro Actualizado..!!", Toast.LENGTH_LONG).show();
            }
        });

        //Eliminar Registro
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtId.getText().toString() == ""){
                    Toast.makeText(getBaseContext(), "Ingrese el ID", Toast.LENGTH_LONG).show();
                }
                else {
                    Object[] argumentos = new Object[]{
                            Integer.parseInt(txtId.getText().toString())
                    };
                    db.execSQL("DELETE FROM VOTANTES_MMMB WHERE Id = ?", argumentos);
                    Toast.makeText(getBaseContext(), "Registro Eliminado..!!", Toast.LENGTH_LONG).show();
                    txtId.setText("");
                }

            }
        });
    }

    public void insertarClick(View v){
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtRecintoElectoral = (EditText) findViewById(R.id.txtRecintoElectoral);
        txtAnoNacimiento = (EditText) findViewById(R.id.txtAnoNacimiento);


        boolean estaInsertado = dbSQLITE.insertar(txtNombre.getText().toString(), txtApellido.getText().toString(), txtRecintoElectoral.getText().toString(), Integer.parseInt(txtAnoNacimiento.getText().toString()));

        if (estaInsertado)
            Toast.makeText(MainActivityMMMB.this, "Datos Ingresados", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivityMMMB.this, "Ocurrió un Error", Toast.LENGTH_SHORT).show();
    }

    public void verTodoClick(View v){
        Cursor res  = dbSQLITE.verTodos();
        if (res.getCount() == 0){
            mostrarmensaje("Error", "No se encontraron datos");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()){
            buffer.append("Id : " + res.getString(0)+"\n");
            buffer.append("Nombre : " + res.getString(1)+"\n");
            buffer.append("Apellido : " + res.getString(2)+"\n");
            buffer.append("RecintoElectoral : " + res.getString(3)+"\n");
            buffer.append("AnoNacimiento : " + res.getInt(4)+"\n\n");
        }

        mostrarmensaje("Registros", buffer.toString());
    }

    public void mostrarmensaje(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.show();
    }

    //btnBuscar = (Button)findViewById(R.id.btnBuscar);
    //btnConsultaUno = (Button) findViewById(R.id.btnConsultaUno);
}
