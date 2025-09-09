package com.example.executor;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button btnStart;

    // Executor con un solo hilo
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Handler para volver al hilo principal
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> iniciarContador());
    }

    private void iniciarContador() {
        executor.execute(() -> {
            for (int i = 10; i >= 0; i--) {
                int finalI = i;

                // Volvemos al hilo principal para actualizar la UI
                handler.post(() -> textView.setText("Contador: " + finalI));

                try {
                    Thread.sleep(1000); // 1 segundo entre cada número
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
