package com.enioka.scanner.sdk.zebraoss.commands;

import com.enioka.scanner.bt.api.BluetoothScanner;
import com.enioka.scanner.bt.api.Command;
import com.enioka.scanner.sdk.zebraoss.data.RsmAttributeReply;
import com.enioka.scanner.sdk.zebraoss.ssi.SsiCommand;
import com.enioka.scanner.sdk.zebraoss.ssi.SsiMonoPacketWrapper;
import com.enioka.scanner.sdk.zebraoss.ssi.SsiStatus;

/**
 * Special management command which should be sent before all others, requesting the RSM buffer size.
 */
public class ManagementCommandGetBufferSize implements Command<RsmAttributeReply> {
    private final SsiMonoPacketWrapper packet;

    public ManagementCommandGetBufferSize() {
        packet = new SsiMonoPacketWrapper(SsiCommand.SSI_MGMT_COMMAND.getOpCode(), SsiStatus.DEFAULT.getByte(),new byte[]{0x00, 0x06, 0x20, 0x00, (byte) 0xFF, (byte) 0xFF});
    }

    @Override
    public byte[] getCommand() {
        return packet.toCommandBuffer(false);
    }

    @Override
    public byte[] getCommand(final BluetoothScanner bluetoothScanner) {
        return packet.toCommandBuffer(bluetoothScanner.isBleDevice());
    }

    @Override
    public Class<? extends RsmAttributeReply> getReturnType() {
        return RsmAttributeReply.class;
    }

    @Override
    public int getTimeOut() {
        return 1000;
    }
}
