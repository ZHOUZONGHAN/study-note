package design.snapshot;

import java.util.Stack;

/**
 * @author zhouzonghan
 */
public class SnapshotHolder {
    private final Stack<Snapshot> snapshots = new Stack<>();

    public Snapshot popSnapshot() {
        return snapshots.pop();
    }

    public void pushSnapshot(Snapshot snapshot) {
        snapshots.push(snapshot);
    }
}