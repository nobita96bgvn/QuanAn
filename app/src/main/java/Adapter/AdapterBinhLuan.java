package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haiduongvu0102.quanan.Model.BinhLuanModel;
import com.haiduongvu0102.quanan.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBinhLuan extends RecyclerView.Adapter<AdapterBinhLuan.ViewHodel> {

    Context context;
    int layout;
    List<BinhLuanModel> binhLuanModelList;

    public AdapterBinhLuan (Context context, int layout, List<BinhLuanModel> binhLuanModelList){
        this.context = context;
        this.layout = layout;
        this.binhLuanModelList = binhLuanModelList;

    }



    public class ViewHodel extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txtTieuDeBinhLuan,txtNoiDungBinhLuan,txtSoDiem;
        RecyclerView recyclerViewHinhBinhLuan;

        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circleImageUser);
            txtTieuDeBinhLuan = itemView.findViewById(R.id.txtTieuDeBinhLuan);
            txtNoiDungBinhLuan = itemView.findViewById(R.id.txtNoiDungBinhLuan);
            txtSoDiem = itemView.findViewById(R.id.txtChamDiemBinhLuan);
        }
    }
    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHodel viewHodel = new ViewHodel(view);
        return viewHodel;
    }

    @Override
    public void onBindViewHolder(AdapterBinhLuan.ViewHodel hodel, int position) {

        final BinhLuanModel binhLuanModel = binhLuanModelList.get(position);
        hodel.txtTieuDeBinhLuan.setText(binhLuanModel.getTieude());
        hodel.txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
        hodel.txtSoDiem.setText(binhLuanModel.getChamdiem()+"");

    }

    // lay so binh luan hien ra
    public int getItemCount() {
        int soBinhLuan = binhLuanModelList.size();
        if(soBinhLuan > 5){
            return 5;
        }else{
            return binhLuanModelList.size();
        }
    }


}
