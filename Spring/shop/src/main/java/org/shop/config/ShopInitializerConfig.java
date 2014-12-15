package org.shop.config;

import java.util.HashMap;
import java.util.Map;

import org.shop.DataInitializer;
import org.shop.ProductInitializer;
import org.shop.ProposalInitializer;
import org.shop.SellerInitializer;
import org.shop.UserInitializer;
import org.shop.api.ProductService;
import org.shop.api.SellerService;
import org.shop.api.UserService;
import org.shop.common.Sellers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopInitializerConfig {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private SellerService sellerService;
    
    @Autowired
    private UserService userService;
    
    @Bean(initMethod = "initData")
    public DataInitializer dataInitializer() {
        DataInitializer dataInitializer = new DataInitializer();
        dataInitializer.setProductInitializer(productInitializer());
        dataInitializer.setProposalInitializer(proposalInitializer());
        dataInitializer.setSellerInitializer(sellerInitializer());
        dataInitializer.setUserInitializer(userInitializer());
        
        return dataInitializer;
    }
    
    @Bean
    public ProductInitializer productInitializer() {
        return new ProductInitializer(productService);
    }
    
    @Bean
    public ProposalInitializer proposalInitializer() {
        return new ProposalInitializer();
    }
    
    @Bean
    public SellerInitializer sellerInitializer() {
        Map<Long, String> map = new HashMap<Long, String>();
        map.put(new Long(1), Sellers.AMAZON);
        map.put(new Long(2), Sellers.SAMSUNG);
        map.put(new Long(3), "Apple");
        
        return new SellerInitializer(sellerService, map);
    }
    
    @Bean
    public UserInitializer userInitializer() {
        return new UserInitializer(userService);
    }
}
