package com.example.catbti;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private int pNum;
    private ArrayList<PostItem> postItems;
    private Context mContext;
    private DBHelper mDBHelper;
    private ArrayList<Comment> comments;
    private CommentDBHelper mCommentDBHelper;
    private CommentAdapter mCommentAdapter;

    public int getpNum() {
        return pNum;
    }

    public void setpNum(int pNum) {
        this.pNum = pNum;
    }

    public PostAdapter(ArrayList<PostItem> postItems, Context mContext) {
        this.postItems = postItems;
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.boardlist, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        holder.tv_title.setText(postItems.get(position).getTitle());
        holder.tv_content.setText(postItems.get(position).getContent());
        holder.tv_mbti.setText(postItems.get(position).getMbti());
    }

    @Override
    public int getItemCount() {
        return postItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_mbti;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_mbti = itemView.findViewById(R.id.tv_mbti);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int curPos = getAdapterPosition(); //?????? ????????? ????????? ??????
                    PostItem postItem = postItems.get(curPos);
                    String[] strChoiceItems = {"????????????"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //????????????
                            if (i==0){

                                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                                dialog.setContentView(R.layout.activity_detail);
                                TextView dtitle_tv = dialog.findViewById(R.id.dtitle_tv);
                                TextView dcontent_tv = dialog.findViewById(R.id.dcontent_tv);
                                TextView dmbti_tv = dialog.findViewById(R.id.dmbti_tv);



                                dtitle_tv.setText(postItem.getTitle());
                                dcontent_tv.setText(postItem.getContent());
                                dmbti_tv.setText(postItem.getMbti());


                                Button comment_btn = dialog.findViewById(R.id.comment_btn);
                                comment_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        int postNum = postItem.getPostNum();
                                        Intent intent = new Intent(mContext, CommentActivity.class);
                                        intent.putExtra("postNum",postNum);
                                        mContext.startActivity(intent);

                                    }
                                });



                                dialog.dismiss();

                                dialog.show();



                            }

                        }
                    });
                    builder.show();

                }
            });
        }

    }
    //?????????????????? ???????????? ???????????? ,?????? ???????????? ??? ????????? ????????? ???????????? ???????????? ???????????? ??????.
    public void addItem(PostItem _item){
        postItems.add(0, _item);
        notifyItemInserted(0);
    }
}
