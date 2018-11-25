package jaballogian.bluetoothtest;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;

import me.anwarshahriar.calligrapher.Calligrapher;


public class ledControl extends AppCompatActivity {

//    // Button btnOn, btnOff, btnDis;
//    ImageButton On, Off, Discnt, Abt;

    ImageButton leftBody, rightBody, leftShoulder, rightShoulder, leftElbow, rightElbow, leftFingers, rightFingers, disconnect;

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //view of the ledControl
        setContentView(R.layout.activity_led_control);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "PRODUCT_SANS.ttf", true);

//        //call the widgets
//        On = (ImageButton)findViewById(R.id.on);
//        Off = (ImageButton)findViewById(R.id.off);
//        Discnt = (ImageButton)findViewById(R.id.discnt);
//        Abt = (ImageButton)findViewById(R.id.abt);

        leftBody = (ImageButton) findViewById(R.id.leftBodyButtonMainActivity);
        rightBody = (ImageButton) findViewById(R.id.rightBodyButtonMainActivity);
        leftShoulder = (ImageButton) findViewById(R.id.leftShoulderButtonMainActivity);
        rightShoulder = (ImageButton) findViewById(R.id.rightShoulderButtonMainActivity);
        leftElbow = (ImageButton) findViewById(R.id.leftElbowButtonMainActivity);
        rightElbow = (ImageButton) findViewById(R.id.rightElbowButtonMainActivity);
        leftFingers = (ImageButton) findViewById(R.id.leftFingersButtonMainActivity);
        rightFingers = (ImageButton) findViewById(R.id.rightFingersButtonMainActivity);
        disconnect = (ImageButton) findViewById(R.id.disconnectButton);

        new ConnectBT().execute(); //Call the class to connect

//        //commands to be sent to bluetooth
//        On.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                turnOnLed();      //method to turn on
//            }
//        });
//
//        Off.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                turnOffLed();   //method to turn off
//            }
//        });
//
//        Discnt.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Disconnect(); //close connection
//            }
//        });

        leftBody.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rotateBodyToLeft();
            }
        });

        rightBody.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rotateBodyToRight();
            }
        });

//        stop.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                stopInstruction();
//            }
//        });

        leftShoulder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rotateShoulderToLeft();
            }
        });

        rightShoulder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rotateShoulderToRIght();
            }
        });

        leftElbow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rotateElbowToLeft();
            }
        });

        rightElbow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rotateElbowToRight();
            }
        });

        leftFingers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rotateFingersToLeft();
            }
        });

        rightFingers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rotateFingersToRight();
            }
        });

        disconnect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                disconnectInstruction();
            }
        });

    }

//    private void Disconnect()
//    {
//        if (btSocket!=null) //If the btSocket is busy
//        {
//            try
//            {
//                btSocket.close(); //close connection
//            }
//            catch (IOException e)
//            { msg("Error");}
//        }
//        finish(); //return to the first layout
//
//    }
//
//    private void turnOffLed()
//    {
//        if (btSocket!=null)
//        {
//            try
//            {
//                btSocket.getOutputStream().write("0".toString().getBytes());
//            }
//            catch (IOException e)
//            {
//                msg("Error");
//            }
//        }
//    }
//
//    private void turnOnLed()
//    {
//        if (btSocket!=null)
//        {
//            try
//            {
//                btSocket.getOutputStream().write("1".toString().getBytes());
//            }
//            catch (IOException e)
//            {
//                msg("Error");
//            }
//        }
//    }

    private void rotateBodyToLeft()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("1".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void rotateShoulderToRIght()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("4".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void stopInstruction()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("9".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void rotateShoulderToLeft()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("3".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void rotateBodyToRight()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("2".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void rotateElbowToLeft()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("5".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void rotateElbowToRight()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("6".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void rotateFingersToLeft()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("7".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void rotateFingersToRight()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("8".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void disconnectInstruction()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

//    public  void about(View v)
//    {
//        if(v.getId() == R.id.abt)
//        {
//            Intent i = new Intent(this, AboutActivity.class);
//            startActivity(i);
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_led_control, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            //progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            //progress.dismiss();
        }
    }
}
