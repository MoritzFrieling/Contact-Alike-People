package com.project.cap.UserInterface.ReycleAdapterPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.cap.Backend.BackendController;
import com.project.cap.Backend.retrofit.Responses.GetInfoOfRequestorsResponse;
import com.project.cap.Entity.Notification;
import com.project.cap.Entity.SocialData;
import com.project.cap.Logic.LogicController;
import com.project.cap.R;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotiRecycler extends RecyclerView.Adapter<NotiRecycler.ViewHolderNote>{
    private List<GetInfoOfRequestorsResponse> values;
    private Context cxt;

    public class ViewHolderNote extends RecyclerView.ViewHolder{
        // data items
        //TODO
        public View layout;
        public Notification item;
        public TextView header;
        public LinearLayout requestWindow;
        public Button accept;
        public Button decline;
        public LinearLayout acceptedWindow;
        public boolean accepted = false;
        public RecyclerView recyclerView;
        public ViewHolderNote(View v) {
            super(v);
            layout = v;
            //TODO declare fields of view (row items)
            header = v.findViewById(R.id.notiHeader);
            requestWindow = v.findViewById(R.id.requestWindow);
            acceptedWindow = v.findViewById(R.id.acceptedWindow);
            accept = v.findViewById(R.id.acceptButton);
            decline = v.findViewById(R.id.declineButton);
            recyclerView = v.findViewById(R.id.recyclerSocialsAccepted);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNote holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final GetInfoOfRequestorsResponse item = values.get(position);
        //TODO setting the item values
        //holder.header.setText( "Somebody would like to get into Contact.");
        holder.accept.setOnClickListener(new View.OnClickListener() {//OnClick when accepted
            @Override
            public void onClick(View view) {
                holder.accepted = true;
                holder.header.setText("View Contact Information for somebody");
                holder.requestWindow.setVisibility(View.GONE);
                // load recycler
                List<SocialData> socialData = null;

                try {
                    socialData = LogicController.getInstance().getFutureGateway().getSocialsByIdAsync(item.getUid()).get();
                    recyclerInitHelper(holder.recyclerView, new RecyclerAdapterSocialsOverview(socialData));
                    holder.acceptedWindow.setVisibility(View.VISIBLE);

                    //send confirmation to requesting user
                    LogicController.getInstance().getFutureGateway().sendAcceptedAsync(item.getUid());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }//closes request window and opens acceptedWindow
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {//OnClick when decline
            @Override
            public void onClick(View view) {
                //delete noti?
                holder.requestWindow.setVisibility(View.GONE);//close window for now
            }
        });
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.accepted){//accepted only open acceptedWindow
                    holder.requestWindow.setVisibility(View.GONE);//making sure the view is gone
                    if(holder.acceptedWindow.getVisibility() == View.VISIBLE){//makes accepted Window appear and disappear
                        holder.acceptedWindow.setVisibility(View.GONE);
                    }else{
                        holder.acceptedWindow.setVisibility(View.VISIBLE);
                    }

                }else{//not accepted, only open requestWindow
                    //hide and show when needed
                    if(holder.requestWindow.getVisibility() == View.VISIBLE){//makes request Window appear and disappear
                        holder.requestWindow.setVisibility(View.GONE);
                    }else{
                        holder.requestWindow.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }
    /**
     * Constructor uses a list of AdapterItems. No methods need to be implemented. Its just so we can reuse this.
     * @param myDataset
     */
    public NotiRecycler(List<GetInfoOfRequestorsResponse> myDataset, Context cxt) {
        values = myDataset;
        this.cxt = cxt;
    }
    @NonNull
    @Override
    public ViewHolderNote onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.noti_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolderNote vh = new ViewHolderNote(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void add(int position, GetInfoOfRequestorsResponse item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Simplifies init of recyclers.
     * @param recycler
     * @param adapter
     */
    private void recyclerInitHelper(RecyclerView recycler, RecyclerView.Adapter adapter){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(cxt);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

    }
}
