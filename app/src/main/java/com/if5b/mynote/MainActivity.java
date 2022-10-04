package com.if5b.mynote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.if5b.mynote.databinding.ActivityMainBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private File path;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        path = getFilesDir();
    }

    private void newFile() {
        binding.etTitle.setText("");
        binding.etFile.setText("");
        Toast.makeText(this, "Clearing Files", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title){
        FileModel fileModel = FileHelper.readFormFile(this, title);
        binding.etTitle.setText(fileModel.getFilename());
        binding.etFile.setText(fileModel.getData());

        Toast.makeText(this, "Loading.." + fileModel.getFilename() + "data", Toast.LENGTH_SHORT).show();
    }

    private void openFile() {
        showList();
    }

    private void showList(){
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, path.list());

        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                loadData(items[which].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void saveFile(){
        if (binding.etTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi", Toast.LENGTH_SHORT).show();
        } else if (binding.etFile.getText().toString().isEmpty()) {
            Toast.makeText(this, "Konten harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = binding.etTitle.getText().toString();
            String text = binding.etFile.getText().toString();
            FileModel fileModel = new FileModel();
            fileModel.setFilename(title);
            fileModel.setData(text);
            FileHelper.writeToFile(fileModel, this);
            Toast.makeText(this, "Saving" + fileModel.getFilename() + " file", Toast.LENGTH_SHORT).show();
        }
    }
}

