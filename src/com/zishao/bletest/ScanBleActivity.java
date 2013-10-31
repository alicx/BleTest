package com.zishao.bletest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

/**
 * 测试扫描蓝牙设备
 * 
 * @author 紫韶
 * @date Oct 30, 2013
 */
public class ScanBleActivity extends ScanBaseActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler = new Handler();

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    /* (non-Javadoc)
     * @see com.zishao.bletest.ScanBaseActivity#initScanBluetooth()
     */
    protected void initScanBluetooth() {
        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = manager.getAdapter();
        startScanLen(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mScanning) {
            startScanLen(false);
        }
    }

    /**
     * 开始扫描
     * @param enable
     */
    private void startScanLen(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
        new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                addDevice(device);
            }
        };

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        
    }
}
