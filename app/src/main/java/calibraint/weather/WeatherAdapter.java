package calibraint.weather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by syedthoufiq on 07/04/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {

    private List<WeatherModal> weatherList;
    private static final String CLEAR_SKY = "clear sky";
    private static final String FEW_CLOUDS = " few clouds ";
    private static final String SCATTERED_CLOUDS = " scattered clouds ";
    private static final String RAIN_CLOUDS = "rain";

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView climateday, daytempmin, daytempmax;
        public ImageView dayclimate;

        public MyViewHolder(View itemView) {
            super(itemView);
            daytempmin = (TextView)itemView.findViewById(R.id.day_temp_min);
            daytempmax = (TextView)itemView.findViewById(R.id.day_temp_max);
            climateday = (TextView)itemView.findViewById(R.id.day_climate_bottomsheet);
            dayclimate = (ImageView)itemView.findViewById(R.id.day_img_climate_bottomsheet);
        }
    }
        public WeatherAdapter(WeatherActivity weatherActivity, List<WeatherModal> weatherList){
            this.weatherList = weatherList;
        }

    @Override
    public WeatherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemadapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.MyViewHolder holder, int position) {

        WeatherModal weathermodal = weatherList.get(position);
        holder.daytempmin.setText(weathermodal.getTempmin());
        holder.daytempmax.setText(weathermodal.getTempmax());
        holder.climateday.setText(weathermodal.getDay());
        switch (weathermodal.getDescription()){
            case CLEAR_SKY :
                holder.dayclimate.setImageResource(R.drawable.clear_sky);
                break;
            case FEW_CLOUDS :
                holder.dayclimate.setImageResource(R.drawable.few_clouds);
                break;
            case SCATTERED_CLOUDS :
                holder.dayclimate.setImageResource(R.drawable.few_clouds);
                break;
            case RAIN_CLOUDS :
                holder.dayclimate.setImageResource(R.drawable.rain_sky);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

}
