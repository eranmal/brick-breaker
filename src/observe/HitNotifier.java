package observe;

/**
 * The {@code HitNotifier} interface should be implemented by any object that can be hit and needs
 * to notify registered listeners about hit events.
 * It allows listeners to register and unregister themselves to receive notifications of hits.
 */
public interface HitNotifier {

    /**
     * Adds a {@link HitListener} to the list of listeners that will be notified of hit events.
     *
     * @param hl the hit listener to add
     */
    void addHitListener(HitListener hl);

    /**
     * Removes a {@link HitListener} from the list of listeners that are notified of hit events.
     *
     * @param hl the hit listener to remove
     */
    void removeHitListener(HitListener hl);
}
