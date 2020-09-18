package com.ketan_studio.example.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    SimpleArcLoader arc;
    TextView CorfirmCase,TotalDead,Recover,TodayCases;
    CardView cardView;
    PieChart mPieChart;
    Integer cases,death,todayCases,recovered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arc = (SimpleArcLoader)findViewById(R.id.simple_arc);
        CorfirmCase = (TextView)findViewById(R.id.confirm_case_Result_tv);
        TotalDead = (TextView)findViewById(R.id.TotalDead_Result_tv);
        Recover = (TextView)findViewById(R.id.Recover_Result_tv);
        TodayCases = (TextView)findViewById(R.id.Today_case_Result_tv);
        cardView = (CardView)findViewById(R.id.card);

        arc.start();

        String url = "https://disease.sh/v3/covid-19/all";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    arc.stop();
                    cardView.setVisibility(View.VISIBLE);
                    arc.setVisibility(View.GONE);
                    JSONObject object = new JSONObject(response);
                    CorfirmCase.setText(object.getString("cases"));
                    TotalDead.setText(object.getString("deaths"));
                    Recover.setText(object.getString("recovered"));
                    TodayCases.setText(object.getString("todayCases"));
                    cases = Integer.parseInt(CorfirmCase.getText().toString());
                    death = Integer.parseInt(TotalDead.getText().toString());
                    recovered = Integer.parseInt(Recover.getText().toString());
                    todayCases = Integer.parseInt(TodayCases.getText().toString());
                    PieChart(cases,death,recovered);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);




    }
    void PieChart(Integer cases,Integer death,Integer recovered){
        mPieChart = (PieChart) findViewById(R.id.piechart);
        mPieChart.addPieSlice(new PieModel("Cases", cases, Color.parseColor("#FF3700B3")));
        mPieChart.addPieSlice(new PieModel("Death",death, Color.parseColor("#FF1100")));
        mPieChart.addPieSlice(new PieModel("Recovered",recovered, Color.parseColor("#FFEB3B")));
        mPieChart.startAnimation();
    }
}
