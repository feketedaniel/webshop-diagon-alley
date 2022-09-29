package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.mem.ProductCategoryDaoMem;
import com.codecool.shop.dao.mem.ProductDaoMem;
import com.codecool.shop.dao.mem.SupplierDaoMem;
import com.codecool.shop.dao.mem.UserDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;
import org.apache.log4j.chainsaw.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@WebListener
public class Initializer implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Server content initialization...");
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier ollivanders = new Supplier("Ollivanders", "Makers of Fine Wands since 382 B.C.");
        supplierDataStore.add(ollivanders);
        Supplier nimbusRacingBroomCompany = new Supplier("Nimbus Racing Broom Company", "The Nimbus Racing Broom Company was a broomstick company formed in 1967 by Devlin Whitehorn.");
        supplierDataStore.add(nimbusRacingBroomCompany);
        Supplier malkins = new Supplier("Madam Malkin's Robes for All Occasions", "Most of the page was devoted to an advertisement for Madame Malkin's Robes for All Occations, which was apparently having a sale.");
        supplierDataStore.add(malkins);
        Supplier ellerbyAndSpudmore = new Supplier("Ellerby and Spudmore", "Black Forest broomstick manufacturing company created by Ellerby and Able Spudmore");
        supplierDataStore.add(ellerbyAndSpudmore);
        Supplier eeylopsOwlEmporium = new Supplier("Eeylops Owl Emporium", "Eeylops Owl Emporium is a dark shop that sells owls.");
        supplierDataStore.add(eeylopsOwlEmporium);
        Supplier flourishAndBlotts = new Supplier("Flourish and Blotts", "Flourish and Blotts where the shelves were stacked to the ceiling with books as large as paving stones bound in leather; books the size of postage stamps in covers of silk; books full of peculiar symbols and a few books with nothing in them at all.");
        supplierDataStore.add(flourishAndBlotts);
        Supplier sugarplumsSweetsShop = new Supplier("Sugarplum's Sweets Shop", "Magical sweets");
        supplierDataStore.add(sugarplumsSweetsShop);
        Supplier bertieBotts = new Supplier("Bertie Bott's", "Magical sweets");
        supplierDataStore.add(bertieBotts);

        //setting up a new product category
        ProductCategory wand = new ProductCategory("Wands", "magical instrument", "A wand is the object through which a witch or wizard channels his or her magic. It is made from wood and has a magical substance at its core.");
        productCategoryDataStore.add(wand);
        ProductCategory robe = new ProductCategory("Robes", "clothes", "");
        productCategoryDataStore.add(robe);
        ProductCategory broomstick = new ProductCategory("Broomsticks", "magical instrument", "");
        productCategoryDataStore.add(broomstick);
        ProductCategory pets = new ProductCategory("Pets", "magical creatures", "");
        productCategoryDataStore.add(pets);
        ProductCategory sweets = new ProductCategory("Sweets", "magical sweets", "");
        productCategoryDataStore.add(sweets);

        //setting up products and printing it
        productDataStore.add(new Product("Vine wood wand and dragon heartstring core", new BigDecimal("49.9"), "GAL", "Made with unique vine wood and dragon heartstring core, rigid 10 3/4\" long", wand, ollivanders, "https://cdn.shopify.com/s/files/1/0514/6332/3817/products/Hermione2_grande.png"));
        productDataStore.add(new Product("Holly wood wand with phoenix feather core", new BigDecimal("49.9"), "GAL", "Made with  unique holly wood with a phoenix feather core, fairly flexible 11\" long", wand, ollivanders, "https://cdn.shopify.com/s/files/1/0514/6332/3817/products/Harry2_grande.png"));
        productDataStore.add(new Product("Elderberry wood wand with tail hair of a thestral core", new BigDecimal("49.9"), "GAL", "Made with  unique elderberry wood with tail hair of a thestral core, unbending 12 1/4\" long", wand, ollivanders, "https://cdn.shopify.com/s/files/1/0514/6332/3817/products/Dumbledore2_grande.png"));
        productDataStore.add(new Product("Nimbus 2000", new BigDecimal("49.9"), "GAL", "One of the Nimbus Racing Broom Company's most successful models. Highly reliable with good speed and exceptional handling — not for beginners!", broomstick, nimbusRacingBroomCompany, "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/43b88a73-9b7b-426e-b2a1-a05e6c25a3bc/d4qte7b-60276053-af9c-4b6b-9ad9-0d5dbafd7f5b.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzQzYjg4YTczLTliN2ItNDI2ZS1iMmExLWEwNWU2YzI1YTNiY1wvZDRxdGU3Yi02MDI3NjA1My1hZjljLTRiNmItOWFkOS0wZDVkYmFmZDdmNWIucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.DPqAZbB5rajXCqF-z-K0kljMqVR0j5i9ZZXTj3TztN4"));
        productDataStore.add(new Product("Nimbus 2001", new BigDecimal("49.9"), "GAL", "The top of the Nimbus Racing Broom Company's range. Capable of previously unseen speed and control. A world-class broom.", broomstick, nimbusRacingBroomCompany, "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/43b88a73-9b7b-426e-b2a1-a05e6c25a3bc/d4qteb9-3d792244-f793-4a92-8d12-80733b1e88c4.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzQzYjg4YTczLTliN2ItNDI2ZS1iMmExLWEwNWU2YzI1YTNiY1wvZDRxdGViOS0zZDc5MjI0NC1mNzkzLTRhOTItOGQxMi04MDczM2IxZTg4YzQucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.Dx87bIya3ZhQ1uI9CLtKTMlnIA7Qv6A-JK2ypM-sRkw"));
        productDataStore.add(new Product("Firebolt", new BigDecimal("49.9"), "GAL", "This state-of-the-art racing broom sports a streamlined superfine handle of ash, treated with a diamond-hard polish and hand-numbered with its own registration number. Each individually selected birch twig in the broomtail has been honed to aerodynamic perfection, giving the Firebolt unsurpassable balance and pinpoint precision. The firebolt has an acceleration of 150 miles an hour in ten seconds and incorporates an unbreakable Braking Charm.", broomstick, ellerbyAndSpudmore, "https://www.seekpng.com/png/full/151-1519736_harry-potter-broom-png-graphic-black-and-white.png"));
        productDataStore.add(new Product("Chocolate Frog", new BigDecimal("49.9"), "GAL", "Delicious solid milk Chocolate Frog and a lenticular wizard card", sweets, sugarplumsSweetsShop, "https://cdn.shopify.com/s/files/1/0514/6332/3817/products/1338923_1_grande.png?v=1610552119"));
        productDataStore.add(new Product("Bertie Bott's Every Flavour Beans", new BigDecimal("49.9"), "GAL", "Take home these tasty gourmet jellybeans which come in up to 20 flavours. The flavours range from delicious to disgusting.", sweets, bertieBotts, "https://cdn.shopify.com/s/files/1/0514/6332/3817/products/1338924_1_grande.png?v=1610551920"));
        productDataStore.add(new Product("Gryffindor Robe", new BigDecimal("49.9"), "GAL", "Hogwarts school uniform", robe, malkins, "https://cdn.shopify.com/s/files/1/0514/6332/3817/products/1296477_1_grande.png?v=1616145395"));
        productDataStore.add(new Product("Hufflepuff Robe", new BigDecimal("49.9"), "GAL", "Hogwarts school uniform", robe, malkins, "https://cdn.shopify.com/s/files/1/0514/6332/3817/products/1296498_1296661_0_grande.png?v=1616145737"));
        productDataStore.add(new Product("Ravenclaw Robe", new BigDecimal("49.9"), "GAL", "Hogwarts school uniform", robe, malkins, "https://cdn.shopify.com/s/files/1/0514/6332/3817/products/1296491_1296657_0_grande.png?v=1613579776"));
        productDataStore.add(new Product("Slytherin Robe", new BigDecimal("49.9"), "GAL", "Hogwarts school uniform", robe, malkins, "https://cdn.shopify.com/s/files/1/0514/6332/3817/products/1296484_1296653_0_grande.png?v=1613579670"));
        productDataStore.add(new Product("Aurora", new BigDecimal("49.9"), "GAL", "Snowy Owl", pets, eeylopsOwlEmporium, "https://www.seekpng.com/png/full/46-465733_snowy-owl-png.png"));

        UserDao userDao = UserDaoMem.getInstance();

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = null;
        byte[] hash;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        userDao.add(new User("Dani","dani@gmail.com",hash,salt));

        logger.info("Server content initialization finished.");
    }
}
