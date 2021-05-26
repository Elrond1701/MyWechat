package com.example.mywechat.ui.chats;

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

import com.example.mywechat.R;

public class ChatsFragment extends Fragment {

    private ChatsViewModel chatsViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinkedList<Chat> chats=new LinkedList<>();
        Chat chat1 = new Chat("FXL",1,"Hello World!","10:00");
        Chat chat2 = new Chat("XLF",1,"So tired!","9:00");
        chats.add(chat1);
        chats.add(chat2);
        chatsViewModel = new ViewModelProvider(this).get(ChatsViewModel.class);
        chatsViewModel.setChats(chats);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatsViewModel =
                new ViewModelProvider(this).get(ChatsViewModel.class);
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }
}