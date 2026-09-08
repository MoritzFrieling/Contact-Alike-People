package com.project.cap.UserInterface;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.cap.Backend.retrofit.Responses.GetInfoOfRequestorsResponse;
import com.project.cap.Entity.Notification;
import com.project.cap.Logic.LogicController;
import com.project.cap.R;
import com.project.cap.UserInterface.ReycleAdapterPackage.NotiRecycler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

public class NotiFragment extends Fragment{
    Context cxt;
    View root;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
       //TODO instantiate here
        root = inflater.inflate(R.layout.notification_page, container, false);
        RecyclerView parentRecycler = root.findViewById(R.id.notificationList);
        //TODO get list of noti from backend
        List<GetInfoOfRequestorsResponse> notiList = null;

        try {
            notiList = LogicController.getInstance().getFutureGateway().getInfoOfRequestorsAsync().get();
            notiList.forEach(n -> {
                System.out.println(n.uid);
                System.out.println(n.accepted);
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.recyclerInitHelper(parentRecycler,new NotiRecycler(notiList,cxt));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = view;
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

    public void setCxt(Context cxt) {
        this.cxt = cxt;
    }
}
