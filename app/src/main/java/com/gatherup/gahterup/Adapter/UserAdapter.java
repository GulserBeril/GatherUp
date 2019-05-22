package com.gatherup.gahterup.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.gatherup.gahterup.MessageActivity;
import com.gatherup.gahterup.Model.UserModel;
import com.gatherup.gahterup.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends FirestoreRecyclerAdapter<UserModel, UserAdapter.UserHolder> {
    public static Context context;
    List<UserModel> userModels;

    public UserAdapter(@NonNull FirestoreRecyclerOptions<UserModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull final UserModel model) {
        holder.username.setText(String.valueOf(model.getName()));
        holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        /*
        if (model.getImgURL().equals("default")) {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(model.getImgURL()).into(holder.profile_image);
        }*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MessageActivity.class);
                intent.putExtra("userid", model.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false);
        return new UserHolder(v);
    }

    class UserHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_image;
        TextView username;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.user_item_profile_image);
            username = itemView.findViewById(R.id.user_item_username);
        }
    }
}