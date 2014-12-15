package org.shop;

import org.shop.api.ItemService;
import org.shop.api.OrderService;
import org.shop.api.ProductService;
import org.shop.api.ProposalService;
import org.shop.api.UserService;
import org.shop.config.ShopConfig;
import org.shop.data.Item;
import org.shop.data.Order;
import org.shop.data.Product;
import org.shop.data.Proposal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * The ShopLauncher class.
 */
public class ShopLauncher {
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ShopConfig.class);

        //get by type
        ProductService productService = context.getBean(ProductService.class); 
        OrderService orderService = context.getBean(OrderService.class);
        ItemService itemService = context.getBean(ItemService.class);
        UserService userService = context.getBean(UserService.class);
        ProposalService proposalService = context.getBean(ProposalService.class);

        testProduct(productService, orderService, itemService, userService, proposalService);

        //get by name
        ProductService productService1 = (ProductService) context.getBean("product");
        OrderService orderService1 = (OrderService) context.getBean("order");
        ItemService itemService1 = (ItemService) context.getBean("item");
        UserService userService1 = (UserService) context.getBean("user");
        ProposalService proposalService1 = (ProposalService) context.getBean("proposal");

        testProduct(productService1, orderService1, itemService1, userService1, proposalService1);

        //get by name and type
        ProductService productService2 = context.getBean("product", ProductService.class);
        OrderService orderService2 = context.getBean("order" , OrderService.class);
        ItemService itemService2 = context.getBean("item", ItemService.class);
        UserService userService2 = context.getBean("user", UserService.class);
        ProposalService proposalService2 = context.getBean("proposal", ProposalService.class);

        testProduct(productService2, orderService2, itemService2, userService2, proposalService2);


        //get by name
        ProductService productService3 = (ProductService) context.getBean("productService");
        OrderService orderService3 = (OrderService) context.getBean("orderService");
        ItemService itemService3 = (ItemService) context.getBean("itemService");
        UserService userService3 = (UserService) context.getBean("userService");
        ProposalService proposalService3 = (ProposalService) context.getBean("proposalService");

        testProduct(productService3, orderService3, itemService3, userService3, proposalService3);


    }

    private static void testProduct(ProductService productService, OrderService orderService, ItemService itemService, UserService userService, ProposalService proposalService) {
        Product galaxy = productService.getProductsByName("Samsung Galaxy Tab").get(0);
        Proposal proposal = proposalService.getProposalsByProduct(galaxy).get(0);

        orderService.createOrder(userService.getUserById((long) 1), proposal);

        for (Order order : orderService.getOrdersByUserId((long) 1)) {
            System.out.println(order);

            for (Item item : itemService.getItemsByOrderId(order.getId())) {
                System.out.println(item);
            }
        }
    }
}
