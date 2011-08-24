/*******************************************************************************
 * Copyright (C) 2011 by Ethan Hall
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/

package com.kopysoft.wifiselector.Adapter;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.zip.Inflater;

public class WifiAdapter extends BaseAdapter {

    List<WifiConfiguration> gWifiList;
    Context gContext;

    public WifiAdapter(List<WifiConfiguration> wifiList, Context context){

        gWifiList = wifiList;
        gContext = context;

    }
    public int getCount() {
        return gWifiList.size();
    }

    public WifiConfiguration getItem(int i) {
        return gWifiList.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        Row curRow;
        if(view == null)
            curRow = new Row(gContext);
        else
            curRow = (Row)view;

        String SSID = gWifiList.get(i).SSID;
        SSID = SSID.replace("\"", "");
        curRow.WifiNetwork().setText(SSID);
        return curRow;
    }
}
