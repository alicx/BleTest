package com.zishao.bletest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 测试扫描蓝牙设备
 * 
 * @author 紫韶
 * @date Oct 30, 2013
 */
public class ScanNomoreActivity extends ScanBaseActivity {

    /* (non-Javadoc)
     * @see com.zishao.bletest.ScanBaseActivity#initScanBluetooth()
     */
    protected void initScanBluetooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "不支持普通蓝牙", Toast.LENGTH_LONG).show();
            return;
        }
        // Register the BroadcastReceiver no ble
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        BluetoothDevice device = (BluetoothDevice) mLeDeviceListAdapter.getItem(position);
        device.getUuids();
    }

    // 普通的蓝牙搜索
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /*
         * (non-Javadoc)
         * 
         * @see
         * android.content.BroadcastReceiver#onReceive(android.content.Context,
         * android.content.Intent)
         */
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device =
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                addDevice(device);
            }
        }
    };
}
