package commons.pipeline;

import commons.transfer.TransferableObject;
import commons.transfer.Transferables;
import io.netty.channel.ChannelHandlerContext;

public class Connection {

    private final ChannelHandlerContext context;

    public Connection(ChannelHandlerContext context) {
        this.context = context;
    }

    public void send(TransferableObject transferable) {
        if (context == null || transferable == null) {
            System.out.println("Context: " + context);
            return;
        }
        for (Transferables transfer : Transferables.values()) {
            if (transferable.getTransfer() == transfer) {
                context.writeAndFlush(transferable);
                return;
            }
        }
        dispose();
    }

    public void dispose() {
        if (context != null) {
            context.disconnect();
            context.close();
        }
    }

    public ChannelHandlerContext getContext() {
        return context;
    }

}

