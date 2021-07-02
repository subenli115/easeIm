package com.benwunet.easeui.delegate;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.benwunet.easeui.manager.EaseAtMessageHelper;
import com.benwunet.easeui.manager.EasePreferenceManager;
import com.benwunet.easeui.utils.StringUtils;
import com.benwunet.easeui.utils.TimeFormat;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.benwunet.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.benwunet.easeui.adapter.EaseBaseDelegate;
import com.benwunet.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.benwunet.easeui.domain.EaseAvatarOptions;
import com.benwunet.easeui.domain.EaseUser;
import com.benwunet.easeui.utils.EaseCommonUtils;
import com.benwunet.easeui.utils.EaseSmileUtils;
import com.benwunet.easeui.widget.EaseImageView;

/**
 * 会话条目代理
 */
public class ConversationDelegate extends EaseBaseDelegate<EMConversation, ConversationDelegate.ViewHolder> {
    @Override
    public boolean isForViewType(EMConversation item, int position) {
        return item != null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ease_item_row_chat_history;
    }

    @Override
    protected ConversationDelegate.ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends EaseBaseRecyclerViewAdapter.ViewHolder<EMConversation> {
        private ConstraintLayout listIteaseLayout;
        private EaseImageView avatar;
        private TextView mUnreadMsgNumber;
        private TextView name;
        private TextView time;
        private TextView tvBelong;
        private TextView temp;
        private ImageView mMsgState;
        private TextView mentioned;
        private TextView message;
        private Context mContext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View itemView) {
            mContext = itemView.getContext();
            listIteaseLayout = findViewById(R.id.list_itease_layout);
            avatar = findViewById(R.id.avatar);
            mUnreadMsgNumber = findViewById(R.id.unread_msg_number);
            name = findViewById(R.id.name);
            time = findViewById(R.id.time);
            mMsgState = findViewById(R.id.msg_state);
            mentioned = findViewById(R.id.mentioned);
            message = findViewById(R.id.message);
            EaseAvatarOptions avatarOptions = EaseIM.getInstance().getAvatarOptions();
            if(avatarOptions != null) {
                avatar.setShapeType(avatarOptions.getAvatarShape());
            }

        }

        @Override
        public void setData(EMConversation object, int position) {
            EMConversation item = (EMConversation) object;
            String username = item.conversationId();
            EaseUser easeUser = EaseIM.getInstance().getUserProvider().getUser(username);
            listIteaseLayout.setBackground(!TextUtils.isEmpty(item.getExtField())
                    ? ContextCompat.getDrawable(mContext, R.drawable.ease_conversation_top_bg)
                    : null);
            mentioned.setVisibility(View.GONE);
            tvBelong.setVisibility(View.GONE);
            temp.setVisibility(View.GONE);

            if (item.getType() == EMConversation.EMConversationType.GroupChat) {
                if (EaseAtMessageHelper.get().hasAtMeMsg(username)) {
                    mentioned.setText(R.string.were_mentioned);
                    mentioned.setVisibility(View.VISIBLE);
                }
                if(easeUser!=null){
                    if (!StringUtils.isEmpty(easeUser.getAvatar())) {
                        Glide.with(mContext).load(easeUser.getAvatar()).into(avatar);
                    }
                    if (!StringUtils.isEmpty(easeUser.getNickname())) {
                        name.setText(easeUser.getNickname());
                    }
                }

            }  else {
                if (easeUser != null) {
                    if (StringUtils.equals("1",easeUser.getIsTemporary())) {
                        temp.setVisibility(View.VISIBLE);
                    }else {
                        temp.setVisibility(View.GONE);
                    }
                    String belong = easeUser.getBelong();
                    String orgType = easeUser.getOrgType();
                    if (!StringUtils.isEmpty(orgType) && orgType.equals("1") && !StringUtils.isEmpty(belong)) {
                        tvBelong.setVisibility(View.VISIBLE);
                        tvBelong.setText("@"+belong);
                    }
                    Glide.with(mContext).load(easeUser.getAvatar()).into(avatar);
                    name.setText(easeUser.getNickname());
                }else {
                    avatar.setImageResource(R.drawable.ease_default_avatar);
                    name.setText(username);
                }
            }


            if (item.getUnreadMsgCount() > 0) {
                mUnreadMsgNumber.setVisibility(View.VISIBLE);
                mUnreadMsgNumber.setText(String.valueOf(item.getUnreadMsgCount()));
            } else {
                mUnreadMsgNumber.setVisibility(View.GONE);
            }

            if (item.getAllMsgCount() != 0) {
                EMMessage lastMessage = item.getLastMessage();
                message.setText(EaseSmileUtils.getSmiledText(mContext, EaseCommonUtils.getMessageDigest(lastMessage, mContext)));
                TimeFormat timeFormat = new TimeFormat(mContext, lastMessage.getMsgTime());
                time.setText(timeFormat.getTime());

                if (lastMessage.direct() == EMMessage.Direct.SEND && lastMessage.status() == EMMessage.Status.FAIL) {
                    mMsgState.setVisibility(View.VISIBLE);
                } else {
                    mMsgState.setVisibility(View.GONE);
                }
            }

            if (mentioned.getVisibility() != View.VISIBLE) {
                String unSendMsg = EasePreferenceManager.getInstance().getUnSendMsgInfo(username);
                if (!TextUtils.isEmpty(unSendMsg)) {
                    mentioned.setText(R.string.were_not_send_msg);
                    message.setText(unSendMsg);
                    mentioned.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
