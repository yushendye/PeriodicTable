package com.chinmay.periodictable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    List<Integer> allElements = Arrays.asList(R.id.e1,R.id.e2,R.id.e3,R.id.e4,R.id.e5,R.id.e6,R.id.e7,R.id.e8,R.id.e9,R.id.e10,
            R.id.e11,R.id.e12,R.id.e13,R.id.e14,R.id.e15,R.id.e16,R.id.e17,R.id.e18,R.id.e19,R.id.e20,
            R.id.e21,R.id.e22,R.id.e23,R.id.e24,R.id.e25,R.id.e26,R.id.e27,R.id.e28,R.id.e29,R.id.e30,
            R.id.e31,R.id.e32,R.id.e33,R.id.e34,R.id.e35,R.id.e36,R.id.e37,R.id.e38,R.id.e39,R.id.e40,
            R.id.e41,R.id.e42,R.id.e43,R.id.e44,R.id.e45,R.id.e46,R.id.e47,R.id.e48,R.id.e49,R.id.e50,
            R.id.e51,R.id.e52,R.id.e53,R.id.e54,R.id.e55,R.id.e56,R.id.e57,R.id.e58,R.id.e59,R.id.e60,
            R.id.e61,R.id.e62,R.id.e63,R.id.e64,R.id.e65,R.id.e66,R.id.e67,R.id.e68,R.id.e69,R.id.e70,
            R.id.e71,R.id.e72,R.id.e73,R.id.e74,R.id.e75,R.id.e76,R.id.e77,R.id.e78,R.id.e79,R.id.e80,
            R.id.e81,R.id.e82,R.id.e83,R.id.e84,R.id.e85,R.id.e86,R.id.e87,R.id.e88,R.id.e89,R.id.e90,
            R.id.e91,R.id.e92,R.id.e93,R.id.e94,R.id.e95,R.id.e96,R.id.e97,R.id.e98,R.id.e99,R.id.e100,
            R.id.e101,R.id.e102,R.id.e103,R.id.e104,R.id.e105,R.id.e106,R.id.e107,R.id.e108,R.id.e109,R.id.e110,
            R.id.e111,R.id.e112,R.id.e113,R.id.e114,R.id.e115,R.id.e116,R.id.e117,R.id.e118);

    TextView[] element_txt_view = new TextView[118];

    TextView alkaline_earth_metals, transition_metals, p_block_elements, chalcogens, halogens, noble_gases, metalloids, alkaline_metals;

    Dialog dialog;

    int[] c = new int[118];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speaker = new TextToSpeech(this, this);
        setContentView(R.layout.activity_main);
        initTextViews();
    }

    /*
        method readJSON():
            Reads the JSON file put in assets folder.
            Returns the JSON data as a string
     */
    String readJSON(){
        String json = null;
        try {
            InputStream is = getAssets().open("PeriodicTable.json");
            int avl = is.available();
            byte[] buffer = new byte[avl];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }catch (Exception e){
            toast(e.toString());
        }
        return json;
    }

    void toast(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    void initTextViews(){
        int size = allElements.size();
        element_txt_view = new TextView[size];

        /*
            Code below is used to store the default colors
            Purpose of this is to set colors to the defaults after they are highlighted.
         */
        for(int i = 0; i < element_txt_view.length; i++){
            element_txt_view[i] = findViewById(allElements.get(i));
            element_txt_view[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayDetails(view.getId());
                }
            });
        }
        alkaline_earth_metals = findViewById(R.id.alkaline_earth_metals);
        transition_metals = findViewById(R.id.transition_metals);
        p_block_elements = findViewById(R.id.p_block_elements);
        chalcogens = findViewById(R.id.chalcogens);
        halogens = findViewById(R.id.halogens);
        noble_gases = findViewById(R.id.noble_gases);
        metalloids = findViewById(R.id.metalloids);
        alkaline_metals = findViewById(R.id.alkali_metals);

        ColorDrawable d[] = new ColorDrawable[118];
        for(int i = 0; i < element_txt_view.length; i++) {
            d[i] = (ColorDrawable)element_txt_view[i].getBackground();
            c[i] = d[i].getColor();
        }

        TextView[] headers = {alkaline_earth_metals, transition_metals, p_block_elements, chalcogens, halogens, noble_gases, metalloids, alkaline_metals};
        for(int i = 0; i < headers.length;i++){
            headers[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    highlight(view.getId());
                    describe(view.getId());
                }
            });
        }
    }

    TextToSpeech speaker;
    String text = "";
    public void read(){
        speaker.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onPause() {

        if(speaker != null){
            speaker.stop();
            speaker.shutdown();
        }
        super.onPause();
    }

    void displayDetails(int id){
        /*
            Parameter id will be passed programmatically when user clicks a TextView
            ID of that TexView is passed as a parameter to this function so as to identify which element is clicked


            A object of TextView is taken because in activity_main.xml file every element is displayed as TextView
            In TextView we also can retrieve the name.
         */

        //find TextView element using the ID passed as parameter.
        final TextView view = findViewById(id);

        //in activity_main.xml, we can see text of element is stored as (atomic_number)\n(symbol).
        //we can separate them using \n as regex.
        String[] details = view.getText().toString().split("\n");


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.element_details);

        ImageView element_img = dialog.findViewById(R.id.elementImg);
        TextView element_symbol = dialog.findViewById(R.id.element_symbol);
        final TextView element_name = dialog.findViewById(R.id.element_name);
        final TextView element_discoverer = dialog.findViewById(R.id.element_discoverer);
        final TextView element_appearance = dialog.findViewById(R.id.element_appearance);
        TextView atomic_no = dialog.findViewById(R.id.atomic_number);
        TextView atomic_weight = dialog.findViewById(R.id.element_weight);
        ImageView close_btn = dialog.findViewById(R.id.close_btn);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_dialog(view);
            }
        });

        element_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = element_name.getText().toString();
                speaker.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        TextView read_info = dialog.findViewById(R.id.txt_read_info);
        read_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";
                data += "Name " + element_name.getText().toString() + ".";
                data += "It was discovered by " + element_discoverer.getText().toString() + ".";
                data += "Appearance " + element_appearance.getText().toString() + ".";
                speaker.speak(data, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        final int atomic_number = Integer.parseInt(details[0]); //after split is performed at 0th index atomic number will be stored
        String symbol = details[1];                       //after split is performed at 1st index symbol will be stored.

        String elt_name;
        double elt_atomic_weight;

        String data = readJSON();
        try {
            JSONObject object = new JSONObject(data);
            JSONArray array = object.getJSONArray("elements");
            for (int i = 0; i < array.length(); i++){
                JSONObject iterator = array.getJSONObject(i);
                int it_atomic_no = iterator.getInt("number");
                if(it_atomic_no == atomic_number){
                    String el_sym = iterator.getString("symbol");
                    String el_name = iterator.getString("name");
                    double el_atomic_mass = iterator.getDouble("atomic_mass");
                    String el_img = iterator.getString("spectral_img");
                    String el_app = iterator.getString("appearance");
                    String el_discoverer = iterator.getString("discovered_by");

                    element_appearance.setText(el_app);
                    element_discoverer.setText(el_discoverer);
                    element_symbol.setText(el_sym);
                    element_name.setText(el_name);
                    atomic_weight.setText(el_atomic_mass + "");
                    atomic_no.setText(new String(it_atomic_no + ""));
                    Picasso.get().load(el_img).into(element_img);
                }
            }
        }catch (Exception e){
            toast("error at " + e.getMessage());
        }
        dialog.show();

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_dialog(view);
            }
        });
    }

    void close_dialog(View view){
        dialog.dismiss();
    }

    void highlight(int id){
        for(int i = 0; i < element_txt_view.length; i++)
            element_txt_view[i].setBackgroundColor(c[i]);

        TextView selected = findViewById(id);
        ColorDrawable color = (ColorDrawable)selected.getBackground();
        int colorid = color.getColor();

        for(int i = 0; i < element_txt_view.length; i++){
            ColorDrawable elt_color = (ColorDrawable) element_txt_view[i].getBackground();
            int code = elt_color.getColor();

            if(colorid == code)
                element_txt_view[i].setBackgroundColor(getResources().getColor(R.color.pureBlack));
                element_txt_view[i].setTextColor(getResources().getColor(R.color.white));
        }
    }

    void describe(int id){
        TextView view = findViewById(id);
        String description = "";
        String type = view.getText().toString().replace('\n', ' ');

        switch (type){
            case "Alkaline Earth Metals":
                description = "A group of chemical elements in the periodic table with similar properties: shiny, silvery-white, somewhat reactive at standard temperature and pressure. They readily lose their two outermost electrons to form cations with charge +2.";
                break;
            case "Transition Metals" :
                description = "They are harder and less reactive than the alkaline earth metals. They are also harder than the post transition metals. They make colorful chemical compounds with other elements. Most of them have more than one oxidation state";
                break;
            case "p-block elements":
                description = " The p-block elements are found on the right side of the periodic table. They include the boron, carbon, nitrogen, oxygen and flourine families in addition to the noble gases. The noble gases have full p-orbital's and are nonreactive.";
                break;
            case "Chalcogens":
                description = "The chalcogens are the name for the Periodic Table group 16 (or V1). The group consists of the elements: oxygen, sulfur, selenium, tellurium, and polonium.The term \"chalcogens\" was derived from the Greek word chalcos , meaning \"ore formers,\" since they all are be found in copper ores.";
                break;
            case "Halogens":
                description = "Halogen, any of the six nonmetallic elements that constitute Group 17 (Group VIIa) of the periodic table. They were given the name halogen, from the Greek roots hal- (“salt”) and -gen (“to produce”), because they all produce sodium salts of similar properties, of which sodium chloride—table salt, or halite—is best known.";
                break;
            case "Noble Gases":
                description = "The noble gases are colourless, odourless, tasteless, nonflammable gases. They traditionally have been labeled Group 0 in the periodic table because for decades after their discovery it was believed that they could not bond to other atoms; that is, that their atoms could not combine with those of other elements to form chemical compounds.";
                break;
            case "Metalloids":
                description = "A metalloid is an element that has properties that are intermediate between those of metals and nonmetals.  Metalloids can also be called semimetals. On the periodic table, the elements colored yellow, which generally border the stair-step line, are considered to be metalloids.";
                break;
            case "Alkali Metals" :
                description = "The alkali metals are all soft, shiny, reactive metals. Although they are soft enough to cut with a knife, exposing a bright surface, the metals react with water and air to quickly tarnish. The pure metals are stored in an inert atmosphere or under oil to prevent oxidation.";
                break;
        }

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.group_description);

        TextView group_name = dialog.findViewById(R.id.group_name);
        TextView group_desc = dialog.findViewById(R.id.grp_desc);
        ImageView close_btn = dialog.findViewById(R.id.close_btn);

        group_name.setText(type);
        group_desc.setText(description);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close_dialog(view);
            }
        });
        dialog.show();
    }
    @Override
    public void onInit(int status) {
        if(status != TextToSpeech.ERROR){
            speaker.setLanguage(Locale.UK);
            Toast.makeText(getApplicationContext(), "TTS initiated", Toast.LENGTH_LONG).show();
        }
    }



}
