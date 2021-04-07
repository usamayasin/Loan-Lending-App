package org.jethro.mobile.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jethro.mobile.R;
import org.jethro.mobile.models.accounts.loan.LoanAccount;
import org.jethro.mobile.models.accounts.loan.Periods;
import org.jethro.mobile.utils.CurrencyUtil;
import org.jethro.mobile.utils.DateHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoanRepaymentScheduleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Periods> loanRepaymentList = new ArrayList<>();
    private String currency = "";

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setLoanRepaymentList(List<Periods> loanRepaymentList) {
        this.loanRepaymentList = loanRepaymentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.loan_repayment_schedule_item, parent, false);
        vh = new LoanRepaymentScheduleRecyclerViewAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            Periods data = loanRepaymentList.get(position);
            ((ViewHolder) holder).tv_loan_repayment_schedule_date.setText(DateHelper.getDateAsString(data.getDueDate()));

            Double principal = data.getPrincipalOriginalDue();
            if (principal == null) {
                principal = 0.00;
            }
            ((ViewHolder) holder).tv_loan_repayment_schedule_loan_balance.
                    setText(holder.itemView.getContext().getString(R.string.string_and_double,
                            currency, principal));

            Double loanBalance = data.getPrincipalLoanBalanceOutstanding();
            if (loanBalance == null) {
                loanBalance = 0.00;
            }
            ((ViewHolder) holder).tv_loan_repayment_schedule_loan_repaymnet.
                    setText(holder.itemView.getContext().getString(R.string.string_and_string,
                            currency, CurrencyUtil.formatCurrency(holder.itemView.getContext(), loanBalance)));

        }

    }

    @Override
    public int getItemCount() {
        return loanRepaymentList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_loan_repayment_schedule_loan_repaymnet)
        TextView tv_loan_repayment_schedule_loan_repaymnet;

        @BindView(R.id.tv_loan_repayment_schedule_loan_balance)
        TextView tv_loan_repayment_schedule_loan_balance;

        @BindView(R.id.tv_loan_repayment_schedule_date)
        TextView tv_loan_repayment_schedule_date;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
