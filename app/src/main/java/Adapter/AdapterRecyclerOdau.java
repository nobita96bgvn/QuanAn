package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.haiduongvu0102.quanan.Model.BinhLuanModel;
import com.haiduongvu0102.quanan.Model.ChiNhanhQuanAnModel;
import com.haiduongvu0102.quanan.Model.QuanAnModel;
import com.haiduongvu0102.quanan.R;
import com.haiduongvu0102.quanan.View.ChiTietQuanAnActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecyclerOdau extends RecyclerView.Adapter<AdapterRecyclerOdau.ViewHolder> {


    List<QuanAnModel> quanAnModelList;
    int resource;
    Context context;

    public AdapterRecyclerOdau(Context context, List<QuanAnModel> quanAnModelList, int resource) {
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTenQuanAnOdau;
        TextView txtTieuDeBinhLuan1, txtTieuDeBinhLuan2, txtNoiDungBinhLuan1, txtNoiDungBinhLuan2;
        TextView txtChamDiemBinhLuan1, txtChamDiemBinhLuan2, txtTongBinhLuan,txtTongHinhBinhLuan;
        TextView txtDiaChiQuanAnOdau, txtKhoangCachQuanAnOdau;
        Button btnDatMonOdau;
        ImageView imageHinhQuanAnOdau;

        CircleImageView circleImageUser1, circleImageUser2;
        LinearLayout containerBinhLuan1, containerBinhLuan2;

        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenQuanAnOdau = itemView.findViewById(R.id.txtTenQuanQuanOdau);
            btnDatMonOdau = itemView.findViewById(R.id.btnDatMonOdau);
            imageHinhQuanAnOdau = itemView.findViewById(R.id.imageHinhQuanAnOdau);


            cardView = itemView.findViewById(R.id.cardViewOdau);

            txtDiaChiQuanAnOdau = itemView.findViewById(R.id.txtDiaChiQuanAnODau);
            txtKhoangCachQuanAnOdau = itemView.findViewById(R.id.txtKhoangCachQuanAnODau);





        }
    }

    public AdapterRecyclerOdau.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerOdau.ViewHolder holder, int position) {
        final QuanAnModel quanAnModel = quanAnModelList.get(position);
        holder.txtTenQuanAnOdau.setText(quanAnModel.getTenquanan());

        // hien thi dat mon neu co
        if (quanAnModel.isGiaohang()) {
            holder.btnDatMonOdau.setVisibility(View.VISIBLE);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // hien thi hinh quan an
        if (quanAnModel.getHinhanhquanan().size() > 0) {
            StorageReference storageHinhanh = FirebaseStorage.getInstance().getReference().child("hinhanhquan").child(quanAnModel.getHinhanhquanan().get(0));
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imageHinhQuanAnOdau.setImageBitmap(bitmap);
                }
            });


        }


        //end hien thi hinh anh


        //Lay chi nhanh quan an , hien thi dia chi va km
        if (quanAnModel.getChiNhanhQuanAnModelList().size() > 0) {
            if (quanAnModel.getChiNhanhQuanAnModelList().size() > 0) {
                ChiNhanhQuanAnModel chiNhanhQuanAnModelTam = quanAnModel.getChiNhanhQuanAnModelList().get(0);
                for (ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModel.getChiNhanhQuanAnModelList()) {
                    if (chiNhanhQuanAnModelTam.getKhoangcach() > chiNhanhQuanAnModel.getKhoangcach()) {
                        chiNhanhQuanAnModelTam = chiNhanhQuanAnModel;
                    }
                }

                holder.txtDiaChiQuanAnOdau.setText(chiNhanhQuanAnModelTam.getDiachi());
                holder.txtKhoangCachQuanAnOdau.setText(String.format("%.1f", chiNhanhQuanAnModelTam.getKhoangcach()) + " km");
            }

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);
                    iChiTietQuanAn.putExtra("quanan", new Gson().toJson(quanAnModel));
                    context.startActivity(iChiTietQuanAn);

                }
            });
        }



    }





    @Override
    public int getItemCount () {
        return quanAnModelList.size();
    }



}
