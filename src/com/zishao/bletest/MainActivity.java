package com.zishao.bletest;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    private BluetoothAdapter mBluetoothAdapter;
    private final static int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager =
            (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If
        // not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            showButton(false);
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            showButton(true);
            Toast.makeText(this, R.string.ble_enable_success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ScanBleActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (mBluetoothAdapter.isEnabled()) {
                showButton(true);
                Toast.makeText(this, R.string.ble_enable_success, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ScanBleActivity.class);
                startActivity(intent);
            } else {
                showButton(false);
                Toast.makeText(this, R.string.ble_enable_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    
    /**
     * 展示
     * @param isShow
     */
    public void showButton(boolean isShow) {
        if (isShow) {
            findViewById(R.id.showBleScan).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.showBleScan).setVisibility(View.GONE);
        }
    }
    
    /**
     * 按钮跳转
     * @param view
     */
    public void toScan(View view) {
        switch (view.getId()) {
        case R.id.showBleScan:
            Intent intent = new Intent(this, ScanBleActivity.class);
            startActivity(intent);
            break;
        case R.id.showNomoreScan:
            Intent intent2 = new Intent(this, ScanNomoreActivity.class);
            startActivity(intent2);
            break;
        }
        
    }
}
