package prodigious.fabian.com.englishchatapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import prodigious.fabian.com.englishchatapp.model.Chat;
import prodigious.fabian.com.englishchatapp.model.ChatAdapter;

public class MainActivity extends Activity {
    ListView messages;
    List<Chat> chats = new ArrayList<Chat>();
    ArrayAdapter<Chat> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateListView();
        adapter = new ChatAdapter(this,R.layout.message, chats);
        messages.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateListView() {
        messages = (ListView) findViewById(R.id.messages_list);

        chats.add(new Chat(null, "hola"));
        chats.add(new Chat("yo", "que mas"));

    }

    public void sendMessage(View view) {
        EditText textInput = (EditText) findViewById(R.id.text_input);
        adapter.add(new Chat("yo", textInput.getText().toString()));
        textInput.setText("");
    }
}
