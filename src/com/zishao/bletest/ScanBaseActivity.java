package com.zishao.bletest;

import java.util.ArrayList;

import com.zishao.adapter.LeDeviceListAdapter;

import android.app.ListActivity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 基类
 * @author 紫韶 
 * @date Oct 31, 2013
 */
abstract public class ScanBaseActivity extends ListActivity {

    protected LeDeviceListAdapter mLeDeviceListAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_scan);
        mLeDeviceListAdapter = new LeDeviceListAdapter(this, new ArrayList<BluetoothDevice>());
        this.setListAdapter(mLeDeviceListAdapter);
        initScanBluetooth();
    }

    /**
     * 初始化蓝牙扫描
     * 
     */
    abstract protected void initScanBluetooth();
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        BluetoothDevice device = (BluetoothDevice) mLeDeviceListAdapter.getItem(position);
        ParcelUuid[] uuids = device.getUuids();
        String uuidString = "设备：" + device.getName() + ";UUID:";
        if (null != uuids && uuids.length > 0) {
            uuidString += uuids[0].getUuid().toString();
        } else {
            uuidString += "empty";
        }
        Toast.makeText(this, uuidString, Toast.LENGTH_LONG).show();
    }
    
    /**
     * 扫描到设备添加
     * @param device
     */
    protected synchronized void addDevice(final BluetoothDevice device) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLeDeviceListAdapter.addDevice(device);
                mLeDeviceListAdapter.notifyDataSetChanged();
            }
        });
    }
}
