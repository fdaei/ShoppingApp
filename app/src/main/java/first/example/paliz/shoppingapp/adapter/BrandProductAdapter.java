package first.example.paliz.shoppingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import first.example.paliz.shoppingapp.Global.Key;
import first.example.paliz.shoppingapp.R;
import first.example.paliz.shoppingapp.activity.CompareActivity;
import first.example.paliz.shoppingapp.model.Product;

public class BrandProductAdapter extends RecyclerView.Adapter<BrandProductAdapter.MyViewHolder> {

    Context context;
    List<Product> data;

    public BrandProductAdapter(Context context, List<Product> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_compare_product , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_name_product.setText(data.get(position).getName());
        holder.txt_price.setText(data.get(position).getPrice());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_product);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView txt_name_product , txt_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            txt_name_product = itemView.findViewById(R.id.name_product);
            txt_price = itemView.findViewById(R.id.txt_price);

        }
    }
}
