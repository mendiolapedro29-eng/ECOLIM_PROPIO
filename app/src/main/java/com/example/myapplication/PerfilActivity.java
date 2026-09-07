package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class PerfilActivity extends AppCompatActivity {

    // Componentes del Formulario
    TextView imgAvatar;
    TextView btnCambiarFoto, btnEliminarFoto;

    EditText edtNombres, edtApellidos, edtFechaNacimiento, edtGenero, edtDocumentoIdentidad;
    EditText edtCorreo, edtTelefono, edtDireccion, edtCiudadDistrito;

    Button btnGuardarCambios, btnCerrarSesion;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // 1. Vincular componentes visuales
        imgAvatar = findViewById(R.id.imgAvatar);
        btnCambiarFoto = findViewById(R.id.btnCambiarFoto);
        btnEliminarFoto = findViewById(R.id.btnEliminarFoto);

        edtNombres = findViewById(R.id.edtNombres);
        edtApellidos = findViewById(R.id.edtApellidos);
        edtFechaNacimiento = findViewById(R.id.edtFechaNacimiento);
        edtGenero = findViewById(R.id.edtGenero);
        edtDocumentoIdentidad = findViewById(R.id.edtDocumentoIdentidad);

        edtCorreo = findViewById(R.id.edtCorreo);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtDireccion = findViewById(R.id.edtDireccion);
        edtCiudadDistrito = findViewById(R.id.edtCiudadDistrito);

        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // 2. Cargar datos actuales desde SharedPreferences
        preferences = getSharedPreferences("sesion", MODE_PRIVATE);

        edtNombres.setText(preferences.getString("nombre", ""));
        edtApellidos.setText(preferences.getString("apellidos", ""));
        edtFechaNacimiento.setText(preferences.getString("fecha_nacimiento", ""));
        edtGenero.setText(preferences.getString("genero", ""));

        // El documento de identidad se simula cargado de manera predeterminada y es de solo lectura
        edtDocumentoIdentidad.setText(preferences.getString("documento_identidad", "12345678"));

        edtCorreo.setText(preferences.getString("correo", ""));
        edtTelefono.setText(preferences.getString("telefono", ""));
        edtDireccion.setText(preferences.getString("direccion", ""));
        edtCiudadDistrito.setText(preferences.getString("ciudad_distrito", ""));

        // 3. Configurar selector de fecha interactivo para el cumpleaños
        edtFechaNacimiento.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectYear, selectMonth, selectDay) -> {
                String fechaFormateada = selectDay + "/" + (selectMonth + 1) + "/" + selectYear;
                edtFechaNacimiento.setText(fechaFormateada);
            }, year, month, day);
            datePickerDialog.show();
        });

        // 4. Gestión de Foto de Perfil (Simulado por el momento)
        btnCambiarFoto.setOnClickListener(v -> Toast.makeText(this, "Accediendo a la galería...", Toast.LENGTH_SHORT).show());
        btnEliminarFoto.setOnClickListener(v -> {
            imgAvatar.setText("👤");
            Toast.makeText(this, "Foto eliminada", Toast.LENGTH_SHORT).show();
        });

        // 5. Botón para Guardar los Cambios
        btnGuardarCambios.setOnClickListener(v -> {
            String strNombres = edtNombres.getText().toString().trim();
            String strApellidos = edtApellidos.getText().toString().trim();

            if (strNombres.isEmpty() || strApellidos.isEmpty()) {
                Toast.makeText(this, "Los nombres y apellidos son campos obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar todos los nuevos textos de manera local persistente
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("nombre", strNombres);
            editor.putString("apellidos", strApellidos);
            editor.putString("fecha_nacimiento", edtFechaNacimiento.getText().toString().trim());
            editor.putString("genero", edtGenero.getText().toString().trim());
            editor.putString("correo", edtCorreo.getText().toString().trim());
            editor.putString("telefono", edtTelefono.getText().toString().trim());
            editor.putString("direccion", edtDireccion.getText().toString().trim());
            editor.putString("ciudad_distrito", edtCiudadDistrito.getText().toString().trim());
            editor.apply();

            Toast.makeText(this, "Perfil actualizado con éxito", Toast.LENGTH_SHORT).show();
            finish(); // Cierra el formulario y regresa a la pantalla principal
        });

        // 6. Botón Cerrar Sesión
        btnCerrarSesion.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();
        });
    }
}
