package com.andeptrai.doantotnghiep.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.BillDeliveryAdapter;
import com.andeptrai.doantotnghiep.data.model.BillDeliveryOrdered;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.andeptrai.doantotnghiep.interf.BillDeliveryInterf;
import com.andeptrai.doantotnghiep.ui.delivery.EditBillDeliveryActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.andeptrai.doantotnghiep.URL.urlGetAllBillDeliveryByIdUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillDeliveryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillDeliveryFragment extends Fragment implements BillDeliveryInterf {

    ImageView icPageBack;
    RecyclerView rvBillDelivery;
    BillDeliveryAdapter billDeliveryAdapter;
    ArrayList<BillDeliveryOrdered> billDeliveryOrderedArrayList = new ArrayList<>();

    private final int CODE_REQUEST_EDIT_BILL_DELIVERY = 1000;
    int currentPositionBill = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BillDeliveryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BillDeliveryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillDeliveryFragment newInstance(String param1, String param2) {
        BillDeliveryFragment fragment = new BillDeliveryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bill_delivery, container, false);

        rvBillDelivery = view.findViewById(R.id.rvBillDeliveryOrdered);
        billDeliveryAdapter = new BillDeliveryAdapter(billDeliveryOrderedArrayList, getContext(), this);
        rvBillDelivery.setAdapter(billDeliveryAdapter);
        rvBillDelivery.setLayoutManager(new LinearLayoutManager(getContext()));
        getAllBillDelivery();
        return view;
    }

    private void getAllBillDelivery() {
        billDeliveryOrderedArrayList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlGetAllBillDeliveryByIdUser
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("Get all delivery this user fail")){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            billDeliveryOrderedArrayList.add(new BillDeliveryOrdered(jsonObject.getString("Id_bill"),
                                    jsonObject.getInt("Id_us_order"),
                                    jsonObject.getString("Id_restaurant"),
                                    jsonObject.getString("Time_create_bill"),
                                    jsonObject.getString("Address_delivery"),
                                    jsonObject.getString("Time_delivery"),
                                    jsonObject.getLong("Total_money"),
                                    jsonObject.getString("Payment"),
                                    jsonObject.getString("Notes"),
                                    jsonObject.getString("Detail_bill"),
                                    jsonObject.getString("Detail_food"),
                                    jsonObject.getInt("Status_confirm"),
                                    jsonObject.getString("Name_restaurant")));
                        }
                        billDeliveryAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {

                        Toast.makeText(getContext(), "Get bill delivery error exception! -- " + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Get bill delivery fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Get all bill delivery by id user error!---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idUser", InfoUserCurr.currentId + "");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    @Override
    public void updateBillClickListener(BillDeliveryOrdered billDeliveryOrdered, int position) {
        currentPositionBill = position;
        Intent intent = new Intent(getContext(), EditBillDeliveryActivity.class);
        intent.putExtra("currentBillDelivery", billDeliveryOrdered);
        startActivityForResult(intent, CODE_REQUEST_EDIT_BILL_DELIVERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUEST_EDIT_BILL_DELIVERY && resultCode == 1001){
            BillDeliveryOrdered updateBillDelivery = (BillDeliveryOrdered) data.getSerializableExtra("updateBillDelivery");
            billDeliveryOrderedArrayList.remove(currentPositionBill);
            billDeliveryOrderedArrayList.add(currentPositionBill, updateBillDelivery);
            billDeliveryAdapter.notifyItemChanged(currentPositionBill);
        }
    }
}