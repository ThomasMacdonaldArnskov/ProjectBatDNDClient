package net;

import commons.pipeline.ChannelHandler;
import commons.transfer.TransferableObject;
import commons.transfer.Transferables;
import commons.transfer.objects.FiducialTransfer;
import commons.transfer.objects.ScreenTransfer;
import commons.transfer.objects.SimpleTransfer;
import game.BatClient;
import game.characters.CharacterSheet;
import io.netty.channel.ChannelHandlerContext;

import java.awt.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
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
                        boolean notHere = true;
                        for (FiducialTransfer ft : fiducials) {
                            if (ft.isEqual((FiducialTransfer) transferable)) {
                                ft.update((FiducialTransfer) transferable);
                                notHere = false;
                            }
                        }
                        if (notHere) {
                            add.add((FiducialTransfer) transferable);
                        }
                        System.out.println(transferable);
                    }
                }
            }
        }
        fiducials.addAll(add.stream().collect(Collectors.toList()));
        add.clear();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        super.exceptionCaught(context, cause);
    }

}
