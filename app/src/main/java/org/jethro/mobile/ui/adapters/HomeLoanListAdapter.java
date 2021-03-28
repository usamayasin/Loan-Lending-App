package org.jethro.mobile.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jethro.mobile.R;
import org.jethro.mobile.models.accounts.loan.LoanAccount;
import org.jethro.mobile.utils.DateHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeLoanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LoanAccount> homeLoanList = new ArrayList<>();

    public HomeLoanListAdapter(List<LoanAccount> homeLoanList) {
        this.homeLoanList = homeLoanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.home_loan_list_jtem, parent, false);
        vh = new HomeLoanListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {

            LoanAccount loanAccount = homeLoanList.get(position);
            ((ViewHolder) holder).tv_homeLoanListItemAccountNumber.setText(loanAccount.getAccountNo());
            ((ViewHolder) holder).tv_homeLoanListItemAmount.setText("$"+String.valueOf(loanAccount.getAmountPaid()));
            if (loanAccount.getStatus().getActive() && loanAccount.getInArrears()) {

                ((ViewHolder) holder).tv_homeLoanListItemStatus.setText(
                        holder.itemView.getContext().getString(R.string.disbursement));
                ((ViewHolder) holder).tv_homeLoanListItemStatus.setTextColor(
                        ContextCompat.getColor(holder.itemView.getContext(), R.color.red));

            } else if (loanAccount.getStatus().getActive()) {

                ((ViewHolder) holder).tv_homeLoanListItemStatus.setText(
                        holder.itemView.getContext().getString(R.string.disbursement));
                ((ViewHolder) holder).tv_homeLoanListItemStatus.setTextColor(
                        ContextCompat.getColor(holder.itemView.getContext(), R.color.deposit_green));

            } else if (loanAccount.getStatus().getWaitingForDisbursal()) {

                ((ViewHolder) holder).tv_homeLoanListItemStatus.setText(
                        holder.itemView.getContext().getString(R.string.approved));
                ((ViewHolder) holder).tv_homeLoanListItemStatus.setTextColor(
                        ContextCompat.getColor(holder.itemView.getContext(), R.color.blue));

            } else if (loanAccount.getStatus().getPendingApproval()) {

                ((ViewHolder) holder).tv_homeLoanListItemStatus.setText(
                        holder.itemView.getContext().getString(R.string.
                                submitted));
                ((ViewHolder) holder).tv_homeLoanListItemStatus.setTextColor(
                        ContextCompat.getColor(holder.itemView.getContext(), R.color.light_yellow));

            } else if (loanAccount.getStatus().getOverpaid()) {

                ((ViewHolder) holder).tv_homeLoanListItemStatus.setText(
                        holder.itemView.getContext().getString(R.string.disbursement));
                ((ViewHolder) holder).tv_homeLoanListItemStatus.setTextColor(
                        ContextCompat.getColor(holder.itemView.getContext(), R.color.purple));

            } else if (loanAccount.getStatus().getClosed()) {
                ((ViewHolder) holder).tv_homeLoanListItemStatus.setText(
                        holder.itemView.getContext().getString(R.string.closed));
                ((ViewHolder) holder).tv_homeLoanListItemStatus.setTextColor(
                        ContextCompat.getColor(holder.itemView.getContext(),  R.color.black));

            } else {
                ((ViewHolder) holder).tv_homeLoanListItemStatus.setText(
                        holder.itemView.getContext().getString(R.string.withdrawn));
                ((ViewHolder) holder).tv_homeLoanListItemStatus.setTextColor(
                        ContextCompat.getColor(holder.itemView.getContext(),  R.color.gray_dark));

            }

        }

    }

    @Override
    public int getItemCount() {
        return homeLoanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_homeLoanListItemAccountNumber)
        TextView tv_homeLoanListItemAccountNumber;

        @BindView(R.id.tv_homeLoanListItemStatus)
        TextView tv_homeLoanListItemStatus;

        @BindView(R.id.tv_homeLoanListItemAmount)
        TextView tv_homeLoanListItemAmount;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
