package first.example.paliz.shoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import first.example.paliz.shoppingapp.Global.Key;
import first.example.paliz.shoppingapp.Global.Link;
import first.example.paliz.shoppingapp.R;
import first.example.paliz.shoppingapp.adapter.AllCategoryAdapter;
import first.example.paliz.shoppingapp.adapter.AmazingProductAdapter;
import first.example.paliz.shoppingapp.adapter.DetailCategoryAdapter;
import first.example.paliz.shoppingapp.adapter.DetailNewProductAdapter;
import first.example.paliz.shoppingapp.adapter.DetailProductPopularAdapter;
import first.example.paliz.shoppingapp.model.Amazing;
import first.example.paliz.shoppingapp.model.AmazingOfferProduct;
import first.example.paliz.shoppingapp.model.Category;
import first.example.paliz.shoppingapp.model.DetailCategory;
import first.example.paliz.shoppingapp.model.FirstAmazing;
import first.example.paliz.shoppingapp.model.Product;


public class DetailCategoryActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    Bundle bundle;
    String name_category , id;
    TextView title;

    //Category
    List<DetailCategory> listDetailCategory = new ArrayList<>();
    DetailCategoryAdapter detailCategoryAdapter;
    RecyclerView recyclerView_detail_category;

    //Popular Product
    List<AmazingOfferProduct> listPopular = new ArrayList<>();
    DetailProductPopularAdapter detailProductPopularAdapter;
    RecyclerView recyclerView_popular;

    //New Product
    List<Amazing> listNewProduct = new ArrayList<>();
    RecyclerView recyclerView_new_product;
    DetailNewProductAdapter detailNewProductAdapter;

    TextView txt_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category);

        requestQueue = Volley.newRequestQueue(this);

        bundle = getIntent().getExtras();
        name_category = bundle.getString(Key.title);
        id = bundle.getString(Key.id);
        title = findViewById(R.id.title);

        txt_category = findViewById(R.id.txt_category);

        title.setText(name_category);

        txt_category.setText(" ???????? ???????? ?????? ?????????? ???? " + name_category+ " : " );

        Toast.makeText(this, id+"", Toast.LENGTH_SHORT).show();

        getDetailCategory(id);
        getPopularDetailCategoryProduct(id);
        getDetailNewProduct(id);

    }

    private void getDetailNewProduct(String id) {


        recyclerView_new_product =findViewById(R.id.recyclerView_new_product);
        recyclerView_new_product.setHasFixedSize(true);
        recyclerView_new_product.setLayoutManager(new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.HORIZONTAL , false));

        FirstAmazing firstAmazing = new FirstAmazing("???? ?????? ???????? ?????????????? ???? ???????? ?????????? ?????????????? ???? ???????????? ??????????",
                "https://img2.pngio.com/digital-png-1-png-image-digital-asset-png-1203_1022.png");;

        listNewProduct.add(new Amazing(1 , firstAmazing));

        detailNewProductAdapter = new DetailNewProductAdapter(this  , listNewProduct);
        recyclerView_new_product.setAdapter(detailNewProductAdapter);

        String url = Link.LINK_NEW_PRODUCT_DETAIL;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString() ,  Product[].class);

                for (int i = 0 ; i<products.length ; i++){

                    listNewProduct.add(new Amazing(0 , products[i]));
                    detailNewProductAdapter.notifyDataSetChanged();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST , url  ,listener , errorListener ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String , String> map = new HashMap<>();
                map.put(Key.id , id);
                return map;


            }
        };
        requestQueue.add(request);



    }


    private void getPopularDetailCategoryProduct(String id) {

        recyclerView_popular = findViewById(R.id.recyclerView_popular);
        recyclerView_popular.setHasFixedSize(true);
        recyclerView_popular.setLayoutManager(new LinearLayoutManager(this));
        detailProductPopularAdapter = new DetailProductPopularAdapter(this , listPopular);
        recyclerView_popular.setAdapter(detailProductPopularAdapter);

        String url = Link.LINK_POPULAR_DETAIL;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                AmazingOfferProduct[] amazingOfferProducts = gson.fromJson(response.toString() ,  AmazingOfferProduct[].class);

                for (int i = 0 ; i<amazingOfferProducts.length ; i++){

                    listPopular.add(amazingOfferProducts[i]);
                    detailProductPopularAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST , url  ,listener , errorListener ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String , String> map = new HashMap<>();
                map.put(Key.id , id);
                return map;


            }
        };
        requestQueue.add(request);


    }

    private void getDetailCategory(String id) {


        recyclerView_detail_category = findViewById(R.id.recyclerView_Category);
        recyclerView_detail_category.setHasFixedSize(true);
        recyclerView_detail_category.setLayoutManager(new GridLayoutManager(this , 2));
        detailCategoryAdapter = new DetailCategoryAdapter(this , listDetailCategory);
        recyclerView_detail_category.setAdapter(detailCategoryAdapter);

        String url = Link.LINK_DETAIL_CATEGORY;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                DetailCategory[] detailCategories = gson.fromJson(response.toString() ,  DetailCategory[].class);

                for (int i = 0 ; i<detailCategories.length ; i++){

                    listDetailCategory.add(detailCategories[i]);
                    detailCategoryAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST , url  ,listener , errorListener ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String , String> map = new HashMap<>();
                map.put(Key.id , id);
                return map;


            }
        };
        requestQueue.add(request);

    }


}