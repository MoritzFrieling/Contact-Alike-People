package com.project.cap.UserInterface.ReycleAdapterPackage;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.project.cap.R;
import com.project.cap.Entity.Interest;
import com.project.cap.UserInterface.accountCreationActivity;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {
    private List<Interest> values;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // data items
        //TODO
        public Interest item;
        public ImageView image;
        public TextView interestName;
        public Spinner levelOfSkill;
        public TextView startDate;
        public View layout;
        public DatePickerDialog.OnDateSetListener dateSetListener;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            //TODO declare fields of view (row items)
            interestName = (TextView) v.findViewById(R.id.textInterestName);
            startDate = (TextView) v.findViewById(R.id.textStartDate);
            levelOfSkill = (Spinner) v.findViewById(R.id.spinnerLevel);
            //Making them disappear
            levelOfSkill.setVisibility(View.GONE);
            startDate.setVisibility(View.GONE);
        }
    }

    public void add(int position, Interest item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)

    /**
     * Constructor uses a list of AdapterItems. No methods need to be implemented. Its just so we can reuse this.
     * @param myDataset
     */
    public RecyclerAdapter(List<Interest> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.hobby_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Interest item = values.get(position);
        //TODO
        holder.item = item;
        holder.interestName.setText(item.getName());
        holder.interestName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //making them disappear again (on click)
                if(holder.levelOfSkill.getVisibility() == View.VISIBLE){
                    holder.levelOfSkill.setVisibility(View.GONE);
                    holder.startDate.setVisibility(View.GONE);
                }else {//making them appear again (on click)
                    holder.levelOfSkill.setVisibility(View.VISIBLE);
                    holder.startDate.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.startDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        holder.layout.getRootView().getContext(),//TODO adjust this to fit context aka name of file
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        holder.dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//color is transparent, why not
                dialog.show();//show the picker
            }
        });
        holder.dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;// month starts  at 0
                String date = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
                holder.startDate.setText(date);//display what we got
            }
        };

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
