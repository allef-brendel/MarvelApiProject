package com.e.marvelapiproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.marvelapiproject.R;
import com.e.marvelapiproject.interfaces.RecycleViewOnClickListinerHack;
import com.e.marvelapiproject.pojo.Quadrinho;

import java.util.List;

    public class QuadrinhoAdapter extends RecyclerView.Adapter<QuadrinhoAdapter.MyViewHolder> {

        private Context context;
        private List<Quadrinho> mList;
        private LayoutInflater mLayoutInflater;
        private RecycleViewOnClickListinerHack mRecycleViewOnClickListinerHack;

        public QuadrinhoAdapter(Context c, List<Quadrinho> l){
            mList = l;
            mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.context = c;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = mLayoutInflater.inflate(R.layout.item_personagens_card, parent, false);
            MyViewHolder mvh = new MyViewHolder(v);
            return mvh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

                holder.tv_nome.setText(mList.get(position).getTitulo());
                holder.tv_clan.setText(mList.get(position).getQuantPaginas());
                holder.tv_preco.setText(mList.get(position).getPrecos());

                Glide.with(context).load(mList.get(position).getUrl() + "/portrait_uncanny.jpg").into(holder.iv_personagem);
            }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView iv_personagem;
            TextView tv_nome;
            TextView tv_clan;
            TextView tv_preco;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                iv_personagem = itemView.findViewById(R.id.iv_personagem);
                tv_clan = itemView.findViewById(R.id.tv_clan);
                tv_nome = itemView.findViewById(R.id.tv_nome);
                tv_preco = itemView.findViewById(R.id.tv_preco);

                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                if(mRecycleViewOnClickListinerHack != null){
                    mRecycleViewOnClickListinerHack.onClickListiner(v,getPosition());
                }
            }
        }
    }
