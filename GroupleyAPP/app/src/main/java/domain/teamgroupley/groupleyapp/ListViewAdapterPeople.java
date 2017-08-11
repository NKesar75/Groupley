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
 * Created by Raj Chandan on 8/10/2017.
 */

public class ListViewAdapterPeople extends ArrayAdapter<People>
{
    private Activity context;
    private int resource;
    private List<People> listImage;

    public ListViewAdapterPeople(Context context, int resource, List<People> objects)
    {
        super(context, resource);
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
            v = inflater.inflate(R.layout.list_item_people,null);
        }
        People people = getItem(position);
        ImageView img = (ImageView) v.findViewById(R.id.imgLoad_People);
        TextView username =(TextView) v.findViewById(R.id.username_People);

        Glide.with(context).load(listImage.get(position).getImageid()).into(img);
        username.setText(people.getUsername());

        return v;
    }
}
