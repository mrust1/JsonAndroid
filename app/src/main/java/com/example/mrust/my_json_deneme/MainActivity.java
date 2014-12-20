package com.example.mrust.my_json_deneme;
/*
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    //private String url1 = "http://api.openweathermap.org/data/2.5/weather?q=";

    private String url1 = "https://api.dakick.com/api/v1/";
    private EditText events,event,event_name,humidity,pressure;
    private HandleXML obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        events = (EditText)findViewById(R.id.editText1);
        event = (EditText)findViewById(R.id.editText2);
        event_name = (EditText)findViewById(R.id.editText3);

       // url = (EditText)findViewById(R.id.editText6);
     //  humidity = (EditText)findViewById(R.id.editText4);
     //   pressure = (EditText)findViewById(R.id.editText5);
    }


*/
    /////////////////////
    import java.util.ArrayList;
    import java.util.HashMap;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import android.app.ListActivity;
    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.AdapterView.OnItemClickListener;
    import android.widget.ListAdapter;
    import android.widget.ListView;
    import android.widget.SimpleAdapter;
    import android.widget.TextView;

    public class MainActivity extends ListActivity {

        private ProgressDialog pDialog;

        // URL to get contacts JSON
       // private static String url = "http://api.androidhive.info/contacts/";
        private static String url = "https://api.dakick.com/api/v1/events?page=1&per_page=1";


        // JSON Node names
        private static final String TAG_CONTACTS = "events";
        private static final String TAG_ID = "id";
        private static final String TAG_NAME = "name";
        private static final String TAG_EMAIL = "slug";
        private static final String TAG_ADDRESS = "icon";
        private static final String TAG_GENDER = "gender";
        private static final String TAG_PHONE = "phone";
        private static final String TAG_PHONE_MOBILE = "mobile";
        private static final String TAG_PHONE_HOME = "home";
        private static final String TAG_PHONE_OFFICE = "office";

        // contacts JSONArray
        JSONArray contacts = null;

        // Hashmap for ListView
        ArrayList<HashMap<String, String>> contactList;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            contactList = new ArrayList<HashMap<String, String>>();

            ListView lv = getListView();

            // Listview on item click listener
            lv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // getting values from selected ListItem
                    String name = ((TextView) view.findViewById(R.id.name))
                            .getText().toString();
                    String cost = ((TextView) view.findViewById(R.id.email))
                            .getText().toString();
                    String description = ((TextView) view.findViewById(R.id.mobile))
                            .getText().toString();

                    // Starting single contact activity
                    Intent in = new Intent(getApplicationContext(),SingleContactActivity.class);
                    in.putExtra(TAG_NAME, name);
                    in.putExtra(TAG_EMAIL, cost);
                    in.putExtra(TAG_ADDRESS, description);
                    startActivity(in);

                }
            });

            // Calling async task to get json
            new GetContacts().execute();
        }

        /**
         * Async task class to get json by making HTTP call
         * */
        private class GetContacts extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();

            }

            @Override
            protected Void doInBackground(Void... arg0) {
                // Creating service handler class instance
                ServiceHandler sh = new ServiceHandler();

                // Making a request to url and getting response
                String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

                Log.d("Response: ", "> " + jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node
                        contacts = jsonObj.getJSONArray(TAG_CONTACTS);

                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);

                            String id = c.getString(TAG_ID);
                            String name = c.getString(TAG_NAME);
                            String email = c.getString(TAG_EMAIL);
                            String address = c.getString(TAG_ADDRESS);
                          //  String gender = c.getString(TAG_GENDER);

                            // Phone node is JSON Object
                         //   JSONObject phone = c.getJSONObject(TAG_PHONE);
                         //  String mobile = phone.getString(TAG_PHONE_MOBILE);
                         //   String home = phone.getString(TAG_PHONE_HOME);
                         //   String office = phone.getString(TAG_PHONE_OFFICE);

                            // tmp hashmap for single contact
                            HashMap<String, String> contact = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            contact.put(TAG_ID, id);
                            contact.put(TAG_NAME, name);
                            contact.put(TAG_EMAIL, email);
                          //  contact.put(TAG_PHONE_MOBILE, mobile);

                            // adding contact to contact list
                            contactList.add(contact);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                // Dismiss the progress dialog
                if (pDialog.isShowing())
                    pDialog.dismiss();
                /**
                 * Updating parsed JSON data into ListView
                 * */
                ListAdapter adapter = new SimpleAdapter(
                        MainActivity.this, contactList,
                        R.layout.list_item, new String[] { TAG_NAME, TAG_EMAIL,
                        TAG_ADDRESS }, new int[] { R.id.name,
                        R.id.email, R.id.mobile });

                setListAdapter(adapter);
            }

        }

    }
    /////////////////////








/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items
        //to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void open(View view){
       // String url = events.getText().toString(); // address yada baska bişey
        String url = "events";
        String finalUrl = " 'X-DAKICK-API-TOKEN: fsTXyvfrJ4EuhsUArs2x' " + url1 + url + "?page=1&per_page=1";
       event.setText(finalUrl);
       // obj = new HandleXML(finalUrl);
       // obj.fetchJSON();

        while(obj.parsingComplete) {
            event.setText(obj.getEvent());
            event_name.setText(obj.getEventName());
            // humidity.setText(obj.getHumidity());
            // pressure.setText(obj.getPressure());
        }
    }
}*/
