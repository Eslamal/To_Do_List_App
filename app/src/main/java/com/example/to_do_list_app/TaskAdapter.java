package com.example.to_do_list_app;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list_app.database.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks = new ArrayList<>();
    private OnItemLongClickListener longClickListener;
    private OnContextMenuItemClickListener contextMenuListener;
    private boolean showCheckboxes = false;

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = tasks.get(position);
        holder.titleTextView.setText(currentTask.getTitle());
        holder.descriptionTextView.setText(currentTask.getDescription());
        holder.checkBox.setVisibility(showCheckboxes ? View.VISIBLE : View.GONE);
        holder.checkBox.setChecked(currentTask.isSelected());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentTask.setSelected(isChecked);
        });
    }

    public void setShowCheckboxes(boolean showCheckboxes) {
        this.showCheckboxes = showCheckboxes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public Task getTaskAt(int position) {
        return tasks.get(position);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private CheckBox checkBox;

        public TaskViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.taskTitle);
            descriptionTextView = itemView.findViewById(R.id.taskDescription);
            checkBox = itemView.findViewById(R.id.checkbox_select);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (longClickListener != null && position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(tasks.get(position));
                        return false;
                    }
                    return false;
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");
            MenuItem edit = menu.add(Menu.NONE, 2, 2, "Edit");

            delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (contextMenuListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        contextMenuListener.onDeleteClick(tasks.get(getAdapterPosition()));
                    }
                    return true;
                }
            });

            edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (contextMenuListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        contextMenuListener.onEditClick(tasks.get(getAdapterPosition()));
                    }
                    return true;
                }
            });
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Task task);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public interface OnContextMenuItemClickListener {
        void onDeleteClick(Task task);
        void onEditClick(Task task);
    }

    public void setOnContextMenuItemClickListener(OnContextMenuItemClickListener listener) {
        this.contextMenuListener = listener;
    }
}
