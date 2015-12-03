package commons.transfer.objects;

import com.sun.istack.internal.NotNull;
import commons.transfer.TransferableObject;
import commons.transfer.Transferables;

import java.awt.*;

public class BlobTransfer extends TransferableObject {

    private final Long id;
    private Boolean active;
    private Point position;

    public BlobTransfer(@NotNull Long id, @NotNull Boolean active, @NotNull Point position) {
        super(Transferables.PING_BLOB);
        this.id = id;
        this.active = active;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public Boolean isActive() {
        return active;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isSame(long id) {
        return this.id == id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + (active ? "Active" : "InActive");
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
