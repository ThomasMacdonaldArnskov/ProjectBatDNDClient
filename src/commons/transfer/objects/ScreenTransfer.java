package commons.transfer.objects;

import com.sun.istack.internal.NotNull;
import commons.transfer.TransferableObject;
import commons.transfer.Transferables;

public class ScreenTransfer extends TransferableObject {
    private final Integer width;
    private final Integer height;

    public ScreenTransfer(@NotNull Integer width, @NotNull Integer height) {
        super(Transferables.IMAGE_SIZE);
        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
