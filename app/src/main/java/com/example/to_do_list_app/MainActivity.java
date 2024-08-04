package com.example.to_do_list_app;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list_app.database.Task;
import com.example.to_do_list_app.database.TaskViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TASK_REQUEST = 1;
    public static final int EDIT_TASK_REQUEST = 2;

    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TaskAdapter adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST);
            }
        });

        adapter.setOnItemLongClickListener(new TaskAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Task task) {
                // You can use this to perform any action on long click if needed
            }
        });

        adapter.setOnContextMenuItemClickListener(new TaskAdapter.OnContextMenuItemClickListener() {
            @Override
            public void onDeleteClick(Task task) {
                showDeleteConfirmationDialog(task);
            }

            @Override
            public void onEditClick(Task task) {
                Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
                intent.putExtra(EditTaskActivity.EXTRA_ID, task.getId());
                intent.putExtra(EditTaskActivity.EXTRA_TITLE, task.getTitle());
                intent.putExtra(EditTaskActivity.EXTRA_DESCRIPTION, task.getDescription());
                startActivityForResult(intent, EDIT_TASK_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddTaskActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddTaskActivity.EXTRA_DESCRIPTION);

            Task task = new Task(title, description);
            taskViewModel.insert(task);

            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TASK_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditTaskActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Task can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(EditTaskActivity.EXTRA_TITLE);
            String description = data.getStringExtra(EditTaskActivity.EXTRA_DESCRIPTION);

            Task task = new Task(title, description);
            task.setId(id);
            taskViewModel.update(task);

            Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteConfirmationDialog(final Task task) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        taskViewModel.delete(task);
                        Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
