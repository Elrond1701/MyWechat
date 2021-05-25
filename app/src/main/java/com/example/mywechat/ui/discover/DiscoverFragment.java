package com.example.mywechat.ui.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Discover;
import com.example.mywechat.ui.chats.ChatAdapter;
import com.example.mywechat.ui.chats.ChatsViewModel;

import java.util.LinkedList;

public class DiscoverFragment extends Fragment {

//    private ChatsViewModel discoViewModel;
    private DiscoverViewModel discoViewModel;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinkedList<Discover> discovers=new LinkedList<>();
        Discover discover1 = new Discover("FXL",1,"啊！作业什么时候能够写完！真的写不完！","1小时前");
        Discover discover2 = new Discover("XLF",1,"啊！我好想可以写完作业，可是我真的写不完了！","2小时前");
        discovers.add(discover1);
        discovers.add(discover2);
        discoViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        discoViewModel.setDiscovers(discovers);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.discover_recyclerview);
        DiscoverAdapter discoverAdapter = new DiscoverAdapter(discoViewModel.getDiscovers());
        recyclerView.setAdapter(discoverAdapter);
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearlayoutmanager);
    }
}