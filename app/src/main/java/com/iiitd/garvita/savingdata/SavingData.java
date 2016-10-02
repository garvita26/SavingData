package com.iiitd.garvita.savingdata;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SavingData extends AppCompatActivity {
    private EditText enterUsername;
    private EditText enterPassword;
    private TextView displayInfo;
    private Button  mreadData;
    private Button mwriteDB;
    private Button mreadDB;
    private Button mdeleteDB;
    private Button mupdateDB;
    private Button msaveext;
    private Button mreadext;
    DBHandler dbhandler;
    private static final String TAG = "SavedataActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_data);
        dbhandler= new DBHandler(this,null,null,1);
        enterUsername =(EditText)findViewById(R.id.editText);
        enterPassword =(EditText)findViewById(R.id.editText2);
        displayInfo = (TextView)findViewById(R.id.textView3);
        mreadData = (Button)findViewById(R.id.button2);
        mwriteDB= (Button)findViewById(R.id.button7);
        mreadDB= (Button)findViewById(R.id.button9);
        mdeleteDB= (Button)findViewById(R.id.button10);
        mupdateDB= (Button)findViewById(R.id.button8);
        msaveext= (Button)findViewById(R.id.button5);
        mreadext=(Button)findViewById(R.id.button6);
        mreadData.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View v) {

                       readSharedPreference();

                    }
                }
        );
        mwriteDB.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View v) {

                        writeDatabase(v);

                    }
                }
        );
        mreadDB.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View v) {

                        readDatabase();

                    }
                }
        );
        mdeleteDB.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View v) {

                        deleteDatabase(v);

                    }
                }
        );
        mupdateDB.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View v) {

                        updateDatabase(v);

                    }
                }
        );
        msaveext.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View v) {
                        savePExtAppStorage(v);
                     savePuExtAppStorage();
                       saveExternalStorage(v);

                    }
                }
        );
        mreadext.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View v) {
                        readExternalStorage(v);
                        readPExternalStorage(v);

                    }
                }
        );
    }

 // saving data using shared preference
    public void saveSharedPreference(View view)
    {
        SharedPreferences spreference = getSharedPreferences("userinfo", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = spreference.edit();
        editor.putString("username",enterUsername.getText().toString());
        editor.putString("password",enterPassword.getText().toString());
        editor.apply();
        Toast.makeText(this,"Saved!",Toast.LENGTH_SHORT).show();

    }
// reading data from shared preference
    public void readSharedPreference()
    {
        SharedPreferences spreference = getSharedPreferences("userinfo", getApplicationContext().MODE_PRIVATE);
        String name = spreference.getString("username","");
        String psswd = spreference.getString("password","");
        displayInfo.setText(name + " " + psswd);

    }
    //saving data in internal storage
    public void saveInternalStorage(View view)
    {
      String uname = enterUsername.getText().toString();
      String tab = "  ";
      String password = enterPassword.getText().toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput("mystorage.txt",MODE_PRIVATE);
            fileOutputStream.write(uname.getBytes());
            fileOutputStream.write(tab.getBytes());
            fileOutputStream.write(password.getBytes());
            fileOutputStream.close();
            enterUsername.setText("");
            enterPassword.setText("");
            Toast.makeText(this,"Saved!",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //reading data from internal storage
    public void readInternalStorage(View view)
    {
        try {
            FileInputStream fileInputStream =  openFileInput("mystorage.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String data;
            while((data=bufferedReader.readLine())!=null)
            {
                stringBuffer.append(data+"\n");
            }
            displayInfo.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //function to check if external device is available for writing
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    //function to check if external device is available for reading
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    //function to save public  file in external storage
    public void saveExternalStorage(View view)
    {
        if(isExternalStorageWritable()==true)
        {
            File Root = Environment.getExternalStorageDirectory();
            File directory = new File(Root.getAbsolutePath()+"/Mystorage");
            if(!directory.exists())
            {
                directory.mkdir();
            }
            File file = new File(directory,"MyStorage.txt");
            String uname = enterUsername.getText().toString();
            String tab = "  ";
            String password = enterPassword.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(uname.getBytes());
                fileOutputStream.write(tab.getBytes());
                fileOutputStream.write(password.getBytes());
                fileOutputStream.close();
                Toast.makeText(this,"Saved to device's external storage!",Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(this,"storage not available!",Toast.LENGTH_SHORT).show();
        }
    }
    //save data to app's private external files
    public void savePExtAppStorage(View view)
    {
        if(isExternalStorageWritable()==true)
        {
            String pathToExternalStorage = (getApplicationContext().getExternalFilesDir(null)).toString();
            File file = new File(pathToExternalStorage,"demo.txt");

            Log.v("name" , String.valueOf(file)) ;

            String uname = enterUsername.getText().toString();
            String tab = "  ";
            String password = enterPassword.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(uname.getBytes());
                fileOutputStream.write(tab.getBytes());
                fileOutputStream.write(password.getBytes());
                fileOutputStream.close();
                Toast.makeText(this,"Saved to apps private external files!",Toast.LENGTH_SHORT).show();
                // saveExtAppStorage(view);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(this,"storage not available!",Toast.LENGTH_SHORT).show();
        }
    }
    //function to save data in app's public external files
    public void savePuExtAppStorage()
    {
        if(isExternalStorageWritable()==true)
        {
            String pathToExternalStorage = Environment.getExternalStorageDirectory().toString();
            File directory = new File(pathToExternalStorage + "/" + "demo2");
            if(!directory.exists())
            {
                directory.mkdir();
            }
            File file = new File(directory,"Myapp.txt");

            String uname = enterUsername.getText().toString();
            String tab = "  ";
            String password = enterPassword.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(uname.getBytes());
                fileOutputStream.write(tab.getBytes());
                fileOutputStream.write(password.getBytes());
                fileOutputStream.close();
                Toast.makeText(this,"Saved to apps public external files!",Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(this,"storage not available!",Toast.LENGTH_SHORT).show();
        }
    }
    //function to read from external storage
    public void readExternalStorage(View view)
    {
        if(isExternalStorageReadable()==true)
        {
            File Root = Environment.getExternalStorageDirectory();
            File directory = new File(Root.getAbsolutePath()+"/Mystorage");
            File file = new File(directory,"MyStorage.txt");
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                String data;
                while((data=bufferedReader.readLine())!=null)
                {
                    stringBuffer.append(data+"\n");
                }
                displayInfo.setText(stringBuffer.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(this,"file not found!",Toast.LENGTH_SHORT).show();
        }

    }
    //function to read from app's private external file
    public void readPExternalStorage(View view)
    {
        if(isExternalStorageReadable()==true)
        {
            String pathToExternalStorage = (getApplicationContext().getExternalFilesDir(null)).toString();
            File file = new File(pathToExternalStorage,"demo.txt");
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                String data;
                while((data=bufferedReader.readLine())!=null)
                {
                    stringBuffer.append(data+"\n");
                }
                displayInfo.setText(stringBuffer.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(this,"file not found!",Toast.LENGTH_SHORT).show();
        }

    }





    //add user to database
    public void writeDatabase(View view)
    {
      Database user = new Database(enterUsername.getText().toString(),enterPassword.getText().toString());
        dbhandler.addUser(user);
      //  readDatabase();
    }
  // delete user from database
    public void deleteDatabase(View view)
    {
      String deleteuser = enterUsername.getText().toString();
        dbhandler.deleteuser(deleteuser);
    }
    //update password
    public void updateDatabase(View view)
    {
        String user = enterUsername.getText().toString();
        String updatepass =enterPassword.getText().toString();
        dbhandler.updateuser(user,updatepass);
    }
    // read from database
    public void readDatabase()
    {
        String dstring = dbhandler.displaydata();
        //System.out.println(dstring);
        displayInfo.setText(dstring);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "Inside onSaveInstance");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Inside OnStart");
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Inside OnPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Inside OnResume");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Inside OnStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Inside OnDestroy");
    }
}
