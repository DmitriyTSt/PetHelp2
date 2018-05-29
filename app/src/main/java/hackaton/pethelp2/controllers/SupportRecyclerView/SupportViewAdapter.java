package hackaton.pethelp2.controllers.SupportRecyclerView;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import hackaton.pethelp2.MyApplication;
import hackaton.pethelp2.R;
import hackaton.pethelp2.databinding.SupportViewItemBinding;

/**
 * Created by dmitriyt on 24.03.18.
 */

public class SupportViewAdapter extends RecyclerView.Adapter<SupportViewAdapter.SupportViewHolder> {

    @Override
    public SupportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SupportViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.support_view_item, parent, false);
        return new SupportViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SupportViewHolder holder, int position) {
        String path = MyApplication.getInstance().getSupportList().get(position).getImage().getPath();
        String name = MyApplication.getInstance().getSupportList().get(position).getName();
        Log.d("PICASSO", MyApplication.getInstance().getImgUrl() + path);
        Picasso.get().load(MyApplication.getInstance().getImgUrl() + path).into(holder.itemBinding.supportItemImage);
        holder.itemBinding.supportName.setText(name);
    }

    @Override
    public int getItemCount() {
        return MyApplication.getInstance().getSupportList().size();
    }

    public class SupportViewHolder extends RecyclerView.ViewHolder{
        SupportViewItemBinding itemBinding;

        public SupportViewHolder(SupportViewItemBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }
    }
}
