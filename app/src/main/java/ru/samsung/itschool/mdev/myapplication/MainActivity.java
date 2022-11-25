package ru.samsung.itschool.mdev.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "MY_TAG";
    private Button bStart, btJustDoIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bStart = findViewById(R.id.btStart);
        btJustDoIt= findViewById(R.id.btJustDoIt);
        // устанавливаем обработчик на кнопку "Начать в потоке"
        btJustDoIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data myData = new Data.Builder()
                        .putString("keyA", "value1")
                        .putInt("keyB", 1)
                        .build();

                OneTimeWorkRequest work =
                        new OneTimeWorkRequest.Builder(MyWorker.class)
                                .setInputData(myData)
                                .build();
                WorkManager.getInstance(getApplicationContext()).enqueue(work);
                WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(work.getId())
                        .observe(MainActivity.this, new Observer<WorkInfo>() {
                            @Override
                            public void onChanged(WorkInfo workInfo) {
                                if (workInfo != null) {
                                    Log.d(TAG, "WorkInfo received: state: " + workInfo.getState());
                                    String message = workInfo.getOutputData().getString("keyC");
                                    int i = workInfo.getOutputData().getInt("keyD", 0);
                                    Log.d(TAG, "message: " + message + " " + i);
                                }
                            }
                        });

            }
        });

        // устанавливаем обработчик на кнопку "Начать не в потоке"
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Work is in progress");

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Work finished");
            }
        });

    }
}