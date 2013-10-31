package com.zishao.adapter;

import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zishao.bletest.R;

/**
 * 列表适配器
 * 
 * @author 紫韶
 * @date Oct 30, 2013
 */
public class LeDeviceListAdapter extends BaseAdapter {
    private List<BluetoothDevice> data;
    private Activity context;

    public LeDeviceListAdapter(Activity context, List<BluetoothDevice> data) {
        this.data = data;
        this.context = context;
    }

    public synchronized void addDevice(BluetoothDevice device) {
        data.add(device);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.leaf_devices_list_item, null);
            convertView.setTag(new DeviceView(convertView));
        }
        DeviceView view = (DeviceView) convertView.getTag();
        view.init((BluetoothDevice) getItem(position));
        return convertView;
    }

    public class DeviceView {
        private TextView title;
        private TextView status;
        private TextView type;
        private TextView address;

        public DeviceView(View view) {
            title = (TextView) view.findViewById(R.id.device_name);
            status = (TextView) view.findViewById(R.id.device_status_txt);
            type = (TextView) view.findViewById(R.id.device_type_txt);
            address = (TextView) view.findViewById(R.id.device_address_txt);
        }

        public void init(BluetoothDevice device) {
            title.setText(device.getName());
            address.setText(device.getAddress());
            setType(device.getType());
            setStatus(device.getBondState());
        }
        
        public void setType(int status) {
            switch(status) {
            case BluetoothDevice.DEVICE_TYPE_CLASSIC:
                type.setText("典型蓝牙");
                break;
            case BluetoothDevice.DEVICE_TYPE_LE:
                type.setText("BLE设备");
                break;
            case BluetoothDevice.DEVICE_TYPE_DUAL:
                type.setText("Dual Mode - BR/EDR/LE");
                break;
            case BluetoothDevice.DEVICE_TYPE_UNKNOWN:
                type.setText("不知名设备");
                break;
            }
        }
        
        public void setStatus(int s) {
            switch(s) {
            case BluetoothDevice.BOND_NONE:
                status.setText("没有配对");
                break;
            case BluetoothDevice.BOND_BONDED:
                status.setText("已经配对");
                break;
            case BluetoothDevice.BOND_BONDING:
                status.setText("正在配对");
                break;
            }
        }
    }
}
