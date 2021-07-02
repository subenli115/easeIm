package com.benwunet.msg.section.message.delegates;

import android.view.View;
import android.widget.TextView;

import com.benwunet.msg.DemoHelper;
import com.benwunet.msg.common.constant.DemoConstant;
import com.benwunet.msg.common.db.entity.InviteMessageStatus;
import com.benwunet.msg.common.manager.PushAndMessageHelper;
import com.hyphenate.chat.EMMessage;
import com.benwunet.msg.R;
import com.benwunet.easeui.adapter.EaseBaseDelegate;
import com.benwunet.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.benwunet.easeui.utils.EaseDateUtils;
import com.benwunet.easeui.widget.EaseImageView;
import com.hyphenate.exceptions.HyphenateException;

import java.util.Date;

import androidx.annotation.NonNull;

public class OtherMsgDelegate extends EaseBaseDelegate<EMMessage, OtherMsgDelegate.ViewHolder> {

    @Override
    public boolean isForViewType(EMMessage msg, int position) {
        String statusParams = null;
        try {
            statusParams = msg.getStringAttribute(DemoConstant.SYSTEM_MESSAGE_STATUS);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        InviteMessageStatus status = InviteMessageStatus.valueOf(statusParams);
        return status != InviteMessageStatus.BEINVITEED &&
                status != InviteMessageStatus.BEAPPLYED &&
                status != InviteMessageStatus.GROUPINVITATION &&
                status != InviteMessageStatus.BEAGREED;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.demo_layout_item_invite_msg_agree;
    }

    @Override
    protected OtherMsgDelegate.ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends EaseBaseRecyclerViewAdapter.ViewHolder<EMMessage> {
        private TextView name;
        private TextView message;
        private EaseImageView avatar;
        private TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View itemView) {
            name = findViewById(R.id.name);
            message = findViewById(R.id.message);
            avatar = findViewById(R.id.avatar);
            time = findViewById(R.id.time);
            avatar.setShapeType(DemoHelper.getInstance().getEaseAvatarOptions().getAvatarShape());
        }

        @Override
        public void setData(EMMessage msg, int position) {
            try {
                name.setText(msg.getStringAttribute(DemoConstant.SYSTEM_MESSAGE_FROM));
                String str = PushAndMessageHelper.getSystemMessage(msg);
                message.setText(str);
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            time.setText(EaseDateUtils.getTimestampString(itemView.getContext(), new Date(msg.getMsgTime())));

        }
    }
}
