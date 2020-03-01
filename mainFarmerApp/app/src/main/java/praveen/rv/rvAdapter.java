package praveen.rv;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parveen.example.mainfarmerapp.R;
import com.parveen.example.mainfarmerapp.posts;
import com.parveen.example.mainfarmerapp.upload;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import praveen.postDetails.postDetails;

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.rvViewHolder> {
    Context context;
    private List<postDetails> data1 = new ArrayList<>();

    public rvAdapter(Context context, List<postDetails> data1) {
        this.context = context;
        this.data1 = data1;
    }

    @NonNull
    @Override
    public rvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater infflate = LayoutInflater.from(parent.getContext());
        View view = infflate.inflate(R.layout.list_item,parent,false);
        return new rvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final rvViewHolder viewHolder, int position) {
        final postDetails data = data1.get(position);
        viewHolder.Price.setText(data.getPriceDetails());
        viewHolder.Quantity.setText(data.getQuantityDetails());
        Picasso.get().load(data.getImgurl()).into(viewHolder.img);
        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editPgae = new Intent(context, upload.class);
                editPgae.putExtra("quantity",data.getQuantityDetails());
                editPgae.putExtra("price",data.getPriceDetails());
                editPgae.putExtra("title",data.getTitle());
                editPgae.putExtra("id",data.getId());
                context.startActivity(editPgae);

            }
        });
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deletePost = new Intent(context, posts.class);
                deletePost.putExtra("id_delete",data.getId());
                context.startActivity(deletePost);
            }
        });
    }



    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class rvViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView Quantity,Price;
        public ImageButton editButton,deleteButton;

        public rvViewHolder(View itemView){
            super(itemView);
            img = itemView.findViewById(R.id.img);
            Quantity = itemView.findViewById(R.id.quantity_Price);
            Price = itemView.findViewById(R.id.price_amount);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);




        }
    }
}

