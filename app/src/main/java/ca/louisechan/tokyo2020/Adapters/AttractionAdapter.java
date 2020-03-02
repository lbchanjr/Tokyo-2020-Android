package ca.louisechan.tokyo2020.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ca.louisechan.tokyo2020.Activities.MainActivity;
import ca.louisechan.tokyo2020.Models.Attraction;
import ca.louisechan.tokyo2020.R;

public class AttractionAdapter extends ArrayAdapter {

    private List<Attraction> attractions;
    private Context context;
    private int resource;

    public AttractionAdapter(Context context, int resource, List<Attraction> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        attractions = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get data to populate in custom view
        String name = attractions.get(position).getName();
        String address = attractions.get(position).getAddress();
        String briefDesc = attractions.get(position).getBriefDesc();
        String imgUrl = attractions.get(position).getImageUrl();

        // Create a layout inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        // Fetch text views
        TextView txtAttrName = (TextView) convertView.findViewById(R.id.textRowAttrName);
        TextView txtAttrAddr = (TextView) convertView.findViewById(R.id.textRowAttrAddress);
        TextView txtAttrBriefDesc = (TextView) convertView.findViewById(R.id.textRowAttrBriefDesc);
        ImageView imgAttrImage = (ImageView) convertView.findViewById(R.id.imgAttrPicture);

        // update row with name, address, description and picture of attraction
        txtAttrName.setText(name);
        txtAttrAddr.setText(address);
        txtAttrBriefDesc.setText(briefDesc);
// TEMPORARILY DISABLED IMAGE LOADING IN LIST
//        if (!imgUrl.isEmpty()) {
//            // Set image view with the attraction picture url
//            //Picasso.get().load(imgUrl).into(imgAttrImage);
//            Picasso.with(context).load(imgUrl).into(imgAttrImage);
//
////            Uri imgUri = Uri.parse(imgUrl);
////            imgAttrImage.setImageURI(imgUri);
//        }


        // Return the view with updated items
        return convertView;
    }
}
