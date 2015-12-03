package net;

import commons.pipeline.ChannelHandler;
import commons.transfer.TransferableObject;
import commons.transfer.Transferables;
import commons.transfer.objects.FiducialTransfer;
import commons.transfer.objects.ScreenTransfer;
import commons.transfer.objects.SimpleTransfer;
import game.BatClient;
import game.characters.Character;
import io.netty.channel.ChannelHandlerContext;

import java.awt.*;

public class ClientChannelHandler extends ChannelHandler {

    public static Character character = Character.generateCharacterFromFiducial(new FiducialTransfer(284l, true, new Point(0, 0)));

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
                        if (character.getFiducial().isSame(((FiducialTransfer) transferable).getId())) {
                            character.getFiducial().update((FiducialTransfer) transferable);
                        }
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
