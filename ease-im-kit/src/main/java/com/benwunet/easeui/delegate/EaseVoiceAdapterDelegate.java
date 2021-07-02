package com.benwunet.easeui.delegate;

import android.view.View;
import android.view.ViewGroup;

import com.benwunet.easeui.interfaces.MessageListItemClickListener;
import com.benwunet.easeui.viewholder.EaseChatRowViewHolder;
import com.benwunet.easeui.viewholder.EaseVoiceViewHolder;
import com.hyphenate.chat.EMMessage;
import com.benwunet.easeui.model.styles.EaseMessageListItemStyle;
import com.benwunet.easeui.widget.chatrow.EaseChatRow;
import com.benwunet.easeui.widget.chatrow.EaseChatRowVoice;

/**
 * 声音代理类
 */
public class EaseVoiceAdapterDelegate extends EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder> {

    public EaseVoiceAdapterDelegate() {
    }

    public EaseVoiceAdapterDelegate(MessageListItemClickListener itemClickListener, EaseMessageListItemStyle itemStyle) {
        super(itemClickListener, itemStyle);
    }

    @Override
    public boolean isForViewType(EMMessage item, int position) {
        return item.getType() == EMMessage.Type.VOICE;
    }

    @Override
    protected EaseChatRow getEaseChatRow(ViewGroup parent, boolean isSender) {
        return new EaseChatRowVoice(parent.getContext(), isSender);
    }

    @Override
    protected EaseChatRowViewHolder createViewHolder(View view, MessageListItemClickListener itemClickListener) {
        return new EaseVoiceViewHolder(view, itemClickListener);
    }
}
