package first.example.paliz.shoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import first.example.paliz.shoppingapp.Global.Key;
import first.example.paliz.shoppingapp.Global.Link;
import first.example.paliz.shoppingapp.R;
import first.example.paliz.shoppingapp.adapter.DetailCategoryProductAdapter;
import first.example.paliz.shoppingapp.adapter.QuestionAdapter;
import first.example.paliz.shoppingapp.model.Product;
import first.example.paliz.shoppingapp.model.Question;

public class QuestionActivity extends AppCompatActivity {

    List<Question> listQuestion = new ArrayList<>();
    QuestionAdapter adapter;
    RecyclerView recyclerView_question;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        requestQueue = Volley.newRequestQueue(this);

        recyclerView_question  = findViewById(R.id.recyclerView_question);
        recyclerView_question.setHasFixedSize(true);
        recyclerView_question.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuestionAdapter(this , listQuestion);
        recyclerView_question.setAdapter(adapter);

        String url = Link.LINK_QUESTION;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Question[] questions = gson.fromJson(response.toString() ,  Question[].class);

                for (int i = 0 ; i<questions.length ; i++){

                    listQuestion.add(questions[i]);
                    adapter.notifyDataSetChanged();
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

        StringRequest request = new StringRequest(Request.Method.GET , url  ,listener , errorListener );

        requestQueue.add(request);

    }
}