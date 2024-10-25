package com.example.ejemploactivitydevolverdatos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FacturaActivity extends AppCompatActivity {

    //VIEWS
    EditText etImporte;
    TextView tvPropina, tvPropina2;

    //LANZADOR
    ActivityResultLauncher<Intent> mLauncherCalculoPropinaActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            resultado -> {
                // mostrar todos los datos que me vienen de la segunda pantalla en esta pantalla
                if (resultado.getResultCode() == RESULT_OK) {
                    Intent data = resultado.getData();
                    actualizarUI (data);
                }
            }
    );

    private void actualizarUI(Intent data) {
        if (data != null) {
            if (data.hasExtra("propina")&& data.hasExtra("total")) {
                {
                    Bundle infoPropina = data.getExtras();
                    double propina = infoPropina.getDouble("propina");
                    double total = infoPropina.getDouble("total");
                    tvPropina.setText(String.valueOf(propina));
                    tvPropina2.setText(String.valueOf(total));
                }
                }
            }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initReferences();

    }

    private void initReferences() {
        etImporte = findViewById(R.id.etImporte);
        tvPropina = findViewById(R.id.tvPropina);
        tvPropina2 = findViewById(R.id.tvPropina2);
    }

    /**
     * Metodo que se ejecuta al pulsar el boton calcular propina
     * @param view el botón pulsado
     */
    public void lanzarCalculadoraPropinaActivity(View view) {
        String importeTexto = etImporte.getText().toString();
        if (importeTexto.isEmpty()) {
            etImporte.setError(getString(R.string.debes_introducir_un_importe));
        } else {
            Intent intent = new Intent(this, CalculoPropinaActivity.class);
            intent.putExtra("importe", Double.parseDouble(importeTexto));
            mLauncherCalculoPropinaActivity.launch(intent);
        }
    }
}