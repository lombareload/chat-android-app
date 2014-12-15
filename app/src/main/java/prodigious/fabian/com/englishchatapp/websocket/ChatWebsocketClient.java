package prodigious.fabian.com.englishchatapp.websocket;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Arrays;

import prodigious.fabian.com.englishchatapp.MainActivity;
import prodigious.fabian.com.englishchatapp.model.Chat;

public class ChatWebsocketClient extends AsyncTask<Void, Chat, Void> implements Serializable, Emitter.Listener {

    public final static String CHAT_WEBSOCKET = ChatWebsocketClient.class.getCanonicalName();
    private String username;
    private final JSONObject metaMessage = new JSONObject();
    private ArrayAdapter<Chat> arrayAdapter;
    private final Socket client;
    private final String JSON_NAME = JSONObject.class.getName();
    private static ChatWebsocketClient instance;

    public static synchronized ChatWebsocketClient getInstance (final Activity context) throws URISyntaxException{
        if (instance == null){
            instance = new ChatWebsocketClient(context);
            instance.execute();
        }
        return instance;
    }

    private ChatWebsocketClient(final Activity context/*, final ArrayAdapter<Chat> arrayAdapter*/) throws URISyntaxException {
        client = IO.socket("http://10.0.2.2:8080");
        client.on("chat", this);
        client.on("login", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                try {
                    Log.e("login", response.toString());
                    String result = response.getString("login").trim();
                    if("name accepted".equals(result)) {
                        username = result;
                        Intent login = new Intent(context, MainActivity.class);
                        context.startActivity(login);
                    }
                } catch (JSONException e) {
                    Log.e("login", "login error", e);
                }

            }
        });
    }

    @Override
    public void call(Object... args) {
        if(args.length > 0){
            if(JSON_NAME.equals(args[0].getClass().getName())) {
                JSONObject message = (JSONObject) args[0];
                try {
                    Log.e("websocket", message.toString());
                    String chat = message.getString("chat");
                    publishProgress(new Chat("yo", chat));
                } catch (JSONException e) {
                    Log.e("websocket", "cant obtain chat message", e);
                }
            }
        }
        Log.i("websocket", (args.length > 0 ? args[0].getClass() + " - " : "") + Arrays.toString(args));
    }

    public void publishMessage (final String message) {
        try {
            metaMessage.put("chat", message);
            client.emit(Socket.EVENT_MESSAGE, metaMessage);
        } catch (JSONException e) {
            Log.e("websocket", "error sending message", e);
        }
    }

    public void login(final String login){
        username = login;
        try {
            metaMessage.put("login", login);
            client.emit("login", login);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setArrayAdapter(ArrayAdapter<Chat> arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }

    @Override
    protected Void doInBackground(Void... params) {
        client.connect();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Chat... values) {
        if(arrayAdapter != null){
            arrayAdapter.add(values[0]);
        }
    }
}
