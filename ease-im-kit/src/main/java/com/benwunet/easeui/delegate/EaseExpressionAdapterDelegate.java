package com.benwunet.easeui.delegate;

import android.view.View;
import android.view.ViewGroup;

import com.benwunet.easeui.constants.EaseConstant;
import com.hyphenate.chat.EMMessage;
import com.benwunet.easeui.interfaces.MessageListItemClickListener;
import com.benwunet.easeui.model.styles.EaseMessageListItemStyle;
import com.benwunet.easeui.viewholder.EaseChatRowViewHolder;
import com.benwunet.easeui.viewholder.EaseExpressionViewHolder;
import com.benwunet.easeui.widget.chatrow.EaseChatRow;
import com.benwunet.easeui.widget.chatrow.EaseChatRowBigExpression;

/**
 * 表情代理类
 */
public class EaseExpressionAdapterDelegate extends EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder> {

    public EaseExpressionAdapterDelegate() {
        super();
    }

    public EaseExpressionAdapterDelegate(MessageListItemClickListener itemClickListener, EaseMessageListItemStyle itemStyle) {
        super(itemClickListener, itemStyle);
    }

    @Override
    public boolean isForViewType(EMMessage item, int position) {
        return item.getType() == EMMessage.Type.TXT && item.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false);
    }

    @Override
    protected EaseChatRow getEaseChatRow(ViewGroup parent, boolean isSender) {
        return new EaseChatRowBigExpression(parent.getContext(), isSender);
    }

    @Override
    protected EaseChatRowViewHolder createViewHolder(View view, MessageListItemClickListener itemClickListener) {
        return new EaseExpressionViewHolder(view, itemClickListener);
    }
}
