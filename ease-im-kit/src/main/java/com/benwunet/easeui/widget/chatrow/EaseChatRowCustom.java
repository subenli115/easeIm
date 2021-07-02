package com.benwunet.easeui.widget.chatrow;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.benwunet.easeui.manager.EaseDingMessageHelper;
import com.benwunet.easeui.utils.StringUtils;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMCustomMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;

import java.util.List;
import java.util.Map;

/**
 *
 * 环信自定义动态名片
 *
 */
public class EaseChatRowCustom extends EaseChatRow {


    private TextView tvJob, tvPhone, tvName, tvCompany, tvDynamic;
    private ImageView ivCover, ivCompany, ivDynamic;
    private ConstraintLayout card;
    private LinearLayout llDynamic, llCompany;
    private TextView tvIndustry;

    public EaseChatRowCustom(Context context, boolean isSender) {
        super(context, isSender);
    }

    public EaseChatRowCustom(Context context, EMMessage message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
	}

	@Override
	protected void onInflateView() {
		inflater.inflate(!showSenderType ?
                R.layout.ease_row_received_custom : R.layout.ease_row_sent_custom, this);
	}

	@Override
	protected void onFindViewById() {
        card = findViewById(R.id.bubble);
        llDynamic = findViewById(R.id.ll_dynamic);
        llCompany = findViewById(R.id.ll_company);

        ivCompany = findViewById(R.id.iv_company);
        tvCompany = findViewById(R.id.tv_company);
        tvIndustry = findViewById(R.id.tv_industry);


        ivDynamic = findViewById(R.id.iv_dynamic);
        tvDynamic = findViewById(R.id.tv_dynamic);

        tvJob = findViewById(R.id.tv_job);
        tvPhone = findViewById(R.id.tv_phone);
        tvName = findViewById(R.id.tv_name);
        ivCover = findViewById(R.id.iv_cover);
	}

    @Override
    public void onSetUpView() {
        EMCustomMessageBody txtBody = (EMCustomMessageBody) message.getBody();
        Map<String, String> params = txtBody.getParams();
        Log.e("EMCustomMessageBody",""+txtBody.event()+"      "+params.get("companyName"));
        if (txtBody.event().equals("card")) {
            llCompany.setVisibility(GONE);
            card.setVisibility(VISIBLE);
            llDynamic.setVisibility(GONE);
            String positionName = params.get("positionName");
            String mobile = params.get("mobile");
            String realName = params.get("realName");
            String imagePhoto = params.get("imagePhoto");
            if (!StringUtils.isEmpty(positionName)) {
                tvJob.setText(positionName);
            } else {
                tvJob.setVisibility(GONE);
            }
            tvPhone.setText(mobile);
            tvName.setText(realName);
            if( StringUtils.isEmpty(imagePhoto)){
                ivCover.setVisibility(GONE);
            }
            Glide.with(getContext()).load(imagePhoto).into(ivCover);
        } else if (txtBody.event().equals("dynamic")) {
            String dynamicContent = params.get("dynamicContent");
            String dynamicPic = params.get("dynamicPic");
            tvDynamic.setText(dynamicContent);
            if( StringUtils.isEmpty(dynamicPic)){
                ivDynamic.setVisibility(GONE);
            }
            Glide.with(getContext()).load(dynamicPic).into(ivDynamic);
            llCompany.setVisibility(GONE);
            card.setVisibility(GONE);
            llDynamic.setVisibility(VISIBLE);

        } else if (txtBody.event().equals("company")) {
            String companyName = params.get("companyName");
            String companyLogo = params.get("companyLogo");
            String industryName = params.get("industryName");
            Glide.with(getContext()).load(companyLogo).into(ivCompany);
            tvCompany.setText(companyName);
            if (industryName != null) {
                tvIndustry.setText(industryName);
            }
            if( StringUtils.isEmpty(companyLogo)){
                ivCompany.setVisibility(GONE);
            }
            llCompany.setVisibility(VISIBLE);
            card.setVisibility(GONE);
            llDynamic.setVisibility(GONE);

        }
    }

    public void onAckUserUpdate(final int count) {
        if (ackedView != null && isSender()) {
            ackedView.post(new Runnable() {
                @Override
                public void run() {
                    ackedView.setVisibility(VISIBLE);
                    ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
                }
            });
        }
    }

    @Override
    protected void onMessageCreate() {
        if(progressBar != null) {
            progressBar.setVisibility(VISIBLE);
        }
        if(statusView != null) {
            statusView.setVisibility(GONE);
        }
    }

    @Override
    protected void onMessageSuccess() {
        if(progressBar != null) {
            progressBar.setVisibility(GONE);
        }
        if(statusView != null) {
            statusView.setVisibility(GONE);
        }

        // Show "1 Read" if this msg is a ding-type msg.
        if (isSender() && EaseDingMessageHelper.get().isDingMessage(message) && ackedView != null) {
            ackedView.setVisibility(VISIBLE);
            int count = message.groupAckCount();
            ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
        }

        // Set ack-user list change listener.
        EaseDingMessageHelper.get().setUserUpdateListener(message, userUpdateListener);
    }

    @Override
    protected void onMessageError() {
        if(progressBar != null) {
            progressBar.setVisibility(GONE);
        }
        if(statusView != null) {
            statusView.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onMessageInProgress() {
        if(progressBar != null) {
            progressBar.setVisibility(VISIBLE);
        }
        if(statusView != null) {
            statusView.setVisibility(GONE);
        }
    }

    private EaseDingMessageHelper.IAckUserUpdateListener userUpdateListener =
            new EaseDingMessageHelper.IAckUserUpdateListener() {
                @Override
                public void onUpdate(List<String> list) {
                    onAckUserUpdate(list.size());
                }
            };
}
