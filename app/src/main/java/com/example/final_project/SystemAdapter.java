package com.example.final_project;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
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
    private Activity activity;
    AlertDialog submitDialog = null;
    private String day;

    public SystemAdapter(Application application, Context context, Activity activity, String day) {
        myViewModel = MainViewModel.getInstance(application);
        myViewModel.setDay(day);
        myViewModel.init(application);
        lessonsList = myViewModel.getLessons().getValue();
        dataList = myViewModel.getData().getValue();
        this.context = context;
        this.activity = activity;
        this.day = day;

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

        //On Click- edit exist lesson
        holder.row_RelativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();

                //ls is the required lesson
                Lesson ls = lessonsList.get(holder.getAdapterPosition());
                editClass(ls, position,holder);
                notifyItemChanged(position);
                notifyDataSetChanged();
            }
        });


        //Long Press- delete this lesson from the list
        holder.row_RelativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteLesson(holder);
                myViewModel.setLessonLiveData(lessonsList);
                myViewModel.setDataLiveData(dataList);
                notifyDataSetChanged();

                return true;
            }
        });

        holder.bindData(lesson);//Bind the data to the raw item
    }

    public void deleteLesson(ViewHolder holder){
        int position = holder.getAdapterPosition();
        StringBuilder str = new StringBuilder();
        dataList = myViewModel.getData().getValue();

        //delete lesson from dataList
        for (int i = 0; i < dataList.size(); i++) {
            if (i != position) {
                str.append(dataList.get(i));
            }
        }
        context.deleteFile(lessonsList.get(position).getDay() + ".txt");
        if(str.length() > 0) {
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(lessonsList.get(position).getDay() + ".txt", Context.MODE_PRIVATE));
                outputStreamWriter.write(str.toString());
                outputStreamWriter.flush();
                outputStreamWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        dataList.remove(position);
        lessonsList.remove(position);

    }


    public void editClass(Lesson ls, int position, ViewHolder holder) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_layout, null);

        //Setting all the views

        EditText etProfession = (EditText) view.findViewById(R.id.etProfession);
        TimePicker tpStartTime = (TimePicker) view.findViewById(R.id.timePicker_Start);
        TimePicker tpEndTime = (TimePicker) view.findViewById(R.id.timePicker_End);
        EditText etLocation = (EditText) view.findViewById(R.id.etLocation);
        TextView tvAlert = (TextView) view.findViewById(R.id.tvAlert);
        Button btnSubmit = (Button) view.findViewById(R.id.bSubmit);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox) ;
        tpStartTime.setIs24HourView(true);
        tpEndTime.setIs24HourView(true);

        //set the current details of lesson
        etProfession.setText(ls.getProfession());
        etLocation.setText(ls.getLocation());
        checkBox.setChecked(ls.attendance);

        tpStartTime.setHour(ls.startHour);
        tpStartTime.setMinute((ls.startMinute));
        tpStartTime.setIs24HourView(true);

        tpEndTime.setHour(ls.endHour);
        tpEndTime.setMinute(ls.endMinute);
        tpEndTime.setIs24HourView(true);


        builder.setView(view);
        submitDialog = builder.create();
        submitDialog.setTitle("Enter the following details:");
        submitDialog.show();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get time from the time picker
                final int start_hour = tpStartTime.getHour();
                final int start_minute = tpStartTime.getMinute();
                final int end_hour = tpEndTime.getHour();
                final int end_minute = tpEndTime.getMinute();
                boolean attendance;

                if(checkBox.isChecked())
                    attendance = true;
                else
                    attendance = false;

                //Checking whether correct lesson times have been entered
                //Checking whether a profession has been entered
                if (TextUtils.isEmpty(etProfession.getText().toString())) {
                    tvAlert.setText("Must enter the profession!");
                    tvAlert.setVisibility(View.VISIBLE);
                } else if ((start_hour > end_hour) || ((start_hour == end_hour) && (start_minute > end_minute))) {
                    tvAlert.setText("End time must be after start time!");
                    tvAlert.setVisibility(View.VISIBLE);
                } else {
                    tvAlert.setVisibility(View.GONE);
                    MainViewModel myViewModel = MainViewModel.getInstance(activity.getApplication());
                    Lesson newLesson = new Lesson(etProfession.getText().toString(),etLocation.getText().toString(), start_hour,start_minute,end_hour,end_minute, day, attendance);


                    ///////////Deleting an existing class/////////////////////////
                    deleteLesson(holder);

                    myViewModel.addNewLesson(holder.itemView.getContext(), newLesson);
                    myViewModel.setLessonLiveData(lessonsList);
                    myViewModel.setDataLiveData(dataList);
                    submitDialog.dismiss();
                }
            }
        });
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