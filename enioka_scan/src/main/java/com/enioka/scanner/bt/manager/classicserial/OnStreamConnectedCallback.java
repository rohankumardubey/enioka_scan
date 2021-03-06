package com.enioka.scanner.bt.manager.classicserial;

import android.bluetooth.BluetoothSocket;

/**
 * Called when a stream has failed or succeeded to connect.
 */
interface OnStreamConnectedCallback {
    void connected(BluetoothSocket bluetoothSocket);

    void failed();
}