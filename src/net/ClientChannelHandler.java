package net;

import commons.pipeline.ChannelHandler;
import commons.transfer.TransferableObject;
import commons.transfer.Transferables;
import commons.transfer.objects.BlobTransfer;
import commons.transfer.objects.FiducialTransfer;
import commons.transfer.objects.ScreenTransfer;
import commons.transfer.objects.SimpleTransfer;
import game.BatClient;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClientChannelHandler extends ChannelHandler {

    public static ArrayList<FiducialTransfer> fiducials = new ArrayList<>();
    public static ArrayList<FiducialTransfer> add = new ArrayList<>();

    @Override
    public void channelActive(ChannelHandlerContext context) {
        super.channelActive(context);
        getConnection().send(new SimpleTransfer(Transferables.JOIN));
        getConnection().send(new ScreenTransfer(BatClient.WIDTH, BatClient.HEIGHT));
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
                        BatClient.batClient.pingFiducial(((FiducialTransfer) transferable));
                        boolean notHere = true;
                        for (FiducialTransfer ft : fiducials) {
                            if (ft.isSame(((FiducialTransfer) transferable).getId())) {
                                ft.update((FiducialTransfer) transferable);
                                System.out.println(((FiducialTransfer) transferable).getPosition());
                                notHere = false;
                            }
                        }
                        if (notHere) {
                            add.add((FiducialTransfer) transferable);
                        }
                        fiducials.addAll(add.stream().collect(Collectors.toList()));
                        add.clear();
                        return;
                    }
                    if (transferable instanceof BlobTransfer) {
                        if (BatClient.batClient != null) {
                            BatClient.batClient.pingBlob(((BlobTransfer) transferable));
                            return;
                        }
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
