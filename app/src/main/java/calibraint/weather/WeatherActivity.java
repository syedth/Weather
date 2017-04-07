package calibraint.weather;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static calibraint.weather.R.id.bottom_sheet;
import static calibraint.weather.R.id.recyclerView;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG_TEMP = "temp";
    private static final String TAG_HuM = "humidity";
    private static final String TAG_PRESS = "pressure";
    private static final String CLEAR_SKY = "clear sky";
    private static final String FEW_CLOUDS = " few clouds ";
    private static final String SCATTERED_CLOUDS = " scattered clouds ";
    private static final String RAIN_CLOUDS = "rain";
    private static String URL_WEATHER = "http://api.openweathermap.org/data/2.5/forecast?id=1264527&appid=4a70f6678e6c3eb7fe3b8f77f4648ce9&mode=json&units=metric";
    private TextView text_temp_min;
    private TextView text_temp_max;
    private TextView location;
    private TextView description;
    private TextView temp;
    private ImageView climate;
    private ImageView tempmax;
    private ImageView tempmin;
    private RecyclerView recyclerView;
    private WeatherAdapter wAdapter;
    List<WeatherModal> weatherList;
    private BottomSheetBehavior mBottomSheetBehavior;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        tempmin = (ImageView) findViewById(R.id.temp_min);
        text_temp_min = (TextView) findViewById(R.id.text_temp_min);
        tempmax = (ImageView) findViewById(R.id.temp_max);
        text_temp_max = (TextView) findViewById(R.id.text_temp_max);
        location = (TextView) findViewById(R.id.location);
        description = (TextView) findViewById(R.id.text_description);
        temp = (TextView) findViewById(R.id.text_temp);
        climate = (ImageView) findViewById(R.id.climate_condition);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        View bottomSheet = findViewById(bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        WeatherDetails weather = new WeatherDetails();
        weather.execute();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Weather Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class WeatherDetails extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL(URL_WEATHER);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d("", "");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = bufferedReader.readLine()) != null) {
                        response.append(inputLine);
                    }

                    Log.d("", response.toString());
                    connection.disconnect();
                    bufferedReader.close();
                    return new JSONObject(response.toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_LONG).show();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(JSONObject result) {
            if (result != null) {
                Log.d("", "");
                try {
                    JSONObject jsonCity = result.getJSONObject("city");
                    jsonCity.getString("name");
                    location.setText(jsonCity.getString("name"));
                    JSONArray jsonmain = result.getJSONArray("list");
                    JSONObject mainObject = jsonmain.getJSONObject(0);
                    temp.setText(mainObject.getJSONObject("main").getString("temp") + (char) 0x00B0);
                    text_temp_min.setText(mainObject.getJSONObject("main").getString("temp_min") + (char) 0x00B0);
                    text_temp_max.setText(mainObject.getJSONObject("main").getString("temp_max") + (char) 0x00B0);
                    String descriptionAsString = result.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
                    description.setText(descriptionAsString);
                    if (descriptionAsString != null) {
                        switch (descriptionAsString) {
                            case CLEAR_SKY:
                                climate.setImageResource(R.drawable.clear_sky);
                                break;
                            case FEW_CLOUDS:
                                climate.setImageResource(R.drawable.few_clouds);
                                break;
                            case SCATTERED_CLOUDS:
                                climate.setImageResource(R.drawable.few_clouds);
                                break;
                            case RAIN_CLOUDS:
                                climate.setImageResource(R.drawable.rain_sky);
                                break;
                        }

                    }
                    weatherList = new ArrayList<>();

                    for (int i = 0; i < 7; i++) {
                        JSONObject weatherObject = jsonmain.getJSONObject(i);
                        JSONObject main = weatherObject.getJSONObject("main");
                        JSONArray weather = weatherObject.getJSONArray("weather");
                        String tempMin = main.getString("temp_min");
                        String tempMax = main.getString("temp_max");

                        String description = weather.getJSONObject(0).getString("description");
                        String time = weatherObject.getString("dt_txt");
                        WeatherModal weathermodal = new WeatherModal(time, tempMax, tempMin, description);
                        weatherList.add(weathermodal);
                    }
                    wAdapter = new WeatherAdapter(WeatherActivity.this, weatherList);
                    recyclerView.setAdapter(wAdapter);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setLayoutManager(new LinearLayoutManager(WeatherActivity.this));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("", "");
            }
        }
    }
}
