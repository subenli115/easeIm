package com.benwunet.msg.section.chat.viewholder;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.benwunet.easeui.interfaces.MessageListItemClickListener;
import com.benwunet.easeui.viewholder.EaseChatRowViewHolder;
import com.benwunet.msg.section.chat.views.ChatRowRecall;

public class ChatRecallViewHolder extends EaseChatRowViewHolder {

    public ChatRecallViewHolder(@NonNull View itemView, MessageListItemClickListener itemClickListener) {
        super(itemView, itemClickListener);
    }

    public static ChatRecallViewHolder create(ViewGroup parent, boolean isSender,
                                              MessageListItemClickListener listener) {
        return new ChatRecallViewHolder(new ChatRowRecall(parent.getContext(), isSender), listener);
    }


}
