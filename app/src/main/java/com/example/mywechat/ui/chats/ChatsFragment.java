package com.example.mywechat.ui.chats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Chat;

import java.util.LinkedList;

public class ChatsFragment extends Fragment {

    private ChatsViewModel chatsViewModel;
    private RecyclerView recyclerView;
//    private LinkedList<Chat>

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinkedList<Chat> chats=new LinkedList<>();
        Chat chat = new Chat("FXL",1,"Hello World!","10:00");
        chats.add(chat);
        chatsViewModel = new ViewModelProvider(this).get(ChatsViewModel.class);
        chatsViewModel.setChats(chats);
    }


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.chats_recyclerview);
        ChatAdapter chatAdapter = new ChatAdapter(chatsViewModel.getChats());
        recyclerView.setAdapter(chatAdapter);
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearlayoutmanager);
    }
}