package com.andremartinez.losverdes;

import android.app.Fragment;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ActionsFragment extends Fragment {

    DataBaseManager manager = LosVerdesCl.getManager();
    int operac = 1;

    public ActionsFragment() {      /* Required empty public constructor */   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_actions, container, false);

        final EditText eName =(EditText) rootView.findViewById(R.id.bsede);
        final EditText eNewLat =(EditText) rootView.findViewById(R.id.eLatNew);
        final EditText eNewLong =(EditText) rootView.findViewById(R.id.eLongNew);

        final TextView tNameV =(TextView) rootView.findViewById(R.id.tName);
        final TextView tLatV =(TextView) rootView.findViewById(R.id.tLat);
        final TextView tLongV =(TextView) rootView.findViewById(R.id.tLong);

        Button botOk =(Button) rootView.findViewById(R.id.buttonOK);
        final Button botDel =(Button) rootView.findViewById(R.id.buttonDel);
        final Button botSav =(Button) rootView.findViewById(R.id.buttonSave);

        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.action_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.radioFind:
                        operac = 1;
                        break;
                    case R.id.radioDel:
                        operac = 2;
                        break;
                    case R.id.radioAdd:
                        operac = 3;
                        break;
                    case R.id.radioMod:
                        operac = 4;
                        break;
                }
            }
        });

        botOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean find;       //Bandera que indica que se encontro la sede

                find = obtener(eName, tNameV, tLatV, tLongV);

                switch (operac){
                    case 1:
                        ocultarTodo(botDel, botSav, eNewLat, eNewLong);
                        break;
                    case 2:
                        if(find) {
                            eNewLat.setText(tLatV.getText());
                            eNewLong.setText(tLongV.getText());

                            botDel.setVisibility(View.VISIBLE);
                            eNewLat.setVisibility(View.INVISIBLE);
                            eNewLong.setVisibility(View.INVISIBLE);
                            botSav.setVisibility(View.INVISIBLE);
                        }
                        else ocultarTodo(botDel, botSav, eNewLat, eNewLong);
                        break;
                    case 3:
                        if(!find) {
                            tNameV.setText(eName.getText());
                            tLatV.setText("");
                            tLongV.setText("");

                            eNewLat.setText("");
                            eNewLong.setText("");

                            botDel.setVisibility(View.INVISIBLE);
                            eNewLat.setVisibility(View.VISIBLE);
                            eNewLong.setVisibility(View.VISIBLE);
                            botSav.setVisibility(View.VISIBLE);
                        }
                        else ocultarTodo(botDel, botSav, eNewLat, eNewLong);
                        break;
                    case 4:
                        if(find) {
                            botDel.setVisibility(View.INVISIBLE);
                            eNewLat.setVisibility(View.VISIBLE);
                            eNewLong.setVisibility(View.VISIBLE);
                            botSav.setVisibility(View.VISIBLE);
                        }
                        else ocultarTodo(botDel, botSav, eNewLat, eNewLong);
                        break;
                }
            }
        });

        botDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.eliminar(tNameV.getText().toString());

                tNameV.setText("");
                tLatV.setText("");
                tLongV.setText("");

                Toast.makeText(getActivity(), "Registro Eliminado", Toast.LENGTH_SHORT).show();
                LosVerdesCl.setManager(manager);    //Actualizamos la base de datos de la clase principal
            }
        });

        botSav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NameV = tNameV.getText().toString();
                String LatV = eNewLat.getText().toString();
                String LongV = eNewLong.getText().toString();

                if (operac == 3) {
                    manager.insertar(NameV, LatV, LongV);
                    Toast.makeText(getActivity(), "Registro Agregado", Toast.LENGTH_SHORT).show();
                }
                else if (operac == 4){
                    manager.modificar_latlong(NameV, LatV, LongV);
                    Toast.makeText(getActivity(), "Registro Modificado", Toast.LENGTH_SHORT).show();
                }

                tLatV.setText(LatV);
                tLongV.setText(LongV);

                LosVerdesCl.setManager(manager);    //Actualizamos la base de datos de la clase principal
            }
        });

        return rootView;
    }

        public boolean obtener (EditText eName, TextView tNameV, TextView tLatV, TextView tLongV) {

        Cursor cursor = manager.buscar_name(eName.getText().toString());

        cursor.moveToFirst();   //mover el cursor al principio
        try{
            String dbnombre = cursor.getString(cursor.getColumnIndex(manager.SD_NAME));
            tNameV.setText(dbnombre);
            String dbLat = cursor.getString(cursor.getColumnIndex(manager.SD_LAT));
            tLatV.setText(dbLat);
            String dbLong = cursor.getString(cursor.getColumnIndex(manager.SD_LONG));
            tLongV.setText(dbLong);

            return true;
        }
        catch(CursorIndexOutOfBoundsException e){
            Toast.makeText(getActivity(), "No Existe este Registro", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void ocultarTodo(Button botDel, Button botSav, EditText eNewLat, EditText eNewLong){
        botDel.setVisibility(View.INVISIBLE);
        eNewLat.setVisibility(View.INVISIBLE);
        eNewLong.setVisibility(View.INVISIBLE);
        botSav.setVisibility(View.INVISIBLE);
    }
}
