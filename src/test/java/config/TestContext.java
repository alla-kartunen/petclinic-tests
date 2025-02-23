package config;

import objects.Owner;

import java.util.ArrayList;

public class TestContext {

    private static final ThreadLocal<Integer> ownerToClear = new ThreadLocal<>();
    private static final ThreadLocal<Integer> petToClear = new ThreadLocal<>();
    private static final ThreadLocal<ArrayList<Owner>> ownerListToClear = new ThreadLocal<>();

    /**
     * Set test context variables
     */

    public static void setOwnerToClear(int ownerId) {
        ownerToClear.set(ownerId);
    }

    public static void setPetToClear(int petId) {
        petToClear.set(petId);
    }

    public static void setOwnerListToClear(ArrayList<Owner> ownersList) {
        ownerListToClear.set(ownersList);
    }

    /**
     * Get test context variables
     */

    public static int getOwnerToClear() {
        if (ownerToClear.get() != null) {
            return ownerToClear.get();
        }
        else return 0;
    }

    public static int getPetToClear() {
        if (petToClear.get() != null) {
            return petToClear.get();
        }
        else return 0;
    }

    public static ArrayList<Owner> getOwnerListToClear() {
        return ownerListToClear.get();
    }

    public static boolean isOwnerListEmpty() {
        return ownerListToClear.get() == null;
    }

    /**
     * Clear test context variables
     */

    public static void clearOwnerId() {
        ownerToClear.remove();
    }

    public static void clearPetId() {
        petToClear.remove();
    }

    public static void clearListOfOwnerId() {
        ownerListToClear.remove();
    }
}
