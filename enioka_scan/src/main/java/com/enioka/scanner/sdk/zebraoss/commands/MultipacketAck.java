package com.enioka.scanner.sdk.zebraoss.commands;

import com.enioka.scanner.sdk.zebraoss.ssi.SsiCommand;

/**
 * A multipacket segment acknowledgment.
 */
public class MultipacketAck extends CommandExpectingNothing {
    public MultipacketAck() {
        super(SsiCommand.MULTIPACKET_ACK.getOpCode());
    }
}
