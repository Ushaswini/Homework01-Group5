package example.com.messageme;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nitin on 9/18/2017.
 */

public class MessagesArrayAdapter extends ArrayAdapter<CustMessage> {
    Context mContext;
    int mResource;
    List<CustMessage> musicTracks;

    public MessagesArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CustMessage> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.musicTracks= objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem,parent,false);
            holder= new ViewHolder();
            holder.senderName = (TextView) convertView.findViewById(R.id.tvSenderName);
            holder.date = (TextView) convertView.findViewById(R.id.tvDate);
            holder.time = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(holder);
        }

        CustMessage msg = new CustMessage();
        holder= (ViewHolder) convertView.getTag();
        holder.senderName.setText(msg.getSender());
        holder.date.setText(msg.getDate());
        holder.time.setText(msg.getTime());


        return convertView;
    }

    static class ViewHolder {
        TextView senderName;
        TextView date;
        TextView time;
    }
}
