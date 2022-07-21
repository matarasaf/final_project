package com.example.final_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class SystemAdapter extends RecyclerView.Adapter<SystemAdapter.ViewHolder> {

    private ArrayList<Lesson> lessonsList;
    private ArrayList<String> dataList;
    private MainViewModel myViewModel;
    private Context context;
    private ViewHolder viewHolder;
    private int selectedRow = -1;

    public SystemAdapter(Application application, Context context, Activity activity, String day) {
        myViewModel = MainViewModel.getInstance(application, context, activity, day); ///// problemL day == null
        lessonsList = myViewModel.getLessons().getValue();
        dataList = myViewModel.getData().getValue();
        this.context = context;

        //observe data changes
        Observer<ArrayList<Lesson>> observeDataLessonChanges = new Observer<ArrayList<Lesson>>() {
            @Override
            public void onChanged(ArrayList<Lesson> list){
                lessonsList = list;
                notifyDataSetChanged();
            }
        };

        myViewModel.getLessons().observe((LifecycleOwner)context, observeDataLessonChanges);
    }

    //class represent one row by inflate.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View lessonView = inflater.inflate(R.layout.row_item, parent, false);

        // Return a new holder instance
        viewHolder = new ViewHolder(lessonView);
        return viewHolder;
    }

    //Put the information about one lesson from the lessonsList
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lesson lesson = lessonsList.get(position);

        // OBSERVE
        // Here we will observe and update the selected row
        Observer<Integer> observeSelectedIndex = new Observer<Integer>() {
            @Override
            //onChanged function- call when the data changed
            public void onChanged(Integer index) {
                selectedRow = index;
            }
        };

        myViewModel.getSelectedItemPosition().observe((LifecycleOwner)context, observeSelectedIndex);

        //Long Press- delete this lesson from the list
        holder.row_RelativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                StringBuilder str = new StringBuilder();
                dataList = myViewModel.getData().getValue();

                for (int i = 0; i < dataList.size(); i++) {
                    if (i != position) {
                        str.append(dataList.get(i));
                    }
                }
                context.deleteFile(lessonsList.get(position).getDay() + ".txt");

                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(lessonsList.get(position).getDay() + ".txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write(str.toString());
                    outputStreamWriter.flush();
                    outputStreamWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dataList.remove(position);
                lessonsList.remove(position);

                myViewModel.setLessonLiveData(lessonsList);
                myViewModel.setDataLiveData(dataList);

                notifyDataSetChanged();

                return true;
            }
        });

        holder.bindData(lesson);//Bind the data to the raw item
    }

    @Override
    public int getItemCount() {
        return lessonsList.size();
    }//Recycler items count

    //inner class
    public class ViewHolder extends RecyclerView.ViewHolder{
        //The information on each raw item in the recycler

        private TextView tvProfession;
        private TextView tvStartTime;
        private TextView tvEndTime;
        private TextView tvLocation;
        private TextView tvAttendance;
        RelativeLayout row_RelativeLayout;

        //constructor
        public ViewHolder(View itemView) {
            super(itemView);
            //Initialize the fields
            tvProfession = (TextView) itemView.findViewById(R.id.tvProfession);
            tvStartTime = (TextView) itemView.findViewById(R.id.tvStartTime);
            tvEndTime = (TextView) itemView.findViewById(R.id.tvEndTime);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            tvAttendance = (TextView) itemView.findViewById(R.id.tvAttendance);
            row_RelativeLayout = itemView.findViewById(R.id.system_item);
        }

        public void bindData( Lesson lesson){

            tvProfession.setText("Profession: " + lesson.getProfession());

            if(lesson.getStartHour() < 10){
                if (lesson.getStartMinute() < 10){
                    tvStartTime.setText("Start time: " + "0" + lesson.getStartHour() + ":" + "0" + lesson.getStartMinute());
                }
                else{
                    tvStartTime.setText("Start time: " + "0" + lesson.getStartHour() + ":" + lesson.getStartMinute());
                }
            }
            else if (lesson.getStartMinute() < 10){
                tvStartTime.setText("Start time: " + lesson.getStartHour() + ":" + "0" + lesson.getStartMinute());
            }
            else {
                tvStartTime.setText("Start time: " + lesson.getStartHour() + ":" + lesson.getStartMinute());
            }

            if(lesson.getEndHour() < 10){
                if (lesson.getEndMinute() < 10){
                    tvEndTime.setText("End time: " + "0" + lesson.getEndHour() + ":" + "0" + lesson.getEndMinute());
                }
                else{
                    tvEndTime.setText("End time: " + "0" + lesson.getEndHour() + ":" + lesson.getEndMinute());
                }
            }
            else if (lesson.getEndMinute() < 10){
                tvEndTime.setText("End time: " + lesson.getEndHour() + ":" + "0" + lesson.getEndMinute());
            }
            else {
                tvEndTime.setText("End time: " + lesson.getEndHour() + ":" + lesson.getEndMinute());
            }

            if(lesson.getLocation().equals("")){
                tvLocation.setText("");
            }
            else {
                tvLocation.setText("Location: " + lesson.getLocation());
            }
            if(lesson.getAttendance()) {
                tvAttendance.setText("Compulsory attendance! ");
            }
            else{
                tvAttendance.setText("");
            }
        }
    }

}
