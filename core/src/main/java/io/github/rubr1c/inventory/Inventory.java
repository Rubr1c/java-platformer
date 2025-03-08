package io.github.rubr1c.inventory;


import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private int slotCount;
    private Map<Item, Integer> items;

    public Inventory(int slotCount) {
        items = new HashMap<>(slotCount);
        this.slotCount = slotCount;
    }

    public boolean addItem(Item item) {
        for (int slotIndex = 0; slotIndex < slotCount; slotIndex++) {
            final int currentSlot = slotIndex;
            boolean slotTaken = items.keySet().stream()
                .anyMatch(existingItem -> existingItem.getSlot() == currentSlot);
            if (!slotTaken) {
                item.setSlot(currentSlot);
                items.compute(item, (k, currentCount) -> (currentCount != null ? currentCount + 1 : 1));
                return true;
            }
        }
        return false;
    }

     public boolean addItem(Item item, int count) {
         // If item already exists in inventory, just update the count
         if (items.containsKey(item)) {
             items.compute(item, (k, currentCount) -> currentCount + count);
             return true;
         }
         // Otherwise, find an empty slot for the new item
         for (int slotIndex = 0; slotIndex < slotCount; slotIndex++) {
             final int currentSlot = slotIndex;
             boolean slotTaken = items.keySet().stream()
                 .anyMatch(existingItem -> existingItem.getSlot() == currentSlot);
             if (!slotTaken) {
                 item.setSlot(currentSlot);
                 items.put(item, count);
                 return true;
             }
         }
         return false;
     }

     public void removeItem(Item item, int count) {
        items.computeIfPresent(item, (key, value) -> {
            int newCount = value - count;
            if (newCount <= 0) {
                // Clear the slot when removing all items
                item.setSlot(-1);
                return null;
            }
            return newCount;
        });
     }

     public void removeItem(Item item) {
        if (items.remove(item) != null) {
            // Clear the slot when removing the item
            item.setSlot(-1);
        }
     }

    public int getSlotCount() {
        return slotCount;
    }

    public void setSlotCount(int slotCount) {
        this.slotCount = slotCount;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }
}
