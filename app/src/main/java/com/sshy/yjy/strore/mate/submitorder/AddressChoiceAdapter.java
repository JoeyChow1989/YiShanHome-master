package com.sshy.yjy.strore.mate.submitorder;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.sshy.yjy.strore.R;
import com.sshy.yjy.strore.mate.main.personal.address.AddressItemFields;
import com.sshy.yjy.strore.mate.main.personal.address.EditAddressDelegate;
import com.sshy.yjy.ui.recycler.MultipleFields;
import com.sshy.yjy.ui.recycler.MultipleItemEntity;
import com.sshy.yjy.ui.recycler.adapter.MultipleRecyclerAdapter;
import com.sshy.yjy.ui.recycler.adapter.MultipleViewHolder;

import java.util.List;

import strore.yjy.sshy.com.mate.util.log.MateLogger;

/**
 * create date：2018/4/23
 * create by：周正尧
 */
public class AddressChoiceAdapter extends MultipleRecyclerAdapter {

    private Activity mContext;

    protected AddressChoiceAdapter(List<MultipleItemEntity> data, Activity context) {
        super(data);
        this.mContext = context;
        addItemType(AddressChoiceItemType.ITEM_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case AddressChoiceItemType.ITEM_ADDRESS:
                final String addressId = entity.getField(MultipleFields.ID);
                final String name = entity.getField(MultipleFields.NAME);
                final String phone = entity.getField(AddressItemFields.PHONE);
                final String address = entity.getField(AddressItemFields.ADDRESS);
                final int isDefault = entity.getField(AddressItemFields.DEFAULT);

                final AppCompatTextView nameText = holder.getView(R.id.tv_address_name);
                final AppCompatTextView phoneText = holder.getView(R.id.tv_address_phone);
                final AppCompatTextView addressText = holder.getView(R.id.tv_address_address);
                final AppCompatTextView editTextView = holder.getView(R.id.tv_address_edit);
                final AppCompatTextView defaultTextView = holder.getView(R.id.tv_address_defult);

                editTextView.setOnClickListener(v -> {
                    EditAddressDelegate.startAction(mContext, addressId);
                });

                nameText.setText(name);
                phoneText.setText(phone);
                addressText.setText(address);

                MateLogger.d("addressId", addressId);

                if (isDefault == 1) {
                    defaultTextView.setVisibility(View.VISIBLE);
                }

                break;
            default:
                break;
        }
    }
}
