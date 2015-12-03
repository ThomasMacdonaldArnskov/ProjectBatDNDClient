package net;

import commons.pipeline.ChannelHandler;
import commons.transfer.TransferableObject;
import commons.transfer.Transferables;
import commons.transfer.objects.FiducialTransfer;
import commons.transfer.objects.ScreenTransfer;
import commons.transfer.objects.SimpleTransfer;
import io.netty.channel.ChannelHandlerContext;

public class ClientChannelHandler extends ChannelHandler {

    @Override
    public void channelActive(ChannelHandlerContext context) {
        super.channelActive(context);
        getConnection().send(new SimpleTransfer(Transferables.JOIN));
        getConnection().send(new ScreenTransfer(800, 600));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object received) throws Exception {
        if (received instanceof TransferableObject) {
            TransferableObject transferable = (TransferableObject) received;
            for (Transferables transfer : Transferables.values()) {
                if (transfer == transferable.getTransfer()) {
                    if (transferable instanceof FiducialTransfer) {
                        System.out.println(transferable);
                    }
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        super.exceptionCaught(context, cause);
    }

}
