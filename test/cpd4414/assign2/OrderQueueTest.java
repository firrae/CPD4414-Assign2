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

import cpd4414.assign2.OrderQueue;
import cpd4414.assign2.Purchase;
import cpd4414.assign2.Order;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Lambe <firrae@gmail.com>
 */
public class OrderQueueTest {
    
    public OrderQueueTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testWhenCustomerExistsAndPurchasesExistThenTimeReceivedIsNow() {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(4, 450));
        order.addPurchase(new Purchase(6, 250));
        orderQueue.add(order);
        
        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    
    @Test
    public void testWhenCustomerDoesNotExistAndPurchasesDoesNotExistThenThrowError() {
        boolean win = false;
        
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("", "");
        order.addPurchase(new Purchase(4, 450));
        order.addPurchase(new Purchase(6, 250));
        try {
            orderQueue.add(order);
        }
        catch(Exception err) {
            win = true;
        }
        
        assertTrue(win);
    }
    
    @Test
    public void testWhenOrderDoesNotContainListOfPurchasesThenThrowError() {
        boolean win = false;
        
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        
        try {
            orderQueue.add(order);
        }
        catch(Exception err) {
            win = true;
        }
        
        assertTrue(win);
    }
    
    @Test
    public void testWhenAskedForNextOrderThatNextOrderIsGiven() {
        OrderQueue orderQueue = new OrderQueue();
        
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(4, 450));
        order.addPurchase(new Purchase(6, 250));
        orderQueue.add(order);
        
        Order order2 = new Order("CUST00002", "ABD Construction");
        order2.addPurchase(new Purchase(4, 450));
        order2.addPurchase(new Purchase(6, 250));
        orderQueue.add(order2);
        
        Order expResult = order;
        Order result = orderQueue.nextOrder();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWhenAskedForNextOrderAndNoOrdersLeftReturnNull() {
        OrderQueue orderQueue = new OrderQueue();
        
        Order expResult = null;
        Order result = orderQueue.nextOrder();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWhenProductIsProcessedAndHasTimeRecievedThatProcessedTimeIsSetToTheCurrentTime() {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(1, 1));
        order.addPurchase(new Purchase(2, 1));
        orderQueue.add(order);
        
        orderQueue.processOrder(orderQueue.nextOrder());
        
        long expResult = new Date().getTime();
        long result = order.getTimeProcessed().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    
    @Test
    public void testWhenProductIsProcessedAndDoesNotTimeRecievedThatThrowsAnError() {
        boolean win = false;
        
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(1, 1));
        order.addPurchase(new Purchase(2, 1));
        
        try {
            orderQueue.processOrder(order);
        }
        catch(Exception err) {
            win = true;
        }
        
        assertTrue(win);
    }
    
    @Test
    public void testWhenProductIsFulfilledAndHasTimeProcessedThatTimeFulfilledIsSetToTheCurrentTime() {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(1, 1));
        order.addPurchase(new Purchase(2, 1));
        orderQueue.add(order);
        
        orderQueue.processOrder(orderQueue.nextOrder());
        orderQueue.fulfillOrder(orderQueue.nextOrder());
        
        long expResult = new Date().getTime();
        long result = order.getTimeFulfilled().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    
    @Test
    public void testWhenProductIsFulfilledAndDoesNotTimeProcessedThatThrowsAnError() {
        boolean win = false;
        
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(1, 1));
        order.addPurchase(new Purchase(2, 1));
        orderQueue.add(order);
        
        try {
            orderQueue.fulfillOrder(orderQueue.nextOrder());
        }
        catch(Exception err) {
            win = true;
        }
        
        assertTrue(win);
    }
    
    @Test
    public void testWhenProductIsFulfilledAndDoesNotTimeRecievedThatThrowsAnError() {
        boolean win = false;
        
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(1, 1));
        order.addPurchase(new Purchase(2, 1));
        
        
        try {
            orderQueue.processOrder(order);
            orderQueue.fulfillOrder(orderQueue.nextOrder());
        }
        catch(Exception err) {
            win = true;
        }
        
        assertTrue(win);
    }
}
