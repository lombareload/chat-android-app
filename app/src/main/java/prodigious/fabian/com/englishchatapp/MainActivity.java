package prodigious.fabian.com.englishchatapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import roboguice.inject.InjectView;


public class MainActivity extends Activity {
    @InjectView(R.id.messages_list)
    ListView messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateListView();
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
        String[] items = {"a","b","c"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.message, items);
        Log.i("english", "adapter: "+adapter);
        Toast.makeText(this, "adapter: "+adapter, Toast.LENGTH_LONG);
        Log.i("english", "messages: "+messages);

        //messages.setAdapter(adapter);
    }
}
