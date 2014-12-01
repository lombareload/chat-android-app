package prodigious.fabian.com.englishchatapp.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import prodigious.fabian.com.englishchatapp.R;

public class ChatAdapter extends ArrayAdapter<Chat> {
    private final List<Chat> chats;

    public ChatAdapter(Context context, int textViewId, List<Chat> chats){
        super(context, textViewId);
        this.chats = chats;
        Log.e("chats", chats.toString());
    }
    @Override
    public void add(Chat chat) {
        chats.add(chat);
        super.add(chat);
        this.notifyDataSetChanged();
        Log.i("notificacion", "added");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message, null);
        }
        Chat message = chats.get(position);
        Log.e("Chat", message.toString());
        TextView text = (TextView) convertView.findViewById(R.id.message);
        text.setText(message.getMessage());
        return convertView;
    }

    @Override
    public int getCount() {
        return chats.size();
    }
}
