package com.example.carlosje.creditravel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;

public class CapturaActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private TextView estado, Fecha_Nacimiento, url_photo,mLatitude,mLongitude,seccion;
    private String usuario, preFecha_clave;
    private EditText ciudad,  profecion, edad,whatsapp,sector, Apell_P,Apell_M, nombre, calle, numero , colonia,  municipio,  localidad, emision, vigencia, clave, email, telefono, face, observ;
    private RadioButton RB_sexo_H, RB_sexo_M, RB_acepto, RB_niv_esc_primaria, RB_niv_esc_secundaria, RB_niv_esc_preparatoria, RB_niv_esc_licenciatura;
    private FloatingActionButton fl_btn_save, fl_btn_add_photo;

    private  int dia_c, mes_c, año_c, hr_c, min_c;
    private  String dia_c_f, mes_c_f, hr_c_f, min_c_f;
    private int flag_foto;
    private String encodedImage;

    private String ciudad_f, niv_escol_F, profecion_F, edad_F, acepto_F, whatsapp_F, sector_F, FechaNow, urlFoto_F, observ_F, Apell_P_F, Apell_M_F,nombre_F, calle_F, numero_F, colonia_F, municipio_F, seccion_F,localidad_F, emision_F, vigencia_F, estado_F,clave_F, sexo_F, fecha_nacimiento_F, email_F, telefono_F, face_F;

    private ProgressBar progressBarPhoto;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);
        usuario = getIntent().getStringExtra("usuario");

        Fecha_Nacimiento = (TextView) findViewById(R.id.Fecha_Nacimiento);

        Apell_P = (EditText) findViewById(R.id.Apell_P);
        Apell_M = (EditText) findViewById(R.id.Apell_M);
        nombre = (EditText) findViewById(R.id.nombre);
        calle = (EditText) findViewById(R.id.calle);
        numero = (EditText) findViewById(R.id.numero);
        colonia = (EditText) findViewById(R.id.colonia);
        ciudad = (EditText) findViewById(R.id.ciudad);
        municipio = (EditText) findViewById(R.id.municipio);



        edad= (EditText) findViewById(R.id.edad);
        profecion= (EditText) findViewById(R.id.profecion);

        estado=(TextView) findViewById(R.id.estado);



        email = (EditText) findViewById(R.id.email);
        telefono = (EditText) findViewById(R.id.telefono);
        face = (EditText) findViewById(R.id.face);
        observ= (EditText) findViewById(R.id.observ);
        whatsapp= (EditText) findViewById(R.id.whatsapp);


        mLatitude = (TextView) findViewById(R.id.mLatitude);
        mLongitude = (TextView) findViewById(R.id.mLongitude);


        url_photo = (TextView) findViewById(R.id.url_photo);
        RB_acepto= (RadioButton) findViewById(R.id.RB_acepto);
        RB_sexo_H = (RadioButton) findViewById(R.id.RB_sexo_H);
        RB_sexo_M = (RadioButton) findViewById(R.id.RB_sexo_M);


        RB_niv_esc_primaria = (RadioButton) findViewById(R.id.RB_niv_esc_primaria);
        RB_niv_esc_secundaria = (RadioButton) findViewById(R.id.RB_niv_esc_secundaria);
        RB_niv_esc_preparatoria = (RadioButton) findViewById(R.id.RB_niv_esc_preparatoria);
        RB_niv_esc_licenciatura = (RadioButton) findViewById(R.id.RB_niv_esc_licenciatura);




        fl_btn_save= (FloatingActionButton) findViewById(R.id.fl_btn_save);
        fl_btn_add_photo= (FloatingActionButton) findViewById(R.id.fl_btn_add_photo);

        progressBarPhoto= (ProgressBar) findViewById(R.id.progressBarPhoto);

        progressBarPhoto.setVisibility(View.GONE);
        acepto_F="";
        fecha_nacimiento_F="";
        Apell_P_F="";
        Apell_M_F="";
        nombre_F="";
        calle_F="";
        numero_F="";
        colonia_F="";
        estado_F="";
        municipio_F="";
        seccion_F="";
        localidad_F="";
        emision_F="";
        vigencia_F="";
        clave_F="";
        sexo_F="";
        preFecha_clave="";
        email_F="";
        telefono_F="";
        face_F="";
        observ_F="";
        urlFoto_F="";
        whatsapp_F="";
        sector_F="";
        edad_F="";
        profecion_F="";
        niv_escol_F="";

        flag_foto=0;

        long timeInMillis = System.currentTimeMillis();
        Calendar calendario = Calendar.getInstance();

        dia_c= calendario.get(Calendar.DAY_OF_MONTH);
        mes_c= calendario.get(Calendar.MONTH)+1;
        año_c= calendario.get(Calendar.YEAR);
        hr_c= calendario.get(Calendar.HOUR_OF_DAY);
        min_c= calendario.get(Calendar.MINUTE);


        if(dia_c<10){
            dia_c_f="0"+dia_c;
        }else{
            dia_c_f=""+dia_c;
        }

        if(mes_c<10){
            mes_c_f="0"+mes_c;
        }else{
            mes_c_f=""+mes_c;
        }

        if(hr_c<10){
            hr_c_f="0"+hr_c;
        }else{
            hr_c_f=""+hr_c;
        }

        if(min_c<10){
            min_c_f="0"+min_c;
        }
        else{
            min_c_f=""+min_c;
        }

        FechaNow = dia_c_f+ "/" +mes_c_f+ "/" +año_c+ " " + hr_c_f +":"+ min_c_f;



        ////TODO Geolocalizacion

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();


    }

    private Location mLastLocation;
    private void processLastLocation() {
        getLastLocation();
        if (mLastLocation != null) {
            updateLocationUI();
        }
    }
    private void getLastLocation() {
        if (isLocationPermissionGranted()) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } else {
            manageDeniedPermission();
        }
    }

    private boolean isLocationPermissionGranted() {
        int permission = ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    private void manageDeniedPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Aquí muestras confirmación explicativa al usuario
            // por si rechazó los permisos anteriormente
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }
    private void updateLocationUI() {

        String errorMessage = "";

        mLatitude.setText(valueOf(mLastLocation.getLatitude()));
        mLongitude.setText(valueOf(mLastLocation.getLongitude()));
    }





    private GoogleApiClient mGoogleApiClient;
    public static final int REQUEST_LOCATION = 1;
    public static final int REQUEST_CHECK_SETTINGS = 2;

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Aquí muestras confirmación explicativa al usuario
                // por si rechazó los permisos anteriormente
            } else {
                ActivityCompat.requestPermissions(
                        this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);
            }
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitude.setText(valueOf(mLastLocation.getLatitude()));
                mLongitude.setText(valueOf(mLastLocation.getLongitude()));
            } else {
                Toast.makeText(this, "Ubicación no encontrada, favor de Conectar GPS", Toast.LENGTH_LONG).show();
            }
        }






    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    mLatitude.setText(valueOf(mLastLocation.getLatitude()));
                    mLongitude.setText(valueOf(mLastLocation.getLongitude()));
                } else {
                    Toast.makeText(this, "Ubicación no encontrada", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Permisos no otorgados", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }









    private static final int PICK_IMAGE_ID = 234;
    public void llamarIntent(View v) {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }



    public void showDatePickerDialog_Head(View v) {


        DialogFragment newFragment = new DatePick();
        Bundle args = new Bundle();
        //args.putInt("num", 1);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED){
            if (requestCode == PICK_IMAGE_ID) {
                Bitmap image = ImagePicker.getImageFromResult(this, resultCode, data);

                ImageView img = (ImageView) findViewById(R.id.photo_ife);

                img.setImageBitmap(image);

                flag_foto = 1;

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                imageBytes = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            }
        }

        if (requestCode == 1) {

            if(resultCode == Activity.RESULT_OK) {

                String act_sel = data.getStringExtra("seccion_seleccion");

                estado.setText(act_sel);


            }

            ////visible campo falla otro




        }




    }



    public void Seleccion_estado(View view) {


        Intent i = new Intent(this, SCActivity.class);
        i.putExtra("tipo","seccion");
        startActivityForResult(i, 1);


    }

    public void ver_aviso(View view) {

        Intent activity_aviso = new Intent(getApplicationContext(), AvisoActivity.class);
        startActivity(activity_aviso);


    }

    private byte[] imageBytes;
    private StorageReference mStorageRef;
    private  String urlFoto;




    ///recoge datos calendario
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        String m_v;
        String d_v;
        String a_v;

        m_v = valueOf(monthOfYear + 1);
        d_v = valueOf(dayOfMonth);
        a_v=valueOf(year);

        if (m_v.length() == 1) {
            m_v = "0" + m_v;
        }

        if (d_v.length() == 1) {
            d_v = "0" + d_v;
        }

        Fecha_Nacimiento.setText(valueOf(year)+ "/"+ m_v + "/" + d_v  );

        a_v=a_v.substring(2,4);

        preFecha_clave = a_v +  m_v  + d_v;



    }




    public void save() {


        fecha_nacimiento_F=Fecha_Nacimiento.getText().toString();
        Apell_P_F=Apell_P.getText().toString();
        Apell_M_F=Apell_M.getText().toString();
        nombre_F=nombre.getText().toString();

        if (RB_sexo_H.isChecked() ) {
            sexo_F=RB_sexo_H.getText().toString();
        }

        if (RB_sexo_M.isChecked() ) {
            sexo_F=RB_sexo_M.getText().toString();
        }

        edad_F=edad.getText().toString();
        profecion_F=profecion.getText().toString();

        if (RB_niv_esc_primaria.isChecked() ) {
            niv_escol_F=RB_niv_esc_primaria.getText().toString();
        }
        if (RB_niv_esc_secundaria.isChecked() ) {
            niv_escol_F=RB_niv_esc_secundaria.getText().toString();
        }
        if (RB_niv_esc_preparatoria.isChecked() ) {
            niv_escol_F=RB_niv_esc_preparatoria.getText().toString();
        }
        if (RB_niv_esc_licenciatura.isChecked() ) {
            niv_escol_F=RB_niv_esc_licenciatura.getText().toString();
        }



        calle_F=calle.getText().toString();
        numero_F=numero.getText().toString();
        colonia_F=colonia.getText().toString();
        ciudad_f=ciudad.getText().toString();
        municipio_F=municipio.getText().toString();
        estado_F=estado.getText().toString();


        email_F=email.getText().toString();
        telefono_F=telefono.getText().toString();
        whatsapp_F=whatsapp.getText().toString();
        face_F=face.getText().toString();
        observ_F=observ.getText().toString();



        if (RB_acepto.isChecked() ) {
            acepto_F=RB_acepto.getText().toString();
        }








        ///////////////////////valida datos



        if (fecha_nacimiento_F.equals("Fecha de Nacimiento")) {

            Context context = getApplicationContext();
            CharSequence text = "Favor seleccionar fecha de nacimiento";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;


        }

        if (Apell_P_F.equals("") || Apell_M_F.equals("") || nombre_F.equals("") || calle_F.equals("") || numero_F.equals("") || colonia_F.equals("")  ) {
            Context context = getApplicationContext();
            CharSequence text = "Favor documenta todos los campos";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        if (estado_F.equals("") || municipio_F.equals("") ||  sexo_F.equals("") || telefono_F.equals("") || niv_escol_F.equals("") || edad_F.equals("") || profecion_F.equals("")|| email_F.equals("") || fecha_nacimiento_F.equals("") ) {
            Context context = getApplicationContext();
            CharSequence text = "Favor documenta todos los campos";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }





        if (flag_foto ==0) {

            urlFoto="";
            Context context = getApplicationContext();
            CharSequence text = "Por favor toma la fotografía antes de guardar el formulario";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }


        if (acepto_F.equals("") ) {
            Context context = getApplicationContext();
            CharSequence text = "Por favor acepta el Aviso de Uso de Información Personal";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }


        Apell_M_F= removerTildes(Apell_M_F);
        Apell_P_F=removerTildes(Apell_P_F);
        nombre_F= removerTildes(nombre_F);


        Apell_M_F= Apell_M_F.toUpperCase();
        Apell_P_F=Apell_P_F.toUpperCase();
        nombre_F= nombre_F.toUpperCase();





        //primera letra
        String c_ap = Apell_P_F;
        int len_c_ap = c_ap.length();
        String a=c_ap.substring(0,1);


        //primera vocal
        String bb="";
        String b="";

        int i= 1;

        int flag_ap= 0;


        while(flag_ap == 0)
        {
            bb= c_ap.substring(i,len_c_ap);
            b=bb.substring(0,1);



            if (b.equals("A") || b.equals("E") || b.equals("I") || b.equals("O") || b.equals("U") ){
                flag_ap =0;
                i=i+1;
            }else{
                flag_ap =1;

            }

        }



        int flag_am= 0;
        String c_am = Apell_M_F;
        int len_c_am = c_am.length();
        String c=c_am.substring(0,1);

        //primera vocal
        String dd="";
        String d="";
        i= 1;
        while(flag_am == 0)
        {
            dd= c_am.substring(i,len_c_am);
            d=dd.substring(0,1);

            if (d.equals("A") || d.equals("E") ||d.equals("I") || d.equals("O") || d.equals("U") ){
                flag_am =0;
                i=i+1;
            }else{
                flag_am =1;

            }

        }


        int flag_n= 0;
        String c_n = nombre_F;
        int len_c_n = c_n.length();
        String e=c_n.substring(0,1);

        //primera vocal
        String ff="";
        String f="";
        i= 1;
        while(flag_n == 0)
        {
            ff= c_n.substring(i,len_c_n);
            f=ff.substring(0,1);

            if (f.equals("A") || f.equals("E") ||f.equals("I") || f.equals("O") || f.equals("U") ){
                flag_n =0;
                i=i+1;
            }else{
                flag_n =1;

            }

        }

        clave_F=sexo_F+(a  + b + c + d + e+ f + preFecha_clave);


        /////sube foto


        progressBarPhoto.setVisibility(View.VISIBLE);
        final String idImage = "CT_" + clave_F;

        mStorageRef = FirebaseStorage.getInstance().getReference().child("CT1/" + idImage);;
        // storageReference = FirebaseStorage.getStorageReference().child("image/" + idImage);



        UploadTask uploadTask = mStorageRef.putBytes(imageBytes);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                HashMap<String, Object> uriChildren = new HashMap<String, Object>();
                uriChildren.put("photo", idImage);

                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                urlFoto=downloadUrl.toString();

                url_photo.setText(urlFoto);

                urlFoto_F=urlFoto;
                sube_datos();



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }


    static String removerTildes(String cadena) {
        return cadena.replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U")
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u");
    }




    public void sube_datos() {
        //sube datos a database




        writeNewPost();







    }


    private ValueEventListener eventListener;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();




    private void writeNewPost() {
        ///// TODO DATABASE FIREBASE
        Post post = new Post();
        Map<String, Object> postValues = post.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(clave_F, postValues);
        database.child("registros").child(estado_F+"/"+usuario).updateChildren(childUpdates);


        ///// TODO CLUDFIRE FIREBASE

        db.collection(estado_F).document(usuario).collection("registros").document(clave_F)
                .set(postValues)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBarPhoto.setVisibility(View.GONE);
                        Context context = getApplicationContext();
                        CharSequence text = "Formulario cargado";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBarPhoto.setVisibility(View.GONE);
                        Context context = getApplicationContext();
                        CharSequence text = "error "+e;
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });
        //Map<String, Object> result = new HashMap<>();
        //result.put("clave",clave_F);






        finish();
        //writeNewTrack();


    }




    @IgnoreExtraProperties
    public class Post {



        public Post() {


        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();

            result.put("clave",clave_F);
            result.put("fecha_nac",fecha_nacimiento_F);
            result.put("nombre",nombre_F);
            result.put("apell_p",Apell_P_F);
            result.put("apell_m",Apell_M_F);
            result.put("sexo",sexo_F);
            result.put("edad",edad_F);
            result.put("profecion",profecion_F);
            result.put("nivel_esc",niv_escol_F);
            result.put("calle",calle_F);
            result.put("numero",numero_F);
            result.put("colonia",colonia_F);
            result.put("ciudad",ciudad_f);
            result.put("municipio",municipio_F);
            result.put("estado",estado_F);
            result.put("email",email_F);
            result.put("telefono",telefono_F);
            result.put("whatsapp",whatsapp_F);
            result.put("face",face_F);
            result.put("observ",observ_F);
            result.put("urlFoto",urlFoto_F);
            result.put("fecha_captura",FechaNow);
            result.put("ubicacion", mLatitude.getText().toString()+","+mLongitude.getText().toString());
            result.put("acepta_uso_de_datos_personales",acepto_F);
            result.put("user",usuario);
            result.put("validado","No");



            return result;
        }





    }



    @Override
    public void onBackPressed() {

        muestraDialogo1();
    }





    public void muestraDialogo1() {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Salir del formulario?");
        myBuild.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });


        myBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        AlertDialog dialog = myBuild.create();
        dialog.show();


    }

    public void muestraDialogoSave(View view) {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Importante");
        myBuild.setMessage("¿Guardar Captura de datos?");
        myBuild.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                save();

            }
        });


        myBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        AlertDialog dialog = myBuild.create();
        dialog.show();


    }


//TODO   agregar wathsapp  y sector a 3 digitos  (obligatorio)




}
