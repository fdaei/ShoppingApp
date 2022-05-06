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
import first.example.paliz.shoppingapp.model.Cart;
import first.example.paliz.shoppingapp.model.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    List<Cart> data;
    deleteProductForId deleteProduct;
    priceAndSizeProduct priceAndSizeProduct;

    public CartAdapter(Context context, List<Cart> data, CartAdapter.deleteProductForId deleteProduct, CartAdapter.priceAndSizeProduct priceAndSizeProduct) {
        this.context = context;
        this.data = data;
        this.deleteProduct = deleteProduct;
        this.priceAndSizeProduct = priceAndSizeProduct;
    }

    public CartAdapter(Context context, List<Cart> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_cart , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_name_product.setText(data.get(position).getName());
        holder.txt_price.setText(data.get(position).getPrice());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_product);

        holder.txt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteProduct.IItemDeleteProduct(data.get(position));



            }
        });

        priceAndSizeProduct.IItemPriceAndSize(data.get(position).getPrice() , data.size()+"");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView txt_name_product , txt_price , txt_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            txt_name_product = itemView.findViewById(R.id.name_product);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_delete = itemView.findViewById(R.id.txt_delete);

        }
    }

    public interface deleteProductForId {
        void IItemDeleteProduct(Cart cart);
    }

    public interface priceAndSizeProduct{
        void IItemPriceAndSize(String price , String  data);
    }

    public void deleteProduct(Cart cart){

        int index = data.indexOf(cart);
        data.remove(index);
        notifyDataSetChanged();

    }

}
