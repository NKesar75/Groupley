package domain.teamgroupley.groupleyapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Raj Chandan on 7/20/2017.
 */

public class ListViewAdapter extends ArrayAdapter<Product>
{
    private Activity context;
    private int resource;
    private List<Product> listImage;

    public ListViewAdapter(Context context, int resource, List<Product> objects)
    {
        super(context, resource, objects);
        this.context = (Activity) context;
        this.resource = resource;
        listImage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if(null == v)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item,null);
        }
        Product product = getItem(position);
        ImageView img = (ImageView) v.findViewById(R.id.imgLoad);
        TextView txttitle =(TextView) v.findViewById(R.id.txttitle);
        TextView txtdate =(TextView) v.findViewById(R.id.txtdate);
        TextView txtcat = (TextView) v.findViewById(R.id.txtcat);

        Glide.with(context).load(listImage.get(position).getImageid()).into(img);
        txttitle.setText(product.getTitle());
        txtdate.setText(product.getDate());
        txtcat.setText(product.getCategory());

        return v;
    }
}
