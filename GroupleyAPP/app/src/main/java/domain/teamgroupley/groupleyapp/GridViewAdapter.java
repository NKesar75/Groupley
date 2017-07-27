package domain.teamgroupley.groupleyapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Raj Chandan on 7/20/2017.
 */

public class GridViewAdapter extends ArrayAdapter<Product> {
    public GridViewAdapter(Context context, int resource, List<Product> objects)
    {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if(null == v)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.griditem,null);
        }
        Product product = getItem(position);
        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        TextView txttitle =(TextView) v.findViewById(R.id.txttitle);
        TextView txtdate =(TextView) v.findViewById(R.id.txtdate);

        img.setImageResource(product.getImageid());
        txttitle.setText(product.getTitle());
        txtdate.setText(product.getDate());

        return v;
    }
}

