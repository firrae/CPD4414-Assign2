/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cpd4414.assign2;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

/**
 *
 * @author Steven Lambe <firrae@gmail.com>
 */
public class OrderQueue {
    Queue<Order> orderQueue = new ArrayDeque<>();
    
    public void add(Order order) {
        if(order.getCustomerId() == "" && order.getCustomerName() == "")
        {
            throw new IllegalStateException("Customer must have a name and an ID.");
        }
        else
        {
            if(order.getListOfPurchases().isEmpty())
            {
                throw new IllegalStateException("An order must have a list of purchases.");
            }
            else
            {
                order.setTimeReceived(new Date());
                orderQueue.add(order);
            }
        }
    }
    
    public Order nextOrder() {
        if(orderQueue.isEmpty())
        {
            return null;
        }
        else
        {
            return orderQueue.peek();
        }
    }
    
    public void processOrder(Order order) {
        boolean inStock = true;
        
        if(order.getTimeReceived() != null)
        {
            for(int i = 0; i < order.getListOfPurchases().size(); i++)
            {
                inStock = checkStock(order.getListOfPurchases().get(i).getQuantity(), order.getListOfPurchases().get(i).getProductId());
            }
            
            if(inStock)
            {
                order.setTimeProcessed(new Date());
            }
        }
        else
        {
            throw new IllegalStateException("An order must have a time recieved to be processed.");
        }
    }
    
    public void fulfillOrder(Order order) {
        boolean inStock = true;
        
        if(order.getTimeReceived() != null)
        {
            if(order.getTimeProcessed() != null)
            {
                for(int i = 0; i < order.getListOfPurchases().size(); i++)
                {
                    inStock = checkStock(order.getListOfPurchases().get(i).getQuantity(), order.getListOfPurchases().get(i).getProductId());
                }

                if(inStock)
                {
                    order.setTimeFulfilled(new Date());
                }
            }
            else
            {
                throw new IllegalStateException("An order must have a time processed to be fulfilled.");
            }
        }
        else
        {
            throw new IllegalStateException("An order must have a time recieved to be fulfilled.");
        }
    }
    
    /**
     * Utility function to determine if an item in in stock according to the ordered amount.
     * @param orderedAmount the amount of the item ordered
     * @param itemID the ID of the ordered item
     * @return true if the item is in stock, false if it is not.
     */
    public boolean checkStock(int orderedAmount, int itemID) {
        boolean inStock = true;
        
        if(orderedAmount > Inventory.getQuantityForId(itemID))
        {
            inStock = false;
        }
        
        return inStock;
    }
}
