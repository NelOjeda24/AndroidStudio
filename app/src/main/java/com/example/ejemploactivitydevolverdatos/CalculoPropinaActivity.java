package com.example.ejemploactivitydevolverdatos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculoPropinaActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvImporteRecibido;
    RadioGroup rgPorcentajesPropina;
    Button btCalcularPropina, btCancelar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calcular_propina_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.calcularPropina), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initReferences();
        // RECOGEMOS DATOS
        Intent iDatos = getIntent();
        if (iDatos != null) {
            String importe = iDatos.getStringExtra("importe");
            tvImporteRecibido.setText(importe);
        }
        setListenersToButtons();
    }

    private void setListenersToButtons() {
        btCalcularPropina.setOnClickListener(this);
        btCancelar.setOnClickListener(this);
    }

    /**
     * Método que inicializa los elementos de la vista
     */
    private void initReferences() {
        tvImporteRecibido = findViewById(R.id.tvImporteSinPropina);
        rgPorcentajesPropina = findViewById(R.id.rgPorcentajesPropina);
        btCalcularPropina = findViewById(R.id.btCalcularPropinaPorcentaje);
        btCancelar = findViewById(R.id.btCancelar);

    }

    /**
     * Método que se ejecuta al pulsar un botón
     * @param view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btCalcularPropinaPorcentaje) {
            // Boton calcular propina
            Bundle infoPropinaDevolver = calcularPropinaSegunPorcentajeSeleccionado(tvImporteRecibido);
            // Devolvemos los datos a la pantalla anterior
            Intent iDevolverDatos = new Intent();
            iDevolverDatos.putExtras(infoPropinaDevolver);
            setResult(RESULT_OK, iDevolverDatos);
            finish();

        }
    }
}