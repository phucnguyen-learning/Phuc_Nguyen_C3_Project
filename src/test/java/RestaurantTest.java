import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    void setupTestRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        restaurant=spy(restaurant);
        when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:35:00"));
        boolean isRestaurantOpen = restaurant.isRestaurantOpen();
        assertTrue(isRestaurantOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        restaurant=spy(restaurant);
        when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("22:30:00"));
        boolean isRestaurantOpen = restaurant.isRestaurantOpen();
        assertFalse(isRestaurantOpen);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER TOTAL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void should_return_the_total_order_amount_for_1_selected_item(){
        List<String> selectedItemNames = Collections.singletonList("Sweet corn soup");
        int totalOrder = restaurant.calculateTotalOrderValue(selectedItemNames);
        assertEquals(119, totalOrder);
    }
    @Test
    public void should_return_the_total_order_amount_for_selected_items(){
        List<String> selectedItemNames = Arrays.asList("Sweet corn soup", "Vegetable lasagne");
        int totalOrder = restaurant.calculateTotalOrderValue(selectedItemNames);
        assertEquals(388, totalOrder);
    }

    @Test
    public void should_return_0_if_no_item_selected() {
        List<String> selectedItemNames = new ArrayList<>();
        int totalOrder = restaurant.calculateTotalOrderValue(selectedItemNames);
        assertEquals(0, totalOrder);
    }

    @Test
    public void should_return_correct_total_amount_when_changing_selected_items() {
        List<String> selectedItemNames = Collections.singletonList("Vegetable lasagne");
        int totalOrderAmountAtInitialSelected = restaurant.calculateTotalOrderValue(selectedItemNames);
        selectedItemNames  = Arrays.asList("Sweet corn soup", "Vegetable lasagne");
        int totalOrderAmountAfterChangingSelected = restaurant.calculateTotalOrderValue(selectedItemNames);
        assertEquals(388, totalOrderAmountAfterChangingSelected);
        assertEquals(119, totalOrderAmountAfterChangingSelected - totalOrderAmountAtInitialSelected);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
