package org.jethro.mobile.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jethro.mobile.R;
import org.jethro.mobile.models.accounts.loan.LoanAccount;
import org.jethro.mobile.models.accounts.share.ShareAccount;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeShareListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShareAccount> homeShareList = new ArrayList<>();

    public HomeShareListAdapter(List<ShareAccount> homeShareList) {
        this.homeShareList = homeShareList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.home_share_list_item, parent, false);
        vh = new HomeShareListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeShareListAdapter.ViewHolder) {

            ShareAccount shareAccount = homeShareList.get(position);
            ((ViewHolder) holder).tv_homeShareListItemAccountNumber.setText(shareAccount.getAccountNo());
            ((ViewHolder) holder).tv_homeShareListItemMember.setText(shareAccount.getProductName());

        }
    }

    @Override
    public int getItemCount() {
        return homeShareList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_homeShareListItemAccountNumber)
        TextView tv_homeShareListItemAccountNumber;

        @BindView(R.id.tv_homeShareListItemMember)
        TextView tv_homeShareListItemMember;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
