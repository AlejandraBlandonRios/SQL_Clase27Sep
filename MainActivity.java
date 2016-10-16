package com.alejandrablandon.sql_clase27sep;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText eNombre,eTelefono,eCorreo;
    Button bInsertar,bActualizar,bBorrar,bBuscar;
    String Nombre,Correo, Telefono;

    ContactosSQLiteHelper Contactos;
    ContentValues dataBD;
    SQLiteDatabase dbContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Contactos = new ContactosSQLiteHelper(this,"ContactosDB",null,1);
        //Cliente = new ContactosSQLiteHelper(this,"ClienteDB",null,1);
        dbContactos = Contactos.getWritableDatabase();
        //dbCliente = Cliente.getWritableDatabase();
        //SQLiteQueryBuilder builder =new SQLiteQueryBuilder();
        //builder.setTables("Clientes");

        eNombre=(EditText)findViewById(R.id.eNombre);
        eTelefono=(EditText)findViewById(R.id.eTelefono);
        eCorreo=(EditText)findViewById(R.id.eCorreo);
        bInsertar=(Button)findViewById(R.id.bInsertar);
        bActualizar=(Button)findViewById(R.id.bActualizar);
        bBorrar=(Button)findViewById(R.id.bBorrar);
        bBuscar=(Button)findViewById(R.id.bBuscar);

        bInsertar.setOnClickListener(this);
        bActualizar.setOnClickListener(this);
        bBorrar.setOnClickListener(this);
        bBuscar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        Nombre=eNombre.getText().toString();
        Correo=eCorreo.getText().toString();
        Telefono=eTelefono.getText().toString();
        switch (id) {
            case R.id.bInsertar:
                dataBD=new ContentValues();
                dataBD.put("Nombre",Nombre);
                dataBD.put("Telefono",Telefono);
                dataBD.put("Correo",Correo);

                dbContactos.insert("Contactos",null,dataBD);
                //dbContactos.insert("Clientes",null,dataBD);
                //dbContactos.execSQL("INSERT INTO Contactos VALUES (null, '"+Nombre+"','"+Telefono+"','"+Correo+"')");
                break;
            case R.id.bBuscar:
                Cursor c=dbContactos.rawQuery("select * from Contactos where nombre'"+Nombre+"')",null);
                if(c.moveToFirst()){
                    eTelefono.setText(c.getString(2));
                    eCorreo.setText(c.getString(3));
                }

                break;
            case R.id.bActualizar:
                dataBD=new ContentValues();
                dataBD.put("Telefono",Telefono);
                dataBD.put("Correo",Correo);
                //dbContactos.update("Contactos", dataDB, "Nombre='"+Nombre+"'",null); Es lo mismo que la siguientes linea solo que en SQL
                dbContactos.execSQL("UPDATE Contactos SET Telefono='"+Telefono+"',Correo='"+Correo+"' WHERE Nombre='"+Nombre+"'");

                break;
            case R.id.bBorrar:
                //dbContactos.delete("Contactos","Nombre='"+Nombre+"'",null);
                dbContactos.execSQL("DELETE FROM Contactos WHERE Nombre='"+Nombre+"')");

                break;
        }
    }
}
