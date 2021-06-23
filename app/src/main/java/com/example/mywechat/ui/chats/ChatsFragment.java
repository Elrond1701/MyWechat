package com.example.mywechat.ui.chats;

import android.database.sqlite.SQLiteDatabase;
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

import com.example.mywechat.util.CommonDatabase;
import com.example.mywechat.util.chatList;
import com.example.mywechat.R;
import com.example.mywechat.data.Chat;
import com.example.mywechat.util.chatList;

import java.util.LinkedList;

public class ChatsFragment extends Fragment {

    private ChatsViewModel chatsViewModel;
    private RecyclerView recyclerView;
    private chatList chatList;
    private LinkedList<Chat> chats;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chats = new LinkedList<>();
//        Chat chat1 = new Chat("FXL",1,"Hello World!","10:00");
//        Chat chat2 = new Chat("XLF",1,"So tired!","9:00");
//        chats.add(chat1);
//        chats.add(chat2);
//        chats = chatList.getChatList();
        CommonDatabase commonDatabase = new CommonDatabase(getActivity(),"chatlist",null,1);
        SQLiteDatabase db = commonDatabase.getReadableDatabase();
        chatList = new chatList();
        if (chatList.getChatList(db) != null){
            for (Chat chat :chatList.getChatList(db)){
                chats.add(chat);
            }
        }
        chatsViewModel = new ViewModelProvider(this).get(ChatsViewModel.class);
        chatsViewModel.setChats(chats);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.chats_recyclerview);
        ChatAdapter chatAdapter = new ChatAdapter(chatsViewModel.getChats());
        chatAdapter.setParent(getActivity());
        recyclerView.setAdapter(chatAdapter);
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearlayoutmanager);
    }
}