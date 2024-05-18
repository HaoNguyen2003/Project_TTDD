package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Account;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder>{
    public ArrayList<Account>list_account=new ArrayList<Account>();
    public optionAccount optionAccount;

    public void setOptionAccount(AccountAdapter.optionAccount optionAccount) {
        this.optionAccount = optionAccount;
    }

    public AccountAdapter(ArrayList<Account> list_account) {
        this.list_account = list_account;
    }

    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_account,parent,false);
        return new AccountHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {
        Account account=list_account.get(position);
        Picasso.get().load(account.getImageURL()).into(holder.avatar);
        holder.textView_Username.setText(account.getUsername());
        holder.textView_Address.setText(account.getAddress());
        holder.textView_email.setText(account.getEmail());
        holder.textView_Sdt.setText(account.getPhonenumber());
        if(account.isAccess()==1)
        {
            holder.imageView_block.setVisibility(View.VISIBLE);
            holder.imageView_Unblock.setVisibility(View.GONE);
        }
        else{
            holder.imageView_block.setVisibility(View.GONE);
            holder.imageView_Unblock.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public int getItemCount() {

        if(list_account.isEmpty()||list_account==null)
            return 0;
        return list_account.size();
    }

    public class AccountHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView textView_Username,textView_Sdt,textView_email,textView_Address;
        ImageView imageView_trash,imageView_block,imageView_Unblock;

        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            avatar=itemView.findViewById(R.id.avatar);
            textView_Username=itemView.findViewById(R.id.textView_Username);
            textView_Sdt=itemView.findViewById(R.id.textView_Sdt);
            textView_email=itemView.findViewById(R.id.textView_email);
            textView_Address=itemView.findViewById(R.id.textView_Address);
            imageView_trash=itemView.findViewById(R.id.imageView_trash);
            imageView_block=itemView.findViewById(R.id.imageView_block);
            imageView_Unblock=itemView.findViewById(R.id.imageView_Unblock);


            imageView_trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int ID=list_account.get(getAbsoluteAdapterPosition()).getAccountID();
                    optionAccount.removeAccount(ID);
                }
            });

            imageView_block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int ID=list_account.get(getAbsoluteAdapterPosition()).getAccountID();
                    optionAccount.blockAccount(ID);
                }
            });

            imageView_Unblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int ID=list_account.get(getAbsoluteAdapterPosition()).getAccountID();
                    optionAccount.unblockAccount(ID);
                }
            });
        }
    }
    public interface optionAccount
    {
        public void removeAccount(int ID);
        public void blockAccount(int ID);
        public void unblockAccount(int ID);
    }
}
