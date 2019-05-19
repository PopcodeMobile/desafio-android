package kleyton.starwarswiki;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>{

    String url_base_favorites = "http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/{id}";
    private Context context;
    private List<Person> personList;

    public PersonAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Person person = personList.get(i);

        final String name       = person.getName();
        final String height     = person.getHeight();
        final String gender     = person.getGender();
        final String mass       = person.getMass();
        final String hair_color = person.getHair_color();
        final String skin_color = person.getSkin_color();
        final String eye_color  = person.getEye_color();
        final String birth_year = person.getBirth_year();
        final String homeworld  = person.getHomeworld();
        final String species    = person.getSpecies();
        final String isbookmark = person.getIsbookmark();

        viewHolder.name.setText(name);
        viewHolder.height.setText(height);
        viewHolder.gender.setText(gender);
        viewHolder.mass.setText(mass);

        SQLiteDatabaseHandler sqLiteDatabaseHandler = new SQLiteDatabaseHandler(context);
        if (sqLiteDatabaseHandler.isBookmark(name)) {
            viewHolder.favIcon.setAlpha(1.0f);
        } else {
            viewHolder.favIcon.setAlpha(0.2f);
        }

        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("height", height);
                intent.putExtra("gender", gender);
                intent.putExtra("mass", mass);
                intent.putExtra("hair_color", hair_color);
                intent.putExtra("skin_color", skin_color);
                intent.putExtra("eye_color", eye_color);
                intent.putExtra("birth_year", birth_year);
                intent.putExtra("homeworld", homeworld);
                intent.putExtra("species", species);
                intent.putExtra("isbookmark", isbookmark);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        viewHolder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Check connectivity first
                String name_text = viewHolder.name.getText().toString();
                SQLiteDatabaseHandler sqLiteDatabaseHandler = new SQLiteDatabaseHandler(context);

                if (sqLiteDatabaseHandler.isBookmark(name_text)) {
                    sqLiteDatabaseHandler.removeBookmark(name_text);
                    viewHolder.favIcon.setAlpha(0.2f);
                    Toast.makeText(context, "Favorito removido", Toast.LENGTH_SHORT).show();
                } else {
                    sqLiteDatabaseHandler.setBookmark(name_text);
                    sendBookmarkRequest(name_text);
                    viewHolder.favIcon.setAlpha(1.0f);
                    Toast.makeText(context, "Favorito adicionado", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, height, gender, mass;
        ImageView favIcon;
        LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);

            name    = itemView.findViewById(R.id.name_textView);
            height  = itemView.findViewById(R.id.height_textView);
            gender  = itemView.findViewById(R.id.gender_textView);
            mass    = itemView.findViewById(R.id.mass_textView);
            item    = itemView.findViewById(R.id.itemLinearLayout);
            favIcon = itemView.findViewById(R.id.fav_icon);
        }


    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    private void parseVolleyError(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            String err = data.getString("error");
            String error_message = data.getString("error_message");
            Toast.makeText(context, err + "\n" + error_message, Toast.LENGTH_LONG).show();
            Log.e("ERROR_RESPONSE", data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void sendBookmarkRequest(final String name) {
        final SharedPreferences pref = context.getSharedPreferences("StarWarsPref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_base_favorites, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status         = jsonObject.getString("status");
                    String message        = jsonObject.getString("message");
                    Toast.makeText(context, status + "\n" + message, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                parseVolleyError(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String parity = pref.getString("parity", "even");
                if (parity.equals("odd")) {
                    headers.put("Prefer", "status=400");
                    editor.putString("parity", "even");
                } else {
                    editor.putString("parity", "odd");
                }
                editor.apply();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", name);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


}
