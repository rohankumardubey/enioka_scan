package com.enioka.scanner.sdk.zebraoss.parsers;

import com.enioka.scanner.sdk.zebraoss.data.ScannerInit;

/**
 * Responsible for handling ACK data
 */
public class ScannerInitParser implements PayloadParser<ScannerInit> {
    @Override
    public ScannerInit parseData(final byte[] dataBuffer) {
        return new ScannerInit();
    }
}
