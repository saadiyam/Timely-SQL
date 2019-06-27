package com.example.sqltodo.Adapters;

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sqltodo.Activities.ViewTask;
import com.example.sqltodo.DatabaseHelper;
import com.example.sqltodo.JSON.Task;
import com.example.sqltodo.R;

import java.util.ArrayList;
import java.util.Comparator;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.taskViewHolder> {

    Context context;
    ArrayList<Task> listTasks;


    public TaskAdapter(Context context, ArrayList<Task> listTasks) {
        this.context = context;
        this.listTasks = listTasks;
    }

    @Override
    public taskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.list_item, parent, false );

        return new taskViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder(taskViewHolder holder, final int position) {

        holder.taskName.setText( listTasks.get( position ).getName() );
        holder.taskDate.setText( listTasks.get( position ).getEndDate() );
        holder.desc.setText( listTasks.get( position ).getDesc() );

        holder.cardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, ViewTask.class );
                intent.putExtra( "name", listTasks.get( position ).getName() );
                context.startActivity( intent );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return listTasks.size();
    }

    public class taskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public TextView taskName;
        public TextView taskDate;
        public TextView desc;
        public CardView cardView;


        public taskViewHolder(View view) {
            super( view );
            taskName = view.findViewById( R.id.itemName );
            taskDate = view.findViewById( R.id.itemDate );
            desc = view.findViewById( R.id.itemDesc );
            cardView = view.findViewById( R.id.cardView );
            cardView.setOnCreateContextMenuListener( this );
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.add( this.getAdapterPosition(), 1, 0, "Delete Task" );
            menu.add( this.getAdapterPosition(), 2, 0, "Mark as complete" );

        }
    }

    public void removeItem(int position){
        DatabaseHelper databaseHelper = new DatabaseHelper( this.context );
        databaseHelper.deleteTask( listTasks.get( position ).getName() );
        listTasks.remove( position );
        notifyDataSetChanged();
    }

    public void markComplete(int position){
        DatabaseHelper databaseHelper = new DatabaseHelper( this.context );
        databaseHelper.marksAsCompleted( listTasks.get( position ).getName() );
        listTasks.remove( position );
        notifyDataSetChanged();
    }

    public void setEnabled(){

    }


    public void setDisabled(){
    }

}

