package com.andeptrai.doantotnghiep.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.doantotnghiep.R;
import com.andeptrai.doantotnghiep.data.model.BillReservationOrdered;
import com.andeptrai.doantotnghiep.interf.ReservationInterf;

import java.util.ArrayList;

public class BillReservationOrderedAdapter extends RecyclerView.Adapter {

    ArrayList<BillReservationOrdered> billReservationOrderedArrayList;
    Context mContext;
    ReservationInterf reservationInterf;

    public BillReservationOrderedAdapter(ArrayList<BillReservationOrdered> billReservationOrderedArrayList
            , Context mContext, ReservationInterf reservationInterf) {
        this.billReservationOrderedArrayList = billReservationOrderedArrayList;
        this.mContext = mContext;
        this.reservationInterf = reservationInterf;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_bill_reservation, parent, false);
        return new BillReservationOrderedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final BillReservationOrdered billReservationOrdered = billReservationOrderedArrayList.get(position);
        BillReservationOrderedViewHolder billReservationOrderedViewHolder = (BillReservationOrderedViewHolder) holder;

        billReservationOrderedViewHolder.txtNameRestaurant.setText(billReservationOrdered.getNameRestaurant());
        billReservationOrderedViewHolder.txtAdultsNumber.setText(billReservationOrdered.getAdultsNumber()+" người");
        billReservationOrderedViewHolder.txtChildrenNumber.setText(billReservationOrdered.getChildrenNumber()+" người");
        billReservationOrderedViewHolder.txtTimeCome.setText(billReservationOrdered.getDatetimeGo());

        if (billReservationOrdered.getStatusConfirm() == 0){
            billReservationOrderedViewHolder.txtYetAccepted.setVisibility(View.VISIBLE);
            billReservationOrderedViewHolder.txtAccepted.setVisibility(View.GONE);
            billReservationOrderedViewHolder.txtRejected.setVisibility(View.GONE);
        }
        else if (billReservationOrdered.getStatusConfirm() == 1){
            billReservationOrderedViewHolder.txtEditBillReservation.setVisibility(View.INVISIBLE);
            billReservationOrderedViewHolder.txtYetAccepted.setVisibility(View.GONE);
            billReservationOrderedViewHolder.txtAccepted.setVisibility(View.VISIBLE);
            billReservationOrderedViewHolder.txtRejected.setVisibility(View.GONE);
        }
        else if (billReservationOrdered.getStatusConfirm() == 2){
            billReservationOrderedViewHolder.txtEditBillReservation.setVisibility(View.INVISIBLE);
            billReservationOrderedViewHolder.txtYetAccepted.setVisibility(View.GONE);
            billReservationOrderedViewHolder.txtAccepted.setVisibility(View.GONE);
            billReservationOrderedViewHolder.txtRejected.setVisibility(View.VISIBLE);
        }

        billReservationOrderedViewHolder.txtEditBillReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reservationInterf.updateBillClickListener(billReservationOrdered, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return billReservationOrderedArrayList.size();
    }

    class BillReservationOrderedViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameRestaurant, txtAdultsNumber, txtChildrenNumber, txtTimeCome, txtEditBillReservation
                , txtAccepted, txtRejected, txtYetAccepted;

        public BillReservationOrderedViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameRestaurant = itemView.findViewById(R.id.txtNameRestaurant);
            txtAdultsNumber = itemView.findViewById(R.id.txtAdultsNumber);
            txtChildrenNumber = itemView.findViewById(R.id.txtChildrenNumber);
            txtTimeCome = itemView.findViewById(R.id.txtTimeCome);
            txtEditBillReservation = itemView.findViewById(R.id.txtEditBillReservation);
            txtAccepted = itemView.findViewById(R.id.txtAccepted);
            txtRejected = itemView.findViewById(R.id.txtRejected);
            txtYetAccepted = itemView.findViewById(R.id.txtYetAccepted);
        }
    }
}
