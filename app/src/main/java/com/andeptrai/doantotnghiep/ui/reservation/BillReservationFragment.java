package com.andeptrai.doantotnghiep.ui.reservation;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.adapter.AdultsChildrenNumberDialogAdapter;
import com.andeptrai.doantotnghiep.data.adapter.BillReservationOrderedAdapter;
import com.andeptrai.doantotnghiep.data.model.BillReservationOrdered;
import com.andeptrai.doantotnghiep.data.model.InfoUserCurr;
import com.andeptrai.doantotnghiep.interf.ReservationInterf;
import com.andeptrai.doantotnghiep.interf.SetNumberDialogInterf;
import com.andeptrai.doantotnghiep.library_code.GET_DATA;
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

import static com.andeptrai.doantotnghiep.URL.urlGetAllBillReservationByIdUser;
import static com.andeptrai.doantotnghiep.URL.urlUpdateBillReservationByIdBillClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillReservationFragment extends Fragment implements ReservationInterf, SetNumberDialogInterf {

    RecyclerView rvBillReservationOrdered;
    BillReservationOrderedAdapter billReservationOrderedAdapter;
    ArrayList<BillReservationOrdered> billReservationOrderedArrayList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BillReservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BillReservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillReservationFragment newInstance(String param1, String param2) {
        BillReservationFragment fragment = new BillReservationFragment();
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
        View view =  inflater.inflate(R.layout.fragment_bill_reservation, container, false);

        rvBillReservationOrdered = view.findViewById(R.id.rvBillReservationOrdered);
        billReservationOrderedAdapter = new BillReservationOrderedAdapter(billReservationOrderedArrayList, getContext(), this);
        rvBillReservationOrdered.setAdapter(billReservationOrderedAdapter);
        rvBillReservationOrdered.setLayoutManager( new LinearLayoutManager(getContext()));
        billReservationOrderedAdapter.notifyDataSetChanged();
        getAllBillReservationByIdUser();
        return view;
    }

    private void getAllBillReservationByIdUser() {
        billReservationOrderedArrayList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlGetAllBillReservationByIdUser
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("Get all reservation this user fail")){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            billReservationOrderedArrayList.add(new BillReservationOrdered(jsonObject.getString("Id_bill"),
                                    jsonObject.getInt("Id_us_order"),
                                    jsonObject.getString("Id_restaurant"),
                                    jsonObject.getString("Time_create_bill"),
                                    jsonObject.getString("Datetime_go"),
                                    jsonObject.getInt("Adults_number"),
                                    jsonObject.getInt("Children_number"),
                                    jsonObject.getString("Notes"),
                                    jsonObject.getString("Name_restaurant"),
                                    jsonObject.getInt("Status_confirm")));
                        }

                    } catch (JSONException e) {

                        Toast.makeText(getContext(), "Get bill reservation error exception! -- " + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Get bill reservation fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Get all bill reservation by id user error!---"+error.toString(), Toast.LENGTH_LONG).show();
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
    public void updateBillClickListener(BillReservationOrdered billReservationOrdered, int position) {
        openDialogEditBillReservation(billReservationOrdered, position);
    }

    private void openDialogEditBillReservation(final BillReservationOrdered billReservationOrdered, final int position) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_edit_bill_reservation);

        final TextView txtTimeGo = dialog.findViewById(R.id.txtTimeGo);
        final TextView txtChangeTimeGo = dialog.findViewById(R.id.txtChangeTimeGo);
        final TextView txtNumberAdults = dialog.findViewById(R.id.txtNumberAdults);
        final TextView txtChangeNumberAdults = dialog.findViewById(R.id.txtChangeNumberAdults);
        final TextView txtNumberChildren = dialog.findViewById(R.id.txtNumberChildren);
        final TextView txtChangeNumberChildren = dialog.findViewById(R.id.txtChangeNumberChildren);
        final EditText edtNotes = dialog.findViewById(R.id.edtNotes);
        Button btnAccept = dialog.findViewById(R.id.btnAccept);
        Button btnBack = dialog.findViewById(R.id.btnBack);

        txtTimeGo.setText(billReservationOrdered.getDatetimeGo());
        txtNumberAdults.setText(billReservationOrdered.getAdultsNumber()+"");
        txtNumberChildren.setText(billReservationOrdered.getChildrenNumber()+"");
        edtNotes.setText(billReservationOrdered.getNotes());

        txtChangeTimeGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Calendar calendar = GET_DATA.editTimeDelivery(getContext());
                GET_DATA.setyyyyMMddHHmmss(getContext(), txtTimeGo);
//                billReservationOrdered.setDatetimeGo(GET_DATA.formatyyyyMMddHHmmss(calendar));
            }
        });

        txtChangeNumberAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAdultsNumber(txtNumberAdults);
            }
        });

        txtChangeNumberChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChildrenNumber(txtNumberChildren);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTimeGo = txtTimeGo.getText().toString();
                String newNumberChildren = txtNumberChildren.getText().toString();
                String newNumberAdults = txtNumberAdults.getText().toString();
                String newNotes = edtNotes.getText().toString();
                updateBillReservationInDB(newTimeGo, newNumberChildren, newNumberAdults,newNotes, billReservationOrdered, position, dialog);
            }
        });

        dialog.show();
    }

    private void updateBillReservationInDB(final String newTimeGo, final String newNumberChildren
            , final String newNumberAdults, final String newNotes
            , final BillReservationOrdered billReservationOrdered, final int position, final Dialog dialog) {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlUpdateBillReservationByIdBillClient
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("Update bill reservation success")){
                    billReservationOrdered.setDatetimeGo(newTimeGo);
                    billReservationOrdered.setAdultsNumber(Integer.parseInt(newNumberAdults));
                    billReservationOrdered.setChildrenNumber(Integer.parseInt(newNumberChildren));
                    billReservationOrdered.setNotes(newNotes);
                    billReservationOrderedAdapter.notifyItemChanged(position);
                    Toast.makeText(getContext(), "Update bill reservation success!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else{
                    Toast.makeText(getContext(), "Update bill reservation fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Update bill reservation error!---"+error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("idBillReservation", billReservationOrdered.getIdBill());
                params.put("newTimeGo", newTimeGo);
                params.put("newNumberChildren", newNumberChildren);
                params.put("newNumberAdults", newNumberAdults);
                params.put("newNotes", newNotes);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void setAdultsNumber(TextView txtNumber) {
        final Dialog adultsNumberDialog = new Dialog(getContext());
        adultsNumberDialog.setContentView(R.layout.dialog_number_adults_children);

        TextView txtTittle = adultsNumberDialog.findViewById(R.id.txtTittle);
        txtTittle.setText("Người lớn");

        RecyclerView rvNumber = adultsNumberDialog.findViewById(R.id.rvNumber);
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = 0; i < 200; i++){
            stringArrayList.add((i+1) + "");
        }

        AdultsChildrenNumberDialogAdapter numberAdapter = new AdultsChildrenNumberDialogAdapter(stringArrayList
                , getContext(), adultsNumberDialog, this, "Adults", txtNumber);
        rvNumber.setAdapter(numberAdapter);
        rvNumber.setLayoutManager(new LinearLayoutManager(getContext()));
        Button btnBack = adultsNumberDialog.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adultsNumberDialog.dismiss();
            }
        });

        adultsNumberDialog.show();
    }

    private void setChildrenNumber(TextView txtNumber) {
        final Dialog childrenNumberDialog = new Dialog(getContext());
        childrenNumberDialog.setContentView(R.layout.dialog_number_adults_children);

        TextView txtTittle = childrenNumberDialog.findViewById(R.id.txtTittle);
        txtTittle.setText("Trẻ em");

        RecyclerView rvNumber = childrenNumberDialog.findViewById(R.id.rvNumber);
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = 0; i < 200; i++){
            stringArrayList.add((i+1) + "");
        }

        AdultsChildrenNumberDialogAdapter numberAdapter = new AdultsChildrenNumberDialogAdapter(stringArrayList
                , getContext(), childrenNumberDialog, this, "Children", txtNumber);
        rvNumber.setAdapter(numberAdapter);
        rvNumber.setLayoutManager(new LinearLayoutManager(getContext()));
        Button btnBack = childrenNumberDialog.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childrenNumberDialog.dismiss();
            }
        });
        childrenNumberDialog.show();
    }

    @Override
    public void setNumberAdultsDialogClickListener(Dialog childrenNumberDialog, String s, TextView txtNumber) {
        txtNumber.setText(s);
        childrenNumberDialog.dismiss();
    }

    @Override
    public void setNumberChildrenDialogClickListener(Dialog childrenNumberDialog, String s, TextView txtNumber) {
        txtNumber.setText(s);
        childrenNumberDialog.dismiss();
    }
}