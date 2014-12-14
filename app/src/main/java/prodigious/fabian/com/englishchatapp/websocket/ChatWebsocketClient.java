package prodigious.fabian.com.englishchatapp.websocket;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Arrays;

import prodigious.fabian.com.englishchatapp.model.Chat;

public class ChatWebsocketClient extends AsyncTask<Void, Chat, Void> implements Emitter.Listener {

    private String username;
    private final JSONObject metaMessage = new JSONObject();
    private final ArrayAdapter<Chat> messages;
    private final Socket client;
    private final String JSON_NAME = JSONObject.class.getName();

    public ChatWebsocketClient(ArrayAdapter<Chat> messages) throws URISyntaxException {
        client = IO.socket("http://10.0.2.2:8080");
        client.on(Socket.EVENT_MESSAGE, this);

        this.messages = messages;
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

    public void publishMessage (String message) {
        try {
            metaMessage.put("chat", message);
            client.emit(Socket.EVENT_MESSAGE, metaMessage);
        } catch (JSONException e) {
            Log.e("websocket", "error sending message", e);
        }
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
        messages.add(values[0]);
    }
}
